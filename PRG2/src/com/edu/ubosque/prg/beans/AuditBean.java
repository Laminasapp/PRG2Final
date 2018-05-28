package com.edu.ubosque.prg.beans;

import com.edu.ubosque.prg.dao.AuditDAO;
import com.edu.ubosque.prg.dao.impl.AuditDAOImpl;
import com.edu.ubosque.prg.entity.Audit;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;

import java.io.File;
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

@ManagedBean
@SessionScoped
public class AuditBean {

	final static Logger logger = Logger.getLogger(AuditBean.class);
	private Audit auditoria;
	private DataModel listaAuditoria;

	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;
	
	public String adicionarAuditoria(Audit auditoria) {
		AuditDAO dao = new AuditDAOImpl();
		dao.save(auditoria);
		return "index";
	}

	public DataModel getListarAuditoria() {
		List<Audit> lista = new AuditDAOImpl().list();
		listaAuditoria = new ListDataModel(lista);
		return listaAuditoria;
	}

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
	
	 }
}