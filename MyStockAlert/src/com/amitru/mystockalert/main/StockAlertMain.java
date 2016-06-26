package com.amitru.mystockalert.main;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.amitru.mystockalert.impl.StockAlertImpl;

public class StockAlertMain {

	public void init() {
		
	}
	
	public static void main(String[] args) throws Exception {
		
		
		ScheduledExecutorService executor =
			    Executors.newSingleThreadScheduledExecutor();

			Runnable periodicTask = new Runnable() {
			    public void run() {
			        // Invoke method(s) to do the work
			        try {
			        	doTasks();
			        } catch(Exception e) {
			        	e.printStackTrace();
			        }
			    }
			};

			executor.scheduleAtFixedRate(periodicTask, 0, 10, TimeUnit.MINUTES);
		
		
		
	}
	
	private static void doTasks() throws Exception {
		
		//STEP 1: Read DATA from source, DB,XLS or any VO
		StockAlertImpl.getInstance().readDataFromSource();
				
		//STEP 2: CALL WS AND FETCH SHARE DETAILS, CALL THIS METHOD ON EVERY 5 MINS OR SO
		StockAlertImpl.getInstance().fetchWatchListShareDetails();
				
		//STEP3: TRIGGER EMAIL FOR SELCTED SCRIPTS found at STEP-2
		StockAlertImpl.getInstance().triggerEmailForWatchList();
		
	}

}
