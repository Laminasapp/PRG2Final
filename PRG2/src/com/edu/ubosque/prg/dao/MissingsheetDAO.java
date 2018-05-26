package com.edu.ubosque.prg.dao;

import java.util.List;

import com.edu.ubosque.prg.entity.Missingsheet;

public interface MissingsheetDAO {
	
	public void save(Missingsheet missingsheet);

	public Missingsheet getMissingsheet(int id);
	
	public List<Missingsheet> list(long pId);

	public Missingsheet getMissingsheet(int userId, int numberSheets);
	
	public void remove(Missingsheet missingsheet);

	public void update(Missingsheet missingsheet);
	
	public List<Missingsheet> list(String userId);

}
