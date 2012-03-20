package cropncombine;

import java.io.File;
import processing.core.PApplet;
import processing.core.PImage;
import sojamo.drop.SDrop;
import toxi.gui.GUIElement;
import toxi.gui.GUIManager;
import toxi.gui.IntRangeBuilder;
import controlP5.*;

public class Tools {

	ControlP5 cp5;
	Slider2D s;
	CropNCombine p;
	GUIManager gui;
	Tab tab;
	private int loadedID = 0;

	  
		
		@GUIElement(label = "frameRate")
		public int frameRate;
		
		
		IntRangeBuilder asd;
	Tools(CropNCombine p5){
		p = p5;
		frameRate = p.frameRate;
	}
	

	public void setup() {
		initGUI();
	}

	void draw() {
	}

	private void initGUI() {
		ControlP5 cp5 = new ControlP5(p);
		tab=cp5.addTab("misc");
		gui = new GUIManager(cp5);
		gui.createControllers(p, 20, 20, "Control");
		for (int i = 0; i < cp5.getControllerList().length; i++) 
		{
			cp5.getControllerList()[i].setColorLabel(0);

		}

	}

	String[] listFileNames(String dir) {
		File file = new File(dir);
		if (file.isDirectory()) {
			String names[] = file.list();
			return names;
		} else {
			// If it's not a directory
			return null;
		}
	}

	public void populate(boolean reset){
		String path = "/Users/Gaston/Desktop/proc/";
		String[] fileNames = listFileNames(path);
		if(reset)
			loadedID = 0;
		for(int i = 0; i < fileNames.length; i++)
		{
			String fileName =  fileNames[i];
			if(p.match(fileName, "((([a-zA-z0-9].)*.(jpeg|gif|jpg)))") != null)
			{
				p.img[loadedID] = p.loadImage(path+fileName);
				++loadedID;
			}
		}
	}
}
