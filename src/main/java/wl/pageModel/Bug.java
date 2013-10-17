package wl.pageModel;

import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;

public class Bug
{

	private String ids;
	private int page;// 当前页
	private int rows;// 每页显示记录数
	private String sort;// 排序字段名
	private String order;// 按什么排序(asc,desc)
	private Date ccreatedatetimeStart;
	private Date ccreatedatetimeEnd;

	private String id;
	private Date createDateTime;
	private String bugDescribe;
	private String name;

	public String getIds()
	{
		return ids;
	}

	public void setIds(String ids)
	{
		this.ids = ids;
	}

	public int getPage()
	{
		return page;
	}

	public void setPage(int page)
	{
		this.page = page;
	}

	public int getRows()
	{
		return rows;
	}

	public void setRows(int rows)
	{
		this.rows = rows;
	}

	public String getSort()
	{
		return sort;
	}

	public void setSort(String sort)
	{
		this.sort = sort;
	}

	public String getOrder()
	{
		return order;
	}

	public void setOrder(String order)
	{
		this.order = order;
	}

	public Date getCcreatedatetimeStart()
	{
		return ccreatedatetimeStart;
	}

	public void setCcreatedatetimeStart(Date ccreatedatetimeStart)
	{
		this.ccreatedatetimeStart = ccreatedatetimeStart;
	}

	public Date getCcreatedatetimeEnd()
	{
		return ccreatedatetimeEnd;
	}

	public void setCcreatedatetimeEnd(Date ccreatedatetimeEnd)
	{
		this.ccreatedatetimeEnd = ccreatedatetimeEnd;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public Date getCreateDateTime()
	{
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime)
	{
		this.createDateTime = createDateTime;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getBugDescribe()
	{
		return bugDescribe;
	}

	public void setBugDescribe(String bugDescribe)
	{
		this.bugDescribe = bugDescribe;
	}

}
