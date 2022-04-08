package net.developia.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.log4j.Log4j;
import net.developia.project.dto.BoardDTO;
import net.developia.project.dto.ReplyBoardDTO;
import net.developia.project.service.ReplyBoardService;

@Log4j
@Controller
public class ReplyBoardController {

	private ReplyBoardService replyboardService;
	
	
	public ReplyBoardController(ReplyBoardService replyboardService) {
		this.replyboardService = replyboardService;
	}

	public String replyInsert(ReplyBoardDTO replyboardDTO) throws Exception{
		
		replyboardService.replyInsert(replyboardDTO);
		
		return "redirect:/main/board/";
		
	}
}
