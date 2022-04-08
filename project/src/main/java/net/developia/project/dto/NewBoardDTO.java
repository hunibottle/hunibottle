package net.developia.project.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
@Data
public class NewBoardDTO implements Serializable {
	private long rownum;
	private long no;
	private String name;
	private Date regdate;
}