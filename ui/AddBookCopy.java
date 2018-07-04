package ui;

import java.io.IOException;

import business.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AddBookCopy extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws LibrarySystemException, IOException {

		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/FXML_addBookCopy.fxml"));

		stage.setTitle("Add Book Copy");
		Scene scene = new Scene(root, 400, 350);
		stage.setScene(scene);
		stage.show();
		TextField textfield = (TextField) root.lookup("#textfield");

		Button button = (Button) root.lookup("#button");
		Label lblMessage = (Label) root.lookup("#lblMessage");
		SystemController syscon = SystemController.getInstance();

		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {

					syscon.addBookCopy(textfield.getText());
					lblMessage.setText(
							"Book Copy has been added to " 
									+ syscon.searchBook(textfield.getText()).getTitle());
				} catch (LibrarySystemException e) {
					lblMessage.setText(e.getLocalizedMessage());
				}
			}
		});

		stage.setOnHiding(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						try {
							//if (MainForm.primaryStage != null)
								MainForm.setStage(new MainForm());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
		});

	}
}
