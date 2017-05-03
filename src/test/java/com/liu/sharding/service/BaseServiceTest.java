package com.liu.sharding.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * biz层测试基类
 * 
 * @author liuzhihong
 */
@ContextConfiguration(locations = { "classpath:conf/applicationContext-sharding.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseServiceTest {

	static {
		// 禁用远程调用组件
	}

	@BeforeClass
	public static void initTestData() throws IOException, SQLException {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"conf/applicationContext-sharding.xml");
	}

	@AfterClass
	public static void afterDaoTest() {
	}
}
