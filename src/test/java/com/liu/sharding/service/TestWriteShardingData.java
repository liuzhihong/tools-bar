package com.liu.sharding.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.liu.core.dao.TUserMapper;
import com.liu.core.entity.TUser;

public class TestWriteShardingData extends BaseServiceTest{

	@Autowired
	private TUserMapper userMapper;
	
	@Test
	public void testInsertUser(){
		List<TUser> userList = new ArrayList<TUser>();
		TUser user1 = new TUser();
		user1.setAge(21);
		user1.setCreateTime(new Date());
		user1.setName("张三");
		user1.setOuId(1401);
		user1.setPosition("程序猿");
		userList.add(user1);
		
		TUser user2 = new TUser();
		user2.setAge(25);
		user2.setCreateTime(new Date());
		user2.setName("李四");
		user2.setOuId(1401);
		user2.setPosition("程序猿");
		userList.add(user2);
		
		TUser user3 = new TUser();
		user3.setAge(22);
		user3.setCreateTime(new Date());
		user3.setName("小珍");
		user3.setOuId(1402);
		user3.setPosition("程序猿");
		userList.add(user3);
		
		for(TUser user : userList){
			userMapper.insertSelective(user);
		}
	}
}
