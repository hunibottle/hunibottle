package net.developia.project.service;

import java.util.List;

import net.developia.project.dto.NewBoardDTO;

public interface NewBoardService {

	List<NewBoardDTO> getNewBoardList() throws Exception;

	void insertNewBoard(NewBoardDTO newboardDTO) throws Exception;
	
}
