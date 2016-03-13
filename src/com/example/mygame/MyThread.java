package com.example.mygame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MyThread extends Thread {
	private SurfaceHolder holder;
	private GameView gameView;
	boolean status;
	float tt;

	public MyThread(SurfaceHolder holder, GameView gameView) {
		// TODO Auto-generated constructor stub
		this.holder = holder;
		this.gameView = gameView;
		tt = 0;

	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Canvas canvas;
		while (status) {
			if (!gameView.game_pause) {
				long startTime = System.currentTimeMillis();
				canvas = null;
				try {
					canvas = this.holder.lockCanvas();
					synchronized (holder) {
						gameView.update(tt);
						gameView.Draw(canvas);

					}
				} finally {
					if(canvas!=null)
					{
						this.holder.unlockCanvasAndPost(canvas);
					}
				}

				long endTime = System.currentTimeMillis();
				tt = (endTime - startTime) / 1000f;
			}
		}
	}

}
