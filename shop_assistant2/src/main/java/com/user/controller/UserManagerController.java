package com.user.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.user.data.DbUser;
import com.user.service.UserService;

@Controller
@RequestMapping("/user")
public class UserManagerController {

	@Autowired
	UserService uService;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody
	String getLoginPage(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			ModelMap model, HttpServletResponse response, HttpSession session) {

		DbUser user = getUser(username, password);
		String uid = uService.addUser(user);

		session.setAttribute("currentUser", username);
		session.setAttribute("uid", uid);
		// response.addCookie(new Cookie("login-user", username));
		return "success";
	}

	@RequestMapping(value = "/current", method = RequestMethod.GET)
	public @ResponseBody
	String getCurrentUser(ModelMap model, HttpServletResponse response,
			HttpSession session) {

		String username = (String) session.getAttribute("currentUser");
		if (username == null || username.equals("")) {
			return "null";
		}
		return username;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public @ResponseBody
	String logout(ModelMap model, HttpServletResponse response,
			HttpSession session) {

		session.setAttribute("currentUser", null);
		session.setAttribute("uid",null);
		return "success";
	}

	public DbUser getUser(String username, String password) {
		DbUser user = new DbUser();
		user.setName(username);
		user.setPassword(password);
		user.setAccountType("user");
		return user;
	}

}
