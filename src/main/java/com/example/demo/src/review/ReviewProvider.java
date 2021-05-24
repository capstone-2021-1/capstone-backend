package com.example.demo.src.review;



import com.example.demo.src.review.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class ReviewProvider {
    private final ReviewDao reviewDao;
    private final JwtService jwtService;

    private JdbcTemplate jdbcTemplate;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public ReviewProvider(ReviewDao reviewDao, JwtService jwtService) {
        this.reviewDao = reviewDao;
        this.jwtService = jwtService;
    }

    public List<GetReviewRes> getReviewRes(int restaurantIdx){
        List<GetReviewRes> getReviewRes = reviewDao.getReviewRes(restaurantIdx);
        return getReviewRes;
    }


    public List<GetAllReviewRes> getAllReviewRes(){
        List<GetAllReviewRes> getAllReviewRes = reviewDao.getAllReviewRes();
        return getAllReviewRes;
    }
    public GetOneReviewRes getOneReviewRes(int orderIdx){
        GetOneReviewRes getOneReviewRes = reviewDao.getOneReviewRes(orderIdx);
        return getOneReviewRes;
    }
}
