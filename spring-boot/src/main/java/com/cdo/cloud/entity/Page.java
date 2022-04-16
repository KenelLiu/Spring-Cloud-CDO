package com.cdo.cloud.entity;

import java.io.Serializable;

public class Page implements Serializable{

	private static final long serialVersionUID = 2285730225527147018L;
	
	private int nPageIndex = 1;
	private int nPageSize = 10;
	private long totalPage;
	private long totalRecords;
	private int startRecord;
	
	public int getnPageIndex() {
		return nPageIndex;
	}
	public void setnPageIndex(int nPageIndex) {
		if(nPageIndex<=0)
			nPageIndex=1;
		this.nPageIndex = nPageIndex;
	}
	public int getnPageSize() {
		return nPageSize;
	}
	public void setnPageSize(int nPageSize) {
		if(nPageSize>500)
			nPageSize=500;
		if(nPageSize<=0)
			nPageSize=10;
		this.nPageSize = nPageSize;
	}
	public long getTotalPage() {
		if (totalRecords % nPageSize == 0) {
			totalPage = totalRecords / nPageSize;
		} else {
			totalPage = totalRecords / nPageSize + 1;
		}
		if(totalPage<=0)
			totalPage=1;
		return totalPage;		
	}
	
	public long getTotalRecords() {
		return totalRecords;
	}
	
	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}
	
	public int getStartRecord() {
		long nTotalPage=getTotalPage();
		if(nPageIndex>nTotalPage){
			nPageIndex=(int)nTotalPage;
		}
		startRecord = (nPageIndex - 1) * nPageSize;		
		return startRecord;		
	}
}
