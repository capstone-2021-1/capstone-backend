package com.example.demo.src.order;

import com.example.demo.src.order.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class OrderProvider {
    private final OrderDao orderDao;
    private final JwtService jwtService;

    private JdbcTemplate jdbcTemplate;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public OrderProvider(OrderDao orderDao, JwtService jwtService) {
        this.orderDao = orderDao;
        this.jwtService = jwtService;
    }
    // 결제 화면 조회
    public GetOrderRes getOrderRes(int orderIdx){
        GetOrderRes getOrderRes = orderDao.getOrderRes(orderIdx);
        return getOrderRes;
    }

    // 결제 내역 조회
    public List<GetOrderRes> getOrderResList(int userIdx){
        List<GetOrderRes> getOrderResList = orderDao.getOrderResList(userIdx);
        return getOrderResList;
    }
}
