package com.edu.notice_board.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.edu.notice_board.domain.NoticeVO;
import com.edu.notice_board.servie.NoticeService;

@Controller
@RequestMapping("/notice_board/*")
public class NoticeController {

private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
	
	@Inject
	NoticeService noticeService;
	
	//-------------------------------------------------------------------------------------------------
	// 게시물 목록
	//-------------------------------------------------------------------------------------------------
	@RequestMapping(value="/notice_list", method=RequestMethod.GET)
	public void getList(Model model) throws Exception {
		
		System.out.println("NoticeController getList() ");
		logger.info("NoticeController getList() ");
		
		List<NoticeVO> noticelist = null;
		noticelist = noticeService.noticelist(); // 작업할 테이블명을 매개변수로 넘겨준다.
		logger.info("NoticeController getList() return Value ==> " + noticelist);
		System.out.println("NoticeController getList() return Value ==> " + noticelist);
		
		model.addAttribute("noticelist", noticelist);
		
	} // End - public void getList(Model model)

//	//-------------------------------------------------------------------------------------------------
//	// 글번호에 해당하는 게시글의 상세정보를 가져온다.
//	//-------------------------------------------------------------------------------------------------
	@RequestMapping(value="/notice_view", method=RequestMethod.GET)
	public void getView(@RequestParam("bno") int bno, Model model) throws Exception {
		
		System.out.println("NoticeController getView(bno) ==> " + bno);
		logger.info("NoticeController getView(bno) ==> " + bno);
		
		NoticeVO NoticeVO = null;
		NoticeVO = noticeService.noticeview(bno);
		logger.info("BoardController return Value ==> " + NoticeVO);
		
		model.addAttribute("Noticeview", NoticeVO);
		
	} // End - public void getView(@RequestParam("bno") int bno, Model model)
	
}
