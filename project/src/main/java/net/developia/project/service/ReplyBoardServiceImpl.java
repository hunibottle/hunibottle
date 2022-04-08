package net.developia.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j;
import net.developia.project.dao.ReplyBoardDAO;
import net.developia.project.dto.ReplyBoardDTO;

@Log4j
@Service
public class ReplyBoardServiceImpl implements ReplyBoardService {
	
	private ReplyBoardDAO replyboardDAO;

	public ReplyBoardServiceImpl(ReplyBoardDAO replyboardDAO) {
		this.replyboardDAO = replyboardDAO;
	}

	@Override
	public List<ReplyBoardDTO> replyList(long re_no) throws Exception {
		return replyboardDAO.replyList(re_no);
	}

	@Override
	public void replyInsert(ReplyBoardDTO replyboardDTO) throws Exception {
		replyboardDAO.replyInsert(replyboardDTO);
		
	}

	@Override
	public void replyDelete(ReplyBoardDTO replyboardDTO) throws Exception {
		replyboardDAO.replyDelete(replyboardDTO);
		
	}
			
}
