package com.lys.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandExecution
{
	public static class CommandResult
	{
		public int result = -1;
		public String errorMsg;
		public String successMsg;
	}

	public static CommandResult execCommand(String command)
	{
		LOG.v("exe : " + command);
		CommandResult commandResult = new CommandResult();
		Process process = null;
		BufferedReader successResult = null;
		BufferedReader errorResult = null;
		StringBuilder successMsg = null;
		StringBuilder errorMsg = null;
		try
		{
			process = Runtime.getRuntime().exec(command);
			commandResult.result = process.waitFor();
			successMsg = new StringBuilder();
			errorMsg = new StringBuilder();
			successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
			errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String s;
			while ((s = successResult.readLine()) != null)
				successMsg.append(s);
			while ((s = errorResult.readLine()) != null)
				errorMsg.append(s);
			commandResult.successMsg = successMsg.toString();
			commandResult.errorMsg = errorMsg.toString();
			LOG.v("result : " + commandResult.result);
			LOG.v("successMsg : " + commandResult.successMsg);
			LOG.v("errorMsg : " + commandResult.errorMsg);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (successResult != null)
					successResult.close();
				if (errorResult != null)
					errorResult.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			if (process != null)
				process.destroy();
		}
		LOG.v("exe over");
		return commandResult;
	}

}