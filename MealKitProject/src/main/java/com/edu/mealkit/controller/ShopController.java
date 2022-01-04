package com.edu.mealkit.controller;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.edu.file.UploadFileUtils;
import com.edu.mealkit.dto.BuyDTO;
import com.edu.mealkit.dto.CartDTO;
import com.edu.mealkit.dto.Criteria;
import com.edu.mealkit.dto.MealkitDTO;
import com.edu.mealkit.dto.MealkitKindDTO;
import com.edu.mealkit.dto.MemberDTO;
import com.edu.mealkit.dto.PageMaker;
import com.edu.mealkit.dto.RV_Criteria;
import com.edu.mealkit.dto.RV_PageMaker;
import com.edu.mealkit.dto.ReviewVO;
import com.edu.mealkit.dto.SearchCriteria;
import com.edu.mealkit.dto.ZzimDTO;
import com.edu.mealkit.service.ManagerService;
import com.edu.mealkit.service.ShopService;

// import net.sf.json.JSONArray;

@Controller
@RequestMapping("/shop")
public class ShopController {
	
	private static final Logger logger = LoggerFactory.getLogger(ShopController.class);
	
	@Inject
	private ShopService shopService;
	
	@Inject
	private ManagerService managerService;
	
	@Autowired
    @Resource(name="uploadPath")
    String uploadPath;
	
	//-------------------------------------------------------------------------------------------------
	// 반찬보기_list 페이지로 이동
	//-------------------------------------------------------------------------------------------------
	@RequestMapping("/goods_list")
	private String goodslist(Model model) throws Exception {
		
		List<MealkitKindDTO> mealkitKindDTO = managerService.getKind();
		model.addAttribute("kinds", mealkitKindDTO);
		
		// 반찬 kind대로 뿌려주기
		List<MealkitDTO> meals = shopService.allMeals();
		
		// 찜하기 버튼을 위한 mealkitDTO
		//MealkitDTO mealkitDTO = shopService.getGoodsView(mk_id);
		
		model.addAttribute("allMeals", meals);
		
		return "shop/goods_list";
		
	} // end String goodslist(Model model)
	
	//-------------------------------------------------------------------------------------------------
	// 반찬보기_list에서 서치했을 때
	//-------------------------------------------------------------------------------------------------
	@RequestMapping("/goods_list_search")
	private String goodslist(Model model, SearchCriteria scri) throws Exception {
		
		List<MealkitKindDTO> mealkitKindDTO = managerService.getSearchKind(scri);
		model.addAttribute("kind", mealkitKindDTO);
		
		List<MealkitKindDTO> mealkitKinds = managerService.getKind();
		model.addAttribute("kinds", mealkitKinds);
		
		// 반찬 kind대로 뿌려주기
		List<MealkitDTO> meals = shopService.searchMeals(scri);
		
		model.addAttribute("allMeals", meals);
		
		return "shop/goods_list";
		
	} // end String goodslist(Model model, SearchCriteria scri)


	//--------------------------------------------------------------------
	// kind_name별 목록 조회 메소드
	//-------------------------------------------------------------------
	@RequestMapping(value="/goods_kind")
	public String goods_kind(Model model, String kind_name) throws Exception{
	
		List<MealkitKindDTO> mealkitKindDTO = managerService.getKind();
		
		MealkitKindDTO kind = managerService.getKind(kind_name);
		model.addAttribute("kind",mealkitKindDTO);
		model.addAttribute("kindView",kind);
		model.addAttribute("listPage", shopService.goodslist(kind_name));
		 
		return "shop/goods_kind";
		
	} // end String goods_kind(Model model, String kind_name)
	
