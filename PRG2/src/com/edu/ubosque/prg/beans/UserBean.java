package com.edu.ubosque.prg.beans;

import com.edu.ubosque.prg.dao.AuditDAO;
import com.edu.ubosque.prg.dao.MissingsheetDAO;
import com.edu.ubosque.prg.dao.ParameterDAO;
import com.edu.ubosque.prg.dao.UserDAO;
import com.edu.ubosque.prg.dao.impl.AuditDAOImpl;
import com.edu.ubosque.prg.dao.impl.MissingsheetDAOImpl;
import com.edu.ubosque.prg.dao.impl.ParameterDAOImpl;
import com.edu.ubosque.prg.dao.impl.UserDAOImpl;
import com.edu.ubosque.prg.entity.Audit;
import com.edu.ubosque.prg.entity.User;
import com.edu.ubosque.prg.util.Util;
import com.edu.ubosque.prg.util.UtilCorreo;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;
/**
 * Descripcion: Clase asociada al formulario de ingreso a la pagina 
 *
 */
@ManagedBean (name="userBean")
@SessionScoped
public class UserBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(UserBean.class);
	private User usuario;
	private User usuarioTemporal;
	private DataModel<User> listaUsuarios;

	private String login;
	private String contra;
	/**
	 * Método que verifca el ingreso de un usurio se corrrecto en el sistema
	 * @return
	 */
	public String verificarIngreso()
	{
		ParameterDAO x = new ParameterDAOImpl();		
		int dias = x.getParameter("C").getNumberValue();

		String respuesta = "prime";

		UserDAO dao = new UserDAOImpl();

		if (contra.contains("@"))
		{
			contra = Util.getStringMessageDigest(contra, Util.MD5);

			usuario = dao.getUsuario(login, contra);

			if (usuario != null)
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
			usuarioTemporal = dao.getUsuario(login);

			if (usuario != null && usuario.getUserType().equals("ADMIN") && contra.equals(usuario.getPassword()))
			{
				hacerAuditoria("Ingresar", 0, "user");
				respuesta = "indexAdmin";
			}

			else if (usuario != null && usuario.getUserType().equals("USER") && contra.equals(usuario.getPassword()))
			{	
				if(obligarCambioContraseña(dias)) {
					usuario.setActive("I");
					UtilCorreo.enviarNuevaContrasenia(usuario.getFullName(), asignarNuevaContrasenia(),
							usuario.getEmailAddress());
					Util.darMensaje("Bloqueado", "Revise su correo para su nueva contraseña");
				}else {
					hacerAuditoria("Ingresar", 0, "user");
					usuario.setAttemps(0);
					respuesta = "indexUsuario";
				}
			}

			else if (usuario != null && !usuario.getPassword().equals(contra) && usuario.getUserType().equals("USER"))
			{
				int attempts = usuario.getAttemps() + 1;
				usuario.setAttemps(attempts);
				hacerAuditoria("IngFallo", 0, "user");
				Util.darMensaje("", "Contraseña incorrecta");

				if (usuario.getAttemps() == 3)
				{
					usuario.setActive("I");
					UtilCorreo.enviarNuevaContrasenia(usuario.getFullName(), asignarNuevaContrasenia(),
							usuario.getEmailAddress());
					Util.darMensaje("Bloqueado", "Revise su correo para su nueva contraseña");
				}

				dao.update(usuario);
			}

			else if (usuario == null)
			{
				Util.darMensaje("", "Usuario no encontrado");
			}
		}
		logger.info("Se verifica el ingreso de un usuario");
		return respuesta;
	}
	/**
	 * Método que obliga a generar una contraseña aleatoria
	 * @return Contraseñan que de devuelve 
	 */
	@SuppressWarnings("deprecation")
	public boolean obligarCambioContraseña(int dias) {
		Date actual = new Date();
		Date fecha = usuario.getDateLastPassword();
		boolean rta = false;
		int actualDia = actual.getDate();
		int fechalDia = fecha.getDate();
		if(actualDia>fechalDia) {
			int param = actualDia - fechalDia;
			System.out.println(param+" - " + dias);
			if(param>=dias) {
				rta = true;
			}
		}
		return rta;
	}
	/**
	 * Método que genera un contraseña aleatoria
	 * @return Contraseñan que de devuelve 
	 */
	public String asignarNuevaContrasenia()
	{
		String nueva = Util.darContraseniaAleatoria();
		usuario.setPassword(Util.getStringMessageDigest(nueva, Util.MD5));
		usuario.setActive("A");
		usuario.setAttemps(0);
		usuario.setDateLastPassword(new Date());
		UserDAO dao = new UserDAOImpl();
		dao.update(usuario);
		logger.info("Se asigna una nueva contraseña");
		return nueva;
	}
	/**
	 * Método que se encarga de enviar la auditoria a la tabla audit
	 * @param mensaje Mensaje que se muestra en la auditoria
	 * @param tableId  Id de la tabla sobre la que se realizo la consulta
	 * @param tableName Nombre de la tabla
	 */
	public void dialogCambioContrasenia()
	{
		Map<String, Object> opciones = new HashMap<String, Object>();
		opciones.put("modal", true);
		opciones.put("draggable", false);
		opciones.put("resizable", false);
		opciones.put("closable", false);
		opciones.put("width", 1000);
		opciones.put("height", 400);
		opciones.put("contentWidth", "100%");
		opciones.put("contentHeight", "100%");
		opciones.put("headerElement", "customheader");

		PrimeFaces.current().dialog().openDynamic("cambioContrasenia", opciones, null);
	}
	/**
	 * Método que se encarga de enviar la auditoria a la tabla audit
	 * @param mensaje Mensaje que se muestra en la auditoria
	 * @param tableId  Id de la tabla sobre la que se realizo la consulta
	 * @param tableName Nombre de la tabla
	 */
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

		logger.info("Se crea un nuevo registro de la tabla auditoria");

		auditDao.save(audit);
	}
	/**
	 * Método que cambia la contraseña de un usaurio
	 */
	public void cambiarContrasenia()
	{
		usuario.setPassword(Util.getStringMessageDigest(contra, Util.MD5));
		usuario.setDateLastPassword(new Date());
		UserDAO dao = new UserDAOImpl();
		dao.update(usuario);

		hacerAuditoria("Update", usuario.getId(), "user");
		logger.info("Se cambia la contraseña del usuario");
		PrimeFaces.current().dialog().closeDynamic(PrimeFaces.current().dialog());
	}
	/**
	 * Método que cambia el estado de un usuaruio
	 * @return Devuelve el tipo de cambio que se realizara
	 */
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
			UtilCorreo.enviarNuevaContrasenia(usuario.getFullName(), asignarNuevaContrasenia(),
					usuario.getEmailAddress());
			elUsuario.setAttemps(0);
		}

		hacerAuditoria("Estado", elUsuario.getId(), "user");
		logger.info("Se cambia el estado del usuario");

		dao.update(elUsuario);

		return "indexAdmin";
	}
	/**
	 *Método que prepara para la agregacion de un usuario
	 * @return Objeto que contiene la direccion de la pagina a la que sera redirigida
	 */
	public String prepararAdicionarUsuario()
	{
		String respuesta = "registroUsuarios";

		nuevoUsuario();

		return respuesta;
	}
	/**
	 * Método que se encarga de agregar un usuario
	 * @return Objeto que devuelve la pagina a la que sera redireccionado
	 */
	public String adicionarUsuario()
	{
		String pagina = null;

		UserDAO dao = new UserDAOImpl();

		if(dao.userName(this.usuario.getUserName())) {

			dao.save(usuario);

			MissingsheetDAO ms = new MissingsheetDAOImpl();

			ms.save(usuario.getId());

			String contrasenia = asignarNuevaContrasenia();

			String nombre = usuario.getFullName();
			String correo = usuario.getEmailAddress();

			UtilCorreo.enviarPrimeraContrasenia(nombre, contrasenia, correo);

			Util.darMensaje("Ha sido registrado", "Revise su correo para su contraseña");

			hacerAuditoria("Crear", usuario.getId(), "user");
			logger.info("Se crea un nuevo usuario");
			nuevoUsuario();

			pagina = "prime";
		}

		else {

			Util.darMensaje("Warning", "Nombre de usuario ya existe");

		}

		return pagina;
	}
	/**
	 * Método que prepara la modificón de un usuario
	 */
	public void prepararModificarUsuario()
	{
		usuarioTemporal = (User) (listaUsuarios.getRowData());
	}
	/**
	 * Método que modifica a un usuario
	 */
	public void modificarUsuario()
	{
		UserDAO dao = new UserDAOImpl();
		dao.update(usuarioTemporal);
		logger.info("Se modifica un usario");
		hacerAuditoria("Update", usuarioTemporal.getId(), "user");
		Util.darMensaje("Exito", "Los datos han sido actualizados");
	}
	/**
	 * Método que inicializa variables de la clase
	 */
	@PostConstruct
	public void nuevoUsuario()
	{
		usuario = new User();
		usuario.setActive("A");
		usuario.setDateLastPassword(new Date());
		usuario.setUserType("USER");
	}
	/**
	 * Método que recupera la contraseña de un usario
	 */
	public void recuperarContrasenia()
	{
		UserDAO dao = new UserDAOImpl();
		String correo = usuario.getEmailAddress();
		usuario = dao.getUsuario(correo);

		if (usuario != null)
		{
			String nombre = usuario.getFullName();
			String contraseña = asignarNuevaContrasenia();
			UtilCorreo.enviarNuevaContrasenia(nombre, contraseña, correo);
		}

		logger.info("Se recupero la contraseña de un nuevo usuario");
		nuevoUsuario();
	}

	public void enviarSms() 
	{
		String userName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("userName");

		UserDAO dao = new UserDAOImpl();
		User usuTmp = dao.getUsuario(userName);
		UtilCorreo.enviarSMS(usuTmp.getPhoneNumber(), "Me contacto contigo mediante LaminasApp Mi usuario es: " + usuario.getUserName() +" Espero tu contacto");

		logger.info("Se envio un mensaje por celular");
	}

	public void enviarCorreo() 
	{
		String userName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("userName");

		UserDAO dao = new UserDAOImpl();
		User usuTmp = dao.getUsuario(userName);

		String cuerpo = "Hola! Soy: "+usuario.getUserName()+" me pongo en contacto contigo mediante la aplicacion laminasApp para llegar un acuerdo, Espero tu respuesta a: " + usuario.getEmailAddress();
		UtilCorreo.enviarMensaje("Contacto mediante LaminasApp", cuerpo, usuTmp.getEmailAddress());

		logger.info("Se envio correo");
	}

	public void pdf()
	{
		try
		{
			File file = new File("ListaUsuario.pdf");
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(file));

			document.open();

			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			String logo = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "img" + File.separator + "prime" + File.separator + "Logo.jpg";
			Image image = Image.getInstance(logo);
			image.scalePercent(25);
			image.setAlignment(Element.ALIGN_CENTER);

			Date fecha = new Date();
			String text = " \n Archivo creado por: " + usuario.getFullName() + ", Fecha: " + fecha ;
			Paragraph paragraph = new Paragraph(text);
			paragraph.setAlignment(Element.ALIGN_CENTER);

			document.add(image);
			document.add(paragraph);
			document.add(new Paragraph(" "));

			PdfPTable table = new PdfPTable(8);
			table.setWidthPercentage(100);

			PdfPCell cell1 = new PdfPCell(new Paragraph("Id"));
			cell1.setBackgroundColor(new Color(255, 102, 102));
			PdfPCell cell2 = new PdfPCell(new Paragraph("Usuario"));
			cell2.setBackgroundColor(new Color(255, 102, 102));
			PdfPCell cell3 = new PdfPCell(new Paragraph("Nombre"));
			cell3.setBackgroundColor(new Color(255, 102, 102));
			PdfPCell cell4 = new PdfPCell(new Paragraph("Correo"));
			cell4.setBackgroundColor(new Color(255, 102, 102));
			PdfPCell cell5 = new PdfPCell(new Paragraph("Telefono"));
			cell5.setBackgroundColor(new Color(255, 102, 102));
			PdfPCell cell6 = new PdfPCell(new Paragraph("Ultima Fecha Contraseña"));
			cell6.setBackgroundColor(new Color(255, 102, 102));
			PdfPCell cell7 = new PdfPCell(new Paragraph("Tipo de Usuario"));
			cell7.setBackgroundColor(new Color(255, 102, 102));
			PdfPCell cell8 = new PdfPCell(new Paragraph("Estado"));
			cell8.setBackgroundColor(new Color(255, 102, 102));

			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);
			table.addCell(cell5);
			table.addCell(cell6);
			table.addCell(cell7);
			table.addCell(cell8);

			for (User data : listaUsuarios)
			{
				cell1 = new PdfPCell(new Paragraph(data.getId() + ""));
				cell2 = new PdfPCell(new Paragraph(data.getUserName()));
				cell3 = new PdfPCell(new Paragraph(data.getFullName()));
				cell4 = new PdfPCell(new Paragraph(data.getEmailAddress()));
				cell5 = new PdfPCell(new Paragraph(data.getPhoneNumber()));
				cell6 = new PdfPCell(new Paragraph(data.getDateLastPassword() + ""));
				cell7 = new PdfPCell(new Paragraph(data.getUserType()));
				cell8 = new PdfPCell(new Paragraph(data.getActive()));

				table.addCell(cell1);
				table.addCell(cell2);
				table.addCell(cell3);
				table.addCell(cell4);
				table.addCell(cell5);
				table.addCell(cell6);
				table.addCell(cell7);
				table.addCell(cell8);
			}

			document.add(table);
			document.close();

			Desktop.getDesktop().open(file);
		}

		catch (DocumentException | IOException e)
		{
			e.printStackTrace();
		}

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

	public DataModel<User> getListaUsuarios()
	{
		List<User> lista = new UserDAOImpl().list();
		listaUsuarios = new ListDataModel<User>(lista);
		return listaUsuarios;
	}

	public User getUsuarioTemporal()
	{
		return usuarioTemporal;
	}

	public void setUsuarioTemporal(User usuarioTemporal)
	{
		this.usuarioTemporal = usuarioTemporal;
	}
}
