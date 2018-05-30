package com.edu.ubosque.prg.dao;

import java.util.List;

import com.edu.ubosque.prg.entity.Missingsheet;
import com.edu.ubosque.prg.entity.User;
/**
 * Descripción: Interface que modela la tabla Missingsheet
 *
 */
public interface MissingsheetDAO {
	/**
	 * Método que guarda en la tabla missingsheet
	 * @param userID indicador del usuario que se graba
	 */
	public void save(int userID);
	/**
	 * Método que devuelve la lamina que le falta
	 * @param id numero de la lamina que falta
	 * @return devuelve la lamina que le falta
	 */
	public Missingsheet getMissingsheet(int id);
	/**
	 * método que devuelve la lista de las laminas faltantes de un usuario
	 * @param pId
	 * @return
	 */
	public List<Missingsheet> list(int pId);
	/**
	 * Método que devuelve las laminas repetidas de un usuario especifico
	 * @param userId identificador del usuario
	 * @param numberSheets identificador de la lamina
	 * @return devuelve la lamina
	 */
	public Missingsheet getMissingsheet(int userId, int numberSheets);
	/**
	 * Método que remueve de la base de datos la lamina (cmabia de  1 a 0)
	 * @param missingsheet
	 */
	public void remove(Missingsheet missingsheet);
	/**
	 * Método que actualiza un objto de missingsheet
	 * @param missingsheet objeto a actualizar
	 */
	public void update(Missingsheet missingsheet);
	/**
	 * Método que devuelve una lista missingsheets buscada por el nombre del usuario
	 * @param userId Usuario a encontrar
	 * @return Lista de missingsheets
	 */
	public List<Missingsheet> list(String userId);
	/**
	 * Método que devuelve una lista de las laminas por parametro
	 * @param lamina numero de la lamina a encontrar
	 * @return Lista de laminas
	 */
	public List<Missingsheet> listaLaminas(int lamina);
	/**
	 * Método que verifica si el usuario lleno el álbum
	 * @param pId Id del usuario a verificar
	 * @return true o false dependiendo si lo lleno o no
	 */
	public boolean lleno(int pId);
}
