package com.amitru.mystockalert.beans;

import java.util.List;

public class WatchListStock extends Stock {

	private double intrestedPrice;
	private double livePrice;
	private boolean emailFlag;
	private List<String> emailIdList;
	private String comments;
	
	public WatchListStock() {
	
	}
	
	public WatchListStock(String sCode, String sType) {
		this.setScriptCode(sCode);
		this.setScriptType(sType);
	}
	
	public WatchListStock(int price, boolean flg, List<String> eids, String comments) {
		
	}

	
	public double getLivePrice() {
		return livePrice;
	}

	public void setLivePrice(double livePrice) {
		this.livePrice = livePrice;
	}

	public double getIntrestedPrice() {
		return intrestedPrice;
	}

	public void setIntrestedPrice(double intrestedPrice) {
		this.intrestedPrice = intrestedPrice;
	}

	public boolean isEmailFlag() {
		return emailFlag;
	}

	public void setEmailFlag(boolean emailFlag) {
		this.emailFlag = emailFlag;
	}

	public List<String> getEmailIdList() {
		return emailIdList;
	}

	public void setEmailIdList(List<String> emailIdList) {
		this.emailIdList = emailIdList;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	
	
}
