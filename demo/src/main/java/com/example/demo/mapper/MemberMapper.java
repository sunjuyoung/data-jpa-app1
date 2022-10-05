package com.example.demo.mapper;


import com.example.demo.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {

    @Select("Select * from member m where m.nickname = #{nickname} ")
    Member findByNickname(@Param("nickname") String nickname);

    @Select("Select * from member m where m.email = #{email} ")
    Member findByEmail(@Param("email") String email);
}
