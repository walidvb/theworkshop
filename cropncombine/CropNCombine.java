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

	
	public int frameRate = 30;
	private static final long serialVersionUID = 1L;
	public PImage img[];
	private int totHeight = 450;
	private int totWidth = 450;
	private int step = 0;
	public int dir = 0;
	private int state = 3;
	private int imageAmount;
	Tools tool;

	int imageToDraw = 0;

	public boolean loadNext = false;

	public void setup() {

		size(450, 450);
		String folderPath = selectFolder("Choose the folder where your pictures will be stored") + "/";
		tool = new Tools(this, folderPath);
		// controls.populate(true);
		tool.setup();

		img = new PImage[0];
		imageAmount = tool.refresh(true);
		/*fs = new FullScreen(this);
		fs.enter();
		fs.setShortcutsEnabled(true);
*/
	}

	public void draw() {
		background(0);

		switch (state) {
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

		// draw_=false; button(state, totWidth, totHeight);

		tool.draw();
		button(PAUSE, 10, 10);
	}

	public void mousePressed() {
		//if (overRect(totWidth - 30, totHeight - 30, 20, 20)) 
		{
			state = (state + 1) % STATES;
		}
	}

	// ----------------------functions
	// ---------Horizontal Stripes
	void horStripes(int move) {
		PImage tmpimg = createImage(totWidth, totHeight, G);

		imageAmount = img.length;
		int height = totHeight / (imageAmount);
		int fill = 0;
		int fillOffset = 0;

		switch (FWD) {
		case FWD:
			// make part appear at the beginning
			fill = (imageAmount - 1 - step > 0)? (imageAmount - 1 - step): 0;
			fillOffset = 0;
			break;
		case BCK:
			fill = step;
			fillOffset = (imageAmount) * height + dir;
			break;
		}
		tmpimg.copy(img[fill], 0, fillOffset, totWidth, dir, 0, fillOffset,
				totWidth, dir);
		// loop over img[] and take the 1/n percent of each image
		for (int i = 0; i < imageAmount; i++) {
			int pos = (i + step) % (imageAmount);
			int offset = pos * height + dir;

			tmpimg.copy(img[i], 0, offset, totWidth, height, 0, offset,
					totWidth, height);

			// Make 'em move and return to beginning
			if (offset > totHeight) {
				step = (step + 1) % imageAmount;
				dir = 0;
			} else if (offset < 0) {
				step = (step + 1) % imageAmount;
				dir = 0;
			}
		}
		dir += move;
		image(tmpimg, 0, 0);
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

	public void keyReleased() {
		if (key == 'n')
			imageAmount = tool.refresh(true);
		if (key == 'f')
		if (key == 'm')
			imageToDraw++;
	}
	
}
