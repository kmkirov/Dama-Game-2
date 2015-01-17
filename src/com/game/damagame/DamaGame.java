package com.game.damagame;

import java.util.ArrayList;

import com.game.animation.CustomViewWinner;
import com.game.constants.GameConstants;
import com.game.database.PlayerDatabaseHelper;


import android.app.Activity;

import android.content.Intent;


import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import android.view.animation.AnimationSet;

import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import android.widget.Button;
import android.widget.TextView;


public class DamaGame extends Activity implements OnClickListener
{
	//board
	private TextView a1;
	private TextView a2;
	private TextView a3;
	private TextView a4;
	private TextView a5;
	private TextView a6;
	private TextView a7;
	private TextView a8;
	
	
	private TextView b1;
	private TextView b2;
	private TextView b3;
	private TextView b4;
	private TextView b5;
	private TextView b6;
	private TextView b7;
	private TextView b8;
	
	
	private TextView c1;
	private TextView c2;
	private TextView c3;
	private TextView c4;
	private TextView c5;
	private TextView c6;
	private TextView c7;
	private TextView c8;
	//end board
	
	private TextView gameMessages, anticrash_redchip, anticrash_blue_chip, anticrash_text_players;
	private String first_player_name, second_player_name;
	private Button resing_button;
	//private TextView player1_name;
	private int first_part_game = 1;//1 first part, 0 second part
	//private final String mesg_intent = "Player Names";
	
	
	private int counter_red = 4, //9,
				counter_blue =4,// 9,
				need_delete = 0, //if player have 3 in a row :)
				firstplayer_turn = 1,//1 true redchip , 2 second player turn bluechip
				countPlayingRed = 0, /// all playing -- when given
				countPlayingBlue = 0;
	// player 1 -> 1 in matrix, player 2 ->2 in matrix
	private int [][] matrix = {//1 2 3 4 5 6 7 8 position
								{0,0,0,0,0,0,0,0},//a index
								{0,0,0,0,0,0,0,0},//b
								{0,0,0,0,0,0,0,0} //c
							};

	Pair selected_place = null;
	int redchip = R.drawable.redchip;
	int bluechip = R.drawable.bluechip;
	int avalablechip = R.drawable.available;
	int cleanChip = R.drawable.free;
	int store_selected_color = 0;
	
	 CustomViewWinner animatedImage;
	 
	 
	 void write_changes_to_db(String winner, String looser)
	 {
		 PlayerDatabaseHelper.addPlayerInfoOnWin(this, winner,  "16",GameConstants.WINS_TYPE);
		 PlayerDatabaseHelper.addPlayerInfoOnWin(this, looser,  "16",GameConstants.LOOSES_TYPE);
		 animatedImage.updateState(winner);
	 }
	 
	 
	private boolean end_game()
	{
		if(firstplayer_turn == 1 && countPlayingRed < 3 && first_part_game!=1 || !playerHasPosibleMove())/// first loose
			write_changes_to_db(second_player_name,first_player_name);

		if(firstplayer_turn == 2 && countPlayingBlue < 3 && first_part_game!=1 || !playerHasPosibleMove())///second loose
			write_changes_to_db(first_player_name,second_player_name);
	
		return false;
	}
	protected void onCreate(Bundle savedInstanceState) 
	{
		  
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.dama_game);
	       
	        //animation view
	    	animatedImage = (CustomViewWinner) findViewById(R.id.animation_win_view);  	  
	      	animatedImage.setVisibility(View.INVISIBLE);
	      	animatedImage.setOnClickListener(this);
	        
	      	resing_button = (Button) findViewById(R.id.resign_button);
	      	resing_button.setOnClickListener(this);
	      	anticrash_redchip = (TextView) findViewById(R.id.redchip_textview);
	      	anticrash_blue_chip = (TextView) findViewById(R.id.bluechip_textview);
	      	anticrash_text_players = (TextView) findViewById(R.id.players_textview);
	      	
