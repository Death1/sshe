package wl.pageModel;

import java.util.List;

public class SessionInfo
{

	private String userId; // 用户ID
	private String loginName;// 用户登录名称
	private String loginPassword;// 登录密码
	private String ip;// IP地址
	private List<Auth> auths;// 用户拥有的权限

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getLoginName()
	{
		return loginName;
	}

	public void setLoginName(String loginName)
	{
		this.loginName = loginName;
	}

	public String getLoginPassword()
	{
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword)
	{
		this.loginPassword = loginPassword;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public List<Auth> getAuths()
	{
		return auths;
	}

	public void setAuths(List<Auth> auths)
	{
		this.auths = auths;
	}

}
