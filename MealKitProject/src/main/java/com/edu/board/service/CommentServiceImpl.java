package com.edu.board.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.edu.board.dao.CommentDAO;
import com.edu.board.domain.CommentDTO;

@Service
public class CommentServiceImpl implements CommentService {
	@Inject
	CommentDAO commentDAO;
	  
	//-----------------------------------
	// 댓글목록
	//-----------------------------------
	@Override
	public List<CommentDTO> list(Integer bno) throws Exception {
		return commentDAO.list(bno);
		
	} // end List<CommentDTO> list(Integer bno)
	
	//-----------------------------------
	// 댓글입력
	//-----------------------------------
	@Override
	public void insert(CommentDTO dto) throws Exception {
		commentDAO.insert(dto);
		
	} // end void insert(CommentDTO dto)
	
	//-----------------------------------
	// 댓글상세보기
	//-----------------------------------
	@Override
	public CommentDTO detail(int idx) throws Exception {
		return commentDAO.detail(idx);
		
	} // end CommentDTO detail(int idx)
	
	//-----------------------------------
	// 댓글수정
	//-----------------------------------
	@Override
	public void update(CommentDTO dto) throws Exception {
		commentDAO.update(dto);
		
	} // end void update(CommentDTO dto)
	
	//-----------------------------------
	// 댓글삭제
	//-----------------------------------
	@Override
	public int delete(Integer idx) throws Exception {
		 return commentDAO.delete(idx); 
		
	} // end int delete(Integer idx)
	
	//-----------------------------------
	// 매니저 댓글삭제
	//-----------------------------------
	@Override
	public String findResult(int idx) throws Exception {
		return commentDAO.findResult(idx);
		
	} // end String findResult(int idx)

	
	
} // end class CommentServiceImpl implements CommentService
