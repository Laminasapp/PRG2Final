package com.edu.ubosque.prg.beans;

import java.util.ArrayList;
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
import com.edu.ubosque.prg.dao.MissingsheetDAO;
import com.edu.ubosque.prg.dao.RepeatedsheetDAO;
import com.edu.ubosque.prg.dao.UserDAO;
import com.edu.ubosque.prg.dao.impl.AuditDAOImpl;
import com.edu.ubosque.prg.dao.impl.MissingsheetDAOImpl;
import com.edu.ubosque.prg.dao.impl.RepeatedsheetDAOImpl;
import com.edu.ubosque.prg.dao.impl.UserDAOImpl;
import com.edu.ubosque.prg.entity.Audit;
import com.edu.ubosque.prg.entity.Missingsheet;
import com.edu.ubosque.prg.entity.Repeatedsheet;
import com.edu.ubosque.prg.entity.User;
import com.edu.ubosque.prg.util.Util;
/**
 * Descripcion: CLase Bean que se vincula a un formulario el cual realiza consultas en la tabla missingsheet
 *
 */
@ManagedBean
@SessionScoped
public class MissingsheetBean {
	final static Logger logger = Logger.getLogger(MissingsheetBean.class);

	private Missingsheet missingSheet;
	private DataModel<Missingsheet> listaMissingsheet;

	private List<Missingsheet> faltantes;

	private List<User> usuarios;

	private List<User> laminasAVender;

	private List<User> llenaron;

	private Missingsheet selectedMissingSheet;

	@ManagedProperty(value = "#{userBean}")
	private UserBean usuario;
	/**
	 * M�todo que incializa variables necesarias para el formilario
	 */
	@PostConstruct
	public void init() {
		faltantes = new ArrayList<Missingsheet>();
		listarUsuarios();
		venderLaminas();
		albumLleno();
	}

	public List<Missingsheet> getFaltantes() {
		return faltantes;
	}

	public void setFaltantes(List<Missingsheet> faltantes) {
		this.faltantes = faltantes;
	}
	/**
	 * M�todo que se encarga de agregar laminas repetidas en la tabla
	 */
	public void adicionarMissingsheet() {
		MissingsheetDAO dao = new MissingsheetDAOImpl();
		Missingsheet ms = selectedMissingSheet;

		if (ms.getCountSheets() == 1) {
			ms.setCountSheets(0);
		}

		else {
			ms.setCountSheets(1);
		}

		hacerAuditoria("Update", ms.getId(), "missingsheets");
		logger.info("Se cambia el estado de la lamina");
		dao.update(ms);
	}
	/**
	 * M�todo que se encarga de hacer auditoria a los movimierntos realizados por los usuarios
	 * @param mensaje Accion realizada por el usuario
	 * @param tableId Id de a tabla sobre la cual se realizo la acci�n
	 * @param tableName Nombre de la tabla sobre la cual se realizo la acci�n
	 */
	public void hacerAuditoria(String mensaje, int tableId, String tableName) {
		AuditDAO auditDao = new AuditDAOImpl();
		Audit audit = new Audit();

		audit.setCreateDate(new Date());
		audit.setTableName(tableName);
		audit.setUserId(usuario.getUsuario().getId());
		audit.setUserIp(Util.darIp());
		audit.setTableId(tableId);
		audit.setOperation(mensaje);

		logger.info("Se crea un nuevo registro de la tabla auditoria");

		auditDao.save(audit);
	}

