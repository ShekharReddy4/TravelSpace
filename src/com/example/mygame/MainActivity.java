package com.example.mygame;

import android.support.v7.app.ActionBarActivity;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends Activity {
	RelativeLayout inerRelative;
	RelativeLayout mainLayout;
	ImageView imgView;
	TextView startText;
	MediaPlayer mp;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	 
    	super.onCreate(savedInstanceState);
    	 requestWindowFeature(Window.FEATURE_NO_TITLE);
         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
             WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        mainLayout=(RelativeLayout)findViewById(R.id.mainScreen);
        inerRelative=(RelativeLayout)findViewById(R.id.intRelLayout);
        imgView=(ImageView)findViewById(R.id.butImg);
        startText=(TextView)findViewById(R.id.strtGame);
        
        
        mp=MediaPlayer.create(MainActivity.this, R.raw.bloop);
        
        Typeface txt=Typeface.createFromAsset(getAssets(), "Dragon.otf");
        startText.setTypeface(txt);
      
        mainLayout.setOnTouchListener(new OntouchImpl());
        
        startText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					Intent it=new Intent(getApplicationContext(),GameActivity.class);
					startActivity(it);
			}
		});
        
    }
    
    class OntouchImpl implements OnTouchListener
    {

    	@Override
    	public boolean onTouch(View v, MotionEvent event) {
    		// TODO Auto-generated method stub
    		if(!mp.isPlaying())
    			mp.start();
    		if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
    		     imgView.setImageResource(R.drawable.b2);
    		     startText.setTextSize(80);
    		     startText.setTextColor(Color.YELLOW);
    		    } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
    		    	imgView.setImageResource(R.drawable.b1);
    		    	 startText.setTextSize(125);
    		    	 startText.setTextColor(Color.MAGENTA);
    		    }
    		return true;
    	}
    	
    }
    


}


