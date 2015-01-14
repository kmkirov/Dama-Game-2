package com.game.database;


import com.game.database.PlayerContract.PlayerEntry;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlayerDatabaseHelper extends SQLiteOpenHelper
{
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
		// TODO Auto-generated method stub
		db.execSQL(SQL_CREATE_ENTRIES);
	}

}
