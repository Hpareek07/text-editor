package com.fo.controls.fontpicker;

import com.fo.controls.fontpicker.FontPicker;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class fontselector extends Texteditor {


	public void display(Texteditor editor){
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Font Selector");
		stage.setMinWidth(400);
		Button button = new Button();
		FontPicker control = new FontPicker();
		//you can change width of fontpicker
		control.setPrefWidth(150);

		button.setText("OK");
		button.setOnAction(e->{ 
//			control.valueProperty().addListener(observable -> {
//			});
			System.out.println(control.getValue());
			editor.changeFont(control.getValue());
			stage.close();
		});
		VBox vbox = new VBox();
		vbox.getChildren().addAll(control);
		vbox.getChildren().add(button);
		vbox.setAlignment(Pos.CENTER_RIGHT);
		Scene scene = new Scene(vbox);
		stage.setScene(scene);
		stage.showAndWait();
	}

	public void fontset(String fontname){
		
	}


}