package com.cdo.cloud;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.List;

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

public class WordDyTable {

	public static void main(String[] args) {
		String srcPath = "D:/file/activiti/templateWord/template_netManage_policy-dyTable.docx";
        String targetPath = "D:/file/activiti/templateWord/dyTable.docx";
        String key = "tbl";// 在文档中需要替换插入表格的位置
        XWPFDocument doc = null;        
        OutputStream out=null;
        try {
        	out=new FileOutputStream(targetPath);
            doc = new XWPFDocument(POIXMLDocument.openPackage(srcPath));
            List<XWPFParagraph> paragraphList = doc.getParagraphs();
            if (paragraphList != null && paragraphList.size() > 0) {
                for (XWPFParagraph paragraph : paragraphList) {
                    List<XWPFRun> runs = paragraph.getRuns();
                    for (int i = 0; i < runs.size(); i++) {
                        String text = runs.get(i).getText(0).trim();
                        System.out.println("text="+text);
                        if (text != null) {
                            if (text.indexOf(key) >= 0) {
                                runs.get(i).setText(text.replace(key, ""), 0);
                                XmlCursor cursor = paragraph.getCTP().newCursor();
                                // 在指定游标位置插入表格
                                XWPFTable table = doc.insertNewTbl(cursor);

                                CTTblPr tablePr = table.getCTTbl().getTblPr();
                                CTTblWidth width = tablePr.addNewTblW();
                                width.setW(BigInteger.valueOf(9500));                                
                                inserInfo(table);                                
                                break;
                            }
                        }
                    }
                }
            }
      
            doc.write(out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

	}
	/**
     * 把信息插入表格
     * @param table
     * @param data
     */
    private static void inserInfo(XWPFTable table) {
        //List<DTO> data = mapper.getInfo();//需要插入的数据
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = null;
        for (int col = 1; col < 9; col++) {//默认会创建一列，即从第2列开始
            // 第一行创建了多少列，后续增加的行自动增加列
            CTTcPr cPr =row.createCell().getCTTc().addNewTcPr();
            CTTblWidth width = cPr.addNewTcW();
            switch(col){
            	case 1:
            	case 2:
            		width.setW(BigInteger.valueOf(1200));
            		break;
            	case 3:
            	case 4:
            	case 5:	
            		width.setW(BigInteger.valueOf(1500));
            		break;            	
            	case 6:
            	case 7:	
            	case 8:	
            		width.setW(BigInteger.valueOf(1000));
            		break;
            }            	
        }
        //row.getCell(0).setText("序号");
        XWPFRun run = row.getCell(0).addParagraph().createRun();
        run.setFontFamily("微软雅黑 Light");
        run.setBold(true);
        run.setFontSize(10);        
        run.setText("序号");
       
        row.getCell(1).setText("应用名称");
        row.getCell(2).setText("源地址IP");
        row.getCell(3).setText("目的地址域名");
        row.getCell(4).setText("目的地址IP");
        row.getCell(5).setText("目的端口号");
        row.getCell(6).setText("协议");
        row.getCell(7).setText("访问用途");
        row.getCell(8).setText("备注");
        
       
        /**
        for(DTO item : data){
            row = table.createRow();
            row.getCell(0).setText(item.getZbmc());
            cell = row.getCell(1);
            cell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2000));
            cell.setText(item.getZbsm());
            cell = row.getCell(2);
            cell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2000));
            cell.setText(item.getJsgs());
            if(item.getCkz()!=null&&!item.getCkz().contains("$")){
                row.getCell(3).setText(item.getCkz());
            }
            cell = row.getCell(4);
            cell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2000));
            cell.setText(item.getSm());
            row.getCell(5).setText(item.getJsjg()==null?"无法计算":item.getJsjg());
        }
        **/
    }
}