	//-------------------------------------------------------------------------------------------------
	// 반찬보기_view 페이지로 이동
	//-------------------------------------------------------------------------------------------------
	@RequestMapping("/goods_view")
	public String goodsView(@RequestParam("mk_id") int mk_id, Model model, BuyDTO buyDTO, HttpSession session) throws Exception {
		
		MealkitDTO mealkitDTO = shopService.getGoodsView(mk_id);
		List<MealkitKindDTO> mealkitKindDTO = managerService.getKind();
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		
		if (memberDTO == null) {
			model.addAttribute("mealkit", mealkitDTO);
			model.addAttribute("kind", mealkitKindDTO);
			
			List<ReviewVO> reviewList = shopService.readReview(mealkitDTO.getMk_id());
			model.addAttribute("reviewList", reviewList);
			
			return "shop/goods_view";
			
		} else {	
			
			buyDTO.setMember_id(memberDTO.getId());
			int result = shopService.RJJ(buyDTO);
			
			if (result >= 1) { // 리뷰가 있으면
				model.addAttribute("msg", true);
				model.addAttribute("mealkit", mealkitDTO);
				model.addAttribute("kind", mealkitKindDTO);
				
				List<ReviewVO> reviewList = shopService.readReview(mealkitDTO.getMk_id());
				model.addAttribute("reviewList", reviewList);
				
			} else {
				model.addAttribute("msg", false);
				model.addAttribute("mealkit", mealkitDTO);
				model.addAttribute("kind", mealkitKindDTO);
			
				List<ReviewVO> reviewList = shopService.readReview(mealkitDTO.getMk_id());
				model.addAttribute("reviewList", reviewList);
			}
			
			return "shop/goods_view";
		}
		
	} // end String goodsView(@RequestParam("mk_id") int mk_id, Model model, BuyDTO buyDTO, HttpSession session)
	
	//-------------------------------------------------------------------------------------------------
	// 재고수량 초과 금지 ajax
	//-------------------------------------------------------------------------------------------------
	@ResponseBody
	@RequestMapping(value="goods_count", method = RequestMethod.POST)
	public int goodsCount(int cnt, int mk_id) throws Exception {
		
		int result = shopService.getBuy_count(cnt, mk_id);
		return result;
		
	} // end int goodsCount(int cnt)
	
	//-------------------------------------------------------------------------------------------------
	// 마이페이지 -> 제품 페이지 이동
	//-------------------------------------------------------------------------------------------------
	@RequestMapping(value="/goReviewPage", method=RequestMethod.GET)
	public void goReviewPage(MemberDTO memberDTO, HttpSession session, Model model) throws Exception {
		
		memberDTO = (MemberDTO)session.getAttribute("member");
		model.addAttribute("member", memberDTO);
		
		//-----------------------------------------------------------------------------------------------------------
		// 구매내역 모두 찾는 메서드
		//-----------------------------------------------------------------------------------------------------------
		List<BuyDTO> buys = shopService.getBuys(memberDTO.getId());
		model.addAttribute("buys", buys);
		
	} // end void goReviewPage(MemberDTO memberDTO, HttpSession session, Model model)
	
	//-------------------------------------------------------------------------------------------------
	// 리뷰 목록
	//-------------------------------------------------------------------------------------------------
	@ResponseBody
	@RequestMapping("readReview")
	// 리턴데이터를 json으로 변환
	public List<ReviewVO> readReview(@RequestParam int r_mkno) throws Exception {
		List<ReviewVO> list = shopService.readReview(r_mkno);
		return list;
		
	} // end List<ReviewVO> readReview(@RequestParam int r_mkno)
	
	//-------------------------------------------------------------------------------------------------
	// 리뷰쓰기
	//-------------------------------------------------------------------------------------------------
	@ResponseBody
	@RequestMapping("writeReview")
	public void writeReview(ReviewVO reviewVO) throws Exception {
		shopService.writeReview(reviewVO);
		
	} // end void writeReview(ReviewVO reviewVO)
	
	//-------------------------------------------------------------------------------------------------
	// 리뷰삭제
	//-------------------------------------------------------------------------------------------------
	@ResponseBody
	@RequestMapping(value="/deleteReview/{r_no}",method = RequestMethod.POST)
	public int deleteReview(@PathVariable int r_no, HttpSession session, ReviewVO reviewVO) throws Exception {
	   
		int result = 0;
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		String memberId = shopService.findResult(reviewVO.getR_no());
		
		if(memberDTO.getId().equals(memberId)) {
			reviewVO.setR_writer(memberDTO.getId());
			shopService.deleteReview(reviewVO.getR_no());
			result = 1;
		}
		
		return result;
		
	} // end int deleteReview(@PathVariable int r_no, HttpSession session, ReviewVO reviewVO)
	
