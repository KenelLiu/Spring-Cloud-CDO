package com.cdo.cloud.word;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class POITableRow {
	XWPFTableRow target; 
	XWPFTableRow source;
	
	POITableRow(XWPFTableRow target, XWPFTableRow source){
		this.target=target;
		this.source=source;
	}

	public void copyTableRow(Integer index) {
		// 复制样式
		if (source.getCtRow() != null) {
			target.getCtRow().setTrPr(source.getCtRow().getTrPr());
		}
		// 复制单元格
		for (int i = 0; i < source.getTableCells().size(); i++) {
			XWPFTableCell cell1 = target.getCell(i);
			XWPFTableCell cell2 = source.getCell(i);
			if (cell1 == null) {
				cell1 = target.addNewTableCell();
			}
			copyTableCell(cell1, cell2, index);
		}
	}	
	
	void copyTableCell(XWPFTableCell target, XWPFTableCell source, Integer index) {
		// 列属性
		if (source.getCTTc() != null) {
			target.getCTTc().setTcPr(source.getCTTc().getTcPr());
		}
		// 删除段落
		for (int pos = 0; pos < target.getParagraphs().size(); pos++) {
			target.removeParagraph(pos);
		}
		// 添加段落
		for (XWPFParagraph sp : source.getParagraphs()) {
			XWPFParagraph targetP = target.addParagraph();
			copyParagraph(targetP, sp, index);
		}
	}	
	
	void copyParagraph(XWPFParagraph target, XWPFParagraph source, Integer index) {

		// 设置段落样式
		target.getCTP().setPPr(source.getCTP().getPPr());

		// 移除所有的run
		for (int pos = target.getRuns().size() - 1; pos >= 0; pos--) {
			target.removeRun(pos);
		}
		// copy 新的run
		for (XWPFRun s : source.getRuns()) {
			XWPFRun targetrun = target.createRun();
			copyRun(targetrun, s, index);
		}
	}	
	
	void copyRun(XWPFRun target, XWPFRun source, Integer index) {
		// 设置run属性
		target.getCTR().setRPr(source.getCTR().getRPr());
		// 设置文本
		//String tail = "";
		//if (index != null) {
		//	tail = index.toString();
		//}
		target.setText(source.text());
	}	
}
