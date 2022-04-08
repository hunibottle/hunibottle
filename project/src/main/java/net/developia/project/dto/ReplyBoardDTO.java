package net.developia.project.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ReplyBoardDTO implements Serializable{
	private long re_no; 
	private String name;
	private String re_context;
	private Date regdate;
	private long seq_first; 
	private long seq_second;
}
