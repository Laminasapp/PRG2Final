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

@ManagedBean
@SessionScoped
public class RepeatedsheetBean {

	final static Logger logger = Logger.getLogger(RepeatedsheetBean.class);
	private Repeatedsheet repeatedsheet;
	private DataModel<Repeatedsheet> listaRepeatedsheet;
	
	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;
	
	public void adicionarRepeatedsheet() {
		RepeatedsheetDAO dao = new RepeatedsheetDAOImpl();
		repeatedsheet.setUserId(userBean.getUsuario().getId());
		dao.save(repeatedsheet);
		
		hacerAuditoria("Create", repeatedsheet.getId(), "repeatedsheets");
		logger.info("Se adiciona una nueva lamina repetida");
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
	
	public DataModel<Repeatedsheet> getListarRepeatedsheet() {
		List<Repeatedsheet> lista = new RepeatedsheetDAOImpl().list();
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
