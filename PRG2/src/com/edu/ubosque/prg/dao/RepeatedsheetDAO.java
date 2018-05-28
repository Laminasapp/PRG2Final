package com.edu.ubosque.prg.dao;

import java.util.List;

import com.edu.ubosque.prg.entity.Repeatedsheet;

public interface RepeatedsheetDAO {

	public void save(Repeatedsheet repeatedsheet);

	public Repeatedsheet getRepeatedsheet(int id);
	
	public List<Repeatedsheet> listLaminas(int pId);

	public void remove(Repeatedsheet repeatedsheet);

	public void update(Repeatedsheet repeatedsheet);
	
	public List<Repeatedsheet> list(int pId);
	
	public List<Repeatedsheet> listaRepetidas(int pId);
	
	public List<Repeatedsheet> repetidasUsuario(int pId);
	
}
