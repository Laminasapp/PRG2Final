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
import com.edu.ubosque.prg.dao.RepeatedsheetDAO;
import com.edu.ubosque.prg.dao.impl.AuditDAOImpl;
import com.edu.ubosque.prg.dao.impl.RepeatedsheetDAOImpl;
import com.edu.ubosque.prg.entity.Audit;
import com.edu.ubosque.prg.entity.Repeatedsheet;
import com.edu.ubosque.prg.util.Util;
/**
 * Descripción: Clase Bean asociada a los formularios que necesitan la tabla repeatedsheet
 *
 */
@ManagedBean
@SessionScoped
public class RepeatedsheetBean {

	final static Logger logger = Logger.getLogger(RepeatedsheetBean.class);
	private Repeatedsheet repeatedsheet;
	private DataModel<Repeatedsheet> listaRepeatedsheet;
	
	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;
	/**
	 * Método que adiciona laminas repetidas a la tabla repeatedsheets
	 */
	public void adicionarRepeatedsheet() {
		RepeatedsheetDAO dao = new RepeatedsheetDAOImpl();
		repeatedsheet.setUserId(userBean.getUsuario().getId());
		dao.save(repeatedsheet);
		
		hacerAuditoria("Create", repeatedsheet.getId(), "repeatedsheets");
		logger.info("Se adiciona una nueva lamina repetida");
	}
	/**
	 * Método que se encarga de hacer la auditoria de las acciones realizadas por el usuario
	 * @param mensaje Accion realiazada por el usuario
	 * @param tableId Id de la tabla en la que se realizo la consulta
	 * @param tableName Nombre de la tabla en la que se realizo la consulta
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
	
	public DataModel<Repeatedsheet> getListarRepeatedsheet() {
		List<Repeatedsheet> lista = new RepeatedsheetDAOImpl().listLaminas(userBean.getUsuario().getId());
		listaRepeatedsheet = new ListDataModel<Repeatedsheet>(lista);
		return listaRepeatedsheet;
	}
	
	@PostConstruct
	public void init(){
		repeatedsheet = new Repeatedsheet();
	}

	public Repeatedsheet getRepeatedsheet() {
		return repeatedsheet;
	}

	public void setRepeatedsheet(Repeatedsheet repeatedsheet) {
		this.repeatedsheet = repeatedsheet;
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
