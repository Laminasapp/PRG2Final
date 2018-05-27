package com.edu.ubosque.prg.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.apache.log4j.Logger;

import com.edu.ubosque.prg.dao.MissingsheetDAO;
import com.edu.ubosque.prg.dao.RepeatedsheetDAO;
import com.edu.ubosque.prg.dao.UserDAO;
import com.edu.ubosque.prg.dao.impl.MissingsheetDAOImpl;
import com.edu.ubosque.prg.dao.impl.RepeatedsheetDAOImpl;
import com.edu.ubosque.prg.dao.impl.UserDAOImpl;
import com.edu.ubosque.prg.entity.Missingsheet;
import com.edu.ubosque.prg.entity.Repeatedsheet;
import com.edu.ubosque.prg.entity.User;

@ManagedBean
public class MissingsheetBean {

	final static Logger logger = Logger.getLogger(MissingsheetBean.class);

	private Missingsheet missingSheet;
	private DataModel listaMissingsheet;

	private List<Missingsheet> faltantes;

	private List<User> usuarios;

	private Missingsheet selectedMissingSheet;

	@ManagedProperty("#{userBean}")
	private UserBean user;

	@PostConstruct
	public void init() {
		faltantes = new ArrayList<Missingsheet>();
		//aca sse blokea la consulta
		listarUsuarios();
	}

	public List<Missingsheet> getFaltantes() {
		return faltantes;
	}

	public void setFaltantes(List<Missingsheet> faltantes) {
		this.faltantes = faltantes;
	}

	public String adicionarMissingsheet() {
		return "indexUsuario";
	}

	public void listarUsuarios() {

		List<User> usuarios = new ArrayList<User>();

		// Obtengo el Id del usuario que ingreso.
		int idUsuario = user.getUsuario().getId();
	
		// Se obtienen las laminas que le faltan al usuario.
		MissingsheetDAO daoM = new MissingsheetDAOImpl();
		List <Missingsheet> faltantes = daoM.list(idUsuario);

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
		this.usuarios = usuarios;
	}

	public Missingsheet getSelectedMissingSheet() {
		return selectedMissingSheet;
	}

	public void setSelectedMissingSheet(Missingsheet selectedMissingSheet) {
		this.selectedMissingSheet = selectedMissingSheet;
	}

	public DataModel getListarMissingsheet() {
		List<Missingsheet> lista = new MissingsheetDAOImpl().list( user.getUsuario().getId()+"" );
		listaMissingsheet = new ListDataModel(lista);
		return listaMissingsheet;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public List<User> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<User> usuarios) {
		this.usuarios = usuarios;
	}

}
