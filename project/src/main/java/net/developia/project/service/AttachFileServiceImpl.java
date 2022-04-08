package net.developia.project.service;

import java.util.List;

import net.developia.project.dao.AttachFileDAO;
import net.developia.project.dto.AttachFileDTO;

public class AttachFileServiceImpl implements AttachFileService{

	private AttachFileDAO attachFileDAO;
	
	
	public AttachFileServiceImpl(AttachFileDAO attachFileDAO) {
		this.attachFileDAO = attachFileDAO;
	}
}
