/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import org.apache.commons.math3.util.MathArrays;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TableRowAlign;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STOnOff;

/**
 *
 * @author benPC
 */
public class ClassAnalyse {
    private float countMoy_TowDevoir=0;
    private float countMoy_OneDevoir=0;
      public enum TextOrientation {		
      LTR,		
      RTL		
   }
      private  int mOneD=0;
      private  int mTowD=0;
       private  int yesMoy=0;
       private float maxM=0;
       private float minM=20;
       private float MAXMoy;
       private float MINMoy;

    public float getMINMoy() {
        return MINMoy;
    }

    public void setMINMoy(float MINMoy) {
        this.MINMoy = MINMoy;
    }

    public float getMAXMoy() {
        return MAXMoy;
    }

    public void setMAXMoy(float MAXMoy) {
        this.MAXMoy = MAXMoy;
    }

    public int getYesMoy() {
        return yesMoy;
    }

    public void setYesMoy(int yesMoy) {
        this.yesMoy = yesMoy;
    }
      private float moyenne;

    public float getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(float moyenne) {
        this.moyenne = moyenne;
    }
    
    
    public void analyseTableOne(XWPFDocument document,String path,String tete,int numEleve,float MoyClass,int admitMoy) throws IOException{
        // XWPFDocument document=new XWPFDocument();
          
         // FileOutputStream out=new FileOutputStream(new File(path+".docx"));
          XWPFParagraph paragraph=document.createParagraph();
        
          paragraph.setAlignment(ParagraphAlignment.CENTER);
          
          
          setOrientation(paragraph, TextOrientation.RTL); // mettre le paragraphe en RTL
         // paragraph.set
          XWPFRun run=paragraph.createRun();
      
          run.setFontSize(22);
         
          run.setBold(true);
          
          
          run.setText(tete);
          run.setUnderline(UnderlinePatterns.SINGLE);
         
          XWPFTable tableinfo=document.createTable();
          XWPFTableRow tableRow=tableinfo.getRow(0);
          tableinfo.setTableAlignment(TableRowAlign.CENTER);
          tableinfo.setWidth(6000);
         
         
          /////////////////////////
          tableRow.setHeight(20);
          setRun(tableRow.getCell(0).addParagraph(),13, ""+numEleve);
          setRun(tableRow.addNewTableCell().addParagraph(), 13, "تعداد القسم");
          /////////////////////////
          
          XWPFTableRow tableRowThree=tableinfo.createRow();
          DecimalFormat df=new DecimalFormat("#.##");
          df.format(MoyClass);
          setRun(tableRowThree.getCell(0).addParagraph(),13, ""+String.format("%.4g%n",MoyClass));
          setRun(tableRowThree.getCell(1).addParagraph(), 13, "معدل القسم");
          //////////////////////////
          XWPFTableRow tableRow4=tableinfo.createRow();
          setRun(tableRow4.getCell(0).addParagraph(),13, "%"+(admitMoy*100)/numEleve);
          setRun(tableRow4.getCell(1).addParagraph(), 13, "نسبة النجاح");
         
    }
     public void analyseTableTow(XWPFDocument document,int admitMoy,int notMoy,float maxMoy,float minMoy) throws IOException{
        
             XWPFParagraph paragraph=document.createParagraph();
        
          paragraph.setAlignment(ParagraphAlignment.CENTER);
          
          
          setOrientation(paragraph, TextOrientation.RTL); // mettre le paragraphe en RTL
          
          XWPFRun run=paragraph.createRun();
      
          run.setFontSize(20);
          run.setText("\n");
          XWPFTable tableinfo=document.createTable();
          XWPFTableRow tableRow=tableinfo.getRow(0);
          tableinfo.setTableAlignment(TableRowAlign.CENTER);
          tableinfo.setWidth(10000);
         
          /////////////////////////
          
          setRun(tableRow.getCell(0).addParagraph(),13, ""+maxMoy);
          setRun(tableRow.addNewTableCell().addParagraph(), 13,"أحسن معدل   "); 
          setRun(tableRow.addNewTableCell().addParagraph(), 13,""+admitMoy);
          setRun(tableRow.addNewTableCell().addParagraph(), 13,"الحاصلين على المعدل   ");
          /////////////////////////
          XWPFTableRow tableRowThree=tableinfo.createRow();
          setRun(tableRowThree.getCell(0).addParagraph(),13, ""+minMoy);
          setRun(tableRowThree.getCell(1).addParagraph(), 13,"أسوء معدل   ");
          setRun(tableRowThree.getCell(2).addParagraph(),13, ""+notMoy);
          setRun(tableRowThree.getCell(3).addParagraph(), 13,"غير الحاصلين على المعدل   "); 
         
         
    }
    
