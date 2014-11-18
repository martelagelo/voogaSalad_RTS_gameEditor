package application;
	
import game_editor.GUIPaneGenerator;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			GUIPaneGenerator gPG = new GUIPaneGenerator();
			GridPane splash = gPG.generateGridPane("GUIPanes/SplashPage.fxml", 0, 0);
			Scene scene = new Scene(splash);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
