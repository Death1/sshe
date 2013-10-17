package wl.service;

import java.util.List;

import wl.pageModel.Auth;
import wl.pageModel.TreeNode;

public interface AuthServiceI
{

	public List<Auth> treegrid(Auth auth);

	public void delete(Auth auth);

	public void addOrEdit(Auth auth);

	public List<TreeNode> tree(Auth auth);
}
