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
/**
 * Descripción: Clase Bean asociado a un formulario que ncesita realizar operaciones con la tabla parameter
 * @author Laminasapp
 *
 */
@ManagedBean
@SessionScoped
public class NewBean
{
	
	final static Logger logger = Logger.getLogger(NewBean.class);
	private New news;
	private DataModel<New> listaNews;
	private DataModel<New> listaNewsActives;
	
	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;
	
	public DataModel<New> getListarNews()
	{
		List<New> lista = new NewDAOImpl().list();
		listaNews = new ListDataModel<New>(lista);
		return listaNews;
	}
	public DataModel<New> getListarNewsActives()
	{
		List<New> lista = new NewDAOImpl().listA();
		listaNewsActives = new ListDataModel<New>(lista);
		return listaNewsActives;
	}
	/**
	 * Método que se encaraga de agregar una noticia en la tabla new
	 */
	public void agregarNew()
	{
		NewDAO DAO = new NewDAOImpl();
		DAO.save(news);
		Util.darMensaje("Ha sido agregado", "Se ha agregado la nueva noticia");
		hacerAuditoria("Create", news.getId(), "news");
		logger.info("Se crea un nueva noticia");
	}
	/**
	 * Metodo que cambia el estado de una noticia
	 */
	public void cambiarEstado() {
		New n = listaNews.getRowData();
		NewDAO dao = new NewDAOImpl();
		if(n.getState().equals("A")) {
			n.setState("I");
		}else {
			n.setState("A");
		}
		System.out.println("Buena perro" + n.getLargeDescription());
		dao.update(n);
		logger.info("Se cambio el estado de una noticia");
	}
	/**
	 * Método que se encarga de realizar auditoria de todos los movimientos que el usario realize
	 * @param mensaje Accion que el usaurio realizo
	 * @param tableId Id de la tabla sobre la cual se realizo la operacióon
	 * @param tableName Nombre de la tabla sobre la cual se realizo la operación
	 */
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
	
	public New getNews()
	{
		return news;
	}
	
	public void setNews(New news)
	{
		this.news = news;
	}
	/**
	 * Método que inicializa variable de la clase
	 */
	@PostConstruct
	public void init()
	{
		news = new New();
		news.setDateNews(new Date());
		news.setState("A");
		news.setIdUser(userBean.getUsuario().getId());
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
