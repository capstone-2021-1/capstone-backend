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
    // 주문 인덱스, 배달 시간, 배달 주소, 매장 이름, 메뉴 리스트, 전체 금액, 결제 수단

    public GetOrderRes getOrderRes(int orderIdx){
        return this.jdbcTemplate.queryForObject("select O.idx as orderIdx, R.deliveryTime, UDA.detailedAddress, R.name as restaurantName, GROUP_CONCAT(distinct M.name) as menuList, O.totalPrice, O.paymentMethod\n" +
                "from UserOrder O\n" +
                "join UserOrderMenu UOM on O.idx = UOM.orderIdx\n" +
                "join Menu M on UOM.menuIdx = M.idx\n" +
                "join Restaurant R on O.restaurantIdx = R.idx\n" +
                "join User U on O.userIdx = U.idx\n" +
                "join UserDeliveryAddress UDA on U.idx = UDA.userIdx\n" +
                "where O.idx =?\n" +
                "group by O.idx", (rs, rowNum)-> new GetOrderRes(
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
    public List<GetOrderRes> getOrderResList(int userIdx){
        return this.jdbcTemplate.query("select O.idx as orderIdx, R.deliveryTime, UDA.detailedAddress, R.name as restaurantName, GROUP_CONCAT(distinct M.name) as menuList, O.totalPrice, O.paymentMethod\n" +
                "from UserOrder O\n" +
                "join UserOrderMenu UOM on O.idx = UOM.orderIdx\n" +
                "join Menu M on UOM.menuIdx = M.idx\n" +
                "join Restaurant R on O.restaurantIdx = R.idx\n" +
                "join User U on O.userIdx = U.idx\n" +
                "join UserDeliveryAddress UDA on U.idx = UDA.userIdx\n" +
                "where U.idx =?\n" +
                "group by O.idx", (rs, rowNum)-> new GetOrderRes(
                rs.getInt("orderIdx"),
                rs.getInt("deliveryTime"),
                rs.getString("detailedAddress"),
                rs.getString("restaurantName"),
                rs.getString("menuList"),
                rs.getInt("totalPrice"),
                rs.getString("paymentMethod")
        ),userIdx);
    }
}
