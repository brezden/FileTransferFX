package application;
	
import javax.swing.Icon;
import javax.swing.ImageIcon;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception{
	
		Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("File Transfer FX");
		primaryStage.setScene(scene);
		
		Image programIcon = new Image("images/fileTransferFXLogo.png");
		primaryStage.getIcons().add(programIcon);
		
		primaryStage.show();
			
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
