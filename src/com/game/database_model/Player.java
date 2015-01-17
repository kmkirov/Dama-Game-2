package com.game.database_model;

public class Player {
	
	private int mId;
	
	public int getmId() {
		return mId;
	}
	public void setmId(int mId) {
		this.mId = mId;
	}
	private String player_name;
	private int player_wins;
	private int player_loses;
	
	
	public Player(int mId, String player_name, int player_wins,
			int player_loses) 
	{
		
		this.mId = mId;
		this.player_name = player_name;
		this.player_wins = player_wins;
		this.player_loses = player_loses;
	}
	public String getPlayer_name() {
		return player_name;
	}
	public void setPlayer_name(String player_name) {
		this.player_name = player_name;
	}
	public int getPlayer_wins() {
		return player_wins;
	}
	public void setPlayer_wins(int player_wins) {
		this.player_wins = player_wins;
	}
	public int getPlayer_loses() {
		return player_loses;
	}
	public void setPlayer_loses(int player_loses) {
		this.player_loses = player_loses;
	}
	}
