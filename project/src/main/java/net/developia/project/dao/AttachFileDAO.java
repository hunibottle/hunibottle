package net.developia.project.dao;

import java.util.List;

import net.developia.project.dto.AttachFileDTO;

public interface AttachFileDAO {

	void insertFile(AttachFileDTO attachDTO) throws Exception;
	
}
