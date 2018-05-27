package com.edu.ubosque.prg.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.apache.log4j.Logger;

import com.edu.ubosque.prg.dao.ParameterDAO;
import com.edu.ubosque.prg.dao.impl.ParameterDAOImpl;
import com.edu.ubosque.prg.entity.Parameter;

@ManagedBean
public class ParameterBean {

	final static Logger logger = Logger.getLogger(ParameterBean.class);
	private Parameter parameter;
	private DataModel listaParameter;

	public String adicionarParameter(Parameter parameter) {
		ParameterDAO dao = new ParameterDAOImpl();
		dao.save(parameter);
		return "index";
	}

	public DataModel getListarParameter() {
		List<Parameter> lista = new ParameterDAOImpl().list();
		listaParameter = new ListDataModel(lista);
		return listaParameter;
	}
	
	public void modificarParameter()
	{
		
	}
}
