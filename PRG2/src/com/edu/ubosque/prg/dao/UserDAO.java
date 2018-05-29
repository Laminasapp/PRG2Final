package com.edu.ubosque.prg.dao;

import com.edu.ubosque.prg.entity.User;
import java.util.List;

/**
 * Descripci�n: Interface que modela la tabla user
 *
 */
public interface UserDAO {
	/**
	 * M�todo que graba un objeto en la tabla user
	 * @param usuario Objeto que se graba en la tabla user
	 */
	public void save(User usuario);
	/**
	 * M�todo que busca un usario por su id
	 * @param id Id del usuario que se busca
	 * @return Usuario que se encontro
	 */
	public User getUsuario(int id);
	/**
	 * M�todo que encuentra un usuario por su nombre
	 * @param parameter Nombre edl usuario que se esta buscando
	 * @return usuario que se encontro en caso de que no se encuentre se devuelve null
	 */
	public User getUsuario(String parameter);
	/**
	 * M�todo que busca en la tabla usuario por el nombre y el pasword ingresado
	 * @param name Nombre del usuario que se busca
	 * @param pass Contrase�a del usuario
	 * @return Usuario que se encontro
	 */
	public User getUsuario(String name,String pass);
	/**
	 * M�todo que devuelve un lista de todos los usuarios contenidos en la tabla
	 * @return Lista de usuarios
	 */
	public List<User> list();
	/**
	 * M�todo que remueve un usuario de la tabla user ( Cambia de estado de A a I)
	 * @param usuario usaurio que se inactiva
	 */
	public void remove(User usuario);
	/**
	 * M�todo que actualiza un campo en la tabal de user
	 * @param usuario Objeto a actualizar en la tabla user
	 */
	public void update(User usuario);
	/**
	 * M�todo que verifica si el nombre del usuario ya existe en la tabla
	 * @param pName Nombre del usuario que se mira si ya existe
	 * @return respuesta de si existe el nombre o no
	 */
	public boolean userName(String pName);

}
