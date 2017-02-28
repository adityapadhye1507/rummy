package com.data.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.data.dao.RoomDao;
import com.web.model.Request;
import com.web.model.WaitingRoom;

@Repository("roomDao")
public class RoomDaoImpl implements RoomDao {

	@Autowired
	SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<String> getUsers(int userId) throws Exception {

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Criteria criteria = session.createCriteria(WaitingRoom.class);
			criteria.add(Restrictions.eq("inGame", new Byte("0")));
			criteria.add(Restrictions.not(Restrictions.eq("userId", userId)));
			List players = criteria.list();
			List<String> users = new ArrayList<String>();
			for (Object player : players) {
				WaitingRoom wrPlayer = (WaitingRoom) player;
				users.add(String.valueOf(wrPlayer.getUserId()));
			}
			tx.commit();
			session.close();
			return users;
		} catch (Exception ex) {
			// Log the exception here
			tx.rollback();
			ex.printStackTrace();
			throw ex;
		}
	}

	@Override
	public List<String> getUsers() throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Criteria criteria = session.createCriteria(WaitingRoom.class);
			criteria.add(Restrictions.eq("inGame", new Byte("0")));
			List players = criteria.list();
			List<String> users = new ArrayList<String>();
			for (Object player : players) {
				WaitingRoom wrPlayer = (WaitingRoom) player;
				users.add(String.valueOf(wrPlayer.getUserId()));
			}
			tx.commit();
			session.close();
			return users;
		} catch (Exception ex) {
			// Log the exception here
			tx.rollback();
			ex.printStackTrace();
			throw ex;
		}
	}

	@Override
	public WaitingRoom getUser(int userId) throws Exception {

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Criteria criteria = session.createCriteria(WaitingRoom.class);
			WaitingRoom row = (WaitingRoom) criteria.add(Restrictions.eq("userId", userId)).uniqueResult();
			tx.commit();
			session.close();
			return row;
		} catch (Exception ex) {
			// Log the exception here
			tx.rollback();
			ex.printStackTrace();
			throw ex;
		}
	}

	@Override
	public boolean add(WaitingRoom row) throws Exception {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.saveOrUpdate(row);
			tx.commit();
			session.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(WaitingRoom row) throws Exception {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.delete(row);
			tx.commit();
			session.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(WaitingRoom row) throws Exception {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.saveOrUpdate(row);
			tx.commit();
			session.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean addRequest(Request row) throws Exception {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.save(row);
			tx.commit();
			session.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Request getRequestBySender(String senderId) throws Exception {

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Request.class);
		System.out.println("Fetching senderID: " + senderId);
		Request row = (Request) criteria.add(Restrictions.eq("sender", senderId)).uniqueResult();
		if (row != null) {
			System.out.println("Request already found for sender: " + row.getSender());
		}
		tx.commit();
		session.close();
		return row;

	}

	@Override
	public Request getRequestByReceiver(String recieverId) throws Exception {

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		Criteria criteria = session.createCriteria(Request.class);
		System.out.println("Fetching recieverId: " + recieverId);
		Request row = (Request) criteria.add(Restrictions.eq("reciever", recieverId)).uniqueResult();
		if (row != null) {
			System.out.println("Request found for reciever: " + row.getReciever());
			System.out.println("Requested by sender: " + row.getSender());
		}

		tx.commit();
		session.close();
		return row;
	}

	@Override
	public boolean deleteRequest(Request row) throws Exception {

		/*
		 * Session session = sessionFactory.openSession(); Object o =
		 * session.load(Request.class, row.getSender()); Transaction tx =
		 * session.beginTransaction(); session.delete(o); tx.commit();
		 * session.close(); return true;
		 */

		try {
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.delete(row);
			tx.commit();
			session.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
