package com.example.mygame;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class BariearManager {
	Bitmap bitmap;
	int shipHeight;
	ArrayList<Bariear> topWall = null;
	ArrayList<Bariear> bottomWall = null;
	int screnHeight;
	int num, dl, depth,target=-1;
	GameView gameView;
	public int gap=0;
	
	public BariearManager(Bitmap decodeResource, GameView gameview) {
		// TODO Auto-generated constructor stub
		this.bitmap = decodeResource;
		this.gameView = gameview;
	}

	public void setShipHeight(int height) {
		this.shipHeight = height;
	}

	public void setScreen(int width, int height) {
		dl=screnHeight = height;
		gap = (dl / 4 )+(dl / 2);
		bitmap=Bitmap.createScaledBitmap(bitmap, gap/6, gap/6, true);
		num = (width) / bitmap.getWidth() + 4;
		int h = bitmap.getHeight() / 2;
		depth = screnHeight / 2;
		int new_dl = screnHeight * 3 / 5;
		int ink = (dl - new_dl) / num;
		
		topWall = new ArrayList<Bariear>();
		bottomWall = new ArrayList<Bariear>();
		for (int i = -2; i < num + 1; i++) {
			dl = dl - ink;
			Bariear bb = new Bariear(bitmap, bitmap.getWidth()
					* i, 0);
				bb.setManager(this);
				bb.setY(depth - (dl / 2 + h*3));
			topWall.add(bb);
			
			Bariear bbb = new Bariear(bitmap, bitmap.getWidth()
					* i, 0);
			bbb.setManager(this);
			bbb.setY(depth + (dl / 4 - h*2));
			bottomWall.add(bbb);
			
		}
		
	}

	

	public void draw(Canvas canvas) {
		for (int i = 0; i <= num + 2; i++) {
			topWall.get(i).draw(canvas);
			bottomWall.get(i).draw(canvas);

		}
	}

	public void update(float ft) {
		for (int i = 0; i <= num + 2; i++) {
			topWall.get(i).update(ft, true);
			bottomWall.get(i).update(ft, false);
		}
	}
}
