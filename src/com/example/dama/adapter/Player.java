package com.example.dama.adapter;

public class Player {

	private String playername;
	private int wins;
	private int loses;
	Player(String name, int w, int l)
	{
		playername = name;
		wins = w;
		loses = l;
	}
	public String getPlayername() {
		return playername;
	}
	public void setPlayername(String playername) {
		this.playername = playername;
	}
	public int getWins() {
		return wins;
	}
	public void setWins(int wins) {
		this.wins = wins;
	}
	public int getLoses() {
		return loses;
	}
	public void setLoses(int loses) {
		this.loses = loses;
	}
}
