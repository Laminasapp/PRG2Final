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
		Missingsheet[] tmp = new Missingsheet[671];
		faltantes = new ArrayList<Missingsheet>();
		List<Missingsheet> lista = new MissingsheetDAOImpl().list( user.getUsuario().getId()+"" );

		for(int i = 0; i < lista.size(); i++) {

			tmp[lista.get(i).getNumberSheets()]=lista.get(i);

		}
		for (int i = 1; i < tmp.length; i++) {
			if(tmp[i] == null) {
				Missingsheet ms = new Missingsheet();
				ms.setId(user.getUsuario().getId());
				ms.setUserId(user.getUsuario().getId());
				ms.setNumberSheets(i);
				ms.setCountSheets(0);
				faltantes.add(ms);
			}else {
				faltantes.add(tmp[i]);
			}

		}
		listarUsuarios();
	}

	public List<Missingsheet> getFaltantes() {
		return faltantes;
	}

	public void setFaltantes(List<Missingsheet> faltantes) {
		this.faltantes = faltantes;
	}

	public String adicionarMissingsheet() {

		Missingsheet ms = selectedMissingSheet;

		MissingsheetDAO dao = new MissingsheetDAOImpl();

		if(dao.getMissingsheet(ms.getUserId(), ms.getNumberSheets())!=null) {
			ms.setCountSheets(0);
			dao.update(ms);

		}else {
			ms.setCountSheets(1);
			dao.save(ms);
		}


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
