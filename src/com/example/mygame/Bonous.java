package com.example.mygame;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

public class Bonous {
	Bitmap bitmap;
	int x;
	int y;
	BariearManager bmgr;
	int size;

	public Bonous(Bitmap decodeResource, int i, int j,int size) {
		// TODO Auto-generated constructor stub
		bitmap = decodeResource.createScaledBitmap(decodeResource, size/8, size/8, true);
		x = i;
		y = j;
		this.size=size;
	}

	public void setBarriearManager(BariearManager b) {
		bmgr = b;
	}

	public Bitmap getBitmap() {
		return bitmap;
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

	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2),y - (bitmap.getHeight() / 2), null);

	}

	public void update(float dt) {
		if(x<=-100)
		{
			x=bmgr.gameView.scrWidth +bitmap.getWidth();
			Random r=new Random();
			y=r.nextInt(bmgr.dl)+bmgr.depth-bmgr.dl-10;
		}
		x-=bmgr.gameView.shipSpeed*dt;

	}

	public void setX(int i) {
		// TODO Auto-generated method stub
		this.x=i;
	}

	public void setY(int i) {
		this.y=i;
		// TODO Auto-generated method stub
		
	}
}