     private static void setOrientation(XWPFParagraph par, TextOrientation orientation) {
      if ( par.getCTP().getPPr()==null ) {
          par.getCTP().addNewPPr();
      }
      if ( par.getCTP().getPPr().getBidi()==null ) {
         par.getCTP().getPPr().addNewBidi();
      }
      par.getCTP().getPPr().getBidi().setVal(orientation==TextOrientation.RTL?STOnOff.ON:STOnOff.OFF);
   }
    
      public void ReadFile2Analyse(String filePath, ProgressBar progress, Button btn, Button btnNote,Button btnerr, Text tx) throws  IOException {
       //   progressb.setVisible(true);

          btn.setDisable(true);
          btnerr.setDisable(true);
           FileInputStream fsIP= new FileInputStream(new File(filePath));
           FileOutputStream out = null;
            
            XWPFDocument document=new XWPFDocument();
         //Workbook workbook = WorkbookFactory.create(new File(label.getText()));
         HSSFWorkbook workbook = new HSSFWorkbook(fsIP);
          double numProg=1.f/(workbook.getNumberOfSheets()-1);
          double no,s = 0.f;
          double fin_no = 0.f;
         
        for(int listC=0;listC<workbook.getNumberOfSheets()-1 ;listC++){
            
            HSSFSheet sheet = workbook.getSheetAt(listC);
           
            DataFormatter dataFormatter = new DataFormatter();
            int rowCount = sheet.getLastRowNum()+1;
          
              String SDevoir=sheet.getRow(7).getCell(7).getStringCellValue().toString();
          
            System.out.println("dev:"+SDevoir);
          
            System.out.println("num Ligne Liste:"+rowCount);
            Row row_info = sheet.getRow(4);
                
          
            String info_class=new String(row_info.getCell(0).toString());
               
                int Num_SemTest1=info_class.indexOf("الفصل");
                int Num_SemTest2=info_class.length();
                String Num_Sem=info_class.substring(Num_SemTest1,Num_SemTest2);
                String tete="تحليل نتائج "+Num_Sem;
               // System.out.println("تحليل نتائج"+Num_Sem);
            no=(numProg/(rowCount-8));
                if(SDevoir.equals("التقديرات")){
                    out=new FileOutputStream(new File(filePath+".docx"));
            for (int i = 8; i < rowCount; i++)
            {
                float m=MoyModule_oneDevoir(i, listC, filePath, rowCount-8);
                setMoyenne(m);
                int numM=Admit_MoyModule_oneDevoir(i, listC, filePath);
                setYesMoy(numM);
                float max=Max_MoyModule_oneDevoir(i, listC,filePath);
                setMAXMoy(max);
                float min=Min_MoyModule_oneDevoir(i, listC,filePath);
                setMINMoy(min);
                s=fin_no+(no*(i-7));
                //System.out.println("--------------->"+s);
                progress.setProgress(s);
                                        
            }
            
            analyseTableOne(document,filePath,tete,rowCount-8,getMoyenne(),getYesMoy());
            analyseTableTow(document,getYesMoy(), (rowCount-8)-getYesMoy(),getMAXMoy(),getMINMoy());
            
            countMoy_OneDevoir=0;mOneD=0;maxM=0;minM=20;
                }else{
             for (int i = 8; i < rowCount; i++)
            {
                float m=MoyModule_TowDevoir(i, listC, filePath, rowCount-8);
                setMoyenne(m);
                int numM=Admit_MoyModule_TowDevoir(i, listC, filePath);
                setYesMoy(numM);
                float max=Max_MoyModule_TowDevoir(i, listC,filePath);
                setMAXMoy(max);
                float min=Min_MoyModule_TowDevoir(i, listC,filePath);
                setMINMoy(min);

                s=fin_no+(no*(i-7));
                // System.out.println("--------------->"+s);
                progress.setProgress(s);
            }//out=new FileOutputStream(new File(filePath+".docx"));
            analyseTableOne(document,filePath,tete,rowCount-8,getMoyenne(),getYesMoy());
            analyseTableTow(document,getYesMoy(), (rowCount-8)-getYesMoy(),getMAXMoy(),getMINMoy());
            
            countMoy_TowDevoir=0;mTowD=0;maxM=0;minM=20;
                }
            fin_no=s;
                int clas=listC+1;
            tx.setText("جاري تحليل النتائج ...  " + clas);
        }
        document.write(out);
          out.close();
         // progressb.setVisible(false);
          btnerr.setDisable(false);
          btn.setDisable(false);
          btnNote.setDisable(false);
          progress.setVisible(false);
          tx.setText("تم حفظ ملف تحليل النتائج في نفس مكان ملف الحجز بنفس الاسم");

         // remLab.setVisible(true);
        
      }       
         
       
      
      
      public float MoyModule_oneDevoir(int i_row,int j_sheet,String path,int numEleve) throws IOException
      {  
          
        FileInputStream fsIP= new FileInputStream(new File(path));
        HSSFWorkbook workbook = new HSSFWorkbook(fsIP);
        // Get Sheet at index 0
        HSSFSheet sheet = workbook.getSheetAt(j_sheet);
        float Moy=0;
       // float somMoy=0;
        // Get Row at index 1
        HSSFRow row = sheet.getRow(i_row);
        try{float eval=Float.parseFloat(sheet.getRow(i_row).getCell(4).toString());
            float devoir=Float.parseFloat(sheet.getRow(i_row).getCell(5).toString());
            float exam=Float.parseFloat(sheet.getRow(i_row).getCell(6).toString());
            Moy=((eval+devoir)+exam*3)/5;
            countMoy_OneDevoir=countMoy_OneDevoir+Moy;
            System.out.println("Somme:"+countMoy_OneDevoir); }catch (NumberFormatException e){
            if(sheet.getRow(i_row).getCell(4).toString()==""||sheet.getRow(i_row).getCell(5).toString()==""||sheet.getRow(i_row).getCell(6).toString()==""){


                // System.out.println("Complete 00:"+sheet.getRow(i_row).getCell(2).toString()+" "+sheet.getRow(i_row).getCell(1).toString());
                return Moy;
        }

        }
   return countMoy_OneDevoir/numEleve;
}
       public float MoyModule_TowDevoir(int i_row,int j_sheet,String path,int numEleve) throws IOException
      {  
          
        FileInputStream fsIP= new FileInputStream(new File(path));
        HSSFWorkbook workbook = new HSSFWorkbook(fsIP);
        // Get Sheet at index 0
        HSSFSheet sheet = workbook.getSheetAt(j_sheet);
        float Moy=0;
       // float somMoy=0;
        // Get Row at index 1
        HSSFRow row = sheet.getRow(i_row);
          try{float eval=Float.parseFloat(sheet.getRow(i_row).getCell(4).toString());
              float devoir01=Float.parseFloat(sheet.getRow(i_row).getCell(5).toString());
              float devoir02=Float.parseFloat(sheet.getRow(i_row).getCell(6).toString());
              float exam=Float.parseFloat(sheet.getRow(i_row).getCell(7).toString());
              Moy=(((eval+devoir01+devoir02)/3)*2+exam*3)/5;
              countMoy_TowDevoir=countMoy_TowDevoir+Moy;
              //System.out.println("Somme:"+countMoy_TowDevoir);


          }catch (NumberFormatException e){
              if(sheet.getRow(i_row).getCell(4).toString()==""||sheet.getRow(i_row).getCell(5).toString()==""||sheet.getRow(i_row).getCell(6).toString()==""){


                  // System.out.println("Complete 00:"+sheet.getRow(i_row).getCell(2).toString()+" "+sheet.getRow(i_row).getCell(1).toString());
                  return Moy;
              }
          }
       return countMoy_TowDevoir/numEleve; }



