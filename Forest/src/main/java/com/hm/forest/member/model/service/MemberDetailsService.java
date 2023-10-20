package com.hm.forest.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hm.forest.member.model.mapper.MemberMapper;
import com.hm.forest.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberDetailsService implements UserDetailsService {
	
	@Autowired
	private MemberMapper mapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = mapper.selectMemberById(username);
		
		if(member == null) {
			throw new UsernameNotFoundException(username + "not found");
		}
		
		log.info("username : " + member.getId());
		log.info("role : " + member.getRole());

        return member;
    }
}