package com.example.demo.src.review;

import com.example.demo.src.review.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.transaction.Transactional;

@Transactional
@Repository
public class ReviewDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 리뷰 등록
    public void postReview(PostReviewReq postReviewReq){

//        GetRestaurantIdx getRestaurantIdx = this.jdbcTemplate.query("select r2.idx as idx from Review r inner join UserOrder o on r.orderIdx = o.idx inner join UserOrderMenu m on m.orderIdx = o.idx inner join Menu M2 on m.menuIdx = M2.idx inner join MenuCategory MC on M2.categoryIdx = MC.idx inner join Restaurant r2 on r2.idx = MC.restaurantId = r2.idx where r.orderIdx=? group by m.orderIdx",
//                (rs, rowNum) -> new GetRestaurantIdx(
//                        rs.getInt("idx")
//                ),postReviewReq.getOrderIdx());


        String postReviewQuery = "insert into Review (orderIdx,score, content,image1,image2,image3,image4,image5) VALUES (?,?,?,?,?,?,?,?)";
        Object[] postReviewParams = new Object[]{postReviewReq.getOrderIdx(),postReviewReq.getScore(), postReviewReq.getContent(),postReviewReq.getImage1(),postReviewReq.getImage2(),postReviewReq.getImage3(),postReviewReq.getImage4(),postReviewReq.getImage5()};
        this.jdbcTemplate.update(postReviewQuery, postReviewParams);

    }
//    public void postReviewImage(PostReviewReq postReviewReq){
//        int lastIdx = jdbcTemplate.queryForObject("select max(idx) from Review", Integer.class);
//
//
//        String postReviewImageQuery = "insert into ReviewImage (reviewIdx, image) VALUES (?,?)";
//        Object[] postReviewImageParams = new Object[]{lastIdx,postReviewReq.getImage()};
//        this.jdbcTemplate.update(postReviewImageQuery, postReviewImageParams);
//
//
//    }

    // 리뷰 수정
    public void patchReview(int reviewIdx, PatchReviewReq patchReviewReq){
        String patchReviewQuery = "update Review set score=?, content=? where idx=?";
        Object[] patchReviewParams = new Object[]{patchReviewReq.getScore(), patchReviewReq.getContent(), reviewIdx};
        this.jdbcTemplate.update(patchReviewQuery, patchReviewParams);
    }


    // 리뷰 삭제
    public void deleteReview(int reviewIdx){
        String deleteReviewQuery = "update Review set status=? where idx=?";
        Object[] deleteReviewParams = new Object[]{"N", reviewIdx};
        this.jdbcTemplate.update(deleteReviewQuery,deleteReviewParams);
    }

}
