package com.edu.ubosque.prg.dao;

import java.util.List;

import com.edu.ubosque.prg.entity.Missingsheet;
import com.edu.ubosque.prg.entity.User;
/**
 * Descripci�n: Interface que modela la tabla Missingsheet
 *
 */
public interface MissingsheetDAO {
	/**
	 * M�todo que guarda en la tabla missingsheet
	 * @param userID indicador del usuario que se graba
	 */
	public void save(int userID);
	/**
	 * M�todo que devuelve la lamina que le falta
	 * @param id numero de la lamina que falta
	 * @return devuelve la lamina que le falta
	 */
	public Missingsheet getMissingsheet(int id);
	/**
	 * m�todo que devuelve la lista de las laminas faltantes de un usuario
	 * @param pId
	 * @return
	 */
	public List<Missingsheet> list(int pId);
	/**
	 * M�todo que devuelve las laminas repetidas de un usuario especifico
	 * @param userId identificador del usuario
	 * @param numberSheets identificador de la lamina
	 * @return devuelve la lamina
	 */
	public Missingsheet getMissingsheet(int userId, int numberSheets);
	/**
	 * M�todo que remueve de la base de datos la lamina (cmabia de  1 a 0)
	 * @param missingsheet
	 */
	public void remove(Missingsheet missingsheet);
	/**
	 * M�todo que actualiza un objto de missingsheet
	 * @param missingsheet objeto a actualizar
	 */
	public void update(Missingsheet missingsheet);
	/**
	 * M�todo que devuelve una lista missingsheets buscada por el nombre del usuario
	 * @param userId Usuario a encontrar
	 * @return Lista de missingsheets
	 */
	public List<Missingsheet> list(String userId);
	/**
	 * M�todo que devuelve una lista de las laminas por parametro
	 * @param lamina numero de la lamina a encontrar
	 * @return Lista de laminas
	 */
	public List<Missingsheet> listaLaminas(int lamina);
	/**
	 * M�todo que verifica si el usuario lleno el �lbum
	 * @param pId Id del usuario a verificar
	 * @return true o false dependiendo si lo lleno o no
	 */
	public boolean lleno(int pId);
}
