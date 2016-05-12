package cn.itcast.oa.base;

import org.junit.BeforeClass;
import org.junit.Test;

import cn.itcast.oa.dao.RoleDao;
import cn.itcast.oa.dao.UserDao;
import cn.itcast.oa.dao.impl.RoleDaoImpl;
import cn.itcast.oa.dao.impl.UserDaoImpl;

public class BaseDaoTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testSava() {
		UserDao userDao = new UserDaoImpl();
		RoleDao roleDao = new RoleDaoImpl();
		
	}

}
