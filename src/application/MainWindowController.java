package application;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import viewmodel.ViewModel;

public class MainWindowController implements Initializable, Observer{
	
	// *********************** data members **************************** //
	
	// view model members
	ViewModel vm;
	// colorfulmap members
	@FXML
	ColorfulMapDisplayer colorfulMapDisplayer;
	File csv;
	// joystick members
	@FXML
	Circle circleFrame;
	@FXML
	Circle circleJoyStick;
	JoyStickDisplayer joyStickDisplayer;
	// text area members
	@FXML
	TextArea scriptTextArea;
	@FXML
	Button loadScript;
	@FXML
	Button run;
	// radio members
	@FXML
	RadioButton autopilot;
	@FXML
	RadioButton manual;
	ToggleGroup tg;
	// sliders members
	@FXML
	Slider rudderSlider;
	@FXML
	Slider throttleSlider;
	// to be sure we are connected and the script is on
	boolean isScriptLoaded=false;
	boolean isConnected=false;
	
	
	// ** Initialize method, (a part of the implementation), we can call it any time in our program in constant to the constructor ** // 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// joystick part //
		joyStickDisplayer = new JoyStickDisplayer(this);
		// scroll bars part //
		//colorfulMapDisplayer = new ColorfulMapDisplayer();
		
