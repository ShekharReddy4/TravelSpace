package com.example.mygame;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

	MyThread thread;
	int shipHeight;
	public boolean game_pause;
	private BackGround background;
	public float shipSpeed;
	public Ship ship;
	private BariearManager bm;
	private Bonous oil;
	public int scrWidth;
	public int scrHeight;
	GameActivity game;
	boolean fst = false;

	public GameView(Context context, GameActivity game, int screenWidth,
			int screenHeight) {
		super(context);
		this.game = game;
		getHolder().addCallback(this);
		thread = new MyThread(getHolder(), this);
		setFocusable(true);
		scrWidth = screenWidth;
		scrHeight = screenHeight;
		shipSpeed = screenWidth / 2f;
		background = new BackGround(BitmapFactory.decodeResource(
				getResources(), R.drawable.pic), screenWidth, screenHeight,
				this);
		bm = new BariearManager(BitmapFactory.decodeResource(getResources(),
				R.drawable.bar), GameView.this);
		bm.setScreen(screenWidth, screenHeight);
		shipHeight = bm.gap;
		ship = new Ship(BitmapFactory.decodeResource(getResources(),
				R.drawable.qq), 100, screenHeight / 5, screenWidth,
				screenHeight, shipHeight);
		ArrayList<Bitmap> anim = new ArrayList<Bitmap>();
		anim.add(BitmapFactory.decodeResource(getResources(), R.drawable.boom1));
		anim.add(BitmapFactory.decodeResource(getResources(), R.drawable.boom2));
		anim.add(BitmapFactory.decodeResource(getResources(), R.drawable.boom3));
		anim.add(BitmapFactory.decodeResource(getResources(), R.drawable.boom4));
		anim.add(BitmapFactory.decodeResource(getResources(), R.drawable.boom5));
		ship.setBoomAnimation(anim);
		oil = new Bonous(BitmapFactory.decodeResource(getResources(),
				R.drawable.coin), -200, -200,shipHeight);
		oil.setBarriearManager(bm);

		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (fst == false) {
			game_pause = false;
			fst = true;
		} else {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				ship.up = true;
			}
			if (event.getAction() == MotionEvent.ACTION_UP) {
				ship.up = false;
			}
		}
		return true;
	}

	void Draw(Canvas canvas) {
		if (!game_pause) {
			if (canvas != null) {
				background.draw(canvas);
				ship.draw(canvas);
				bm.draw(canvas);
				oil.draw(canvas);
			}
		}

	}

	void update(float tt) {

		ship.update(tt);

		if (!ship.death) {
			background.update(tt);
			bm.update(tt);
			oil.update(tt);

			ArrayList<Point> oil_score = new ArrayList<Point>(oil.getArray());
			if (ship.boom(oil_score.get(0), oil_score.get(1), oil_score.get(2),
					oil_score.get(3))) {
				oil.setX(-200);
				oil.setY(-200);
				Message msg = game.handler.obtainMessage();
				msg.what = game.update_score;
				game.handler.sendMessage(msg);
			}
			for (int i = 0; i < bm.topWall.size(); i++) {
				ArrayList<Point> temp = new ArrayList<Point>(bm.topWall.get(i)
						.getArray());
				ArrayList<Point> temp1 = new ArrayList<Point>(bm.bottomWall
						.get(i).getArray());

				if ((ship.boom(temp.get(0), temp.get(1), temp.get(2),
						temp.get(3)))
						|| (ship.boom(temp1.get(0), temp1.get(1), temp1.get(2),
								temp1.get(3)))) {
					ship.death = true;
					if (game.media.isPlaying())
						game.media.stop();
					MediaPlayer mp = MediaPlayer.create(game, R.raw.boo);
					mp.start();
					Message msg = game.handler.obtainMessage();
					msg.what = game.death;
					game.handler.sendMessage(msg);

				}

			}
		}

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		thread.setStatus(true);
		thread.start();
		try {
			Thread.sleep(200);
		} catch (Exception e) {
			// TODO: handle exception
		}
		game_pause = true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

}
