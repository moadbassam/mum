package ui;

import business.LibrarySystemException;
import business.SystemController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class AddBookController {
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
	protected void handleAddBookAction() throws Exception {
		Alert alert = new Alert(AlertType.ERROR);
		ci = SystemController.getInstance();
		String isbn = txtISBN.getText();
		String title = txtTitle.getText();
		try {
			int maxCheckoutLength = Integer.parseInt(txtMaxChkLength.getText());
			String authors = txtAuthors.getText();
			int noOfCopies = Integer.parseInt(txtNoOfCopy.getText());

			ci.addBook(isbn, title, maxCheckoutLength, authors, noOfCopies);
		} catch (NumberFormatException e) {
			if (e instanceof NumberFormatException)
				alert.setContentText("Make sure of Fields data!");
			alert.show();
			return;
		}
		lblMessage.setText(title + " has been added successfully!");
	}

	@FXML
	protected void handleCancelBookAction() throws Exception {
		MainForm.setStage(new MainForm());
	}
}
