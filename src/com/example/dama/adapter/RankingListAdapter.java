package com.example.dama.adapter;
import com.example.dama.*;
import com.example.dama.adapter.Player;
import java.util.ArrayList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class RankingListAdapter extends BaseAdapter {
	
	static class Holder
	{
		public TextView name;
		public TextView wins;
		public TextView loses;
	}
	private ArrayList<Player> players;
	
	public RankingListAdapter( ArrayList<Player> players)
	{
		
		this.players = players;
	} 
	@Override
	public int getCount() {
		
		return players.size();
	}

	@Override
	public Player getItem(int arg0) {
		
		return players.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
	
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View rowView = convertView;
		if(rowView == null)
		{
			
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			rowView = inflater.inflate(R.layout.cell_ranking, parent, false);
			Holder holder = new Holder();
			holder.name = (TextView) rowView.findViewById(R.id.textview_username_name);
			holder.wins = (TextView) rowView.findViewById(R.id.textview_username_wins);
			holder.loses = (TextView) rowView.findViewById(R.id.textview_username_loses);
			rowView.setTag(holder);
		}
		Holder holder =(Holder) rowView.getTag();
		
		Log.v("game",position + " " + players.get(position).getPlayername() +"  " +
		players.get(position).getWins() + " " + players.size());
		Player plr = getItem(position);
		holder.name.setText(plr.getPlayername());
		holder.wins.setText(plr.getWins());
		holder.loses.setText(plr.getLoses());
		return rowView;
	}
	
}