	      	Intent info = getIntent();
	      	first_player_name = info.getStringExtra(GameConstants.FIRST_PLAYER_NAME);
	      	second_player_name = info.getStringExtra(GameConstants.SECOND_PLAYER_NAME);
	      	Object[] params = new String[] { first_player_name, second_player_name};
	      	anticrash_text_players.setText(this.getString(R.string.first_player_name, params));
	      	anticrash_text_players.invalidate();
	      	
	      	
	      	
	      	
	      	
	      	
	    	first_part_game = 1;
		  	firstplayer_turn = 1;
		  	
		  	
		  	gameMessages = (TextView) findViewById(R.id.messages);
		  	gameMessages.setText("game started " + "b" + 1 );
		  	
	       
	       
	       
	        
	        a1 = (TextView) findViewById(R.id.a1);
	        a2 = (TextView) findViewById(R.id.a2);
	        a3 = (TextView) findViewById(R.id.a3);
	        a4 = (TextView) findViewById(R.id.a4);
	        a5 = (TextView) findViewById(R.id.a5);
	        a6 = (TextView) findViewById(R.id.a6);
	        a7 = (TextView) findViewById(R.id.a7);
	        a8 = (TextView) findViewById(R.id.a8);
	        a1.setOnClickListener(this);
	        a2.setOnClickListener(this);
	        a3.setOnClickListener(this);
	        a4.setOnClickListener(this);
	        a5.setOnClickListener(this);
	        a6.setOnClickListener(this);
	        a7.setOnClickListener(this);
	        a8.setOnClickListener(this);
	        
	        
	        b1 = (TextView) findViewById(R.id.b1);
	        b2 = (TextView) findViewById(R.id.b2);
	        b3 = (TextView) findViewById(R.id.b3);
	        b4 = (TextView) findViewById(R.id.b4);
	        b5 = (TextView) findViewById(R.id.b5);
	        b6 = (TextView) findViewById(R.id.b6);
	        b7 = (TextView) findViewById(R.id.b7);
	        b8 = (TextView) findViewById(R.id.b8);
	        b1.setOnClickListener(this);
	        b2.setOnClickListener(this);
	        b3.setOnClickListener(this);
	        b4.setOnClickListener(this);
	        b5.setOnClickListener(this);
	        b6.setOnClickListener(this);
	        b7.setOnClickListener(this);
	        b8.setOnClickListener(this);
	     
	       
	        
