package com.example.mygame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

public class Bariear {

	Bitmap bitmap;
	int x, y;
	BariearManager bm;
	boolean doIt;

	public Bariear(Bitmap bitmap, int i, int j) {
		// TODO Auto-generated constructor stub
		this.bitmap=bitmap;
		x = i;
		y = j;
	}

	public void setManager(BariearManager bariearManager) {
		// TODO Auto-generated method stub
		bm = bariearManager;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setY(int i) {
		// TODO Auto-generated method stub
		this.y = i;
	}

	
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2),
				y - (bitmap.getHeight() / 2), null);
	}

	public void update(float ft, boolean b) {
		// TODO Auto-generated method stub
		if (x <= bitmap.getWidth() * -2) {
			if (b) {
				if (Math.abs(bm.target - bm.depth) < 50) {
					doIt = true;
				}
				if (bm.target == -1 || doIt) {
					bm.target = new Random()
							.nextInt(bm.screnHeight - bm.dl / 2) + bm.dl / 4;
				}
				if (bm.depth < bm.target) {
					bm.depth = bm.depth + new Random().nextInt(15);
				} else {
					bm.depth = bm.depth - new Random().nextInt(15);
				}

				y = bm.depth - (bm.dl / 2 + bitmap.getHeight()*3/2);
			} else {
				y = bm.depth + (bm.dl / 4 - bitmap.getHeight());
			}
			x = (int) (x + bitmap.getWidth() * (bm.topWall.size()) + 1);
		}
		x = (int) (x - bm.gameView.shipSpeed * ft);
	}

	ArrayList<Point> getArray() {
		Point tl = new Point(), tr = new Point(), lr = new Point(), ll = new Point();
		tl.x = x - bitmap.getWidth() / 3;
		tl.y = y - bitmap.getHeight() / 3;

		tr.x = x + bitmap.getWidth() / 3;
		tr.y = y - bitmap.getHeight() / 3;

		lr.x = x + bitmap.getWidth() / 3;
		lr.y = y + bitmap.getHeight() / 3;

		ll.x = x - bitmap.getWidth() / 3;
		ll.y = y + bitmap.getHeight() / 3;

		ArrayList<Point> ar = new ArrayList<Point>();
		ar.add(tl);
		ar.add(tr);
		ar.add(lr);
		ar.add(ll);
		return ar;
	}

}
