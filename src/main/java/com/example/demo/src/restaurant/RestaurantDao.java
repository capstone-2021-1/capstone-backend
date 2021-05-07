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


    // 메뉴 싫어요
    public void postMenuHate(int menuIdx, int userIdx){
        String postMenuHateQuery = "insert into UserMenuHate (menuIdx, userIdx) VALUES (?,?)";
        Object[] postLikePostParams = new Object[]{menuIdx, userIdx};
        this.jdbcTemplate.update(postMenuHateQuery, postLikePostParams);
    }

    // 메뉴 좋아요
    public void postMenuLike(int menuIdx, int userIdx){
        String postMenuHateQuery = "insert into UserMenuLike (menuIdx, userIdx) VALUES (?,?)";
        Object[] postLikePostParams = new Object[]{menuIdx, userIdx};
        this.jdbcTemplate.update(postMenuHateQuery, postLikePostParams);
    }
    // 메뉴 찜
    public void postMenuJjim(int menuIdx, int userIdx){
        String postMenuHateQuery = "insert into UserJjimMenu (menuIdx, userIdx) VALUES (?,?)";
        Object[] postLikePostParams = new Object[]{menuIdx, userIdx};
        this.jdbcTemplate.update(postMenuHateQuery, postLikePostParams);
    }

    // 매장 찜
    public void postRestaurantJjim(int restaurantIdx, int userIdx){
        String postMenuHateQuery = "insert into UserJjimRestaurant (restaurantIdx, userIdx) VALUES (?,?)";
        Object[] postLikePostParams = new Object[]{restaurantIdx, userIdx};
        this.jdbcTemplate.update(postMenuHateQuery, postLikePostParams);
    }

    // 매장 피드 보기
    // 피드 인덱스, 매장 인덱스, 매장 명, 피드 사진(4장),피드 내용, (댓글)
    public List<GetRestaurantFeedRes> getRestaurantFeedRes(int restaurantIdx){
        return this.jdbcTemplate.query("select f.idx as feedIdx, f.restaurantIdx, R.name, concat(R.image1, ',',R.image2, ',',R.image3) as images, f.content, GROUP_CONCAT(H.content) AS hashtag\n" +
                "from NewsFeed f\n" +
                "join Restaurant R on f.restaurantIdx = R.idx\n" +
                "left join NewsFeedHashtag NFH on f.idx = NFH.feedIdx\n" +
                "left join Hashtag H on NFH.hashtagIdx = H.idx\n" +
                "where R.idx = ?\n" +
                "group by f.idx",(rs, rowNum) -> new GetRestaurantFeedRes(
                rs.getInt("feedIdx"),
                rs.getInt("restaurantIdx"),
                rs.getString("name"),
                rs.getString("images"),
                rs.getString("content"),
                rs.getString("hashTag")
        ),restaurantIdx);
    }


    // 매장 피드 좋아요
    public void postFeedLike(int feedIdx, int userIdx){
        String postMenuHateQuery = "insert into NewsFeedLike (feedIdx, userIdx) VALUES (?,?)";
        Object[] postLikePostParams = new Object[]{feedIdx, userIdx};
        this.jdbcTemplate.update(postMenuHateQuery, postLikePostParams);
    }




}
