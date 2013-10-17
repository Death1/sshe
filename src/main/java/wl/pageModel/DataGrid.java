package wl.pageModel;

import java.util.ArrayList;
import java.util.List;

import wl.model.Tuser;

public class DataGrid
{

	private int total = 0; // 总记录数
	private List rows = new ArrayList();// 每行记录

	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}

	public List getRows()
	{
		return rows;
	}

	public void setRows(List rows)
	{
		this.rows = rows;
	}

}
