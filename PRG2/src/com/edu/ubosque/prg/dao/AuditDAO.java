package com.edu.ubosque.prg.dao;

import com.edu.ubosque.prg.entity.Audit;

import java.util.Date;
import java.util.List;
/**
 * Descripci�n: Interface que modela un DAO para la el Audit
 * 
 */
public interface AuditDAO {
	/**
	 * M�todo que guarda un objeto de la clase Audit
	 * @param auditoria objeto a guardar
	 */
	public void save(Audit auditoria);
	/**
	 * M�todo que devuelve lista de cada una de las filas de la tabla Audit
	 * @return filas de Audit
	 */
	public List<Audit> list();
	/**
	 * Metodoq ue filtra por fechas
	 * @return filas de Audit
	 */
	public List<Audit> filtrados(Date d1, Date d2);
}