      private  static void setRun(XWPFParagraph paragraphtable,int FontSize,String text){
         
         // XWPFParagraph paragraphtable;//=tableRow.createCell().addParagraph();
         XWPFRun run=paragraphtable.createRun();
          paragraphtable.setAlignment(ParagraphAlignment.CENTER);
          setOrientation(paragraphtable, TextOrientation.RTL);
          run.setFontSize(FontSize);
          run.setText(text);
          run.setBold(true);
         // if(addBreak)
      }
      public int Admit_MoyModule_oneDevoir(int i_row,int j_sheet,String path) throws IOException
      {  
          
        FileInputStream fsIP= new FileInputStream(new File(path));
        HSSFWorkbook workbook = new HSSFWorkbook(fsIP);
        // Get Sheet at index 0
        HSSFSheet sheet = workbook.getSheetAt(j_sheet);
        float Moy;
       
       // float somMoy=0;
        // Get Row at index 1
        HSSFRow row = sheet.getRow(i_row);
          try{float eval=Float.parseFloat(sheet.getRow(i_row).getCell(4).toString());
              float devoir=Float.parseFloat(sheet.getRow(i_row).getCell(5).toString());
              float exam=Float.parseFloat(sheet.getRow(i_row).getCell(6).toString());
              Moy=((eval+devoir)+exam*3)/5;

              if(Moy>=10){
                  mOneD=mOneD+1;
              }
              return mOneD ; }catch (NumberFormatException e){
              if(sheet.getRow(i_row).getCell(4).toString()==""||sheet.getRow(i_row).getCell(5).toString()==""||sheet.getRow(i_row).getCell(6).toString()==""){


                  // System.out.println("Complete 00:"+sheet.getRow(i_row).getCell(2).toString()+" "+sheet.getRow(i_row).getCell(1).toString());
                  return mOneD;
          }

        }
            return mOneD;
      }
       public int Admit_MoyModule_TowDevoir(int i_row,int j_sheet,String path) throws IOException
      {  
          
        FileInputStream fsIP= new FileInputStream(new File(path));
        HSSFWorkbook workbook = new HSSFWorkbook(fsIP);
        // Get Sheet at index 0
        HSSFSheet sheet = workbook.getSheetAt(j_sheet);
        float Moy;
       
       // float somMoy=0;
        // Get Row at index 1
        HSSFRow row = sheet.getRow(i_row);
          try{float eval=Float.parseFloat(sheet.getRow(i_row).getCell(4).toString());
              float devoir01=Float.parseFloat(sheet.getRow(i_row).getCell(5).toString());
              float devoir02=Float.parseFloat(sheet.getRow(i_row).getCell(6).toString());
              float exam=Float.parseFloat(sheet.getRow(i_row).getCell(7).toString());
              Moy=(((eval+devoir01+devoir02)/3)*2+exam*3)/5;

              if(Moy>=10){
                  mTowD=mTowD+1;
              }
              return mTowD ;  }catch (NumberFormatException e){if(sheet.getRow(i_row).getCell(4).toString()==""||sheet.getRow(i_row).getCell(5).toString()==""||sheet.getRow(i_row).getCell(6).toString()==""){


              // System.out.println("Complete 00:"+sheet.getRow(i_row).getCell(2).toString()+" "+sheet.getRow(i_row).getCell(1).toString());
              return mTowD;
          }}

          return mTowD ;
      }
       public float Max_MoyModule_oneDevoir(int i_row,int j_sheet,String path) throws IOException
      {  
          
        FileInputStream fsIP= new FileInputStream(new File(path));
        HSSFWorkbook workbook = new HSSFWorkbook(fsIP);
        // Get Sheet at index 0
        HSSFSheet sheet = workbook.getSheetAt(j_sheet);
        float Moy;
        
       
       // float somMoy=0;
        // Get Row at index 1
        HSSFRow row = sheet.getRow(i_row);
          try{float eval=Float.parseFloat(sheet.getRow(i_row).getCell(4).toString());
              float devoir=Float.parseFloat(sheet.getRow(i_row).getCell(5).toString());
              float exam=Float.parseFloat(sheet.getRow(i_row).getCell(6).toString());
              Moy=((eval+devoir)+exam*3)/5;

              if(Moy>maxM){
                  maxM=Moy;
              }
              return maxM ; }catch (NumberFormatException e){
              if(sheet.getRow(i_row).getCell(4).toString()==""||sheet.getRow(i_row).getCell(5).toString()==""||sheet.getRow(i_row).getCell(6).toString()==""){


              // System.out.println("Complete 00:"+sheet.getRow(i_row).getCell(2).toString()+" "+sheet.getRow(i_row).getCell(1).toString());
              return mOneD;
          }}

        return maxM;
      }
       public float Max_MoyModule_TowDevoir(int i_row,int j_sheet,String path) throws IOException
      {  
          
        FileInputStream fsIP= new FileInputStream(new File(path));
        HSSFWorkbook workbook = new HSSFWorkbook(fsIP);
        // Get Sheet at index 0
        HSSFSheet sheet = workbook.getSheetAt(j_sheet);
        float Moy;
        
       
       // float somMoy=0;
        // Get Row at index 1
        HSSFRow row = sheet.getRow(i_row);
          try{float eval=Float.parseFloat(sheet.getRow(i_row).getCell(4).toString());
              float devoir01=Float.parseFloat(sheet.getRow(i_row).getCell(5).toString());
              float devoir02=Float.parseFloat(sheet.getRow(i_row).getCell(6).toString());
              float exam=Float.parseFloat(sheet.getRow(i_row).getCell(7).toString());
              Moy=(((eval+devoir01+devoir02)/3)*2+exam*3)/5;

              if(Moy>maxM){
                  maxM=Moy;
              }
              return maxM ;}catch (NumberFormatException e){if(sheet.getRow(i_row).getCell(4).toString()==""||sheet.getRow(i_row).getCell(5).toString()==""||sheet.getRow(i_row).getCell(6).toString()==""){


              // System.out.println("Complete 00:"+sheet.getRow(i_row).getCell(2).toString()+" "+sheet.getRow(i_row).getCell(1).toString());
              return mOneD;
          }}
        return maxM;
   
      }
       public float Min_MoyModule_oneDevoir(int i_row,int j_sheet,String path) throws IOException
      {  
          
        FileInputStream fsIP= new FileInputStream(new File(path));
        HSSFWorkbook workbook = new HSSFWorkbook(fsIP);
        // Get Sheet at index 0
        HSSFSheet sheet = workbook.getSheetAt(j_sheet);
        float Moy;
        
       
       // float somMoy=0;
        // Get Row at index 1
        HSSFRow row = sheet.getRow(i_row);
          try{float eval=Float.parseFloat(sheet.getRow(i_row).getCell(4).toString());
              float devoir=Float.parseFloat(sheet.getRow(i_row).getCell(5).toString());
              float exam=Float.parseFloat(sheet.getRow(i_row).getCell(6).toString());
              Moy=((eval+devoir)+exam*3)/5;

              if(Moy<minM){
                  minM=Moy;
              }
              return minM ;  }catch (NumberFormatException e){if(sheet.getRow(i_row).getCell(4).toString()==""||sheet.getRow(i_row).getCell(5).toString()==""||sheet.getRow(i_row).getCell(6).toString()==""){


              // System.out.println("Complete 00:"+sheet.getRow(i_row).getCell(2).toString()+" "+sheet.getRow(i_row).getCell(1).toString());
              return mOneD;
          }}
        return minM;
   
      }
       
