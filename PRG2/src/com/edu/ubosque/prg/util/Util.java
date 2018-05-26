package com.edu.ubosque.prg.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class Util
{
	
	// algoritmos
	public static String MD5 = "MD5";
	
	/***
	 * Convierte un arreglo de bytes a String usando valores hexadecimales
	 * 
	 * @param digest
	 *            arreglo de bytes a convertir
	 * @return String creado a partir de <code>digest</code>
	 */
	private static String toHexadecimal(byte[] digest)
	{
		String hash = "";
		for (byte aux : digest)
		{
			int b = aux & 0xff;
			if (Integer.toHexString(b).length() == 1) hash += "0";
			hash += Integer.toHexString(b);
		}
		return hash;
	}
	
	/***
	 * Encripta un mensaje de texto mediante algoritmo de resumen de mensaje.
	 * 
	 * @param message
	 *            texto a encriptar
	 * @param algorithm
	 *            algoritmo de encriptacion, puede ser: MD2, MD5, SHA-1, SHA-256,
	 *            SHA-384, SHA-512
	 * @return mensaje encriptado
	 */
	public static String getStringMessageDigest(String message, String algorithm)
	{
		byte[] digest = null;
		byte[] buffer = message.getBytes();
		try
		{
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.reset();
			messageDigest.update(buffer);
			digest = messageDigest.digest();
		}
		catch (NoSuchAlgorithmException ex)
		{
			System.out.println("Error creando Digest");
		}
		return toHexadecimal(digest);
	}
	
	public static void darMensaje(String titulo, String mensaje)
	{
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(titulo, mensaje));
	}
	
	public static String darContraseniaAleatoria()
	{
		StringBuilder aleatoria = new StringBuilder();
		
		while (aleatoria.length() < 6)
		{
			aleatoria.append((char) ((Math.random() * 26 + 'A')));
			aleatoria.append((char) ((Math.random() * 26 + 'a')));
			aleatoria.append((int) ((Math.random() * 9) + 1));
		}
		
		aleatoria.insert((int) ((Math.random() * 8) + 1), "@");
		
		return aleatoria.toString();
	}
	
	public static String darIp()
	{
		String ipAddress;
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		ipAddress = request.getHeader("X-FORWARDED-FOR");
		
		if (ipAddress == null)
		{
			ipAddress = request.getRemoteAddr();
		}
		
		return ipAddress;
	}
}
