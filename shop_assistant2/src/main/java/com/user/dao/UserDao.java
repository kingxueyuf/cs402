package com.user.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.product.data.Product;
import com.user.data.DbUser;

@Repository
public class UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	public List<DbUser> listUser() {
		Session session = sessionFactory.openSession();
		List<DbUser> list;
		try {
			list = session.createQuery("from DbUser").list();
		} finally {
			session.close();
		}
		return list;
	}

	public String addUser(DbUser user) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		String uid = (String) session.save(user);
		session.close();
		return uid;
	}

}
