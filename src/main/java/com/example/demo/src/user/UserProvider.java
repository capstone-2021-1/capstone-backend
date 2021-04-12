package com.example.demo.src.user;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class UserProvider {

    private final UserDao userDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserDao userDao, JwtService jwtService) {
        this.userDao = userDao;
        this.jwtService = jwtService;
    }



    public GetUserRes getUser(int userIdx) throws BaseException {
        try {
            GetUserRes getUserRes = userDao.getUser(userIdx);
            return getUserRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkEmail(String email) throws BaseException{
        try{
            return userDao.checkEmail(email);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

//    public int checkPhoneNumber(String phoneNumber) throws BaseException{
//        try{
//            return userDao.checkPhoneNumber(phoneNumber);
//        } catch (Exception exception){
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }

    public int checkNickName(String nickName) throws BaseException{
        try{
            return userDao.checkNickName(nickName);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostLoginRes login(PostLoginReq postLoginReq) throws BaseException{
        User user = userDao.getPwd(postLoginReq);
        String password;
        try {
            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(user.getUserPassword());
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }

        if(postLoginReq.getUserPassword().equals(password)){
            int userIdx = userDao.getPwd(postLoginReq).getUserIdx();
            String jwt = jwtService.createJwt(userIdx);
            // 삭제된 회원이면 로그인불가
            if(user.getUserStatus().equals("N")){throw new BaseException(DELETED_ID);}
            return new PostLoginRes(userIdx,jwt);
        }
        else{
            throw new BaseException(FAILED_TO_LOGIN);
        }

    }

    // 대표 주소 보여주기
    public GetUserAddrRes getUserAddr(int userIdx) throws BaseException{
        try {
            GetUserAddrRes getUserAddr = userDao.getUserAddr(userIdx);
            return getUserAddr;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    // 주소 리스트 보여주기
    public List<GetUserAddrListRes> getUserAddrList(int userIdx) throws  BaseException{
        try {
            List<GetUserAddrListRes> getUserAddrList = userDao.getUserAddrList(userIdx);
            return getUserAddrList;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
