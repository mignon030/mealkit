package com.edu.mealkit.dao;

import com.edu.mealkit.dto.MemberDTO;

public interface MealkitDAO {

	// 아이디 중복 검사
	public int idCheck(MemberDTO memberDTO) throws Exception;
	
	// 회원가입 POST (Ajax)
	public void register(MemberDTO memberDTO) throws Exception;	
	
	// 중복 회원가입 방지
	public int phoneCheck(MemberDTO memberDTO) throws Exception;
	
	// 로그인
	public MemberDTO login(MemberDTO memberDTO) throws Exception;
	
	// 비밀번호 찾기
	public int findPass(MemberDTO memberDTO) throws Exception;
	
	// 비밀번호 새로 저장
	public void newPass(MemberDTO memberDTO) throws Exception;
	
	// 회원탈퇴
	public void delete(MemberDTO memberDTO) throws Exception;
	
    // 아이디찾기
	public String findId(MemberDTO memberDTO) throws Exception;
	
	// 회원정보수정
	public void updateMypage(MemberDTO memberDTO) throws Exception;
	
	// 추천인 넣기
	public MemberDTO cc_id(String id, String ccid) throws Exception;
	
	// 추천인 멤버 여부
	public int findccname(String id) throws Exception;
	
	// 본인 아이디 추천인 추천 불가
	public String findCC_id(String id) throws Exception;
	
	// 추천인 기입 여부
	public int ccTrue(String ccTure) throws Exception;
	
} // end interface MealkitDAO
