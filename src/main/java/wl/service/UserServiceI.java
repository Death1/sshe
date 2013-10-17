package wl.service;

import java.util.List;

import wl.model.Tuser;
import wl.pageModel.Auth;
import wl.pageModel.DataGrid;
import wl.pageModel.User;

public interface UserServiceI
{

	public void test();

	public User save(User user);

	public void add(User user);

	public Tuser login(User user);

	public DataGrid dataGird(User user);

	public void remove(String ids);

	public void edit(User user);

	public List<User> loginCombobox(User user);

	public void modifyUserRole(User user);

	public void modifyUserPwd(User user);

	public List<Auth> getAuths(Tuser u);
}
