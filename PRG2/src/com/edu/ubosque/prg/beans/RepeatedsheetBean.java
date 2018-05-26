package com.edu.ubosque.prg.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.apache.log4j.Logger;

import com.edu.ubosque.prg.dao.RepeatedsheetDAO;
import com.edu.ubosque.prg.dao.impl.RepeatedsheetDAOImpl;
import com.edu.ubosque.prg.entity.Repeatedsheet;

@ManagedBean
public class RepeatedsheetBean {

	final static Logger logger = Logger.getLogger(RepeatedsheetBean.class);
	private Repeatedsheet repeatedsheet;
	private DataModel listaRepeatedsheet;

	public String adicionarRepeatedsheet(Repeatedsheet repeatedsheet) {
		RepeatedsheetDAO dao = new RepeatedsheetDAOImpl();
		dao.save(repeatedsheet);
		return "index";
	}

	public DataModel getListarRepeatedsheet() {
		List<Repeatedsheet> lista = new RepeatedsheetDAOImpl().list();
		listaRepeatedsheet = new ListDataModel(lista);
		return listaRepeatedsheet;
	}
	
}
