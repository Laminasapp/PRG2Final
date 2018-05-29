package com.edu.ubosque.prg.dao;

import java.util.List;

import com.edu.ubosque.prg.entity.New;
/**
 * Descripción: Interfaz que modela New (noticias)
 * 
 */
public interface NewDAO {
	/**
	 * Método que guarda en la tabla new
	 * @param New objeto que se graba
	 */
	public void save(New news);
	/**
	 * Método que devuelve una noticia por id
	 * @param id Identificador de la noticia que se devuelv
	 * @return Noticia que se busco
	 */
	public New getNew(int id);
	/**
	 * Método que remueve de la base de datos (actualiza)
	 * @param news Objeto a remover
	 */
	public void remove(New news);
	/**
	 * Método que actualiza las noticias
	 * @param news objeto que se actualiza
	 */
	public void update(New news);

	/**
	 * Método que devuelve lista de todas las noticias
	 * @return List Lista de las nosticias almacenadas en la Base de Datos
	 */
	public List<New> list();

}
