package com.user.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.user.dao.UserDao;
import com.user.data.DbUser;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserDao userDao;

	public CustomUserDetailsService() {
		System.out.println("CustomUserDetailsService has been made");
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		System.out.println(username
				+ "hererere!!!!!!!!!!!!!!!!!!!!!!!!!!!22222");
		List<DbUser> userList = userDao.listUser();
		System.out.println(username + "hererere!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		DbUser currentUser = null;
		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).getName().equals(username)) {
				System.out.println(username);
				currentUser = userList.get(i);
			}
		}

		UserDetails user = new User(currentUser.getName(),
				currentUser.getPassword(), true, true, true, true,
				this.getAuthorities(currentUser.getAccountType()));

		return user;
	}

	/**
	 * get authorities of user
	 * 
	 * @param accountType
	 * @return
	 */
	public Collection<GrantedAuthority> getAuthorities(String accountType) {
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);

		if (accountType.equals("user")) {
			authList.add(new GrantedAuthorityImpl("ROLE_USER"));
		}

		if (accountType.equals("admin")) {
			authList.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
		}
		return authList;
	}

}
