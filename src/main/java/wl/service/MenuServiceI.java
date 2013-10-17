package wl.service;

import java.util.List;

import wl.pageModel.Menu;

public interface MenuServiceI
{

	public List<Menu> getTree(String id);

	public List<Menu> treegrid(String id);

	public List<Menu> menuTreeRecursive();

	public void edit(Menu menu);

	public void delete(Menu menu);

	public void add(Menu menu);
}
