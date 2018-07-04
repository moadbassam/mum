package ui;

import business.Book;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class EditBook extends Application {
	
	public static Stage primaryStage;
	@Override
	public void start(Stage stage) throws Exception {
		primaryStage = stage;
		Parent root = FXMLLoader.load(getClass().getResource("../FXML_Files/FXML_EditBook.fxml"));
		Book book = (Book) BooksForm.primaryStage.getUserData();
		System.out.println(book);

		 TextField txtISBN = (TextField) root.lookup("#isbn") ;
		
		 TextField txtTitle= (TextField) root.lookup("#title") ;
		
		 TextField txtAuthors= (TextField) root.lookup("#authors") ;
		 TextField txtMaxChkLength= (TextField) root.lookup("#chekout") ;
		
		 TextField txtNoOfCopy= (TextField) root.lookup("#numcompies") ;
		
		System.out.println(book);
		
		txtISBN.setText(book.getIsbn());
		txtTitle.setText(book.getTitle());
		txtAuthors.setText(book.getAuthors().toString());
		int length  = book.getMaxCheckoutLength();
		String max =   String.valueOf(length);
		txtMaxChkLength.setText(max);
		txtNoOfCopy.setText( String.valueOf(book.getNoOfCopies()));
		stage.setTitle("Edit Book Form");
		stage.setOnHiding(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						try {
							stage.hide();
							BooksForm.primaryStage = new Stage();
							BooksForm.setStage(new BooksForm());
							// UIController.goPrev(new BooksForm(),
							// UIController.prev);
						} catch (Exception e) {

							e.printStackTrace();
						}
					}
				});
			}
		});

		stage.setScene(new Scene(root, 900, 550));
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