	//-------------------------------------------------------------------------------------------------
	// 장바구니 페이지로 이동
	//-------------------------------------------------------------------------------------------------
	@RequestMapping("/cart")
	public String cart(CartDTO cartDTO, Model model, HttpSession session) throws Exception {
		
		//-------------------------------------------------------------------------------------------------
		// 장바구니 접속 시 로그인 유무에 따른 메서드
		//-------------------------------------------------------------------------------------------------
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		
		if(memberDTO == null) {
			model.addAttribute("login", "need");
			return "mealkit/login";
		}
		
		cartDTO.setBuy_id(memberDTO.getId());

		//-------------------------------------------------------------------------------------------------
		// 장바구니 개수를 찾는 메서드
		//-------------------------------------------------------------------------------------------------
		int cnt = shopService.getCartCnt(cartDTO.getBuy_id());
		
		if(cnt < 1) {
			model.addAttribute("cnt", "zero");
		} else {
			//-------------------------------------------------------------------------------------------------
			// 장바구니 상품들 찾는 메서드
			//-------------------------------------------------------------------------------------------------
			List<CartDTO> carts = shopService.showCarts(cartDTO);
			
			List<MealkitDTO> mealkits = new ArrayList<MealkitDTO>();
			  
			for(int i =0; i< carts.size(); i++) {
				mealkits.add(shopService.productMkCount(carts.get(i).getMk_id()));
			}
			
			model.addAttribute("cnt", "noze");
			model.addAttribute("carts", carts);
			model.addAttribute("mealkits",mealkits);
		}
		
		return "shop/cart";
		
	} // end String cart(MemberDTO memberDTO, Model model)
	
	//-------------------------------------------------------------------------------------------------
	// goods_view에서 장바구니로 담아주는 메서드
	//-------------------------------------------------------------------------------------------------
	@RequestMapping(value="/goCart", method=RequestMethod.POST)
	public String goCart(CartDTO cartDTO, Model model) throws Exception {
		      
		if(cartDTO.getBuy_id() == "") {
			model.addAttribute("login", "need");
			return "mealkit/login";      
		}
		
		// 장바구니에 기존 상품이 있는지 검사      
		int count = shopService.countCarts(cartDTO);
		  
		if(count == 0) {
			// 장바구니에 중복되는 상품이 없으면 장바구니에 담아주기(getCarts)
			shopService.getCarts(cartDTO);   
		} else {
			// 장바구니에 중복되는 상품이 있으면 알려주고 갯수 합치기 (updateCarts)
			shopService.updateCarts(cartDTO);
		}
		return "redirect:/shop/cart";
		      
	} // end String goCart(CartDTO cartDTO)
	
	//-------------------------------------------------------------------------------------------------
	// 찜한 상품에서 장바구니로 담아주는 ajax ~ 아이콘 클릭할 때
	//-------------------------------------------------------------------------------------------------
	@ResponseBody
	@RequestMapping(value="/zzimGo", method=RequestMethod.POST)
	public String zzimGo(ZzimDTO zzimDTO) throws Exception {
	    	
		CartDTO carts = new CartDTO();
		carts.setBuy_id(zzimDTO.getBuy_id());
		carts.setMk_id(zzimDTO.getMk_id());
		carts.setBuy_price(zzimDTO.getMk_price());
		carts.setBuy_count(1);
	    	
		//-------------------------------------------------------------------------------------------------
		// 장바구니 중복되는지 찾는 메서드
		//-------------------------------------------------------------------------------------------------
    	int count = shopService.countCarts(carts);
  		
  		if(count == 0) { // 장바구니에 중복되는 상품이 없으면 장바구니에 담아주기
     		
       		shopService.zzimGo(zzimDTO.getZzim_id());
    		
    		//-------------------------------------------------------------------------------------------------
        	// 장바구니 담아준 후 삭제하기 메서드
        	//-------------------------------------------------------------------------------------------------
            shopService.zzDelete(Integer.toString(zzimDTO.getZzim_id()));
            
  		} else {

 			shopService.updateCarts(carts);
 			
 			//-------------------------------------------------------------------------------------------------
        	// 장바구니 담아준 후 삭제하기 메서드
        	//-------------------------------------------------------------------------------------------------
            shopService.zzDelete(Integer.toString(zzimDTO.getZzim_id()));
  		}
     	return "sumCart";
     	
	} // end String zzimGo(ZzimDTO zzimDTO)
	
