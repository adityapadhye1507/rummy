package com.data.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.data.dao.GameDao;
import com.web.model.GameDetail;

@Repository("gameDao")
public class GameDaoImpl implements GameDao {

	@Autowired
	SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public boolean addGame(GameDetail game) throws Exception {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.save(game);
			tx.commit();
			session.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public GameDetail getGameById(String gameId) throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(GameDetail.class);
		System.out.println("Fetching game: " + gameId);
		GameDetail game = (GameDetail) criteria.add(Restrictions.eq("gameId", gameId)).uniqueResult();
		tx.commit();
		session.close();
		return game;
	}

	@Override
	public GameDetail getGameBySender(int senderId) throws Exception {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(GameDetail.class);
		Transaction tx = session.beginTransaction();
		System.out.println("Fetching game for player: " + senderId);
		GameDetail game = (GameDetail) criteria.add(Restrictions.eq("player1", senderId))
				.add(Restrictions.eq("active", "true")).uniqueResult();
		tx.commit();
		session.close();
		return game;
	}

	@Override
	public boolean delete(GameDetail game) throws Exception {
		try {
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			game.setActive("false");
			session.saveOrUpdate(game);
			tx.commit();
			session.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(GameDetail game) throws Exception {
		try {
			game.setActive("true");
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.saveOrUpdate(game);
			tx.commit();
			session.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
