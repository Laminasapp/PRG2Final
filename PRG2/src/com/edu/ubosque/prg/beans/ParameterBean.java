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
import com.edu.ubosque.prg.dao.ParameterDAO;
import com.edu.ubosque.prg.dao.impl.AuditDAOImpl;
import com.edu.ubosque.prg.dao.impl.ParameterDAOImpl;
import com.edu.ubosque.prg.entity.Audit;
import com.edu.ubosque.prg.entity.Parameter;
import com.edu.ubosque.prg.util.Util;
/**
 * Descripción: Clase Bean asociado a un formulario que ncesita realizar operaciones con la tabla parameter
 * @author Laminasapp
 *
 */
@ManagedBean
@SessionScoped
public class ParameterBean {
	
	final static Logger logger = Logger.getLogger(ParameterBean.class);
	private Parameter parameter;
	private DataModel<Parameter> listaParameter;
	
	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;
	
	public DataModel<Parameter> getListaParameter() {
		List<Parameter> lista = new ParameterDAOImpl().list();
		listaParameter = new ListDataModel<Parameter>(lista);
		return listaParameter;
	}
	/**
	 * Método que se encarga de agregar en la tabla parameter
	 */
	public void agregarParametro()
	{
		ParameterDAOImpl parameterDAO = new ParameterDAOImpl();
		parameterDAO.save(parameter);
		
		Util.darMensaje("Ha sido agregado", "Se ha agregado el nuevo parametro");
		hacerAuditoria("Create", parameter.getId(), "parameter");
		logger.info("Se crea un nuevo usuario");
	}
	/**
	 * Método que se encargar de modificar un parametro
	 */
	public void modificarParametro()
	{
		ParameterDAO parameterDAO = new ParameterDAOImpl();
		parameterDAO.update(parameter);
		
		
		logger.info("Se modifica un parametro");
		hacerAuditoria("Update", parameter.getId(), "parameter");
		Util.darMensaje("Exito", "Los datos han sido actualizados");
	}
	/**
	 * Método que se encarga de hacer la auditoria de los movimientos que el usuario haga
	 * @param mensaje Accion realizada pro el usuario
	 * @param tableId Id de la tabla sobre la cual se realizo la consulta
	 * @param tableName Nombre de la tabla sobre la cual se realizo la consulta
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
	
	public void prepararModificarParametro()
	{
		parameter = (Parameter) (listaParameter.getRowData());
	}
	
	@PostConstruct
	public void nuevoParametro()
	{
		parameter = new Parameter();
	}
	
	public Parameter getParameter()
	{
		return parameter;
	}
	
	public void setParameter(Parameter parameter)
	{
		this.parameter = parameter;
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
