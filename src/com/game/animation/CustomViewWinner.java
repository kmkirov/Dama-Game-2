package com.game.animation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
//import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.game.damagame.R;


public class CustomViewWinner extends LinearLayout
{
	//	private ImageView image;
		private TextView text;

		public CustomViewWinner(Context context, AttributeSet attrs) {
			super(context, attrs);

			LayoutInflater.from(context).inflate(R.layout.win_animation_layout, this, true);

		//	image = (ImageView) findViewById(R.id.winimage);
			text = (TextView) findViewById(R.id.wintext);
			
		}
		
		public void updateState(String playername) 
		{
			Object[] params = new String[] {
					playername };
			text.setText(getContext().getString(R.string.animation_winner, params));
			invalidate();
		}

		
	}

