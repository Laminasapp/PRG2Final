package com.edu.ubosque.prg.beans;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
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
	
	public void pdf()
	{
		try
		{
			File file = new File("ListaParameter.pdf");
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(file));

			document.open();

			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			String logo = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "img" + File.separator + "prime" + File.separator + "Logo.jpg";
			Image image = Image.getInstance(logo);
			image.scalePercent(25);
			image.setAlignment(Element.ALIGN_CENTER);

			Date fecha = new Date();
			String text = " \n Archivo creado por: " + userBean.getUsuario().getFullName() + ", Fecha: " + fecha ;
			Paragraph paragraph = new Paragraph(text);
			paragraph.setAlignment(Element.ALIGN_CENTER);

			document.add(image);
			document.add(paragraph);
			document.add(new Paragraph(" "));

			PdfPTable table = new PdfPTable(6);

			PdfPCell cell1 = new PdfPCell(new Paragraph("Id"));
			cell1.setBackgroundColor(new Color(255, 102, 102));
			PdfPCell cell2 = new PdfPCell(new Paragraph("Tipo de parametro"));
			cell2.setBackgroundColor(new Color(255, 102, 102));
			PdfPCell cell3 = new PdfPCell(new Paragraph("Codigo"));
			cell3.setBackgroundColor(new Color(255, 102, 102));
			PdfPCell cell4 = new PdfPCell(new Paragraph("Descripción"));
			cell4.setBackgroundColor(new Color(255, 102, 102));
			PdfPCell cell5 = new PdfPCell(new Paragraph("Valor texto"));
			cell5.setBackgroundColor(new Color(255, 102, 102));
			PdfPCell cell6 = new PdfPCell(new Paragraph("Valor numerico"));
			cell6.setBackgroundColor(new Color(255, 102, 102));

			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);
			table.addCell(cell5);
			table.addCell(cell6);

			for (Parameter data : listaParameter)
			{
				cell1 = new PdfPCell(new Paragraph(data.getId() + ""));
				cell2 = new PdfPCell(new Paragraph(data.getParameterType()));
				cell3 = new PdfPCell(new Paragraph(data.getParameterCode()));
				cell4 = new PdfPCell(new Paragraph(data.getDescriptionParameter()));
				cell5 = new PdfPCell(new Paragraph(data.getTextValue()));
				cell6 = new PdfPCell(new Paragraph(data.getNumberValue() + ""));

				table.addCell(cell1);
				table.addCell(cell2);
				table.addCell(cell3);
				table.addCell(cell4);
				table.addCell(cell5);
				table.addCell(cell6);
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
