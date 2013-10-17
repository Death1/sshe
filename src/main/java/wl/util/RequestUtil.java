package wl.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil
{

	public static String getRequestPath(HttpServletRequest request)
	{
		String requestPath = request.getRequestURI();
		requestPath = requestPath.substring(request.getContextPath().length()); // 去掉项目路径
		return requestPath;
	}

}
