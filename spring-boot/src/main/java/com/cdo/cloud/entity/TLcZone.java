package com.cdo.cloud.entity;

import java.util.Date;
import java.util.List;

public class TLcZone  extends BaseModel{
	
	private static final long serialVersionUID = 6900095081870432118L;

	private String code;

    private String name;

    private String type;

    private String mapCode;

    private String createUser;

    private Date createTime;

    private String modifyUser;

    private Date modifyTime;
    //=======extra======//    
    private String mapName;
    private String fileUrl;
    private List<String> beacons;
    private List<TLcZoneVertex> vertexs;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMapCode() {
		return mapCode;
	}

	public void setMapCode(String mapCode) {
		this.mapCode = mapCode;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public List<String> getBeacons() {
		return beacons;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public List<TLcZoneVertex> getVertexs() {
		return vertexs;
	}

	public void setVertexs(List<TLcZoneVertex> vertexs) {
		this.vertexs = vertexs;
	}

	public void setBeacons(List<String> beacons) {
		this.beacons = beacons;
	}
}