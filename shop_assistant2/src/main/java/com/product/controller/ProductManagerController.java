package com.product.controller;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.product.data.Product;
import com.product.service.ProductService;

/**
 * @author robin-xue This controller is for administrator All the path should be
 *         spring secured
 */
@Controller
@RequestMapping("/productmanager")
public class ProductManagerController {

	@Autowired
	private ProductService proService;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private ServletConfig config;

	@RequestMapping(value = "/getallproduct", method = RequestMethod.GET)
	public @ResponseBody
	List<Product> getProductList(Model model) {

		return proService.getProduct();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String toManagePage(Model model) {

		return "redirect:/pages/productmanager.html";
	}

	@RequestMapping(value = "/addproduct", method = RequestMethod.POST)
	public String addProduct(
			Model model,
			@RequestParam(value = "p_name", required = true) String name,
			@RequestParam(value = "price", required = true) String price,
			@RequestParam(value = "vip_price", required = true) String vip_price,
			@RequestParam(value = "location", required = true) String location,
			@RequestParam(value = "amount", required = false) int amount,
			@RequestParam(value = "brand", required = false) String brand,
			@RequestParam(value = "category", required = true) String category,
			@RequestParam(value = "detail", required = false) String detail,
			@RequestParam(value = "other", required = false) String other,
			@RequestParam(value = "picture", required = true) MultipartFile picture) {

		// ServletContext application = session.getServletContext();
		// String serverRealPath = application.getRealPath("/") ;
		// System.out.println(System.getProperty("catalina.base"));

		String appPath = config.getServletContext().getRealPath("/");

		System.out.println(appPath);
		System.out.println("Add a new product");
		String[] path = proService.storeFile(picture, appPath);

		Product product = generateVO(name, price, vip_price, location, amount,
				brand, category, detail, other, path);

		proService.addProduct(product);

		return "redirect:/pages/productmanager.html";
	}

	@RequestMapping(value = "/deleteproduct", method = RequestMethod.GET)
	public String deleteProduct(
			@RequestParam(value = "id", required = true) String id) {

		proService.deleteProduct(id);

		return "redirect:/pages/productmanager.html";
	}

	public Product generateVO(String name, String price, String vip_price,
			String location, int amount, String brand, String category,
			String detail, String other, String[] path) {
		Product pro = new Product();
		pro.setName(name);
		pro.setNormalPrice(price);
		pro.setMemberPrice(vip_price);
		pro.setLocation(location);
		pro.setAmount(amount);
		pro.setBrand(brand);
		pro.setCategory(category);
		pro.setPictureUrl(path[0]);
		return pro;

	}

}
