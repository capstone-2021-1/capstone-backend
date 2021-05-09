package com.example.demo.src.order;

import com.example.demo.src.order.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class OrderDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 주문 등록
    public void postOrder(int userIdx,PostOrderReq postOrderReq){
        String postOrderQuery = "insert into UserOrder (userIdx, restaurantIdx, totalPrice,commentToOwner,commentToDeliveryer,paymentMethod) VALUES (?,?,?,?,?,?)";
        Object[] postOrderParams = new Object[]{userIdx,postOrderReq.getRestaurantIdx(),postOrderReq.getTotalPrice(),postOrderReq.getCommentToOwner(),postOrderReq.getCommentToDeliveryer(),postOrderReq.getPaymentMethod()};
        this.jdbcTemplate.update(postOrderQuery, postOrderParams);

        PostOrderReq.MenuList[] menuList = postOrderReq.getMenuList();

        int lastIdx = jdbcTemplate.queryForObject("select max(idx) from UserOrder", Integer.class);

        for(int i=0; i< menuList.length;i++){
            String postMenuListQuery = "insert into UserOrderMenu (orderIdx, menuIdx, menuOption,price,number) VALUES (?,?,?,?,?)";
            Object[] postMenuListParams = new Object[]{lastIdx, menuList[i].getmenuIdx(),menuList[i].getMenuOption(), menuList[i].getPrice(),menuList[i].getNumber()};
            this.jdbcTemplate.update(postMenuListQuery, postMenuListParams);
        }

    }
}
