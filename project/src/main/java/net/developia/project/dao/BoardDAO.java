package net.developia.project.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.developia.project.dto.AttachFileDTO;
import net.developia.project.dto.BoardDTO;

public interface BoardDAO {

	void insertBoard(BoardDTO boardDTO) throws Exception;

	List<BoardDTO> getBoardList() throws Exception;

	BoardDTO getDetail(long no) throws Exception;

	void updateReadcount(long no) throws Exception;

	int deleteBoard(BoardDTO boardDTO) throws Exception;

	int updateBoard(BoardDTO boardDTO) throws Exception;

	List<BoardDTO> getBoardListPage(@Param("startNum") long startNum, @Param("endNum") long endNum, @Param("new_no")long new_no);

	long getBoardCount(long new_no) throws Exception;

}