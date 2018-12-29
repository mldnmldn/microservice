package com.itmuch.cloud.study.mapper;

import java.util.Set;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper {
	public Set<String> findAllByMember(String mid) ;
}
