package com.amitru.mystockalert.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amitru.mystockalert.beans.Stock;
import com.amitru.mystockalert.beans.WatchListStock;
import com.amitru.mystockalert.interfaces.MyStockAlertI;
import com.amitru.mystockalert.service.MyStockServiceImpl;
import com.amitru.mystockalert.util.MailUtil;
import com.amitru.mystockalert.util.XLSUtil;

public class StockAlertImpl implements MyStockAlertI {
	
	private static StockAlertImpl instance = new StockAlertImpl(); //Object created in advance

	public static StockAlertImpl getInstance() {
	      return instance;
	}
	
	List<WatchListStock> watchListStockList;
	List<WatchListStock> triggerStocks=new ArrayList<WatchListStock>();
	
	//Read Data from Data Source {xls/db/etc}
	@Override
	public void readDataFromSource() throws Exception {
		this.watchListStockList = new XLSUtil().readDataFromXLS();
	}
	
	//Call Webservice of Stocks based on certain intervals
	@Override
	public void fetchWatchListShareDetails() throws Exception
	{
		if(watchListStockList!=null) {
			Map<String,String> stockPriceMap = MyStockServiceImpl.getInstance().fetchCurrentPriceForMultipleStocks(watchListStockList);
			System.out.println(stockPriceMap);
			//Now check price for trigger and set liveprice.. & make a new list
			findStocksForEmailAlert(stockPriceMap, this.watchListStockList);
		}
		
		
	}
	
	private  void findStocksForEmailAlert(Map<String,String> stockPriceMap,List<WatchListStock> wList) {
		
		for(WatchListStock wLStock: wList) {
			double intrestedPrice = wLStock.getIntrestedPrice();
			double livePrice = Double.parseDouble(stockPriceMap.get(wLStock.getScriptCode() + "." + wLStock.getScriptType()));
			
			if(livePrice<=intrestedPrice) {
				System.out.println("Price alert triggered for stock-" + wLStock.getScriptCode() );
				wLStock.setLivePrice(livePrice);
				this.triggerStocks.add(wLStock);
			}
		}
	}
	
	
	@Override
	public void triggerEmailForWatchList() {
		System.out.println("Trigerring EMAIL: triggerEmailForWatchList() entered...");
		MailUtil.getInstance().sendEmail(this.triggerStocks);
		
	}
	
}
