package cropncombine;

import java.io.File;

import processing.core.PApplet;
import processing.core.PImage;
import toxi.gui.GUIElement;
import toxi.gui.GUIManager;
import controlP5.*;

public class Tools {
	ControlP5 cp5;
	Slider2D s;
	CropNCombine p;
	int limit = 50;
	/*GUIManager gui;
	Tab tab;*/

	//path to pictures
	String targetFolder = "/Users/Gaston/Desktop/proc1/";

	private String[] fileList = new String[0];

	@GUIElement(label = "frameRate")
	public int frameRate;

	Tools(CropNCombine p5, String folderPath) {
		p = p5;
		frameRate = p.frameRate;
		targetFolder = folderPath;
	}

	public void setup() {
		PApplet.println("place pictures in : " + targetFolder);
	}

	void draw() {
	}

	//-------------------Controls Work

	//-------------------File Work

	String[] listFileNames(String dir) {
		File file = new File(dir);
		if (file.isDirectory()) {
			String names[] = file.list();
			String checked[] = new String[0];
			for(int i = 0; i<names.length; ++i)
			{
				if (PApplet.match(names[i], "((([a-zA-z0-9]|.|_)*.(jpeg|gif|jpg)))") != null) 
				{
					String[] tmp = PApplet.append(checked, names[i]);
					checked = tmp;
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
	public int refresh(boolean reset) {
		String[] fileNames = listFileNames(targetFolder);


		for (int i = 0; i < fileNames.length; i++) {
			if(p.img.length > 60)
				break;
			String fileName = fileNames[i];

			if(!contains(fileList, fileName))
			{
				if (fileName.endsWith("jpeg") || fileName.endsWith("jpg") || fileName.endsWith("gif") || fileName.endsWith("png")) {
					{
						
						int loadedImagesAmount = p.img.length;
						PImage ImageToAdd;

						String[] newList = PApplet.append(fileList, fileName);
						fileList = newList;
						ImageToAdd = p.loadImage(targetFolder + fileName);
						PImage[] img2 = (PImage[]) PApplet.append(p.img, ImageToAdd);
						p.img = img2;
						PApplet.println(fileName+ " added.");
					}
				}
			}
		}
		return fileList.length;
	}

	private boolean contains(String[] list, String test){
		boolean contains = false;
		for(int i = 0; i < list.length; i++)
		{

			if(list[i].equals(test)) 
			{
				return true;
			}
		}
		return false;
	}
}


