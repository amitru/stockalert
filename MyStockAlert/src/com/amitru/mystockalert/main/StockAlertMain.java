package com.amitru.mystockalert.main;

import com.amitru.mystockalert.impl.StockAlertImpl;

public class StockAlertMain {

	public void init() {
		
	}
	
	public static void main(String[] args) throws Exception {
		
		//STEP 1: Read DATA from source, DB,XLS or any VO
		StockAlertImpl.getInstance().readDataFromSource();
		
		//STEP 2: CALL WS AND FETCH SHARE DETAILS, CALL THIS METHOD ON EVERY 5 MINS OR SO
		StockAlertImpl.getInstance().fetchWatchListShareDetails();
		
		//STEP3: TRIGGER EMAIL FOR SELCTED SCRIPTS found at STEP-2
		StockAlertImpl.getInstance().triggerEmailForWatchList();
		
	}

}
