package com.edu.ubosque.prg.dao;

import java.util.List;

import com.edu.ubosque.prg.entity.Parameter;
/**
 * Descripción: interface que modela la tabla Parameter
 *
 */
public interface ParameterDAO {
	/**
	 * Método que guarda un objeto en la Base de datos Parameter
	 * @param parameter Objeto que se va guardar
	 */
	public void save(Parameter parameter);
	/**
	 * Método que busca en la tabla Parameter por un identificador
	 * @param id Identificador de la tabla parameter
	 * @return
	 */
	public Parameter getParameter(int id);
	/**
	 * Método que remueve de la tabla Parameter 
	 * @param parameter 
	 */
	public Parameter getParameter(String param);
	/**
	 * Método que actualiza la tabla Parameter
	 * @param parameter objeto a actualizar
	 */
	public void remove(Parameter parameter);
	/**
	 * Método que devuelve la lista de la tabla paramter
	 * @return Lista de parametros
	 */
	public void update(Parameter parameter);
	/**
	 * Método que devuelve los parametros ingresado por parametro
	 * @param param Paramero a buscar
	 * @return
	 */
	public List<Parameter> list();
	
}
