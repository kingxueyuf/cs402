package com.product.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.product.data.UserProduct;

@Repository
public class UserProductDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void add(UserProduct up) {
		Session session = this.sessionFactory.openSession();
		session.save(up);
		session.close();
	}

	public List<UserProduct> view() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		List<UserProduct> list;
		try {
			list = session.createQuery("from UserProduct").list();
		} finally {
			session.close();
		}
		return list;
	}

	public void delete(String upId) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("delete UserProduct where id = :id");
		query.setParameter("id", Integer.valueOf(upId));
		query.executeUpdate();
		session.close();
	}

	public void deleteByPid(Integer pid) {
		Session session = this.sessionFactory.openSession();
		Query query = session
				.createQuery("delete UserProduct where pid = :pid");
		query.setParameter("pid", pid);
		query.executeUpdate();
		session.close();

	}
}
