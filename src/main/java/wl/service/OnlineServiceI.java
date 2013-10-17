package wl.service;

import wl.model.Tonline;
import wl.pageModel.DataGrid;
import wl.pageModel.Online;

public interface OnlineServiceI
{

	public DataGrid datagrid(Online online);

	public void updateOnline(Tonline online);

	public void delete(String loginName, String ip);

}
