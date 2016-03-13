package com.example.mygame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class BackGround {
	Bitmap bitmap;
	int x, y;
	int screen_width;
	int screen_hight;
	int count_back;
	GameView gameView;

	public BackGround(Bitmap bitmap, int screen_width,int height, GameView gameView) {
		
		x = y = 0;
		this.screen_width = screen_width;
		this.screen_hight=height;
		this.bitmap = bitmap.createScaledBitmap(bitmap, screen_width, screen_hight, true);
		count_back = screen_width / bitmap.getWidth() + 1;
		this.gameView = gameView;
	}

	void draw(Canvas canvas) {
		for (int i = 0; i < count_back + 1; i++) {
			if (canvas != null) {
				canvas.drawBitmap(bitmap, bitmap.getWidth() * i + x, y, null);
				
			
			}
			if (Math.abs(x) > bitmap.getWidth()) {
				x = x + bitmap.getWidth();
			}

		}

	}

	void update(float dt) {
		x =(int)(x - gameView.shipSpeed * dt);
	}

}
