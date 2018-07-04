package ui;

import java.io.IOException;

import business.Address;
import business.SystemController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AddNewMemeber extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/FXML_addNewMember.fxml"));
		Scene sc = new Scene(root, 600, 400);
		stage.setScene(sc);

		TextField txtid = (TextField) root.lookup("#id");
		TextField txtfname = (TextField) root.lookup("#fname");
		TextField txtlname = (TextField) root.lookup("#lname");
		TextField txtphone = (TextField) root.lookup("#phone");

		TextField txtstreet = (TextField) root.lookup("#street");
		TextField txtstate = (TextField) root.lookup("#state");
		TextField txtlcity = (TextField) root.lookup("#city");
		TextField txtzip = (TextField) root.lookup("#zip");

		stage.setTitle("Add New Member!");
		Button bclear = (Button) root.lookup("#cancel");
		bclear.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MemberList m = new MemberList();
				try {
					m.start(stage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		SystemController systemontroller = SystemController.getInstance();

		Button bsubmit = (Button) root.lookup("#submit");
		bsubmit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				try {

					String id = txtid.getText().toString();
					String fname = txtfname.getText().toString();
					String lname = txtlname.getText().toString();
					String phone = txtphone.getText().toString();
					String street = txtstreet.getText().toString();
					String state = txtstate.getText().toString();
					String city = txtlcity.getText().toString();
					String zip = txtzip.getText().toString();

					if (id.equals("")) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error Dialog");
						alert.setContentText("Member ID is a required filed!");
						alert.showAndWait();
					} else if (systemontroller.checkMemberId(id)) {
						Address add = new Address(street, city, state, zip);
						systemontroller.addNewMember(id, fname, lname, phone, add);
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Info Dialog");
						alert.setContentText("You Have Successfuly inserted a new Member!");
						alert.showAndWait();
						// Clearing the values////////
						txtid.setText("");
						txtfname.setText("");
						txtlname.setText("");
						txtphone.setText("");
						txtstreet.setText("");
						txtstate.setText("");
						txtlcity.setText("");
						txtzip.setText("");
						////////////////////////////
					} else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error Dialog");
						alert.setContentText("Member ID Exists already, Kindly enter another value!");
						alert.showAndWait();
					}

				} catch (Exception e) {
					e.getMessage();
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
							stage.hide();
							if (MemberList.primaryStage != null) {
								MemberList.setStage(new MemberList());
							} else

								MainForm.setStage(new MainForm());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
		});

		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}// main method

}// AddNewMember Class
