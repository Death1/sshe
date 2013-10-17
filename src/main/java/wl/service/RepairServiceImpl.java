package wl.service;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import wl.dao.AuthDaoI;
import wl.dao.BugDaoI;
import wl.dao.MenuDaoI;
import wl.dao.RoleAuthDaoI;
import wl.dao.RoleDaoI;
import wl.dao.UserDaoI;
import wl.dao.UserRoleDaoI;
import wl.model.Tauth;
import wl.model.Tmenu;
import wl.model.Trole;
import wl.model.Troletauth;
import wl.model.Tuser;
import wl.model.Tusertrole;
import wl.util.Encrypt;

@Component("repairService")
public class RepairServiceImpl implements RepairServiceI {

	private MenuDaoI menuDao;
	private UserDaoI userDao;
	private AuthDaoI authDao;
	private RoleAuthDaoI roleAuthDao;
	private RoleDaoI roleDao;
	private BugDaoI bugDao;
	private UserRoleDaoI userRoleDao;

	synchronized public void deleteAndRepair() {
		bugDao.executeHql("delete Tbug");
		// onlineDao.executeHql("delete Tonline");
		menuDao.executeHql("update Tmenu t set t.tmenu = null");
		menuDao.executeHql("delete Tmenu");
		roleAuthDao.executeHql("delete Troletauth");
		userRoleDao.executeHql("delete Tusertrole");
		authDao.executeHql("update Tauth t set t.tauth = null");
		authDao.executeHql("delete Tauth");
		roleDao.executeHql("delete Trole");
		userDao.executeHql("delete Tuser");
		repair();
	}

	@Override
	synchronized public void repair() {
		repairMenu(); // 修复菜单
		repairAutn();// 修复权限
		repairRole();// 修复角色
		repairUser();// 修复用户
		repairRoleAuth();// 修复角色权限关系
		repairUserRole();// 修复用户和角色的关系

	}

	private void repairUserRole() {
		userRoleDao.executeHql("delete Tusertrole t where t.tuser.id = '0'");
		Tusertrole tusertrole = new Tusertrole();
		tusertrole.setId(UUID.randomUUID().toString());
		tusertrole.setTrole(roleDao.get(Trole.class, "0"));
		tusertrole.setTuser(userDao.get(Tuser.class, "0"));
		userRoleDao.saveOrUpdate(tusertrole);
	}

	private void repairRole() {
		Trole admin = new Trole();
		admin.setId("0");
		admin.setName("超级管理员");
		admin.setRoleRescribe("拥有系统所有的权限");
		roleDao.saveOrUpdate(admin);

		Trole guest = new Trole();
		guest.setId("1");
		guest.setName("来宾");
		guest.setRoleRescribe("");

		roleDao.saveOrUpdate(guest);
	}

