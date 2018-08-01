package ui;

import java.io.IOException;

import business.CheckoutRecordView;
import business.ControllerInterface;
import business.LibrarySystemException;
import business.SystemController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Color;
import javafx.util.converter.NumberStringConverter;

public class BookCheckoutRecordController {
	@FXML
	private TextField txtMemberId;
	@FXML
	private Label lblMessage;
	ControllerInterface ci;
	private ObservableList<CheckoutRecordView.CheckoutRecordEntryView> data;
	@FXML
	TableView<CheckoutRecordView.CheckoutRecordEntryView> tblCheckoutBook;

	@SuppressWarnings("unchecked")
	@FXML
	protected void handleRequest() throws IOException {
		tblCheckoutBook.setVisible(true);
		ci = SystemController.getInstance();
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/FXML_OverduePerBook.fxml"));

		try {
			data = ci.readCheckoutRecordByISBN(txtMemberId.getText());
			tblCheckoutBook.setItems(data);

			TableColumn<CheckoutRecordView.CheckoutRecordEntryView, String> titleCol = new TableColumn<>("Title");
			titleCol.setMinWidth(80);
			titleCol.setCellValueFactory(
					new PropertyValueFactory<CheckoutRecordView.CheckoutRecordEntryView, String>("title"));
			titleCol.setCellFactory(TextFieldTableCell.forTableColumn());

			TableColumn<CheckoutRecordView.CheckoutRecordEntryView, String> MemberName = new TableColumn<>(
					"Member Name");
			MemberName.setMinWidth(80);
			MemberName.setCellValueFactory(
					new PropertyValueFactory<CheckoutRecordView.CheckoutRecordEntryView, String>("MemberName"));
			MemberName.setCellFactory(TextFieldTableCell.forTableColumn());

			TableColumn<CheckoutRecordView.CheckoutRecordEntryView, String> dueDateCol = new TableColumn<>("Due Date");
			dueDateCol.setMinWidth(80);
			dueDateCol.setCellValueFactory(
					new PropertyValueFactory<CheckoutRecordView.CheckoutRecordEntryView, String>("dueDate"));
			dueDateCol.setCellFactory(TextFieldTableCell.forTableColumn());

			TableColumn<CheckoutRecordView.CheckoutRecordEntryView, String> isbnCol = new TableColumn<>("ISBN");
			isbnCol.setMinWidth(80);
			isbnCol.setCellValueFactory(
					new PropertyValueFactory<CheckoutRecordView.CheckoutRecordEntryView, String>("isbnIssueNum"));
			isbnCol.setCellFactory(TextFieldTableCell.forTableColumn());
			
			TableColumn<CheckoutRecordView.CheckoutRecordEntryView, String> CopyNum = new TableColumn<>("Copy Number");
			CopyNum.setMinWidth(80);
			CopyNum.setCellValueFactory(
					new PropertyValueFactory<CheckoutRecordView.CheckoutRecordEntryView, String>("copyNum"));
		CopyNum.setCellFactory(TextFieldTableCell.forTableColumn());
			
			TableColumn<CheckoutRecordView.CheckoutRecordEntryView, String> Status = new TableColumn<>("Status");
			Status.setMinWidth(80);
			Status.setCellValueFactory(
					new PropertyValueFactory<CheckoutRecordView.CheckoutRecordEntryView, String>("Status"));
			Status.setCellFactory(TextFieldTableCell.forTableColumn());

			tblCheckoutBook.getColumns().clear();
			tblCheckoutBook.getColumns().addAll(titleCol, isbnCol,CopyNum, MemberName, dueDateCol,Status);

		} catch (LibrarySystemException ex) {
			tblCheckoutBook.setVisible(false);
			lblMessage.setTextFill(Color.RED);
			lblMessage.setText("Error : " + ex.getMessage());
		}
	}

}
