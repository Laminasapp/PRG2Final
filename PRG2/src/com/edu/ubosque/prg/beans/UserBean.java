package com.edu.ubosque.prg.beans;

import com.edu.ubosque.prg.dao.AuditDAO;
import com.edu.ubosque.prg.dao.UserDAO;
import com.edu.ubosque.prg.dao.impl.AuditDAOImpl;
import com.edu.ubosque.prg.dao.impl.UserDAOImpl;
import com.edu.ubosque.prg.entity.Audit;
import com.edu.ubosque.prg.entity.User;
import com.edu.ubosque.prg.util.Util;
import com.edu.ubosque.prg.util.UtilCorreo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

@ManagedBean
@SessionScoped
public class UserBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(UserBean.class);
	private User usuario;
	private DataModel<User> listaUsuarios;
	
	private String login;
	private String contra;
	
	public String verificarIngreso()
	{
		String respuesta = "prime";
		
		UserDAO dao = new UserDAOImpl();
		
		if (contra.contains("@"))
		{
			contra = Util.getStringMessageDigest(contra, Util.MD5);
			
			usuario = dao.getUsuario(login, contra);
			
			if (usuario != null && usuario.getUserType().equals("USER"))
			{
				dialogCambioContrasenia();
			}
			
			else if (usuario == null)
			{
				Util.darMensaje("", "Usuario no encontrado");
			}
		}
		
		else if (!contra.contains("@"))
		{
			contra = Util.getStringMessageDigest(contra, Util.MD5);
			usuario = dao.getUsuario(login);
			
			if (usuario != null && usuario.getUserType().equals("ADMIN") && contra.equals(usuario.getPassword()))
			{
				hacerAuditoria("Ingresar", 0, "user");
				respuesta = "indexAdmin";
			}
			
			else if (usuario != null && usuario.getUserType().equals("USER")
					&& contra.equals(usuario.getPassword()))
			{
				hacerAuditoria("Ingresar", 0, "user");
				usuario.setAttemps(0);
				respuesta = "indexUsuario";
			}
			
			else if (usuario != null && !usuario.getPassword().equals(contra)
					&& usuario.getUserType().equals("USER"))
			{
				int attempts = usuario.getAttemps() + 1;
				usuario.setAttemps(attempts);
				
				Util.darMensaje("", "Contraseña incorrecta");
				
				if (usuario.getAttemps() == 3)
				{
					usuario.setActive("I");
					UtilCorreo.enviarNuevaContrasenia(usuario.getFullName(), asignarNuevaContrasenia(), usuario.getEmailAddress());
					Util.darMensaje("Bloqueado", "Revise su correo para su nueva contraseña");
				}
				
				dao.update(usuario);
			}
			
			else if (usuario == null)
			{
				Util.darMensaje("", "Usuario no encontrado");
			}
		}
		
		return respuesta;
	}
	
	public String asignarNuevaContrasenia()
	{
		String nueva = Util.darContraseniaAleatoria();
		usuario.setPassword(Util.getStringMessageDigest(nueva, Util.MD5));
		UserDAO dao = new UserDAOImpl();
		dao.update(usuario);
		
		return nueva;
	}
	
	public void dialogCambioContrasenia()
	{
		Map<String, Object> opciones = new HashMap<String, Object>();
		opciones.put("modal", true);
		opciones.put("draggable", false);
		opciones.put("resizable", false);
		opciones.put("width", 1000);
		opciones.put("height", 400);
		opciones.put("contentWidth", "100%");
		opciones.put("contentHeight", "100%");
		opciones.put("headerElement", "customheader");
		
		PrimeFaces.current().dialog().openDynamic("cambioContrasenia", opciones, null);
	}
	
	public void hacerAuditoria(String mensaje, int tableId, String tableName)
	{
		AuditDAO auditDao = new AuditDAOImpl();
		Audit audit = new Audit();
		
		audit.setCreateDate(new Date());
		audit.setTableName(tableName);
		audit.setUserId(usuario.getId());
		audit.setUserIp(Util.darIp());
		audit.setTableId(tableId);
		audit.setOperation(mensaje);
		
		auditDao.save(audit);
	}
	
	public void cambiarContrasenia()
	{
		usuario.setPassword(Util.getStringMessageDigest(contra, Util.MD5));
		UserDAO dao = new UserDAOImpl();
		dao.update(usuario);
		
		PrimeFaces.current().dialog().closeDynamic(PrimeFaces.current().dialog());
	}
	
	public String cambiarEstado()
	{
		User elUsuario = (User) (listaUsuarios.getRowData());
		UserDAO dao = new UserDAOImpl();
		
		if (elUsuario.getActive().equals("A"))
		{
			elUsuario.setActive("I");
		}
		
		else
		{
			elUsuario.setActive("A");
		}
		
		dao.update(elUsuario);
		
		return "indexAdmin";
	}
	
	public String prepararAdicionarUsuario()
	{
		String respuesta = "registroUsuarios";
		
		nuevoUsuario();
		
		return respuesta;
	}
	
	public String adicionarUsuario()
	{
		UserDAO dao = new UserDAOImpl();
		dao.save(usuario);
		
		String contrasenia = asignarNuevaContrasenia();
		
		String nombre = usuario.getFullName();
		String correo = usuario.getEmailAddress();
		
		UtilCorreo.enviarPrimeraContrasenia(nombre, contrasenia, correo);
		
		Util.darMensaje("Ha sido registrado", "Revise su correo para su contraseña");
		
		hacerAuditoria("Crear", 1, "user");
		
		nuevoUsuario();
		
		return "prime";
	}
	
	public void prepararModificarUsuario()
	{
		usuario = (User) (listaUsuarios.getRowData());
		
		Map<String, Object> opciones = new HashMap<String, Object>();
		opciones.put("modal", true);
		opciones.put("draggable", false);
		opciones.put("resizable", false);
		opciones.put("width", 640);
		opciones.put("height", 340);
		opciones.put("contentWidth", "100%");
		opciones.put("contentHeight", "100%");
		opciones.put("headerElement", "customheader");
		
		PrimeFaces.current().dialog().openDynamic("registroUsuariosAdmin", opciones, null);
	}
	
	public void modificarUsuario()
	{
		UserDAO dao = new UserDAOImpl();
		dao.update(usuario);
		
		PrimeFaces.current().dialog().closeDynamic(PrimeFaces.current().dialog());
	}
	
	@PostConstruct
	public void nuevoUsuario()
	{
		usuario = new User();
		usuario.setActive("A");
		usuario.setDateLastPassword(new Date());
		usuario.setUserType("USER");
	}
	
	public void recuperarContrasenia()
	{
		UserDAO dao = new UserDAOImpl();
		String correo = usuario.getEmailAddress();
		usuario = dao.getUsuario(correo);
		
		if(usuario != null)
		{
			String nombre = usuario.getFullName();
			String contraseña = asignarNuevaContrasenia();
			UtilCorreo.enviarNuevaContrasenia(nombre, contraseña, correo);	
		}
		
		nuevoUsuario();
	}
	
	public String getLogin()
	{
		return login;
	}
	
	public void setLogin(String login)
	{
		this.login = login;
	}
	
	public String getContra()
	{
		return contra;
	}
	
	public void setContra(String contra)
	{
		this.contra = contra;
	}
	
	public String darInicio()
	{
		return "prime";
	}
	
	public User getUsuario()
	{
		return usuario;
	}
	
	public void setUsuario(User usuario)
	{
		this.usuario = usuario;
	}
	
	public DataModel<User> getListarUsuarios()
	{
		List<User> lista = new UserDAOImpl().list();
		listaUsuarios = new ListDataModel<User>(lista);
		return listaUsuarios;
	}
	
}
