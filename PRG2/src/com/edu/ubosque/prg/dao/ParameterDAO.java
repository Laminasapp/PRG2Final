package com.edu.ubosque.prg.dao;

import java.util.List;

import com.edu.ubosque.prg.entity.Parameter;

public interface ParameterDAO {
	
	public void save(Parameter parameter);

	public Parameter getParameter(int id);

	public Parameter getParameter(String param);
	
	public void remove(Parameter parameter);

	public void update(Parameter parameter);

	public List<Parameter> list();
	
}
