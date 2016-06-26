package com.amitru.mystockalert.service;

import com.amitru.mystockalert.beans.Stock;

public interface MyStockServiceI {

	public double fetchCurrentPrice(Stock stock) throws Exception;
	
}
