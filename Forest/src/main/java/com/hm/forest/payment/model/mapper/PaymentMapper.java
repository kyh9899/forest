package com.hm.forest.payment.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hm.forest.member.model.vo.Cart;
import com.hm.forest.payment.model.vo.Delivery;
import com.hm.forest.payment.model.vo.Order;
import com.hm.forest.payment.model.vo.Payment;

@Mapper
public interface PaymentMapper {

	// 결제하기 클릭 시 입력되는 정보들
	// 1. 결제 정보 설정(입력)
	int insertPayment(Payment payment);

	// 2. 해당 결제에 관련된 주문정보 등록
	int insertOrdersByPaymentNo(Order order);

	// 3. 해당 결제에 관련된 배송정보 등록 
	int insertDeliveryByPaymentNo(@Param("paymentNo") int paymentNo, Delivery delivery);

	// 결제 성공 시 
	// 1. 재고 변경_주문수량 조회
	List<Order> getOrderQuantityByNo(int no);

	// 1. 재고 변경_재고 수량 감소
	int updateStockByOrderQuantity(Order order);

	// 2. 해당 결제 건의 상태값 변경(N -> Y)
	int updatePaymentStatusByNo(@Param("no") int no, @Param("memberNo") int memberNo, @Param("status") String status);

	// 3. 장바구니 상품 삭제
	int deleteCartByCartNo(@Param("memberNo") int memberNo, @Param("cartNo") int cartNo);

	// 4. 결제 내역 조회_주문 내역
	List<Cart> getOrderListsByNo(@Param("no") int no, @Param("memberNo") int memberNo);

	// 4. 결제 내역 조회_배송 내역
	Delivery getDeliveryByNo(@Param("no") int no, @Param("memberNo") int memberNo);

	// 마이페이지 > 주문 내역 조회
	List<Cart> getMyOrderListsByNo(@Param("memberNo") int memberNo);

	
}
