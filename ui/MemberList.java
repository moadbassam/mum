package ui;

import java.io.IOException;

import business.LibraryMember;
import business.LibrarySystemException;
import business.Person;
import business.SystemController;
import dataaccess.DataAccessFacade;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MemberList extends Application {

	TableView<Person> libraryMemberTable;
	// TableView<Person> personTable;
	public static Stage primaryStage;

	private IntegerProperty index = new SimpleIntegerProperty();
	private ObservableList<LibraryMember> data;
	TableView<LibraryMember> personTable;

	public final double getIndex() {
		return index.get();
	}

	public final void setIndex(Integer value) {
		index.set(value);
	}

	public IntegerProperty indexProperty() {
		return index;
	}

	boolean EditClicked = false;
	boolean AddClicked = false;

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage stage) throws IOException {

		this.primaryStage = stage;
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/FXML_MemberTable.fxml"));
		stage.setTitle("LibraryMember");

		personTable = (TableView<LibraryMember>) root.lookup("#personTable");

		DataAccessFacade dataaccessfacade = new DataAccessFacade();

		TableColumn<LibraryMember, String> MemberId = new TableColumn<>(String.format("ID"));
		MemberId.setMinWidth(90);
		MemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));

		TableColumn<LibraryMember, String> fname = new TableColumn<>(String.format("First Name"));
		fname.setMinWidth(90);
		fname.setCellValueFactory(new PropertyValueFactory<>("firstName"));

		TableColumn<LibraryMember, String> lname = new TableColumn<>(String.format("Last Name"));
		lname.setMinWidth(80);
		lname.setCellValueFactory(new PropertyValueFactory<>("lastName"));

		TableColumn<LibraryMember, String> phone = new TableColumn<>(String.format("Phone"));
		phone.setMinWidth(80);
		phone.setCellValueFactory(new PropertyValueFactory<>("telephone"));

		data = dataaccessfacade.readLibraryMemberMapList();
		personTable.setItems(data);
		personTable.getColumns().addAll(MemberId, fname, lname, phone);

		Button moreInfo = (Button) root.lookup("#moreinfo");
		moreInfo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				final Text info = (Text) root.lookup("#addressinfo");
				info.setFill(Color.FIREBRICK);
try {
	info.setText(personTable.getSelectionModel().getSelectedItem().toString());
} catch (Exception e) {
	info.setText("Please Selet Member in The list!");
}
				

			}
		});

		Button EditButton = (Button) root.lookup("#edit");
		EditButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				stage.hide();
				try {
					EditClicked = true;
					EditMember editform = new EditMember();
					primaryStage.setUserData(personTable.getSelectionModel().getSelectedItem());

					editform.start(primaryStage);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		personTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue observable, Object oldvalue, Object newValue) {
				setIndex(personTable.getSelectionModel().getSelectedIndex());
			}
		});

		Button bdelete = (Button) root.lookup("#deleteButton");
		bdelete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				SystemController systemontroller = SystemController.getInstance();
				LibraryMember librarymember = (LibraryMember) personTable.getSelectionModel().getSelectedItem();
				try {
					systemontroller.deleteMember(librarymember);
					data.remove(index.get());
					personTable.getSelectionModel().clearSelection();
				} catch (LibrarySystemException e) {
					e.printStackTrace();
				}
			}
		});

		//
		stage.setOnHiding(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						try {
							if (!EditClicked && !AddClicked) {
								stage.hide();
								MainForm form = new MainForm();
								form.start(primaryStage);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
		});

		Button addButton = (Button) root.lookup("#addButton");

		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					AddClicked = true;
					AddNewMemeber addForm = new AddNewMemeber();
					addForm.start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		Scene scene = new Scene(root, 550, 550);

		scene.getStylesheets().add(getClass().getResource("../Css/InnerForms.css").toExternalForm());

		stage.setScene(scene);
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void getSelectedRow() {
		try {
			System.out.println(personTable.getSelectionModel().getSelectedItem());

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	public static void setStage(Application form) throws Exception {
		form.start(primaryStage);
	}
}
