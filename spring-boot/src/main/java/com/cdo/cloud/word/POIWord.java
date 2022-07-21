package com.cdo.cloud.word;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHpsMeasure;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSym;

import com.cdoframework.cdolib.data.cdo.CDO;


public class POIWord {
	private static Log logger = LogFactory.getLog(POIWord.class);
	private File templateFile; //word 模板
	private File outputFile; // 根据word模板生成文件
	
	private CDO valCDO;//当valCDO.key=templateFile.字段,则templateFile.字段替换成valCDO.value	
	private Set<String> breakKeys;//字段需要回车换行,多个电话情况
	private Set<String> wingdings2;//特殊字符处理
	private Set<String> textAreaKeys;//textArea内容有\\n需要进行换行
	
	//============copyRowWordKeys.key=在word里查找到复制行标识,表示本次table里行需要进行复制行来填充数据;//
	//============copyRowWordKeys.value=填充到复制行里具体数据=================//;
	private Map<String,List<CDO>> copyRowWordKeys;
	
	public POIWord(File templateFile,File outputFile,CDO valCDO){
		this.templateFile=templateFile;
		this.outputFile=outputFile;
		this.valCDO=valCDO;
	}

	public void setBreakKeys(Set<String> breakKeys) {
		this.breakKeys = breakKeys;
	}

	public void setWingdings2(Set<String> wingdings2) {
		this.wingdings2 = wingdings2;
	}	
	
	public void setTextAreaKeys(Set<String> textAreaKeys) {
		this.textAreaKeys = textAreaKeys;
	}
	
	public void setCopyRowWordKeys(Map<String, List<CDO>> copyRowWordKeys) {
		this.copyRowWordKeys = copyRowWordKeys;
	}

	public void process() throws OpenXML4JException{
		XWPFDocument doc=null;
		FileOutputStream os=null;
		String text=null;
        try{             
        	doc = new XWPFDocument(OPCPackage.open(templateFile));
        	//========处理段落=========//
        	processParagraph(doc.getParagraphs(),this.valCDO,false);
        	//========处理表格数据======//
        	processTable(doc);
        	os=new FileOutputStream(outputFile);
        	doc.write(os);
        } catch (Exception e) {
        	e.printStackTrace();
        	throw new OpenXML4JException("key:"+text+";"+e.getMessage(),e);
        }finally{
        	if(os!=null)try{os.close();}catch(Exception ex){}
    	//不能关闭,否则模板中的变量被回写
    	//if(doc!=null)try{doc.close();}catch(Exception ex){} 
        }
	}
	
	private void processTable(XWPFDocument doc) throws XmlException{
		 List<XWPFTable> tables=doc.getTables();
		 if(tables==null || tables.size()==0) return;
		 String copyKey=null;		 
         for(XWPFTable tab : tables){
             for(XWPFTableRow row : tab.getRows()){
                 for(XWPFTableCell cell : row.getTableCells()){                	 
                	 copyKey=processParagraph(cell.getParagraphs(),this.valCDO,true);
                	 if(copyKey!=null)
                		 break;
                 }//XWPFTableCell
                 if(copyKey!=null)
            		 break;
             }//XWPFTableRow
             if(copyKey!=null){   
            	 copyRow(tab, copyKey);
             }  
             copyKey=null;
         }//XWPFTable
	}
	
	private void copyRow(XWPFTable table,String copyKey) throws XmlException{
	   	 List<CDO> tblListCDO=copyRowWordKeys.get(copyKey);
	   	 if(tblListCDO==null || tblListCDO.size()==0){
	   		 logger.warn("根据key["+copyKey+"],未找到对应填充数据");   		 
	   		 return;
	   	 }            	 
	   	 //========待复制的行共2行,第1行为标题，第二行是模板数据=======//
	   	 XWPFTableRow srcRow=table.getRow(1);//复制第2行
	   	 for (int i = 1; i <tblListCDO.size(); i++) {
				//==========创建新行,将第二行数据进行复制=========//
	   		 XWPFTableRow dstRow=table.createRow();
	   		 POITableRow tabRow=new POITableRow(dstRow, srcRow);
	       	 tabRow.copyTableRow(i);
	   	 }
	   	 
	   	 //========填充数据===========//
	   	 List<XWPFTableRow>  rows=table.getRows();
	   	 for(int i=1;i<rows.size();i++){
	   		CDO data=tblListCDO.get(i-1);
	   		XWPFTableRow row=rows.get(i);
	   		for(XWPFTableCell cell : row.getTableCells()){  
	   			processParagraph(cell.getParagraphs(),data,false);
            }//XWPFTableCell
	   	 }
	}
	
