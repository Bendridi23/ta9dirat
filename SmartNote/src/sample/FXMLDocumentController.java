/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import java.io.File;
import java.io.FileInputStream;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import org.apache.poi.hssf.usermodel.HSSFRow;

import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.ss.usermodel.DataFormatter;

/**
 * @author benPC
 */
public class FXMLDocumentController implements Initializable {
    public String getError() {
        return S_error;
    }

    public void setError(String s_error) {
        S_error = s_error;
    }

    private String se = "";
    private String sed2 = "";

    public String getNoError() {
        return noError;
    }

    public void setNoError(String noError) {
        this.noError = noError;
    }

    private String noError;

    private String S_error;

    public String getS_errorD2() {
        return S_errorD2;
    }

    public void setS_errorD2(String s_errorD2) {
        S_errorD2 = s_errorD2;
    }

    private String S_errorD2;
    @FXML
    private FontAwesomeIconView remLab;
    @FXML
    private TextField lab1, lab2, lab3, lab4, lab5, lab6, lab7, lab8, src_excel;
    //@FXML
    // private JFXSpinner progressb;
    @FXML
    private Button button;
    @FXML
    private TextArea TextObs;
    @FXML
    private Button analyseButt;
    @FXML
    private Button btnError;
    @FXML
    private Text txt;
    @FXML
    private ProgressBar progress;
    @FXML
    private Text remText;
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private VBox vboxVide;


    private int numSheet = 0;
    static double ss = 0;
    private int n = 0;
    private String s = "أكمل نقاط التلاميذ التالية:";
    private String s1 = "";

    @FXML
    private void handleButtonAction(ActionEvent event) {
        FILE_XLS_PATH();


    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        Tooltip tooltip = new Tooltip("خاص بمادة التربية البدنية أو اللغة الأمازيغية للذين يملكون إعفاء");
        tooltip.setStyle("-fx-font-family: 'Tajawal Medium';");
        //setCloseDialog("ON");


        lab8.setTooltip(tooltip);


    }

    public String FILE_XLS_PATH() {
        String path = null;
        try {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter fileExtension = new FileChooser.ExtensionFilter("Classeur Excel", "*.xls", "*.ods", "*.xlsx");
            fileChooser.getExtensionFilters().add(fileExtension);
            File f = fileChooser.showOpenDialog(null);

            path = f.getAbsolutePath();

            src_excel.setText(path);
            btnError.setDisable(true);
            return path;
        } catch (Exception e) {
            src_excel.setText("");
            btnError.setDisable(false);
        }


        return path;
    }

