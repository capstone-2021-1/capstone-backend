package com.example.demo.src.restaurant;


import com.example.demo.src.restaurant.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import javax.sql.DataSource;
import java.util.List;

@Repository
public class RestaurantDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetAdRes> getAd(){
        return this.jdbcTemplate.query("select idx, adImage from Ad",
                (rs, rowNum)->new GetAdRes(
                        rs.getInt("idx"),
                        rs.getNString("adImage")
                ));
    }

    // 가게 리스트 받아오기(카테고리)
    // 매장 인덱스, 가게명, 가게 이미지, 해시태그, 찜한 수, 최근 피드 수, 배달시간, 배송료, 최소주문금
    public List<GetCategoryRestaurantListRes> getCategoryRestaurantListRes(String category){
        return this.jdbcTemplate.query("select r.idx as restaurantIdx, r.name, r.image1 as image, GROUP_CONCAT(H.content) AS hashtag, count(UJR.idx) as jjim, r.deliveryTime, r.deliveryCost, r.minimumOrderAmount from Restaurant r\n" +
                "left join RestaurantHashtag RH on r.idx = RH.restaurantIdx\n" +
                "left join Hashtag H on RH.hashtagIdx = H.idx\n" +
                "left join UserJjimRestaurant UJR on r.idx = UJR.restaurantIdx\n" +
                "where category=?\n" +
                "group by r.idx",(rs, rowNum) -> new GetCategoryRestaurantListRes(
                rs.getInt("restaurantIdx"),
                rs.getString("name"),
                rs.getString("image"),
                rs.getString("hashtag"),
                rs.getInt("jjim"),
                rs.getInt("deliveryTime"),
                rs.getInt("deliveryCost"),
                rs.getInt("minimumOrderAmount")
        ),category);
    }



    public void postMenuHate(int menuIdx, int userIdx){
        String postMenuHateQuery = "insert into UserMenuHate (menuIdx, userIdx) VALUES (?,?)";
        Object[] postLikePostParams = new Object[]{menuIdx, userIdx};
        this.jdbcTemplate.update(postMenuHateQuery, postLikePostParams);
    }
    public void postMenuLike(int menuIdx, int userIdx){
        String postMenuHateQuery = "insert into UserMenuLike (menuIdx, userIdx) VALUES (?,?)";
        Object[] postLikePostParams = new Object[]{menuIdx, userIdx};
        this.jdbcTemplate.update(postMenuHateQuery, postLikePostParams);
    }
    public void postMenuJjim(int menuIdx, int userIdx){
        String postMenuHateQuery = "insert into UserJjimMenu (menuIdx, userIdx) VALUES (?,?)";
        Object[] postLikePostParams = new Object[]{menuIdx, userIdx};
        this.jdbcTemplate.update(postMenuHateQuery, postLikePostParams);
    }
    public void postRestaurantJjim(int restaurantIdx, int userIdx){
        String postMenuHateQuery = "insert into UserJjimRestaurant (restaurantIdx, userIdx) VALUES (?,?)";
        Object[] postLikePostParams = new Object[]{restaurantIdx, userIdx};
        this.jdbcTemplate.update(postMenuHateQuery, postLikePostParams);
    }

    public void postFeedLike(int feedIdx, int userIdx){
        String postMenuHateQuery = "insert into NewsFeedLike (feedIdx, userIdx) VALUES (?,?)";
        Object[] postLikePostParams = new Object[]{feedIdx, userIdx};
        this.jdbcTemplate.update(postMenuHateQuery, postLikePostParams);
    }


}
