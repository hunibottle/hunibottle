package net.developia.project.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class BoardDTO implements Serializable {
	private long no;
	private long new_no;
	private String title;
	private String name;
	private String content;
	private Date regdate;
	private int readcount;
	private String password;
}