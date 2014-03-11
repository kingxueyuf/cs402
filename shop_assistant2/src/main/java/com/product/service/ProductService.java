package com.product.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.common.util.FileStorage;
import com.product.dao.ProductDao;
import com.product.dao.UserProductDao;
import com.product.data.Product;
import com.product.data.SearchItem;
import com.product.data.UserProduct;

@Service
public class ProductService implements IProductService {

	@Autowired
	private ProductDao productDao;

	@Autowired
	private UserProductDao upDao;

	public List<Product> getProduct() {
		return productDao.listProduct();
	}

	public List<Product> getProductOnCategory(Product product) {
		// TODO Auto-generated method stub
		List<Product> list = productDao.listProduct();
		List<Product> resultList = new ArrayList<Product>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getCategory().equals(product.getCategory())) {
				resultList.add(list.get(i));
			}
		}
		return resultList;
	}

	public List<Product> getProductOnSearch(SearchItem item) {
		List<Product> list = productDao.listProduct();
		List<Product> resultList = new ArrayList<Product>();
		String keyWord = item.getKeyWord().toLowerCase();
		for (int i = 0; i < list.size(); i++) {
			Product product = list.get(i);
			if (product.getCategory().toLowerCase().contains(keyWord)
					|| product.getName().toLowerCase().contains(keyWord)) {
				resultList.add(list.get(i));
			}
		}
		return resultList;
	}

	public void addProduct(Product product) {

		productDao.addProduct(product);

	}

	public void addUserProduct(UserProduct up) {
		upDao.add(up);
	}

	public String[] storeFile(MultipartFile picture, String appPath) {

		return FileStorage.uploadFile(picture, appPath);
	}

	public void deleteProduct(String id) {
		// TODO Auto-generated method stub
		productDao.deleteProduct(Integer.valueOf(id));
		upDao.deleteByPid(Integer.valueOf(id));
	}

	public List<Product> viewUserWatchList(String uid) {
		// TODO Auto-generated method stub

		List<UserProduct> upList = upDao.view();
		HashSet<String> pidSet = new HashSet<String>();

		for (int i = 0; i < upList.size(); i++) {
			if (uid.equals(upList.get(i).getUid() + "")) {
				pidSet.add(upList.get(i).getPid() + "");
			}
		}

		List<Product> pList = productDao.listProduct();

		List<Product> userWatchList = new ArrayList<Product>();
		for (int i = 0; i < pList.size(); i++) {
			if (pidSet.contains(pList.get(i).getProductId().toString())) {
				pList.get(i).setBought("true");
				userWatchList.add(pList.get(i));
			}
		}

		return userWatchList;
	}

	public void updateListOnWatchList(List<Product> list, String uid) {
		// TODO Auto-generated method stub
		System.out.println("uid----" + uid);
		List<UserProduct> upList = upDao.view();
		HashSet<String> pidSet = new HashSet<String>();

		for (int i = 0; i < upList.size(); i++) {
			System.out.println("_____" + upList.get(i).getUid());
			if (uid.equals(upList.get(i).getUid() + "")) {
				pidSet.add(upList.get(i).getPid() + "");
			}
		}

		for (int i = 0; i < list.size(); i++) {
			if (pidSet.contains(list.get(i).getProductId().toString())) {
				list.get(i).setBought("true");
				System.out.println("here");
			}
		}
	}

	public void deleteUserProduct(UserProduct up) {
		// TODO Auto-generated method stub
		List<UserProduct> upList = upDao.view();
		String upId = "";
		for (int i = 0; i < upList.size(); i++) {
			if (upList.get(i).getPid().intValue() == up.getPid().intValue()
					&& upList.get(i).getUid().intValue() == up.getUid()
							.intValue()) {
				upId = upList.get(i).getId() + "";
			}

		}
		if (!upId.equals("")) {
			upDao.delete(upId);
		}
	}
}
