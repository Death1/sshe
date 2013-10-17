package Hql.test;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wl.dao.BaseDaoI;
import wl.model.Tbug;
import wl.model.Tuser;
import wl.service.RepairServiceI;
import wl.service.RoleServiceI;

public class HqlTest {

	@Test
	public void Test() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] { "classpath:spring.xml", "classpath:spring-hibernate.xml" });
		SessionFactory sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");
		@SuppressWarnings("unchecked")
		BaseDaoI<Tuser> baseDao = (BaseDaoI<Tuser>) ctx.getBean("baseDao");
		BaseDaoI<Tbug> bugDao = (BaseDaoI<Tbug>) ctx.getBean("baseDao");
		RepairServiceI repairService = (RepairServiceI) ctx.getBean("repairService");
		RoleServiceI roleService = (RoleServiceI) ctx.getBean("roleService");
		// System.out.println(sessionFactory.openSession().createQuery("FROM User u WHERE u.pwd='550a141f12de6341fba65b0ad0433500'").list());
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		// session.saveOrUpdate(new Menu("0", "首页", ""));
		Query query = session.createQuery("from Tuser u where 1=1 and u.name like '%%a%%'");
		System.out.println(query.list().size());
		// System.out.println(query.setFirstResult(0).setMaxResults(5).list());
		// System.out.println(baseDao.find("from Tuser u where u.createDateTime >= '2013-03-26 18:59:02'"));
		// Tmenu t = (Tmenu) session.get(Tmenu.class, "buggl");
		// System.out.println(t.getMenu().getText());
		// Tbug t=(Tbug) session.get(Tbug.class, "1");
		// System.out.println(t.getBugDescribe());
		// repairService.repair();
		// Role role = new Role();
		// role.setOrder("desc");
		// role.setPage(1);
		// role.setRows(10);
		// role.setSort("name");
		// roleService.datagrid(role);
		tx.commit();
	}
}
