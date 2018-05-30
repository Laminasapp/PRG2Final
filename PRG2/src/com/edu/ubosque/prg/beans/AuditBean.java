package com.edu.ubosque.prg.beans;

import com.edu.ubosque.prg.dao.AuditDAO;
import com.edu.ubosque.prg.dao.impl.AuditDAOImpl;
import com.edu.ubosque.prg.entity.Audit;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.apache.log4j.Logger;
/**
 * Descripción: Clase Bean que se asocia con un formulario que necesite la tabla audit
 *
 */
@ManagedBean
@SessionScoped
public class AuditBean {

	final static Logger logger = Logger.getLogger(AuditBean.class);
	private Audit auditoria;
	private DataModel<Audit> listaAuditoria;
	private DataModel<Audit> listaAuditoriafiltrada;
	private Date fecha1;
	private Date fecha2;
	
	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;
	/**
	 * Método que se encarga de agregar un nuevo objeto en la tabla audit
	 * @param auditoria Objeto que se agrega en la tabla
	 * @return Objeto que contien la pagina a la que sera direccionado
	 */
	public String adicionarAuditoria(Audit auditoria) {
		AuditDAO dao = new AuditDAOImpl();
		dao.save(auditoria);
		logger.info("Se agrego una auditoria");
		return "index";
	}

	public DataModel<Audit> getListarAuditoria() {
		List<Audit> lista = new AuditDAOImpl().list();
		listaAuditoria = new ListDataModel<Audit>(lista);
		return listaAuditoria;
	}
	/**
	 * Método que prepara la generación de un documento PF
	 * @param document Objeto que se va a convertir a PDF
	 * @throws BadElementException 
	 * @throws MalformedURLException
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void preProcessPDF(Object document) throws BadElementException, MalformedURLException, DocumentException, IOException
	 {
	  Document pdf = (Document) document;
	  pdf.open();
	  pdf.setPageSize(PageSize.A4);
	  
	  ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	  String logo = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "img" + File.separator + "prime" + File.separator + "Logo.jpg";
	  Image imagen = Image.getInstance(logo);
	  imagen.scalePercent(25);
	  pdf.add(imagen);
	  Date fecha = new Date();
	  String texto = " \n Archivo creado por: " + userBean.getUsuario().getFullName() + ", Fecha: " + fecha ;
	  pdf.add(new Paragraph(texto));
	  pdf.add(new Paragraph(" "));
	  logger.info("Se descarga el pdf");
	
	 }
	public void consultaFechas() {
		if((fecha1!=null)&&(fecha2!=null)) {
			List<Audit> lista = new AuditDAOImpl().filtrados(fecha1, fecha2);
			listaAuditoriafiltrada = new ListDataModel<Audit>(lista);
			logger.info("Se creo la consulta por fechas de la auditoria");
		}
	}
	public void pdf()
	{
		try
		{
			File file = new File("ListaAuditoria.pdf");
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

			PdfPTable table = new PdfPTable(7);

			PdfPCell cell1 = new PdfPCell(new Paragraph("Id"));
			cell1.setBackgroundColor(new Color(255, 102, 102));
			PdfPCell cell2 = new PdfPCell(new Paragraph("Id del usuario"));
			cell2.setBackgroundColor(new Color(255, 102, 102));
			PdfPCell cell3 = new PdfPCell(new Paragraph("Operacion"));
			cell3.setBackgroundColor(new Color(255, 102, 102));
			PdfPCell cell4 = new PdfPCell(new Paragraph("Nombre de la tabla"));
			cell4.setBackgroundColor(new Color(255, 102, 102));
			PdfPCell cell5 = new PdfPCell(new Paragraph("Id de la tabla"));
			cell5.setBackgroundColor(new Color(255, 102, 102));
			PdfPCell cell6 = new PdfPCell(new Paragraph("Fecha de Creacion"));
			cell6.setBackgroundColor(new Color(255, 102, 102));
			PdfPCell cell7 = new PdfPCell(new Paragraph("IP del usuario"));
			cell7.setBackgroundColor(new Color(255, 102, 102));

			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);
			table.addCell(cell5);
			table.addCell(cell6);
			table.addCell(cell7);

			for (Audit data : listaAuditoria)
			{
				cell1 = new PdfPCell(new Paragraph(data.getId() + ""));
				cell2 = new PdfPCell(new Paragraph(data.getUserId() + ""));
				cell3 = new PdfPCell(new Paragraph(data.getOperation()));
				cell4 = new PdfPCell(new Paragraph(data.getTableName()));
				cell5 = new PdfPCell(new Paragraph(data.getTableId() + ""));
				cell6 = new PdfPCell(new Paragraph(data.getCreateDate() + ""));
				cell7 = new PdfPCell(new Paragraph(data.getUserIp()));

				table.addCell(cell1);
				table.addCell(cell2);
				table.addCell(cell3);
				table.addCell(cell4);
				table.addCell(cell5);
				table.addCell(cell6);
				table.addCell(cell7);
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

	public DataModel<Audit> getListaAuditoriafiltrada() {
		return listaAuditoriafiltrada;
	}

	public void setListaAuditoriafiltrada(DataModel<Audit> listaAuditoriafiltrada) {
		this.listaAuditoriafiltrada = listaAuditoriafiltrada;
	}

	public DataModel<Audit> getListaAuditoria()
	{
		return listaAuditoria;
	}

	public void setListaAuditoria(DataModel<Audit> listaAuditoria)
	{
		this.listaAuditoria = listaAuditoria;
	}

	public UserBean getUserBean()
	{
		return userBean;
	}

	public void setUserBean(UserBean userBean)
	{
		this.userBean = userBean;
	}

	public Audit getAuditoria()
	{
		return auditoria;
	}

	public void setAuditoria(Audit auditoria)
	{
		this.auditoria = auditoria;
	}
	public Date getFecha1() {
		return fecha1;
	}

	public void setFecha1(Date fecha1) {
		this.fecha1 = fecha1;
	}

	public Date getFecha2() {
		return fecha2;
	}

	public void setFecha2(Date fecha2) {
		this.fecha2 = fecha2;
	}

}