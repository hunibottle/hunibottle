package net.developia.project.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.log4j.Log4j;
import net.developia.project.controller.BoardDetailController;
import net.developia.project.dto.BoardDTO;
import net.developia.project.dto.ReplyBoardDTO;
import net.developia.project.service.BoardService;
import net.developia.project.service.ReplyBoardService;

@Log4j
@Controller
@RequestMapping("main/board/{new_no}/{pg}/{no}") //new_no까지 넘겨주기 
public class BoardDetailController {

	private BoardService boardService;
	
	private ReplyBoardService replyboardService;
	
	public BoardDetailController(BoardService boardService,ReplyBoardService replyboardService) {
		this.boardService = boardService;
		this.replyboardService =replyboardService;
	}

	@GetMapping(value = "/")
	public String detail(
			@PathVariable long pg,
			@PathVariable long no,
			Model model) {
		
		try {
			List<ReplyBoardDTO> reply = null;
			reply = replyboardService.replyList(no);
			BoardDTO boardDTO = boardService.getDetail(no);
			model.addAttribute("boardDTO", boardDTO);
			model.addAttribute("reply",reply); //댓글보여주기
			return "board.detail";
		} catch(RuntimeException e) { 
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("url", "../");
			return "result";
		} catch (Exception e) {
			model.addAttribute("msg", "상세보기 에러");
			model.addAttribute("url", "../");
			return "result";
		}
	}
	
	@PostMapping(value ="/")
	public String replyInsert(@ModelAttribute ReplyBoardDTO replyboardDTO, Model model){
		
		log.info(replyboardDTO.toString());
		try {
			replyboardService.replyInsert(replyboardDTO);
			return "redirect:../"+replyboardDTO.getRe_no()+"/";//현재 디렉토리 
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("url", "../");
			return "result";
		}
	}
	
	
	@GetMapping("delete")
	public String delete(@PathVariable long no, Model model) {
		return "board.delete";
	}

	@PostMapping("delete")
	public ModelAndView delete(@ModelAttribute BoardDTO boardDTO) {
		log.info(boardDTO.toString());
		ModelAndView mav = new ModelAndView("result");
		try {
			boardService.deleteBoard(boardDTO);
			mav.addObject("msg", boardDTO.getNo() + "번 게시물이 삭제되었습니다.");
			mav.addObject("url", "../../1/");
		} catch (Exception e) {
			mav.addObject("msg", e.getMessage());
			mav.addObject("url", "javascript:history.back();");
		}
		return mav;
	}

	@GetMapping("update")
	public String update(@PathVariable long no, Model model) {
		try {
			BoardDTO boardDTO = boardService.getDetail(no);
			model.addAttribute("boardDTO", boardDTO);
			return "board.update";
		} catch (Exception e) {
			model.addAttribute("msg", "해당하는 게시물이 없거나 시스템 에러입니다.");
			model.addAttribute("url", "../../1/");
			return "result";
		}
	}

	@PostMapping("update")
	public String updateBoard(@ModelAttribute BoardDTO boardDTO,
		Model model) {

		log.info(boardDTO.toString());
		try {
			boardService.updateBoard(boardDTO);
			model.addAttribute("msg", boardDTO.getNo() + "번 게시물이 수정되었습니다.");
			model.addAttribute("url", "./");
			return "result";
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("url", "javascript:history.back();");
			return "result";
		}
	}
}

