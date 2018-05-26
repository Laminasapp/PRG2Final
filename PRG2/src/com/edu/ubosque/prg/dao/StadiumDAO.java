package com.edu.ubosque.prg.dao;

import java.util.List;

import com.edu.ubosque.prg.entity.Stadium;

public interface StadiumDAO {

	public void save(Stadium stadium);

	public Stadium getStadium(int id);

	public void remove(Stadium stadium);

	public void update(Stadium stadium);
	
	public List<Stadium> list();

	

	
}
