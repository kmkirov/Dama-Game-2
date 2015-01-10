package com.example.dama;

import com.example.dama.adapter.RankingList;
import com.example.dama.database.DBContract.PlayerEntry;
import com.example.dama.database.PlayersDatabaseHelper;
import android.support.v7.app.ActionBarActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements OnClickListener 
{	
	private Button play_game_button;
	private Button ranking_button;
	private Button register_players_button;
	private EditText first_player_edittext;
	private EditText second_player_edittext;
	private TextView first_player_text;
	private TextView second_player_text;
	private final String mesg_intent = "Player Names";
	
	private PlayersDatabaseHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        play_game_button = (Button) findViewById(R.id.button_playgame);
    	ranking_button = (Button) findViewById(R.id.button_ranking);
    	register_players_button = (Button) findViewById(R.id.button_register);
     
    	first_player_edittext = (EditText) findViewById(R.id.edittext_firstusername);
    	second_player_edittext = (EditText) findViewById(R.id.edittext_secondusername);
    	
    	first_player_text = (TextView) findViewById(R.id.textview_firstusername);
    	second_player_text = (TextView) findViewById(R.id.textview_secondusername);
    	
    	play_game_button.setOnClickListener(this);
    	ranking_button.setOnClickListener(this);
    	register_players_button.setOnClickListener(this);
    	helper = new PlayersDatabaseHelper(this);
    	
    }
    
    
	public void startGame(String user1, String user2)
	{
		Intent game = new Intent(this, DamaGame.class);
		game.putExtra(mesg_intent,first_player_edittext.getText().toString()+" "+ second_player_edittext.getText().toString());
		startActivity(game);
	}
	
	
	public void showRanking()
	{
		Intent ranks = new Intent(this, RankingList.class);
		startActivity(ranks);
	}
	public void registerPlayer()
    {	
		
    	
    	ContentValues value = new ContentValues();
    	value.put(PlayerEntry.COLUMN_NAME_PLAYER,
    			first_player_edittext.getText().toString());
    	value.put(PlayerEntry.COLUMN_NAME_WINS, 0);
    	value.put(PlayerEntry.COLUMN_NAME_LOSES, 0);
    	
    	SQLiteDatabase db = helper.getWritableDatabase();
    	long ids = db.insert(PlayerEntry.TABLE_NAME, null, value);
    	if(ids == -1)
    	{
    		register_players_button.setBackgroundColor(0xfff00000);
    	}
    	else
    	{
    		register_players_button.setBackgroundColor(0x000fff00);
    	}
    }
	@Override
	public void onClick(View v) 
	{
		if(v.getId() == play_game_button.getId())
		{
			startGame(first_player_text.getText().toString(), second_player_text.getText().toString());
		}
		else if(v.getId() == ranking_button.getId())
		{
			 showRanking();
		}
		else if(v.getId() == register_players_button.getId())
		{
			 registerPlayer();
		}
	}
}