	private void repairAutn() {
		Tauth sshe = new Tauth();
		sshe.setId("0");
		sshe.setTauth(null);
		sshe.setName("首页");
		sshe.setUrl("");
		sshe.setSeq("1");
		sshe.setAuthDescribe("EasyUI示例项目");
		authDao.saveOrUpdate(sshe);

		Tauth sjkgl = new Tauth();
		sjkgl.setId("sjkgl");
		sjkgl.setTauth(sshe);
		sjkgl.setName("数据库管理");
		sjkgl.setUrl("");
		sjkgl.setSeq("1");
		sjkgl.setAuthDescribe("可查看数据库链接信息，SQL语句执行情况");
		authDao.saveOrUpdate(sjkgl);

		Tauth ljcjk = new Tauth();
		ljcjk.setId("ljcjk");
		ljcjk.setTauth(sjkgl);
		ljcjk.setName("连接池监控");
		ljcjk.setUrl("/datasourceAction!druid.action");
		ljcjk.setSeq("1");
		ljcjk.setAuthDescribe("可查看数据库链接信息");
		authDao.saveOrUpdate(ljcjk);

		Tauth xtgl = new Tauth();
		xtgl.setId("xtgl");
		xtgl.setTauth(sshe);
		xtgl.setName("系统管理");
		xtgl.setUrl("");
		xtgl.setSeq("2");
		xtgl.setAuthDescribe("");
		authDao.saveOrUpdate(xtgl);

		Tauth yhgl = new Tauth();
		yhgl.setId("yhgl");
		yhgl.setTauth(xtgl);
		yhgl.setName("用户管理");
		yhgl.setUrl("");
		yhgl.setSeq("1");
		yhgl.setAuthDescribe("");
		authDao.saveOrUpdate(yhgl);

		Tauth yhglym = new Tauth();
		yhglym.setId("yhglym");
		yhglym.setTauth(yhgl);
		yhglym.setName("用户管理页面");
		yhglym.setUrl("/userAction!user.action");
		yhglym.setSeq("1");
		yhglym.setAuthDescribe("");
		authDao.saveOrUpdate(yhglym);

		Tauth yhcx = new Tauth();
		yhcx.setId("yhcx");
		yhcx.setTauth(yhgl);
		yhcx.setName("用户查询");
		yhcx.setUrl("/userAction!datagrid.action");
		yhcx.setSeq("2");
		yhcx.setAuthDescribe("");
		authDao.saveOrUpdate(yhcx);

		Tauth yhadd = new Tauth();
		yhadd.setId("yhadd");
		yhadd.setTauth(yhgl);
		yhadd.setName("用户添加");
		yhadd.setUrl("/userAction!add.action");
		yhadd.setSeq("3");
		yhadd.setAuthDescribe("");
		authDao.saveOrUpdate(yhadd);

		Tauth yhedit = new Tauth();
		yhedit.setId("yhedit");
		yhedit.setTauth(yhgl);
		yhedit.setName("用户修改");
		yhedit.setUrl("/userAction!edit.action");
		yhedit.setSeq("4");
		yhedit.setAuthDescribe("");
		authDao.saveOrUpdate(yhedit);

		Tauth yhsc = new Tauth();
		yhsc.setId("yhsc");
		yhsc.setTauth(yhgl);
		yhsc.setName("用户删除");
		yhsc.setUrl("/userAction!delete.action");
		yhsc.setSeq("5");
		yhsc.setAuthDescribe("");
		authDao.saveOrUpdate(yhsc);

		Tauth yhxgmm = new Tauth();
		yhxgmm.setId("yhxgmm");
		yhxgmm.setTauth(yhgl);
		yhxgmm.setName("修改密码");
		yhxgmm.setUrl("/userAction!modifyPwd.action");
		yhxgmm.setSeq("6");
		yhxgmm.setAuthDescribe("批量修改用户密码");
		authDao.saveOrUpdate(yhxgmm);

		Tauth yhxgjs = new Tauth();
		yhxgjs.setId("yhxgjs");
		yhxgjs.setTauth(yhgl);
		yhxgjs.setName("修改角色");
		yhxgjs.setUrl("/userAction!modifyUserRole.action");
		yhxgjs.setSeq("7");
		yhxgjs.setAuthDescribe("批量修改用户角色");
		authDao.saveOrUpdate(yhxgjs);

		Tauth jsgl = new Tauth();
		jsgl.setId("jsgl");
		jsgl.setTauth(xtgl);
		jsgl.setName("角色管理");
		jsgl.setUrl("");
		jsgl.setSeq("2");
		jsgl.setAuthDescribe("");
		authDao.saveOrUpdate(jsgl);

		Tauth jsglym = new Tauth();
		jsglym.setId("jsglym");
		jsglym.setTauth(jsgl);
		jsglym.setName("角色管理页面");
		jsglym.setUrl("/roleAction!role.action");
		jsglym.setSeq("1");
		jsglym.setAuthDescribe("");
		authDao.saveOrUpdate(jsglym);

		Tauth jscx = new Tauth();
		jscx.setId("jscx");
		jscx.setTauth(jsgl);
		jscx.setName("角色查询");
		jscx.setUrl("/roleAction!datagrid.action");
		jscx.setSeq("2");
		jscx.setAuthDescribe("");
		authDao.saveOrUpdate(jscx);

		Tauth jsadd = new Tauth();
		jsadd.setId("jsadd");
		jsadd.setTauth(jsgl);
		jsadd.setName("角色添加");
		jsadd.setUrl("/roleAction!add.action");
		jsadd.setSeq("3");
		jsadd.setAuthDescribe("");
		authDao.saveOrUpdate(jsadd);

		Tauth jsedit = new Tauth();
		jsedit.setId("jsedit");
		jsedit.setTauth(jsgl);
		jsedit.setName("角色编辑");
		jsedit.setUrl("/roleAction!edit.action");
		jsedit.setSeq("4");
		jsedit.setAuthDescribe("");
		authDao.saveOrUpdate(jsedit);

		Tauth jsdelete = new Tauth();
		jsdelete.setId("jsdelete");
		jsdelete.setTauth(jsgl);
		jsdelete.setName("角色删除");
		jsdelete.setUrl("/roleAction!delete.action");
		jsdelete.setSeq("5");
		jsdelete.setAuthDescribe("");
		authDao.saveOrUpdate(jsdelete);

		Tauth qxgl = new Tauth();
		qxgl.setId("qxgl");
		qxgl.setTauth(xtgl);
		qxgl.setName("权限管理");
		qxgl.setUrl("");
		qxgl.setSeq("3");
		qxgl.setAuthDescribe("");
		authDao.saveOrUpdate(qxgl);

		Tauth qxglym = new Tauth();
		qxglym.setId("qxglym");
		qxglym.setTauth(qxgl);
		qxglym.setName("权限管理页面");
		qxglym.setUrl("/authAction!auth.action");
		qxglym.setSeq("1");
		qxglym.setAuthDescribe("");
		authDao.saveOrUpdate(qxglym);

		Tauth qxcx = new Tauth();
		qxcx.setId("qxcx");
		qxcx.setTauth(qxgl);
		qxcx.setName("权限查询");
		qxcx.setUrl("/authAction!treegrid.action");
		qxcx.setSeq("2");
		qxcx.setAuthDescribe("");
		authDao.saveOrUpdate(qxcx);

		Tauth qxadd = new Tauth();
		qxadd.setId("qxadd");
		qxadd.setTauth(qxgl);
		qxadd.setName("权限添加");
		qxadd.setUrl("/authAction!add.action");
		qxadd.setSeq("3");
		qxadd.setAuthDescribe("");
		authDao.saveOrUpdate(qxadd);

		Tauth qxedit = new Tauth();
		qxedit.setId("qxedit");
		qxedit.setTauth(qxgl);
		qxedit.setName("权限编辑");
		qxedit.setUrl("/authAction!edit.action");
		qxedit.setSeq("4");
		qxedit.setAuthDescribe("");
		authDao.saveOrUpdate(qxedit);

		Tauth qxdelete = new Tauth();
		qxdelete.setId("qxdelete");
		qxdelete.setTauth(qxgl);
		qxdelete.setName("权限删除");
		qxdelete.setUrl("/authAction!delete.action");
		qxdelete.setSeq("5");
		qxdelete.setAuthDescribe("");
		authDao.saveOrUpdate(qxdelete);

		Tauth cdgl = new Tauth();
		cdgl.setId("cdgl");
		cdgl.setTauth(xtgl);
		cdgl.setName("菜单管理");
		cdgl.setUrl("");
		cdgl.setSeq("4");
		cdgl.setAuthDescribe("");
		authDao.saveOrUpdate(cdgl);

		Tauth cdglym = new Tauth();
		cdglym.setId("cdglym");
		cdglym.setTauth(cdgl);
		cdglym.setName("菜单管理页面");
		cdglym.setUrl("/menuAction!menu.action");
		cdglym.setSeq("1");
		cdglym.setAuthDescribe("");
		authDao.saveOrUpdate(cdglym);

		Tauth cdcx = new Tauth();
		cdcx.setId("cdcx");
		cdcx.setTauth(cdgl);
		cdcx.setName("菜单查询");
		cdcx.setUrl("/menuAction!treegrid.action");
		cdcx.setSeq("2");
		cdcx.setAuthDescribe("");
		authDao.saveOrUpdate(cdcx);

		Tauth cdadd = new Tauth();
		cdadd.setId("cdadd");
		cdadd.setTauth(cdgl);
		cdadd.setName("菜单添加");
		cdadd.setUrl("/menuAction!add.action");
		cdadd.setSeq("3");
		cdadd.setAuthDescribe("");
		authDao.saveOrUpdate(cdadd);

		Tauth cdedit = new Tauth();
		cdedit.setId("cdedit");
		cdedit.setTauth(cdgl);
		cdedit.setName("菜单编辑");
		cdedit.setUrl("/menuAction!edit.action");
		cdedit.setSeq("4");
		cdedit.setAuthDescribe("");
		authDao.saveOrUpdate(cdedit);

		Tauth cddelete = new Tauth();
		cddelete.setId("cddelete");
		cddelete.setTauth(cdgl);
		cddelete.setName("菜单删除");
		cddelete.setUrl("/menuAction!delete.action");
		cddelete.setSeq("5");
		cddelete.setAuthDescribe("");
		authDao.saveOrUpdate(cddelete);

		Tauth buggl = new Tauth();
		buggl.setId("buggl");
		buggl.setTauth(xtgl);
		buggl.setName("BUG管理");
		buggl.setUrl("");
		buggl.setSeq("5");
		buggl.setAuthDescribe("");
		authDao.saveOrUpdate(buggl);

		Tauth bugglym = new Tauth();
		bugglym.setId("bugglym");
		bugglym.setTauth(buggl);
		bugglym.setName("BUG管理页面");
		bugglym.setUrl("/bugAction!bug.action");
		bugglym.setSeq("1");
		bugglym.setAuthDescribe("");
		authDao.saveOrUpdate(bugglym);

		Tauth bugcx = new Tauth();
		bugcx.setId("bugcx");
		bugcx.setTauth(buggl);
		bugcx.setName("BUG查询");
		bugcx.setUrl("/bugAction!datagrid.action");
		bugcx.setSeq("2");
		bugcx.setAuthDescribe("");
		authDao.saveOrUpdate(bugcx);

		Tauth bugadd = new Tauth();
		bugadd.setId("bugadd");
		bugadd.setTauth(buggl);
		bugadd.setName("BUG描述添加");
		bugadd.setUrl("/bugAction!add.action");
		bugadd.setSeq("3");
		bugadd.setAuthDescribe("");
		authDao.saveOrUpdate(bugadd);

		Tauth bugedit = new Tauth();
		bugedit.setId("bugedit");
		bugedit.setTauth(buggl);
		bugedit.setName("BUG编辑");
		bugedit.setUrl("/bugAction!edit.action");
		bugedit.setSeq("4");
		bugedit.setAuthDescribe("");
		authDao.saveOrUpdate(bugedit);

		Tauth bugdelete = new Tauth();
		bugdelete.setId("bugdelete");
		bugdelete.setTauth(buggl);
		bugdelete.setName("BUG删除");
		bugdelete.setUrl("/bugAction!delete.action");
		bugdelete.setSeq("5");
		bugdelete.setAuthDescribe("");
		authDao.saveOrUpdate(bugdelete);

		Tauth bugupload = new Tauth();
		bugupload.setId("bugupload");
		bugupload.setTauth(buggl);
		bugupload.setName("BUG上传");
		bugupload.setUrl("/bugAction!upload.action");
		bugupload.setSeq("6");
		bugupload.setAuthDescribe("");
		authDao.saveOrUpdate(bugupload);

	}

