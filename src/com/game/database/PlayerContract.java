package com.game.database;

import android.provider.BaseColumns;

public class PlayerContract {

	public static final String DATABASE_NAME = "Players21.db";
	public static final int DATABASE_VERSION = 1;
	private PlayerContract(){;}
	
	public static class PlayerEntry implements BaseColumns
	{
		public static final String TABLE_NAME = "Ranking";
		public static final String PLAYER_ID = "_ID";
		public static final String PLAYER_NAME = "playername"; 
		public static final String PLAYER_WINS = "wins";
		public static final String PLAYER_LOSES = "loses";
		
		private PlayerEntry(){}
	}
}