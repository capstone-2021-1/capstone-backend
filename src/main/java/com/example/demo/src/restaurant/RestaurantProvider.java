package com.example.demo.src.restaurant;

import com.example.demo.src.restaurant.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
@Service

public class RestaurantProvider {
    private final RestaurantDao restaurantDao;
    private final JwtService jwtService;

    private JdbcTemplate jdbcTemplate;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public RestaurantProvider(RestaurantDao restaurantDao, JwtService jwtService) {
        this.restaurantDao = restaurantDao;
        this.jwtService = jwtService;
    }


    // 광고 화면
    public List<GetAdRes> getAd(){
        List<GetAdRes> getAdRes = restaurantDao.getAd();
        return getAdRes;
    }

    // 카테고리별 매장 목록
    public List<GetCategoryRestaurantListRes> getCategoryRestaurantListRes(String category){
        List<GetCategoryRestaurantListRes> getCategoryRestaurantListRes = restaurantDao.getCategoryRestaurantListRes(category);
        return getCategoryRestaurantListRes;
    }

    // 매장별 피드 목록
    public List<GetRestaurantFeedRes> getRestaurantFeedRes(int restaurantIdx){
        List<GetRestaurantFeedRes> getRestaurantFeedRes = restaurantDao.getRestaurantFeedRes(restaurantIdx);
        return getRestaurantFeedRes;
    }

    // 매장별 메뉴 목록
    public List<GetMenuListRes> getMenuListRes(int restaurantIdx){
        List<GetMenuListRes> getMenuListRes = restaurantDao.getMenuListRes(restaurantIdx);
        return getMenuListRes;
    }

}
