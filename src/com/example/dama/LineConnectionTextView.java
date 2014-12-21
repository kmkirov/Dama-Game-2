package com.example.dama;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class LineConnectionTextView extends TextView {
	private Paint mPaint;
	
	public LineConnectionTextView(Context context, AttributeSet attrs) {
		super(context, attrs);		
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.RED);
		mPaint.setStrokeWidth(5);
		
		
		
	}
	
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawLine(getX()/2, getY() / 2, getX() +  500,  getY()/2 , mPaint);
		canvas.drawLine(getX()/2, getY() / 2, getX()/2 ,  getY() + 500, mPaint);
	}
}
