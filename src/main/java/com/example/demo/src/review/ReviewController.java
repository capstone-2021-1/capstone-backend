package com.example.demo.src.review;

import com.example.demo.src.user.model.PostUserAddrReq;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.hibernate.sql.Delete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.review.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/app/reviews")
public class ReviewController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ReviewProvider reviewProvider;
    @Autowired
    private final ReviewService reviewService;
    @Autowired
    private final JwtService jwtService;


    public ReviewController(ReviewProvider reviewProvider, ReviewService reviewService, JwtService jwtService) {
        this.reviewProvider = reviewProvider;
        this.reviewService = reviewService;
        this.jwtService = jwtService;
    }


    // 리뷰 등록
    @ResponseBody
    @PostMapping("/post")
    public BaseResponse<String> postReview(@RequestBody PostReviewReq postReviewReq){
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();

            //같다면 리뷰등록
            PostReviewReq postReviewReq2 = new PostReviewReq(postReviewReq.getOrderIdx(), postReviewReq.getScore(), postReviewReq.getContent(), postReviewReq.getImage1(), postReviewReq.getImage2(), postReviewReq.getImage3(), postReviewReq.getImage4(), postReviewReq.getImage5());
            reviewService.postReview(postReviewReq2);


            String result = "리뷰 등록이 완료되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    // 리뷰 수정
    @ResponseBody
    @PatchMapping("/{reviewIdx}")
    public BaseResponse<String> patchReview(@PathVariable("reviewIdx") int reviewIdx,@RequestBody PatchReviewReq patchReviewReq) {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(patchReviewReq.getUserIdx() != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 리뷰 수정
            PatchReviewReq patchReviewReq2 = new PatchReviewReq(patchReviewReq.getUserIdx(),patchReviewReq.getScore(), patchReviewReq.getContent());
            reviewService.patchReview(reviewIdx,patchReviewReq2);

            String result = "리뷰가 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 리뷰 삭제
    @ResponseBody
    @PatchMapping("/{reviewIdx}/status")
    public BaseResponse<String> deleteReview(@PathVariable("reviewIdx") int reviewIdx){
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();

            //같다면 리뷰 삭제
            reviewService.deleteReview(reviewIdx);

            String result = "리뷰가 삭제되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @GetMapping("/restaurants/{restaurantIdx}")
    public BaseResponse<List<GetReviewRes>> getReview(@PathVariable("restaurantIdx") int restaurantIdx){
        List<GetReviewRes> getReviewRes = reviewProvider.getReviewRes(restaurantIdx);
        return new BaseResponse<>(getReviewRes);
    }

    @GetMapping("")
    public BaseResponse<List<GetAllReviewRes>> getAllReview(){
        List<GetAllReviewRes> getAllReviewRes = reviewProvider.getAllReviewRes();
        return new BaseResponse<>(getAllReviewRes);
    }

    @GetMapping("/{orderIdx}")
    public BaseResponse<GetOneReviewRes> getOneReview(@PathVariable("orderIdx") int orderIdx){
        GetOneReviewRes getOneReviewRes = reviewProvider.getOneReviewRes(orderIdx);
        return new BaseResponse<>(getOneReviewRes);
    }
}
