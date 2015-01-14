package com.game.damagame;



import com.game.database.PlayerContract.PlayerEntry;
import com.game.database.PlayerDatabaseHelper;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


public class LoginScreen extends Activity implements OnClickListener {
	 
	private EditText _first_player, _second_player, _increase_player;
	private PlayerDatabaseHelper _db;
	private Button  _saveToDataBase, _showRanking, _increase_player_wins;//, _deleteDB;
	private TextView _all_players;
	
	String[] projection = {
		    PlayerEntry._ID,
		    PlayerEntry.PLAYER_NAME,
		    PlayerEntry.PLAYER_WINS,
		    PlayerEntry.PLAYER_LOSES
		    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        _first_player = (EditText) findViewById(R.id.first_player_edit_text);
        _second_player = (EditText) findViewById(R.id.second_player_edittext);
        _saveToDataBase = (Button) findViewById(R.id.save_button);
       
        _showRanking = (Button) findViewById(R.id.show_button);
       // _all_players = (TextView) findViewById(R.id.result_textview);
      //  _increase_player = (EditText) findViewById(R.id.increase_name_edittext);
        _increase_player_wins = (Button) findViewById(R.id.increase_button);

        _increase_player_wins.setOnClickListener(this);
     //   _increase_player.setOnClickListener(this);
      //  _all_players.setOnClickListener(this);
        _showRanking.setOnClickListener(this);        
        _saveToDataBase.setOnClickListener(this);
        _first_player.setOnClickListener(this);
        _second_player.setOnClickListener(this);
        
        _db = new PlayerDatabaseHelper(this);
    }
    
    int player_names( String tyrseno_ime)
    { 
    	SQLiteDatabase db  = _db.getReadableDatabase();
    	
    	Cursor c = db.query(
				PlayerEntry.TABLE_NAME,  // The table to query
			    projection,                               // The columns to return
			    null,                                // The columns for the WHERE clause
			    null,                            // The values for the WHERE clause
			    null,                                     // don't group the rows
			    null,                                     // don't filter by row groups
			    null                                 // The sort order
			    );
    	
    	
    	c.moveToFirst();
		while(!c.isAfterLast())
		{
			String tmp_name = c.getString(c.getColumnIndexOrThrow(PlayerEntry.PLAYER_NAME));
			int id = c.getInt(c.getColumnIndexOrThrow(PlayerEntry._ID));
			if(tyrseno_ime.equals(tmp_name))
			{
				c.close();
		    	db.close();
				return id;
			}
			c.moveToNext();
		}
		c.close();
    	db.close();
    	return -1;
    }
    
    void savePlayer()
    {
    	SQLiteDatabase db = _db.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		ContentValues values2 = new ContentValues();
		
		values.put(PlayerEntry.PLAYER_NAME, _first_player.getText().toString());
		values.put(PlayerEntry.PLAYER_WINS, 1);
		values.put(PlayerEntry.PLAYER_LOSES,1);
		
		values2.put(PlayerEntry.PLAYER_NAME, _second_player.getText().toString());
		values2.put(PlayerEntry.PLAYER_WINS, 1);
		values2.put(PlayerEntry.PLAYER_LOSES,1);
		
		db.insert(PlayerEntry.TABLE_NAME, null, values);
		db.insert(PlayerEntry.TABLE_NAME, null, values2);
		db.close();
    }

    void showRanking()
    {
    	
    	SQLiteDatabase db  = _db.getReadableDatabase();

		String sortOrder = PlayerEntry.PLAYER_WINS + " DESC";
		Cursor c = db.query(
				PlayerEntry.TABLE_NAME,  // The table to query
			    projection,                               // The columns to return
			    null,                                // The columns for the WHERE clause
			    null,                            // The values for the WHERE clause
			    null,                                     // don't group the rows
			    null,                                     // don't filter by row groups
			    sortOrder                                 // The sort order
			    );
		 
		c.moveToFirst();
		
		_all_players.setText("count is " + c.getCount() +'\n');
		while(!c.isAfterLast())
		{
			String tmp_name = c.getString(c.getColumnIndexOrThrow(PlayerEntry.PLAYER_NAME));
			String tmp_wins = c.getString(c.getColumnIndexOrThrow(PlayerEntry.PLAYER_WINS));
			String tmp_loses = c.getString(c.getColumnIndexOrThrow(PlayerEntry.PLAYER_LOSES));
			String old = _all_players.getText().toString();
			_all_players.setText(old + tmp_name + " " + tmp_wins + " " + tmp_loses + '\n');
			c.moveToNext();
		}
		c.close();
		db.close();
    }
   
    int getIdByPlayername(String name)
    {
    	int p = player_names( "a");
    	if( p != -1)
    		_all_players.setText("uspehq da namereq id " + Integer.toString(p) );
    	else
    		_all_players.setText("error " + Integer.toString(p) );
    		
    	return p;
    }
    void changePlayerInfo( int id, String value)//taka raboti samo
    {
    	SQLiteDatabase db = _db.getWritableDatabase();
    	ContentValues cv = new ContentValues();
    	cv.put(PlayerEntry.PLAYER_WINS, value); /// ne
    	db.update(PlayerEntry.TABLE_NAME, cv, PlayerEntry.PLAYER_ID + " = " + Integer.toString(id) , null);
    	db.close();
    }
    void addPlayerInfoOnWin( String playername, String value)//taka raboti samo
    {
    	SQLiteDatabase db = _db.getWritableDatabase();
    	ContentValues cv = new ContentValues();
    	cv.put(PlayerEntry.PLAYER_WINS, value);
    	cv.put(PlayerEntry.PLAYER_LOSES, 0);
    	cv.put(PlayerEntry.PLAYER_NAME, playername);
    	db.insert(PlayerEntry.TABLE_NAME, null, cv);
    	///db.update(PlayerEntry.TABLE_NAME, cv, PlayerEntry.PLAYER_ID + " = " + Integer.toString(id) , null);
    	db.close();
    }
    
    
    
    @Override
	public void onClick(View v) {
		if(v.getId() == _first_player.getId())
		{
			_first_player.setText(null);
		}
		
		if(v.getId() == _second_player.getId())
		{
			_second_player.setText(null);
		}
	
		if(v.getId() == _saveToDataBase.getId())
		{
			 savePlayer();
		}
		
		if(v.getId() == _showRanking.getId())
		{
			Intent intent = new Intent(this, DamaGame.class);
			startActivity(intent);
		}
		if(v.getId() == _increase_player_wins.getId())
		{
			Intent intent = new Intent(this, PlayerRankingList.class);
			startActivity(intent);
		}
	}
}
