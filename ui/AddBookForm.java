package ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AddBookForm extends Application {
	public static Stage primaryStage;
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/FXML_AddBook.fxml"));

		stage.setOnHiding(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						try {
							MainForm.setStage(new MainForm());
						} catch (Exception e) {

							e.printStackTrace();
						}
					}
				});
			}
		});

		stage.setTitle("Add Book");
		Scene scene = new Scene(root, 578, 330);
		scene.getStylesheets().add(getClass().getResource("../Css/InnerForms.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
