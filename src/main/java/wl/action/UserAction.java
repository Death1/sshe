package wl.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import wl.model.Tuser;
import wl.pageModel.Json;
import wl.pageModel.SessionInfo;
import wl.pageModel.User;
import wl.service.UserServiceI;
import wl.util.IpUtil;
import wl.util.ResourceUtil;

import com.opensymphony.xwork2.ModelDriven;

@Namespace("/")
@Action(value = "userAction", results =
{ @Result(name = "yhgl", location = "/admin/yhgl.jsp") })
public class UserAction extends BaseAction implements ModelDriven<User>
{

	private UserServiceI userService;
	private User user = new User();

	public void add() throws Exception
	{
		try
		{
			userService.add(user);
			super.writeJson(new Json(true, "添加成功"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			super.writeJson(new Json(false, "添加失败"));
		}
	}

	public void addAction() throws Exception
	{
		try
		{
			User u = userService.save(user);
			super.writeJson(new Json(true, "添加成功", u));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			super.writeJson(new Json(false, "添加失败"));
		}
	}

	public void logout()
	{
		HttpSession session = ServletActionContext.getRequest().getSession();
		if (session != null)
		{
			session.invalidate();
		}
		writeJson(new Json(true, ""));
	}

	public void dataGird() throws Exception
	{
		super.writeJson(userService.dataGird(user));
	}

	public void edit() throws Exception
	{
		try
		{
			userService.edit(user);
			super.writeJson(new Json(true, "编辑成功"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			super.writeJson(new Json(false, "编辑失败"));
		}
	}

	public void editAction() throws Exception
	{
		try
		{
			userService.edit(user);
			super.writeJson(new Json(true, "编辑成功"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			super.writeJson(new Json(false, "编辑失败"));
		}
	}

	@Override
	public User getModel()
	{
		return user;
	}

	public void loginAction() throws Exception
	{
		Tuser u = userService.login(user);
		if (u != null)
		{
			SessionInfo sessionInfo = saveSessionInfo(u);
			super.writeJson(new Json(true, "登录成功", sessionInfo));
		}
		else
		{
			super.writeJson(new Json(false, "登录失败,用户名或密码错误"));

		}
	}

	private SessionInfo saveSessionInfo(Tuser u)
	{
		SessionInfo sessionInfo = new SessionInfo();
		sessionInfo.setUserId(u.getId());
		sessionInfo.setLoginName(u.getName());
		sessionInfo.setLoginPassword(u.getPwd());
		sessionInfo.setIp(IpUtil.getIpAddr(ServletActionContext.getRequest()));
		ServletActionContext.getRequest().getSession().setAttribute(ResourceUtil.getSessionInfoName(), sessionInfo);
		sessionInfo.setAuths(userService.getAuths(u));
		return sessionInfo;
	}

	public void loginCombobox() throws Exception
	{
		if (user.getQ() != null && !user.getQ().trim().equals(""))
		{
			user.setName(user.getQ());
		}
		super.writeJson(userService.loginCombobox(user));
	}

	/**
	 * 表格方式登录
	 * @throws Exception
	 */
	public void loginDatagrid() throws Exception
	{
		if (user.getQ() != null && !user.getQ().trim().equals(""))
		{
			user.setName(user.getQ());
		}
		super.writeJson(userService.dataGird(user));
	}

	public void modifyUserRole() throws Exception
	{
		try
		{
			userService.modifyUserRole(user);
			super.writeJson(new Json(true, "编辑成功"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			super.writeJson(new Json(false, "编辑失败"));
		}
	}

	public void modifyUserPwd() throws Exception
	{
		try
		{
			userService.modifyUserPwd(user);
			super.writeJson(new Json(true, "密码修改成功"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			super.writeJson(new Json(false, "密码修改成功"));
		}
	}

	public void regAction() throws Exception
	{
		try
		{

			userService.save(user);
			super.writeJson(new Json(true, "注册成功"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			super.writeJson(new Json(false, "注册失败"));
		}
	}

	public void removeAction() throws Exception
	{
		try
		{
			userService.remove(user.getIds());
			super.writeJson(new Json(true, "删除成功"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			super.writeJson(new Json(false, "删除失败"));
		}
	}

	/**
	 * public void test() { // ApplicationContext ctx = // WebApplicationContextUtils.getWebApplicationContext (ServletActionContext.getServletContext()); // UserServiceI userService = (UserServiceI) ctx.getBean("userService"); userService.test(); System.out.println("Hello Struts2!"); }
	 * 
	 * public void addUser() { userService.save(new User(6, "小花", 66)); }
	 * 
	 * @throws Exception
	 * 
	 **/
	@Resource
	public void setUserService(UserServiceI userService)
	{
		this.userService = userService;
	}

	/**
	 * 跳转到用户管理页面
	 * 
	 * @return
	 */
	public String yhgl()
	{
		return "yhgl";
	}
}
