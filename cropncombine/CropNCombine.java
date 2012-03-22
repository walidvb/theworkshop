package cropncombine;

import fullscreen.FullScreen;
import processing.core.PApplet;
import processing.core.PImage;

public class CropNCombine extends PApplet {


	final int PAUSE = 0;
	final int FWD = 1;
	final int BCK = 2;
	final int SETUP = 50;
	final int STATES = 3;
	FullScreen fs; 


	public int frameRate = 10;
	private static final long serialVersionUID = 1L;
	public PImage img[] = new PImage[0];
	public int dir = 1;
	private int state = FWD;
	private int imageAmount;
	Tools tool;
	Masking masks;
	int imageToDraw = 0;

	public boolean loadNext = false;

	public void setup() {

		size(480, 640);
		String folderPath = selectFolder("Choose the folder where your pictures will be stored") + "/";
		tool = new Tools(this, folderPath);
		imageAmount = tool.refresh(true);
		println(imageAmount);
		masks = new Masking(this.width, this.height, this);
		masks.setup();
		tool.setup();

		img = new PImage[0];

	}

	public void draw() {
		background(0);

		switch(state)
		{
		case PAUSE: 
			break;
		case FWD:
			masks.update(dir);
			break;
		}

		for(PImage result : masks.resultImgs)
			image(result, 0, 0);
		button(PAUSE, 10, 10);
	}

	public void mousePressed() {
		//if (overRect(totWidth - 30, totHeight - 30, 20, 20)) 
		{
			state = (state + 1) % STATES;
		}
	}

	// ----------------------functions
	public void keyReleased()
	{
		if(key == ' ' || key== 'r')
		{

			PApplet.println("refreshing");
			tool.refresh(true);
			masks.setup();
		}
	}
	// ---------Shapes

	boolean overRect(int x, int y, int width, int height) {
		if (mouseX >= x && mouseX <= x + width && mouseY >= y
				&& mouseY <= y + height) {
			return true;
		} else {
			return false;
		}
	}

	// ---------Utils

	void button(int state, int xpos, int ypos) {
		int butWidth = 20;
		int butHeight = 20;
		xpos -= (1.5 * butWidth);
		ypos -= (1.5 * butHeight);
		rect(xpos, ypos, 20, 20);
		fill(255, 255, 255);

		int x1, y1, x2, y2, x3, y3;

		switch (state) {
		case PAUSE:
			int pauseWidth = butWidth / 4;
			int pauseHeight = butHeight - 10;
			int xpos_ = xpos + butWidth / 6;
			int xpos2_ = xpos_ + pauseWidth + butWidth / 6;
			int ypos_ = ypos + 5;
			rect(xpos_, ypos_, pauseWidth, pauseHeight);
			rect(xpos2_, ypos_, pauseWidth, pauseHeight);
			break;
		case FWD:
			x1 = xpos + 5;
			y1 = ypos + 5;
			x2 = xpos + 5;
			y2 = ypos + butHeight - 5;
			x3 = xpos + butWidth - 5;
			y3 = ypos + butHeight / 2;
			triangle(x1, y1, x2, y2, x3, y3);
			break;
		case BCK:
			x1 = xpos + butWidth - 5;
			y1 = ypos + 5;
			x2 = xpos + butWidth - 5;
			y2 = ypos + butHeight - 5;
			x3 = xpos + 5;
			y3 = ypos + butHeight / 2;
			triangle(x1, y1, x2, y2, x3, y3);
			break;
		default:
			break;
		}
		fill(0, 0, 0);

	}

}