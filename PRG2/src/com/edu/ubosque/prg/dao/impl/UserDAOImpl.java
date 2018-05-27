package com.edu.ubosque.prg.dao.impl;

import com.edu.ubosque.prg.entity.User;
import com.edu.ubosque.prg.dao.UserDAO;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.edu.ubosque.prg.util.HibernateUtil;

public class UserDAOImpl implements UserDAO {

	public void save(User usuario) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.save(usuario);
		t.commit();
		session.close();
	}

	public User getUsuario(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		return (User) session.load(User.class, id);
	}
	
	@Override
	public User getUsuario(String parameter)
	{
		User rta = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List<User> list;
		
		
		if(!parameter.contains("@"))
		{
			list = session.createQuery("from User where userName = '" +parameter+"' ").list();	
		}
		
		else
		{
			list = session.createQuery("from User where emailAddress = '" +parameter+"' ").list();	
		}
		
		if(list.size()!=0) {
			rta = (User) list.get(0);
		}
		t.commit();
		session.close();
		return rta;
	}
	
	public User getUsuario(String name, String pass) {
		User rta = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List list = session.createQuery("from User where userName = '" +name+"' AND password = '"+pass+"'").list();
		if(list.size()!=0) {
			rta = (User) list.get(0);
		}
		t.commit();
		session.close();
		return rta;
	}

	public void update(User usuario) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.update(usuario);
		t.commit();
		session.close();
	}

	public void remove(User usuario) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(usuario);
		t.commit();
		session.close();
	}

	public List<User> list() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List lista = session.createQuery("from User").list();
		t.commit();
		session.close();
		return lista;
	}

}
