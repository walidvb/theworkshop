package cropncombine;



import java.io.File;


import processing.core.PApplet;
import processing.core.PImage;


public class CropNCombine extends PApplet {
	
	PImage img[] = new PImage[3];
	PImage render;
	int totHeight = 450;
	int totWidth = 450;
	boolean draw_ = true;

	public void setup() {

		size(totWidth, totHeight);
		String path = "/Users/Gaston/Desktop/proc/";
		String[] fileNames = listFileNames(path);
		int loadedID = 0;
		for(int i = 0; i < fileNames.length; i++)
		{
			String fileName =  fileNames[i];
			println("b4: "+i+" " + fileName);

			if(match(fileName, "((([a-zA-z0-9].)*.(jpeg|gif|jpg)))") != null)
			{
				println(loadedID+" "+path + fileName);
				img[loadedID] = loadImage(path+fileName);
				++loadedID;
			}
		}
	}

	public void draw() {

		if(draw_)
		{
			PImage tmpimg = createImage(totWidth, totHeight, RGB);
			int n = img.length;
			//loop over img[] and take the 1/n percent of each image
			for(int i = 0; i < n; i++)
			{

				int height = totHeight/(n);
				int offset = i*height;

				tmpimg.copy(img[i], 0, offset, totWidth, height, 0, offset, totWidth, height); 
			}
			image(tmpimg, 0, 0);
			draw_=false;
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
}

