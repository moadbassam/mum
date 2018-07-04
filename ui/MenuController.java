package ui;

import java.net.URL;
import java.util.ResourceBundle;


import business.SystemController;
import dataaccess.Auth;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class MenuController implements Initializable {

	@FXML
	private Menu LibraryMenu;

	@FXML
	private Menu BookMenu;

	@FXML
	private Menu ActionsMenu;

	@FXML
	private Menu lstofBooks;

	@FXML
	private MenuItem chkrecordMenu;

	@FXML
	private MenuItem checkoutBookMenu;

	@FXML
	private MenuItem prntChkMenu;

	@FXML
	private void AddMemeber() throws Exception {
		MainForm.primaryStage.hide();
		Thread.sleep(500);
		MainForm.setStage(new AddNewMemeber());
	}

	@FXML
	private void ShowMemberList() throws Exception {
		MainForm.primaryStage.hide();
		Thread.sleep(500);
		MainForm.setStage(new MemberList());
	}

	@FXML
	private void showBooksList() throws Exception {
		MainForm.primaryStage.hide();
		Thread.sleep(500);
		MainForm.setStage(new BooksForm());
	}

	@FXML
	private void AddBook() throws Exception {

		MainForm.primaryStage.hide();
		Thread.sleep(500);
		MainForm.setStage(new AddBookForm());

	}

	@FXML
	private void addCopyforBook() throws Exception {

		MainForm.primaryStage.hide();
		Thread.sleep(500);
		MainForm.setStage(new AddBookCopy());

	}

	@FXML
	private void showPrintForm() throws Exception {
		MainForm.primaryStage.hide();
		Thread.sleep(500);
		MainForm.setStage(new PrintCheckOutRecord());
	}

	@FXML
	private void showCheckoutBook() throws Exception {
		MainForm.primaryStage.hide();
		Thread.sleep(500);
		MainForm.setStage(new CheckoutBook());
	}

	@FXML
	private void showCheckRecords() throws Exception {
		MainForm.primaryStage.hide();
		Thread.sleep(500);
		MainForm.setStage(new MemberCheckoutRecord());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		if (SystemController.currentAuth == Auth.ADMIN)
			ActionsMenu.setVisible(false);
		else if (SystemController.currentAuth == Auth.LIBRARIAN) {
			BookMenu.setVisible(false);
			LibraryMenu.setVisible(false);
		}

	}

}
