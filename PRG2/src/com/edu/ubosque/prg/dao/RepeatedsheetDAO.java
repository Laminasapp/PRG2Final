package com.edu.ubosque.prg.dao;

import java.util.List;

import com.edu.ubosque.prg.entity.Repeatedsheet;
/**
 * Descripci�n: Interface que modela la tabla Repeatedsheet
 *
 */
public interface RepeatedsheetDAO {
	/**
	 * M�todo que graba en la tabla Repeatedsheet
	 * 
	 * @param repeatedsheet
	 *            objeto a grabar
	 */
	public void save(Repeatedsheet repeatedsheet);
	/**
	 * M�todo que devuelve las laminas por el id
	 * 
	 * @param id
	 *            de la lamina que se esta buscando
	 * @return
	 */
	public Repeatedsheet getRepeatedsheet(int id);
	/**
	 * M�todo que devuelve Lista de las laminas repetidas por el id de usuario
	 * 
	 * @param pId
	 *            Id del usuario
	 * @return Lista de laminas repetidas
	 */
	public List<Repeatedsheet> listLaminas(int pId);
	/**
	 * M�todo que remueve de la tabla repeatedsheet
	 * 
	 * @param repeatedsheet
	 */
	public void remove(Repeatedsheet repeatedsheet);
	/**
	 * M�todo que actualiza un objeto en la tabla repeatedsheet
	 * 
	 * @param repeatedsheet
	 *            Objeto a actualizar
	 */
	public void update(Repeatedsheet repeatedsheet);
	/**
	 * M�todo que deveulve una lista de laminas repetidas buscada por un id de
	 * usuario
	 * 
	 * @param pId
	 *            del usuario
	 * @return Lista de la slaminas repetidas
	 */
	public List<Repeatedsheet> list(int pId);
	/**
	 * M�todo que deveulve una lista de laminas repetidas buscada por un id de
	 * usuario
	 * 
	 * @param pId
	 *            del usuario
	 * @return Lista de la slaminas repetidas
	 */
	public List<Repeatedsheet> listaRepetidas(int pId);
	/**
	 * M�todo que deveulve una lista de laminas repetidas buscada por un id de
	 * usuario
	 * 
	 * @param pId
	 *            del usuario
	 * @return Lista de la slaminas repetidas
	 */
	public List<Repeatedsheet> repetidasUsuario(int pId);
	
}
