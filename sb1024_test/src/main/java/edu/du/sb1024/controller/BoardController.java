package edu.du.sb1024.controller;

import edu.du.sb1024.entity.BoardDto;
import edu.du.sb1024.entity.BoardFileDto;
import edu.du.sb1024.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {
	//추가 및 수정 사항 : 기본적으로 뷰와 모든 연동이 되고 작동이 잘 되면, 게시판 세부글 보기에서 이미지 다운로드 부분과 이미지가 보이는 부분을 조정.

	final BoardService boardService;

	@RequestMapping("/board/openBoardList.do")
	public String openBoardList(Model model, @PageableDefault(page = 0, size = 10) Pageable pageable) throws Exception{
		log.info("====> openBoardList {}", "테스트");
//		List<BoardDto> list = boardService.selectBoardList();

//		List<Board> list = boardRepository.findAllByOrderByBoardIdxDesc();
		List<BoardDto> list = boardService.selectBoardList();
		// 페이지 정보에 따라 현재 페이지의 시작 인덱스를 계산
		final int start = (int) pageable.getOffset();
		// 현재 페이지의 끝 인덱스를 계산하되, 목록 크기를 초과하지 않도록 함
		final int end = Math.min((start + pageable.getPageSize()), list.size());
		// 현재 페이지의 아이템 서브리스트를 포함하는 Page 객체 생성
		final Page<BoardDto> page = new PageImpl<>(list.subList(start, end), pageable, list.size());
		// 페이지 객체를 모델에 추가하여 뷰에서 접근 가능하도록 함
		model.addAttribute("list", page);
//		model.addAttribute("list", list);
		// 게시물 목록을 표시할 뷰 이름 반환
		return "/info/board/boardList";
	}



	@GetMapping("/board/openBoardWrite.do")
	public String openBoardWrite() throws Exception{
		return "/info/board/boardWrite";
	}
	
	@PostMapping("/board/insertBoard.do")
	public String insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest, HttpSession session) throws Exception{
		board.setCreatorId(session.getAttribute("authInfo").toString());
		boardService.insertBoard(board, multipartHttpServletRequest);
		return "redirect:/board/openBoardList.do";
	}
	
//	@RequestMapping("/board/openBoardDetail.do")
//	public ModelAndView openBoardDetail(@RequestParam int boardIdx) throws Exception{
//		ModelAndView mv = new ModelAndView("/board/boardDetail");
//
//		BoardDto board = boardService.selectBoardDetail(boardIdx);
//		mv.addObject("board", board);
//
//		return mv;
//	}

	@RequestMapping("/board/openBoardDetail.do")
	public String openBoardDetail(@RequestParam int boardIdx , Model model,HttpSession session) throws Exception{
		BoardDto board = boardService.selectBoardDetail(boardIdx);
		model.addAttribute("board", board);
		model.addAttribute("uname", session.getAttribute("authInfo").toString());

		return "/info/board/boardDetail";
	}
	
	@RequestMapping("/board/updateBoard.do")
	public String updateBoard(BoardDto board) throws Exception{
		boardService.updateBoard(board);
		return "redirect:/board/openBoardList.do";
	}
	
	@RequestMapping("/board/deleteBoard.do")
	public String deleteBoard(int boardIdx) throws Exception{
		boardService.deleteBoard(boardIdx);
		return "redirect:/board/openBoardList.do";
	}
	
	@RequestMapping("/board/downloadBoardFile.do")
	public void downloadBoardFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse response) throws Exception{
		String currentPath = Paths.get("").toAbsolutePath().toString();
		System.out.println("---------------------"+currentPath);
		BoardFileDto boardFile = boardService.selectBoardFileInformation(idx, boardIdx);
		if(!ObjectUtils.isEmpty(boardFile)) {
			String fileName = boardFile.getOriginalFileName();
			
			byte[] files = FileUtils.readFileToByteArray(new File("./src/main/resources/static"+boardFile.getStoredFilePath()));
			
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName,"UTF-8")+"\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			
			response.getOutputStream().write(files);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
}
