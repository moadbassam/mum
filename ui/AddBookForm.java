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
		stage.setScene(new Scene(root, 900, 550));
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
