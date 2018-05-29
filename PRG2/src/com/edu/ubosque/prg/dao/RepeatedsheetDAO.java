package com.edu.ubosque.prg.dao;

import java.util.List;

import com.edu.ubosque.prg.entity.Repeatedsheet;
/**
 * Descripción: Interface que modela la tabla Repeatedsheet
 *
 */
public interface RepeatedsheetDAO {
	/**
	 * Método que graba en la tabla Repeatedsheet
	 * 
	 * @param repeatedsheet
	 *            objeto a grabar
	 */
	public void save(Repeatedsheet repeatedsheet);
	/**
	 * Método que devuelve las laminas por el id
	 * 
	 * @param id
	 *            de la lamina que se esta buscando
	 * @return
	 */
	public Repeatedsheet getRepeatedsheet(int id);
	/**
	 * Método que devuelve Lista de las laminas repetidas por el id de usuario
	 * 
	 * @param pId
	 *            Id del usuario
	 * @return Lista de laminas repetidas
	 */
	public List<Repeatedsheet> listLaminas(int pId);
	/**
	 * Método que remueve de la tabla repeatedsheet
	 * 
	 * @param repeatedsheet
	 */
	public void remove(Repeatedsheet repeatedsheet);
	/**
	 * Método que actualiza un objeto en la tabla repeatedsheet
	 * 
	 * @param repeatedsheet
	 *            Objeto a actualizar
	 */
	public void update(Repeatedsheet repeatedsheet);
	/**
	 * Método que deveulve una lista de laminas repetidas buscada por un id de
	 * usuario
	 * 
	 * @param pId
	 *            del usuario
	 * @return Lista de la slaminas repetidas
	 */
	public List<Repeatedsheet> list(int pId);
	/**
	 * Método que deveulve una lista de laminas repetidas buscada por un id de
	 * usuario
	 * 
	 * @param pId
	 *            del usuario
	 * @return Lista de la slaminas repetidas
	 */
	public List<Repeatedsheet> listaRepetidas(int pId);
	/**
	 * Método que deveulve una lista de laminas repetidas buscada por un id de
	 * usuario
	 * 
	 * @param pId
	 *            del usuario
	 * @return Lista de la slaminas repetidas
	 */
	public List<Repeatedsheet> repetidasUsuario(int pId);
	
}
