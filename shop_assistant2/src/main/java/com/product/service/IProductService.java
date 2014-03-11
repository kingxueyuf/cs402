package com.product.service;

import java.util.List;

import com.product.data.Product;

public interface IProductService {

	public List<Product> getProductOnCategory(Product product);
}
