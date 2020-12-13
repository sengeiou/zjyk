package com.lys.app.manager;

import com.lys.protobuf.SSvnDirObj;
import com.lys.utils.CommandHelper;
import com.lys.utils.FsUtils;
import com.lys.utils.LOG;
import com.lys.utils.TimeDebug;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.tmatesoft.svn.core.*;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.*;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.*;

@Component
public class SVNManager
{
	@Value("${app.config.svn.url}")
	private String svnUrl = null;

	@Value("${app.config.svn.account}")
	private String svnAccount = null;

	@Value("${app.config.svn.psw}")
	private String svnPsw = null;

	private SVNRepository repository = null;

	private SVNClientManager svnClientManager = null;

	private SVNCopyClient copyClient = null;
	private SVNUpdateClient updateClient = null;
	private SVNCommitClient commitClient = null;
	private SVNWCClient wcClient = null;
	private SVNStatusClient statusClient = null;

	private boolean initialize(String url, String username, String password)
	{
		LOG.v(String.format("svnUrl = %s", url));
		LOG.v(String.format("svnAccount = %s, svnPsw = %s", username, password));

		DAVRepositoryFactory.setup();
		SVNRepositoryFactoryImpl.setup();
		FSRepositoryFactory.setup();

		try
		{
			repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
			ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password);
			repository.setAuthenticationManager(authManager);

			LOG.v("root : " + repository.getRepositoryRoot(true));
//			LOG.v("latestRevision : " + repository.getLatestRevision());

			ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
//			svnClientManager = SVNClientManager.newInstance((DefaultSVNOptions) options, username, password);
			svnClientManager = SVNClientManager.newInstance(options, authManager);

			copyClient = svnClientManager.getCopyClient();
			copyClient.setIgnoreExternals(false);
			copyClient.setEventHandler(new ISVNEventHandler()
			{
				@Override
				public void checkCancelled() throws SVNCancelException
				{
//					LOG.v("svn copy checkCancelled");
				}

				@Override
				public void handleEvent(SVNEvent event, double progress) throws SVNException
				{
					LOG.v(String.format("svn copy %s : %s", event, progress));
				}
			});

			updateClient = svnClientManager.getUpdateClient();
			updateClient.setIgnoreExternals(false);
			DefaultSVNOptions updateOptions = (DefaultSVNOptions) updateClient.getOptions();
			updateOptions.setConflictHandler(new ISVNConflictHandler()
			{
				@Override
				public SVNConflictResult handleConflict(SVNConflictDescription conflictDescription) throws SVNException
				{
					SVNConflictReason reason = conflictDescription.getConflictReason();
					SVNMergeFileSet mergeFiles = conflictDescription.getMergeFiles();

					LOG.v("conflict reason : " + reason);
//					LOG.v("conflict getWCFile : " + mergeFiles.getWCFile());
					LOG.v("conflict getResultFile : " + mergeFiles.getResultFile());

//					SVNConflictChoice choice = SVNConflictChoice.POSTPONE;
//					SVNConflictChoice choice = SVNConflictChoice.MINE_FULL;
					SVNConflictChoice choice = SVNConflictChoice.THEIRS_FULL;

					return new SVNConflictResult(choice, mergeFiles.getResultFile());
				}
			});
			updateClient.setEventHandler(new ISVNEventHandler()
			{
				@Override
				public void checkCancelled() throws SVNCancelException
				{
//					LOG.v("svn update checkCancelled");
				}

				@Override
				public void handleEvent(SVNEvent event, double progress) throws SVNException
				{
					LOG.v(String.format("svn update %s : %s", event, progress));
				}
			});

			commitClient = svnClientManager.getCommitClient();
			commitClient.setIgnoreExternals(false);
			commitClient.setEventHandler(new ISVNEventHandler()
			{
				@Override
				public void checkCancelled() throws SVNCancelException
				{
//					LOG.v("svn commit checkCancelled");
				}

				@Override
				public void handleEvent(SVNEvent event, double progress) throws SVNException
				{
					LOG.v(String.format("svn commit %s : %s", event, progress));
				}
			});

			wcClient = svnClientManager.getWCClient();

			statusClient = svnClientManager.getStatusClient();

			return true;
		}
		catch (SVNException e)
		{
			e.printStackTrace();
		}

