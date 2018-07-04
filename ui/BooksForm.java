package ui;

import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import business.Author;
import business.Book;
import business.LibrarySystemException;
import business.SystemController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class BooksForm extends Application {

	private IntegerProperty index = new SimpleIntegerProperty();
	private ObservableList<Book> data;
	public static Stage primaryStage;

	public final double getIndex() {
		return index.get();
	}

	public final void setIndex(Integer value) {
		index.set(value);
	}

	public IntegerProperty indexProperty() {
		return index;
	}

	TableView<Book> booksTable;

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage stage) throws IOException {
		BooksForm.primaryStage = stage;
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/FXML_BooksForm.fxml"));
		stage.setTitle("Books");

		SystemController controller = getData(root);

		Button editButton = (Button) root.lookup("#editButton");
		editButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (booksTable.getSelectionModel() != null) {
					primaryStage.setUserData(booksTable.getSelectionModel().getSelectedItem());
					try {
						editRow(booksTable.getSelectionModel().getSelectedItem());
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Please select a row first");
					}
				}
			}
		});

		booksTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue observable, Object oldvalue, Object newValue) {
				setIndex(data.indexOf(newValue));
			}
		});

		Button deleteButton = (Button) root.lookup("#deleteButton");
		Alert alert = new Alert(AlertType.ERROR);
		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println(booksTable.getSelectionModel().getSelectedIndex());
				if (booksTable.getSelectionModel() == null) {

					alert.setContentText("Please select a row to delete!");
					alert.show();
					return;
				}

				String isbn = booksTable.getSelectionModel().getSelectedItem().getIsbn();
				try {
					controller.deleteBook(isbn);
					data.remove(index.get());
					booksTable.getSelectionModel().clearSelection();

				} catch (LibrarySystemException e) {
					System.out.println(e.getMessage());
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
							MainForm.setStage(new MainForm());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
		});

		Scene scene = new Scene(root, 900, 550);
		stage.setScene(scene);
		stage.show();

	}

	@SuppressWarnings("unchecked")
	private SystemController getData(Parent root) {

		booksTable = (TableView<Book>) root.lookup("#booksTable");

		SystemController controller = SystemController.getInstance();

		TableColumn<Book, String> BookName = new TableColumn<>(String.format("Book Title"));
		BookName.setMinWidth(170);
		BookName.setCellValueFactory(new PropertyValueFactory<>("title"));

		TableColumn<Book, String> Noofcopies = new TableColumn<>(String.format("Num Of Copies"));
		Noofcopies.setMinWidth(200);
		Noofcopies.setCellValueFactory(new PropertyValueFactory<>("noOfCopies"));

		TableColumn<Book, String> bookISBN = new TableColumn<>(String.format("ISBN"));
		bookISBN.setMinWidth(80);
		bookISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));

		TableColumn<Book, String> bookLength = new TableColumn<>(String.format("Days"));
		bookLength.setMinWidth(80);
		bookLength.setCellValueFactory(new PropertyValueFactory<>("maxCheckoutLength"));

		TableColumn<Book, String> colAuthors = new TableColumn<Book, String>("Authors");
		colAuthors.setCellValueFactory(new Callback<CellDataFeatures<Book, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Book, String> p) {

				List<Author> authorList = p.getValue().getAuthors();
				String authors = "";
				for (int i = 0; i < authorList.size(); i++) {
					authors += authorList.get(i).getLastName() + ' ' + authorList.get(i).getFirstName();
					if (i < authorList.size() - 2) {
						authors += ",";
					}
				}
				return new ReadOnlyStringWrapper(authors);

			}
		});

		data = controller.readBooksMap();
		booksTable.setItems(data);
		booksTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		booksTable.getColumns().addAll(BookName, bookISBN, bookLength, Noofcopies, colAuthors);

		return controller;
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void editRow(Book item) throws Exception {
		EditBook form = new EditBook();
		form.start(primaryStage);
	}
	
	public static void setStage(Application form) throws Exception {
		form.start(primaryStage);
	}
}
