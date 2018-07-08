package ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MemberCheckoutRecord extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/FXML_checkoutRecordUI.fxml"));

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

		stage.setTitle("Checkout Records by Member");
		Scene scene = new Scene(root, 600, 360);
		scene.getStylesheets().add(getClass().getResource("../Css/InnerForms.css").toExternalForm());

		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
