package com.data.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.data.dao.UserDao;
import com.web.model.User;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

	@Autowired
	SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public boolean addUser(User user) {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.save(user);
			tx.commit();
			session.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public User getUser(String userName) throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(User.class);
		User user = (User) criteria.add(Restrictions.eq("username", userName)).uniqueResult();
		tx.commit();
		session.close();
		return user;
	}

	@Override
	public User getUserById(int userId) throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Criteria criteria = session.createCriteria(User.class);
		User user = (User) criteria.add(Restrictions.eq("userId", userId)).uniqueResult();
		
		tx.commit();
		session.close();
		return user;
	}

	@Override
	public boolean deleteUser(int id) throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Object o = session.load(User.class, id);
		session.delete(o);
		tx.commit();
		session.close();
		return false;
	}

}
