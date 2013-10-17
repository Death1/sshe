package wl.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import wl.comparator.AuthComparator;
import wl.dao.AuthDaoI;
import wl.model.Tauth;
import wl.pageModel.Auth;
import wl.pageModel.TreeNode;

@Component("authService")
public class AuthServiceImpl implements AuthServiceI
{

	private AuthDaoI authDao;

	@Resource
	public void setAuthDao(AuthDaoI authDao)
	{
		this.authDao = authDao;
	}

	public List<TreeNode> tree(Auth auth)
	{
		String hql = "";
		Map<String, Object> params = new HashMap<String, Object>();
		if (auth != null && auth.getId() != null)
		{
			hql = "from Tauth t where t.tauth.id = :id order by t.seq";
			params.put("id", auth.getId());
		}
		else
		{
			hql = "from Tauth t where t.tauth.id is null order by t.seq";
		}
		List<TreeNode> tree = new ArrayList<TreeNode>();
		List<Tauth> lt = authDao.find(hql, params);

		if (lt != null && lt.size() > 0)
		{
			for (Tauth t : lt)
			{
				tree.add(tree(t));
			}
		}
		return tree;

	}

	private TreeNode tree(Tauth t)
	{
		Map<String, Object> attributes = new HashMap<String, Object>();
		TreeNode treeNode = new TreeNode();
		treeNode.setId(t.getId());
		treeNode.setText(t.getName());
		treeNode.setAttributes(attributes);
		if (t.getTauths() != null && !t.getTauths().isEmpty())// 查询递归子节点
		{
			List<Tauth> l = new ArrayList<Tauth>(t.getTauths());
			Collections.sort(l, new AuthComparator()); // 排序
			List<TreeNode> children = new ArrayList<TreeNode>();
			for (Tauth r : l)
			{
				TreeNode tn = tree(r);
				children.add(tn);
			}
			treeNode.setChildren(children);
		}
		return treeNode;
	}

	@Override
	public List<Auth> treegrid(Auth auth)
	{
		String hql = "";
		Map<String, Object> params = new HashMap<String, Object>();
		if (auth != null && auth.getId() != null)
		{
			hql = "from Tauth t where t.tauth.id = :id order by t.seq";
			params.put("id", auth.getId());
		}
		else
		{
			hql = "from Tauth t where t.tauth.id is null order by t.seq";
		}
		List<Tauth> lt = authDao.find(hql, params);
		List<Auth> la = new ArrayList<Auth>();
		if (lt != null && lt.size() > 0)
		{
			for (Tauth t : lt)
			{
				Auth a = new Auth();
				BeanUtils.copyProperties(t, a);
				a.setText(t.getName());
				if (t.getTauth() != null)
				{
					a.setPid(t.getTauth().getId());
					a.setPname(t.getTauth().getName());
				}
				if (t.getTauths() != null && !t.getTauths().isEmpty())
				{
					a.setState("closed");
				}
				else
				{
					a.setState("open");
				}
				la.add(a);
			}
		}
		return la;

	}

	@Override
	public void delete(Auth auth)
	{
		del(auth.getId());
	}

	private void del(String id)
	{
		Tauth t = authDao.get(Tauth.class, id);
		if (t != null)
		{
			Set<Tauth> st = t.getTauths();
			// 说明有子菜单
			if (st != null && !st.isEmpty())
			{
				for (Tauth a : st)
				{
					del(a.getId());
				}
			}
			authDao.delete(t);
		}
	}

	@Override
	public void addOrEdit(Auth auth)
	{
		Tauth t = new Tauth();
		BeanUtils.copyProperties(auth, t);
		if (auth.getPid() != null && !auth.getPid().equals(auth.getId()))
		{
			t.setTauth(authDao.get(Tauth.class, auth.getPid()));
		}
		authDao.saveOrUpdate(t);
	}
}
