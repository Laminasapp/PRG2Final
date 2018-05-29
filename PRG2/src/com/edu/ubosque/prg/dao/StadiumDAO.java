package com.edu.ubosque.prg.dao;

import java.util.List;

import com.edu.ubosque.prg.entity.Stadium;

public interface StadiumDAO {
	/**
	 * M�todo que graba un objeto en la tabla Stadium
	 * @param stadium Objeto que se graba en la tabla
	 */
	public void save(Stadium stadium);
	/**
	 * M�todo que devuelve un estadio buscado por su ID
	 * @param id del estadio que se esta buscando
	 * @return Estadio que se devuelve
	 */
	public Stadium getStadium(int id);
	/**
	 * M�todo que remueve de la tabla Stadium
	 * @param stadium Estadio que se va a remover
	 */
	public void remove(Stadium stadium);
	/**
	 * M�todo que actualiza la tabla stadium
	 * @param stadium Objeto que se actualiza
	 */
	public void update(Stadium stadium);
	
	/**
	 * M�todo que devuelve una lista de los estadios en la tabla
	 * @return Lista de las filas que se encunetran el tabla stadium
	 */
	public List<Stadium> list();

	

	
}
