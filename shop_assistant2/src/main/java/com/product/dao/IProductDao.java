package com.product.dao;

import java.util.List;

import com.product.data.Product;

public interface IProductDao {

	public List<Product> listProduct();

	public void addProduct(Product product);

	public void removeProduct(Product product);
}
