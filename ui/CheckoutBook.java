package ui;

import business.CheckoutRecordView;
import business.CheckoutRecordView.CheckoutRecordEntryView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class CheckoutBook extends Application {

	@FXML
	TableView<CheckoutRecordView.CheckoutRecordEntryView> tblCheckoutBook;

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/FXML_CheckoutBook.fxml"));
		tblCheckoutBook = (TableView<CheckoutRecordEntryView>) root.lookup("#tblCheckoutBook");
		tblCheckoutBook.setVisible(false);
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
		stage.setTitle("Checkout Book");
		stage.setScene(new Scene(root, 800, 360));
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
