package com.cdo.cloud;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.File;
import java.io.FileOutputStream;

/*
needs the full ooxml-schemas-1.4.jar as mentioned in https://poi.apache.org/faq.html#faq-N10025
*/

public class WordInsertFormFields {

 static void insertFormField(XWPFParagraph paragraph, String type, CTString[] options) {
  XWPFRun run = paragraph.createRun();
  run.getCTR().addNewFldChar().setFldCharType(STFldCharType.BEGIN);
  if ("FORMTEXT".equals(type)) {
   run.getCTR().getFldCharArray(0).addNewFfData().addNewTextInput();
  } else if ("FORMDROPDOWN".equals(type)) {
   run.getCTR().getFldCharArray(0).addNewFfData().addNewDdList().setListEntryArray(options);
  } else if ("FORMCHECKBOX".equals(type)) {
   run.getCTR().getFldCharArray(0).addNewFfData().addNewCheckBox();
  }
  run = paragraph.createRun();
  run.getCTR().addNewInstrText().setStringValue(type);
  if ("FORMTEXT".equals(type)) {
   run = paragraph.createRun();
   run.getCTR().addNewFldChar().setFldCharType(STFldCharType.SEPARATE);
   for (int i = 0; i < 5; i++) {
    run = paragraph.createRun();
    //run.setText(" "); // Unicode Character 'EN SPACE' (U+2002)
    run.setText("\u2002");
   }
  }
  run = paragraph.createRun();
  run.getCTR().addNewFldChar().setFldCharType(STFldCharType.END);
 }

 public static void main(String[] args) throws Exception {

  XWPFDocument document = new XWPFDocument();

  XWPFParagraph paragraph = document.createParagraph();
  XWPFRun run = paragraph.createRun();
  run.setText("Input Name: ");
  insertFormField(paragraph, "FORMTEXT", null);

  paragraph = document.createParagraph();
  run = paragraph.createRun();
  run.setText("Choose gender: ");
  CTString male = CTString.Factory.newInstance(); male.setVal("male");
  CTString female = CTString.Factory.newInstance(); female.setVal("female");
  insertFormField(paragraph, "FORMDROPDOWN", new CTString[]{male, female});

  paragraph = document.createParagraph();
  run = paragraph.createRun();
  run.setText("Will you answer mails?: ");
  insertFormField(paragraph, "FORMCHECKBOX", null);

  document.enforceFillingFormsProtection();

  FileOutputStream out = new FileOutputStream(new File("e:/WordInsertFormFields.docx"));
  document.write(out);
  out.close();
 }
}