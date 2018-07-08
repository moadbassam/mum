package ui;

import java.io.IOException;

import business.Address;
import business.LibraryMember;
import business.LibrarySystemException;
import business.SystemController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class EditMember extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent rot = FXMLLoader.load(getClass().getResource("../FXML_Files/FXML_EditMember.fxml"));
		LibraryMember person = (LibraryMember) MemberList.primaryStage.getUserData();

		TextField txtid = (TextField) rot.lookup("#id");
		TextField txtfname = (TextField) rot.lookup("#fname");
		TextField txtlname = (TextField) rot.lookup("#lname");
		TextField txtphone = (TextField) rot.lookup("#phone");
		TextField txtstreet = (TextField) rot.lookup("#street");
		TextField txtstate = (TextField) rot.lookup("#state");
		TextField txtlcity = (TextField) rot.lookup("#city");
		TextField txtzip = (TextField) rot.lookup("#zip");

		txtid.setText(person.getMemberId());
		txtfname.setText(person.getFirstName());
		txtlname.setText(person.getLastName());
		txtphone.setText(person.getTelephone());
		txtstreet.setText(person.getStreetAddress());
		txtstate.setText(person.getStateAddress());
		txtlcity.setText(person.getCityAddress());
		txtzip.setText(person.getZipAddress());

		Button bclear = (Button) rot.lookup("#cancel");
		bclear.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				MemberList m = new MemberList();
				try {
					m.start(primaryStage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		Button bsubmit = (Button) rot.lookup("#submit");
		bsubmit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SystemController systemontroller = SystemController.getInstance();
				String id = txtid.getText().toString();
				String fname = txtfname.getText().toString();
				String lname = txtlname.getText().toString();
				String phone = txtphone.getText().toString();
				String street = txtstreet.getText().toString();
				String city = txtlcity.getText().toString();
				String zip = txtzip.getText().toString();
				String state = txtstate.getText().toString();
				try {
					if (id.equals("")) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error Dialog");
						alert.setContentText("Member ID is a required filed!");
						alert.showAndWait();
					} else if (!systemontroller.checkMemberId(id)) {
						Address ad = new Address(street, city, state, zip);
						systemontroller.updateMemberInfo(id, fname, lname, phone, ad);
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Info Dialog");
						alert.setContentText("You Have Successfuly updated the Member!");
						alert.showAndWait();
					}
				} catch (LibrarySystemException e) {
					e.getMessage();
				}
			}

		});
		
		primaryStage.setOnHiding(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						try {
							MainForm.setStage(new MemberList());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
		});

		primaryStage.setTitle("Edit Member");
Scene scene=new Scene(rot,400,460);
		scene.getStylesheets().add(getClass().getResource("../Css/InnerForms.css").toExternalForm());

		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
