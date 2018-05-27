package com.edu.ubosque.prg.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import com.edu.ubosque.prg.dao.RepeatedsheetDAO;
import com.edu.ubosque.prg.dao.impl.RepeatedsheetDAOImpl;
import com.edu.ubosque.prg.entity.Repeatedsheet;

@ManagedBean
public class RepeatedsheetBean {

	final static Logger logger = Logger.getLogger(RepeatedsheetBean.class);
	private Repeatedsheet repeatedsheet;
	private DataModel listaRepeatedsheet;

	@ManagedProperty(value="#{userBean}")
	private UserBean user;
	
	public String adicionarRepeatedsheet() {
		RepeatedsheetDAO dao = new RepeatedsheetDAOImpl();
		dao.save(repeatedsheet);
		return "indexUsuario";
	}

	
	
	public DataModel getListarRepeatedsheet() {
		List<Repeatedsheet> lista = new RepeatedsheetDAOImpl().list();
		listaRepeatedsheet = new ListDataModel(lista);
		return listaRepeatedsheet;
	}
	
	@PostConstruct
	public void init(){
		repeatedsheet = new Repeatedsheet();
		repeatedsheet.setUserId(user.getUsuario().getId());
//		dialogNuevaRS();
	}
	
	public String prepararAdicionarRS() {
		dialogNuevaRS();
		return "indexUsuario";
	}
	
	public void dialogNuevaRS(){
		Map<String, Object> opciones = new HashMap<String, Object>();
		opciones.put("modal", true);
		opciones.put("draggable", false);
		opciones.put("resizable", false);
		opciones.put("width", 1000);
		opciones.put("height", 400);
		opciones.put("contentWidth", "100%");
		opciones.put("contentHeight", "100%");
		opciones.put("headerElement", "customheader");

		PrimeFaces.current().dialog().openDynamic("nuevaRepeatedSheets", opciones, null);
	}

	public Repeatedsheet getRepeatedsheet() {
		return repeatedsheet;
	}

	public void setRepeatedsheet(Repeatedsheet repeatedsheet) {
		this.repeatedsheet = repeatedsheet;
	}
	
}
