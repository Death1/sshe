package wl.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import wl.dao.RoleDaoI;
import wl.dao.UserDaoI;
import wl.dao.UserRoleDaoI;
import wl.model.Tauth;
import wl.model.Trole;
import wl.model.Troletauth;
import wl.model.Tuser;
import wl.model.Tusertrole;
import wl.pageModel.Auth;
import wl.pageModel.DataGrid;
import wl.pageModel.User;
import wl.util.Encrypt;

@Component("userService")
public class UserServiceImpl implements UserServiceI
{

	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
	private RoleDaoI roleDao;
	private UserDaoI userDao;
	private UserRoleDaoI userRoleDao;

	@Override
	public void add(User user)
	{
		Tuser t = new Tuser();
		BeanUtils.copyProperties(user, t);
		t.setPwd(Encrypt.e(user.getPwd()));
		t.setCreateDateTime(new Date());
		userDao.save(t);

		savaUserRole(user, t);
	}

	public String addOrder(User user, String hql)
	{
		if (user.getSort() != null)
		{
			hql += " order by " + user.getSort() + " " + user.getOrder();
		}
		return hql;
	}

	public String addWhere(User user, String hql, Map<String, Object> params)
	{
		if (user.getName() != null && !user.getName().trim().equals(""))
		{
			hql += " and u.name like :name";
			params.put("name", "%%" + user.getName() + "%%");
		}
		if (user.getCreateDateTimeStart() != null)
		{
			hql += " and u.createDateTime >= :createDateTimeStart";
			params.put("createDateTimeStart", user.getCreateDateTimeStart());
		}
		if (user.getCreateDateTimeEnd() != null)
		{
			hql += " and u.createDateTime <= :createDateTimeEnd";
			params.put("createDateTimeEnd", user.getCreateDateTimeEnd());
		}
		if (user.getModifyDateTimeStart() != null)
		{
			hql += " and u.modifyDateTime >= :modifyDateTimeStart";
			params.put("modifyDateTimeStart", user.getModifyDateTimeStart());
		}
		if (user.getModifyDateTimeEnd() != null)
		{
			hql += " and u.modifyDateTime <= :modifyDateTimeEnd";
			params.put("modifyDateTimeEnd", user.getModifyDateTimeEnd());
		}
		return hql;
	}

	public void changeModel(List<Tuser> lt, List<User> lu)
	{
		if (lt != null && lt.size() > 0)
		{
			for (Tuser t : lt)
			{
				User u = new User();
				BeanUtils.copyProperties(t, u);

				// 根据用户ID查找出用户属于的角色
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("id", t.getId());
				List<Tusertrole> tuserTroles = userRoleDao.find("from Tusertrole t where t.tuser.id = :id", params);
				String roleIds = "";
				String roleNames = "";
				for (Tusertrole r : tuserTroles)
				{
					roleIds += "," + r.getTrole().getId();
					roleNames += "," + r.getTrole().getName();
				}
				if (roleIds.equals(""))
				{
					u.setRoleIds("");
					u.setRoleNames("");
				}
				else
				{
					u.setRoleIds(roleIds.substring(1));
					u.setRoleNames(roleNames.substring(1));
				}
				lu.add(u);
			}
		}
	}

	@Override
	public DataGrid dataGird(User user)
	{
		DataGrid dg = new DataGrid();

		String hql = "from Tuser u where 1=1";
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhere(user, hql, params);

		String totalHql = "select count(*)" + hql;
		hql = addOrder(user, hql);

		List<Tuser> lt = userDao.find(hql, params, user.getPage(), user.getRows());
		List<User> lu = new ArrayList<User>();
		changeModel(lt, lu);

		dg.setTotal((int) userDao.count(totalHql, params));
		dg.setRows(lu);
		return dg;
	}

	@Override
	public void edit(User user)
	{
		Tuser t = new Tuser();
		user.setModifyDateTime(new Date());
		BeanUtils.copyProperties(user, t);
		userDao.saveOrUpdate(t);
		savaUserRole(user, t);
	}

	public List<User> getUsersFromTusers(List<Tuser> tusers)
	{
		List<User> user = new ArrayList<User>();
		if (tusers != null && tusers.size() > 0)
		{
			for (Tuser tu : tusers)
			{
				User u = new User();
				BeanUtils.copyProperties(tu, u);
				user.add(u);
			}
		}
		return user;
	}

	@Override
	public Tuser login(User user)
	{
		// return userDao.get("FROM User u WHERE u.name='" + user.getName() + "' and u.pwd='" +
		// Encrypt.e(user.getPwd()) + "'");
		// return userDao.get("from User u where u.name = ? and u.pwd = ?", new Object[] {
		// user.getName(), Encrypt.e(user.getPwd()) });
		// Tuser tuser = new Tuser();
		// BeanUtils.copyProperties(user, tuser);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", user.getName());
		params.put("pwd", Encrypt.e(user.getPwd()));
		return userDao.get("from Tuser u where u.name = :name and u.pwd = :pwd", params);
	}

