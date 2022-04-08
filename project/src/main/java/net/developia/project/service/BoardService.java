package net.developia.project.service;

import java.util.List;

import net.developia.project.dto.AttachFileDTO;
import net.developia.project.dto.BoardDTO;

public interface BoardService {

	void insertBoard(BoardDTO boardDTO) throws Exception;

	List<BoardDTO> getBoardList() throws Exception;

	BoardDTO getDetail(long no) throws Exception;

	void deleteBoard(BoardDTO boardDTO) throws Exception;

	void updateBoard(BoardDTO boardDTO) throws Exception;

	List<BoardDTO> getBoardListPage(long pg, long new_no) throws Exception;

	long getBoardCount(long new_no) throws Exception;

	void insertFile(AttachFileDTO attachDTO) throws Exception;


}