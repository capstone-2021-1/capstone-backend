package com.example.demo.src.user;

import com.fasterxml.jackson.databind.ser.Serializers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/app/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;




    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService){
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /**
     * 회원가입 API
     * [POST] /users
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @ResponseBody
    @PostMapping("/signin")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
        if(postUserReq.getEmail() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        //이메일 정규표현
        if(!isRegexEmail(postUserReq.getEmail())){
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        if(postUserReq.getEmail() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        try{
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 로그인 API
     * [POST] /users/login
     * @return BaseResponse<PostLoginRes>
     */
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<PostLoginRes> login(@RequestBody PostLoginReq postLoginReq){
        try{
            // TODO: 로그인 값들에 대한 형식적인 validatin 처리해주셔야합니다!
            // TODO: 유저의 status ex) 비활성화된 유저, 탈퇴한 유저 등을 관리해주고 있다면 해당 부분에 대한 validation 처리도 해주셔야합니다.
            PostLoginRes postLoginRes = userProvider.login(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 유저 주소등록 API
     * [POST] /app/users/:userIdx/address
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PostMapping("/address")
    public BaseResponse<String> postUserAddr(@RequestBody PostUserAddrReq postUserAddrReq){
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();

            //같다면 주소 등록
            PostUserAddrReq postUserAddrReq2 = new PostUserAddrReq(postUserAddrReq.getUserAddress(),postUserAddrReq.getUserDetailedAddress(),postUserAddrReq.getUserAddressType());
            userService.postUserAddrReq(userIdxByJwt, postUserAddrReq2);

            String result = "주소 등록이 완료되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // My 이츠
    @GetMapping("")
    public BaseResponse<GetUserRes> getUser() {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인

            //같다면 유저 정보 가져오기
            GetUserRes getUserRes = userProvider.getUser(userIdxByJwt);
            return new BaseResponse<>(getUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    //유저 대표 주소 보기
    @GetMapping("/address")
    public BaseResponse<GetUserAddrRes> getUserAddr() {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();

            //같다면 유저 정보 가져오기
            GetUserAddrRes getUserAddrRes = userProvider.getUserAddr(userIdxByJwt);
            return new BaseResponse<>(getUserAddrRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    // 유저 주소 목록 보기
    @GetMapping("/address-list")
    public BaseResponse<List<GetUserAddrListRes>> getUserAddrList(){
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();

            //같다면 유저 정보 가져오기
            List<GetUserAddrListRes> getUserAddrListRes = userProvider.getUserAddrList(userIdxByJwt);
            return new BaseResponse<>(getUserAddrListRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 유저 대표 주소 설정
    @ResponseBody
    @PatchMapping("/address")
    public BaseResponse<String> patchUserMainAddr(@RequestBody PatchUserMainAddrReq patchUserMainAddrReq){
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();

            //같다면 유저 정보 가져오기
            userService.patchUserMainAddr(userIdxByJwt,patchUserMainAddrReq.getIdx());
            String result = "대표 주소가 변경되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }


    }

    // 유저 삭제

    @PatchMapping("/status")
    public BaseResponse<String> deleteLikeStory() {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();

            //같다면 유저 삭제
            userService.deleteUser(userIdxByJwt);

            String result = "회원탈퇴되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
