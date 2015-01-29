package com.game.database;


import com.game.constants.GameConstants;
import com.game.database.PlayerContract.PlayerEntry;
import com.game.database_model.Player;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlayerDatabaseHelper extends SQLiteOpenHelper
{
	static String[] projection = {
		    PlayerEntry._ID,
		    PlayerEntry.PLAYER_NAME,
		    PlayerEntry.PLAYER_WINS,
		    PlayerEntry.PLAYER_LOSES
		    };
	
	private static PlayerDatabaseHelper sInstance;
	
	public static PlayerDatabaseHelper getInstance(Context context)
	{
	    if (sInstance == null) 
	    {
	      sInstance = new PlayerDatabaseHelper(context.getApplicationContext());
	    }
	    return sInstance;
	}
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = " , ";
	private static final String SQL_CREATE_ENTRIES =
	    "CREATE TABLE " + PlayerEntry.TABLE_NAME + " (" +
	    PlayerEntry._ID + " INTEGER PRIMARY KEY , " +
	    PlayerEntry.PLAYER_NAME + TEXT_TYPE +   " UNIQUE " + COMMA_SEP +
	    PlayerEntry.PLAYER_WINS + TEXT_TYPE + COMMA_SEP +
	    PlayerEntry.PLAYER_LOSES + TEXT_TYPE  +
	    " );";

	private static final String SQL_DELETE_ENTRIES =
	    "DROP TABLE IF EXISTS " + PlayerEntry.TABLE_NAME;

	
	public PlayerDatabaseHelper(Context context) 
	{
		super(context, PlayerContract.DATABASE_NAME, null, PlayerContract.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		db.execSQL(SQL_CREATE_ENTRIES);		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		db.execSQL(SQL_DELETE_ENTRIES);
		
		db.execSQL(SQL_CREATE_ENTRIES);
	}
	
	static public Player player_names(Context context, String tyrseno_ime)
    { 
	   SQLiteDatabase db;
	   if(sInstance != null)
		    db  = sInstance.getReadableDatabase();
	   else 
		   db = PlayerDatabaseHelper.getInstance(context).getReadableDatabase();
    	
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
			String tmp_wins = c.getString(c.getColumnIndexOrThrow(PlayerEntry.PLAYER_WINS));
			String tmp_looses = c.getString(c.getColumnIndexOrThrow(PlayerEntry.PLAYER_LOSES));
			
			int id = c.getInt(c.getColumnIndexOrThrow(PlayerEntry._ID));
			if(tyrseno_ime.equals(tmp_name))
			{
				c.close();
		    	Player player = new Player(id, tmp_name, Integer.parseInt(tmp_wins),Integer.parseInt(tmp_looses));
				return player;//instead of id
			}
			c.moveToNext();
		}
		c.close();
    	
    	return null;
    	
    }

	static public int getIdByPlayername(Context context ,String name)
    {
    	return  player_names(context, name).getmId();
    }
	   

	   static public void addPlayerInfoOnWin(Context context, String playername, String value, int typeOfChange)//taka raboti samo
	   {
	    	SQLiteDatabase db;
	    	if(sInstance != null)
			    db  = sInstance.getReadableDatabase();
		   else 
			   db = PlayerDatabaseHelper.getInstance(context).getReadableDatabase();
	    	
	    	ContentValues cv = new ContentValues();
	    	Player pl = player_names(context, playername);
	    	if(typeOfChange == GameConstants.WINS_TYPE)
	    	{
	    		cv.put(PlayerEntry.PLAYER_WINS, pl.getPlayer_wins() + 1);
		    	cv.put(PlayerEntry.PLAYER_LOSES, pl.getPlayer_loses());
		    	cv.put(PlayerEntry.PLAYER_NAME, playername);
	    	}
	    	if(typeOfChange == GameConstants.LOOSES_TYPE)
	    	{
	    		cv.put(PlayerEntry.PLAYER_WINS, pl.getPlayer_wins());
		    	cv.put(PlayerEntry.PLAYER_LOSES, pl.getPlayer_loses() + 1);
		    	cv.put(PlayerEntry.PLAYER_NAME, playername);
	    	}
	    	//db.insert(PlayerEntry.TABLE_NAME, null, cv);
	    	db.update(PlayerEntry.TABLE_NAME, cv, PlayerEntry.PLAYER_ID + " = " + Integer.toString(getIdByPlayername(context,playername)) , null);
	    	db.close();
	   }

}

