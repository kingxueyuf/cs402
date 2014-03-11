package com.user.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.product.data.Product;
import com.product.data.SearchItem;
import com.user.data.DbUser;
import com.user.service.UserService;

@Controller
@RequestMapping("/userAuthentication")
public class UserController {

	@Autowired
	private UserService uService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginPage(
			@RequestParam(value = "error", required = false) boolean error,
			ModelMap model) {
		model.put("error", error);
		return "login";
	}

	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public @ResponseBody
	String checkLogin(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			ModelMap model, HttpSession session) {

		String uid = uService.checkLogin(username, password);

		if (uid != null) {
			session.setAttribute("currentUser", username);
			session.setAttribute("uid", uid);
			return "success";

		} else {
			return "failure";
		}
	}

	@RequestMapping(value = "/denied", method = RequestMethod.GET)
	public String getDeniedPage(
			@RequestParam(value = "error", required = false) boolean error,
			ModelMap model) {

		model.put("error", error);
		return "denied";
	}

}