	//-------------------------------------------------------------------------------------------------
	// 찜한 상품에서 장바구니로 담아주는 ajax ~ 체크박스 선택 후 배열로 넘겨주기
	//-------------------------------------------------------------------------------------------------
	@ResponseBody
	@RequestMapping(value="/arrZzimgo", method=RequestMethod.POST)
	public String zzimGo(int[] zzimId, String[] buy_id, int[] mk_id, int[] mk_price) throws Exception {
	    	
		for(int i=0; i < zzimId.length; i++) {
		    	
	    	CartDTO carts = new CartDTO();
	    	carts.setBuy_id(buy_id[i]);
	    	carts.setMk_id(mk_id[i]);
	    	carts.setBuy_price(mk_price[i]);
	    	carts.setBuy_count(1);
	    	
			//-------------------------------------------------------------------------------------------------
			// 장바구니 중복되는지 찾는 메서드
			//-------------------------------------------------------------------------------------------------
	    	int count = shopService.countCarts(carts);
	  		
	  		if(count == 0) { // 장바구니에 중복되는 상품이 없으면 장바구니에 담아주기
	     		
        		shopService.zzimGo(zzimId[i]);
        		
        		//-------------------------------------------------------------------------------------------------
            	// 장바구니 담아준 후 삭제하기 메서드
            	//-------------------------------------------------------------------------------------------------
                shopService.zzDelete(Integer.toString(zzimId[i]));
                
	  		} else {
         		
     			shopService.updateCarts(carts);
     			
     			//-------------------------------------------------------------------------------------------------
            	// 장바구니 담아준 후 삭제하기 메서드
            	//-------------------------------------------------------------------------------------------------
                shopService.zzDelete(Integer.toString(zzimId[i]));
	  		}   
	  		
		} // end for(int i=0; i < zzimdto.length; i++)
		
		return "sumCart";
	} // end String zzimGo(int[] zzimId, String[] buy_id, int[] mk_id, int[] mk_price)
    	
	/*
	//-------------------------------------------------------------------------------------------------
	// 찜에서 장바구니로 배열 json써서 하는 거
	//-------------------------------------------------------------------------------------------------
	@ResponseBody
	@RequestMapping(value="/zzimGo", method=RequestMethod.POST)
	public Map<String, Object> zzimGo(@RequestParam String zzimDTO) throws Exception {
		 
		 Map<String, Object> result = new HashMap<>();
		  try {
		    List<Map<String,Object>> info = new ArrayList<Map<String,Object>>();
		    info = JSONArray.fromObject(zzimDTO);

		    for (Map<String, Object> zzim : info) {
		    	logger.info("^^이것");
		    	
		    	logger.info("배열 선택하기? info ?? " +info);

        		logger.info("중복 없으면...장바구니 담아줘 " + zzim);
        		String a = (String)zzim.get("zzim_id");
        		shopService.zzimGo(Integer.parseInt(a));
        		logger.info("짐");
        		
        		//-------------------------------------------------------------------------------------------------
            	// 장바구니 담아준 후 삭제하기 메서드
            	//-------------------------------------------------------------------------------------------------
        		shopService.zzDelete(a);
		    }  
		      result.put("result", true);
		  } catch (Exception e) {
		      result.put("result", false);
		  }
		  return result;
		
	} // Map<String, Object> zzimGo(@RequestParam String zzimDTO)
	*/
	
	//-------------------------------------------------------------------------------------------------
	// 장바구니 상품들 업데이트 하는 메서드
	//-------------------------------------------------------------------------------------------------
	@ResponseBody
	@RequestMapping(value="/goUpdate", method=RequestMethod.POST)
	public int goUpdate(CartDTO cartDTO) throws Exception {
		int result = shopService.goUpdate(cartDTO);
		return result;
		
	} // end int goUpdate(CartDTO cartDTO)
			
