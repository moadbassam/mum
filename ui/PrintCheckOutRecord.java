package ui;

import java.io.IOException;
import java.util.List;

import business.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PrintCheckOutRecord extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws LibrarySystemException, IOException {
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/FXML_memberCheckoutRecord.fxml"));

		stage.setTitle("Print Checkout Record");
		stage.setOnHiding(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						try {

							stage.hide();
							MainForm form = new MainForm();
							form.start(stage);

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});

		TextField memberIdtxt = (TextField) root.lookup("#memberid");

		Button printbtb = (Button) root.lookup("#print");

		SystemController syscon = SystemController.getInstance();

		printbtb.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					syscon.printCheckoutRecord(memberIdtxt.getText());
				} catch (LibrarySystemException e) {
					System.out.println(e.getMessage());
				}
			}
		});

		Scene scene = new Scene(root, 400, 350);
		stage.setScene(scene);
		stage.show();
	}
}
