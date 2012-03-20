package cropncombine;



import java.io.File;


import processing.core.PApplet;
import processing.core.PImage;


public class CropNCombine extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PImage img[] = new PImage[3];
	PImage render;
	int totHeight = 450;
	int totWidth = 450;
	boolean draw_ = true;
	int loadedID = 0;
	int step = 0;
	int r;
	
	public void setup() {
		  frameRate(10);

		size(totWidth, totHeight);
		populate(true);
	}

	public void draw() {
		
		if(draw_)
		{

			//update the img list
			//populate(true);
			PImage tmpimg = createImage(totWidth, totHeight, RGB);
			int n = img.length;
			int height = totHeight/(n);
			//loop over img[] and take the 1/n percent of each image
			for(int i = 0; i < n; i++)
			{
				int pos = (i+step)%(n);
				int offset = pos*height+r;
				tmpimg.copy(img[i], 0, offset, totWidth, height, 0, offset, totWidth, height);
				
				println(step + " pos: " + pos + " offset: " + offset + " tot: " + totHeight);
				if(offset > totHeight)
				{
					//println(step + " pos: " + pos + "offset " + offset + " tot" + totHeight);
					step = (step+1)%n;
					r=0;
				}
			}
			image(tmpimg, 0, 0);
			//something to be done with this... 
			r+=1;
			//draw_=false;
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

			if(match(fileName, "((([a-zA-z0-9].)*.(jpeg|gif|jpg)))") != null)
			{
				img[loadedID] = loadImage(path+fileName);
				++loadedID;
			}
		}
	}

}

