package wl.pageModel;

import wl.model.Tauth;

public class Auth
{

	/**
	 * 数据库表中的字段
	 */
	private String id;
	private String authDescribe;
	private String name;
	private String seq;
	private String url;
	/**
	 * 需要在前台显示的字段
	 */
	private String pid;
	private String pname;
	private String state = "open";// 是否展开(open,closed)
	private String text;
	
	public String getText()
	{
		return text;
	}

	
	public void setText(String text)
	{
		this.text = text;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getAuthDescribe()
	{
		return authDescribe;
	}

	public void setAuthDescribe(String authDescribe)
	{
		this.authDescribe = authDescribe;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getSeq()
	{
		return seq;
	}

	public void setSeq(String seq)
	{
		this.seq = seq;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getPid()
	{
		return pid;
	}

	public void setPid(String pid)
	{
		this.pid = pid;
	}

	public String getPname()
	{
		return pname;
	}

	public void setPname(String pname)
	{
		this.pname = pname;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}
}
