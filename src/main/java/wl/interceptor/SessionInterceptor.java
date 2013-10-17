package wl.interceptor;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import wl.pageModel.SessionInfo;
import wl.util.RequestUtil;
import wl.util.ResourceUtil;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class SessionInterceptor extends MethodFilterInterceptor
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected String doIntercept(ActionInvocation actionInvocation) throws Exception
	{
		SessionInfo sessionInfo = (SessionInfo) ServletActionContext.getRequest().getSession().getAttribute(ResourceUtil.getSessionInfoName());
		if (sessionInfo == null)
		{
			ServletActionContext.getRequest().setAttribute("msg", "您还没有登录或登录已超时，请重新登录，然后再刷新本功能！");
			System.out.println("Hi,Session");
			return "noSession";
		}
		return actionInvocation.invoke();
	}
}
