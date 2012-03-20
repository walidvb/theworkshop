package cropncombine;



import java.io.File;

import controlP5.ControlP5;

import processing.core.PApplet;
import processing.core.PImage;


public class CropNCombine extends PApplet {
	final int PAUSE = 0;
	final int FWD = 1;
	final int BCK = 2;
	final int SETUP = 3;
	final int STATES = 3;
	/**
	 * 
	 */
	public int frameRate = 30;
	private static final long serialVersionUID = 1L;
	public PImage img[] = new PImage[4];
	private int totHeight = 450;
	private int totWidth = 450;
	private boolean draw_ = true;
	private int loadedID = 0;
	private int step = 0;
	public int dir = 0;
	private int state = 3;
	private int n;

    Tools controls = new Tools(this);

	public void setup() {
		frameRate(frameRate);

		size(totWidth, totHeight);
		controls.populate(true);
		controls.setup();
	}

	public void draw() {
		controls.draw();
		switch(state)
		{
		case SETUP:
			break;
		case PAUSE:
			horStripes(0);
			break;
		case FWD:
			horStripes(1);
			break;
		case BCK:
			horStripes(-1);
			break;
		
		}
		//draw_=false;
		button(state, totWidth, totHeight);
	}

	public void mousePressed()
	{
		if(overRect(totWidth-30, totHeight-30, 20, 20)) {
			state = (state+1)%STATES;
			println(state);
		}
	}





	//----------------------functions
	//---------Horizontal Stripes
	void horStripes(int move){
		PImage tmpimg = createImage(totWidth, totHeight, RGB);

		n = img.length;
		int height = totHeight/(n);
		//make part appear at the beginning
		int first = (n-1-step);
		tmpimg.copy(img[first], 0, 0, totWidth, dir, 0, 0, totWidth, dir);

		//loop over img[] and take the 1/n percent of each image
		for(int i = 0; i < n; i++)
		{
			int pos = (i+step)%(n);
			int offset = pos*height+dir;

			tmpimg.copy(img[i], 0, offset, totWidth, height, 0, offset, totWidth, height);

			//Make 'em move and return to beginning
			if(offset > totHeight)
			{
				step = (step+1)%n;
				dir=0;
			}
		}
		dir+=move;
		image(tmpimg, 0, 0);
	}
	//---------Shapes
	
	
	boolean overRect(int x, int y, int width, int height) 
	{
		if (mouseX >= x && mouseX <= x+width && 
				mouseY >= y && mouseY <= y+height) {
			return true;
		} else {
			return false;
		}
	}
	//---------Utils
	
	void button(int state, int xpos, int ypos)
	{
		int butWidth = 20;
		int butHeight = 20;
		xpos -= (1.5*butWidth);
		ypos -= (1.5*butHeight);
		rect(xpos, ypos, 20, 20);
		fill(255,255,255);

		int  x1, y1, x2, y2, x3, y3;

		switch(state)
		{
		case PAUSE:
			int pauseWidth = butWidth/4;
			int pauseHeight = butHeight-10;
			int xpos_ = xpos + butWidth/6;
			int xpos2_ = xpos_ + pauseWidth + butWidth/6;
			int ypos_ = ypos + 5;
			rect(xpos_, ypos_, pauseWidth, pauseHeight);
			rect(xpos2_, ypos_, pauseWidth, pauseHeight);
			break;
		case FWD:
			x1 = xpos+5;
			y1 = ypos+5;
			x2 = xpos+5;
			y2 = ypos+butHeight-5;
			x3 = xpos+butWidth-5;
			y3 = ypos+butHeight/2;
			triangle(x1, y1, x2, y2, x3, y3);
			break;
		case BCK:
			x1 = xpos+butWidth-5;
			y1 = ypos+5;
			x2 = xpos+butWidth-5;
			y2 = ypos+butHeight-5;
			x3 = xpos+5;
			y3 = ypos+butHeight/2;
			triangle(x1, y1, x2, y2, x3, y3);
			break;
		default:
			break;
		}
		fill(0,0,0);

	}

}

