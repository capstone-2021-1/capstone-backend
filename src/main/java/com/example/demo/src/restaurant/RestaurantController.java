package com.example.demo.src.restaurant;

import com.fasterxml.jackson.databind.ser.Serializers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;

import com.example.demo.src.restaurant.model.*;

import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;


@RestController
@RequestMapping("/app/restaurants")
public class RestaurantController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final RestaurantProvider restaurantProvider;
    @Autowired
    private final RestaurantService restaurantService;
    @Autowired
    private final JwtService jwtService;

    public RestaurantController(RestaurantProvider restaurantProvider, RestaurantService restaurantService, JwtService jwtService){
        this.restaurantProvider = restaurantProvider;
        this.restaurantService = restaurantService;
        this.jwtService = jwtService;
    }

    // 광고 화면
    @GetMapping("/home/ad")
    public BaseResponse<List<GetAdRes>> getAd(){
        List<GetAdRes> getAdRes = restaurantProvider.getAd();
        return new BaseResponse<>(getAdRes);
    }
    // 카테고리별 상세 화면
    @GetMapping("")
    public BaseResponse<List<GetCategoryRestaurantListRes>> getCategoryestaurantListRes(@RequestParam(required = false) String category){
        List<GetCategoryRestaurantListRes> getCategoryRestaurantListRes = restaurantProvider.getCategoryRestaurantListRes(category);
        return new BaseResponse<>(getCategoryRestaurantListRes);
    }

    // 메뉴 싫어요
    @ResponseBody
    @PostMapping("/menus/{menuIdx}/hate")
    public BaseResponse<String> postMenuHate(@PathVariable("menuIdx") int menuIdx){
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();

            restaurantService.postMenuHate(menuIdx,userIdxByJwt);

            String result = "메뉴 싫어요";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 메뉴 좋아요
    @ResponseBody
    @PostMapping("/menus/{menuIdx}/like")
    public BaseResponse<String> postMenuLike(@PathVariable("menuIdx") int menuIdx){
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();

            restaurantService.postMenuLike(menuIdx,userIdxByJwt);

            String result = "메뉴 좋아요";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 메뉴 찜
    @ResponseBody
    @PostMapping("/menus/{menuIdx}/jjim")
    public BaseResponse<String> postMenuJjim(@PathVariable("menuIdx") int menuIdx){
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();

            restaurantService.postMenuJjim(menuIdx,userIdxByJwt);

            String result = "메뉴 찜";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    // 매장 찜
    @ResponseBody
    @PostMapping("/{restaurantIdx}/jjim")
    public BaseResponse<String> postRestaurantJjim(@PathVariable("restaurantIdx") int restaurantIdx){
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();

            restaurantService.postRestaurantJjim(restaurantIdx,userIdxByJwt);

            String result = "매장 찜";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    // 매장별 피드 불러오기
    @ResponseBody
    @GetMapping("/feeds/{restaurantIdx}")
    public BaseResponse<List<GetRestaurantFeedRes>> getRestaurantFeedRes(@PathVariable("restaurantIdx") int restaurantIdx){
        List<GetRestaurantFeedRes> getRestaurantFeedRes = restaurantProvider.getRestaurantFeedRes(restaurantIdx);
        return new BaseResponse<>(getRestaurantFeedRes);
    }

    // 피드 좋아요
    @ResponseBody
    @PostMapping("/feeds/{feedIdx}/like")
    public BaseResponse<String> postFeedLike(@PathVariable("feedIdx") int feedIdx){
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();

            restaurantService.postFeedLike(feedIdx,userIdxByJwt);

            String result = "피드 좋아요";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 피드 댓글
    @ResponseBody
    @PostMapping("/feeds/{feedIdx}/comments")
    public BaseResponse<String> postRestaurantComments(@PathVariable("feedIdx") int feedIdx, @RequestBody PostFeedCommentReq postFeedCommentReq){
        try{

            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();

            restaurantService.postRestaurantComments(feedIdx, userIdxByJwt, postFeedCommentReq);

            String result = "피드 댓글 성공";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 매장 정보
    // 매장별 메뉴 목록
    @ResponseBody
    @GetMapping("/{restaurantIdx}")
    public BaseResponse<GetRestaurantRes> getRestaurantRes(@PathVariable("restaurantIdx") int restaurantIdx){
        GetRestaurantRes getRestaurantRes = restaurantProvider.getRestaurantRes(restaurantIdx);
        return new BaseResponse<>(getRestaurantRes);
    }

    // 매장별 메뉴 목록
    @ResponseBody
    @GetMapping("/{restaurantIdx}/menu-board")
    public BaseResponse<List<GetMenuListRes>> getMenuListRes(@PathVariable("restaurantIdx") int restaurantIdx){
        List<GetMenuListRes> getMenuListRes = restaurantProvider.getMenuListRes(restaurantIdx);
        return new BaseResponse<>(getMenuListRes);
    }
}
