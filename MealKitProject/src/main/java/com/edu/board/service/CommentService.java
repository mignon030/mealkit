package com.edu.board.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.edu.board.dao.CommentDAO;
import com.edu.board.domain.CommentDTO;

public interface CommentService {
	
	// 댓글목록
	public List<CommentDTO> list(Integer bno) throws Exception;
	
	// 댓글입력
	public void insert(CommentDTO dto) throws Exception;
	
	// 댓글상세보기
	public CommentDTO detail(int idx) throws Exception;
	
	// 댓글수정
	public void update(CommentDTO dto) throws Exception;
	
	// 댓글삭제
	public int delete(Integer idx) throws Exception;
	
	// 매니저 댓글삭제
	public String findResult(int idx) throws Exception;

	
} // end interface CommentService
