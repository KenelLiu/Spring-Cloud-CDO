package com.cdo.cloud.entity;

import java.math.BigDecimal;

public class TLcZoneVertex {
    private String code;

    private BigDecimal x;

    private BigDecimal y;

    public String getCode() {
        return code;
    }

	public BigDecimal getX() {
		return x;
	}

	public void setX(BigDecimal x) {
		this.x = x;
	}

	public BigDecimal getY() {
		return y;
	}

	public void setY(BigDecimal y) {
		this.y = y;
	}

	public void setCode(String code) {
		this.code = code;
	}
}