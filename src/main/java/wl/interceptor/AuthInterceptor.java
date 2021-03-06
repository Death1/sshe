package wl.interceptor;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;


import wl.pageModel.Auth;
import wl.pageModel.SessionInfo;
import wl.util.RequestUtil;
import wl.util.ResourceUtil;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class AuthInterceptor extends MethodFilterInterceptor
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(AuthInterceptor.class);

	@Override
	protected String doIntercept(ActionInvocation actionInvocation) throws Exception
	{
		SessionInfo sessionInfo = (SessionInfo) ServletActionContext.getRequest().getSession().getAttribute(ResourceUtil.getSessionInfoName());
		// 如果是admin用户则不需要验证权限
		if (sessionInfo.getLoginName().equals("admin"))
		{
			return actionInvocation.invoke();
		}
		String requestPath = RequestUtil.getRequestPath(ServletActionContext.getRequest());
		logger.debug(actionInvocation.getAction().getClass());
		logger.debug(requestPath);
		List<Auth> auths = sessionInfo.getAuths();
		if (auths != null && auths.size() > 0)
		{
			boolean b = false;
			for (Auth a : auths)
			{
				if (requestPath.equals(a.getUrl()))
				{
					b = true;
					break;
				}
			}
			if (b)
			{
				return actionInvocation.invoke();
			}
		}
		ServletActionContext.getRequest().setAttribute("msg", "您没有访问此功能的权限！权限路径为[" + requestPath + "]请联系管理员给你赋予相应权限。");
		return "noAuth";
	}
}
