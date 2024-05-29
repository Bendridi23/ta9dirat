package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataFormatter;


import java.io.*;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DialogController implements Initializable  {
    private int numSheetF1 = 0,numSheetF2 = 0;
    private double xOffset=0;
    private double yOffset=0;


    public String getFirst_file_path() {
        return first_file_path;
    }

    public void setFirst_file_path(String first_file_path) {
        this.first_file_path = first_file_path;
    }

    public String getSecond_file_path() {
        return second_file_path;
    }

    public void setSecond_file_path(String second_file_path) {
        this.second_file_path = second_file_path;
    }

    private String first_file_path,second_file_path;
    @FXML
    private Text nameFirstFile;
    @FXML
    private Text nameSecondFile;
    @FXML
    private ProgressBar progress;
    @FXML
    private Button btnCopy;
    @FXML
    private Text txtProg;
    @FXML
    private Button btnclose,btnFirstFile,btnSecondFile;
    @FXML
    private VBox vbox2,vbox1;





    public void myImage(Button btn,String urlImage,boolean cacheImg,double Height,double Width){
         Image img=new Image(urlImage);
         ImageView imageView=new ImageView(img);
         if(cacheImg==false){
             imageView.imageProperty().set(null);
         }else{

        imageView.setFitHeight(Height);
        imageView.setFitWidth(Width);
        btn.setGraphic(imageView);}
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        progress.setVisible(false);
        myImage(btnFirstFile,"/sample/excel1.png",true,80,80);
        myImage(btnSecondFile,"/sample/excel1.png",true,80,80);





    }

    @FXML
    public void onUploadFirstFile(ActionEvent ev){



        //vbox1.setStyle("-fx-background-color: #02723b;-fx-background-radius:3;");
        //myImage(btnFirstFile,"/sample/excel2.png",true,80,80);
        setFirst_file_path(FilePath(nameFirstFile,btnFirstFile,vbox1));

    }
    @FXML
    public void onUploadSecondFile(ActionEvent ev){


        setSecond_file_path(FilePath(nameSecondFile,btnSecondFile,vbox2));

            //btnSecondFile.setStyle("-fx-background-image: url(\"excel2.png\"); -fx-background-image-color: #00000000;");

    }
    @FXML
    public void onMouseEntredFile2(MouseEvent e){

        myImage(btnSecondFile,"/sample/arrow.png",true,80,80);

    }
    @FXML
    public void onMouseExitedFile2(MouseEvent e){
        if(nameSecondFile.getText().equals("")){
            myImage(btnSecondFile,"/sample/excel1.png",true,80,80);
        }else{
            myImage(btnSecondFile,"/sample/excel2.png",true,80,80);
        }



    }
    @FXML
    public void onMouseEntredFile1(MouseEvent e){

        myImage(btnFirstFile,"/sample/arrow.png",true,80,80);

    }
    @FXML
    public void onMouseExitedFile1(MouseEvent e){
        if(nameFirstFile.getText().equals("")){
            myImage(btnFirstFile,"/sample/excel1.png",true,80,80);
        }else{
            myImage(btnFirstFile,"/sample/excel2.png",true,80,80);
        }



    }
    public String FilePath(Text tf,Button bt,VBox vb) {
        String path = null;
        try{
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter fileExtension = new FileChooser.ExtensionFilter("Classeur Excel", "*.xls", "*.ods", "*.xlsx");
            fileChooser.getExtensionFilters().add(fileExtension);
            File f = fileChooser.showOpenDialog(null);

            path = f.getAbsolutePath();

            String nameFile = f.getName();

            tf.setText(nameFile);
            vb.setStyle("-fx-background-color: #02723b;-fx-background-radius:3;");
            myImage(bt,"/sample/excel2.png",true,80,80);
            return path;
        }catch (Exception e)
        {
            tf.setText(null);
            vb.setStyle(null);
            myImage(bt, "/sample/excel1.png", true, 80, 80);
        }

       return path;
    }
    @FXML
    public void onCopyNotes(ActionEvent ev) throws IOException {
        if(getSecond_file_path()==null || getFirst_file_path()==null){
            showDialog("","قم برفع أحد ملفات الحجز","Black");
        }else {

            btnCopy.setDisable(true);
            progress.setVisible(true);
            txtProg.setText("يرجى انتضار نسخ النقاط ...");
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        CopyNotes();

                    } catch (IOException ex) {
                        System.out.println("XX" + ex);
                    }

                }
            });

            t.start();

        }


    }
    public void CopyNotes() throws IOException {
        FileInputStream fsIPF1 = new FileInputStream(new File(getFirst_file_path()));
        FileInputStream fsIPF2 = new FileInputStream(new File(getSecond_file_path()));
        HSSFWorkbook workbookF1 = new HSSFWorkbook(fsIPF1);
        HSSFWorkbook workbookF2 = new HSSFWorkbook(fsIPF2);
        numSheetF1 = workbookF1.getNumberOfSheets() - 1;
        numSheetF2 = workbookF2.getNumberOfSheets() - 1;
        System.out.println("Workbook File One: " + workbookF1.getNumberOfSheets() + " Sheets : ");
        System.out.println("Workbook File Tow: " + workbookF2.getNumberOfSheets() + " Sheets : ");

        double numProg = 1.f / (workbookF2.getNumberOfSheets() - 1);
        double no,s = 0;
        double fin_no = 0.f;

        for (int listF1 = 0; listF1 < workbookF1.getNumberOfSheets() - 1; listF1++) {

            for (int listF2 = 0; listF2 < workbookF2.getNumberOfSheets() - 1; listF2++) {
                HSSFSheet sheetF1 = workbookF1.getSheetAt(listF1);
                int rowCountF1 = sheetF1.getLastRowNum() + 1;
                HSSFSheet sheetF2 = workbookF2.getSheetAt(listF2);
                int rowCountF2 = sheetF2.getLastRowNum() + 1;
                no = (numProg / (rowCountF2 - 8));
                for (int iF1 = 8; iF1 < rowCountF1; iF1++) {

                    for (int iF2 = 8; iF2 < rowCountF2; iF2++) {
                        String devoir2F1 = sheetF1.getRow(7).getCell(7).getStringCellValue();
                        String devoir2F2 = sheetF2.getRow(7).getCell(7).getStringCellValue();

                        String numIdF1=sheetF1.getRow(iF1).getCell(0).toString();
                        String dateNaissF1 = sheetF1.getRow(iF1).getCell(3).toString();

                        String numIdF2=sheetF2.getRow(iF2).getCell(0).toString();
                        String dateNaissF2 = sheetF2.getRow(iF2).getCell(3).toString();

                        String EvalF1=sheetF1.getRow(iF1).getCell(4).toString();
                        String DevoirF1 = sheetF1.getRow(iF1).getCell(5).toString();
                        String ExamF1 = sheetF1.getRow(iF1).getCell(6).toString();
                        String examMod_tow_devor=sheetF1.getRow(iF1).getCell(7).toString();
                        try{
                            double d_EvalF1=Double.parseDouble(EvalF1);
                            if(numIdF1.equals(numIdF2)&&dateNaissF1.equals(dateNaissF2)){
                                writeNotesSecondFile(iF2,listF2,d_EvalF1 ,4);
                                s = fin_no + (no * (iF2 - 7));
                                progress.setProgress(s);
                            }
                        }catch (NumberFormatException e) {
                            if(numIdF1.equals(numIdF2)&&dateNaissF1.equals(dateNaissF2)){
                                writeErrorNote(iF2,listF2,EvalF1,4);
                                s = fin_no + (no * (iF2 - 7));
                                progress.setProgress(s);
                            }
                        }
                        try{
                            double d_DevoirF1=Double.parseDouble(DevoirF1);
                            if(numIdF1.equals(numIdF2)&&dateNaissF1.equals(dateNaissF2)){
                                writeNotesSecondFile(iF2,listF2,d_DevoirF1 ,5);
                                s = fin_no + (no * (iF2 - 7));
                                progress.setProgress(s);
                            }
                        }catch (NumberFormatException e) {
                            //String s_EvalF1=String.valueOf(DevoirF1);
                            if(numIdF1.equals(numIdF2)&&dateNaissF1.equals(dateNaissF2)){
                                writeErrorNote(iF2,listF2,DevoirF1,5);
                                s = fin_no + (no * (iF2 - 7));
                                progress.setProgress(s);
                            }
                        }
                        try{
                            double d_ExamF1=Double.parseDouble(ExamF1);
                            if(numIdF1.equals(numIdF2)&&dateNaissF1.equals(dateNaissF2)){
                                writeNotesSecondFile(iF2,listF2,d_ExamF1 ,6);
                                s = fin_no + (no * (iF2 - 7));
                                progress.setProgress(s);
                            }
                        }catch (NumberFormatException e) {
                            //String s_ExamF1=String.valueOf(ExamF1);
                            if(numIdF1.equals(numIdF2)&&dateNaissF1.equals(dateNaissF2)){
                                writeErrorNote(iF2,listF2,ExamF1,6);
                                s = fin_no + (no * (iF2 - 7));
                                progress.setProgress(s);
                            }
                        }
                        if(!devoir2F1.equals("التقديرات") && !devoir2F2.equals("التقديرات")){
                            try{
                                double d_examModTowDev=Double.parseDouble(examMod_tow_devor);
                                if(numIdF1.equals(numIdF2)&&dateNaissF1.equals(dateNaissF2)){
                                    writeNotesSecondFile(iF2,listF2,d_examModTowDev ,7);
                                    s = fin_no + (no * (iF2 - 7));
                                    progress.setProgress(s);
                                }
                            }catch (NumberFormatException e) {
                                //String s_ExamF1=String.valueOf(ExamF1);
                                if(numIdF1.equals(numIdF2)&&dateNaissF1.equals(dateNaissF2)){
                                    writeErrorNote(iF2,listF2,examMod_tow_devor,7);
                                    s = fin_no + (no * (iF2 - 7));
                                    progress.setProgress(s);
                                }
                            }

                        }
                       /* s = fin_no + (no * (iF2 - 7));
                        System.out.println((iF2 - 7)+")--------------->" + s);

                        progress.setProgress(s);*/

                    }


                    //System.out.println("------------------------------------------------" );
                }


                fin_no = s;
            }



        }

        txtProg.setText("");
        btnCopy.setDisable(false);
        progress.setVisible(false);





    }
    public void writeNotesSecondFile(int iRow, int jSheet, double note, int cellobs) throws IOException {
        FileInputStream fsIP = new FileInputStream(new File(getSecond_file_path())); //Read the spreadsheet that needs to be updated
        HSSFWorkbook wb = new HSSFWorkbook(fsIP); //Access the workbook
        HSSFSheet worksheet = wb.getSheetAt(jSheet); //Access the worksheet, so that we can update / modify it.
        Cell cell = null; // declare a Cell object
        cell = worksheet.getRow(iRow).getCell(cellobs);   // Access the second cell in second row to update the value
        cell.setCellValue(note);

        cell.setCellValue(note);  // Get current cell value value and overwrite the value

        fsIP.close(); //Close the InputStream
        FileOutputStream fileOut = new FileOutputStream(getSecond_file_path());  //Open FileOutputStream to write updates
        wb.write(fileOut); //write changes
        fileOut.close();  //close the stream
        wb.close();

    }
    public void writeErrorNote(int iRow, int jSheet, String note, int cellobs) throws IOException {
        FileInputStream fsIP = new FileInputStream(new File(getSecond_file_path())); //Read the spreadsheet that needs to be updated
        HSSFWorkbook wb = new HSSFWorkbook(fsIP); //Access the workbook
        HSSFSheet worksheet = wb.getSheetAt(jSheet); //Access the worksheet, so that we can update / modify it.
        Cell cell = null; // declare a Cell object
        cell = worksheet.getRow(iRow).getCell(cellobs);   // Access the second cell in second row to update the value
        cell.setCellValue(note);

        cell.setCellValue(note);  // Get current cell value value and overwrite the value

        fsIP.close(); //Close the InputStream
        FileOutputStream fileOut = new FileOutputStream(getSecond_file_path());  //Open FileOutputStream to write updates
        wb.write(fileOut); //write changes
        fileOut.close();  //close the stream
        wb.close();

    }
    public void showDialog(String titre, String content, String colorTitle) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        Text t = new Text(content);
        Text title = new Text(titre);
        t.setStyle("-fx-font-family: 'Tajawal Medium';");
        title.setStyle("-fx-font-family: 'Tajawal Medium';-fx-fill:" + colorTitle + ";");
        TextFlow tf = new TextFlow();

        tf.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        Stage stage=(Stage)a.getDialogPane().getScene().getWindow();

        stage.getIcons().add(new Image("/sample/logo.png"));

        // tf.setId("mytext1");
        tf.getChildren().addAll(title, t);
        a.setTitle("تنبيه");

        a.setHeaderText(null);

        a.getDialogPane().setContent(tf);
        // a.setContentText("قم برفع ملف الحجز اولا حتى تتمكن من تطبيق الملاحظات");
        a.showAndWait();
    }
    @FXML
    public void onCloseDialog(ActionEvent ev){
        if(txtProg.getText().equals("")){
            Stage stage=(Stage)((Node)ev.getSource()).getScene().getWindow();
            stage.close();
        }else{
            showDialog("","لا يمكنك الخروج حتى انتهاء عملية نسخ النقاط"+"\n" ,"black");
        }


    }



}
