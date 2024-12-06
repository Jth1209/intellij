package edu.du.sb1024.mapper;


import edu.du.sb1024.entity.BoardDto;
import edu.du.sb1024.entity.BoardFileDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {
	List<BoardDto> selectBoardList() throws Exception;

	List<BoardDto> selectFiveBoard() throws Exception;

	List<BoardDto> selectBoardListByCnt() throws Exception;

	List<BoardDto> selectBoardListWithKeyword(String keyword) throws Exception;

	List<BoardDto> selectBoardListWithKeywordAndCommon(String keyword , String type) throws Exception;

	List<BoardDto> selectBoardListWithKeywordAndInfo(String keyword , String type) throws Exception;

	List<BoardDto> selectBoardListWithCommon(String type) throws Exception;

	List<BoardDto> selectBoardListWithInfo(String type) throws Exception;
	
	void insertBoard(BoardDto board) throws Exception;

	BoardDto selectBoardDetail(int boardIdx) throws Exception;

	void updateHitCount(int boardIdx) throws Exception;
	
	void updateBoard(BoardDto board) throws Exception;
	
	void deleteBoard(int boardIdx) throws Exception;

	void insertBoardFileList(List<BoardFileDto> list) throws Exception;

	List<BoardFileDto> selectBoardFileList(int boardIdx) throws Exception;

	BoardFileDto selectBoardFileInformation(@Param("idx") int idx, @Param("boardIdx" )int boardIdx);
}
