package com.edu.ubosque.prg.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.edu.ubosque.prg.dao.NewDAO;
import com.edu.ubosque.prg.entity.New;
import com.edu.ubosque.prg.util.HibernateUtil;

public class NewDAOImpl implements NewDAO {

	@Override
	public List<New> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from New").list();
		t.commit();
		return lista;
	}

	@Override
	public void save(New news) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(news);
		t.commit();
	}

	@Override
	public New getNew(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return (New) session.load(New.class, id);
	}

	@Override
	public void remove(New news) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(news);
		t.commit();
	}

	@Override
	public void update(New news) {
		// TODO Auto-generated method stub
		
	}

}