    public void create_Observation_in_File() throws IOException {

        button.setDisable(true);
        btnError.setDisable(true);
        //  progressb.setVisible(true);

        // FileOutputStream fileOut = null;
        FileInputStream fsIP = new FileInputStream(new File(src_excel.getText()));
        //Workbook workbook = WorkbookFactory.create(new File(label.getText()));
        HSSFWorkbook workbook = new HSSFWorkbook(fsIP);
        numSheet = workbook.getNumberOfSheets() - 1;
        System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");
        double numProg = 1.f / (workbook.getNumberOfSheets() - 1);
        double no, s = 0.f;
        double fin_no = 0.f;
        // float d=16/100.0f;
        // System.out.println("Retrieving Sheets using for-each loop"+workbook.getNumberOfSheets()+"RMm:"+numProg+"RMm>:"+d);

        //  progress.setProgress(0.5);

        for (int listC = 0; listC < workbook.getNumberOfSheets() - 1; listC++) {

            HSSFSheet sheet = workbook.getSheetAt(listC);
            // Create a DataFormatter to format and get each cell's value as String
            DataFormatter dataFormatter = new DataFormatter();
            int rowCount = sheet.getLastRowNum() + 1;
            //  String SDevoir[]=new String[]{sheet.getRow(7).getCell(7).getStringCellValue().toString(),sheet.getRow(7).getCell(8).getStringCellValue().toString()};
            String SDevoir = sheet.getRow(7).getCell(7).getStringCellValue();

            //  String SDevoir2=sheet.getRow(7).getCell(8).getStringCellValue().toString();
            // System.out.println("obs:"+S0);
            System.out.println("dev:" + SDevoir);
            // System.out.println("dev1:"+SDevoir[1]);
            System.out.println("num Ligne Liste:" + (rowCount - 8));
            //for(int ii=0;ii<=2;ii++){
            System.out.println("******************" + SDevoir);
            no = (numProg / (rowCount - 8));
            if (SDevoir.equals("التقديرات")) {
                for (int i = 8; i < rowCount; i++) {
                    float moy = MoyModule_oneDevoir(i, listC);
                    if (moy == 0) {
                        String_Observation(i, listC, "", 7);

                    } else {
                        String obs = observation(moy);

                        String_Observation(i, listC, obs, 7);
                    }

                    s = fin_no + (no * (i - 7));
                    System.out.println((i-7)+"--------------->" + s);
                    progress.setProgress(s);


                }

                System.out.println("=========>" + fin_no);

            } else {
                for (int i = 8; i < rowCount; i++) {
                    float moy = MoyModule_TowDevoir(i, listC);
                    if (moy == 0) {
                        String_Observation(i, listC, "", 8);
                    } else {
                        String obs = observation(moy);
                        // progressb.setVisible(true);
                        String_Observation(i, listC, obs, 8);
                    }
                    s = fin_no + (no * (i - 7));
                    // System.out.println("--------------->"+s);
                    progress.setProgress(s);

                }


            }
            fin_no = s;
            // no=no+numProg;
            // progress.setProgress((listC+1)*numProg);
            //numProg=(listC+1)*numProg;
            int clas = (listC + 1);

            remText.setText("جاري تطبيق الملاحظات ...  " + clas);

        }//comboDevoir.setValue("عدد فروض المادة");
        // progressb.setVisible(false);
        //   remLab.setVisible(true);
        remText.setText("تم الأنتهاء من تطبيق الملاحظات على جميع الأقسام بنجاح");
        button.setDisable(false);
        btnError.setDisable(false);
        progress.setVisible(false);
        //analyseButt.setVisible(true);
    }

