package net.developia.project.service;

import java.util.List;

import net.developia.project.dto.ReplyBoardDTO;

public interface ReplyBoardService {
	
	List<ReplyBoardDTO> replyList(long re_no) throws Exception;
	
	void replyDelete(ReplyBoardDTO replyboardDTO) throws Exception;

	void replyInsert(ReplyBoardDTO replyboardDTO) throws Exception;
		
}
