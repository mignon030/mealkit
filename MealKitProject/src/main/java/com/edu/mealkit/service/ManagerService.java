package com.edu.mealkit.service;

import java.util.List;

import com.edu.mealkit.dto.MealkitDTO;
import com.edu.mealkit.dto.MealkitKindDTO;
import com.edu.mealkit.dto.MemberDTO;
import com.edu.mealkit.dto.SearchCriteria;
import com.edu.mealkit.dto.M_Criteria;
import com.edu.mealkit.dto.BuyDTO;
import com.edu.mealkit.dto.ManagerDTO;
 
public interface ManagerService {
	
	// 매니저 로그인
	public ManagerDTO managerLogin(ManagerDTO managerDTO) throws Exception;
	
	// 제품 등록
	public void productRegister(MealkitDTO mealkitDTO) throws Exception;
	
	// 밀키트 이름 중복 검사
	public int mkCheck(MealkitDTO mealkitDTO) throws Exception;

	// 제품 종류 가져오기
	public List<MealkitKindDTO> getKind() throws Exception;
	
	// 서치한 제품 종류 가져오기
	public List<MealkitKindDTO> getSearchKind(SearchCriteria scri) throws Exception;

	// kind_name에따른 제품 종류 가져오기
	public MealkitKindDTO getKind(String kind_name) throws Exception;
	
	// 제품 목록
	public List<MealkitDTO> productList(M_Criteria cri) throws Exception;
	
	// 제품 총 개수
	public int listCount() throws Exception;
	
	// 구매 제품 총 개수
	public int orderListCount() throws Exception;

	// 밀키트 제품번호에 해당하는 게시글의 상세정보를 가져온다.
	public MealkitDTO productView(int mk_id) throws  Exception;
	
	// 제품 번호에 해당하는 게시글의 내용을 수정한다.
	public void productUpdate(MealkitDTO mealkitDTO) throws Exception;

	// 게시글 번호에 해당하는 게시글의 정보를 삭제한다. : POST
	public void productDelete(int mk_id) throws Exception;
		
	// 매니저가 회원목록을 조회한다
	public List<MemberDTO> memberListView() throws Exception;
	
	// 특정한 회원정보를 수정한다
	public void memberUpdate(MemberDTO memberDTO) throws Exception;
	
	// 특정한 회원을 삭제한다
	public void memberDelete(String id) throws Exception;
	
	// 주문 받은 밀키트 목록
	public List<BuyDTO> orderList(M_Criteria cri) throws Exception;
	
	// 주문 상세정보를 가져온다.
	public List<BuyDTO> orderView(String order_id) throws Exception;

	// 장바구니 총 가격 찾기
	public int sumBuy(String order_id) throws Exception;
	
	// 배송 상태 변경
	public void delivery(BuyDTO buyDTO) throws Exception;
	
	// 밀키트 재고 수량 조절
	public void changeCount(MealkitDTO mealkitDTO) throws Exception;

	
} // end interface managerService