	private void repairMenu() {
		Tmenu root = new Tmenu("0", "首页", "", 1);
		menuDao.saveOrUpdate(root);

		Tmenu sjkgl = new Tmenu("sjkgl", root, "数据库管理", "", 2);
		menuDao.saveOrUpdate(sjkgl);

		Tmenu druidIndex = new Tmenu("druidIndex", sjkgl, "druid监控", "/druid/index.html", 3);
		menuDao.saveOrUpdate(druidIndex);

		Tmenu xtgl = new Tmenu("xtgl", root, "系统管理", "", 3);
		menuDao.saveOrUpdate(xtgl);

		Tmenu yhgl = new Tmenu("yhgl", xtgl, "用户管理", "/admin/yhgl.jsp", 1);
		menuDao.saveOrUpdate(yhgl);

		Tmenu jsgl = new Tmenu("jsgl", xtgl, "角色管理", "/admin/jsgl.jsp", 2);
		menuDao.saveOrUpdate(jsgl);

		Tmenu qxgl = new Tmenu("qxgl", xtgl, "权限管理", "/admin/qxgl.jsp", 3);
		menuDao.saveOrUpdate(qxgl);

		Tmenu cdgl = new Tmenu("cdgl", xtgl, "菜单管理", "/admin/cdgl.jsp", 4);
		menuDao.saveOrUpdate(cdgl);

		Tmenu buggl = new Tmenu("buggl", xtgl, "BUG管理", "/admin/buggl.jsp", 5);
		menuDao.saveOrUpdate(buggl);

	}

