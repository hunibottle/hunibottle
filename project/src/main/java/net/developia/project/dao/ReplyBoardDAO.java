package net.developia.project.dao;

import java.util.List;

import net.developia.project.dto.ReplyBoardDTO;

public interface ReplyBoardDAO {

	List<ReplyBoardDTO> replyList(long re_no) throws Exception;

	void replyInsert(ReplyBoardDTO replyboardDTO) throws Exception;

	void replyDelete(ReplyBoardDTO replyboardDTO) throws Exception;

}