    @FXML
    private void observat(ActionEvent event) throws IOException {
        setError(null);
        setS_errorD2(null);
        setNoError(null);
        //  Font f=new Font(Font.)
        if (src_excel.getText().equals("")) {
            showDialog("ملف الحجز غير جاهز", "", "\n قم برفع ملف الحجز اولا حتى تتمكن من تطبيق الملاحظات", "black");
        } else {
            analyseButt.setDisable(true);
            progress.setVisible(true);
            s1 = "";
            n = 0;

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        create_Observation_in_File();
                        analyseButt.setDisable(false);
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("XXXXXXXXXXXXXXXXXXXXxxx" + ex);
                    }

                }
            });

            t.start();


            //  }
        }

    }

    @FXML
    private void close(ActionEvent event) throws Throwable {

        System.exit(0);
        // finalize();//
    }


    public float MoyModule_oneDevoir(int i_row, int j_sheet) throws IOException {

        FileInputStream fsIP = new FileInputStream(new File(src_excel.getText()));
        HSSFWorkbook workbook = new HSSFWorkbook(fsIP);
        // Get Sheet at index 0
        HSSFSheet sheet = workbook.getSheetAt(j_sheet);
        float Moy = 0;
        String S_evel, S_devoir, S_exam, name, prenom;
        name = sheet.getRow(i_row).getCell(1).toString();
        prenom = sheet.getRow(i_row).getCell(2).toString();
        S_evel = sheet.getRow(i_row).getCell(4).toString();
        S_devoir = sheet.getRow(i_row).getCell(5).toString();
        S_exam = sheet.getRow(i_row).getCell(6).toString();
        getErrorOneDev(j_sheet + 1, i_row + 1, name, prenom, S_evel, S_devoir, S_exam);
        // Get Row at index 1
        HSSFRow row = sheet.getRow(i_row);
        try {
            // getErrorOneDev(j_sheet + 1, i_row + 1, name, prenom, S_evel, S_devoir, S_exam);
            float eval = Float.parseFloat(S_evel);
            float devoir = Float.parseFloat(S_devoir);
            float exam = Float.parseFloat(S_exam);

            Moy = ((eval + devoir) + exam * 3) / 5;
            System.out.println(">>>" + Moy);
            return Moy;
        } catch (NumberFormatException e) {


            //getErrorOneDev(j_sheet + 1, i_row + 1, name, prenom, S_evel, S_devoir, S_exam);

            if (S_evel.equals("معفى") || S_devoir.equals("معفى") || S_exam.equals("معفى")) {
                System.out.println("Complete 00:");
                String_Observation(i_row, j_sheet, lab8.getText(), 4);
                String_Observation(i_row, j_sheet, lab8.getText(), 5);
                String_Observation(i_row, j_sheet, lab8.getText(), 6);
                Moy = -1;
                return Moy;
            } else {
                if (S_evel == "" && S_devoir == "" && S_exam == "") {
                    String_Observation(i_row, j_sheet, lab8.getText(), 4);
                    String_Observation(i_row, j_sheet, lab8.getText(), 5);
                    String_Observation(i_row, j_sheet, lab8.getText(), 6);
                    Moy = -1;
                    return Moy;

                } else {
                    if (S_evel == "" || S_devoir == "" || S_exam == "") {
                        // if(S_evel==""){}else{if(S_devoir==""){getErrorOneDev(name,prenom,i_row,S_devoir);}else{getErrorOneDev(name,prenom,i_row,S_exam);}}
                        n = n + 1;
                        s1 = s1 + "\n" + "  " + n + ")" + sheet.getRow(i_row).getCell(1).toString() + " " + sheet.getRow(i_row).getCell(2).toString();
                        //lab1.getText().toString()

//                    TextObs.setText(s+" "+s1);
                        Moy = -2;
                        return Moy;
                    }
                }
            }

        }
        return Moy;
    }

    public float MoyModule_TowDevoir(int i_row, int j_sheet) throws IOException {

        FileInputStream fsIP = new FileInputStream(new File(src_excel.getText()));
        HSSFWorkbook workbook = new HSSFWorkbook(fsIP);
        // Get Sheet at index 0
        HSSFSheet sheet = workbook.getSheetAt(j_sheet);
        float Moy = 0;
        String S_evel, S_devoir01, S_devoir02, S_exam, name, prenom;
        name = sheet.getRow(i_row).getCell(1).toString();
        prenom = sheet.getRow(i_row).getCell(2).toString();
        S_evel = sheet.getRow(i_row).getCell(4).toString();
        S_devoir01 = sheet.getRow(i_row).getCell(5).toString();
        S_devoir02 = sheet.getRow(i_row).getCell(6).toString();
        S_exam = sheet.getRow(i_row).getCell(7).toString();
        getErrorTowDev(j_sheet + 1, i_row + 1, name, prenom, S_evel, S_devoir01, S_devoir02, S_exam);
        HSSFRow row = sheet.getRow(i_row);
        try {
            float eval = Float.parseFloat(sheet.getRow(i_row).getCell(4).toString());
            float devoir01 = Float.parseFloat(sheet.getRow(i_row).getCell(5).toString());
            float devoir02 = Float.parseFloat(sheet.getRow(i_row).getCell(6).toString());
            float exam = Float.parseFloat(sheet.getRow(i_row).getCell(7).toString());
            Moy = (((eval + devoir01 + devoir02) / 3) * 2 + exam * 3) / 5;
            System.out.println(">>>" + Moy);
            return Moy;


        } catch (NumberFormatException e) {

            if (S_evel.equals("معفى") || S_devoir01.equals("معفى") || S_devoir02.equals("معفى") || S_exam.equals("معفى")) {
                System.out.println("Complete 00:");
                String_Observation(i_row, j_sheet, lab8.getText(), 4);
                String_Observation(i_row, j_sheet, lab8.getText(), 5);
                String_Observation(i_row, j_sheet, lab8.getText(), 6);
                String_Observation(i_row, j_sheet, lab8.getText(), 7);
                Moy = -1;
                return Moy;
            } else {
                if (S_evel == "" && S_devoir01 == "" && S_devoir02 == "" && S_exam == "") {
                    String_Observation(i_row, j_sheet, lab8.getText(), 4);
                    String_Observation(i_row, j_sheet, lab8.getText(), 5);
                    String_Observation(i_row, j_sheet, lab8.getText(), 6);
                    String_Observation(i_row, j_sheet, lab8.getText(), 7);
                    Moy = -1;
                    return Moy;

                } else {
                    if (S_evel == "" || S_devoir01 == "" || S_devoir02 == "" || S_exam == "") {
                        // if(S_evel==""){}else{if(S_devoir==""){}else{}}
                        n = n + 1;
                        s1 = s1 + "\n" + "  " + n + ")" + sheet.getRow(i_row).getCell(1).toString() + " " + sheet.getRow(i_row).getCell(2).toString();
                        //lab1.getText().toString()

//                    TextObs.setText(s+" "+s1);
                        Moy = -2;
                        return Moy;
                    }
                }


            }

        }
        return Moy;
    }

    public void String_Observation(int iRow, int jSheet, String Obs, int cellobs) throws IOException {
        FileInputStream fsIP = new FileInputStream(new File(src_excel.getText())); //Read the spreadsheet that needs to be updated
        HSSFWorkbook wb = new HSSFWorkbook(fsIP); //Access the workbook

        HSSFSheet worksheet = wb.getSheetAt(jSheet); //Access the worksheet, so that we can update / modify it.

        Cell cell = null; // declare a Cell object

        cell = worksheet.getRow(iRow).getCell(cellobs);   // Access the second cell in second row to update the value
        //System.out.println("RRRRRRRRRR"+Obs);

        cell.setCellValue("" + Obs);  // Get current cell value value and overwrite the value

        fsIP.close(); //Close the InputStream

        FileOutputStream fileOut = new FileOutputStream(src_excel.getText());  //Open FileOutputStream to write updates

        wb.write(fileOut); //write changes

        fileOut.close();  //close the stream
        wb.close();
    }

    public String observation(float moy) throws UnknownHostException {
        String obs = "";


        if (moy >= 1) {
            if (moy < 6) {
                obs = lab1.getText();

                return obs;
            } else if (moy < 10.0) {
                obs = lab2.getText();

                return obs;
            } else if (moy < 12.0) {
                obs = lab3.getText();

                return obs;
            } else if (moy < 14.0) {
                obs = lab4.getText();

                return obs;
            } else if (moy < 16.0) {
                obs = lab5.getText();

                return obs;
            } else if (moy < 18.0) {
                obs = lab6.getText();

                return obs;
            } else {
                obs = lab7.getText();

                return obs;

            }
        } else {
            if (moy == -1) {
                obs = "معفى";
                return obs;
            } else {
                obs = "غائب";
                return obs;
            }

        }
    }

    @FXML
    private void modify(ActionEvent event) {
        lab1.setEditable(true);
        lab2.setEditable(true);
        lab3.setEditable(true);
        lab4.setEditable(true);
        lab5.setEditable(true);
        lab6.setEditable(true);
        lab7.setEditable(true);

    }

    @FXML
    private void fileAnalyse(ActionEvent event) throws IOException {
        if (src_excel.getText().equals("")) {
            showDialog("ملف الحجز غير جاهز", "", "\n قم برفع ملف الحجز اولا حتى تتمكن من تحليل النتائج", "black");
        } else {
            //showDialog("", "\n تأكد من تصحيح جميع الأخطاء للحصول على تحليل نتائج صحيح", "black");
            String l = " تأكد من تصحيح جميع الأخطاء للحصول على تحليل نتائج صحيح";
            String l2 = "لتحليل النتائج اضغط على موافق(OK)";
            Alert a = new Alert(Alert.AlertType.WARNING);
            Text tx = new Text(l + "\n\n" + l2);
            //Text title = new Text(titre);
            tx.setStyle("-fx-font-family: 'Tajawal Medium';");
            // title.setStyle("-fx-font-family: 'Tajawal Medium';-fx-fill:" + colorTitle + ";");
            TextFlow tf = new TextFlow();

            tf.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

            // tf.setId("mytext1");
            tf.getChildren().addAll(tx);
            a.setTitle("ملف الحجز غير جاهز");

            a.setHeaderText(null);

            a.getDialogPane().setContent(tf);
            Optional<ButtonType> result = a.showAndWait();
            ButtonType btn = result.orElse(ButtonType.CANCEL);
            if (btn == ButtonType.OK) {
                btnError.setDisable(false);
                button.setDisable(true);
                progress.setVisible(true);
//          remLab.setVisible(false);
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ClassAnalyse FileAnalyse = new ClassAnalyse();

                            FileAnalyse.ReadFile2Analyse(src_excel.getText(), progress, analyseButt, button, btnError, remText);
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                });

                t.start();
            } else {
            }
            // a.setContentText("قم برفع ملف الحجز اولا حتى تتمكن من تطبيق الملاحظات");


        }
    }

    @FXML
    private void closeApp(ActionEvent ev) {
        Stage stage = (Stage) ((Node) ev.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void minusApp(ActionEvent ev) {

        Stage stage = (Stage) ((Node) ev.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void showDialog(String title, String titre, String content, String colorTitle) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        Text t = new Text(content);
        Text titl = new Text(titre);
        t.setStyle("-fx-font-family: 'Tajawal Medium';");
        titl.setStyle("-fx-font-family: 'Tajawal Medium';-fx-fill:" + colorTitle + ";");
        TextFlow tf = new TextFlow();
        tf.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        // tf.setId("mytext1");
        tf.getChildren().addAll(titl, t);
        a.setTitle(title);

        Stage stage = (Stage) a.getDialogPane().getScene().getWindow();

        stage.getIcons().add(new Image("/sample/logo.png"));
        a.setHeaderText(null);

        a.getDialogPane().setContent(tf);
        // a.setContentText("قم برفع ملف الحجز اولا حتى تتمكن من تطبيق الملاحظات");
        a.showAndWait();
    }

    @FXML
    private void onShowError(ActionEvent event) {
        // Error er=new Error();
        System.out.println(getError());
        String title = "\n" + "رقم الصفحة التي فيها الخلل:" + " ..." + "  " + "  " + "الاسم و اللقب" + " " + " : " + "\t" + " | " + " ت " + " | " + " ف " + " | " + " إخ " + " | ";
        String titled2 = "\n" + "رقم الصفحة التي فيها الخلل:" + " ..." + "  " + "  " + "الاسم و اللقب" + " " + " : " + "\t" + " | " + " ت " + " | " + " ف1 " + " | " + " ف2 " + " | " + " إخ " + " | ";
        //showDialog(title, getError()+"\n"+getS_errorD2(), "red");
        System.out.println("*******************************************>>>" + src_excel.getText());
        if (src_excel.getText().equals("")) {
            showDialog("ملف الحجز غير جاهز", "", "قم برفع ملف الحجز أولا", "red");
        } else {
            if (getS_errorD2() == null && getError() == null && getNoError() == "C") {
                showDialog("مواقع الخطأ", "", "لا يوجد خطـأ", "green");
                se = "";
                sed2 = "";
            } else {
                if (getS_errorD2() == null && getError() == null && getNoError() == null) {
                    showDialog("ملف الحجز غير جاهز", "", "\n قم بتطبيق الملاحظات أولا حتى تتمكن  من رؤية الأخطاء", "black");
                    se = "";
                    sed2 = "";
                } else {
                    if (!(getS_errorD2() == null) && getError() == null && !(getNoError() == null)) {
                        showDialog("مواقع الخطأ", titled2, getS_errorD2(), "red");
                        sed2 = "";
                        se = "";
                    }

                    // setError("");setS_errorD2("");
                    else {
                        if (getS_errorD2() == null && !(getError() == null) && !(getNoError() == null)) {
                            showDialog("مواقع الخطأ", title, getError(), "red");
                            sed2 = "";
                            se = "";
                            //setError(null);
                            //setS_errorD2("");setError("");
                        }
                    }
                }
            }
        }
        // setError("");setS_errorD2("");
    }

    private String getErrorOneDev(int numClass, int row, String name, String prenom, String eval, String dev, String exam) {
        float ev, dv, exm;

        // s.add("");
        //int n=;

        try {
            ev = Float.parseFloat(eval);
            dv = Float.parseFloat(dev);
            exm = Float.parseFloat(exam);
            if (ev > 20 || dv > 20 || exm > 20 || ev < 0 || dv < 0 || exm < 0) {
                se = se + "\n" + "رقم الصفحة التي فيها الخلل:" + " " + numClass + "  " + name + " " + prenom + " : " + "\t" + " | " + eval + " | " + dev + " | " + exam + " | ";
                // se=se+"\n"+exm+"-"+dv+"-"+ev+":"+" "+row+" "+numClass;

                setError(se);
                return se;
            } else {
                setNoError("C");

            }

        } catch (NumberFormatException e) {
            if (!eval.equals("معفى") || !dev.equals("معفى") || !exam.equals("معفى")) {

                // se=se+"\n"+exam+" / "+dev+" / "+eval+" : "+prenom+""+name+" "+numClass;
                se = se + "\n" + "رقم الصفحة التي فيها الخلل:" + " " + numClass + "  " + name + " " + prenom + " : " + "\t" + " | " + eval + " | " + dev + " | " + exam + " | ";
                setError(se);

                return se;
            } else {
                setNoError("C");
                //return se;
            }

        }


        return se + "\n";
    }

    private String getErrorTowDev(int numClass, int row, String name, String prenom, String eval, String dev1, String dev2, String exam) {
        float ev, dv1, dv2, exm;

        // s.add("");
        //int n=;

        try {
            ev = Float.parseFloat(eval);
            dv1 = Float.parseFloat(dev1);
            dv2 = Float.parseFloat(dev2);
            exm = Float.parseFloat(exam);
            if (ev > 20 || dv1 > 20 || dv2 > 20 || exm > 20 || ev < 0 || dv1 < 0 || dv2 < 0 || exm < 0) {
                sed2 = sed2 + "\n" + "رقم الصفحة التي فيها الخلل:" + " " + numClass + "  " + name + " " + prenom + " : " + "\t" + " | " + eval + " | " + dev1 + " | " + dev2 + " | " + exam + " | ";

                setS_errorD2(sed2);
                return sed2;
            } else {
                setNoError("C");
                //return se;
            }

        } catch (NumberFormatException e) {
            if (!eval.equals("معفى") || !dev1.equals("معفى") || !dev2.equals("معفى") || !exam.equals("معفى")) {

                sed2 = sed2 + "\n" + "رقم الصفحة التي فيها الخلل:" + " " + numClass + "  " + name + " " + prenom + " : " + "\t" + " | " + eval + " | " + dev1 + " | " + dev2 + " | " + exam + " | ";
                setS_errorD2(sed2);

                return sed2;
            } else {
                setNoError("C");
                //return se;
            }

        }

        return sed2 + "\n";
    }

    @FXML
    public void onExchangeNote(ActionEvent ev) {

        Parent root = null;

        try {
            root = FXMLLoader.load(getClass().getResource("Dialog_copy.fxml"));


        } catch (IOException io) {
            System.out.println(">>>>>>>>>>>>>>" + io);

        }


        Stage stage = new Stage();
        //   DialogPane dialogPane=dialog.getDialogPane();
        // Stage stage=(Stage)dialog.getDialogPane().getScene().getWindow();
        //Scene scene = new Scene(root);

        stage.setScene(new Scene(root));//setContent(root);
        //dialog.getDialogPane().setPadding(Insets.EMPTY);
        stage.getScene().setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);


        //Scene scene = new Scene(root);
        // scene.setFill(Color.TRANSPARENT);


        // Window window=dialog.getDialogPane().getScene().getWindow();

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();

            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });


        // anchor.getScene().getWindow().hide();
        stage.show();
        //dialog.show();
        // window.set
        //System.out.println("***********>>*****"+getCloseDialog());
        /*window.setOnCloseRequest(event -> {

            if(getCloseDialog().equals("OFF")){
                showDialog("","لا يمكنك الخروج حتى انتهاء عملية نسخ النقاط","black");

            }else{
                dialog.close();
            }

        });

        dialog.show();*/


    }


}
