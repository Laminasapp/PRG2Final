package com.edu.ubosque.prg.dao.impl;

import com.edu.ubosque.prg.entity.Audit;
import com.edu.ubosque.prg.dao.AuditDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.edu.ubosque.prg.util.HibernateUtil;

public class AuditDAOImpl implements AuditDAO {

	public void save(Audit auditoria) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(auditoria);
		t.commit();
		session.close();
	}

	public List<Audit> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from Audit").list();
		t.commit();
		session.close();
		return lista;
	}

	@Override
	public List<Audit> filtrados(Date d1, Date d2) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List<Audit> lista = session.createQuery("from Audit").list();
		List<Audit> tmp = new ArrayList<>();
		for (int i = 0; i < lista.size(); i++) {
			
			if((lista.get(i).getCreateDate().compareTo(d1)>0) && (lista.get(i).getCreateDate().compareTo(d2)<0)) {
				tmp.add(lista.get(i));
			}
			
		}
		
		t.commit();
		session.close();
		return tmp;
	}

}