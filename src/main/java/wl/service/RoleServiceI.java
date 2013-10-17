package wl.service;

import java.util.List;

import wl.pageModel.DataGrid;
import wl.pageModel.Role;

public interface RoleServiceI
{

	public DataGrid datagrid(Role role);

	public void addOrEdit(Role role);

	public void delete(String ids);

	public List<Role> combobox();

}
