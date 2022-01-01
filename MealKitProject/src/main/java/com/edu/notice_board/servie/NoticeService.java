package com.edu.notice_board.servie;

import java.util.List;

import com.edu.notice_board.domain.NoticeVO;

public interface NoticeService {

	public List<NoticeVO> noticelist() throws Exception;
	
	//-------------------------------------------------------------------------------------------------
	// 글번호에 해당하는 게시글의 상세정보를 가져온다.
	//-------------------------------------------------------------------------------------------------
	public NoticeVO noticeview(int bno) throws  Exception;
	
}
