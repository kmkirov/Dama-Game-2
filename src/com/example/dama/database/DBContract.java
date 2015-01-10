package com.example.dama.database;

import android.provider.BaseColumns;

public class DBContract 
{
	public static String DATABASE_NAME = "PlayersRanking.db";
	public static int DATABASE_VERSION = 1;
	
	
	private DBContract()//Forbidden
	{
	}
	
	
	public static class PlayerEntry implements BaseColumns
	{ 
		public static final String TABLE_NAME = "Ranking";
		public static final String COLUMN_NAME_PLAYER = "Player";
		public static final String COLUMN_NAME_WINS = "Wins";
		public static final String COLUMN_NAME_LOSES = "Loses";
		
		PlayerEntry()//Forbidden
		{}
	}
}
