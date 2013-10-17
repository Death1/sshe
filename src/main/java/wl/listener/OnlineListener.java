package wl.listener;

import java.util.Date;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import wl.model.Tonline;
import wl.pageModel.SessionInfo;
import wl.service.OnlineServiceI;
import wl.util.ResourceUtil;

public class OnlineListener implements ServletContextListener, ServletContextAttributeListener, HttpSessionListener, HttpSessionAttributeListener, HttpSessionActivationListener, HttpSessionBindingListener, ServletRequestListener, ServletRequestAttributeListener
{

	private static final Logger logger = Logger.getLogger(OnlineListener.class);

	private static ApplicationContext ctx = null;

	/**
	 * 向session里增加属性市调用(用户登录成功后会调用)
	 */
	@Override
	public void attributeAdded(HttpSessionBindingEvent evt)
	{

		String name = evt.getName();
		logger.debug("向session存入属性：" + name);
		HttpSession session = evt.getSession();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ResourceUtil.getSessionInfoName());
		if (sessionInfo != null)
		{
			OnlineServiceI onlineService = (OnlineServiceI) ctx.getBean("onlineService");
			Tonline online = new Tonline();
			online.setIp(sessionInfo.getIp());
			online.setName(sessionInfo.getLoginName());
			online.setDatetime(new Date());
			onlineService.updateOnline(online);
		}
	}

	@Override
	public void attributeAdded(ServletContextAttributeEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void attributeAdded(ServletRequestAttributeEvent evt)
	{

	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void attributeRemoved(ServletContextAttributeEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void attributeRemoved(ServletRequestAttributeEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void attributeReplaced(ServletContextAttributeEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void attributeReplaced(ServletRequestAttributeEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0)
	{
		logger.debug("服务器关闭");
	}

	@Override
	public void contextInitialized(ServletContextEvent evt)
	{
		logger.debug("服务器启动");
		ctx = WebApplicationContextUtils.getWebApplicationContext(evt.getServletContext());
	}

	@Override
	public void requestDestroyed(ServletRequestEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void requestInitialized(ServletRequestEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionCreated(HttpSessionEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	/**
	 * session销毁
	 */

	@Override
	public void sessionDestroyed(HttpSessionEvent evt)
	{
		HttpSession session = evt.getSession();
		if (session != null)
		{
			logger.debug("session销毁:" + session.getId());
			SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ResourceUtil.getSessionInfoName());
			if (sessionInfo != null)
			{
				OnlineServiceI onlineService = (OnlineServiceI) ctx.getBean("onlineService");
				System.out.println("session销毁");
				onlineService.delete(sessionInfo.getLoginName(), sessionInfo.getIp());
			}
		}
	}

	@Override
	public void sessionDidActivate(HttpSessionEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionWillPassivate(HttpSessionEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void valueBound(HttpSessionBindingEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent arg0)
	{
		// TODO Auto-generated method stub

	}

}
