package ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import business.Author;
import business.Book;
import business.SystemController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class EditBookController implements Initializable {
	@FXML
	private TextField txtISBN;
	@FXML
	private TextField txtTitle;
	@FXML
	private TextField txtAuthors;
	@FXML
	private TextField txtMaxChkLength;
	@FXML
	private TextField txtNoOfCopy;

	@FXML
	private Label lblMessage;

	SystemController ci;

	@FXML
	protected void handleEditBookAction() throws Exception {
		Alert alert = new Alert(AlertType.ERROR);
		ci = SystemController.getInstance();

		Book book = (Book) BooksForm.primaryStage.getUserData();

		try {
			int maxCheckoutLength = Integer.parseInt(txtMaxChkLength.getText().toString());
			int noOfCopies = Integer.parseInt(txtNoOfCopy.getText().toString());
			String isbn = txtISBN.getText().toString();
			String title = txtTitle.getText().toString();
			List<Author> authorsList = new ArrayList();

			for (Author auther : book.getAuthors())
				authorsList.add(auther);
			ci.updateBookInfo(isbn, title, maxCheckoutLength, authorsList, noOfCopies);
			lblMessage.setText(title + " has been updated successfully!");
			
		} catch (NumberFormatException e) {
			alert.setContentText("Make sure of Fields data!");
			alert.show();
			return;
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtTitle.setText("Hello");

	}

	@FXML
	protected void handleCancelBookAction() throws Exception {
		EditBook.primaryStage.hide();
		BooksForm.primaryStage = new Stage();
		BooksForm.setStage(new BooksForm());
	}
}
