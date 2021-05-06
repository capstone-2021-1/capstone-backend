package com.example.demo.src.restaurant;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.restaurant.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class RestaurantService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RestaurantDao restaurantDao;
    private final RestaurantProvider restaurantProvider;
    private final JwtService jwtService;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public RestaurantService(RestaurantDao restaurantDao, RestaurantProvider restaurantProvider, JwtService jwtService) {
        this.restaurantDao = restaurantDao;
        this.restaurantProvider = restaurantProvider;
        this.jwtService = jwtService;
    }

    public void postMenuHate(int menuIdx, int userIdx){ restaurantDao.postMenuHate(menuIdx, userIdx);}
    public void postMenuLike(int menuIdx, int userIdx){ restaurantDao.postMenuLike(menuIdx, userIdx);}
    public void postMenuJjim(int menuIdx, int userIdx){ restaurantDao.postMenuJjim(menuIdx, userIdx);}
    public void postRestaurantJjim(int restaurantIdx, int userIdx){ restaurantDao.postRestaurantJjim(restaurantIdx, userIdx);}
    public void postFeedLike(int feedIdx, int userIdx){ restaurantDao.postFeedLike(feedIdx, userIdx);}


}
