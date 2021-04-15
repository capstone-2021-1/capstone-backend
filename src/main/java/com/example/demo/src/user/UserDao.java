package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.sql.Timestamp;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 유저 생성
    public int createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into User (email, password, name, nickName, profileImage) VALUES (?,?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getUserEmail(), postUserReq.getUserPassword(), postUserReq.getUserName(), postUserReq.getUserNickName(), postUserReq.getUserProfileImage()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select email from User where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);
    }

    public int checkNickName(String nickName){
        String checkNickNameQuery = "select exists(select nickName from User where nickName = ?)";
        String checkNickNameParams = nickName;
        return this.jdbcTemplate.queryForObject(checkNickNameQuery,
                int.class,
                checkNickNameParams);
    }


    public int checkPhoneNumber(String phoneNumber){
        String checkPhoneNumberQuery = "select exists(select phoneNumber from User where phoneNumber = ?)";
        String checkPhoneNumberParams = phoneNumber;
        return this.jdbcTemplate.queryForObject(checkPhoneNumberQuery,
                int.class,
                checkPhoneNumberParams);
    }



    public User getPwd(PostLoginReq postLoginReq){
        String getPwdQuery = "select idx, email, password, phoneNumber, name,status from User where email = ?";
        String getPwdParams = postLoginReq.getUserEmail();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new User(
                        rs.getInt("idx"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phoneNumber"),
                        rs.getString("name"),
                        rs.getString("status")
                ),
                getPwdParams
        );

    }


    // 주소 등록
    public void postUserAddr(int userIdx, PostUserAddrReq postUserAddrReq){
        String postUserAddrQuery = "insert into UserDeliveryAddress (userIdx, address, detailedAddress, addressType) VALUES (?,?,?,?)";
        Object[] postUserAddrParams = new Object[]{userIdx, postUserAddrReq.getUserAddress(),postUserAddrReq.getUserDetailedAddress(),postUserAddrReq.getUserAddressType()};
        this.jdbcTemplate.update(postUserAddrQuery, postUserAddrParams);
    }


    public GetUserRes getUser(int userIdx){
        return this.jdbcTemplate.queryForObject("select name,phoneNumber from User where idx = ?",
                (rs, rowNum) -> new GetUserRes(
                        rs.getString("name"),
                        rs.getString("phoneNumber")
                ),
                userIdx);
    }

    // 대표 주소 보기
    public GetUserAddrRes getUserAddr(int userIdx){
        return this.jdbcTemplate.queryForObject("select address,addressType from UserDeliveryAddress where (userIdx = ? && main=?)",
                (rs, rowNum) -> new GetUserAddrRes(
                        rs.getString("address"),
                        rs.getString("addressType")
                ),
                userIdx,1);
    }

    // 주소 목록 보기
    public List<GetUserAddrListRes> getUserAddrList(int userIdx){
        return this.jdbcTemplate.query("select address,detailedAddress,addressType from UserDeliveryAddress where userIdx = ?",
                (rs, rowNum) -> new GetUserAddrListRes(
                        rs.getString("address"),
                        rs.getString("detailedAddress"),
                        rs.getString("addressType")
                ),
                userIdx);

    }

    // 대표 주소 설정하기
    public void patchUserMainAddr(int userIdx, int idx){

        String clearUserMainAddrQuery = "update UserDeliveryAddress set main = ? where userIdx=?";
        Object[]  clearUserMainAddrParams = new Object[]{0,userIdx};
        this.jdbcTemplate.update(clearUserMainAddrQuery,clearUserMainAddrParams);
        String patchUserMainAddrQuery = "update UserDeliveryAddress set main = ? where idx=?";
        Object[]  patchUserMainAddrParams = new Object[]{1,idx};
        this.jdbcTemplate.update(patchUserMainAddrQuery, patchUserMainAddrParams);

    }

    // 유저 삭제
    public void deleteUser(int userIdx){
        String deleteUserQuery = "update User set status = ? where idx=?";
        Object[] deleteUserParams = new Object[]{"N",userIdx};
        this.jdbcTemplate.update(deleteUserQuery, deleteUserParams);

    }

}
