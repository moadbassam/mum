package ui;

import java.io.IOException;

import business.LoginException;
import business.SystemController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Login extends Application {
	
	@Override
	public void start(Stage primaryStage) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/FXML_LoginForm.fxml"));
		primaryStage.setTitle("Login Form");
//added by ABEER
		
		//by moad
		// you can communicate with the components using id's
		TextField usertf = (TextField) root.lookup("#usertf");
		PasswordField passwordField = (PasswordField) root.lookup("#passwordField");
		Text actiontarget = (Text) root.lookup("#actiontarget");
		Button submitbtn = (Button) root.lookup("#submitbtn");

		submitbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				SystemController controller = SystemController.getInstance();
				try {

					controller.login(usertf.getText(), passwordField.getText());
					MainForm main = new MainForm();
					primaryStage.hide();
					Thread.sleep(200);
					main.start(primaryStage);
					primaryStage.show();

				} catch (LoginException e1) {

					actiontarget.setText("Error : " + e1.getMessage());
					// e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		Scene scene = new Scene(root, 500, 250);
		scene.getStylesheets().add(getClass().getResource("../Css/Login.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
