package com.edu.ubosque.prg.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.apache.log4j.Logger;

import com.edu.ubosque.prg.dao.NewDAO;
import com.edu.ubosque.prg.dao.impl.NewDAOImpl;
import com.edu.ubosque.prg.entity.New;

@ManagedBean
public class NewBean {
	
	final static Logger logger = Logger.getLogger(NewBean.class);
	private New news;
	private DataModel listaNews;

	public String adicionarNews(New news) {
		NewDAO dao = new NewDAOImpl();
		dao.save(news);
		return "index";
	}

	public DataModel getListarNews() {
		List<New> lista = new NewDAOImpl().list();
		listaNews = new ListDataModel(lista);
		return listaNews;
	}
}
