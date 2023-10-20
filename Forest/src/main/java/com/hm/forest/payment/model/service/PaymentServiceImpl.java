package com.hm.forest.payment.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hm.forest.member.model.vo.Cart;
import com.hm.forest.payment.model.mapper.PaymentMapper;
import com.hm.forest.payment.model.vo.Delivery;
import com.hm.forest.payment.model.vo.Order;
import com.hm.forest.payment.model.vo.Payment;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentMapper paymentMapper;

	// 결제하기 클릭 시 입력되는 정보들
	// 1. 결제 정보 설정(입력)
	@Override
	@Transactional
	public int save(Payment payment) {
		int result = 0;
		
		if (payment.getNo() > 0) {
			// update
		//	result = paymentMapper.updatePayment(payment);
		} else {
			// insert
			result = paymentMapper.insertPayment(payment);
		}	
		return result;
	}

	// 2. 해당 결제에 관련된 주문정보 등록(INSERT 반복)
	@Override
	@Transactional
	public int insertOrderLists(int paymentNo, List<Order> orderLists) {
		   int totalInsertedResult = 0;
		   
		    for (Order order : orderLists) {
		    	order.setPaymentNo(paymentNo); // 해당 결제 번호 설정
		        int result = paymentMapper.insertOrdersByPaymentNo(order);
		        totalInsertedResult += result;
		    }
		    return totalInsertedResult;
	}

	// 3. 해당 결제에 관련된 배송정보 등록 
	@Override
	@Transactional
	public int save(int paymentNo, Delivery delivery) {
		int result = 0;
		
		if (delivery.getNo() > 0) {
			// update
		//	result = paymentMapper.updatePayment(payment);
		} else {
			// insert
			//delivery.setPaymentNo(paymentNo); // 해당 결제 번호 설정
			result = paymentMapper.insertDeliveryByPaymentNo(paymentNo, delivery);
		}	
		return result;
	}

	// 결제 성공 시 
	// 1. 재고 변경_주문수량 조회
	@Override
	public List<Order> getOrderQuantityByNo(int no) {
		return paymentMapper.getOrderQuantityByNo(no);
	}
	
	// 1. 재고 변경_재고 수량 감소
	@Override
	@Transactional
	public int updateStockByOrderQuantity(List<Order> orderInfo) {
		   int totalUpdateResult = 0;
		   
		    for (Order order : orderInfo) {
		        int result = paymentMapper.updateStockByOrderQuantity(order);
		        totalUpdateResult += result;
		    }
		    return totalUpdateResult;
	}

	// 2. 해당 결제 건의 상태값 변경(N -> Y)
	@Override
	@Transactional
	public int updatePaymentStatusByNo(int no, int memberNo) {
		return paymentMapper.updatePaymentStatusByNo(no, memberNo, "Y");
	}

	// 3. 장바구니 상품 삭제
	@Override
	@Transactional
	public int deleteCartByCartNo(int memberNo, List<Order> orderInfo) {	
		   int totalDeleteResult = 0;
		   
		    for (Order order : orderInfo) {
		    	if (order.getCartNo() != 0) {
		    		int result = paymentMapper.deleteCartByCartNo(memberNo, order.getCartNo());
		    		totalDeleteResult += result;					
				}
		    }
		    return totalDeleteResult;
	}

	// 4. 결제 내역 조회_주문 내역
	@Override
	public List<Cart> getOrderListsByNo(int no, int memberNo) {
		return paymentMapper.getOrderListsByNo(no, memberNo);
	}

	// 4. 결제 내역 조회_배송 내역
	@Override
	public Delivery getDeliveryByNo(int no, int memberNo) {
		return paymentMapper.getDeliveryByNo(no, memberNo);
	}

	
	// 마이페이지 > 주문 내역 조회
	@Override
	public List<Cart> getMyOrderListsByNo(int memberNo) {
		return paymentMapper.getMyOrderListsByNo(memberNo);
	}

}
