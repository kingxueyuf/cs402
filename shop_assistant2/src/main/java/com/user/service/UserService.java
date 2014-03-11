package com.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.user.dao.UserDao;
import com.user.data.DbUser;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	public Boolean checkAccount(DbUser user) {
		List<DbUser> list = userDao.listUser();
		for (int i = 0; i < list.size(); i++) {
			DbUser item = list.get(i);
			// if(item.)
		}
		return null;
	}

	public String  addUser(DbUser user) {
		String uid = userDao.addUser(user);
		return uid;
	}

	public String checkLogin(String username, String password) {
		// TODO Auto-generated method stub

		List<DbUser> list = userDao.listUser();
		for (int i = 0; i < list.size(); i++) {
			DbUser user = list.get(i);
			if (user.getName().equals(username)
					&& user.getPassword().equals(password)) {
				return user.getId();
			}
		}
		return null;
	}

}
