package com.edu.ubosque.prg.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.edu.ubosque.prg.dao.StadiumDAO;
import com.edu.ubosque.prg.entity.Stadium;
import com.edu.ubosque.prg.util.HibernateUtil;

public class StadiumDAOImpl implements StadiumDAO{

	@Override
	public List<Stadium> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from Stadium").list();
		t.commit();
		session.close();
		return lista;
	}

	@Override
	public void save(Stadium stadium) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(stadium);
		t.commit();
		session.close();
	}

	@Override
	public Stadium getStadium(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return (Stadium) session.load(Stadium.class, id);
	}

	@Override
	public void remove(Stadium stadium) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(stadium);
		t.commit();
		session.close();
	}

	@Override
	public void update(Stadium stadium) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(stadium);
		t.commit();
		session.close();
	}

	
	
}