	private void repairRoleAuth() {
		roleAuthDao.executeHql("delete Troletauth t where t.trole.id = '0'");
		roleAuthDao.executeHql("delete Troletauth t where t.trole.id is null");
		Trole role = roleDao.get(Trole.class, "0");
		List<Tauth> auths = authDao.find("from Tauth t");
		if (auths != null && auths.size() > 0) {
			for (Tauth t : auths) {
				Troletauth roleAuth = new Troletauth();
				roleAuth.setTauth(t);
				roleAuth.setId(UUID.randomUUID().toString());
				roleAuth.setTrole(role);
				roleAuthDao.save(roleAuth);
			}
		}
	}

	private void repairUser() {
		userDao.saveOrUpdate(new Tuser("0", "admin", Encrypt.e("admin")));
	}

	@Resource
	public void setAuthDao(AuthDaoI authDao) {
		this.authDao = authDao;
	}

	@Resource
	public void setBugDao(BugDaoI bugDao) {
		this.bugDao = bugDao;
	}

	@Resource
	public void setMenuDao(MenuDaoI menuDao) {
		this.menuDao = menuDao;
	}

	@Resource
	public void setRoleAuthDao(RoleAuthDaoI roleAuthDao) {
		this.roleAuthDao = roleAuthDao;
	}

	@Resource
	public void setRoleDao(RoleDaoI roleDao) {
		this.roleDao = roleDao;
	}

	@Resource
	public void setUserDao(UserDaoI userDao) {
		this.userDao = userDao;
	}

	@Resource
	public void setUserRoleDao(UserRoleDaoI userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

}
