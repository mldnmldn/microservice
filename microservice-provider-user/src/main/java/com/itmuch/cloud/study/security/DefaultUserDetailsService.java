package com.itmuch.cloud.study.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.itmuch.cloud.study.entity.Member;
import com.itmuch.cloud.study.service.IMemberService;

public class DefaultUserDetailsService implements UserDetailsService {

	@Autowired
	private IMemberService memberService;
	
	@SuppressWarnings("unchecked")
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      
		Map<String,Object> map = this.memberService.get(username);
		Member member = (Member) map.get("member");  //取得用户信息
		if(member == null) {
			throw new UsernameNotFoundException("用户不存在,请重新注册");
		}
		
		Set<String> allRoles = (Set<String>) map.get("allRoles"); //取得用户的角色信息
		List<GrantedAuthority> allGrantedAuthority = new ArrayList<GrantedAuthority>();
		allRoles.stream().forEach(role ->{
			allGrantedAuthority.add(new SimpleGrantedAuthority(role));
		});
		
		User user = new User(username, member.getPassword(), allGrantedAuthority);
		return user;
	}

}
