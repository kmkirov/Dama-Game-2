package com.game.damagame;


import com.game.constants.GameConstants;
import com.game.database.PlayerContract.PlayerEntry;
import com.game.database.PlayerDatabaseHelper;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class LoginScreen extends Activity implements OnClickListener {
    String[] projection = {
            PlayerEntry._ID,
            PlayerEntry.PLAYER_NAME,
            PlayerEntry.PLAYER_WINS,
            PlayerEntry.PLAYER_LOSES
    };
    private EditText _first_player, _second_player;
    private PlayerDatabaseHelper _db;
    private Button _showRanking, _start_game;//, _deleteDB;
    private TextView _all_players;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        _first_player = (EditText) findViewById(R.id.first_player_edit_text);
        _second_player = (EditText) findViewById(R.id.second_player_edittext);

        _showRanking = (Button) findViewById(R.id.ranking_button);
        _start_game = (Button) findViewById(R.id.play_button);
        _start_game.setOnClickListener(this);
        _showRanking.setOnClickListener(this);
        _first_player.setOnClickListener(this);
        _second_player.setOnClickListener(this);
        _db = PlayerDatabaseHelper.getInstance(this);
    }


    public void savePlayer() {

        ContentValues values = new ContentValues();
        ContentValues values2 = new ContentValues();

        if (PlayerDatabaseHelper.player_names(this, _first_player.getText().toString()) == null) {
            SQLiteDatabase db = _db.getWritableDatabase();
            values.put(PlayerEntry.PLAYER_NAME, _first_player.getText().toString());
            values.put(PlayerEntry.PLAYER_WINS, 0);
            values.put(PlayerEntry.PLAYER_LOSES, 0);
            db.insert(PlayerEntry.TABLE_NAME, null, values);


        }
        if (PlayerDatabaseHelper.player_names(this, _second_player.getText().toString()) == null) {
            SQLiteDatabase db = _db.getWritableDatabase();
            values2.put(PlayerEntry.PLAYER_NAME, _second_player.getText().toString());
            values2.put(PlayerEntry.PLAYER_WINS, 0);
            values2.put(PlayerEntry.PLAYER_LOSES, 0);
            db.insert(PlayerEntry.TABLE_NAME, null, values2);

        }

    }

    public void showRanking() {

        SQLiteDatabase db = _db.getReadableDatabase();

        String sortOrder = PlayerEntry.PLAYER_WINS + " DESC";
        Cursor c = db.query(
                PlayerEntry.TABLE_NAME,                  // The table to query
                projection,                               // The columns to return
                null,                                      // The columns for the WHERE clause
                null,                                      // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        db.close();
        c.moveToFirst();

        _all_players.setText("count is " + c.getCount() + '\n');
        while (!c.isAfterLast()) {
            String tmp_name = c.getString(c.getColumnIndexOrThrow(PlayerEntry.PLAYER_NAME));
            String tmp_wins = c.getString(c.getColumnIndexOrThrow(PlayerEntry.PLAYER_WINS));
            String tmp_loses = c.getString(c.getColumnIndexOrThrow(PlayerEntry.PLAYER_LOSES));
            String old = _all_players.getText().toString();
            _all_players.setText(old + tmp_name + " " + tmp_wins + " " + tmp_loses + '\n');
            c.moveToNext();
        }
        c.close();

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == _first_player.getId()) {
            _first_player.setText(null);
        }

        if (v.getId() == _second_player.getId()) {
            _second_player.setText(null);
        }


        if (v.getId() == _start_game.getId()) {
            Intent intent = new Intent(this, DamaGame.class);
            savePlayer();
            intent.putExtra(GameConstants.FIRST_PLAYER_NAME, _first_player.getText().toString());
            intent.putExtra(GameConstants.SECOND_PLAYER_NAME, _second_player.getText().toString());
            startActivity(intent);
        }
        if (v.getId() == _showRanking.getId()) {
            Intent intent = new Intent(this, PlayerRankingList.class);
            _db.close();
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

    }
}
