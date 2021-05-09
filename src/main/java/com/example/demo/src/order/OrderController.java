package com.example.demo.src.order;

import com.example.demo.src.order.model.PostOrderReq;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.hibernate.sql.Delete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.order.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/app/orders")
public class OrderController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final OrderProvider orderProvider;
    @Autowired
    private final OrderService orderService;
    @Autowired
    private final JwtService jwtService;


    public OrderController(OrderProvider orderProvider, OrderService orderService, JwtService jwtService) {
        this.orderProvider = orderProvider;
        this.orderService = orderService;
        this.jwtService = jwtService;
    }

    // 결제 등록
    @ResponseBody
    @PostMapping("")
    public BaseResponse<String> postOrder(@RequestBody PostOrderReq postOrderReq){
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();

            PostOrderReq postOrderReq2 = new PostOrderReq(postOrderReq.getRestaurantIdx(),postOrderReq.getTotalPrice(),postOrderReq.getCommentToOwner(),postOrderReq.getCommentToDeliveryer(),postOrderReq.getPaymentMethod(),postOrderReq.getMenuList());
            orderService.postOrder(userIdxByJwt,postOrderReq2);
            String result = "주문이 완료되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