	        c1 = (TextView) findViewById(R.id.c1);
	        c2 = (TextView) findViewById(R.id.c2);
	        c3 = (TextView) findViewById(R.id.c3);
	        c4 = (TextView) findViewById(R.id.c4);
	        c5 = (TextView) findViewById(R.id.c5);
	        c6 = (TextView) findViewById(R.id.c6);
	        c7 = (TextView) findViewById(R.id.c7);
	        c8 = (TextView) findViewById(R.id.c8);
	        c1.setOnClickListener(this);
	        c2.setOnClickListener(this);
	        c3.setOnClickListener(this);
	        c4.setOnClickListener(this);
	        c5.setOnClickListener(this);
	        c6.setOnClickListener(this);
	        c7.setOnClickListener(this);
	        c8.setOnClickListener(this);
	    }

	boolean checkThree(int index, int number, int owner )
	{
		// playernumber == 1 || 2
		if(number % 2 == 0)//kraino pole
		{
			if	(   matrix[index][(number + 8) % 8] == owner && 
					matrix[index][(number + 9) % 8] == owner &&
					matrix[index][(number + 10 )% 8] == owner)
				return true;
			else if(matrix[index][(number+8) % 8] == owner && 
					matrix[index][(number - 1 + 8) % 8] == owner &&
					matrix[index][(number - 2 + 8)% 8] == owner)
				return true;
		}
		else//number%2 == 1 sredno pole
		{
			if	(   matrix[index][(number + 7) % 8] == owner && 
					matrix[index][(number ) % 8] == owner &&
					matrix[index][(number + 9 )% 8] == owner)
				return true;
			if	(   matrix[(index + 1) % 3][number] == owner && 
					matrix[(index + 2) % 3][number] == owner &&
					matrix[    index      ][number] == owner)
				return true;
		}
		return false;
		
	}
	  
	int getIndexOfView(String l)
	{
	  int index = 0;
	  if(l.equals("b"))index = 1;
	  if(l.equals("c"))index = 2;
	  return index;
	  
	}
	  
	void putInMatrix(View v, int number, String letter)
	{
	  int index = getIndexOfView(letter);
	  
	  if(matrix[index][number] != 0 )
		  return;
	  
	  if(matrix[index][number] == 0 && firstplayer_turn == 1)
	  {
		  //v.setBackgroundColor(color_first);
		 
		  v.setBackgroundResource(redchip);
		  firstplayer_turn = 2;//second playerturn
		  matrix[index][number] = 1;
		  counter_red--;
		  countPlayingRed++;
		  gameMessages.setText("doesnt have 3 no need to delete");
		  if(checkThree(index, number,1))
		  {
			  firstplayer_turn = 1;
			  need_delete = 1;
			  gameMessages.setText("has 3 need to delete");
		  }
		  
	  }
	  else if(matrix[index][number] == 0 && firstplayer_turn == 2)
	  {
		  //v.setBackgroundColor(color_second);
		  v.setBackgroundResource(bluechip);
		  firstplayer_turn = 1;
		  counter_blue--;
		  matrix[index][number] = 2;
		  countPlayingBlue++;
		  gameMessages.setText("doesnt have 3 no need to delete");
		  if(checkThree(index, number,2))
		  {
			  firstplayer_turn = 2;
			  need_delete = 1;
			  gameMessages.setText("has 3 need to delete");
		  }
	  }
  }
	  
	boolean deletePosition(View v,Pair p, int player_who_deletes_color)
	{
		int index =getIndexOfView(p.letter);
		 gameMessages.setText("call deletes position");
		if(matrix[index][p.position] == 0)
			return false;
		
		//if the player has 3 puls in dama (crash if no other pools) ne raboti
		if(player_who_deletes_color == 1 && matrix[index][p.position] == 2 && 
				checkThree(index,p.position,2))
			return false;
		
		if(player_who_deletes_color == 2 && matrix[index][p.position] == 1 && 
				checkThree(index,p.position,1))
			return false;
		
		
		if( player_who_deletes_color == 1
				&& matrix[index][p.position] == 2 )
		{
			matrix[index][p.position] = 0;
			v.setBackgroundResource(cleanChip);
			firstplayer_turn = 2;
			need_delete = 0;
			countPlayingRed--;
			return true;
		}
		if( player_who_deletes_color == 2
				&& matrix[index][p.position] == 1 )
		{
			matrix[index][p.position] = 0;
			v.setBackgroundResource(cleanChip);
			firstplayer_turn = 1;
			need_delete = 0;
			countPlayingBlue--;
			return true;
		}
		
		return false;
	}
	
	void firstPartOfTheGame(View v, Pair selected)
	{
		if(need_delete == 1)
		{
			deletePosition(v,selected,firstplayer_turn);
			gameMessages.setText(" delete " + firstplayer_turn);
			return;
		}
		
		if(first_part_game == 1)
		{
			if(counter_red + counter_blue > 0 && first_part_game == 1)
			{
				 putInMatrix(v,selected.position, selected.letter);
				 gameMessages.setText(" putting pull " + firstplayer_turn);
				
				 return;
			}
			if(counter_red + counter_blue == 0)
			{
				first_part_game=0;//end first part
				 gameMessages.setText(" end first part");
			}
		}
	}

	void drawAvalable(ArrayList<Pair> available_pair, int avalable)
	{
		for(Pair p : available_pair)
		{
			if(avalable > 0)
				p.view.setBackgroundResource(avalablechip);
			else
				p.view.setBackgroundResource(cleanChip);
		}
	}
	int getOwnerOfPair(Pair p)
	{
		return matrix[getIndexOfView(p.letter)][p.position];
	}
	ArrayList<Pair> get_available_moves(Pair selected)//raboti
	{
		int index = getIndexOfView(selected.letter);
		int position = selected.position;
		ArrayList<Pair> available_moves = new ArrayList<Pair>();
		
		if(matrix[index][(position + 1) % 8] == 0)
		{
			View v = fromPositionToVeiw(index, (position + 1) % 8);
			Pair p =  position_selected(v);
			available_moves.add(p);
		}
		if(matrix[index][(position + 7) % 8] == 0)
		{
			View v = fromPositionToVeiw(index , (position + 7) % 8);
			Pair p =  position_selected(v);
			available_moves.add(p);
		}
		if(position % 2 == 0)//ako sme v nqkoi ot rybovete za vsichki e ednakvo
		{
			drawAvalable(available_moves,1);
			return available_moves;//nqma poveche vyzmojnosti za rub
		}//edge end
		
		if(position % 2 == 1)
		{
			if(index == 0 || index == 1)//a midpoint
			{
				View v = fromPositionToVeiw(index+1 , position );
				Pair p =  position_selected(v);
				if(getOwnerOfPair(p) == 0)
					available_moves.add(p);
			}
			if(index == 2 || index == 1)//c midpoint
			{
				View v = fromPositionToVeiw(index - 1 , position );
				Pair p =  position_selected(v);
				if(getOwnerOfPair(p) == 0)
					available_moves.add(p);
			}
		}
		drawAvalable(available_moves,1);
		return available_moves;
	}
	
	boolean selected_move(View v, Pair p)
	{
		if(checkPossibleSelection(getIndexOfView(p.letter),p.position))
		{
			selected_place = p;
			v.setBackgroundResource(cleanChip);
		}
		else 
			return false;
		return true;
	}
	void secondPartOfTheGame(View v, Pair selected)
	{	
		end_game();
		int current_player_color = firstplayer_turn == 1? redchip:bluechip;
		if(need_delete == 1)
		{
			deletePosition(v,selected,firstplayer_turn);
			gameMessages.setText(" delete " + firstplayer_turn);
			return;
		}
		
		if(selected_place == null)
		{
			//first time click and select a space
			gameMessages.setText(" selected space " + firstplayer_turn);
			if( selected_move(v,selected) == true)
				get_available_moves(selected);
			return;
		}
		if(selected_place != null && selected_place.ravni(selected)) /// raboti
		{
			//disselect if again tapped on the same item
			v.setBackgroundResource(current_player_color);
			gameMessages.setText(" restored " + firstplayer_turn);
			ArrayList<Pair> available_pos = get_available_moves(selected_place);
			drawAvalable(available_pos, 0);
			selected_place = null;
			return;
		}
		if(selected_place != null)
		{
		//moving chip	
			//1 check if in available
			ArrayList<Pair> available_pos = get_available_moves(selected_place);
			drawAvalable(available_pos, 0);
			boolean prinadleji_na_dopustimite = false;
			//proverqva dali sa poveche ot 3
			if((countPlayingRed > 3 && firstplayer_turn == 1 )||(countPlayingBlue > 3 && firstplayer_turn == 2))
				for(Pair i : available_pos) 
				{
					if(selected.ravni(i))
						prinadleji_na_dopustimite = true;
				}
			else//less then 3 chips can move everywhere
				prinadleji_na_dopustimite = true;
			
			if(prinadleji_na_dopustimite)
			{	
				gameMessages.setText(" moving  success" + firstplayer_turn);
				v.setBackgroundResource(current_player_color);
				View v_old = fromPositionToVeiw(getIndexOfView(selected_place.letter),selected_place.position);
				v_old.setBackgroundResource(cleanChip);
				matrix[getIndexOfView(selected_place.letter)][selected_place.position] = 0;
				matrix[getIndexOfView(selected.letter)][selected.position] = firstplayer_turn;
				selected_place = null;	
				if(checkThree(getIndexOfView(selected.letter), selected.position, firstplayer_turn))
				{
					need_delete = 1; //ne promenqme igracha
				}
				else
					firstplayer_turn = firstplayer_turn == 1 ? 2 : 1;//ako nqma 3ka promenqma igracha
						
			}
			else
			{
				//redraw
				View v_old = fromPositionToVeiw(getIndexOfView(selected_place.letter),selected_place.position);
				v_old.setBackgroundResource(current_player_color);
				selected_place = null;
			}
			
			}
			
		}
		
	
	
	@Override
	public void onClick(View v) 
	{
		Pair selected = position_selected(v);
		if(v.getId() == anticrash_redchip.getId() || v.getId() == anticrash_blue_chip.getId() 
				|| v.getId() == anticrash_text_players.getId() )
			return;
		if(v.getId() == resing_button.getId())
		{
			 //wining animation + database update + end of game
			if(firstplayer_turn == 1)
				write_changes_to_db(second_player_name,first_player_name);
			else 
				write_changes_to_db(first_player_name, second_player_name);
			win_animation();
			return;
		}
		
		if(v.getId() == animatedImage.getId())
		{
			//this.finishActivity(1);
			finish();
		}
		//game 
		if(first_part_game == 1)
		{
			firstPartOfTheGame(v, selected);
			replace(v);
			return;
		}
		else
		{
			secondPartOfTheGame(v, selected);
			replace(v);
		}
	}
	
	boolean playerHasPosibleMove()
	{
		for(int i = 0 ;i < 3; ++i)
			for (int j = 0 ;j < 8; ++j)
				if(matrix[i][j] == firstplayer_turn)
				{
					View v = fromPositionToVeiw(i , j );
					Pair p =  position_selected(v);
					if(get_available_moves(p).size() > 0)
						return true;
				}
		return false;				
	}
	
	boolean checkPossibleSelection(int index, int number)
	{
 		if(matrix[index][number] == firstplayer_turn)
			return true;
		return false;
	}
	
	

	boolean checkAvalabilityOfMove(Pair position_to_move, int current_player)
	{
		
		//selected_place old pos
	
		//int old_index = 
		
		//if(matrix[index][number] != current_player)
			return true;
		//return true;
	}
	
	boolean avalableMoves(int playerOnTurn)
	{
		
//		// ako ima svobodno pole do nqkoe ot 
//		//pritejavannite ot player on turn znachi igrata movje da produlji
//		for(int i = 0;i<3;++i)
//			for(int j=0;j<8;++j)
//			{
//				if(matrix[i][j] == playerOnTurn)
//				{
//					if(j % 2 == 0 &&
//					(matrix[i][j + 1] == 0 || matrix[(i + 7) % 8][j +1] == 0))
//					return true;
//					if(j % 2 == 1 &&
//							(matrix[i][j + 1] == 0 || matrix[i][(i + 7) % 8] == 0 ||
//							matrix[(i + 1) % 3][j] == 0 || matrix[(i + 2) % 3][j] == 0))
//							return true;	
//				}
//			}		
//		return false;
		return true;
	}
	
	boolean endGame(int firstplayer_turn)
	{
		if(firstplayer_turn == 1 && counter_red < 3)
			return true;
		if(firstplayer_turn == 2 && counter_blue < 3)
			return true;
		if(!avalableMoves(firstplayer_turn))
			return true;
		return false;
	}

	TextView fromPositionToVeiw(int index, int position)
{
	TextView v = null;
	if(index == 0)
	{
		if(position == 0)
			return a1;
		if(position == 1)
			return a2;
		if(position == 2)
			return a3;
		if(position == 3)
			return a4;
		if(position == 4)
			return a5;
		if(position == 5)
			return a6;
		if(position == 6)
			return a7;
		if(position == 7)
			return a8;
	}
	
	else if(index == 1)
	{
		if(position == 0)
			return b1;
		if(position == 1)
			return b2;
		if(position == 2)
			return b3;
		if(position == 3)
			return b4;
		if(position == 4)
			return b5;
		if(position == 5)
			return b6;
		if(position == 6)
			return b7;
		if(position == 7)
			return b8;
	}
	else//==3
	{
		if(position == 0)
			return c1;
		if(position == 1)
			return c2;
		if(position == 2)
			return c3;
		if(position == 3)
			return c4;
		if(position == 4)
			return c5;
		if(position == 5)
			return c6;
		if(position == 6)
			return c7;
		if(position == 7)
			return c8;
	}
	return v;
}	
	
	Pair position_selected(View v)
	{
		if(v.getId() == c1.getId())
			 return new Pair(c1,"c",0);
		if(v.getId() == c2.getId())
			return new Pair(c2,"c",1);
		if(v.getId() == c3.getId())
			return new Pair(c3,"c",2);
		if(v.getId() == c4.getId())
			return new Pair(c4,"c",3);
		if(v.getId() == c5.getId())
			return new Pair(c5,"c",4);
		if(v.getId() == c6.getId())
			return new Pair(c6,"c",5);
		if(v.getId() == c7.getId())
			return new Pair(c7,"c",6);
		if(v.getId() == c8.getId())
			return new Pair(c8,"c",7);
		
		if(v.getId() == b1.getId())
			return new Pair(b1,"b",0);
		if(v.getId() == b2.getId())
			return new Pair(b2,"b",1);
		if(v.getId() == b3.getId())
			return new Pair(b3,"b",2);
		if(v.getId() == b4.getId())
			return new Pair(b4,"b",3);
		if(v.getId() == b5.getId())
			return new Pair(b5,"b",4);
		if(v.getId() == b6.getId())
			return new Pair(b6,"b",5);
		if(v.getId() == b7.getId())
			return new Pair(b7,"b",6);
		if(v.getId() == b8.getId())
			return new Pair(b8,"b",7);
			
		if(v.getId() == a1.getId())
			return new Pair(a1,"a",0);
		if(v.getId() == a2.getId())
			return new Pair(a2,"a",1);
		if(v.getId() == a3.getId())
			return new Pair(a3,"a",2);
		if(v.getId() == a4.getId())
			return new Pair(a4,"a",3);
		if(v.getId() == a5.getId())
			return new Pair(a5,"a",4);
		if(v.getId() == a6.getId())
			return new Pair(a6,"a",5);
		if(v.getId() == a7.getId())
			return new Pair(a7,"a",6);
		if(v.getId() == a8.getId())
			return new Pair(a8,"a",7);
		
		return new Pair(null,"d",-1);
	}
	
