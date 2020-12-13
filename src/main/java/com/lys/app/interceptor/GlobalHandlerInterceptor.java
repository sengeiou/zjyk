package com.lys.app.interceptor;

import com.lys.utils.LOG;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

public class GlobalHandlerInterceptor implements HandlerInterceptor
{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		if (false)
		{
			LOG.v("处理器前方法:" + request);

			LOG.v("getContextPath : " + request.getContextPath());
			LOG.v("getRealPath : " + request.getServletContext().getRealPath(""));
			LOG.v("getMethod : " + request.getMethod());
			LOG.v("getRequestURI : " + request.getRequestURI());
			LOG.v("getProtocol : " + request.getProtocol());
			LOG.v("getPathInfo : " + request.getPathInfo());
			LOG.v("getQueryString : " + request.getQueryString());
			LOG.v("getRemoteHost : " + request.getRemoteHost());
			LOG.v("getRemoteAddr : " + request.getRemoteAddr());
			LOG.v("getRemotePort : " + request.getRemotePort());

			LOG.v("-------------- getHeaders --------------");
			Enumeration<String> e = request.getHeaderNames();
			while (e.hasMoreElements())
			{
				String name = e.nextElement();
				String value = request.getHeader(name);
				LOG.v(name + " = " + value);
			}

			LOG.v("-------------- getSession --------------");
			HttpSession session = request.getSession();
			e = session.getAttributeNames();
			while (e.hasMoreElements())
			{
				String name = e.nextElement();
				Object value = session.getAttribute(name);
				LOG.v(name + " = " + value);
			}

			SecurityContextImpl sci = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
			if (sci != null)
			{
				for (GrantedAuthority authority : sci.getAuthentication().getAuthorities())
				{
					LOG.v("authority" + " = " + authority.getAuthority());
				}
				LOG.v("getPrincipal" + " = " + sci.getAuthentication().getPrincipal());
				User user = (User) sci.getAuthentication().getPrincipal();
				LOG.v("getUsername" + " = " + user.getUsername());
				LOG.v("getPassword" + " = " + user.getPassword());
				for (GrantedAuthority authority : user.getAuthorities())
				{
					LOG.v("authority" + " = " + authority.getAuthority());
				}
			}

			LOG.v("-------------- getParameters --------------");
			e = request.getParameterNames();
			while (e.hasMoreElements())
			{
				String name = e.nextElement();
				String value = request.getParameter(name);
				LOG.v(name + " = " + value);
			}

			LOG.v("-------------- getCookies --------------");
			Cookie[] cookies = request.getCookies();
			if (cookies != null)
			{
				for (int i = 0; i < cookies.length; i++)
				{
					Cookie c = cookies[i];
					String name = c.getName();
					String value = c.getValue();
					LOG.v(name + " = " + value);
				}
			}

			LOG.v("-------------- addCookie --------------");
			String name = request.getParameter("cookieName");
			if (name != null && name.length() > 0)
			{
				String value = request.getParameter("cookieValue");
				Cookie c = new Cookie(name, value);
				LOG.v(name + " = " + value);
				response.addCookie(c);
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
	{
//		LOG.v("处理器后方法");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
	{
//		LOG.v("处理器完成方法");
	}
}