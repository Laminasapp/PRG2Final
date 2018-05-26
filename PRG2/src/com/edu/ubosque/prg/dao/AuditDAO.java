package com.edu.ubosque.prg.dao;

import com.edu.ubosque.prg.entity.Audit;
import java.util.List;

public interface AuditDAO {

	public void save(Audit auditoria);

	public List<Audit> list();

}
