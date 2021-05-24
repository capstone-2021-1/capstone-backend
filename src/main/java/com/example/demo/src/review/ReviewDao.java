package com.example.demo.src.review;

import com.example.demo.src.review.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.List;

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

    // 리뷰 조회
    //가게 상세 페이지: 리뷰 리스트 API
    public List<GetReviewRes> getReviewRes(int restaurantIdx){
        return this.jdbcTemplate.query("select R.idx as reviewIdx, concat_ws(',',R.image1,R.image2,R.image3,R.image4, R.image5) as images, R.content, R.score, R.createdAt\n" +
                "from Review R\n" +
                "join UserOrder UO on R.orderIdx = UO.idx\n" +
                "join Restaurant R2 on UO.restaurantIdx = R2.idx\n" +
                "where R2.idx = ?",(rs, rowNum) -> new GetReviewRes(
                rs.getInt("reviewIdx"),
                rs.getString("images"),
                rs.getString("content"),
                rs.getInt("score"),
                rs.getString("createdAt")
        ),restaurantIdx);
    }
    // 모든 리뷰 조회
    public List<GetAllReviewRes> getAllReviewRes(){
        return this.jdbcTemplate.query("select R.idx as reviewIdx, R2.idx as restaurantIdx, concat_ws(',',R.image1,R.image2,R.image3,R.image4, R.image5) as images, R.content, R.score, R.createdAt\n" +
                "from Review R\n" +
                "join UserOrder UO on R.orderIdx = UO.idx\n" +
                "join Restaurant R2 on UO.restaurantIdx = R2.idx\n",
                (rs, rowNum) -> new GetAllReviewRes(
                rs.getInt("reviewIdx"),
                rs.getInt("restaurantIdx"),
                rs.getString("images"),
                rs.getString("content"),
                rs.getInt("score"),
                rs.getString("createdAt")
        ));
    }
    // 특정 리뷰 조회
    public GetOneReviewRes getOneReviewRes(int reviewIdx){
        return this.jdbcTemplate.queryForObject("select R.idx as reviewIdx,R2.idx as restaurantIdx, U.idx as userIdx, U.name as userName, concat_ws(',',R.image1,R.image2,R.image3,R.image4, R.image5) as images, R.content, R.score, R.createdAt\n" +
                        "from Review R\n" +
                        "join UserOrder UO on R.orderIdx = UO.idx\n" +
                        "join Restaurant R2 on UO.restaurantIdx = R2.idx\n" +
                        "join User U on UO.userIdx = U.idx\n" +
                        "where R.idx=?",
                (rs, rowNum) -> new GetOneReviewRes(
                        rs.getInt("reviewIdx"),
                        rs.getInt("restaurantIdx"),
                        rs.getInt("userIdx"),
                        rs.getString("userName"),
                        rs.getString("images"),
                        rs.getString("content"),
                        rs.getInt("score"),
                        rs.getString("createdAt")
                ),reviewIdx);
    }


    // 리뷰 도움이 돼요
    public void postReviewHate(int reviewIdx, int userIdx){
        String postMenuHateQuery = "insert into UserMenuHate (reviewIdx, userIdx) VALUES (?,?)";
        Object[] postLikePostParams = new Object[]{reviewIdx, userIdx};
        this.jdbcTemplate.update(postMenuHateQuery, postLikePostParams);
    }

    // 리뷰 도움이 안돼요
    public void postMenuLike(int reviewIdx, int userIdx){
        String postMenuHateQuery = "insert into UserMenuLike (reviewIdx, userIdx) VALUES (?,?)";
        Object[] postLikePostParams = new Object[]{reviewIdx, userIdx};
        this.jdbcTemplate.update(postMenuHateQuery, postLikePostParams);
    }
}
