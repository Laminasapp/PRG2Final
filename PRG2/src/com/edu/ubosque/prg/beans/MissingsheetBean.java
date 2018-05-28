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

@ManagedBean
@SessionScoped
public class MissingsheetBean
{
	final static Logger logger = Logger.getLogger(MissingsheetBean.class);
	
	private Missingsheet missingSheet;
	private DataModel<Missingsheet> listaMissingsheet;
	
	private List<Missingsheet> faltantes;
	private List<User> laminasAVender;
	private List<User> usuarios;
	
	private Missingsheet selectedMissingSheet;
	
	@ManagedProperty(value = "#{userBean}")
	private UserBean usuario;
	
	@PostConstruct
	public void init()
	{
		faltantes = new ArrayList<Missingsheet>();
		listarUsuarios();
	}
	
	public List<Missingsheet> getFaltantes()
	{
		return faltantes;
	}
	
	public void setFaltantes(List<Missingsheet> faltantes)
	{
		this.faltantes = faltantes;
	}
	
	public void adicionarMissingsheet()
	{
		MissingsheetDAO dao = new MissingsheetDAOImpl();
		Missingsheet ms = selectedMissingSheet;
		
		if (ms.getCountSheets() == 1)
		{
			ms.setCountSheets(0);
		}
		
		else
		{
			ms.setCountSheets(1);
		}
		
		hacerAuditoria("Update", ms.getId(), "missingsheets");
		logger.info("Se cambia el estado de la lamina");
		dao.update(ms);
	}
	
	public void hacerAuditoria(String mensaje, int tableId, String tableName)
	{
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
	
	public void listarUsuarios()
	{
		List<User> usuarios = new ArrayList<User>();
		
		// Obtengo el Id del usuario que ingreso.
		int idUsuario = usuario.getUsuario().getId();
		
		// Se obtienen las laminas que le faltan al usuario.
		MissingsheetDAO daoM = new MissingsheetDAOImpl();
		List<Missingsheet> faltantes = daoM.list(idUsuario);
		
		// Se Busca en las repetidas.
		UserDAO user = new UserDAOImpl();
		RepeatedsheetDAO daoR = new RepeatedsheetDAOImpl();
		for (int i = 0; i < faltantes.size(); i++)
		{
			
			List<Repeatedsheet> laminas = daoR.list(faltantes.get(i).getNumberSheets());
			
			// Se agregan a los usuarios.
			for (int j = 0; j < laminas.size(); j++)
			{
				
				int tmp = laminas.get(j).getUserId();
				usuarios.add(user.getUsuario(tmp));
				
			}
		}
		this.usuarios = usuarios;
	}
	
	
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

		this.laminasAVender = vender;
	}

	public List<User> albumLleno() {
		
		List<User> users = new ArrayList<User>();
		
		
		
		
		return null;
	}
	
	public Missingsheet getSelectedMissingSheet()
	{
		return selectedMissingSheet;
	}
	
	public void setSelectedMissingSheet(Missingsheet selectedMissingSheet)
	{
		this.selectedMissingSheet = selectedMissingSheet;
	}
	
	public DataModel<Missingsheet> getListarMissingsheet()
	{
		List<Missingsheet> lista = new MissingsheetDAOImpl().list(usuario.getUsuario().getId() + "");
		listaMissingsheet = new ListDataModel<Missingsheet>(lista);
		return listaMissingsheet;
	}
	
	public UserBean getUsuario()
	{
		return usuario;
	}
	
	public void setUsuario(UserBean usuario)
	{
		this.usuario = usuario;
	}
	
	public List<User> getUsuarios()
	{
		return usuarios;
	}
	
	public void setUsuarios(List<User> usuarios)
	{
		this.usuarios = usuarios;
	}
	
	public Missingsheet getMissingSheet()
	{
		return missingSheet;
	}
	
	public void setMissingSheet(Missingsheet missingSheet)
	{
		this.missingSheet = missingSheet;
	}

	public List<User> getLaminasAVender()
	{
		return laminasAVender;
	}

	public void setLaminasAVender(List<User> laminasAVender)
	{
		this.laminasAVender = laminasAVender;
	}
	
}