	/**
	 * M�todo que genera una lista de los usuarios que tienen laminas que le sirvern a un usuario
	 */
	public void listarUsuarios() {
		List<User> usuarios = new ArrayList<User>();

		// Obtengo el Id del usuario que ingreso.
		int idUsuario = usuario.getUsuario().getId();

		// Se obtienen las laminas que le faltan al usuario.
		MissingsheetDAO daoM = new MissingsheetDAOImpl();
		List<Missingsheet> faltantes = daoM.list(idUsuario);

		// Se Busca en las repetidas.
		UserDAO user = new UserDAOImpl();
		RepeatedsheetDAO daoR = new RepeatedsheetDAOImpl();
		for (int i = 0; i < faltantes.size(); i++) {

			List<Repeatedsheet> laminas = daoR.list(faltantes.get(i).getNumberSheets());

			// Se agregan a los usuarios.
			for (int j = 0; j < laminas.size(); j++) {

				int tmp = laminas.get(j).getUserId();
				usuarios.add(user.getUsuario(tmp));

			}
		}
		logger.info("Se realiza una consulta en la base de datos");
		this.usuarios = usuarios;
	}
	/**
	 * M�todo que genera un lista de las laminas que puedo vender buscando los usuarios necesitados
	 */
	public void venderLaminas() {

		List<User> vender = new ArrayList<User>();

		// Obtengo el Id del usuario que ingreso.
		int idUsuario = usuario.getUsuario().getId();

		// Se obtienen las laminas repetidas del usuario
		RepeatedsheetDAO daoR = new RepeatedsheetDAOImpl();
		List<Repeatedsheet> laminasRepetidas = daoR.repetidasUsuario(idUsuario);

		// se buscan a los usuarios que le voy a vender
		UserDAO user = new UserDAOImpl();
		MissingsheetDAO daoM = new MissingsheetDAOImpl();

		for (int i = 0; i < laminasRepetidas.size(); i++) {

			List<Missingsheet> faltantes = daoM.listaLaminas(laminasRepetidas.get(i).getNumberSheets());

			// ahora se buscan los usuarios

			for (int j = 0; j < faltantes.size(); j++) {

				int tmp = faltantes.get(j).getUserId();
				vender.add(user.getUsuario(tmp));
			}

		}
		logger.info("Se realiza una consulta en la base de datos");
		laminasAVender = vender;
	}

	/**
	 * M�todo que muestra los usarios que ya llenaron el �lbum 
	 */
	public void albumLleno() {

		List<User> completaron = new ArrayList<User>();

		UserDAO user = new UserDAOImpl();
		List<User> listaUsuarios = new ArrayList<User>();

		listaUsuarios = user.list();

		MissingsheetDAO daoM = new MissingsheetDAOImpl();

		for (int i = 0; i < listaUsuarios.size(); i++) {

			if (daoM.lleno(listaUsuarios.get(i).getId())) {
				completaron.add(listaUsuarios.get(i));
			}
		}
		logger.info("Se realiza una consulta en la base de datos");
		this.llenaron = completaron;
	}

	public List<User> getLlenaron() {
		return llenaron;
	}

	public void setLlenaron(List<User> llenaron) {
		this.llenaron = llenaron;
	}

	public Missingsheet getSelectedMissingSheet() {
		return selectedMissingSheet;
	}

	public void setSelectedMissingSheet(Missingsheet selectedMissingSheet) {
		this.selectedMissingSheet = selectedMissingSheet;
	}

	public DataModel<Missingsheet> getListarMissingsheet() {
		List<Missingsheet> lista = new MissingsheetDAOImpl().list(usuario.getUsuario().getId() + "");
		listaMissingsheet = new ListDataModel<Missingsheet>(lista);
		return listaMissingsheet;
	}

	public UserBean getUsuario() {
		return usuario;
	}

	public void setUsuario(UserBean usuario) {
		this.usuario = usuario;
	}

	public List<User> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<User> usuarios) {
		this.usuarios = usuarios;
	}

	public Missingsheet getMissingSheet() {
		return missingSheet;
	}

	public void setMissingSheet(Missingsheet missingSheet) {
		this.missingSheet = missingSheet;
	}

	public DataModel<Missingsheet> getListaMissingsheet() {
		return listaMissingsheet;
	}

	public void setListaMissingsheet(DataModel<Missingsheet> listaMissingsheet) {
		this.listaMissingsheet = listaMissingsheet;
	}

	public List<User> getLaminasAVender() {
		return laminasAVender;
	}

	public void setLaminasAVender(List<User> laminasAVender) {
		this.laminasAVender = laminasAVender;
	}

}
