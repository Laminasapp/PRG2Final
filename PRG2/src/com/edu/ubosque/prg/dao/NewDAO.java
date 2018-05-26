package com.edu.ubosque.prg.dao;

import java.util.List;

import com.edu.ubosque.prg.entity.New;

public interface NewDAO {
	
	public void save(New news);

	public New getNew(int id);

	public void remove(New news);

	public void update(New news);

	
	public List<New> list();

}