	//-------------------------------------------------------------------------------------------------
	// 구매하기에서 goPay로 가는 메서드 : GET
	//-------------------------------------------------------------------------------------------------
	@RequestMapping(value="/goPay", method=RequestMethod.GET)
	public String getPay(int[] mk_id, int[] RowCheck, HttpSession session, Model model) throws Exception {
		
		//-------------------------------------------------------------------------------------------------
		// 장바구니 접속 시 로그인 유무에 따른 메서드
		//-------------------------------------------------------------------------------------------------
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		
		if(memberDTO == null) {
			model.addAttribute("login", "need");
			return "mealkit/login";
		}
		
		CartDTO cartDTO = new CartDTO();
		cartDTO.setBuy_id(memberDTO.getId());
		
		if(RowCheck == null) {
			//-------------------------------------------------------------------------------------------------
			// 장바구니 상품들 찾는 메서드
			//-------------------------------------------------------------------------------------------------
			List<CartDTO> carts = shopService.showCarts(cartDTO);
			model.addAttribute("carts", carts);
			
			//-------------------------------------------------------------------------------------------------
			// 장바구니 총 가격을 찾는 메서드
			//-------------------------------------------------------------------------------------------------
			int allMoney = shopService.sumCart(cartDTO.getBuy_id());
			model.addAttribute("allMoney", allMoney);

		} else {
			//-------------------------------------------------------------------------------------------------
			// 선택한 장바구니 상품들 찾는 메서드
			//-------------------------------------------------------------------------------------------------
			List<CartDTO> carts = new ArrayList<CartDTO>();
			
			for(int i=0; i < RowCheck.length; i++) {
				
				CartDTO cart = new CartDTO();
				cart.setBuy_id(memberDTO.getId());
				cart.setCart_id(RowCheck[i]);
				
				carts.add(shopService.choiceBuy(cart));
			}

			model.addAttribute("carts", carts);
			
			//-------------------------------------------------------------------------------------------------
			// 선택한 장바구니 총 가격을 찾는 메서드
			//-------------------------------------------------------------------------------------------------
			int allMoney = 0;
			
			for(int i=0; i < RowCheck.length; i++) {
				allMoney += shopService.sumChoice(RowCheck[i]);
			}

			model.addAttribute("allMoney", allMoney);
			model.addAttribute("RowCheck", RowCheck);
			model.addAttribute("mk_id", mk_id);
		}
		
		return "/shop/goPay";
		
	} // end String getPay(int[] mk_id, int[] RowCheck, HttpSession session, Model model)
	
	//-------------------------------------------------------------------------------------------------
	// 구매하기에서 goPay로 가는 메서드 : POST
	//-------------------------------------------------------------------------------------------------
	@RequestMapping(value="/goPay", method=RequestMethod.POST)
	public String postPay(int[] mk_id, int[] RowCheck, BuyDTO buyDTO, HttpSession session, Model model) throws Exception {
		
		//-------------------------------------------------------------------------------------------------
		// 장바구니 접속 시 로그인 유무에 따른 메서드
		//-------------------------------------------------------------------------------------------------
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		
		if(memberDTO == null) {
			model.addAttribute("login", "need");
			return "mealkit/login";
		}
		
		CartDTO cartDTO = new CartDTO();
		cartDTO.setBuy_id(memberDTO.getId());
		buyDTO.setMember_id(memberDTO.getId());
		
		//-------------------------------------------------------------------------------------------------
		// order_id 주문번호 만드는 메서드
		//-------------------------------------------------------------------------------------------------
		buyDTO.setMember_id(memberDTO.getId());
        
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		String ym = year + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
		String ymd = ym + new DecimalFormat("00").format(cal.get(Calendar.DATE));
		String subNum = "";
  
		for(int i = 1; i <= 6; i ++) {
			subNum += (int)(Math.random() * 10);
		}
  
		String order_id = ymd + "_" + subNum;
        
		buyDTO.setOrder_id(order_id);

		if(RowCheck == null) {
			
			//-------------------------------------------------------------------------------------------------
			// 장바구니 상품들을 Buy테이블에 담는 메서드
			//-------------------------------------------------------------------------------------------------
			shopService.insertPay(buyDTO);
						
			//-------------------------------------------------------------------------------------------------
			// 장바구니 모두 삭제하기
			//-------------------------------------------------------------------------------------------------
			shopService.allDelete(cartDTO.getBuy_id());

		} else {
			
			//-------------------------------------------------------------------------------------------------
			// 선택한 장바구니 상품들을 Buy테이블에 담는 메서드
			//-------------------------------------------------------------------------------------------------
			for(int i=0; i < RowCheck.length; i++) {
				buyDTO.setMk_id(mk_id[i]);
				shopService.insertChoicePay(buyDTO);
			}
			
			//-------------------------------------------------------------------------------------------------
			// 장바구니에서 선택한 거 삭제하기
			//-------------------------------------------------------------------------------------------------
			for(int i=0; i < RowCheck.length; i++) {
				cartDTO.setMk_id(mk_id[i]);
				shopService.choiceDelete(cartDTO);
			}
			
		}

		return "redirect:/mealkit/myPage";
		
	} // end String postPay(int[] mk_id, int[] RowCheck, BuyDTO buyDTO, HttpSession session, Model model)
	
