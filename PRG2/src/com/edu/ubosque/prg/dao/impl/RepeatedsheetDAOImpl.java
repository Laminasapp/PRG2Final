package com.edu.ubosque.prg.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.edu.ubosque.prg.dao.RepeatedsheetDAO;
import com.edu.ubosque.prg.entity.Repeatedsheet;
import com.edu.ubosque.prg.util.HibernateUtil;

public class RepeatedsheetDAOImpl implements RepeatedsheetDAO{

	@Override
	public List<Repeatedsheet> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from Repeatedsheet").list();
		t.commit();
		return lista;
	}

	@Override
	public void save(Repeatedsheet repeatedsheet) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(repeatedsheet);
		t.commit();
	}

	@Override
	public Repeatedsheet getRepeatedsheet(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return (Repeatedsheet) session.load(Repeatedsheet.class, id);
	}

	@Override
	public void remove(Repeatedsheet repeatedsheet) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(repeatedsheet);
		t.commit();
	}

	@Override
	public void update(Repeatedsheet repeatedsheet) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(repeatedsheet);
		t.commit();
	}

	@Override
	public List<Repeatedsheet> list(int pLamina) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from Repeatedsheet where numberSheets = " + pLamina).list();
		t.commit();
		return lista;
	}

}
