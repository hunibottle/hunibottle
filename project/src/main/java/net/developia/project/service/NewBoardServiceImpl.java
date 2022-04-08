package net.developia.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j;
import net.developia.project.dao.BoardDAO;
import net.developia.project.dao.NewBoardDAO;
import net.developia.project.dto.NewBoardDTO;

@Log4j
@Service
public class NewBoardServiceImpl implements NewBoardService{
	
	private NewBoardDAO newboardDAO;

	public NewBoardServiceImpl(NewBoardDAO newboardDAO) {
		this.newboardDAO = newboardDAO;
	}

	@Override
	public List<NewBoardDTO> getNewBoardList() throws Exception {
		return newboardDAO.getNewBoardList();
	}

	@Override
	public void insertNewBoard(NewBoardDTO newboardDTO) throws Exception {
		newboardDAO.insertNewBoard(newboardDTO);
	}
		
}