	//-------------------------------------------------------------------------------------------------
	// 선택 삭제하기 - 장바구니에서
	//-------------------------------------------------------------------------------------------------
	@ResponseBody
	@RequestMapping(value="/delete")
    public String ajaxTest(HttpServletRequest request) throws Exception {
            
        String[] ajaxMsg = request.getParameterValues("valueArr");
        
        int size = ajaxMsg.length;
        
        for(int i=0; i<size; i++) {
        	shopService.delete(ajaxMsg[i]);
        }
        return "cc";
        
    } // end String ajaxTest(HttpServletRequest request)
	
	//-------------------------------------------------------------------------------------------------
	// 선택 삭제하기 - 찜에서
	//-------------------------------------------------------------------------------------------------
	@ResponseBody
	@RequestMapping(value="/zzDelete")
    public String zzDelete(HttpServletRequest request) throws Exception {
            
        String[] zzim_id = request.getParameterValues("valueArr");
        
        int size = zzim_id.length;
        
        for(int i=0; i<size; i++) {
        	shopService.zzDelete(zzim_id[i]);
        }
        return "zz";
        
    } // end String zzDelete(HttpServletRequest request)
	
	//-------------------------------------------------------------------------------------------------
	// goods_view에서 찜하기버튼 클릭 시 비로그인했을 때 로그인 페이지로 가는 메서드
	//-------------------------------------------------------------------------------------------------
	@RequestMapping("/goZzim")
	public String goZzim(Model model) throws Exception {
		
		model.addAttribute("login","need");
		return "mealkit/login";
		
	} // end String goZzim(Model model)
	
	//-------------------------------------------------------------------------------------------------
	// 찜여부체크
	//-------------------------------------------------------------------------------------------------
	@ResponseBody
	@RequestMapping("/zzimCheck")
	public int zzimCheck(ZzimDTO zzimDTO, Model model) throws Exception{
		
		boolean result = shopService.zzimCheck(zzimDTO.getMk_id()); 
		int count =0;
		
		if(result==true) {
			count = 1;
		} else { // false면 즉 값이없었으면!!!! 
			shopService.getZzims(zzimDTO); 
			model.addAttribute("result",count);
		}
			
		return count;
		
	} // end int zzimCheck(@ModelAttribute ZzimDTO zzimDTO,Model model)
	
	//-------------------------------------------------------------------------------------------------
	// 찜한상품 페이지로 이동하는 메소드
	//-------------------------------------------------------------------------------------------------
	@RequestMapping("/zzim")
	public String zzim(ZzimDTO zzimDTO,Model model, HttpSession session) throws Exception {
		
		//-------------------------------------------------------------------------------------------------
		// 찜한 상품 로그인 유무에 따른 메서드
		//-------------------------------------------------------------------------------------------------
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		
		if(memberDTO==null) {
			model.addAttribute("login", "need");
			return "mealkit/login";
		}
		
		zzimDTO.setBuy_id(memberDTO.getId());
		
		//-------------------------------------------------------------------------------------------------
		// 찜하기 개수를 찾는 메서드
		//-------------------------------------------------------------------------------------------------
		int cnt = shopService.getZzimCnt(zzimDTO.getBuy_id());
		  
		if(cnt < 1) {
			model.addAttribute("cnt", "zero");
		} else {
			List<ZzimDTO> zzim = shopService.showZzims(zzimDTO); //SELECT해옴
			model.addAttribute("cnt", "noze");
			model.addAttribute("zzims", zzim);
		}
		
		return "shop/zzim";
		
	} // end zzim(ZzimDTO zzimDTO,Model model, HttpSession session)
	
	//-------------------------------------------------------------------------------------------------
	// 모든리뷰목록
	//-------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/allReviewList")
	private String allReviewList(Model model, RV_Criteria cri) throws Exception {
		
		List<ReviewVO> reviewList = shopService.allReviewList(cri);
		
		model.addAttribute("list", reviewList);
		
		RV_PageMaker RV_pageMaker = new RV_PageMaker();
		RV_pageMaker.setRv_cri(cri);
		RV_pageMaker.setTotalCount(shopService.reviewCount());
		
		model.addAttribute("pageMaker", RV_pageMaker);

		return "/shop/allReviewList";
		
	} // end String allReviewList(Model model, RV_Criteria cri)

} // end class ShopController
