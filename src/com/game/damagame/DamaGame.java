package com.game.damagame;

import java.util.ArrayList;
import com.game.animation.Animations;
import com.game.animation.CustomViewWinner;
import com.game.constants.GameConstants;
import com.game.database.PlayerDatabaseHelper;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DamaGame extends Activity implements OnClickListener
{
	private int FIRST_PLAYER = 1;
	private int SECOND_PLAYER = 2;
	
	private TextView 	gameMessages,
						anticrash_redchip,
						anticrash_blue_chip,
						anticrash_text_players;
	
	private String first_player_name,
				   second_player_name;
	
	private Button resing_button;
	private boolean resign = false;
	
	private ArrayList<Coords> moves_made = new ArrayList<Coords>();
	
	//game variables
	private int first_part_game = 1;		//1 first part, 0 second part
	private int counter_red = 9, 			//9, number of chips to be put on board
				counter_blue = 9,			// 9,
				need_delete = 0, 			//if player have 3 in a row :) needs to delete and it is 1
				firstplayer_turn = 1,		//1 true redchip , 2 second player turn bluechip
				countPlayingRed = 0, 		/// all playing chips -- when given
				countPlayingBlue = 0;
	
											// player 1 -> 1 in matrix, player 2 ->2 in matrix
	private int [][] matrix = {//1 2 3 4 5 6 7 8 position
								{0,0,0,0,0,0,0,0},//a index
								{0,0,0,0,0,0,0,0},//b
								{0,0,0,0,0,0,0,0} //c
							};

	Coords selected_place = null; 
	ArrayList<Coords> selected_place_available_places = null;
	
	//int store_selected_color = 0;
	
	private CustomDamaView dama_board;
	private CustomViewWinner win_view;
	//resources
	int redchip = R.drawable.redchip;
	int bluechip = R.drawable.bluechip;
	int avalablechip = R.drawable.available;
	int cleanChip = R.drawable.free;
	 
	void write_changes_to_db(String winner, String looser)
	{
		PlayerDatabaseHelper.addPlayerInfoOnWin(this, winner, "16", GameConstants.WINS_TYPE);
		PlayerDatabaseHelper.addPlayerInfoOnWin(this, looser, "16", GameConstants.LOOSES_TYPE);
		win_view = (CustomViewWinner) findViewById(R.id.animation_win_view);
		win_view.updateState(winner);
		Animations.win_animation(win_view);
		gameMessages.setText(  "Winner is " + winner);
		//timer for win
		new CountDownTimer(3000, 1000) {
	         public void onFinish() {
	             finish();
	     
	     }
	     public void onTick(long millisUntilFinished) {
	
	     }
	   }.start();
		
	}
	 
	
	void check_draw()
	{
		int count_moves = moves_made.size();
		if(count_moves < 8)
			return;
		if(count_moves > 200)
		{
			moves_made.clear();//risk for AI maybe move last four in the array
			return;
		}
		if(	moves_made.get(count_moves-8).equal_coords(moves_made.get(count_moves-4)) &&
			moves_made.get(count_moves-7).equal_coords(moves_made.get(count_moves-3)) &&
			moves_made.get(count_moves-6).equal_coords(moves_made.get(count_moves-2)) &&
			moves_made.get(count_moves-5).equal_coords(moves_made.get(count_moves-1)) 
			)
			resign = true; //decided 
	}
	

	private boolean end_game()
	{
		if(resign && firstplayer_turn == FIRST_PLAYER)
		{
			write_changes_to_db(second_player_name, first_player_name);
			return true;
		}
		else if(resign && firstplayer_turn == SECOND_PLAYER)
		{
			write_changes_to_db(first_player_name, second_player_name);
			return true;
		}
		
		if(firstplayer_turn == FIRST_PLAYER && countPlayingRed < 3 && first_part_game != 1)/// first loose
		{
			write_changes_to_db(second_player_name, first_player_name);
			return true;
		}

		else if(firstplayer_turn == SECOND_PLAYER && countPlayingBlue < 3 && first_part_game != 1)///second loose
		{
			write_changes_to_db(first_player_name, second_player_name);
			return true;
		}
		
		return false;
	}
	
	
	boolean playerHasPosibleMove()
	{
		//if(firstplayer_turn ==FIRST_PLAYER && //)
		for(int i = 0; i < 3; ++i)
			for (int j = 0; j < 8; ++j)
				if(matrix[i][j] == firstplayer_turn && get_available_moves(new Coords(i,j)).size() != 0)
					return true;
		return false;				
	}

	
	protected void onCreate(Bundle savedInstanceState) 
	{
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.dama_game);

	      	dama_board = (CustomDamaView) findViewById(R.id.dama_board);
	      	dama_board.setOnClickListener(this);
	      	dama_board.listener = this;

	      	resing_button = (Button) findViewById(R.id.resign_button);
	      	resing_button.setOnClickListener(this);
	      	
	      	anticrash_redchip = (TextView) findViewById(R.id.redchip_textview);
	      	anticrash_blue_chip = (TextView) findViewById(R.id.bluechip_textview);
	      	anticrash_text_players = (TextView) findViewById(R.id.players_textview);
	      	
	      	anticrash_redchip.setTextColor(Color.GREEN);
	      	anticrash_blue_chip.setTextColor(Color.GREEN);
	      	anticrash_text_players.setTextColor(Color.BLACK);
	      	anticrash_text_players.setBackgroundColor(Color.WHITE);
	      	
	      	Intent info = getIntent();
	      	first_player_name = info.getStringExtra(GameConstants.FIRST_PLAYER_NAME);
	      	second_player_name = info.getStringExtra(GameConstants.SECOND_PLAYER_NAME);
	      	Object[] params = new String[] { first_player_name, second_player_name};
	      	anticrash_text_players.setText(this.getString(R.string.first_player_name, params));
	      	anticrash_text_players.invalidate();
	      		      	
	    	first_part_game = 1;
		  	firstplayer_turn = FIRST_PLAYER;
		  	
		  	gameMessages = (TextView) findViewById(R.id.messages);
		  	gameMessages.setText("Game started");
		  	gameMessages.setBackgroundColor(Color.WHITE);
	    }

	
	boolean hasPossibilityToGetAnyChip()
	{
		int other_player = 0;
		if(firstplayer_turn == FIRST_PLAYER)
			other_player = 2;
		else
			other_player = 1;
		
		for(int i = 0;i < 3;++i)
			for(int j = 0 ;j < 8;++j)
				if(matrix[i][j] == other_player && !checkThree(i, j,other_player) )
						return true;
		return false;				
	}
	
	
	void putHelperOnSuccess(int index, int position, int player_on_turn)
	{
		
		 matrix[index][position] = player_on_turn;//set matrix 
		 if(player_on_turn == FIRST_PLAYER)
		 {
			 dama_board.setBackgroundWithImage(index, position, redchip);//set texview
			 counter_red--;
			 countPlayingRed++;
			//Animations.replace( anticrash_blue_chip, anticrash_redchip);
		 }
		 else
		 {
			 dama_board.setBackgroundWithImage(index, position, bluechip);
			 counter_blue--;
		  	 countPlayingBlue++;
		 }
		  sendMessageGame("put and doesnt have 3 no need to delete");
		  firstplayer_turn = player_on_turn == FIRST_PLAYER? SECOND_PLAYER : FIRST_PLAYER;//second playerturn
		  if(checkThree(index, position,player_on_turn))
		  {
			  firstplayer_turn = player_on_turn;//change player
			  need_delete = 1;
			  sendMessageGame("put and has 3 need to delete");
		  }
		  
	}
	
	
	boolean checkThree(int index, int position, int owner )
	{
		// playernumber == 1 || 2
		if(position % 2 == 0)//kraino pole
		{
			if	(   matrix[index][(position + 8) % 8] == owner && 
					matrix[index][(position + 9) % 8] == owner &&
					matrix[index][(position + 10 )% 8] == owner)
				return true;
			else if(matrix[index][(position + 8) % 8] == owner && 
					matrix[index][(position - 1 + 8) % 8] == owner &&
					matrix[index][(position - 2 + 8)% 8] == owner)
				return true;
		}
		else//number%2 == 1 sredno pole
		{
			if	(   matrix[index][(position + 7) % 8] == owner && 
					matrix[index][(position ) % 8] == owner &&
					matrix[index][(position + 9 )% 8] == owner)
				return true;
			if	(   matrix[(index + 1) % 3][position] == owner && 
					matrix[(index + 2) % 3][position] == owner &&
					matrix[    index      ][position] == owner)
				return true;
		}
		return false;
		
	}
	  

	void putInMatrix(Coords coord)
	{
	  int index = coord.index;
	  int position = coord.position;
	  
	  if(matrix[index][position] != 0)//if not posible to put chip
		  return;
	  putHelperOnSuccess(index, position, firstplayer_turn);
	}
	 
	
	void deleteHelper(int index, int position, int player_who_deletes)
	{
		matrix[index][position] = 0;
		dama_board.setBackgroundWithImage(index, position, cleanChip);
		sendMessageGame("success delete position: "+index + ", " + position);
		need_delete = 0;
		if(player_who_deletes == FIRST_PLAYER)
			countPlayingBlue--;//bug possible
		else
			countPlayingRed--;
		firstplayer_turn = firstplayer_turn == FIRST_PLAYER ? SECOND_PLAYER : FIRST_PLAYER;
	}
	

	boolean deletePosition(Coords coord, int player_who_deletes_color)
	{
		int index = coord.index;
		int position = coord.position;
		sendMessageGame("call deletes position");
				
		int second_player_number = firstplayer_turn == FIRST_PLAYER ? SECOND_PLAYER : FIRST_PLAYER;
		if(matrix[index][position] == 0)//still needs to delete
			return false;
		
		if(!hasPossibilityToGetAnyChip())
		{
			firstplayer_turn = firstplayer_turn == FIRST_PLAYER ? SECOND_PLAYER : FIRST_PLAYER;
			need_delete = 0;
			sendMessageGame("cant find possible delete");
		}
		
		if(matrix[index][position] == second_player_number && 
				checkThree(index,position,second_player_number))
			return false;
		
		if(matrix[index][position] == second_player_number )
		{
			deleteHelper(index, position, player_who_deletes_color);
			return true;
		}
		
		return false;
	}
	
	
	void firstPartOfTheGame(Coords selected)
	{
		if(first_part_game == 1)
		{
			if(need_delete == 1)//delete option
			{
				deletePosition(selected,firstplayer_turn);
				return;
			}
			if(counter_red + counter_blue > 0)
			{
				 putInMatrix(selected);
				 return;
			}
			else
			{
				first_part_game = 0;//end first part
				sendMessageGame("first part end");
			}
		}
	}


	void clear_board()
	{
		drawAvalable(selected_place_available_places, 0);
		dama_board.setBackgroundWithImage(selected_place, cleanChip); 
		selected_place_available_places = null;
		selected_place = null;
	}
	
	
	boolean jump_available(Coords selected)
	{
		boolean canMoveToSelected = false;
		if((countPlayingRed > 3 && firstplayer_turn == FIRST_PLAYER )||(countPlayingBlue > 3 && firstplayer_turn == SECOND_PLAYER))
			for(Coords c : selected_place_available_places) 
			{
				if(selected.equal_coords(c))
					canMoveToSelected = true;
			}
		else//less then 3 chips can move everywhere
			canMoveToSelected = true;
		
		return canMoveToSelected;
	}
	
	
	void secondPartOfTheGame(Coords selected)//given the selected space
	{	
		if(selected.index == -1)return;
		if (first_part_game != 1)
		{
			int current_player_color = firstplayer_turn == FIRST_PLAYER ? redchip:bluechip;//color chip selection
			
			if(need_delete == 1)  //deleting moment
			{
				deletePosition(selected,firstplayer_turn);
				sendMessageGame("call deletes position");
				return;
			}
			
			if(selected_place == null) /// not selected a pull
			{
				//first time click and select a space
				if( checkPossibleToSelectPullAndSave(selected) == true)
					sendMessageGame("selected space");
				return;
			}
			else if(selected_place != null && selected_place.equal_coords(selected)) /// raboti
			{
				//disselect if again tapped on the same item
				clear_board();				
				dama_board.setBackgroundWithImage(selected, current_player_color);// last position was deleted on select :)
				matrix[selected.index][selected.position] = firstplayer_turn;
				sendMessageGame("restored space");
				return;
			}
			else if(selected_place != null)
			{				
				if(jump_available(selected))/// get options to move for selected
				{							
					if(matrix[selected.index][selected.position] != 0) return;
					sendMessageGame("moving  success on position " + selected.index + " " + selected.position);
					matrix[selected.index][selected.position] = firstplayer_turn;
					matrix[selected_place.index][selected_place.position] = 0;
					moves_made.add(selected);
					clear_board();	
					dama_board.setBackgroundWithImage(selected, current_player_color);
					
					if(checkThree(selected.index, selected.position, firstplayer_turn))
					{
						need_delete = 1; //ne promenqme igracha, trqbva da iztiem nqkoi :)
					}
					else
						firstplayer_turn = firstplayer_turn == FIRST_PLAYER ? SECOND_PLAYER : FIRST_PLAYER;  //ako nqma 3ka promenqma igracha			
				}
				else//if cant move
				{
					//redraw if cant move
					Coords tmp = selected_place;
					clear_board();
					dama_board.setBackgroundWithImage(tmp, current_player_color); 
				}
				
			}
			}
		}
	
	
	void drawAvalable(ArrayList<Coords> available_pair, int avalable)
	{
		for(Coords p : available_pair)
		{
			if(avalable > 0)
				dama_board.setBackgroundWithImage(p, avalablechip);//show available
			else
				dama_board.setBackgroundWithImage(p,cleanChip); //clean available
		}
	}
	
	
	int getOwnerOfPair(Coords p)
	{
		return matrix[p.index][p.position];
	}
	
	
	ArrayList<Coords> get_available_moves(Coords selected)//raboti
	{
		Coords tmp = null;
		ArrayList<Coords> available_moves = new ArrayList<Coords>();
		
		if((countPlayingRed <= 3 && firstplayer_turn == FIRST_PLAYER )||(countPlayingBlue <= 3 && firstplayer_turn == SECOND_PLAYER))
		{
			for(int i=0;i<3;++i)
				for(int j = 0;j<8;++j)
				{
					if(matrix[i][j] == 0)
					{
						tmp = new Coords(i,j);
						available_moves.add(tmp);
					}
				}
			return available_moves;
		}
		
		
		int index = selected.index;
		int position = selected.position;
		
		if(matrix[index][(position + 1) % 8] == 0)
		{
			tmp = new Coords(index,(position + 1) % 8 );
			if(getOwnerOfPair(tmp) == 0)
				available_moves.add(tmp);
			
		}
		if(matrix[index][(position + 7) % 8] == 0)
		{
			tmp = new Coords(index,(position + 7) % 8 );
			if(getOwnerOfPair(tmp) == 0)
				available_moves.add(tmp);
		}
		// if not edge ->	
		if(position % 2 == 1)
		{
			if(index == 0 || index == 1)
			{
				tmp = new Coords(index+1,position);
				if(getOwnerOfPair(tmp) == 0)
					available_moves.add(tmp);
			}
			if(index == 1 || index == 2)
			{
				tmp = new Coords(index-1,position);
				if(getOwnerOfPair(tmp) == 0)
					available_moves.add(tmp);
			}
		}
		return available_moves;
	}
	
	
	boolean checkPossibleToSelectPullAndSave(Coords p)
	{
		if(p == null)
			return false;
		if(p.index == -1)
			return false;
		if(matrix[p.index][p.position] == firstplayer_turn)
		{
			selected_place = p;
			dama_board.setBackgroundWithImage(p, cleanChip);
			selected_place_available_places = get_available_moves(selected_place);
			drawAvalable(selected_place_available_places,1);
			return true;
		}
		else 
			return false;
	}


	void sendMessageGame(String mesg)
	{
		if(firstplayer_turn == FIRST_PLAYER)//message from ?
		{
			gameMessages.setText(  first_player_name + " " + mesg);
		}
		else
		{
			gameMessages.setText(  second_player_name + " " + mesg);
		}
		if(first_part_game == 1)//updating chips
		{
			anticrash_redchip.setText("hand:" + counter_red);
			anticrash_blue_chip.setText("hand:" + counter_blue);
		}
		else
		{
			anticrash_redchip.setText("playing:" + countPlayingRed);
			anticrash_blue_chip.setText("playing:" + countPlayingBlue);
		}
	
}

	
@Override
	public void onClick(View v) 
	{
		if(resign == true)
			return;// don't allow to click if game is ended so winner can't be changed.
		sendMessageGame("turn");
		if(v.getId() == resing_button.getId())
		{
			resign = true;
			if(firstplayer_turn == FIRST_PLAYER)
				counter_red = 0;//trick to finish game
			else
				counter_blue = 0;
			end_game();
		}
		Coords tmp = dama_board.getLastClick();
		
		if(tmp != null && tmp.index != -1)
		{
			if(first_part_game == 1)
			{
				firstPartOfTheGame(tmp);
			}
			else
			{
				if(playerHasPosibleMove() == false)//if possible to move 
					resign = true;
				check_draw();
				end_game(); //check possibility to continue the game
				secondPartOfTheGame(tmp);
				end_game();
			}
			dama_board.delLastClick();
			return;
		}
		
	}

}


