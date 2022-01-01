package com.edu.board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.edu.board.domain.CommentDTO;

@Repository
public class CommentDAOImpl implements CommentDAO {
	
	@Inject
	SqlSession sqlSession;
     
	// namespace 이름 정확하게 작성할 것 : 조심하자!
	private static String namespace = "com.edu.mealkit.mapper.commentMapper";
   
	//------------------------------------
	// 댓글목록
	//------------------------------------
	@Override
	public List<CommentDTO> list(Integer bno) throws Exception {
		return sqlSession.selectList(namespace+".listComment", bno);
		
	} // end List<CommentDTO> list(Integer bno)
	
	//------------------------------------
	// 댓글입력
	//------------------------------------
	@Override
	public void insert(CommentDTO dto) throws Exception {
		sqlSession.insert(namespace + ".insertComment", dto);
		
	} // end void insert(CommentDTO dto)
	
	//------------------------------------
	// 댓글상세보기
	//------------------------------------
	@Override
	public CommentDTO detail(int idx) throws Exception {
		return sqlSession.selectOne(namespace+".detailComment", idx);
		
	} // end CommentDTO detail(int idx)

	//-----------------------------------
	// 댓글수정
	//-----------------------------------
	@Override
	public void update(CommentDTO dto) throws Exception {
		sqlSession.update(namespace+".updateComment", dto);
		
	} // end void update(CommentDTO dto)
	
	//-----------------------------------
	// 댓글삭제
	//-----------------------------------
	@Override
	public int delete(Integer idx) throws Exception {
		return sqlSession.delete(namespace+".deleteComment", idx);	
		
	} // end int delete(Integer idx)
	
	//-----------------------------------
	// 매니저 댓글삭제
	//-----------------------------------
	@Override
	public String findResult(int idx) throws Exception {
		return sqlSession.selectOne(namespace+".findResult", idx);
		
	} // end String findResult(int idx)

	
} // end CommentDAOImpl implements CommentDAO
