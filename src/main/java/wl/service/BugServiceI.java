package wl.service;

import wl.model.Tbug;
import wl.pageModel.Bug;
import wl.pageModel.DataGrid;

public interface BugServiceI
{

	public DataGrid datagrid(Bug bug);

	public void saveOrUpdate(Bug bug);

	public Tbug get(Bug bug);

	public void delete(String ids);
}