		// radio button part //
		this.tg = new ToggleGroup(); // responsible for the clicking right, when you clicked on one button, the other one turns off
		tg.getToggles().add(autopilot);
		tg.getToggles().add(manual);
	}
	
	public void updatedDirecrionJoyStick() {
		vm.controlElevatorAileronVm();
		}
    public void setViewModel(ViewModel vm) {
        this.vm = vm;
        vm.joyStickX.bind(circleJoyStick.centerXProperty());
        vm.joyStickY.bind(circleJoyStick.centerYProperty());
        vm.throttle.bind(throttleSlider.valueProperty());
        vm.rudder.bind(rudderSlider.valueProperty());
    }
	
	
	//  **********************************  Buttons Methods  *****************************************//
	
	
	// ** Connect button ** // 
	
	public void connect() {
		
		System.out.println("connected");
		//String[] ip = new String[1];
		//int[] port = new int[1];
		Stage popup = new Stage();
		VBox box = new VBox(20);
		Label ipLabel = new Label("Plese Enter Ip:");
		Label portLabel = new Label("Plese Enter Port:");
		TextField ipUserInput = new TextField();
		TextField portUserInput = new TextField();
		Button submit = new Button("Submit");
		box.getChildren().addAll(ipLabel,ipUserInput,portLabel,portUserInput, submit);
		popup.setScene(new Scene(box,350,250));
		popup.setTitle("Connect to FlightGear");
		popup.show();
		submit.setOnAction(e -> 
								{ System.out.println("Getting ip&port logic");
								  String ip = ipUserInput.getText(); // saving ip and port data!
								  String port = portUserInput.getText();
								  vm.connectToSimVM(ip, port);
								  popup.close();
								  // now we need to send it to the sockets...
								});
		isConnected = true;
	}
	
	
	
	
	// ** Load data button ** // 
	
	public void loadData() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Load data file"); // title of opening window
		fc.setInitialDirectory(new File("./resources")); // setting default directory
		
		// Enabling to open csv files ONLY
		FileChooser.ExtensionFilter csvExtensionFilter =
	           // new FileChooser.ExtensionFilter("CSV - Comma-separated values�? (.csv)", "*.csv");
				new FileChooser.ExtensionFilter("TXT - Text file (.txt)", "*.txt");
	    fc.getExtensionFilters().add(csvExtensionFilter);
	    fc.setSelectedExtensionFilter(csvExtensionFilter);
	    
		
		File chosen = fc.showOpenDialog(null); // getting a reference to the chosen file
		if(chosen != null) {
			System.out.println(chosen.getName()); // just for checking, printing the name of the chosen file
		}
		this.csv = chosen;
		colorfulMapDisplayer.csvReader(csv);
		colorfulMapDisplayer.redrawMap();
	}
	
	 
	// ** Radio buttons ** //
	
	public void manualClicked(){
		
		System.out.println("manual clicked");
		joyStickDisplayer.JoyStickFunctoinallity();
		loadScript.setDisable(true);
		run.setDisable(true);
		scriptTextArea.setDisable(true);
		rudderSlider.setDisable(false);
		throttleSlider.setDisable(false);
		
		}
	
	public void autopilotClicked(){
		
		System.out.println("autopilot clicked");
		joyStickDisplayer.disableJoyStick();
		loadScript.setDisable(false);
		run.setDisable(false);
		scriptTextArea.setDisable(false);
		rudderSlider.setDisable(true);
		throttleSlider.setDisable(true);
		}
	
	// ** loadScript button ** //
	
	public void loadScript() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Load Script"); // title of opening window
		fc.setInitialDirectory(new File("./resources")); // setting default directory
		
		// Enabling to open csv files ONLY
		FileChooser.ExtensionFilter csvExtensionFilter =
	            new FileChooser.ExtensionFilter("TXT - Text file (.txt)", "*.txt");
	    fc.getExtensionFilters().add(csvExtensionFilter);
	    fc.setSelectedExtensionFilter(csvExtensionFilter);
	    
		
		File chosen = fc.showOpenDialog(null); // getting a reference to the chosen file
		if(chosen != null) {
			System.out.println(chosen.getName()); // just for checking, printing the name of the chosen file
			
			  Scanner s;
			try {
				s = new Scanner(new FileReader(chosen)).useDelimiter("\n");
				String temp = null;
				while (s.hasNext()) {
					temp = s.next();
					scriptTextArea.appendText(temp+"\n");
					}
			} catch (FileNotFoundException e) {e.printStackTrace();}
		}
		isScriptLoaded = true;
		
	}
	
	
	// *** run button *** //
	
	public void run(){
        if(isConnected&&isScriptLoaded){
            vm.runScriptVm(scriptTextArea.getText());
         }
         else{
             if(!isConnected) {
                 popUpMassage("Please connect first");
             }
             if(!isScriptLoaded){
            	 popUpMassage("Please load script first");
             }
         }
     }
	
	
	// ** calculate button ** //
	
	public void calculatePath() {
		System.out.println("the shortest path is displayed.."); // BFS algorithm, due to matrix which was printed on the colorful map 
		Stage popup = new Stage();
		VBox box = new VBox(20);
		Label ipLabel = new Label("Plese Enter Ip:");
		Label portLabel = new Label("Plese Enter Port:");
		TextField ipUserInput = new TextField();
		TextField portUserInput = new TextField();
		Button submit = new Button("Submit");
		box.getChildren().addAll(ipLabel,ipUserInput,portLabel,portUserInput, submit);
		popup.setScene(new Scene(box,350,250));
		popup.setTitle("Calculate Path");
		popup.show();
		submit.setOnAction(e -> 
								{ System.out.println("Getting ip&port logic");
								  String ip = ipUserInput.getText(); // saving ip and port data!
								  String port = portUserInput.getText();
								  popup.close();
								  // now we need to send it to the sockets...
								  vm.connectToCalcServerVm(ip, port, colorfulMapDisplayer.matrix);
								});
		
	}
	
	//*** JoyStick ***//
	public void controlElevatorAileron() {
		vm.controlElevatorAileronVm();
	}
	
	//*** Sliders***//
	public void controlRudder() {
		vm.controlRudderVm();
	}
	
	public void controlThrottle() {
		vm.controlThrottleVm();
	}
	
    public void popUpMassage(String name){
        Stage popup = new Stage();
        VBox box = new VBox(10);
        Label msg = new Label(name);
        Button ok = new Button("ok");
        box.getChildren().addAll(msg, ok);
        popup.setScene(new Scene(box, 250, 100));
        popup.setTitle("Massage");
        popup.show();
        ok.setOnAction(e->{
            if(name.contains("script")) loadScript();
            if(name.contains("connect")) connect();
            popup.close();
        });
    }

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}




	
}
