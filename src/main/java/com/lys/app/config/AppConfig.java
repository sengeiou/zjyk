package com.lys.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Component
public class AppConfig
{
	public static final String ROLE_SupterMaster = "ROLE_SupterMaster";
	public static final String ROLE_Master = "ROLE_Master";
	public static final String ROLE_Teacher = "ROLE_Teacher";
	public static final String ROLE_Student = "ROLE_Student";

	public static final String TransEvt_FriendChange = "FriendChange";

	public static final String defaultHead = "http://file.k12-eco.com/head/default_head.jpg";

	public static final String defaultMst1 = "mst1";
	public static final String defaultMst2 = "mst2";

	public static String getUsername(HttpSession session)
	{
		if (session != null)
		{
			SecurityContextImpl sci = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
			if (sci != null)
			{
				User user = (User) sci.getAuthentication().getPrincipal();
				return user.getUsername();
			}
		}
		return null;
	}

	public static List<String> getAuthorities(HttpSession session)
	{
		List<String> authorities = new ArrayList<>();
		if (session != null)
		{
			SecurityContextImpl sci = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
			if (sci != null)
			{
				for (GrantedAuthority authority : sci.getAuthentication().getAuthorities())
				{
					authorities.add(authority.getAuthority());
				}
			}
		}
		return authorities;
	}

	@Value("${url.root}")
	private String urlRoot = null;

	public String checkUrl(String url)
	{
		if (StringUtils.isEmpty(url))
			return url;
		else
		{
			if (url.startsWith("http://") || url.startsWith("https://"))
				return url;
			else
				return urlRoot + url;
		}
	}


}