package application;
import javafx.scene.shape.Circle;

public class JoyStickDisplayer{
	
	double maxRange; // hold the max range the smaller circle can move
	MainWindowController mwc;
	
	
	public JoyStickDisplayer(MainWindowController mwc) {
		this.mwc = mwc;
		this.maxRange = mwc.circleFrame.getRadius();
	}
	
	 // holds the center
	public void JoyStickFunctoinallity() {
		
		
		mwc.circleJoyStick.setOnMouseDragged((e)->{
			   double vectorLength=Math.sqrt(Math.pow(e.getX(), 2) + Math.pow(e.getY(), 2));
	            if (vectorLength <= maxRange) {
	            	mwc.circleJoyStick.setCenterX(e.getX());
	            	mwc.circleJoyStick.setCenterY(e.getY());
	            	mwc.updatedDirecrionJoyStick(); }
	            else {
	            	mwc.circleJoyStick.setCenterX(e.getX() * maxRange / vectorLength);
	            	mwc.circleJoyStick.setCenterY(e.getY() * maxRange / vectorLength);
	            	mwc.updatedDirecrionJoyStick(); }
	            });
		
			mwc.circleJoyStick.setOnMouseReleased(e -> {
			mwc.circleJoyStick.setCenterX(0);
			mwc.circleJoyStick.setCenterY(0);
			mwc.updatedDirecrionJoyStick();
	        });
	}
	
	
	
	public void disableJoyStick() {
		// disabling joystick to move
		mwc.circleJoyStick.setOnMouseDragged((e)-> { mwc.circleJoyStick.setCenterX(0.0); mwc.circleJoyStick.setCenterY(0.0); }); // printing it at the center only
		// here we need to close the socket which sends orders.
		
	}


	
}
