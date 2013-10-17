package wl.pageModel;

public class Online
{

	private String id;
	private String name;
	private String ip;
	private String datetime;

	// datagrid 组件用到的属性
	private int page;
	private int rows;
	private String sort;
	private String order;

	public String getDatetime()
	{
		return datetime;
	}

	public String getId()
	{
		return id;
	}

	public String getIp()
	{
		return ip;
	}

	public String getName()
	{
		return name;
	}

	public String getOrder()
	{
		return order;
	}

	public int getPage()
	{
		return page;
	}

	public int getRows()
	{
		return rows;
	}

	public String getSort()
	{
		return sort;
	}

	public void setDatetime(String datetime)
	{
		this.datetime = datetime;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setOrder(String order)
	{
		this.order = order;
	}

	public void setPage(int page)
	{
		this.page = page;
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
