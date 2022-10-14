package application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception{
	
		Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
		Scene scene = new Scene(root);
		String css = this.getClass().getResource("/stylesheets/application.css").toExternalForm();
		scene.getStylesheets().add(css);
		
		FileController fileController = new FileController();
	
		primaryStage.setTitle("File Transfer FX");
		primaryStage.setScene(scene);
		primaryStage.setHeight(650);
		primaryStage.setWidth(1150);
		
		Image programIcon = new Image("images/fileTransferFXLogo.png");
		primaryStage.getIcons().add(programIcon);
		
		primaryStage.show();
			
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
