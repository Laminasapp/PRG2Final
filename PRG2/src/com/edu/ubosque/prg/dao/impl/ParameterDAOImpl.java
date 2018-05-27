package com.edu.ubosque.prg.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.edu.ubosque.prg.dao.ParameterDAO;
import com.edu.ubosque.prg.entity.Parameter;
import com.edu.ubosque.prg.util.HibernateUtil;

public class ParameterDAOImpl implements ParameterDAO
{
	
	@Override
	public List<Parameter> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from Parameter").list();
		t.commit();
		session.close();
		return lista;
	}
	
	@Override
	public void save(Parameter parameter) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(parameter);
		t.commit();
		session.close();
	}
	
	@Override
	public Parameter getParameter(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Parameter respuesta = (Parameter) session.load(Parameter.class, id);
		t.commit();
		session.close();
		return respuesta;
	}
	
	@Override
	public void remove(Parameter parameter) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(parameter);
		t.commit();
		session.close();
	}
	
	@Override
	public void update(Parameter parameter) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(parameter);
		t.commit();
		session.close();
	}
	
	
	
}
