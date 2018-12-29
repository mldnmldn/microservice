package com.itmuch.cloud.study.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itmuch.cloud.study.entity.Member;
import com.itmuch.cloud.study.mapper.MemberMapper;
import com.itmuch.cloud.study.mapper.RoleMapper;
import com.itmuch.cloud.study.service.IMemberService;

@Service
public class MemberServiceImpl implements IMemberService {

	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private RoleMapper roleMapper;
	
	public Map<String, Object> get(String mid) {
		Map<String,Object> map = new HashMap<String,Object>() ;
		Member member = this.memberMapper.findById(mid) ;
		if (member != null) {
			map.put("allRoles", this.roleMapper.findAllByMember(member.getMid())) ;
		}
		map.put("member",member) ; 
		return map;
	}

}
