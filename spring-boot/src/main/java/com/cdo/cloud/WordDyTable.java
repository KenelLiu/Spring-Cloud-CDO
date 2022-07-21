package com.cdo.cloud;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;

import com.cdo.cloud.word.POIWord;
import com.cdoframework.cdolib.data.cdo.CDO;

public class WordDyTable {

	public static void main(String[] args) {
		String srcPath = "D:/file/activiti/templateWord/template_netManage_policy-dyTable.docx";
        String targetPath = "D:/file/activiti/templateWord/dyTable.docx";
        try {
        	CDO valCDO=new CDO();
        	List<CDO> list=new ArrayList<CDO>();
        	list.add(new CDO().setStringValue("F", "1"));
        	list.add(new CDO().setStringValue("F", "2"));
        	list.add(new CDO().setStringValue("F", "3"));
        	
        	Map<String,List<CDO>> copyRowWordKeys=new HashMap<String,List<CDO>>();
        	copyRowWordKeys.put("F", list);
        	POIWord poiWord=new POIWord(new File(srcPath), new File(targetPath), valCDO);   
        	poiWord.setCopyRowWordKeys(copyRowWordKeys);
        	poiWord.process();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
