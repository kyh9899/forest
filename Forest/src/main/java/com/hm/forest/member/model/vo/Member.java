package com.hm.forest.member.model.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Member implements UserDetails{
	
private static final long serialVersionUID = -6789046796860791311L;
	
	private int no;
	
	private String id;
	
	private String password;
	
	private String name;
	
	private String phone;
	
	private String email;
	
	private String pcode;

	private String address1;
	
	private String address2;
	
	private String status;
	
	private Date createDate;
	
	private Date updateDate;

	private String role;
	
	@Override
	public String getUsername() {
		
		return this.id;
	}
	
	@Override
	public String getPassword() {
		
		return this.password;
	}
	
	// 계정의 권한의 목록을 리턴한다.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		authorities.add(new SimpleGrantedAuthority(this.role));
		
		return authorities;
	}

	@Override
	// 계정 만료 여부
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	// 계정 잠김 여부
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	// 패스워드 만료 여부
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	// 계정 활성화 여부
	public boolean isEnabled() {

		return this.status.equals("Y");
	}
}