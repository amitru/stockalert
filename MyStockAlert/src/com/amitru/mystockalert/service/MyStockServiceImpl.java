package com.amitru.mystockalert.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.amitru.mystockalert.beans.Stock;
import com.amitru.mystockalert.beans.WatchListStock;

import yahoofinance.YahooFinance;

public class MyStockServiceImpl implements MyStockServiceI {

	private static MyStockServiceImpl instance = new MyStockServiceImpl(); //Object created in advance
	private static final String URL ="http://finance.yahoo.com/webservice/v1/symbols/PLACEHOLDER/quote?format=json";
	
	public static MyStockServiceImpl getInstance() {
	      return instance;
	}
	
	//TODO IMP: http://stackoverflow.com/questions/885456/stock-ticker-symbol-lookup-api
	//for getting mutiple share price at once
	//Code should be changed, perf impr
	//http://financequotes-api.com/
	//http://finance.yahoo.com/webservice/v1/symbols/BRITANNIA.NS/quote?format=json
	//Great news comma also working....
	//http://finance.yahoo.com/webservice/v1/symbols/BRITANNIA.NS,GABRIEL.NS/quote?format=json
	//http://in.finance.yahoo.com/q/hp?s=AAPL-- for history
	@Override
	public double fetchCurrentPrice(Stock stock) throws Exception
	{
		//yahoofinance direct API Not working for indian stocks, for INTC- intel it works
		//yahoofinance.Stock st = YahooFinance.get("INTC");
		//System.out.println(st.getQuote(true).getPrice());
		//String URL ="http://finance.yahoo.com/webservice/v1/symbols/BRITANNIA.NS/quote?format=json";
		//http://finance.google.com/finance/info?q=NSE:GABRIEL
		
		System.out.println("fetchCurrentPrice() entered " + stock.getScriptCode());
		
		double price = 0;
		
		String dynamicURL = URL;
		
		dynamicURL = dynamicURL.replaceAll("PLACEHOLDER", stock.getScriptCode()+"." + stock.getScriptType());
		
		System.out.println("URL-" + dynamicURL);
		
		URL YahooURL = new URL(dynamicURL);
        HttpURLConnection con = (HttpURLConnection) YahooURL.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        
        in.close();
        
        JSONObject stockJsonObj  = new JSONObject(response.toString());
        JSONObject listObject = stockJsonObj.getJSONObject("list");
        JSONArray resourcesArray = listObject.getJSONArray("resources");
        JSONObject resourceObject = new JSONObject(resourcesArray.get(0).toString());
        JSONObject res = new JSONObject(resourceObject.get("resource").toString());
        JSONObject priceObj = new JSONObject(res.get("fields").toString());
        
        price = Double.parseDouble(priceObj.get("price").toString());
        
        System.out.println("Current price for stock " + stock.getScriptCode() + " is=" + priceObj.get("price"));
        
		return price;
		
	}
	
	
	//MaxLimit is 200 shares can be added...
	
	public Map<String,String> fetchCurrentPriceForMultipleStocks(List<WatchListStock> stock) throws Exception
	{
		//http://finance.yahoo.com/webservice/v1/symbols/BRITANNIA.NS,GABRIEL.NS/quote?format=json
		
		/*
		This is not working for indian stocks, only GOOGLE APPLE works 
		String[] symbols = new String[] {"NSE:ATULAUTO"};
		Map<String, yahoofinance.Stock> stocks = YahooFinance.get(symbols); 

		for(yahoofinance.Stock s : stocks.values()) {
		    System.out.println(s.getName() + ": " + s.getQuote().getPrice());
		}*/
		
		HashMap<String, String> stockPriceMap = new HashMap<>();
		
		
		System.out.println("fetchCurrentPriceForMultipleStocks() entered stock list size -" + stock.size());
		
	
		
		String dynamicURL = URL;
		String stockString = "";
		
		for(Stock myStock: stock) {
			stockString = stockString + myStock.getScriptCode() + "." + myStock.getScriptType() + ",";
		}
		
		stockString = stockString.substring(0, stockString.length()-1);
		
		dynamicURL = dynamicURL.replaceAll("PLACEHOLDER", stockString);
		
		System.out.println("URL-" + dynamicURL);
		
		URL YahooURL = new URL(dynamicURL);
        HttpURLConnection con = (HttpURLConnection) YahooURL.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        
        in.close();
        
        JSONObject stockJsonObj  = new JSONObject(response.toString());
        JSONObject listObject = stockJsonObj.getJSONObject("list");
        JSONArray resourcesArray = listObject.getJSONArray("resources");
        
        System.out.println("resourcesArray size " + resourcesArray.length());
        
	   for(int i=0;i<resourcesArray.length();i++) {
	        JSONObject resourceObject = new JSONObject(resourcesArray.get(i).toString());
	        JSONObject res = new JSONObject(resourceObject.get("resource").toString());
	        JSONObject priceObj = new JSONObject(res.get("fields").toString());
	        stockPriceMap.put(priceObj.get("symbol").toString(), priceObj.get("price").toString());
	        
	        
	        }
        
       // System.out.println("Current price for stock " + stock.getScriptCode() + " is=" + priceObj.get("price"));
        
		return stockPriceMap;
		
	}
	
	public static void main(String[] args) throws Exception {
	
		Stock stock = new Stock("GABRIEL", "NS");
		List<WatchListStock> myStockList = new ArrayList<WatchListStock>();
		myStockList.add(new WatchListStock("BRITANNIA","NS"));
		myStockList.add(new WatchListStock("GABRIEL","NS"));
		MyStockServiceImpl.getInstance().fetchCurrentPriceForMultipleStocks(myStockList);
		
	}
	
}

