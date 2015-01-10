package com.example.dama.database;

import com.example.dama.database.DBContract.PlayerEntry;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlayersDatabaseHelper extends SQLiteOpenHelper
{
	 private static final String DATABASE_CREATE = "create table "
	      + PlayerEntry.TABLE_NAME + "(" 
	      + PlayerEntry._ID  + " integer primary key autoincrement, "
	      + PlayerEntry.COLUMN_NAME_PLAYER + " TEXT UNIQUE,"
	      + PlayerEntry.COLUMN_NAME_WINS +" integer,"
	      + PlayerEntry.COLUMN_NAME_LOSES + " integer);";


	public PlayersDatabaseHelper(Context context) 
	{
		super(context, DBContract.DATABASE_NAME, null, DBContract.DATABASE_VERSION);
		
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		db.execSQL(DATABASE_CREATE);
	}

	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL("DROP TABLE IF EXISTS " + PlayerEntry.TABLE_NAME);
		onCreate(db);
	}

}
