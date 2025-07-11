package kr.co.wikibook.gallery.order;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.wikibook.gallery.cart.CartMapper;
import kr.co.wikibook.gallery.item.ItemMapper;
import kr.co.wikibook.gallery.item.model.ItemGetRes;
import kr.co.wikibook.gallery.order.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;
    private final ItemMapper itemMapper;
    private final OrderItemMapper orderItemMapper;
    private final CartMapper cartMapper;

    @Transactional
    public int saveOrder(OrderPostReq req, int logginedMemberId) {
        // 상품 정보 DB로 부터 가져온다.
        List<ItemGetRes> itemList = itemMapper.findAllByIdIn(req.getItemIds());
        log.info("itemList={}", itemList);

        // 총 구매가격 콘솔에 출력!!

        int amount = 0;
        for (ItemGetRes item : itemList) {
            int price = item.getPrice();
            int discountPer = item.getDiscountPer();
            amount += price - price * discountPer / 100;
        }
        log.info("amount={}", amount);

        // 만드세요! OrderPostDto 객체화하시고 데이터 넣어주세요

        OrderPostDto orderPostDto = new OrderPostDto();
        orderPostDto.setMemberId(logginedMemberId);
        orderPostDto.setName(req.getName());
        orderPostDto.setAddress(req.getAddress());
        orderPostDto.setPayment(req.getPayment());
        orderPostDto.setCardNumber(req.getCardNumber());
        orderPostDto.setAmount(amount);
       // log.info("before-orderPostDto={}", orderPostDto);
        int result = orderMapper.save(orderPostDto);
       // log.info("after-orderPostDto={}", orderPostDto);

        // OrderItemPostDto 객체화 하시면서 데이터 넣어주세요
        OrderItemPostDto orderItemPostDto = new OrderItemPostDto(orderPostDto.getOrderId(), req.getItemIds());
        int orderItemId = orderItemMapper.save(orderItemPostDto);

        cartMapper.deleteByMemberId(logginedMemberId);

        return 1;
    }

    public List<OrderGetRes> findAllByMemberId(int memberId) {
        log.info("req={}", memberId);
        List<OrderGetRes> result = orderMapper.findAllByMemberIdOrderByIdDesc(memberId);
        log.info("result={}", result);
        return result;
    }

    public OrderDetailGetRes detail(OrderDetailGetReq req) {
        OrderDetailGetRes result = orderMapper.findByOrderIdAndMemberId(req);
        log.info("result={}", result);
        return result;

    }


}
