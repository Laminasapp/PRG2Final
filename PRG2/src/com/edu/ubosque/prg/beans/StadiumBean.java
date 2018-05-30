package com.edu.ubosque.prg.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.apache.log4j.Logger;

import com.edu.ubosque.prg.dao.StadiumDAO;
import com.edu.ubosque.prg.dao.impl.StadiumDAOImpl;
import com.edu.ubosque.prg.entity.Stadium;
/**
 * Descripción: Clase bean que se vincula a un bean para manejar un formularo
 *
 */
@ManagedBean
public class StadiumBean {
	
	final static Logger logger = Logger.getLogger(StadiumBean.class);
	private Stadium stadium;
	private DataModel<Stadium> listaStadium;
	/**
	 * Método que se encarga de agregar un estadio a la base de datos
	 * @param stadium Objeto del estadiop que se agrega
	 * @return
	 */
	public String adicionarAuditoria(Stadium stadium) {
		StadiumDAO dao = new StadiumDAOImpl();
		dao.save(stadium);
		logger.info("Se crea un nuevo estadio");
		return "prime";
	}
	/**
	 * Método que devuelve una lista de estadios
	 * @return
	 */
	public DataModel<Stadium> getListarAuditoria() {
		List<Stadium> lista = new StadiumDAOImpl().list();
		listaStadium = new ListDataModel<Stadium>(lista);
		return listaStadium;
	}

	public Stadium getStadium()
	{
		return stadium;
	}

	public void setStadium(Stadium stadium)
	{
		this.stadium = stadium;
	}
	
}
