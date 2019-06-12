package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import model.myModel;
import viewmodel.ViewModel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			myModel model=new myModel(5404);
	        ViewModel vm=new ViewModel(model);
	        model.addObserver(vm);
			primaryStage.setTitle("FlightGear");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Window.fxml"));
			BorderPane root = (BorderPane)loader.load();
		    MainWindowController mwc = loader.getController();
		    mwc.setViewModel(vm);
		    vm.addObserver(mwc);
			Scene scene = new Scene(root,1350,500);
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
