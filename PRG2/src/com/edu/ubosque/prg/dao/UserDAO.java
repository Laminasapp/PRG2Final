package com.edu.ubosque.prg.dao;

import com.edu.ubosque.prg.entity.User;
import java.util.List;

public interface UserDAO {

	public void save(User usuario);

	public User getUsuario(int id);
	
	public User getUsuario(String parameter);
	
	public User getUsuario(String name,String pass);

	public List<User> list();

	public void remove(User usuario);

	public void update(User usuario);
	
	public boolean userName(String pName);

}
