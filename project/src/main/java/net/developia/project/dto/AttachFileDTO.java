package net.developia.project.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AttachFileDTO implements Serializable{
	private String fileName;
	private String uploadPath;
	private String uuid;
	private boolean image;
	private int bno;
}