        public float Min_MoyModule_TowDevoir(int i_row,int j_sheet,String path) throws IOException
      {  
          
        FileInputStream fsIP= new FileInputStream(new File(path));
        HSSFWorkbook workbook = new HSSFWorkbook(fsIP);
        // Get Sheet at index 0
        HSSFSheet sheet = workbook.getSheetAt(j_sheet);
        float Moy;
        
       
       // float somMoy=0;
        // Get Row at index 1
        HSSFRow row = sheet.getRow(i_row);
          try{float eval=Float.parseFloat(sheet.getRow(i_row).getCell(4).toString());
              float devoir01=Float.parseFloat(sheet.getRow(i_row).getCell(5).toString());
              float devoir02=Float.parseFloat(sheet.getRow(i_row).getCell(6).toString());
              float exam=Float.parseFloat(sheet.getRow(i_row).getCell(7).toString());
              Moy=(((eval+devoir01+devoir02)/3)*2+exam*3)/5;

              if(Moy<minM){
                  minM=Moy;
              }
              return minM ; }catch (NumberFormatException e){ if(sheet.getRow(i_row).getCell(4).toString()==""||sheet.getRow(i_row).getCell(5).toString()==""||sheet.getRow(i_row).getCell(6).toString()==""){


              // System.out.println("Complete 00:"+sheet.getRow(i_row).getCell(2).toString()+" "+sheet.getRow(i_row).getCell(1).toString());
              return mTowD;
          }}
        return minM;
   
      }
           
 
     
          
      
    
    
}