//	private class Coords
//	{
//		public int index, position;
//		public Coords()
//		{
//			index = -1;
//			position = -1;
//		}
//
//		public Coords(int i, int p)
//		{
//			index = i;
//			position = p;
//		}
//	}
	
	private class Pair
	{
		public Pair(TextView v, String l, int p)
		{
			this.view = v;
			this.letter = l;
			this.position = p;
		}
//		public Coords place()
//		{
//			return new Coords(getIndexOfView(letter), position);
//		}
		public boolean ravni ( Pair p)
		{
			if(letter == p.letter && position == p.position )
				return true;
			return false;
		}
		public TextView view;
		public String letter;
		public int position;
	}

	
	///animation functions
	public void win_animation() 
    {


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
	   public void replace(View thumbnailView) 
	    {
		   TextView redHelper = (TextView) findViewById(R.id.red_helper);
		   TextView blueHelper = (TextView) findViewById(R.id.blue_helper);	   
				   
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
//	   public void imageClicked(View thumbnailView) 
//	    {
//	    	  ImageView thumbnail = (ImageView)thumbnailView;
//	    	  animatedImage = (ImageView) findViewById(R.id.animatedImage);
//	    	  animatedImage.setImageDrawable(thumbnail.getDrawable());
//	    	  animatedImage.setVisibility(View.VISIBLE);
//
//	    	  Animation animation
//	    	    = AnimationUtils.loadAnimation(this, R.anim.anim_move);
//	    	  animatedImage.startAnimation(animation);
//	    	}




//@Override
//public void onBackPressed() {
//   Log.d("CDA", "onBackPressed Called");
//   Intent setIntent = new Intent(Intent.ACTION_MAIN);
//   setIntent.addCategory(Intent.CATEGORY_HOME);
//   setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//   startActivity(setIntent);
//}
//if(v.getId() == c1.getId())
//	tester("c1", c1);
//if(v.getId() == c2.getId())
//	tester("c2", c2);
//if(v.getId() == c3.getId())
//	tester("c3", c3);
//if(v.getId() == c4.getId())
//	tester("c4", c4);
//if(v.getId() == c5.getId())
//	tester("c5",c5);
//if(v.getId() == c6.getId())
//	tester("c6", c6);
//if(v.getId() == c7.getId())
//	tester("c7", c7);
//if(v.getId() == c8.getId())
//	tester("c8", c8);

