package com.edu.board.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.edu.board.domain.CommentDTO;
import com.edu.board.service.CommentService;
import com.edu.mealkit.dto.MemberDTO;

@Controller
@RequestMapping("/comment/*")
public class CommentController {
	@Inject
	CommentService commentService;
   
	Logger logger = LoggerFactory.getLogger(CommentController.class);
   
	//---------------------------------------------------------
	// 댓글목록 보여주기(view를 리턴해준다)
	//---------------------------------------------------------
	@RequestMapping("list") //   /comment/list로 매핑됨
	public ModelAndView list(@RequestParam int bno,ModelAndView mav) throws Exception {
		
		List<CommentDTO> list =commentService.list(bno);
		//뷰이름 지정
		mav.setViewName("board/comment_list");  //board아래 comment_list.jsp로 view지정
		mav.addObject("list",list);
		
		//commet_list.jsp 로 포워딩
		return mav;
	   
	} // end ModelAndView list(@RequestParam int bno,ModelAndView mav)
   
	//---------------------------------------------------------
	// 댓글목록 보여주기(데이터를 리턴해준다)
	//---------------------------------------------------------
	@ResponseBody
	@RequestMapping("listJson")
	//리턴데이터를 json으로 변환
	public List<CommentDTO> listJson(@RequestParam int bno) throws Exception {
		List<CommentDTO> list = commentService.list(bno);
		return list;
		
	} // end List<CommentDTO> listJson(@RequestParam int bno)
	
	//---------------------------------------------------------
	// 댓글 입력
	//---------------------------------------------------------
	@ResponseBody
	@RequestMapping("insert")
	public void insert(CommentDTO dto) throws Exception {
		commentService.insert(dto);
		
	} // end void insert(CommentDTO dto)
	
	//---------------------------------------------------------
	// 댓글 상세보기
	//---------------------------------------------------------
	@RequestMapping(value="/detail/{idx}", method = RequestMethod.GET)
	public ModelAndView detail(@PathVariable("idx") int idx,CommentDTO dto ,ModelAndView mav) throws Exception {
		//갈 뷰이름 지정
		mav.setViewName("board/commentDetail");
		
		//뷰에 전달할 데이터 지정
		mav.addObject("dto",dto);
		
		//commentDetail.jsp로 포워딩
		return mav;
		
	} // end ModelAndView detail(@PathVariable("idx") int idx,CommentDTO dto ,ModelAndView mav)
  
	//------------------------------------------------------
	// 댓글 수정처리
	//------------------------------------------------------
	//put은 전체수정 patch는 일부수정
	@RequestMapping(value="/update/{idx}", method= {RequestMethod.PUT,RequestMethod.PATCH})
	public ResponseEntity<String> commentUpdate(@PathVariable("idx") int idx, CommentDTO dto) throws Exception {
		
		ResponseEntity<String> entity =null;
		
		try {
			dto.setIdx(idx);
		    commentService.update(dto);
		    //위에 실행문 성공 시
		    entity = new ResponseEntity<String>("success",HttpStatus.OK);
		    
		} catch (Exception e) {
		   
			e.printStackTrace();
		   //댓글수정안돼면
		   entity = new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);   
		}
		
		//수정처리 http상태메시지 리턴한다
		return entity;
	}
	
	//------------------------------------------------------
	// 댓글 삭제처리
	//------------------------------------------------------
	@ResponseBody
	@RequestMapping(value="/delete/{idx}")
	public int delete(@PathVariable int idx, HttpSession session) throws Exception {
      
		int result = 0;
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		
		String memberId = commentService.findResult(idx);
		if(memberDTO.getId().equals(memberId)) {
			commentService.delete(idx);
			result =1;
		} 
		
		return result;
		
	} // end int delete(@PathVariable int idx, HttpSession session)
   
	//------------------------------------------------------
	// 매니저 댓글 삭제처리
	//------------------------------------------------------
	@ResponseBody
	@RequestMapping(value="/m_delete/{idx}")
	public int delete(@PathVariable int idx) throws Exception {
		return commentService.delete(idx);
		
	} // end int delete(@PathVariable int idx)

} // end class CommentController
