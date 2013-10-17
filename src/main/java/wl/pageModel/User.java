package wl.pageModel;

import java.util.Date;

public class User
{

	private String id;
	private String name;
	private String pwd;
	
	private Date createDateTime;
	private Date modifyDateTime;
	private Date createDateTimeStart;
	private Date modifyDateTimeStart;
	private Date createDateTimeEnd;
	private Date modifyDateTimeEnd;

	private String oldPwd;
	private String roleIds;
	private String roleNames;

	private int page;
	private int rows;
	private String sort;
	private String order;
	private String ids;
	private String q;// 补全登录时用到的属性

	public User()
	{
	}

	public User(String name, String pwd)
	{
		super();
		this.name = name;
		this.pwd = pwd;
	}

	public User(String name, String pwd, Date createDateTime)
	{
		super();
		this.name = name;
		this.pwd = pwd;
		this.createDateTime = createDateTime;
	}

	public User(String id, String name, String pwd)
	{
		super();
		this.id = id;
		this.name = name;
		this.pwd = pwd;
	}

	public Date getCreateDateTime()
	{
		return createDateTime;
	}

	public Date getCreateDateTimeEnd()
	{
		return createDateTimeEnd;
	}

	public Date getCreateDateTimeStart()
	{
		return createDateTimeStart;
	}

	public String getId()
	{
		return id;
	}

	public String getIds()
	{
		return ids;
	}

	public Date getModifyDateTime()
	{
		return modifyDateTime;
	}

	public Date getModifyDateTimeEnd()
	{
		return modifyDateTimeEnd;
	}

	public Date getModifyDateTimeStart()
	{
		return modifyDateTimeStart;
	}

	public String getName()
	{
		return name;
	}

	public String getOldPwd()
	{
		return oldPwd;
	}

	public String getOrder()
	{
		return order;
	}

	public int getPage()
	{
		return page;
	}

	public String getPwd()
	{
		return pwd;
	}

	public String getQ()
	{
		return q;
	}

	public String getRoleIds()
	{
		return roleIds;
	}

	public String getRoleNames()
	{
		return roleNames;
	}

	public int getRows()
	{
		return rows;
	}

	public String getSort()
	{
		return sort;
	}

	public void setCreateDateTime(Date createDateTime)
	{
		this.createDateTime = createDateTime;
	}

	public void setCreateDateTimeEnd(Date createDateTimeEnd)
	{
		this.createDateTimeEnd = createDateTimeEnd;
	}

	public void setCreateDateTimeStart(Date createDateTimeStart)
	{
		this.createDateTimeStart = createDateTimeStart;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setIds(String ids)
	{
		this.ids = ids;
	}

	public void setModifyDateTime(Date modifyDateTime)
	{
		this.modifyDateTime = modifyDateTime;
	}

	public void setModifyDateTimeEnd(Date modifyDateTimeEnd)
	{
		this.modifyDateTimeEnd = modifyDateTimeEnd;
	}

	public void setModifyDateTimeStart(Date modifyDateTimeStart)
	{
		this.modifyDateTimeStart = modifyDateTimeStart;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setOldPwd(String oldPwd)
	{
		this.oldPwd = oldPwd;
	}

	public void setOrder(String order)
	{
		this.order = order;
	}

	public void setPage(int page)
	{
		this.page = page;
	}

	public void setPwd(String pwd)
	{
		this.pwd = pwd;
	}

	public void setQ(String q)
	{
		this.q = q;
	}

	public void setRoleIds(String roleIds)
	{
		this.roleIds = roleIds;
	}

	public void setRoleNames(String roleNames)
	{
		this.roleNames = roleNames;
	}

	public void setRows(int rows)
	{
		this.rows = rows;
	}

	public void setSort(String sort)
	{
		this.sort = sort;
	}

}
