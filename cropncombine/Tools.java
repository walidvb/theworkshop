package cropncombine;

import java.io.File;

import processing.core.PApplet;
import processing.core.PImage;
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
	private String fileList[] = new String[0];
	
	@GUIElement(label = "frameRate")
	public int frameRate;

	Tools(CropNCombine p5) {
		p = p5;
		frameRate = p.frameRate;
	}

	public void setup() {
		initGUI();
	}

	void draw() {
	}

	public void initGUI() {
		ControlP5 cp5 = new ControlP5(p);

		gui = new GUIManager(cp5);
		gui.createControllers(p, 20, 20, "Control");
		for (int i = 0; i < cp5.getControllerList().length; i++) {
			cp5.getControllerList()[i].setColorLabel(0);

		}

	}

	String[] listFileNames(String dir) {
		File file = new File(dir);
		if (file.isDirectory()) {
			String names[] = file.list();
			String checked[] = new String[0];
			for(int i = 0; i<names.length; ++i)
			{
				if (PApplet.match(names[i], "((([a-zA-z0-9]|.|_)*.(jpeg|gif|jpg)))") != null) 
				{
					PApplet.append(checked, names[i]);
				}
			}
			return checked;
		} else {
			// If it's not a directory
			return null;
		}
	}

	//Method to refresh what's in the folder, 
	//only adding files not present earlier.
	public void refresh(boolean reset) {
		String path = p.targetFolder;
		String[] fileNames = listFileNames(path);
		
		if (reset)
			loadedID = 0;
		p.println(path + ": " + fileNames.length);

		for (int i = 0; i < fileNames.length; i++) {

			String fileName = fileNames[i];
			if(fileList.contains(fileName))
			if (p.match(fileName, "((([a-zA-z0-9]|.|_)*.(jpeg|gif|jpg)))") != null) {
				{

					int loadedImagesAmount = p.img.length;
					PImage ImageToAdd;

					String imageName = fileNames[p.img.length + 1];
					ImageToAdd = p.loadImage(p.targetFolder + imageName);

					PImage[] img2 = (PImage[]) PApplet.append(p.img, ImageToAdd);
					p.img = img2;
					PApplet.println("this is img length:" + p.img.length);
				}
			}
		}
	}
}
