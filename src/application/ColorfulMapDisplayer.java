package application;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;


// this class represents the logic and presentation of the map. it doesn't hold the map itself! , the map will be defined in the main controller.
public class ColorfulMapDisplayer extends Canvas{
	
	// ** Data members ** //
	Image plane;
	double pCol,pRow; // the plane icon character
	double[][] matrix; 

	// ** Ctor ** // 
	
	public ColorfulMapDisplayer(){ 
		pCol=0; pRow=0; //init starting location
		plane=null;
		matrix=new double[10][10];
		setOnMouseClicked((e)->{ setPlanePosition(e.getSceneX()-40,e.getSceneY()-120);  System.out.println(e.getSceneX()+"-"+e.getSceneY());} ); // puts the icon in the position the mouse clicked.
		}  
	
	
	// ** Methods ** //
	public void setPlanePosition(double x, double y){
		try {plane = new Image(new FileInputStream("./resources/planeicon.png"));}
		catch (FileNotFoundException e) {e.printStackTrace();}
		GraphicsContext gc = getGraphicsContext2D();
		double W = getWidth();
		double H = getHeight();
		double w = W / matrix[0].length;
		double h = H / matrix.length;
		redrawMap();
		gc.drawImage(plane, x, y, 50, 50);
		
	}
	public double getcCol() {return pCol;}
	public void setcCol(int cCol) { this.pCol = cCol; }
	public double getcRow() { return pRow; }
	public void setcRow(int cRow) { this.pRow = cRow; }
	public double[][] getMatrix() { return matrix; }
	public void redrawMap() {
		if(matrix!=null) {
			double W = getWidth();
			double H = getHeight();
			double w = W / matrix[0].length;
			double h = H / matrix.length;
			GraphicsContext gc = getGraphicsContext2D(); // for printing things (ovals, lines, circles , etc..) on the canvas
			for(int i=0; i<matrix.length; i++) {
				for(int j=0; j<matrix[i].length; j++) { // as the number increases- the color gets more red (from green to red through yellow)
					gc.setEffect(new Shadow()); gc.setEffect(new Lighting()); 
					if(matrix[i][j] == 0 ) { gc.setFill(Color.LAWNGREEN) ;gc.fillRect(j*w, i*h, w, h);}
					if(matrix[i][j] == 1 ) { gc.setFill(Color.FORESTGREEN) ;gc.fillRect(j*w, i*h, w, h);}
					if(matrix[i][j] == 2 ) { gc.setFill(Color.GREEN) ;gc.fillRect(j*w, i*h, w, h);}
					if(matrix[i][j] == 3 ) { gc.setFill(Color.YELLOWGREEN) ;gc.fillRect(j*w, i*h, w, h);}
					if(matrix[i][j] == 4 ) { gc.setFill(Color.GREENYELLOW) ;gc.fillRect(j*w, i*h, w, h);}
					if(matrix[i][j] == 5 ) { gc.setFill(Color.GREENYELLOW) ;gc.fillRect(j*w, i*h, w, h);}
					if(matrix[i][j] == 6 ) { gc.setFill(Color.YELLOW) ;gc.fillRect(j*w, i*h, w, h);}
					if(matrix[i][j] == 7 ) { gc.setFill(Color.ORANGERED) ;gc.fillRect(j*w, i*h, w, h);}
					if(matrix[i][j] == 8 ) { gc.setFill(Color.RED) ;gc.fillRect(j*w, i*h, w, h);}
					if(matrix[i][j] == 9 ) { gc.setFill(Color.DARKRED) ;gc.fillRect(j*w, i*h, w, h);}
				}
			}
			for(int i=0; i<matrix.length;i++) {
				for(int j=0;j<matrix.length; j++) {
					gc.setFill(Color.BLACK); gc.fillText(String.valueOf(matrix[i][j]), i*w, j*h);
					System.out.println(matrix[i][j]+" ");
				}
			}
		}
	}

	
	public void csvReader(File csvfile) {
		Scanner s = null; // running along the CSV file
		try { s = new Scanner(csvfile).useDelimiter(","); }
		catch (FileNotFoundException e) { e.printStackTrace(); }
		for(int i=0; i<10; i++) {
			for(int j=0; j<10; j++) { // as the number increases- the color gets more red (from green to red through yellow)
				matrix[i][j] = Double.parseDouble(s.next());
			}
		}	
	}
//	public void solutionDrawer(SimpleStringProperty path) {
//		GraphicsContext gc = getGraphicsContext2D();
//		StringBuilder sb = new StringBuilder();
//		sb.append(path);
//		String[] tmp = sb.toString().split(",");
//		for(String s: tmp) {
//			switch(s) {
//			case "Down": Matrix
//			}
//			
//		}
//	}
}