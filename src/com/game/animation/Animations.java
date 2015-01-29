package com.game.animation;

import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

// ne e setnato animations
public class Animations {
	 static public CustomViewWinner animatedImage;
	///animation functions
	//animation view
// 	animatedImage = (CustomViewWinner) findViewById(R.id.animation_win_view);  	  
//   	animatedImage.setVisibility(View.INVISIBLE);
//   	animatedImage.setOnClickListener(this);
		static public void win_animation() 
	    {
			//animatedImage = (CustomViewWinner) findViewById(R.id.animation_win_view); 
			animatedImage.setVisibility(View.VISIBLE);
	  	
	  	float xScale = 3f, yScale=3f;
	      // create set of animations
	  	AnimationSet replaceAnimation = new AnimationSet(false);
	      // animations should be applied on the finish line
	      replaceAnimation.setFillAfter(true);

	      // create scale animation
	      ScaleAnimation scale = new ScaleAnimation(0.0f, xScale, 0.0f, yScale);
	      scale.setDuration(300);

	      // create translation animation
	      TranslateAnimation trans = new TranslateAnimation(100, 0, 0, 0);
	      trans.setDuration(300);

	      // add new animations to the set
	      replaceAnimation.addAnimation(scale);
	      replaceAnimation.addAnimation(trans);

	      // start our animation
	     
	      animatedImage.startAnimation(replaceAnimation);
	    }
	  
		///long jump not working :)
		  static public void replace(View thumbnailView, int firstplayer_turn) 
		    {//trqbva da gi opravq posle
			   TextView redHelper = null; //(TextView) findViewById(R.id.red_helper);
			   TextView blueHelper = null;//(TextView) findViewById(R.id.blue_helper);	   
					   
			   TextView animationView = firstplayer_turn == 1? redHelper : blueHelper;
		    	
		    	int xTo = thumbnailView.getWidth(),  yTo = thumbnailView.getHeight();
		    	float xScale =1f, yScale = 1f;
		        
		    	// create set of animations
		    	AnimationSet replaceAnimation = new AnimationSet(false);
		        // animations should be applied on the finish line
		        replaceAnimation.setFillAfter(false);

		        // create scale animation
		        ScaleAnimation scale = new ScaleAnimation(1.0f, xScale, 1.0f, yScale);
		        scale.setDuration(300);

		        // create translation animation
		        TranslateAnimation trans = new TranslateAnimation(animationView.getWidth(), animationView.getHeight(),
		        														xTo,				 yTo);
		        trans.setDuration(300);

		        // add new animations to the set
		        replaceAnimation.addAnimation(scale);
		        replaceAnimation.addAnimation(trans);

		        // start our animation
		        animationView.startAnimation(replaceAnimation);
		       // animatedImage.setVisibility(View.INVISIBLE);
		       
		    }
		}
		///simple move
//		   public void imageClicked(View thumbnailView) 
//		    {
//		    	  ImageView thumbnail = (ImageView)thumbnailView;
//		    	  animatedImage = (ImageView) findViewById(R.id.animatedImage);
//		    	  animatedImage.setImageDrawable(thumbnail.getDrawable());
//		    	  animatedImage.setVisibility(View.VISIBLE);
	//
//		    	  Animation animation
//		    	    = AnimationUtils.loadAnimation(this, R.anim.anim_move);
//		    	  animatedImage.startAnimation(animation);
//		    	}


