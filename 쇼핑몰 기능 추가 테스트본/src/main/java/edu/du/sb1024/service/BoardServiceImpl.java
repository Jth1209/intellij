package edu.du.sb1024.service;

import edu.du.sb1024.common.FileUtils;
import edu.du.sb1024.entity.BoardDto;
import edu.du.sb1024.entity.BoardFileDto;
import edu.du.sb1024.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private FileUtils fileUtils;

    @Override
    public List<BoardDto> selectBoardList() throws Exception {
        return boardMapper.selectBoardList();
    }

    @Override
    public List<BoardDto> selectFiveBoard() throws Exception {
        return boardMapper.selectFiveBoard();
    }

    @Override
    public void updateHitCount(int boardIdx) throws Exception {
        boardMapper.updateHitCount(boardIdx);
    }

    @Override
    public void insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        boardMapper.insertBoard(board);
        List<BoardFileDto> list = fileUtils.parseFileInfo(board.getBoardIdx(), multipartHttpServletRequest);
        if (!CollectionUtils.isEmpty(list)) {
            boardMapper.insertBoardFileList(list);
        }
    }

    @Override
    public BoardDto selectBoardDetail(int boardIdx) throws Exception {
        BoardDto board = boardMapper.selectBoardDetail(boardIdx);
        List<BoardFileDto> fileList = boardMapper.selectBoardFileList(boardIdx);
        board.setFileList(fileList);
        return board;
    }

    @Override
    public void updateBoard(BoardDto board) throws Exception {
        boardMapper.updateBoard(board);
    }

    @Override
    public void deleteBoard(int boardIdx) throws Exception {
        boardMapper.deleteBoard(boardIdx);
    }

    @Override
    public BoardFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception {
        return boardMapper.selectBoardFileInformation(idx, boardIdx);
    }

    @Override
    public List<BoardDto> configuration(String type, String keyword) throws Exception {
        List<BoardDto> list = new ArrayList<>();
        switch (type) {
            case "all":
                if (keyword.equals("no")) {
                    list = boardMapper.selectBoardList();
                } else {
                    list = boardMapper.selectBoardListWithKeyword(keyword);
                }
                break;
            case "common":
                if (keyword.equals("no")) {
                    list = boardMapper.selectBoardListWithCommon(type);
                } else {
                    list = boardMapper.selectBoardListWithKeywordAndCommon(keyword, type);
                }
                break;
            case "info":
                if (keyword.equals("no")) {
                    list = boardMapper.selectBoardListWithInfo(type);
                } else {
                    list = boardMapper.selectBoardListWithKeywordAndInfo(keyword, type);
                }
                break;
        }
        return list;
    }
}

