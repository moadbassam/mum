package ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainForm extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	public static Stage primaryStage;

	@Override
	public void start(Stage mainStage) throws Exception {

		primaryStage = mainStage;
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/FXML_MainForm.fxml"));
		mainStage.setTitle("Main Form");

		mainStage.setOnHiding(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						try {
							if (primaryStage != mainStage)
								Platform.exit();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
		});

		Scene scene = new Scene(root, 900, 650);
		scene.getStylesheets().add(getClass().getResource("../Css/MainForm.css").toExternalForm());
		mainStage.setScene(scene);
		mainStage.show();
	}

	public static void setStage(Application form) throws Exception {
		form.start(primaryStage);
	}

}
