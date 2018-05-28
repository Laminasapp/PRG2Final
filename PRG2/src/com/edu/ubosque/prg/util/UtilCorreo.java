package com.edu.ubosque.prg.util;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.auth.AuthMethod;
import com.nexmo.client.auth.TokenAuthMethod;
import com.nexmo.client.sms.SmsSubmissionResult;
import com.nexmo.client.sms.messages.TextMessage;

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
	
	public static void enviarSMS(String numero, String mensaje)
	{
		AuthMethod auth = new TokenAuthMethod("6c792482", "x6V5PAgeNS9yP7kw");
		NexmoClient client = new NexmoClient(auth);

		TextMessage message = new TextMessage("573102068794", numero, mensaje);
		SmsSubmissionResult[] responses;
		try
		{
			responses = client.getSmsClient().submitMessage(message);
			for (SmsSubmissionResult response : responses)
			{
				System.out.println(response);
			}
		}
		catch (IOException | NexmoClientException e)
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
