package com.example.mygame;

import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.display.DisplayManager;
import android.media.MediaPlayer;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameActivity extends Activity {

	final static int update_score=0;
	final static int death=1;
	final static int lose=2;
	RelativeLayout main_Layout;
	View view, cont;
	ImageView contImg, mainImage;
	GameView gmeView;
	TextView txt;
	RelativeLayout rr;
	int width,height;
	int getCoin=0;
	int score=0;
	
	MediaPlayer mp;
	MediaPlayer mp1;
	MediaPlayer media;
	
	final Handler handler=new Handler(){
		
		 @Override
		public void handleMessage(Message msg) {
		
			 if(msg.what==update_score)
			 {
				 i_getCoin();
			 }
			 if(msg.what==death)
			 {
				 postDelayed(new Runnable() {
						
						@Override
						public void run() {
							Message msg=handler.obtainMessage();
							msg.what=lose;
							handler.sendMessage(msg);
						}
					}, 2000);
			 }
			 if(msg.what==lose)
			 {
				 i_loose();
			 }
			super.handleMessage(msg);
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
             WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_game);
		// get the main relative layout
		main_Layout = (RelativeLayout) findViewById(R.id.game_main_layout);

		// get screen width & height
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		height = dm.widthPixels;

		mp=MediaPlayer.create(GameActivity.this, R.raw.coin);
		media=MediaPlayer.create(GameActivity.this, R.raw.back);
		media.setVolume(0.3f, 0.3f);
		media.setLooping(true);
		
		  mp1=MediaPlayer.create(GameActivity.this, R.raw.bloop);
		
		
		// get the game view surface holder
		gmeView = new GameView(getApplicationContext(), this, width,height);
		main_Layout.addView(gmeView);
		
		rr=new RelativeLayout(this);
		rr.setBackgroundResource(R.drawable.sco);
		rr.setGravity(Gravity.CENTER);
		rr.setX(0);
		rr.setY(0);
		
		main_Layout.addView(rr,3*width/5,height/7);
		txt=new TextView(this);
		Typeface custom=Typeface.createFromAsset(getAssets(), "digital.ttf");
		txt.setTypeface(custom);
		txt.setGravity(Gravity.CENTER);
		txt.setTextColor(Color.MAGENTA);
		txt.setText("Score : "+score);
		txt.setTextSize(40);
		txt.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT));
		rr.addView(txt);
		
		rr.setOnClickListener(null);

		
		
		LayoutInflater inf = (LayoutInflater) getApplicationContext()
				.getSystemService(
						getApplicationContext().LAYOUT_INFLATER_SERVICE);

		// get pause menu
		view = inf.inflate(R.layout.pause, null, false);
		

		

		view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT));
		RelativeLayout pause = (RelativeLayout) view
				.findViewById(R.id.pause_layout);
		
		view.setY(0);
		main_Layout.addView(view);
		view.setX(width-100);
		pause.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cont.setVisibility(View.VISIBLE);
				view.setVisibility(View.GONE);
				rr.setVisibility(View.GONE);
				gmeView.game_pause=true;
			}
		});

		// get the continue & main .xml
		cont = inf.inflate(R.layout.pause_continue, null, false);
		main_Layout.addView(cont);
		cont.getLayoutParams().width=width;
		cont.getLayoutParams().height=height;
		cont.setVisibility(View.GONE);
		// get continue img
		contImg = (ImageView)cont.findViewById(R.id.imageView1);
		// get main menu img
		mainImage = (ImageView)cont.findViewById(R.id.imageView2);
		mainImage.setOnTouchListener(new android.view.View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(!mp1.isPlaying())
	    			mp1.start();
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					mainImage.setImageResource(R.drawable.co1);
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					mainImage.setImageResource(R.drawable.co);
				}
				return false;
				
			}
		});
		mainImage.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
			// On main menu Click
				gmeView.thread.status=false;
				GameActivity.this.finish();

			}
		});
		contImg.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// On continue menu Click
				cont.setVisibility(View.GONE);
				view.setVisibility(View.VISIBLE);
				rr.setVisibility(View.VISIBLE);
				gmeView.game_pause=false;
			}
		});
		
		
		contImg.setOnTouchListener(new android.view.View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(!mp1.isPlaying())
	    			mp1.start();
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					contImg.setImageResource(R.drawable.co1);
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					contImg.setImageResource(R.drawable.co);
					
				}
				return false;
				
			}
		});
	}


	
	protected void i_loose() {
		// TODO Auto-generated method stub
		view.setVisibility(View.GONE);
		rr.setX(0);
		rr.setY(2*height/9);
		rr.setLayoutParams(new RelativeLayout.LayoutParams(width,2*height/9));
		txt.setTextSize(60);
		txt.setGravity(Gravity.CENTER);
		txt.setText("Score : "+score);
		txt.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT));
		rr.setOnClickListener(new android.view.View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gmeView.thread.status=false;
				GameActivity.this.finish();
				
			}
		});
		
	}

	

	protected void i_getCoin() {
		// TODO Auto-generated method stub
		if (mp.isPlaying()) {
			mp.stop();
		}
		
		getCoin++;
		score+=20;
		txt.setText("Score : "+score);
		
		mp.start();

	}

@Override
protected void onPause() {
	// TODO Auto-generated method stub
	gmeView.thread.status=false;
	GameActivity.this.finish();
	super.onPause();
}
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	if(!media.isPlaying())
		media.start();
}

@Override
protected void onStop() {
	// TODO Auto-generated method stub
	if(media.isPlaying())
		media.stop();
	super.onStop();
}
@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		cont.setVisibility(View.VISIBLE);
		view.setVisibility(View.GONE);
		gmeView.game_pause=true;
	}
@Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	gmeView.thread.status=false;
	super.onDestroy();
}



	
	
}