		return false;
	}

	@PostConstruct
	public void init()
	{
		LOG.v("-------------------- " + this.getClass().getSimpleName() + " --------- init ---------");
		initialize(svnUrl, svnAccount, svnPsw);
	}

	// ------------------------------------ repository --------------------------------------------

	public void getDir(String path, List<SSvnDirObj> objs) throws SVNException
	{
		Collection entries = repository.getDir(path, -1, null, (Collection) null);
		Iterator iterator = entries.iterator();
		while (iterator.hasNext())
		{
			SVNDirEntry entry = (SVNDirEntry) iterator.next();
//			LOG.v(String.format("%s : %s : %s : %s : %s", entry.getKind(), entry.getName(), entry.getAuthor(), entry.getRevision(), entry.getDate()));
			SSvnDirObj obj = new SSvnDirObj();
			obj.isDir = entry.getKind() == SVNNodeKind.DIR;
			obj.name = entry.getName();
			objs.add(obj);
			if (obj.isDir)
			{
				getDir(path + "/" + obj.name, obj.objs);
			}
		}
	}

	public List<SSvnDirObj> getDir(String path)
	{
		try
		{
			List<SSvnDirObj> objs = new ArrayList<>();
			getDir(path, objs);
			return objs;
		}
		catch (SVNException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public void checkPath(String path)
	{
		try
		{
			SVNNodeKind nodeKind = repository.checkPath(path, -1);
			if (nodeKind == SVNNodeKind.NONE)
			{
				LOG.v(path + " not exists");
			}
			else if (nodeKind == SVNNodeKind.DIR)
			{
				LOG.v(path + " is dir");
			}
			else if (nodeKind == SVNNodeKind.FILE)
			{
				LOG.v(path + " is file");
			}
			if (false)
			{
				SVNProperties fileProperties = new SVNProperties();
				repository.getFile(path, -1, fileProperties, null);
				String mimeType = fileProperties.getStringValue(SVNProperty.MIME_TYPE);
				LOG.v("mimeType : " + mimeType);
				LOG.v("isTextMimeType : " + SVNProperty.isTextMimeType(mimeType));
				Iterator iterator = fileProperties.nameSet().iterator();
				while (iterator.hasNext())
				{
					String propertyName = (String) iterator.next();
					String propertyValue = fileProperties.getStringValue(propertyName);
					LOG.v(propertyName + " : " + propertyValue);
				}
			}
		}
		catch (SVNException e)
		{
			e.printStackTrace();
		}
	}

	// ------------------------------------ copyClient --------------------------------------------

	public SVNCommitInfo doCopy(String urlSrc, String urlDst, String message)
	{
		LOG.v("svn copy : " + urlSrc + " --> " + urlDst + " : " + message);
		SVNCommitInfo ret = null;
		try
		{
			SVNCopySource[] copySources = new SVNCopySource[]{new SVNCopySource(SVNRevision.HEAD, SVNRevision.HEAD, SVNURL.parseURIEncoded(urlSrc))};
			ret = copyClient.doCopy(copySources, SVNURL.parseURIEncoded(urlDst), false, true, true, message, null);
		}
		catch (SVNException e)
		{
			e.printStackTrace();
		}
		if (ret != null)
			LOG.v("success : " + ret);
		else
			LOG.e("fail");
		return ret;
	}

	// ------------------------------------ updateClient --------------------------------------------

	public long reCheckout(String url, File local)
	{
		FsUtils.delete(local);
		if (!local.exists())
			return doCheckout(url, local);
		else
			return -1;
	}

	public long checkoutOrUpdate(String url, File local)
	{
		if (isVersionedDirectory(local))
//		if (local.exists())
			return doUpdate(local);
		else
			return doCheckout(url, local);
	}

	public long doUpdate(File local)
	{
		LOG.v("svn update : " + local);
		long ret = -1;
		try
		{
			ret = updateClient.doUpdate(local, SVNRevision.HEAD, SVNDepth.INFINITY, true, false);
		}
		catch (SVNException e)
		{
			e.printStackTrace();
		}
		if (ret != -1)
			LOG.v("success : " + ret);
		else
			LOG.e("fail");
		return ret;
	}

	public long doCheckout(String url, File local)
	{
		LOG.v("svn checkout : " + url + " --> " + local);
		long ret = -1;
//		FsUtils.createDir(local);
		try
		{
			ret = updateClient.doCheckout(SVNURL.parseURIEncoded(url), local, SVNRevision.HEAD, SVNRevision.HEAD, SVNDepth.INFINITY, true);
		}
		catch (SVNException e)
		{
			e.printStackTrace();
		}
		if (ret != -1)
			LOG.v("success : " + ret);
		else
			LOG.e("fail");
		return ret;
	}

	// ------------------------------------ commitClient --------------------------------------------

	public void commitAll(File local, String message)
	{
//		if (!isWorkingCopy(local))
//		{
//			checkout(url, local);
//		}
		if (!isVersionedDirectory(local))
		{
			doAdd(local);
		}
		doCommit(local, message);
	}

	public SVNCommitInfo doCommit(File local, String message)
	{
		LOG.v("svn commit : " + local + " : " + message);
		SVNCommitInfo ret = null;
		try
		{
			ret = commitClient.doCommit(new File[]{local}, true, message, null, null, true, false, SVNDepth.INFINITY);
		}
		catch (SVNException e)
		{
			e.printStackTrace();
		}
		if (ret != null)
			LOG.v("success : " + ret);
		else
			LOG.e("fail");
		return ret;
	}

	public SVNCommitInfo doMkDir(String url, String message)
	{
		LOG.v("svn makeDirectory : " + url + " : " + message);
		SVNCommitInfo ret = null;
		try
		{
			ret = commitClient.doMkDir(new SVNURL[]{SVNURL.parseURIEncoded(url)}, message);
		}
		catch (SVNException e)
		{
			e.printStackTrace();
		}
		if (ret != null)
			LOG.v("success : " + ret);
		else
			LOG.e("fail");
		return ret;
	}

	public SVNCommitInfo doImport(String url, File local, String message)
	{
		LOG.v("svn importDirectory : " + url + " <-- " + local + " : " + message);
		SVNCommitInfo ret = null;
		try
		{
			ret = commitClient.doImport(local, SVNURL.parseURIEncoded(url), message, null, false, false, SVNDepth.INFINITY);
		}
		catch (SVNException e)
		{
			e.printStackTrace();
		}
		if (ret != null)
			LOG.v("success : " + ret);
		else
			LOG.e("fail");
		return ret;
	}

	// ------------------------------------ wcClient --------------------------------------------

	public void doAdd(File local)
	{
		LOG.v("svn addEntry : " + local);
		try
		{
			wcClient.doAdd(new File[]{local}, false, false, false, SVNDepth.INFINITY, false, false, true);
		}
		catch (SVNException e)
		{
			e.printStackTrace();
		}
	}

	public File getWorkingCopyRoot(File local)
	{
//		LOG.v("svn getWorkingCopy : " + local);
		File ret = null;
		try
		{
			ret = SVNWCUtil.getWorkingCopyRoot(local, false);
		}
		catch (SVNException e)
		{
			e.printStackTrace();
		}
//		if (ret != null)
//			LOG.v("success : " + ret);
//		else
//			LOG.e("fail");
		return ret;
	}

	public boolean isWorkingCopy(File local)
	{
		return getWorkingCopyRoot(local) != null;
	}

	public boolean isVersionedDirectory(File local)
	{
		return SVNWCUtil.isVersionedDirectory(local);
	}

	// ------------------------------------ statusClient --------------------------------------------

	public SVNStatus doStatus(File local, boolean remote)
	{
		LOG.v("svn showStatus : " + local);
		SVNStatus ret = null;
		if (isWorkingCopy(local))
		{
			try
			{
				ret = statusClient.doStatus(local, remote);
			}
			catch (SVNException e)
			{
				e.printStackTrace();
			}
		}
		if (ret != null)
		{
			LOG.v("success : " + ret);
			logStatus(ret);
		}
//		else
//			LOG.e("fail");
		return ret;
	}

	public void logStatus(SVNStatus ret)
	{
		LOG.v(String.format("%30s%40s = %s", "SVNURL", "getURL", ret.getURL()));
		LOG.v(String.format("%30s%40s = %s", "SVNURL", "getRemoteURL", ret.getRemoteURL()));
		LOG.v(String.format("%30s%40s = %s", "File", "getFile", ret.getFile()));
		LOG.v(String.format("%30s%40s = %s", "SVNNodeKind", "getKind", ret.getKind()));
		LOG.v(String.format("%30s%40s = %s", "SVNRevision", "getRevision", ret.getRevision()));
		LOG.v(String.format("%30s%40s = %s", "SVNRevision", "getCommittedRevision", ret.getCommittedRevision()));
		LOG.v(String.format("%30s%40s = %s", "Date", "getCommittedDate", ret.getCommittedDate()));
		LOG.v(String.format("%30s%40s = %s", "String", "getAuthor", ret.getAuthor()));
		LOG.v(String.format("%30s%40s = %s", "SVNStatusType", "getContentsStatus", ret.getContentsStatus()));
		LOG.v(String.format("%30s%40s = %s", "SVNStatusType", "getPropertiesStatus", ret.getPropertiesStatus()));
		LOG.v(String.format("%30s%40s = %s", "SVNStatusType", "getRemoteContentsStatus", ret.getRemoteContentsStatus()));
		LOG.v(String.format("%30s%40s = %s", "SVNStatusType", "getRemotePropertiesStatus", ret.getRemotePropertiesStatus()));
		LOG.v(String.format("%30s%40s = %s", "boolean", "isLocked", ret.isLocked()));
		LOG.v(String.format("%30s%40s = %s", "boolean", "isCopied", ret.isCopied()));
		LOG.v(String.format("%30s%40s = %s", "boolean", "isSwitched", ret.isSwitched()));
		LOG.v(String.format("%30s%40s = %s", "boolean", "isFileExternal", ret.isFileExternal()));
		LOG.v(String.format("%30s%40s = %s", "File", "getConflictNewFile", ret.getConflictNewFile()));
		LOG.v(String.format("%30s%40s = %s", "File", "getConflictOldFile", ret.getConflictOldFile()));
		LOG.v(String.format("%30s%40s = %s", "File", "getConflictWrkFile", ret.getConflictWrkFile()));
		LOG.v(String.format("%30s%40s = %s", "File", "getPropRejectFile", ret.getPropRejectFile()));
		LOG.v(String.format("%30s%40s = %s", "String", "getCopyFromURL", ret.getCopyFromURL()));
		LOG.v(String.format("%30s%40s = %s", "SVNRevision", "getCopyFromRevision", ret.getCopyFromRevision()));
		LOG.v(String.format("%30s%40s = %s", "SVNLock", "getRemoteLock", ret.getRemoteLock()));
		LOG.v(String.format("%30s%40s = %s", "SVNLock", "getLocalLock", ret.getLocalLock()));
		LOG.v(String.format("%30s%40s = %s", "Map", "getEntryProperties", ret.getEntryProperties()));
		LOG.v(String.format("%30s%40s = %s", "SVNRevision", "getRemoteRevision", ret.getRemoteRevision()));
		LOG.v(String.format("%30s%40s = %s", "SVNNodeKind", "getRemoteKind", ret.getRemoteKind()));
		LOG.v(String.format("%30s%40s = %s", "Date", "getRemoteDate", ret.getRemoteDate()));
		LOG.v(String.format("%30s%40s = %s", "String", "getRemoteAuthor", ret.getRemoteAuthor()));
		LOG.v(String.format("%30s%40s = %s", "Date", "getWorkingContentsDate", ret.getWorkingContentsDate()));
		LOG.v(String.format("%30s%40s = %s", "Date", "getWorkingPropertiesDate", ret.getWorkingPropertiesDate()));
		LOG.v(String.format("%30s%40s = %s", "SVNEntry", "getEntry", ret.getEntry()));
		LOG.v(String.format("%30s%40s = %s", "String", "getChangelistName", ret.getChangelistName()));
		LOG.v(String.format("%30s%40s = %s", "SVNTreeConflictDescription", "getTreeConflict", ret.getTreeConflict()));
		LOG.v(String.format("%30s%40s = %s", "int", "getWorkingCopyFormat", ret.getWorkingCopyFormat()));
		LOG.v(String.format("%30s%40s = %s", "boolean", "isConflicted", ret.isConflicted()));
		LOG.v(String.format("%30s%40s = %s", "boolean", "isVersioned", ret.isVersioned()));
		LOG.v(String.format("%30s%40s = %s", "SVNStatusType", "getRemoteNodeStatus", ret.getRemoteNodeStatus()));
		LOG.v(String.format("%30s%40s = %s", "SVNStatusType", "getCombinedNodeAndContentsStatus", ret.getCombinedNodeAndContentsStatus()));
		LOG.v(String.format("%30s%40s = %s", "SVNStatusType", "getCombinedRemoteNodeAndContentsStatus", ret.getCombinedRemoteNodeAndContentsStatus()));
		LOG.v(String.format("%30s%40s = %s", "SVNStatusType", "getNodeStatus", ret.getNodeStatus()));
		LOG.v(String.format("%30s%40s = %s", "SVNURL", "getRepositoryRootURL", ret.getRepositoryRootURL()));
		LOG.v(String.format("%30s%40s = %s", "String", "getRepositoryUUID", ret.getRepositoryUUID()));
		LOG.v(String.format("%30s%40s = %s", "String", "getRepositoryRelativePath", ret.getRepositoryRelativePath()));
		LOG.v(String.format("%30s%40s = %s", "SVNDepth", "getDepth", ret.getDepth()));
		LOG.v(String.format("%30s%40s = %s", "File", "getMovedToPath", ret.getMovedToPath()));
		LOG.v(String.format("%30s%40s = %s", "File", "getMovedFromPath", ret.getMovedFromPath()));
	}

	// ------------------------------------ task --------------------------------------------

	public boolean hasDotSvn(File taskDir)
	{
		return new File(taskDir, ".svn").exists();
	}

	@Value("${spring.servlet.multipart.location}")
	private String uploadPath = null;

	public String getTaskPath(String userId)
	{
		return String.format("/lys.tasks/%s", userId);
	}

	public String getTaskUrl(String userId) throws SVNException
	{
		return repository.getRepositoryRoot(true) + getTaskPath(userId);
	}

	public File getTaskDir(String userId)
	{
		return new File(uploadPath, getTaskPath(userId));
	}

	public String getTaskPath(String userId, String id)
	{
		return String.format("/lys.tasks/%s/%s", userId, id);
	}

	public String getTaskUrl(String userId, String id) throws SVNException
	{
		return repository.getRepositoryRoot(true) + getTaskPath(userId, id);
	}

	public File getTaskDir(String userId, String id)
	{
		return new File(uploadPath, getTaskPath(userId, id));
	}

	public String getTaskPath(String userId, String id, String pageDir)
	{
		return String.format("/lys.tasks/%s/%s/%s", userId, id, pageDir);
	}

	public String getTaskUrl(String userId, String id, String pageDir) throws SVNException
	{
		return repository.getRepositoryRoot(true) + getTaskPath(userId, id, pageDir);
	}

	public long update(File taskDir) throws SVNException
	{
		TimeDebug.record("-------- update start " + taskDir);
		long version = updateClient.doUpdate(taskDir, SVNRevision.HEAD, SVNDepth.INFINITY, true, false);
		TimeDebug.record("-------- update over " + version);
		return version;
	}

	public long checkout(String taskUrl, File taskDir) throws SVNException
	{
		TimeDebug.record("-------- checkout start " + taskDir);
		long version = updateClient.doCheckout(SVNURL.parseURIEncoded(taskUrl), taskDir, SVNRevision.HEAD, SVNRevision.HEAD, SVNDepth.INFINITY, true);
		TimeDebug.record("-------- checkout over " + version);
		return version;
	}

	public SVNCommitInfo Import(String taskUrl, File taskDir, String message) throws SVNException
	{
		TimeDebug.record("-------- Import start " + taskDir);
		SVNCommitInfo info = commitClient.doImport(taskDir, SVNURL.parseURIEncoded(taskUrl), message, null, false, false, SVNDepth.INFINITY);
		TimeDebug.record("-------- Import over " + info);
		return info;
	}

	public void commitAdd(File taskDir) throws SVNException
	{
		File[] files = taskDir.listFiles();
		for (File file : files)
		{
			if (!file.getName().equals(".svn"))
			{
				TimeDebug.record("");
				SVNStatus status = statusClient.doStatus(file, false);
				TimeDebug.record("doStatus");
				if (status.getNodeStatus() == SVNStatusType.STATUS_UNVERSIONED)
				{
					TimeDebug.record("");
					wcClient.doAdd(new File[]{file}, false, false, false, SVNDepth.INFINITY, false, false, true);
					TimeDebug.record("doAdd : " + file);
				}
				else
				{
					if (file.isDirectory())
					{
						commitAdd(file);
					}
				}
			}
		}
	}

	public void commitDelete(String taskPath, File taskDir) throws SVNException
	{
		TimeDebug.record("");
		Collection entries = repository.getDir(taskPath, -1, null, (Collection) null);
		TimeDebug.record("getDir");
		Iterator iterator = entries.iterator();
		while (iterator.hasNext())
		{
			SVNDirEntry entry = (SVNDirEntry) iterator.next();
//			LOG.v(String.format("%s : %s : %s : %s : %s", entry.getKind(), entry.getName(), entry.getAuthor(), entry.getRevision(), entry.getDate()));
			File file = new File(taskDir, entry.getName());
			if (!file.exists() || (entry.getKind() == SVNNodeKind.DIR && file.isFile()) || (entry.getKind() == SVNNodeKind.FILE && file.isDirectory()))
			{
				TimeDebug.record("");
				wcClient.doDelete(file, false, false);
				TimeDebug.record("doDelete : " + taskPath + "/" + entry.getName());
			}
			else
			{
				if (entry.getKind() == SVNNodeKind.DIR)
					commitDelete(taskPath + "/" + entry.getName(), file);
			}
		}
	}

	public void commitDeleteAdd(String taskPath, File taskDir) throws SVNException
	{
		TimeDebug.record("");
		Collection entries = repository.getDir(taskPath, -1, null, (Collection) null);
		TimeDebug.record("getDir");

		HashMap<String, Boolean> map = new HashMap<>();

		Iterator iterator = entries.iterator();
		while (iterator.hasNext())
		{
			SVNDirEntry entry = (SVNDirEntry) iterator.next();
//			LOG.v(String.format("%s : %s : %s : %s : %s", entry.getKind(), entry.getName(), entry.getAuthor(), entry.getRevision(), entry.getDate()));
			File file = new File(taskDir, entry.getName());
			if (!file.exists() || (entry.getKind() == SVNNodeKind.DIR && file.isFile()) || (entry.getKind() == SVNNodeKind.FILE && file.isDirectory()))
			{
				TimeDebug.record("");
				wcClient.doDelete(file, false, false);
				TimeDebug.record("doDelete : " + taskPath + "/" + entry.getName());
			}
			else
			{
				map.put(entry.getName(), entry.getKind() == SVNNodeKind.DIR);
			}
		}

		File[] files = taskDir.listFiles();
		for (File file : files)
		{
			if (!file.getName().equals(".svn"))
			{
				if (!map.containsKey(file.getName()))
				{
					TimeDebug.record("");
					wcClient.doAdd(new File[]{file}, false, false, false, SVNDepth.INFINITY, false, false, true);
					TimeDebug.record("doAdd : " + file);
				}
			}
		}

		for (Map.Entry<String, Boolean> entry : map.entrySet())
		{
			String name = entry.getKey();
			boolean isDir = entry.getValue();
			if (isDir)
			{
				File file = new File(taskDir, name);
				commitDeleteAdd(taskPath + "/" + name, file);
			}
		}
	}

	public SVNCommitInfo commit(String taskPath, String taskUrl, File taskDir, String message) throws SVNException
	{
//		LOG.v("-------- commit add " + taskDir);
//		commitAdd(taskDir);
//
//		LOG.v("-------- commit delete " + taskDir);
//		commitDelete(taskPath, taskDir);

		LOG.v("-------- commit delete add " + taskDir);
		commitDeleteAdd(taskPath, taskDir);

		LOG.v("-------- commit start " + taskDir);
		TimeDebug.record("");
		SVNCommitInfo info = commitClient.doCommit(new File[]{taskDir}, false, message, null, null, false, false, SVNDepth.INFINITY);
		TimeDebug.record("doCommit");
		LOG.v("-------- commit over " + info);

		return info;
	}

	public void deleteIfExists(String userId, String id, String message) throws SVNException
	{
		String taskPath = getTaskPath(userId, id);
		String taskUrl = getTaskUrl(userId, id);
		SVNNodeKind nodeKind = repository.checkPath(taskPath, -1);
		if (nodeKind == SVNNodeKind.DIR || nodeKind == SVNNodeKind.FILE)
		{
			LOG.v("-------- delete start " + taskPath);
			SVNCommitInfo info = commitClient.doDelete(new SVNURL[]{SVNURL.parseURIEncoded(taskUrl)}, message);
			LOG.v("-------- delete over " + info);
		}
	}

	public void deleteIfExists(String userId, String message) throws SVNException
	{
		String taskPath = getTaskPath(userId);
		String taskUrl = getTaskUrl(userId);
		SVNNodeKind nodeKind = repository.checkPath(taskPath, -1);
		if (nodeKind == SVNNodeKind.DIR || nodeKind == SVNNodeKind.FILE)
		{
			LOG.v("-------- delete start " + taskPath);
			SVNCommitInfo info = commitClient.doDelete(new SVNURL[]{SVNURL.parseURIEncoded(taskUrl)}, message);
			LOG.v("-------- delete over " + info);
		}
	}

	public void copy(String userIdSrc, String idSrc, String userIdDst, String idDst, String message)
	{
		try
		{
			String taskPathSrc = getTaskPath(userIdSrc, idSrc);
			String taskUrlSrc = getTaskUrl(userIdSrc, idSrc);
			SVNNodeKind nodeKindSrc = repository.checkPath(taskPathSrc, -1);
			if (nodeKindSrc == SVNNodeKind.DIR)
			{
				String taskPathDst = getTaskPath(userIdDst, idDst);
				String taskUrlDst = getTaskUrl(userIdDst, idDst);
				SVNNodeKind nodeKindDst = repository.checkPath(taskPathDst, -1);
				if (nodeKindDst == SVNNodeKind.NONE)
				{
					LOG.v("-------- copy start " + taskPathSrc + " --> " + taskPathDst);
					SVNCopySource[] copySources = new SVNCopySource[]{new SVNCopySource(SVNRevision.HEAD, SVNRevision.HEAD, SVNURL.parseURIEncoded(taskUrlSrc))};
					SVNCommitInfo info = copyClient.doCopy(copySources, SVNURL.parseURIEncoded(taskUrlDst), false, true, true, message, null);
					LOG.v("-------- copy over " + info);
				}
			}
		}
		catch (SVNException e)
		{
			e.printStackTrace();
		}
	}

	private void copy(String userIdSrc, String idSrc, String pageDirSrc, String userIdDst, String idDst, String pageDirDst, String message) throws Exception
	{
		String taskPathSrc = getTaskPath(userIdSrc, idSrc, pageDirSrc);
		String taskUrlSrc = getTaskUrl(userIdSrc, idSrc, pageDirSrc);
		SVNNodeKind nodeKindSrc = repository.checkPath(taskPathSrc, -1);
		if (nodeKindSrc == SVNNodeKind.DIR)
		{
			String taskPathDst = getTaskPath(userIdDst, idDst, pageDirDst);
			String taskUrlDst = getTaskUrl(userIdDst, idDst, pageDirDst);
			SVNNodeKind nodeKindDst = repository.checkPath(taskPathDst, -1);
			if (nodeKindDst == SVNNodeKind.NONE)
			{
				LOG.v("-------- copy start " + taskPathSrc + " --> " + taskPathDst);
				SVNCopySource[] copySources = new SVNCopySource[]{new SVNCopySource(SVNRevision.HEAD, SVNRevision.HEAD, SVNURL.parseURIEncoded(taskUrlSrc))};
				SVNCommitInfo info = copyClient.doCopy(copySources, SVNURL.parseURIEncoded(taskUrlDst), false, true, true, message, null);
				LOG.v("-------- copy over " + info);
			}
		}
	}

//	public  long getRemoteVision(String taskPath) throws SVNException
//	{
//		TimeDebug.record("");
//		long version = repository.getFile(taskPath, -1, null, null);
//		TimeDebug.record("getFile : " + version);
//		return version;
//	}
//
//	public  long getLocalVision(File taskDir) throws SVNException
//	{
//		TimeDebug.record("");
//		SVNStatus status = statusClient.doStatus(taskDir, false);
////		logStatus(status);
//		long version = status.getRevision().getNumber();
//		TimeDebug.record("doStatus : " + version);
//		return version;
//	}
//
//	public  boolean isNewestVision(String userId, String id) throws SVNException
//	{
//		String taskPath = getTaskPath(userId, id);
//		File taskDir = getTaskDir(userId, id);
//		return getRemoteVision(taskPath) == getLocalVision(taskDir);
//	}

	public boolean isNewestVision(String userId, String id) throws SVNException
	{
		File taskDir = getTaskDir(userId, id);
		TimeDebug.record("");
		SVNStatus status = statusClient.doStatus(taskDir, true);
		long localVision = status.getCommittedRevision().getNumber();
		long remoteVision = status.getRemoteRevision().getNumber();
		if (remoteVision == -1)
			remoteVision = status.getRevision().getNumber();
		TimeDebug.record("isNewestVision : " + status.getCommittedRevision() + "  " + status.getRemoteRevision() + "  " + status.getRevision());
		return localVision == remoteVision;
	}

	public boolean relocateIfNeed(File taskDir) throws SVNException
	{
		if (taskDir.exists())
		{
			if (taskDir.isDirectory())
			{
				if (hasDotSvn(taskDir))
				{
					TimeDebug.record("");
					SVNURL newURL = repository.getRepositoryRoot(true);
					TimeDebug.record("getRepositoryRoot : " + newURL);

					TimeDebug.record("");
					SVNStatus status = statusClient.doStatus(taskDir, false);
					SVNURL oldURL = status.getRepositoryRootURL();
					TimeDebug.record("doStatus : " + oldURL);

					if (!newURL.equals(oldURL))
					{
						TimeDebug.record("");
						updateClient.doRelocate(taskDir, oldURL, newURL, true);
						TimeDebug.record("doRelocate : " + oldURL + " --> " + newURL);
						return true;
					}
				}
			}
		}
		return false;
	}

	public void deleteLocalTaskDir(String userId, String id)
	{
		File taskDir = getTaskDir(userId, id);
		FsUtils.delete(taskDir);
	}

	public void deleteLocalTaskSvnDir(String userId, String id)
	{
		File taskDir = getTaskDir(userId, id);
		FsUtils.delete(new File(taskDir, ".svn"));
	}

	public SvnTaskResult updateTaskImpl(String userId, String id) throws SVNException
	{
		String taskPath = getTaskPath(userId, id);
		String taskUrl = getTaskUrl(userId, id);
		File taskDir = getTaskDir(userId, id);

		TimeDebug.record("");
		SVNNodeKind nodeKind = repository.checkPath(taskPath, -1);
		TimeDebug.record("checkPath");
		if (nodeKind == SVNNodeKind.NONE)
		{
			LOG.v(taskPath + " not exists");
			return new SvnTaskResult(ResultCode_Success);
		}
		else if (nodeKind == SVNNodeKind.DIR)
		{
			LOG.v(taskPath + " is dir");
			if (taskDir.exists())
			{
				if (taskDir.isDirectory())
				{
					if (hasDotSvn(taskDir))
					{
						if (!isNewestVision(userId, id))
						{
							update(taskDir);
						}
					}
					else
					{
						checkout(taskUrl, taskDir);
					}
				}
				else
				{
					return new SvnTaskResult(ResultCode_Fail, taskDir + " is file");
				}
			}
			else
			{
				if (!taskDir.exists())
					taskDir.mkdirs();
				checkout(taskUrl, taskDir);
			}
		}
		else if (nodeKind == SVNNodeKind.FILE)
		{
			return new SvnTaskResult(ResultCode_Fail, taskPath + " is file");
		}

		return new SvnTaskResult(ResultCode_Success);
	}

	public SvnTaskResult commitTaskImpl(String userId, String id, String message) throws SVNException
	{
		String taskPath = getTaskPath(userId, id);
		String taskUrl = getTaskUrl(userId, id);
		File taskDir = getTaskDir(userId, id);

		if (taskDir.exists())
		{
			if (taskDir.isDirectory())
			{
				TimeDebug.record("");
				SVNNodeKind nodeKind = repository.checkPath(taskPath, -1);
				TimeDebug.record("checkPath");
				if (nodeKind == SVNNodeKind.NONE)
				{
					LOG.v(taskPath + " not exists");
					if (!hasDotSvn(taskDir))
					{
						Import(taskUrl, taskDir, message);
						checkout(taskUrl, taskDir);
					}
					else
					{
						Import(taskUrl, taskDir, message);
						checkout(taskUrl, taskDir);
					}
				}
				else if (nodeKind == SVNNodeKind.DIR)
				{
					LOG.v(taskPath + " is dir");
					if (!hasDotSvn(taskDir))
					{
						checkout(taskUrl, taskDir);
						commit(taskPath, taskUrl, taskDir, message);
					}
					else
					{
						if (isNewestVision(userId, id))
						{
							commit(taskPath, taskUrl, taskDir, message);
						}
						else
						{
							return new SvnTaskResult(ResultCode_NotNewestVision, "当前不是最新版本");
						}
					}
				}
				else if (nodeKind == SVNNodeKind.FILE)
				{
					return new SvnTaskResult(ResultCode_Fail, taskPath + " is file");
				}
			}
			else
			{
				return new SvnTaskResult(ResultCode_Fail, taskDir + " is file");
			}
		}

		return new SvnTaskResult(ResultCode_Success);
	}

	public SvnTaskResult updateTask(final boolean force, final String userId, final String id)
	{
		TimeDebug.init();
		SvnTaskResult result;
		try
		{
			if (force)
				FsUtils.delete(getTaskDir(userId, id));
			result = updateTaskImpl(userId, id);
		}
		catch (SVNException e)
		{
			e.printStackTrace();
			if (e.getErrorMessage().getErrorCode() == SVNErrorCode.RA_DAV_REQUEST_FAILED)
			{
				try
				{
					File taskDir = getTaskDir(userId, id);
					if (relocateIfNeed(taskDir))
					{
						LOG.v("服务器地址变更，已更正！");
						result = updateTaskImpl(userId, id);
					}
					else
					{
						result = new SvnTaskResult(ResultCode_Fail, "网络错误 " + e.getErrorMessage().getMessage());
					}
				}
				catch (SVNException e1)
				{
					e1.printStackTrace();
					result = new SvnTaskResult(ResultCode_Fail, "二次处理错误 " + e.getErrorMessage().getMessage());
				}
			}
			else if (e.getErrorMessage().getErrorCode() == SVNErrorCode.WC_LOCKED)
			{
				LOG.v("目录锁定，已修正！");
				try
				{
					deleteLocalTaskSvnDir(userId, id);
					result = updateTaskImpl(userId, id);
				}
				catch (SVNException e1)
				{
					e1.printStackTrace();
					result = new SvnTaskResult(ResultCode_Fail, "二次处理错误 " + e.getErrorMessage().getMessage());
				}
			}
			else
			{
				result = new SvnTaskResult(ResultCode_Fail, "未处理错误 " + e.getErrorMessage().getMessage());
			}
		}
		TimeDebug.over("update over");
		return result;
	}

	public SvnTaskResult commitTask(final boolean force, final String userId, final String id, final String message)
	{
		TimeDebug.init();
		SvnTaskResult result;
		try
		{
			if (force)
				deleteIfExists(userId, id, "delete by server for force commit");
			result = commitTaskImpl(userId, id, message);
		}
		catch (SVNException e)
		{
			e.printStackTrace();
			if (e.getErrorMessage().getErrorCode() == SVNErrorCode.RA_DAV_REQUEST_FAILED)
			{
				result = new SvnTaskResult(ResultCode_Fail, "网络错误 " + e.getErrorMessage().getMessage());
			}
			else if (e.getErrorMessage().getErrorCode() == SVNErrorCode.WC_FOUND_CONFLICT)
			{
				LOG.v("版本冲突！");
				try
				{
					deleteLocalTaskSvnDir(userId, id);
					result = commitTaskImpl(userId, id, message);
				}
				catch (SVNException e1)
				{
					e1.printStackTrace();
					result = new SvnTaskResult(ResultCode_Fail, "二次处理错误 " + e.getErrorMessage().getMessage());
				}
			}
			else
			{
				result = new SvnTaskResult(ResultCode_Fail, "未处理错误 " + e.getErrorMessage().getMessage());
			}
		}
		TimeDebug.over("commit over");
		return result;
	}

	public static final int ResultCode_Fail = 0;
	public static final int ResultCode_Success = 1;
	public static final int ResultCode_NotNewestVision = 2;
	public static final int ResultCode_Relocate = 3;
	public static final int ResultCode_Locked = 4;
	public static final int ResultCode_Conflict = 5;

	public class SvnTaskResult
	{
		public int resultCode;
		public String errorMsg;

		public SvnTaskResult(int resultCode)
		{
			this.resultCode = resultCode;
		}

		public SvnTaskResult(int resultCode, String errorMsg)
		{
			this.resultCode = resultCode;
			this.errorMsg = errorMsg;
		}
	}

	public void checkSvnExternals(String path, File file, int indent) throws SVNException
	{
		SVNProperties fileProperties = new SVNProperties();
		repository.getFile(path, -1, fileProperties, null);
		String externals = fileProperties.getStringValue(SVNProperty.EXTERNALS);
		if (!StringUtils.isEmpty(externals))
		{
			File extFile = new File(file, "ext.txt");
			FsUtils.writeText(extFile, externals.replace("localhost", "desktop-ll4ds63"));
			CommandHelper.executeCommand(String.format("svn propset svn:externals \"%s\" -F \"%s\"", file, extFile));
			CommandHelper.executeCommand(String.format("svn commit \"%s\" -m \"xxx\"", file));
		}
	}

	public void findSvnExternals(String path, File file, int indent) throws SVNException
	{
		checkSvnExternals(path, file, indent);

		Collection entries = repository.getDir(path, -1, null, (Collection) null);
		Iterator iterator = entries.iterator();
		while (iterator.hasNext())
		{
			SVNDirEntry entry = (SVNDirEntry) iterator.next();
//			LOG.v(String.format("%s%s : %s", CommonUtils.getIndentStr(indent), entry.getKind(), entry.getName()));
			if (entry.getKind() == SVNNodeKind.DIR)
			{
				findSvnExternals(path + "/" + entry.getName(), new File(file, entry.getName()), indent + 1);
			}
		}
	}

	public void findSvnExternals(String path, File file)
	{
		try
		{
			findSvnExternals(path, file, 0);
		}
		catch (SVNException e)
		{
			e.printStackTrace();
		}
	}

//	public static void main(String[] args)
//	{
//		SVNManager svnManager = new SVNManager();
//		svnManager.initialize("https://desktop-ll4ds63:8443/svn/langyishi", "wangzhiting", "wangzhiting");
//		svnManager.findSvnExternals("/client/as3", new File("E:/wangzhiting/work/langyishi/client/as3"));
//		LOG.v("-------------------------------- over --------------------------------");
//	}

}
