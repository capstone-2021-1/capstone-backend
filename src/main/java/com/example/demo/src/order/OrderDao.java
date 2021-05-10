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
    // 결제 완료페이지
    public GetOrderRes getOrderRes(int orderIdx){
        return this.jdbcTemplate.queryForObject("select o.idx as orderIdx,r.deliveryTime,uda.detailedAddress,r.name as restaurantName,GROUP_CONCAT(m.name) as menuList, o.totalPrice, o.paymentMethod \n" +
                "from UserOrder o\n" +
                "join Restaurant r on o.restaurantIdx = r.idx\n" +
                "join UserOrderMenu um on um.orderIdx = o.idx\n" +
                "join Menu m on m.idx = um.menuIdx\n" +
                "join User u on u.idx = o.userIdx\n" +
                "join UserDeliveryAddress uda on u.idx = uda.userIdx\n" +
                "where o.idx = ?\n" +
                "group by o.idx", (rs, rowNum)-> new GetOrderRes(
                rs.getInt("orderIdx"),
                rs.getInt("deliveryTime"),
                rs.getString("detailedAddress"),
                rs.getString("restaurantName"),
                rs.getString("menuList"),
                rs.getInt("totalPrice"),
                rs.getString("paymentMethod")
        ),orderIdx);
    }

    // 결제 내역
    public List<GetOrderRes> getOrderResList(){
        return this.jdbcTemplate.query("select o.idx as orderIdx,r.deliveryTime,uda.detailedAddress,r.name as restaurantName,GROUP_CONCAT(m.name) as menuList, o.totalPrice, o.paymentMethod \n" +
                "from UserOrder o\n" +
                "join Restaurant r on o.restaurantIdx = r.idx\n" +
                "join UserOrderMenu um on um.orderIdx = o.idx\n" +
                "join Menu m on m.idx = um.menuIdx\n" +
                "join User u on u.idx = o.userIdx\n" +
                "join UserDeliveryAddress uda on u.idx = uda.userIdx\n" +
                "group by o.idx", (rs, rowNum)-> new GetOrderRes(
                rs.getInt("orderIdx"),
                rs.getInt("deliveryTime"),
                rs.getString("detailedAddress"),
                rs.getString("restaurantName"),
                rs.getString("menuList"),
                rs.getInt("totalPrice"),
                rs.getString("paymentMethod")
        ));
    }
}
