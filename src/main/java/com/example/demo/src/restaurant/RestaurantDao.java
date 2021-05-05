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
}
