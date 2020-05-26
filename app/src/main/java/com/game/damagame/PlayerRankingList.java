package com.game.damagame;

import java.util.ArrayList;
import java.util.Collections;

import com.game.adapter.PlayerAdapter;
import com.game.database.PlayerContract.PlayerEntry;
import com.game.database.PlayerDatabaseHelper;
import com.game.database_model.Player;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class PlayerRankingList extends ListActivity {
    String[] projection = {
            PlayerEntry._ID,
            PlayerEntry.PLAYER_NAME,
            PlayerEntry.PLAYER_WINS,
            PlayerEntry.PLAYER_LOSES
    };
    private PlayerAdapter _adapter;
    private PlayerDatabaseHelper _db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_ranking);

        ArrayList<Player> players = new ArrayList<Player>();
        _db = new PlayerDatabaseHelper(this);

        SQLiteDatabase db = _db.getReadableDatabase();
        String sortOrder = PlayerEntry.PLAYER_WINS;//+ " DESC";
        Cursor c = db.query(
                PlayerEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        c.moveToFirst();


        while (!c.isAfterLast()) {


            int id = c.getInt(c.getColumnIndexOrThrow(PlayerEntry._ID));
            String tmp_name = c.getString(c.getColumnIndexOrThrow(PlayerEntry.PLAYER_NAME));
            String tmp_wins = c.getString(c.getColumnIndexOrThrow(PlayerEntry.PLAYER_WINS));
            String tmp_loses = c.getString(c.getColumnIndexOrThrow(PlayerEntry.PLAYER_LOSES));
            Player p = new Player(id, tmp_name, Integer.parseInt(tmp_wins), Integer.parseInt(tmp_loses));
            players.add(p);
            c.moveToNext();
        }
        c.close();
        db.close();
        // fixing the bug with order
        for (int i = 0; i < players.size() - 1; ++i)
            for (int j = i + 1; j < players.size(); ++j)
                if (players.get(j).getPlayer_wins() > players.get(i).getPlayer_wins())
                    Collections.swap(players, i, j);

        _adapter = new PlayerAdapter(this, players);
        this.setListAdapter(_adapter);


    }
}
