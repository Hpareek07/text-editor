package com.fo.controls.fontpicker;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.print.*;
@SuppressWarnings("unused")
public class Texteditor extends Application {
	KeyCombination keyCombinationShiftC = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
	KeyCombination keyCombinationShiftP = new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN);
	KeyCombination keyCombinationShiftF = new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN);
	Label message = new Label();
	TextArea textarea = new TextArea();
	MenuBar menubar = new MenuBar();
	MenuItem menuItem1 = new MenuItem("New");
	MenuItem menuItem2 = new MenuItem("Save");
	MenuItem menuItem3 = new MenuItem("Open");
	MenuItem menuItem4 = new MenuItem("Exit");
	Menu menu = new Menu("File");
	Menu menu1 = new Menu("Edit");
	MenuItem menuItem5 = new MenuItem("Cut");
	MenuItem menuItem6 = new MenuItem("Copy");
	MenuItem menuItem7 = new MenuItem("Paste");
	MenuItem menuItem15 = new MenuItem("Font...");
	CheckMenuItem menuItem8 = new CheckMenuItem("Word Wrap");
	Menu menu2 = new Menu("Help");
	MenuItem menuItem9 = new MenuItem("About");
	MenuItem menuItem11 = new MenuItem("Print");
	MenuItem menuItem12 = new MenuItem("Page Setup");
	MenuItem menuItem13 = new MenuItem("Find");
	SeparatorMenuItem menuItem10 = new SeparatorMenuItem();
	SeparatorMenuItem menuItem14 = new SeparatorMenuItem();
	@Override
	public void start(Stage arg0) throws Exception {
		arg0.setTitle("NotesPad || Your Very Own Text Editor");
		keyhandle();
		menu.getItems().addAll(menuItem1,menuItem2,menuItem3,menuItem10,menuItem11,menuItem12,menuItem4);
		menu1.getItems().addAll(menuItem5,menuItem6,menuItem7,menuItem8,menuItem14,menuItem13,menuItem15);
		menu2.getItems().add(menuItem9);
		menubar.getMenus().add(menu);
		menubar.getMenus().add(menu1);
		menubar.getMenus().add(menu2);
		menuItem2.setOnAction(e->{savefile();});
		menuItem3.setOnAction(e-> {openfile();});
		menuItem4.setOnAction(e->{exit();});
		menuItem1.setOnAction(e-> {newfile();});
		menuItem5.setOnAction(e-> {textarea.cut();});
		menuItem6.setOnAction(e->{textarea.copy();});
		menuItem7.setOnAction(e->{textarea.paste();});
		menuItem8.setOnAction(e->{wordwrap();});
		menuItem9.setOnAction(e->{about();});
		menuItem11.setOnAction(e->{printservice1(textarea);});
		menuItem12.setOnAction(e->{printpservice(arg0);});
		menuItem13.setOnAction(e->{getString();});
		menuItem15.setOnAction(e->{
			fontselector font = new fontselector();
			font.display(this);
			
		});
	    BorderPane gridpane = new BorderPane();
		gridpane.setTop(menubar);
		gridpane.setCenter(textarea);
		Scene scene = new Scene(gridpane);
		File f = new File("style.css");
		scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
		arg0.setScene(scene);
		arg0.show();
		arg0.setOnCloseRequest(e->{exit();});
	}

	public void changeFont(Font font){
		textarea.setFont(font);
	}

	public void getString(){
		TextInputDialog dialog = new TextInputDialog("Search");
		dialog.setTitle("Search Box");
		dialog.setHeaderText("Enter the Search Term");
		dialog.setContentText("");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    String data = result.get();
		    try {
				searchs(data);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	public void searchs(String searchtext){
		String data = textarea.getText();
		if(textarea.getText().contains(searchtext)){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Found");
			alert.setHeaderText("Found the text "+searchtext);
			alert.setContentText("");
			alert.showAndWait();
		}
		else{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Not Found");
			alert.setHeaderText("Not Found the text "+searchtext);
			alert.setContentText("");
			alert.showAndWait();
		}
	}
	public void printpservice(Window node){
		PrinterJob job = PrinterJob.createPrinterJob();
		if(job!=null && job.showPageSetupDialog(node.getScene().getWindow())){
			boolean success = job.showPageSetupDialog(node);
			if(success){
				job.endJob();
				System.out.println("Success");
			}
		}
	}
	public void keyhandle(){
		textarea.addEventFilter(KeyEvent.KEY_PRESSED, e->{
			if(keyCombinationShiftC.match(e)){
				TextInputDialog dialog = new TextInputDialog("Save");
				dialog.setTitle("Save Dialog Box");
				dialog.setHeaderText("Enter the file Name");
				dialog.setContentText("Enter name which you want to use for saving:");
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()){
				    String filename = result.get();
				    try {
						saveTofile(filename,textarea);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
			if(keyCombinationShiftP.match(e)){
				printservice1(textarea);
			}
			if(keyCombinationShiftF.match(e)){
				getString();
			}
		});
	}
	public void savefile(){
		TextInputDialog dialog = new TextInputDialog("Save");
		dialog.setTitle("Save Dialog Box");
		dialog.setHeaderText("Enter the file Name");
		dialog.setContentText("Enter name which you want to use for saving:");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    String filename = result.get();
		    try {
				saveTofile(filename,textarea);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	public void openfile(){
		TextInputDialog dialog = new TextInputDialog("FileName");
		dialog.setTitle("File Opening Dialog Box");
		dialog.setHeaderText("Enter the File Name");
		dialog.setContentText("File Name:");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		 String filename = result.get();
		 try{
				readFile(filename);
			}
			catch(Exception e1){ System.out.println(e1.getMessage());}
		}
	}
	public void newfile(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Are you really want to proceed?");
		alert.setHeaderText("This action will delete all current data that is not saved.");
		alert.setContentText("Are you ok with this?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			textarea.setText("");
		} else {
		    alert.close();
		}
	}
	public void about(){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About NotesPad");
		alert.setHeaderText("Build On Java by Antilia Technologies");
		alert.setContentText("Programmer: Harshit Pareek || Twitter: @harshitp07");
		alert.showAndWait();
	}
	public void exit(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("NotesPad");
		alert.setHeaderText("Do you want to save changes to the file");
		alert.setContentText("");

		ButtonType buttonTypeOne = new ButtonType("Save");
		ButtonType buttonTypeTwo = new ButtonType("Don't Save");
		ButtonType buttonTypeCancel = new ButtonType("Cancel");

		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo,buttonTypeCancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne){
		    savefile();
		} else if (result.get() == buttonTypeTwo) {
		    System.exit(0);
		} else {
		    
		}
	}
	public void printservice1(Node node){
		PrinterJob job = PrinterJob.createPrinterJob();
		if(job!=null && job.showPrintDialog(node.getScene().getWindow())){
			boolean success = job.printPage(node);
			if(success){
				job.endJob();
				System.out.println("Success");
			}
		}
	}
	/*public void printservice(Node node){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Print Confirmation Dialog");
		alert.setHeaderText("Do you want to create a print job?s");
		alert.setContentText("Are you ok with this?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			PrinterJob job = PrinterJob.createPrinterJob();
		    if (job != null) {
		      System.out.println(job.jobStatusProperty().asString());

		      boolean printed = job.printPage(node);
		      if (printed) {
		        job.endJob();
		      } else {
		        System.out.println("Printing failed.");
		      }
		    } else {
		      System.out.println("Could not create a printer job.");
		    }
		} else {
		  alert.close();
		}
		FileInputStream textStream;
		textStream = new FileInputStream(FILE_NAME);
		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		PrintService services = PrintServiceLookup.lookupPrintServices(flavor,aset);
		PrintService defaultservice = PrintServiceLookup.lookupDefaultPrintService();
		if(services.equals(null)){
			if(defaultservice == null){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Alert Dialog");
				alert.setHeaderText("No Printer Found");
				alert.setContentText("Please configure and connect a printer.");
				alert.showAndWait();
			}
			else{
				DocPrintJob job = defaultservice.createPrintJob();
				job.print(data, aset);
			}
		}
		else{
			PrintService service = ServiceUI.printDialog(null, 200, 200, services, defaultservice, flavor, aset);
			if(service!=null){
				DocPrintJob job = service.createPrintJob();
				job.print(data, aset);
			}
		}
	} */
	public void printpreviewservice(Node node){
		    }
	public void wordwrap(){
		textarea.setWrapText(true);
	}
    public void saveTofile(String filename,TextArea textarea) throws Exception {
		String content = textarea.getText();
		FileOutputStream fop = null;
		File file;
		try {

			file = new File(filename+".txt");
			fop = new FileOutputStream(file);

			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// get the content in bytes
			byte[] contentInBytes = content.getBytes();

			fop.write(contentInBytes);
			fop.flush();
			fop.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			}
	}
	private List<String> readFile(String filename)
	{
	  List<String> records = new ArrayList<String>();
	  try
	  {
	    BufferedReader reader = new BufferedReader(new FileReader(filename+".txt"));
	    String line;
	    while ((line = reader.readLine()) != null)
	    {
	      records.add(line);
	      textarea.setText(line);
	    }
	    reader.close();
	    return records;
	  }
	  catch (Exception e)
	  {
	    System.err.format("Exception occurred trying to read '%s'.", filename);
	    e.printStackTrace();
	    return null;
	  }
	}
	public static void main(String[] args){
		launch(args);
	}
}