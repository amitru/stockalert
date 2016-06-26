package com.amitru.mystockalert.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.amitru.mystockalert.beans.WatchListStock;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class XLSUtil {

	public List<WatchListStock> readDataFromXLS() throws Exception {
		
		WatchListStock wls = new WatchListStock();
		
		List<WatchListStock> watchList = new ArrayList<WatchListStock>();
		
		String inputFile = "d:\\MyStockAlerts.xls";
		
		File inputWorkbook = new File(inputFile);
		
		Workbook w = Workbook.getWorkbook(inputWorkbook);
	    Sheet sheet = w.getSheet(0);
	    int col=0;
	    
	    for (int row = 1; row < sheet.getRows(); row++) {
	        //for (int col = 0; col < sheet.getColumns(); col++) {
	          
	    	  col=0;
	          //Cell cell = sheet.getCell(col,row);
	    	  wls = new WatchListStock();
	    	  Cell cell = sheet.getCell(col,row);
	          wls.setScriptCode(cell.getContents());
	          
	          col=col+1;
	          cell = sheet.getCell(col,row);
	          wls.setScriptType(cell.getContents());
	          
	          col=col+1;
	          cell = sheet.getCell(col,row);
	          wls.setIntrestedPrice(Integer.parseInt(cell.getContents()));
	          
	          col=col+1;
	          cell = sheet.getCell(col,row);
	          wls.setEmailIdList(Arrays.asList(cell.getContents().split("\\s*,\\s*")));
	          
	          col=col+1;
	          cell = sheet.getCell(col,row);
	          wls.setEmailFlag((cell.getContents()=="Y"?true:false));
	          
	          col=col+1;
	          cell = sheet.getCell(col,row);
	          wls.setComments(cell.getContents());
	          
	          watchList.add(wls);
	          
	        //}
	    }
	    
		return watchList;
	}
	
	public static void main(String[] args) throws Exception {
		
		new XLSUtil().readDataFromXLS();
		
	}
	
}
