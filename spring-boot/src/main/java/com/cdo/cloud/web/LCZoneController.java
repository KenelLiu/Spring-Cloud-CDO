package com.cdo.cloud.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cdo.cloud.entity.Page;
import com.cdo.cloud.entity.TLcZone;
import com.cdo.cloud.exception.BussinessException;
import com.cdo.cloud.service.LCZoneService;


@RestController
@RequestMapping("/zone")
public class LCZoneController {
	
	private static Log logger=LogFactory.getLog(LCZoneController.class);
    @Autowired
    private  HttpServletRequest request;
    
	@Resource(name="LCZoneService")
	private LCZoneService lcZoneService;	
	
	/**
	 * content-Type:application/json
	 * @param body
	 * @return
	 */
	@RequestMapping(path="/add",method={RequestMethod.POST},produces={"application/json;charset=utf-8"})
	public String add(@RequestBody String body){	
		JSONObject retJson=new JSONObject();
		try{
			TLcZone tlcMap=com.alibaba.fastjson.JSON.parseObject(body,TLcZone.class);
			if(tlcMap.getCode()==null|| tlcMap.getCode().equals(""))
				throw new BussinessException("参数编号不存在,不能添加");
			String user="";
			tlcMap.setCreateUser(user);
			tlcMap.setModifyUser(user);
			
			lcZoneService.add(tlcMap);
//			retJson.put(IOTConstants.Response.Status, IOTConstants.Response.Status_SUCCESS);
			retJson.put("message","保存成功");
		}catch(BussinessException e1){
			try{
				retJson.put("message", e1.getMessage());
			}catch(Exception e){}
			logger.error(e1.getMessage(),e1);
		}catch(Throwable ex){
			try{
				retJson.put("message", "后台发生异常,联系管理员.");
			}catch(Exception e){}
			logger.error(ex.getMessage(),ex);
		}		
		return retJson.toString();
	}
	
	@RequestMapping(path="/update",method={RequestMethod.POST},produces={"application/json;charset=utf-8"})
	public String update(@RequestBody String body){	
		JSONObject retJson=new JSONObject();
		try{
			TLcZone tlcMap=com.alibaba.fastjson.JSON.parseObject(body,TLcZone.class);
			if(tlcMap.getCode()==null|| tlcMap.getCode().equals(""))
				throw new BussinessException("编号不存在,不能添加");
		
			tlcMap.setModifyUser("");			
			lcZoneService.update(tlcMap,true);				
			//retJson.put(IOTConstants.Response.Status, IOTConstants.Response.Status_SUCCESS);
			retJson.put("message","保存成功");
		}catch(BussinessException e1){
			try{				
				retJson.put("message", e1.getMessage());
			}catch(Exception e){}
			logger.error(e1.getMessage(),e1);
		}catch(Throwable ex){
			try{
				retJson.put("message", "后台发生异常,联系管理员.");
			}catch(Exception e){}
			logger.error(ex.getMessage(),ex);
		}		
		return retJson.toString();
	}
	
	@RequestMapping(path="/delete",method={RequestMethod.DELETE},produces={"application/json;charset=utf-8"})
	public String delete(){	
		JSONObject retJson=new JSONObject();
		try{
			String code=request.getParameter("code")==null?"":request.getParameter("code").trim(); 
			if(code==null || code.equals(""))
				throw new BussinessException("参数编号不存在,不能删除数据");							
			int nRecord=lcZoneService.deleteByKey(code);
			if(nRecord==0){
		
				retJson.put("message","根据code["+code+"]未查询到记录,数据可能已被删除.");
			}else{		

				retJson.put("message","删除成功");
			}
		}catch(BussinessException e1){
			try{
				retJson.put("message", e1.getMessage());
			}catch(Exception e){}
			logger.error(e1.getMessage(),e1);			
		}catch(Throwable ex){
			try{
				retJson.put("message", "后台发生异常,联系管理员.");
			}catch(Exception e){}
			logger.error(ex.getMessage(),ex);
		}		
		return retJson.toString();
	}
	
	@RequestMapping(path="/getListTLcZone",method={RequestMethod.POST},produces={"application/json;charset=utf-8"})
	public String getListTLcMap(@RequestBody String body){	
		JSONObject retJson=new JSONObject();
		try{			
			TLcZone tlcMap=com.alibaba.fastjson.JSON.parseObject(body,TLcZone.class);
			Page page=tlcMap.getPage()==null?new Page():tlcMap.getPage();
			tlcMap.setPage(page);			
			long totalRecords=lcZoneService.getCount(tlcMap);
			page.setTotalRecords(totalRecords);		
			
			List<TLcZone> listTLcMap=lcZoneService.getListModel(tlcMap);
			String arr=com.alibaba.fastjson.JSONArray.toJSONStringWithDateFormat(listTLcMap,"yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat);
			JSONObject dataJson=new JSONObject();
			dataJson.put("listTLcZone", new JSONArray(arr));
			//dataJson.put("page", PageUtils.getJSONPage(page));
			retJson.put("data", dataJson);
			
			retJson.put("message","获取数据成功");
		}catch(Throwable ex){
			try{
				
				retJson.put("message", "后台发生异常,联系管理员.");
			}catch(Exception e){}
			logger.error(ex.getMessage(),ex);
		}		
		return retJson.toString();
	}
	
}
 
