package com.example.dama.adapter;


import java.util.ArrayList;
import com.example.dama.*;
import com.example.dama.database.DBContract.PlayerEntry;
import com.example.dama.database.PlayersDatabaseHelper;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;


public class RankingList extends Activity 
{
	
	private RankingListAdapter adapter;
	
	private static final String[] PLAYERS_STATS = new String[]{
		PlayerEntry._ID,
		PlayerEntry.COLUMN_NAME_PLAYER,
		PlayerEntry.COLUMN_NAME_WINS,
		PlayerEntry.COLUMN_NAME_LOSES};
	private ListView lista;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		//open db with players
		lista = (ListView) findViewById(R.id.listview);
		ArrayList<Player> players = new ArrayList<Player>();
		
		PlayersDatabaseHelper helper = new PlayersDatabaseHelper(this);
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query(PlayerEntry.TABLE_NAME, PLAYERS_STATS, null, null, null, null, null);
		
		try 
		{
			cursor.moveToFirst();
			while(!cursor.isAfterLast())
			{
				long id = cursor.getLong(cursor.getColumnIndexOrThrow(PlayerEntry._ID));
				if(id != -1)
				{
					String player_name = cursor.getString(cursor.getColumnIndexOrThrow(PlayerEntry.COLUMN_NAME_PLAYER));
					int wins = cursor.getInt(cursor.getColumnIndexOrThrow(PlayerEntry.COLUMN_NAME_WINS));
					int loses = cursor.getInt(cursor.getColumnIndexOrThrow(PlayerEntry.COLUMN_NAME_LOSES));
					Player tmp = new Player(player_name, wins, loses);
					players.add(tmp);
				}
				cursor.moveToNext();
				
			}
		}
		finally 
		{
			cursor.close();
		}		
		Log.v("gameproblem"," " + players.size());
		adapter = new RankingListAdapter( players);
		lista.setAdapter(adapter);
		 
		 
	}
}
