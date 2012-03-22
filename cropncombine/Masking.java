package cropncombine;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class Masking {
	CropNCombine p;
	PGraphics masks[];
	PImage imgs[];
	PImage resultImgs[];

	int nbImgs;
	int width, height;
	int totWidth = 480;
	int totHeight = 640;
	int offset = 0;
	int currentHeight;

	public Masking(int width, int height, CropNCombine p){
		this.width = width;
		this.height = height;
		this.p = p;
		this.masks = new PGraphics[nbImgs];
		this.resultImgs = new PImage[nbImgs];
	}
	
	public void setup() {
		this.imgs = p.img;
		nbImgs = imgs.length;
		if(nbImgs != 0)
			currentHeight = totHeight/nbImgs;
		//create arrays of masks
		this.masks = new PGraphics[nbImgs];
		this.resultImgs = new PImage[nbImgs];
		//load images
		

		for(int index = 0; index < nbImgs; index++)
		{
			//masks[index] = p.createGraphics(totWidth, totHeight, PGraphics.JAVA2D);
			masks[index] = updateMasks(0, currentHeight, index);
			resultImgs[index] = createMaskedImage(imgs[index], masks[index], index*currentHeight);
		}
	}

	public void update(int step) {

		for(int i = 0; i < imgs.length; ++i)
		{
			masks[i] = updateMasks(step, currentHeight, i);
			resultImgs[i] = createMaskedImage(imgs[i], masks[i], i*currentHeight);
		}
		if(offset+currentHeight > totHeight)
		{
			//	PImage tmp = im;

			//offset = 0;
		}
	}
	public void refresh()
	{
		imgs = p.img;
	}
	public PImage createMaskedImage(PImage img, PGraphics mask, int ypos)
	{
		img.mask(mask);
		return img;
	}

	public PGraphics updateMasks(int step, int height_, int index)
	{
		PGraphics mask = p.createGraphics(p.width, p.height, p.JAVA2D);
		int ypos = (offset + index*height_)%this.height;

		mask.beginDraw();
		mask.background(0);
		mask.fill(255);
		mask.rect(0, ypos, this.width, height_);
		if(ypos+height_ > this.height)
			mask.rect(0, 0, this.width, ypos+height_-this.height);
		mask.fill(255);

		mask.endDraw();
		offset += step;
		return mask;
	}
}
