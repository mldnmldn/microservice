package com.itmuch.cloud.study.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.itmuch.cloud.study.entity.Member;

@Mapper
public interface MemberMapper {
	public Member findById(String mid) ; 
}
