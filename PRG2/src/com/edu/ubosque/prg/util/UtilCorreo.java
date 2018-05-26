package com.edu.ubosque.prg.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class UtilCorreo
{
	public static void enviarMensaje(String tema, String cuerpo, String correo)
	{
		String email = "applaminas@gmail.com";
		String password = "L4aminas_App";
		String destinario = correo;
		
		Properties properties = System.getProperties();
		String host = "smtp.gmail.com";
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.user", email);
		properties.put("mail.smtp.password", password);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		
		Session seccion = Session.getDefaultInstance(properties);
		MimeMessage mensaje = new MimeMessage(seccion);
		
		try
		{
			mensaje.setFrom(new InternetAddress(email));
			InternetAddress emailDestinario = new InternetAddress(destinario);
			mensaje.addRecipient(Message.RecipientType.TO, emailDestinario);
			
			mensaje.setSubject(tema);
			mensaje.setText(cuerpo);
			Transport transporte = seccion.getTransport("smtp");
			transporte.connect(host, email, password);
			transporte.sendMessage(mensaje, mensaje.getAllRecipients());
			transporte.close();
		}
		catch (MessagingException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void enviarPrimeraContrasenia(String nombre, String contraseña, String correo)
	{
		enviarMensaje("Bienvenid@ " + nombre + "!!!",
				nombre + "esta es tu primera contraseña: " + contraseña, correo);
	}
	
	public static void enviarNuevaContrasenia(String nombre, String contraseña, String correo)
	{
		enviarMensaje("Nueva contraseña", nombre + ", Esta es su nueva contraseña " +  contraseña, correo);
	}
}
