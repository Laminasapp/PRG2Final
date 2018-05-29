package com.edu.ubosque.prg.dao;

import java.util.List;

import com.edu.ubosque.prg.entity.Parameter;
/**
 * Descripci�n: interface que modela la tabla Parameter
 *
 */
public interface ParameterDAO {
	/**
	 * M�todo que guarda un objeto en la Base de datos Parameter
	 * @param parameter Objeto que se va guardar
	 */
	public void save(Parameter parameter);
	/**
	 * M�todo que busca en la tabla Parameter por un identificador
	 * @param id Identificador de la tabla parameter
	 * @return
	 */
	public Parameter getParameter(int id);
	/**
	 * M�todo que remueve de la tabla Parameter 
	 * @param parameter 
	 */
	public Parameter getParameter(String param);
	/**
	 * M�todo que actualiza la tabla Parameter
	 * @param parameter objeto a actualizar
	 */
	public void remove(Parameter parameter);
	/**
	 * M�todo que devuelve la lista de la tabla paramter
	 * @return Lista de parametros
	 */
	public void update(Parameter parameter);
	/**
	 * M�todo que devuelve los parametros ingresado por parametro
	 * @param param Paramero a buscar
	 * @return
	 */
	public List<Parameter> list();
	
}
