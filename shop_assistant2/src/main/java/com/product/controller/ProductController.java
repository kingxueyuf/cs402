package com.product.controller;

import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.product.data.Product;
import com.product.data.SearchItem;
import com.product.data.UserProduct;
import com.product.service.ProductService;

/**
 * @author robin-xue For user to scan product
 */
@Controller
@RequestMapping("/product")
public class ProductController {
	private static final Logger logger = LoggerFactory
			.getLogger(ProductController.class);

	@Autowired
	private ProductService proService;

	@RequestMapping(method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		// Cookie[] cookies = request.getCookies();
		// for (int i = 0; i < cookies.length; i++) {
		// response.addCookie(cookies[i]);
		// }

		return "redirect:/pages/shop.html";
	}

	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public @ResponseBody
	List<Product> getProductListOnCategory(
			@RequestParam("category") String category, Model model,
			HttpSession session) {

		Product product = generateVO(category);
		System.out.println("param = " + category);

		String uid = (String) session.getAttribute("uid");
		List<Product> list = proService.getProductOnCategory(product);
		if (uid != null) {
			proService.updateListOnWatchList(list, uid);
		}
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getName());
		}
		return list;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public @ResponseBody
	List<Product> getProductListOnSearch(
			HttpSession session,
			@RequestParam("keyword") String keyword, Model model) {

		SearchItem item = generateSearchVO(keyword);
		System.out.println("keyWord = " + keyword);

		String uid = (String) session.getAttribute("uid");
		
		List<Product> list = proService.getProductOnSearch(item);
		
		if (uid != null) {
			proService.updateListOnWatchList(list, uid);
		}
		
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getName());
		}
		return list;
	}

	@RequestMapping(value = "/addwatchlist", method = RequestMethod.POST)
	public @ResponseBody String addUserWatchProduct(@RequestParam("pid") String pid,
			HttpSession session, Model model) {

		String uid = (String) session.getAttribute("uid");
		if (uid != null) {
			UserProduct up = generateUP(pid, uid);
			this.proService.addUserProduct(up);
			return "success";
		}
		return "failure";
	}
	

	@RequestMapping(value = "/jviewwatchlist", method = RequestMethod.GET)
	public String viewUserWatchProductJumper(HttpSession session, Model model) {

		return "redirect:/pages/watchlist.html";
	}
	
	@RequestMapping(value = "/viewwatchlist", method = RequestMethod.POST)
	public @ResponseBody
	List<Product> viewUserWatchProduct(HttpSession session, Model model) {

		String uid = (String) session.getAttribute("uid");

		List<Product> list = this.proService.viewUserWatchList(uid);
		return list;
	}

	@RequestMapping(value = "/deletewatchlist", method = RequestMethod.POST)
	public @ResponseBody
	String deleteUserWatchProduct(HttpSession session, Model model,@RequestParam("pid") String pid) {

		String uid = (String) session.getAttribute("uid");
		
		UserProduct up = generateUP(pid,uid);

		this.proService.deleteUserProduct(up);
		
		return "success";
	}

	private UserProduct generateUP(String pid, String uid) {
		// TODO Auto-generated method stub
		UserProduct up = new UserProduct();
		up.setPid(Integer.valueOf(pid));
		up.setUid(Integer.valueOf(uid));
		return up;
	}

	public Product generateVO(String category) {
		Product VO = new Product();
		VO.setCategory(category);
		return VO;
	}

	public SearchItem generateSearchVO(String keyWord) {
		SearchItem item = new SearchItem();
		item.setKeyWord(keyWord);
		return item;
	}

}
