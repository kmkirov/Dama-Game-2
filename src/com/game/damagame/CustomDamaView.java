package com.game.damagame;



import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class CustomDamaView extends LinearLayout implements OnClickListener
{
	//board
		private TextView _a1;
		private TextView _a2;
		private TextView _a3;
		private TextView _a4;
		private TextView _a5;
		private TextView _a6;
		private TextView _a7;
		private TextView _a8;
		
		
		private TextView _b1;
		private TextView _b2;
		private TextView _b3;
		private TextView _b4;
		private TextView _b5;
		private TextView _b6;
		private TextView _b7;
		private TextView _b8;
		
		
		private TextView _c1;
		private TextView _c2;
		private TextView _c3;
		private TextView _c4;
		private TextView _c5;
		private TextView _c6;
		private TextView _c7;
		private TextView _c8;
		//end board
		//public static TextView _lastClickedView;
		//public static TextView _testClickedView;
		Coords _lastTextviewClicked;
		public OnClickListener listener;
	

	public CustomDamaView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.only_dama_board, this, true);
		
		_lastTextviewClicked = new Coords();
		
		_a1 = (TextView) findViewById(R.id.a1);
        _a2 = (TextView) findViewById(R.id.a2);
        _a3 = (TextView) findViewById(R.id.a3);
        _a4 = (TextView) findViewById(R.id.a4);
        _a5 = (TextView) findViewById(R.id.a5);
        _a6 = (TextView) findViewById(R.id.a6);
        _a7 = (TextView) findViewById(R.id.a7);
        _a8 = (TextView) findViewById(R.id.a8);
        //_testClickedView=_c1;
       // _lastClickedView = _a1;//anti null;
        _a1.setOnClickListener(this);
        _a2.setOnClickListener(this);
        _a3.setOnClickListener(this);
        _a4.setOnClickListener(this);
        _a5.setOnClickListener(this);
        _a6.setOnClickListener(this);
        _a7.setOnClickListener(this);
        _a8.setOnClickListener(this);
	         
        _b1 = (TextView) findViewById(R.id.b1);
        _b2 = (TextView) findViewById(R.id.b2);
        _b3 = (TextView) findViewById(R.id.b3);
        _b4 = (TextView) findViewById(R.id.b4);
        _b5 = (TextView) findViewById(R.id.b5);
        _b6 = (TextView) findViewById(R.id.b6);
        _b7 = (TextView) findViewById(R.id.b7);
        _b8 = (TextView) findViewById(R.id.b8);
   
        _b1.setOnClickListener(this);
        _b2.setOnClickListener(this);
        _b3.setOnClickListener(this);
        _b4.setOnClickListener(this);
        _b5.setOnClickListener(this);
        _b6.setOnClickListener(this);
        _b7.setOnClickListener(this);
        _b8.setOnClickListener(this);
	     
        _c1 = (TextView) findViewById(R.id.c1);
        _c2 = (TextView) findViewById(R.id.c2);
        _c3 = (TextView) findViewById(R.id.c3);
        _c4 = (TextView) findViewById(R.id.c4);
        _c5 = (TextView) findViewById(R.id.c5);
        _c6 = (TextView) findViewById(R.id.c6);
        _c7 = (TextView) findViewById(R.id.c7);
        _c8 = (TextView) findViewById(R.id.c8);
        _c1.setOnClickListener(this);
        _c2.setOnClickListener(this);
        _c3.setOnClickListener(this);
        _c4.setOnClickListener(this);
        _c5.setOnClickListener(this);
        _c6.setOnClickListener(this);
        _c7.setOnClickListener(this);
        _c8.setOnClickListener(this);
        //_testClickedView=_c1;
	}

	
	public void setBackgroundWithImage(int index, int position, int resid)//set background with resid
	{
		TextView chip = fromPositionToVeiw(index, position);
		if(chip != null)
			chip.setBackgroundResource(resid);
	}
	
	
	public void setBackgroundWithImage(Coords coord, int resid)//set background with resid
	{
		TextView chip = fromPositionToVeiw(coord.index, coord.position);
		if(chip != null)
			chip.setBackgroundResource(resid);
	}
	
	
	public Coords getLastClick()//return last click view
	{
		return _lastTextviewClicked;		
	}
	
	
	public void delLastClick()
	{
		_lastTextviewClicked = null;
	}
	
	
	@Override
	public void onClick(View v) 
	{
		select_textview(v);
		//_lastClickedView = (TextView)v;
		listener.onClick(v);
	}
	
	
	public void select_textview(View v) 
		{
			int id = v.getId();
			//c ids
			if (id == _c1.getId())
			{
				_lastTextviewClicked = new Coords(Coords.INDEX_2, 0);
			}
			else if (id == _c2.getId()) 
			{
				_lastTextviewClicked = new Coords(Coords.INDEX_2, 1);
			}
			else if (id == _c3.getId()) 
			{
				_lastTextviewClicked = new Coords(Coords.INDEX_2, 2);
			}
			else if (id == _c4.getId()) 
			{
				_lastTextviewClicked = new Coords(Coords.INDEX_2, 3);
			}
			else if (id == _c5.getId()) 
			{
				_lastTextviewClicked = new Coords(Coords.INDEX_2, 4);
			}
			else if (id == _c6.getId())
			{
				_lastTextviewClicked = new Coords(Coords.INDEX_2, 5);
			}
			else if (id == _c7.getId()) 
			{
				_lastTextviewClicked = new Coords(Coords.INDEX_2, 6);
			} 
			else if (id == _c8.getId())
			{
				_lastTextviewClicked = new Coords(Coords.INDEX_2, 7);
			}
			///b ids
			else if (id == _b1.getId()) 
			{
				_lastTextviewClicked = new Coords(Coords.INDEX_1, 0);
			} 
			else if (id == _b2.getId()) 
			{
				_lastTextviewClicked = new Coords(Coords.INDEX_1, 1);
			} 
			else if (id == _b3.getId()) 
			{
				_lastTextviewClicked = new Coords(Coords.INDEX_1, 2);
			} 
			else if (id == _b4.getId())
			{
				_lastTextviewClicked = new Coords(Coords.INDEX_1, 3);
			} 
			else if (id == _b5.getId())
			{
				_lastTextviewClicked = new Coords(Coords.INDEX_1, 4);
			}
			else if (id == _b6.getId())
			{
				_lastTextviewClicked = new Coords(Coords.INDEX_1, 5);
			} 
			else if (id == _b7.getId()) 
			{
				_lastTextviewClicked = new Coords(Coords.INDEX_1, 6);
			}
			else if (id == _b8.getId()) 
			{
				_lastTextviewClicked = new Coords(Coords.INDEX_1, 7);
			}
			///a ids
			else if (id == _a1.getId()) 
			{
				_lastTextviewClicked = new Coords(Coords.INDEX_0, 0);
			} 
			else if (id == _a2.getId()) 
			{
				_lastTextviewClicked = new Coords(Coords.INDEX_0, 1);
			} 
			else if (id == _a3.getId()) 
			{
				_lastTextviewClicked = new Coords(Coords.INDEX_0, 2);
			}
			else if (id == _a4.getId()) 
			{
				_lastTextviewClicked = new Coords(Coords.INDEX_0, 3);
			}
			else if (id == _a5.getId()) 
			{
				_lastTextviewClicked = new Coords(Coords.INDEX_0, 4);
			} 
			else if (id == _a6.getId()) 
			{
				_lastTextviewClicked = new Coords(Coords.INDEX_0, 5);
			}
			else if (id == _a7.getId())
			{
				_lastTextviewClicked = new Coords(Coords.INDEX_0, 6);
			}
			else if (id == _a8.getId()) 
			{
				_lastTextviewClicked = new Coords(Coords.INDEX_0, 7);
			} 
			//not clicked -1 -1
			else
			{
				_lastTextviewClicked = new Coords();
			}  
		}
	

	public TextView fromPositionToVeiw(int index, int position)
	{
		TextView v = null;
		if(index == 0)
		{
			if(position == 0)
				return _a1;
			if(position == 1)
				return _a2;
			if(position == 2)
				return _a3;
			if(position == 3)
				return _a4;
			if(position == 4)
				return _a5;
			if(position == 5)
				return _a6;
			if(position == 6)
				return _a7;
			if(position == 7)
				return _a8;
		}
		
		else if(index == 1)
		{
			if(position == 0)
				return _b1;
			if(position == 1)
				return _b2;
			if(position == 2)
				return _b3;
			if(position == 3)
				return _b4;
			if(position == 4)
				return _b5;
			if(position == 5)
				return _b6;
			if(position == 6)
				return _b7;
			if(position == 7)
				return _b8;
		}
		else//==3
		{
			if(position == 0)
				return _c1;
			if(position == 1)
				return _c2;
			if(position == 2)
				return _c3;
			if(position == 3)
				return _c4;
			if(position == 4)
				return _c5;
			if(position == 5)
				return _c6;
			if(position == 6)
				return _c7;
			if(position == 7)
				return _c8;
		}
		return v;
	}	

}

