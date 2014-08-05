package com.wormchaos.service;

import com.wormchaos.dao.UserDao;
import com.wormchaos.module.User;

public class UserServiceImpl implements UserService{
	private UserDao userDao;
	public User login(String userId, String password) {
		User user=new User();
		user.setUserid(userId);
		user.setPassword(password);
		return userDao.findUser(user);
	}

}
