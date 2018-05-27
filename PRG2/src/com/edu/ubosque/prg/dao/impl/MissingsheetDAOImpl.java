package com.edu.ubosque.prg.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.edu.ubosque.prg.dao.MissingsheetDAO;
import com.edu.ubosque.prg.entity.Missingsheet;
import com.edu.ubosque.prg.entity.User;
import com.edu.ubosque.prg.util.HibernateUtil;

public class MissingsheetDAOImpl implements MissingsheetDAO{

	@Override
	public List<Missingsheet> list(String userId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from Missingsheet WHERE userId = '" +userId + "'" ).list();
		t.commit();
		session.close();
		return lista;
	}

	@Override
	public void save(int userId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		for (int i = 1; i < 671; i++) {
			Missingsheet ms = new Missingsheet();
			ms.setUserId(userId);
			ms.setNumberSheets(i);
			ms.setCountSheets(1);
			session.save(ms);
		}
		
		t.commit();
		session.close();
	}

	@Override
	public Missingsheet getMissingsheet(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return (Missingsheet) session.load(Missingsheet.class, id);
	}

	@Override
	public void remove(Missingsheet missingsheet) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(missingsheet);
		t.commit();
		session.close();
	}

	@Override
	public void update(Missingsheet missingsheet) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(missingsheet);
		t.commit();
		session.close();
	}

	@Override
	public Missingsheet getMissingsheet(int userId, int numberSheets) {
		Missingsheet rta = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List list = session.createQuery("from Missingsheet where userId = " +userId+" AND numberSheets = "+numberSheets+"").list();
		if(list.size()!=0) {
			rta = (Missingsheet) list.get(0);
		}
		t.commit();
		session.close();
		return rta;
	}

	@Override
	public List<Missingsheet> list(long pId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from Missingsheet where userId=" + pId).list();
		t.commit();
		session.close();
		return lista;
	}

}