	/**
	 * 
	 * @param paragraphs
	 * @return  true 表示是一个列表table,需要进行复制行处理,否则为普通数据替换
	 * @throws XmlException
	 */
	private String processParagraph(List<XWPFParagraph> paragraphs,CDO valCDO,boolean bCopyRow) throws XmlException{
		if(paragraphs==null || paragraphs.size()==0) return null;	
        for(XWPFParagraph p : paragraphs){
            List<XWPFRun> runs = p.getRuns();
            if(runs != null){
                for(XWPFRun r : runs){
                    String text = r.getText(0);  
                    if(text==null)continue;
                    text=text.trim();
                    logger.info("text="+text);
                    if(copyRowWordKeys!=null && copyRowWordKeys.containsKey(text)){
                    	if(bCopyRow){//不是填充数据
                    		logger.info("查找到复制行标识["+text+"],当前表需要进行复制.");
                    		return text;//需要复制行进行处理
                    	}
                    }     
                    //=========替换数据=========//
                    if(!valCDO.exists(text))continue;                 
                    String value=String.valueOf(valCDO.getObjectValue(text));
                    if(wingdings2!=null && wingdings2.contains(text)){
                    	handleWingdings2(p, r, value);
                    	break;
                    }else if(breakKeys!=null && breakKeys.contains(text)){
                    	handleBreakKeys(r, value);
                    	break;
                    }else if(textAreaKeys!=null && textAreaKeys.contains(text)){
	       				 String[] val=value.split("\\n");                                			                                     			                                    			 
	        			 for(int i=0;i<val.length;i++){
	        				 if(val[i]==null)continue;
	        				 if(i==0){
	        					 r.setText(val[i],0);
	        				 }else{
	        					 r.addBreak();
	        					 r.setText(val[i]);
	        				 }
	        			 }	
                    	break;                    
                    }else{
                    	 r.setText(value,0);
                    }
                }
            }
        }
        return null;
	}
	
	private void handleWingdings2(XWPFParagraph p,XWPFRun r,String value) throws XmlException{
		 r.setText("",0);
		 String[] keyVal=value.split(";");
		 for(int i=0;i<keyVal.length;i++){
			 String[] vals=keyVal[i].split(",");
			 int v=Integer.valueOf(vals[0]);
			 r.setText("   ");                                				 
			 if(i>0){
				 r=getOrAddParagraph(p,true,false);
			  }      
			  String hpsSize="30";
			  if(v==42){
				  hpsSize="30";
			  }else{
				  hpsSize="22";
			  }
			  CTRPr  pRpr = getRunCTRPr(p, r);                                  				      
			  CTHpsMeasure  sz = pRpr.isSetSz() ? pRpr.getSz() : pRpr.addNewSz();  
		      sz.setVal(new BigInteger(hpsSize));                                  				  
		      CTHpsMeasure szCs = pRpr.isSetSzCs() ? pRpr.getSzCs() : pRpr.addNewSzCs();  
		      szCs.setVal(new BigInteger(hpsSize));  
		 	  List<CTSym> symList = r.getCTR().getSymList();  
			  symList.add(getCTSym("Wingdings 2", "F0" + Integer.toHexString(v)));
			  r=getOrAddParagraph(p,true,false);
			  r.setFontSize(9);
			  r.setText(vals[1]);                                				 
		 }		
	}
	
	private  void handleBreakKeys(XWPFRun r,String value){
		 String[] val=value.split(";");                                			 
		 if(val.length==3){
			r.setFontSize(8); 
		 }
		 if(val.length==4){
			 r.setFontSize(5);
			 //将1和2合并,3与4合并
			 val[0]=val[0]+";"+val[1];
			 val[1]=val[2]+";"+val[3];
			 val[2]=null;
			 val[3]=null;
		 }		 
		 for(int i=0;i<val.length;i++){
			 if(val[i]==null)continue;
			 if(i==0){
				 r.setText(val[i],0);
			 }else{
				 r.addBreak();
				 r.setText(val[i]);
			 }
		 }
	}
	XWPFRun getOrAddParagraph(XWPFParagraph p, boolean isInsert,boolean isNewLine) {  
        XWPFRun pRun = null;  
        if (isInsert) {  
            pRun = p.createRun();  
        } else {  
            if (p.getRuns() != null && p.getRuns().size() > 0) {  
                pRun = p.getRuns().get(0);  
            } else {  
                pRun = p.createRun();  
            }  
        }  
        if (isNewLine) {  
            pRun.addBreak();  
        }  
        return pRun;  
    }  	
	
	CTRPr getRunCTRPr(XWPFParagraph p, XWPFRun r) {  
        CTRPr pRpr = null;  
        if (r.getCTR() != null) {  
            pRpr = r.getCTR().getRPr();  
            if (pRpr == null) {  
                pRpr = r.getCTR().addNewRPr();  
            }  
        } else {  
            pRpr = p.getCTP().addNewR().addNewRPr();  
        }  
        return pRpr;  
    }  
    
	CTSym getCTSym(String wingType, String charStr) throws XmlException {  
        CTSym sym = CTSym.Factory  
                .parse("<xml-fragment w:font=\""  
                        + wingType  
                        + "\" w:char=\""  
                        + charStr  
                        + "\" xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\"> </xml-fragment>");  
        return sym;  
    }  
}
