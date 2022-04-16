package com.cdo.cloud.entity;

import java.io.Serializable;

public class BaseModel implements Serializable{

	private static final long serialVersionUID = -928302475169206326L;
	
	private Page page;
	
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
}
