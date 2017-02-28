package com.data.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.data.dao.ChatDao;
import com.web.model.Chat;

@Repository("chatDao")
public class ChatDaoImpl implements ChatDao {

	@Autowired
	SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Chat> getChats(String gameId) throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Criteria criteria = session.createCriteria(Chat.class);
			criteria.add(Restrictions.eq("gameId", gameId));
			criteria.addOrder(Order.asc("timestamp"));
			List results = criteria.list();
			List<Chat> messages = new ArrayList<Chat>();
			for (Object row : results) {
				Chat chat = (Chat) row;
				messages.add(chat);
			}
			tx.commit();
			session.close();
			return messages;
		} catch (Exception ex) {
			// Log the exception here
			tx.rollback();
			ex.printStackTrace();
			throw ex;
		}
	}

	@Override
	public boolean addChat(Chat chat) throws Exception {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.save(chat);
			tx.commit();
			session.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	

}
