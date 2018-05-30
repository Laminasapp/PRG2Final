package com.edu.ubosque.prg.util;

public class Nodo {

	private int userId;
	private int numeroRepetidas;
	private String nombre;
	
	public Nodo(int pId, int pNum, String pNombre) {
		
		this.userId = pId;
		this.numeroRepetidas = pNum;
		this.nombre = pNombre;
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getNumeroRepetidas() {
		return numeroRepetidas;
	}

	public void setNumeroRepetidas(int numeroRepetidas) {
		this.numeroRepetidas = numeroRepetidas;
	}
	
	
	
}
