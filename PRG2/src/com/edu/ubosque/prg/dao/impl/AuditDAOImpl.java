package com.edu.ubosque.prg.dao.impl;

import com.edu.ubosque.prg.entity.Audit;
import com.edu.ubosque.prg.dao.AuditDAO;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.edu.ubosque.prg.util.HibernateUtil;

public class AuditDAOImpl implements AuditDAO {

	public void save(Audit auditoria) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(auditoria);
		t.commit();
	}

	public List<Audit> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from Audit").list();
		t.commit();
		return lista;
	}

}