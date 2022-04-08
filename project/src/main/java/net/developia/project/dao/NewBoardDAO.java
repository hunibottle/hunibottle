package net.developia.project.dao;

import java.util.List;


import net.developia.project.dto.NewBoardDTO;

public interface NewBoardDAO {

	List<NewBoardDTO> getNewBoardList () throws Exception;

	void insertNewBoard(NewBoardDTO newboardDTO) throws Exception;

}
