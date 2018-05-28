package com.edu.ubosque.prg.beans;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.apache.log4j.Logger;

import com.edu.ubosque.prg.dao.AuditDAO;
import com.edu.ubosque.prg.dao.NewDAO;
import com.edu.ubosque.prg.dao.impl.AuditDAOImpl;
import com.edu.ubosque.prg.dao.impl.NewDAOImpl;
import com.edu.ubosque.prg.entity.Audit;
import com.edu.ubosque.prg.entity.New;
import com.edu.ubosque.prg.util.Util;

@ManagedBean
@SessionScoped
public class NewBean {
	
	final static Logger logger = Logger.getLogger(NewBean.class);
	private New news;
	private DataModel listaNews;

	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;
	
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
	public void agregarNew()
	{
		NewDAO DAO = new NewDAOImpl();
		DAO.save(news);
		
		Util.darMensaje("Ha sido agregado", "Se ha agregado la nueva noticia");
		hacerAuditoria("Create", news.getId(), "news");
		logger.info("Se crea un nueva noticia");
	}
	public void hacerAuditoria(String mensaje, int tableId, String tableName)
	{
		AuditDAO auditDao = new AuditDAOImpl();
		Audit audit = new Audit();
		
		audit.setCreateDate(new Date());
		audit.setTableName(tableName);
		audit.setUserId(userBean.getUsuario().getId());
		audit.setUserIp(Util.darIp());
		audit.setTableId(tableId);
		audit.setOperation(mensaje);
		
		logger.info("Se crea un nuevo registro de la tabla auditoria");
		
		auditDao.save(audit);
	}
	public New getNews() {
		return news;
	}

	public void setNews(New news) {
		this.news = news;
	}
	@PostConstruct
	public void init() {
		news = new New();
	}
	public UserBean getUserBean()
	{
		return userBean;
	}

	public void setUserBean(UserBean userBean)
	{
		this.userBean = userBean;
	}
}