	@Override
	public List<User> loginCombobox(User user)
	{
		String hql = "from Tuser u where 1=1 and u.name like '%%" + user.getName() + "%%'";
		return getUsersFromTusers(userDao.find(hql, 1, 10));
	}

	@Override
	public void modifyUserRole(User user)
	{
		if (user.getIds() != null)
		{
			// 传过来是用户ID
			for (String id : user.getIds().split(","))
			{
				// 存入修改时间
				Tuser u = userDao.get(Tuser.class, Integer.parseInt(id));
				u.setModifyDateTime(new Date());
				userDao.saveOrUpdate(u);
				// 先删除用户拥有的角色
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("id", Integer.parseInt(id));
				userRoleDao.executeHql("delete Tusertrole t where t.tuser.id = :id ", params);

				// 存入用户的新角色
				if (user.getRoleIds() != null)
				{
					for (String roleId : user.getRoleIds().split(","))
					{
						userRoleDao.save(new Tusertrole(UUID.randomUUID().toString(), u, roleDao.get(Trole.class, roleId.trim())));
					}
				}

			}
		}
	}

	@Override
	public void remove(String ids)
	{
		// for (String id : ids.split(","))
		// {
		// Tuser u = userDao.get(Integer.parseInt(id), Tuser.class);
		// if (u != null)
		// {
		// userDao.delete(u);
		// }
		// }
		String[] nids = ids.split(",");
		String hql = "delete Tuser t where t.id in (";
		for (int i = 0; i < nids.length; i++)
		{
			if (i > 0)
			{
				hql += ",";
			}
			hql += "'" + nids[i] + "'";
		}
		hql += ")";
		userDao.executeHql(hql);
	}

	/**
	 * 保存用户和角色的关系
	 * @param user
	 * @param t
	 */
	private void savaUserRole(User user, Tuser t)
	{
		if (user.getRoleIds() != null && !user.getRoleIds().equals(""))
		{
			// 先删除用户 属于的角色
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", user.getId());
			userRoleDao.executeHql("delete Tusertrole t where t.tuser.id = :id ", params);
			for (String roleId : user.getRoleIds().split(","))
			{
				Tusertrole tuserrole = new Tusertrole();
				tuserrole.setId(UUID.randomUUID().toString());
				tuserrole.setTuser(t);
				tuserrole.setTrole(roleDao.get(Trole.class, roleId.trim()));
				userRoleDao.save(tuserrole);
			}
		}
	}

	// 如果要在这里附加别的属性 就需要 用到spring的工具类 反之则不需要
	@Override
	public User save(User user)
	{
		Tuser tuser = new Tuser();
		user.setPwd(Encrypt.e(user.getPwd()));
		user.setCreateDateTime(new Date());

		BeanUtils.copyProperties(user, tuser);
		userDao.save(tuser);
		return user;
	}

	@Resource
	public void setRoleDao(RoleDaoI roleDao)
	{
		this.roleDao = roleDao;
	}

	@Resource
	public void setUserDao(UserDaoI userDao)
	{
		this.userDao = userDao;
	}

	@Resource
	public void setUserRoleDao(UserRoleDaoI userRoleDao)
	{
		this.userRoleDao = userRoleDao;
	}

	@Override
	public void test()
	{
		logger.info("a");
	}

	@Override
	public void modifyUserPwd(User user)
	{
		if (user.getIds() != null)
		{
			for (String id : user.getIds().split(","))
			{
				Tuser t = userDao.get(Tuser.class, id);
				t.setPwd(Encrypt.e(user.getPwd()));
				t.setModifyDateTime(new Date());
			}
		}
	}

	@Override
	public List<Auth> getAuths(Tuser u)
	{
		List<Auth> auths = new ArrayList<Auth>();
		if (u != null)
		{
			Set<Tusertrole> tusertroles = u.getTusertroles();
			if (tusertroles != null && tusertroles.size() > 0)
			{
				for (Tusertrole t : tusertroles)
				{
					Trole trole = t.getTrole();
					if (trole != null)
					{
						Set<Troletauth> troletauths = trole.getTroletauths();
						if (troletauths != null && troletauths.size() > 0)
						{
							for (Troletauth tr : troletauths)
							{
								Tauth tauth = tr.getTauth();
								if (tauth != null)
								{
									Auth a = new Auth();
									a.setName(tauth.getName());
									a.setUrl(tauth.getUrl());
									auths.add(a);
								}
							}
						}
					}
				}
			}
		}
		return auths;
	}
}
