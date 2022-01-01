package com.edu.mealkit.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.edu.mealkit.dto.MealkitDTO;
import com.edu.mealkit.dto.MealkitKindDTO;
import com.edu.mealkit.dto.MemberDTO;
import com.edu.mealkit.dto.SearchCriteria;
import com.edu.mealkit.dto.M_Criteria;
import com.edu.mealkit.dto.BuyDTO;
import com.edu.mealkit.dto.ManagerDTO;

@Repository
public class ManagerDAOImpl implements ManagerDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(MealkitDAOImpl.class);

	@Inject
	SqlSession sqlSession;
	
	private static final String namespace = "com.edu.mealkit.mapper.managerMapper";
	
	//-----------------------------------------------------------------------------------------------------------
	// 매니저 로그인
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public ManagerDTO managerLogin(ManagerDTO managerDTO) throws Exception {
		return sqlSession.selectOne(namespace + ".managerLogin", managerDTO);
		
	} // end managerDTO managerLogin(managerDTO managerDTO)
	
	//-----------------------------------------------------------------------------------------------------------
	// 밀키트 이름 중복 검사
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public int mkCheck(MealkitDTO mealkitDTO) throws Exception {
		return sqlSession.selectOne(namespace + ".mkCheck", mealkitDTO);
		
	} // end int mkCheck(MealkitDTO mealkitDTO)
	
	//-----------------------------------------------------------------------------------------------------------
	// 제품 등록
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public void productRegister(MealkitDTO mealkitDTO) throws Exception {
		sqlSession.insert(namespace + ".register", mealkitDTO);

	} // End - public void productRegister(MealkitDTO mealkitDTO)

	//-----------------------------------------------------------------------------------------------------------
	// 제품 종류 가져오기
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public List<MealkitKindDTO> getKind() throws Exception {
		return sqlSession.selectList(namespace + ".kind");
		
	} // end List<MealkitKindDTO> getKind()
	
	//-----------------------------------------------------------------------------------------------------------
	// 제품 종류 가져오기
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public List<MealkitKindDTO> getSearchKind(SearchCriteria scri) throws Exception {
		return sqlSession.selectList(namespace + ".searchkind", scri);
		
	} // end List<MealkitKindDTO> getSearchKind(SearchCriteria scri)
	
	//-----------------------------------------------------------------------------------------------------------
	// kind_name에따른 제품가져오기
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public MealkitKindDTO getKind(String kind_name) throws Exception{
		return sqlSession.selectOne(namespace+".getKind",kind_name);
		
	} // end MealkitKindDTO getKind(String kind_name)
	
	//-----------------------------------------------------------------------------------------------------------
	// 제품 목록
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public List<MealkitDTO> productList(M_Criteria cri) throws Exception {
		return sqlSession.selectList(namespace + ".productlist", cri);
		
	}  // end List<MealkitDTO> productList(m_Criteria cri)
	
	//-------------------------------------------------------------------------------------------------
	// 제품 총 개수
	//-------------------------------------------------------------------------------------------------
	@Override
	public int listCount() throws Exception {
		return sqlSession.selectOne(namespace + ".productlistCount");
		
	} // end int listCount()
	
	//-------------------------------------------------------------------------------------------------
	// 구매 제품 총 개수
	//-------------------------------------------------------------------------------------------------
	@Override
	public int orderListCount() throws Exception {
		return sqlSession.selectOne(namespace + ".orderListCount");
		
	} // end int orderListCount()

	//-----------------------------------------------------------------------------------------------------------
	// 밀키트 제품 번호에 해당하는 게시글의 상세정보를 가져온다.
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public MealkitDTO productView(int mk_id) throws Exception {
		return sqlSession.selectOne(namespace + ".productview", mk_id);
		
	} // End - public MealkitDTO productView(int mk_id)

	//-------------------------------------------------------------------------------------------------
	// 제품 번호에 해당하는 게시글의 내용을 수정한다.
	//-------------------------------------------------------------------------------------------------
	@Override
	public void productUpdate(MealkitDTO mealkitDTO) throws Exception {
		sqlSession.update(namespace + ".productupdate", mealkitDTO);
		
	} // End - public void productUpdate(MealkitDTO mealkitDTO)

	//-------------------------------------------------------------------------------------------------
	// 게시글 번호에 해당하는 게시글의 정보를 삭제한다. : POST
	//-------------------------------------------------------------------------------------------------
	@Override
	public void productDelete(int mk_id) throws Exception {
		sqlSession.delete(namespace + ".productdelete", mk_id);
		
	} // End - public void productDelete(int mk_id) throws Exception
	
	//-------------------------------------------------------------------------------------------------
	// 매니저가 회원목록을 조회한다
	//-------------------------------------------------------------------------------------------------
	@Override
	public List<MemberDTO> memberListView() throws Exception{
		return sqlSession.selectList(namespace+".memberListView");
		
	} // end List<MemberDTO> memberListView()
	
	//-------------------------------------------------------------------------------------------------
	// 매니저가 특정한 회원정보를 수정 한다
	//-------------------------------------------------------------------------------------------------
	@Override
	public void memberUpdate(MemberDTO memberDTO) throws Exception{
		 sqlSession.update(namespace+ ".memberUpdate",memberDTO);
		 
	} // end void memberUpdate(MemberDTO memberDTO)
	
	//-------------------------------------------------------------------------------------------------
	// 매니저가 특정한 회원을 삭제한다
	//-------------------------------------------------------------------------------------------------
	@Override
	public void memberDelete(String id) throws Exception{
		 sqlSession.delete(namespace+".memberDelete",id);
		 
	} // end void memberDelete(String id)
	
	//-----------------------------------------------------------------------------------------------------------
	// 주문 받은 밀키트 목록
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public List<BuyDTO> orderList(M_Criteria cri) throws Exception {
		return sqlSession.selectList(namespace + ".orderlist", cri);
		
	} // end List<BuyDTO> orderList(m_Criteria cri)
	
 	//-------------------------------------------------------------------------------------------------
	// 주문 상세정보를 가져온다.
	//-------------------------------------------------------------------------------------------------
	@Override
	public List<BuyDTO> orderView(String order_id) throws Exception {
		return sqlSession.selectList(namespace + ".orderView", order_id);
		
	} // end List<BuyDTO> orderView(String order_id)

	//-----------------------------------------------------------------------------------------------------------
	// 장바구니 총 가격 찾기
	//-----------------------------------------------------------------------------------------------------------
	@Override
	public int sumBuy(String order_id) throws Exception {
		return sqlSession.selectOne(namespace + ".sumBuy", order_id);
		
	} // end int sumBuy(String order_id)
	
	//-------------------------------------------------------------------------------------------------
	// 배송 상태 변경
	//-------------------------------------------------------------------------------------------------
	@Override
	public void delivery(BuyDTO buyDTO) throws Exception {
		sqlSession.update(namespace + ".delivery", buyDTO);
		
	} // end void delivery(BuyDTO buyDTO)

	//-------------------------------------------------------------------------------------------------
	// 밀키트 재고 수량 조절
	//-------------------------------------------------------------------------------------------------
	public void changeCount(MealkitDTO mealkitDTO) throws Exception {
		 sqlSession.update(namespace+".changeCount", mealkitDTO);
		 
	} // end void changeCount(MealkitDTO mealkitDTO)

	
} // end class managerDAOImpl implements managerDAO
