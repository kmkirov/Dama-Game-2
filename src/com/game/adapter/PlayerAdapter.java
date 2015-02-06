package com.game.adapter;

import java.util.ArrayList;


import com.game.damagame.R;
import com.game.database_model.Player;


import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class PlayerAdapter extends BaseAdapter {

	private ArrayList<Player> _players;
	private Context _context;
	public PlayerAdapter(Context context, ArrayList<Player> players)
	{
		_context = context;
		_players = players;
	}
	@Override
	public int getCount() 
	{
		
		return _players.size();
	}

	@Override
	public Player getItem(int position) 
	{
		return _players.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;
		if(convertView == null)
		{
			LayoutInflater inflater = LayoutInflater.from(_context);//parrent.getContext()
			convertView = inflater.inflate(R.layout.list_item_ranking, parent, false);
			
			holder = new ViewHolder();
			holder.player_name = (TextView) convertView.findViewById(R.id.playername_textview);
			holder.player_stats = (TextView) convertView.findViewById(R.id.stats_textview);
			convertView.setTag(holder);		
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		Player current = getItem(position);
		
		holder.player_name.setText(current.getPlayer_name());
		holder.player_name.setTextColor(Color.GREEN);
		holder.player_name.setGravity(Gravity.CENTER);
		holder.player_name.setBackgroundColor(Color.GRAY);
		
		holder.player_name.setAlpha(0.7f);
		
		holder.player_stats.setText("wins: " + current.getPlayer_wins() + " looses: " + current.getPlayer_loses());
		holder.player_stats.setTextColor(Color.GREEN);
		holder.player_stats.setGravity(Gravity.CENTER);
		holder.player_stats.setBackgroundColor(Color.GRAY);
		holder.player_stats.setAlpha(0.7f);
		
		if(position == 0)// || current.getPlayer_wins == getItem(0)  // first player
		{
			holder.player_name.setTextColor(Color.BLUE);
			holder.player_name.setBackgroundColor(Color.YELLOW);
			holder.player_name.setAlpha(0.6f);
			
			holder.player_stats.setTextColor(Color.BLUE);
			holder.player_stats.setBackgroundColor(Color.YELLOW);
			holder.player_stats.setAlpha(0.6f);
		}
		
		return convertView;
	}
	private class ViewHolder
	{
		TextView player_name;
		TextView player_stats;//win and loose
	}
}
