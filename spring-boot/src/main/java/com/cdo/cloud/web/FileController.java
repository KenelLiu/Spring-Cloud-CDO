package com.cdo.cloud.web;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.cdo.cloud.config.UploadConfig;

@RestController
@RequestMapping("/file")
public class FileController {
	
	private static Log logger=LogFactory.getLog(FileController.class);
	//@Resource(name="LCMapService")
	//private LCMapService lcMapService;	
	
	@Autowired
	private UploadConfig uploadConfig;
	
	//@Autowired
	//private Environment environment;

    @Autowired
    private  HttpServletRequest request;
	
	@RequestMapping(value = "/upload",method ={RequestMethod.POST},produces={"application/json;charset=utf-8"})
    @ResponseBody
	private String upload(MultipartRequest multRequest){
		JSONObject retJson=new JSONObject();
		try{
			//========type为文件父类目==========//
			String type=request.getParameter("type")==null?"":request.getParameter("type").trim();
			//=======根据code更新fileUrl数据======//
			String code=request.getParameter("code")==null?"":request.getParameter("code").trim();
			if(type==null|| type.equals(""))
				throw new JSONException("参数type未填写");
			if(!uploadConfig.getFileParentPath().contains(type))
				throw new JSONException("不支持参数type["+type+"],请使用规定参数值");
			Map<String, MultipartFile> mapFile=multRequest.getFileMap();	
			if(mapFile==null || mapFile.size()==0)
				throw new JSONException("文件未上传");
			//=====查询更新的记录是否存在========//
			
			
			String fileUrl="";
			for(Iterator<Entry<String, MultipartFile>> it=mapFile.entrySet().iterator();it.hasNext();){
				Entry<String, MultipartFile> entry=it.next();
				String fileName=entry.getKey();
				MultipartFile multiPartFile=entry.getValue();
				if(multiPartFile==null){
					throw new NullPointerException(fileName+" is not exists");
				}
				//=======判断文件类型是否被支持=========//
				String originName=multiPartFile.getOriginalFilename();
				int idxOriginalName=originName.lastIndexOf(".");
				if(idxOriginalName==-1){
					String value=uploadConfig.getPicMap().keySet().stream().distinct().collect(Collectors.joining(","));
					throw new JSONException("文件类型不支持,当前支持文件类型["+value+"],OriginalFileName="+originName);
				}		
				String sufixOriginalName=originName.substring(idxOriginalName+1).toLowerCase();
				
				if(!uploadConfig.getPicMap().keySet().contains(sufixOriginalName)){
					String value=uploadConfig.getPicMap().keySet().stream().distinct().collect(Collectors.joining(","));
					throw new JSONException("文件类型不支持,当前支持文件类型["+value+"],OriginalFileName="+originName);
				}
				//===判断上传的文件完整性==//
				SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
				String suffix=sdf.format(new java.util.Date());
				//String finalName=originName.substring(0,idxOriginalName)+"_"+suffix+"."+sufixOriginalName;
				String finalName=type+"_"+suffix+"."+sufixOriginalName;
				
				String path=uploadConfig.getPath()+"/map";		
				fileUrl="/map/"+finalName;	
				File file=new File(path);
				if(!file.exists() || !file.isDirectory()){
					file.mkdirs();
				}							
				String dest=path+"/"+finalName;
				File destFile=new File(dest);
				if(!destFile.exists()){
					//文件不存在,则不存在多用户同时操作同一文件情况
					multiPartFile.transferTo(destFile);
				}	
				JSONObject data=new JSONObject();
				data.put("fileUrl", fileUrl);
				retJson.put("data", data);
			}
			retJson.put("status", 1);
			retJson.put("message","上传成功");					
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			try{
				retJson.put("status", 0);
				retJson.put("message","上传失败");	    	            	
			}catch(Exception em){}
		}
		return retJson.toString();
	}	
	
	@RequestMapping(value = "/download",method ={RequestMethod.GET})
	public ResponseEntity<InputStreamSource> download(){
		String fileUrl=request.getParameter("fileUrl");   	
		File target = new File(uploadConfig.getPath()+fileUrl);
		//=====构建响应体====//
		if (target.exists()) {
			FileSystemResource resource = new FileSystemResource(target);
			String titleVal=target.getName();
			try {
				titleVal = new String(titleVal.getBytes(), "ISO8859-1");
			} catch (Exception e) {
			}		
			return ResponseEntity.ok()
					.header("Content-Type","application/octet-stream")
					.header("Content-Disposition", "attachment;filename="+titleVal)
					.body(resource);
		}else{
			// 如果文件不存在，返回404响应
			return ResponseEntity.notFound().build();
		}
	}
	
	@RequestMapping(value = "/preview",method ={RequestMethod.GET})
	public ResponseEntity<InputStreamSource> preview(WebRequest request){
		String fileUrl=request.getParameter("fileUrl");   	
		File target = new File(uploadConfig.getPath()+fileUrl);
		//=====构建响应体====//
		if (target.exists()) {
			// 获取文件的最后修改时间
			long lastModified = target.lastModified();
			if (request.checkNotModified(lastModified)) {
				return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
			}
			// 获取文件扩展名
            String ext = fileUrl.substring(fileUrl.lastIndexOf(".")+1).toLowerCase();
            // 根据文件扩展名获取mediaType
            String mediaType =uploadConfig.getPicMap().get(ext);
            // 如果没有找到对应的扩展名，使用默认的mediaType
            if (Objects.isNull(mediaType)) {
                mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }			
			FileSystemResource resource = new FileSystemResource(target);
			return ResponseEntity.ok()
					.cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
					.lastModified(lastModified)
					.header("Content-Type", mediaType)// 指定文件的contentType
					.body(resource);
		}else{
			// 如果文件不存在，返回404响应
			return ResponseEntity.notFound().build();
		}
	}

}
 
