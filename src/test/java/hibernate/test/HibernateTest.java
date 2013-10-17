package hibernate.test;

import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wl.model.Tuser;
import wl.service.UserServiceI;

public class HibernateTest
{

	@Test
	public void test()
	{
		ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] { "classpath:spring.xml", "classpath:spring-hibernate.xml" });
		UserServiceI userService = (UserServiceI) ctx.getBean("userService");
//		userService.save(new Tuser("1","1",new Date()));
	}
}
