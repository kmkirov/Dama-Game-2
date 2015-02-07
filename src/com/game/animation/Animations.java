package com.game.animation;

import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
//class for animimatios
public class Animations {
	 
	static public void win_animation(View v) 
    {
		v.setVisibility(View.VISIBLE);
		float xScale = 3f, yScale=3f;
		// create set of animations
		AnimationSet replaceAnimation = new AnimationSet(false);
		// animations should be applied on the finish line
		replaceAnimation.setFillAfter(true);
		// create scale animation
		ScaleAnimation scale = new ScaleAnimation(0.0f, xScale, 0.0f, yScale);
		scale.setDuration(700);
		// create translation animation
		TranslateAnimation trans = new TranslateAnimation(100, 0, 0, 0);
		trans.setDuration(700);
		// add new animations to the set
		replaceAnimation.addAnimation(scale);
		replaceAnimation.addAnimation(trans);
		// start our animation
		v.startAnimation(replaceAnimation);
    }
	  
		///long jump not working :)
	  static public void replace(TextView moving_view, TextView destination_view) 
	    {
	    	int xTo = destination_view.getWidth(),  yTo = destination_view.getHeight();
	    	// create set of animations
	    	AnimationSet replaceAnimation = new AnimationSet(false);
	        // animations should be applied on the finish line
	        replaceAnimation.setFillAfter(false);
	        // create translation animation
	        TranslateAnimation trans = new TranslateAnimation(moving_view.getWidth(), moving_view.getHeight(),
	     														xTo,				 yTo);
	        trans.setDuration(1000);
	        replaceAnimation.addAnimation(trans);

	        // start our animation
	        moving_view.startAnimation(replaceAnimation);
	       // moving_view.setVisibility(View.INVISIBLE);
	       
	    }
	}


