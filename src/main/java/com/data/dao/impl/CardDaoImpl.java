package com.data.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.data.dao.CardDao;
import com.web.model.Card;

@Repository("cardDao")
public class CardDaoImpl implements CardDao {

	@Autowired
	SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<String> getCards() throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Criteria criteria = session.createCriteria(Card.class);
			List results = criteria.list();
			List<String> messages = new ArrayList<String>();
			for (Object row : results) {
				Card card = (Card) row;
				messages.add(card.getCardId());
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
	public String toCsv(List<String> list) {
		StringBuilder sb = new StringBuilder();
		for (String string : list) {
			sb.append(string + ",");
		}
		return sb.toString();
	}

	@Override
	public List<String> toList(String string) {
		String[] arr = string.split(",");
		List<String> list = new ArrayList<String>();
		for (String str : arr) {
			list.add(str);
		}
		return list;
	}
	
	
}
