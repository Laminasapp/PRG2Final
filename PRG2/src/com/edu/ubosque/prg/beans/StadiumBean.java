package com.edu.ubosque.prg.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.apache.log4j.Logger;

import com.edu.ubosque.prg.dao.StadiumDAO;
import com.edu.ubosque.prg.dao.impl.StadiumDAOImpl;
import com.edu.ubosque.prg.entity.Stadium;

@ManagedBean
public class StadiumBean {
	
	final static Logger logger = Logger.getLogger(StadiumBean.class);
	private Stadium stadium;
	private DataModel listaStadium;

	public String adicionarAuditoria(Stadium stadium) {
		StadiumDAO dao = new StadiumDAOImpl();
		dao.save(stadium);
		return "index";
	}

	public DataModel getListarAuditoria() {
		List<Stadium> lista = new StadiumDAOImpl().list();
		listaStadium = new ListDataModel(lista);
		return listaStadium;
	}
	
}
