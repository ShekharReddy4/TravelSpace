package com.example.mygame;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

public class Ship {
	Bitmap bitmap;
	int x;
	int y;
	int screenWidth, screenHeight;
	float verticalSpeed;
	int speed;
	int img;
	boolean up;
	boolean death = false;
	ArrayList<Bitmap> list = new ArrayList<Bitmap>();

	float animationTime = 0;
	float totalAnimationTime = 1;
	float numFrames;

	public Ship(Bitmap decodeResource, int x, int y, int screenWidtd,
			int screenHeight,int shipSize) {
		this.bitmap = decodeResource.createScaledBitmap(decodeResource, shipSize/5,shipSize/5, true);
		this.x = x;
		this.y = y;
		this.screenWidth = screenWidtd;
		this.screenHeight = screenHeight;
		death = false;
		speed = 1;
		img = 0;
		verticalSpeed = 0;
	}

	public void setBoomAnimation(ArrayList<Bitmap> bitmap) {
		list = new ArrayList<Bitmap>(bitmap);
		numFrames = list.size();
	}

	void draw(Canvas canvas) {
		if (!death) {
			canvas.drawBitmap(bitmap, x - bitmap.getWidth() / 2,
					y - bitmap.getHeight() / 2, null);
		} else {
			int index = (int) (animationTime / totalAnimationTime * numFrames);
			if (index < numFrames) {
				canvas.drawBitmap(list.get(index), x - bitmap.getWidth() / 2, y
						- bitmap.getHeight() / 2, null);
			}
		}
	}

	void update(float dt) {
		if (death) {
			animationTime += dt;
		} else {
			verticalSpeed += screenHeight / 2 * dt;
			if (up) {
				verticalSpeed -= screenHeight * 2 * dt;
			}
			y += verticalSpeed * dt;
			if (y - (bitmap.getHeight() / 2) > screenWidth)
				y = 0 - (bitmap.getHeight() / 2);
		}
	}
	
	public boolean boom(Point otl,Point otr,Point olr,Point oll)
	{
		
		Point tl=new Point(),tr=new Point(),lr=new Point(),ll=new Point();
		ArrayList<Point> pointList=new ArrayList<Point>();
		pointList.add(otl);
		pointList.add(otr);
		pointList.add(olr);
		pointList.add(oll);
		getPoint(tl,tr,lr,ll);
		for (int i = 0; i < pointList.size(); i++) 
		{
			if(lr.x>=pointList.get(i).x)
				if(tl.x<=pointList.get(i).x)
					if(pointList.get(i).y>=tl.y)
						if(pointList.get(i).y<=lr.y)
							return true;
		}
		pointList.clear();
		pointList.add(tl);
		pointList.add(tr);
		pointList.add(lr);
		pointList.add(ll);
		for (int i = 0; i < pointList.size(); i++) 
		{
			if(olr.x>=pointList.get(i).x)
				if(otl.x<=pointList.get(i).x)
					if(pointList.get(i).y>=otl.y)
						if(pointList.get(i).y<=olr.y)
							return true;
		}
		
		return false;
	}

	private void getPoint(Point tl, Point tr, Point lr, Point ll) {
		// TODO Auto-generated method stub
		tl.x=x-bitmap.getWidth()/3;
		tl.y=y-bitmap.getHeight()/3;
		
		tr.x=x+bitmap.getWidth()/3;
		tr.y=y-bitmap.getHeight()/3;
		
		lr.x=x+bitmap.getWidth()/3;
		lr.y=y+bitmap.getHeight()/3;
		
		ll.x=x-bitmap.getWidth()/3;
		ll.y=y+bitmap.getHeight()/3;
		
	}

}
