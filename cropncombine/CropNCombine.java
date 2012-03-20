package cropncombine;



import java.io.File;


import processing.core.PApplet;
import processing.core.PImage;


public class CropNCombine extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PImage img[] = new PImage[3];
	private PImage render;
	private int totHeight = 450;
	private int totWidth = 450;
	private boolean draw_ = true;
	private int loadedID = 0;
	private int step = 0;
	private int r;
	private int state;
	private int n;


	public void setup() {
		frameRate(60);

		size(totWidth, totHeight);
		populate(true);
	}

	public void draw() {
		if(draw_)
		{
			//update the img list
			//populate(true);
			PImage tmpimg = createImage(totWidth, totHeight, RGB);
			n = img.length;
			int height = totHeight/(n);
			//make part appear at the beginning
			int first = (n-1-step);
			tmpimg.copy(img[first], 0, 0, totWidth, r, 0, 0, totWidth, r);

			//loop over img[] and take the 1/n percent of each image
			for(int i = 0; i < n; i++)
			{



				int pos = (i+step)%(n);
				int offset = pos*height+r;


				tmpimg.copy(img[i], 0, offset, totWidth, height, 0, offset, totWidth, height);

				//Make 'em move and return to beginning
				if(offset > totHeight)
				{
					step = (step+1)%n;
					r=0;
				}
			}

			image(tmpimg, 0, 0);
		}
		//something to be done with this...
		switch(state)
		{
		//FWD
		case 0:
			break;
		case 1:
			r+=1;
			break;
		case 2:
			r-=1;
			break;
		default:
			break;
		}
		//draw_=false;
		int butSize=20;
		rect(totWidth-30, totHeight-30, butSize, butSize);
		fill(0,0,0);
		shape(1, 10, 10);
	}

	public void mousePressed()
	{
		if(overRect(totWidth-30, totHeight-30, 20, 20)) {
			state = (state+1)%n;
		}
	}

	boolean overRect(int x, int y, int width, int height) 
	{
		if (mouseX >= x && mouseX <= x+width && 
				mouseY >= y && mouseY <= y+height) {
			return true;
		} else {
			return false;
		}
	}



	//----------------------functions

	//---------Shapes
	public void shape(int shape, int xpos, int ypos){
		switch(shape)
		{
		case 2:
			beginShape(TRIANGLES);
			vertex(30, 75);
			vertex(40, 20);
			vertex(50, 75);
			vertex(60, 20);
			vertex(70, 75);
			vertex(80, 20);
			endShape();
			fill(0,0,0);
		}

	}

	//---------Utils
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

