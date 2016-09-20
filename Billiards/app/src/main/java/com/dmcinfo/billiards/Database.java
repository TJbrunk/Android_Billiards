package com.dmcinfo.billiards;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by ottog on 3/15/2016.
 */
public class Database extends SQLiteOpenHelper {
    private static final  int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Billiards.sqlite3";
    protected static final String PLAYER_TABLE = "Players";

    private static final String CREATE_PLAYER_TABLE = "create table if not exists " +
            PLAYER_TABLE +
            " ( _id integer primary key autoincrement," +
            " FirstName TEXT NOT NULL UNIQUE," +
            " LastName TEXT NOT NULL," +
            " Mu REAL NOT NULL," +
            " Sigma REAL NOT NULL);" ;

   // private static final String DROP_PLAYER_TABLE = "drop table if exists " + PLAYER_TABLE;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //context.deleteDatabase(DATABASE_NAME); Uncomment this if you need to delete the database
    };

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PLAYER_TABLE);
    };

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //not sure what we want to do here, but this must be implemented
    };
};

class DBPlayer extends Database {
    public DBPlayer (Context context) {
        super(context);
    }

    private static final String COL_ID = "_id";
    private static final String COL_FIRSTNAME = "FirstName";
    private static final String COL_LASTNAME = "LastName";
    private static final String COL_MU = "Mu";
    private static final String COL_SIGMA = "Sigma";


    public boolean addPlayer(String FirstName, String LastName ){
        if(!playerExists(FirstName,LastName)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(COL_FIRSTNAME, FirstName);
            values.put(COL_LASTNAME, LastName);
            values.put(COL_MU, 50.0);
            values.put(COL_SIGMA, 0);
            db.insert(PLAYER_TABLE, null, values);
            db.close();

            return true;
        }
        return false;
    };

    public String getPlayer(int Row){
        String name = "none";
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM "+ PLAYER_TABLE +" WHERE _id=" + Row;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){

            do {
                name = cursor.getString(1);
            } while (cursor.moveToNext());
        }

        db.close();

        return name;
    }

    public ArrayList getAllPlayers(){
        ArrayList players = new ArrayList();
        String selectQuery;
        Cursor cursor;

        SQLiteDatabase db = this.getReadableDatabase();

        selectQuery = "SELECT * FROM "+PLAYER_TABLE + " ORDER BY "+ COL_FIRSTNAME;
        cursor = db.rawQuery(selectQuery, null);

        players.add(""); // Add blank player so that none can be selected
        if (cursor.moveToFirst()){
            do {
                players.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        return players;

    }

    public Map getPlayerStats(int pk) {
        Map<String, Float> stats = new HashMap<String, Float>();
        float mu, sigma;
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM "+PLAYER_TABLE+" WHERE _id="+pk;

        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        mu = cursor.getFloat(3);
        sigma = cursor.getFloat(4);

        stats.put("mu",mu );
        stats.put("sigma", sigma);

        return stats;

    }

    public Map getPlayerStats(String first_name) {
        Map<String, Double> stats = new HashMap<String, Double>();
        Double mu, sigma;
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM "+PLAYER_TABLE+" WHERE "+COL_FIRSTNAME+" = '"+first_name+"'";
        selectQuery = "SELECT * FROM Players";

        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        mu = cursor.getDouble(3);
        sigma = cursor.getDouble(4);

        stats.put("mu", mu);
        stats.put("sigma", sigma);

        return stats;

    }

    public boolean playerExists(String FName, String LName){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + PLAYER_TABLE + " WHERE FirstName = ? AND LastName = ?";
        Cursor c = db.rawQuery(query, new String[] {FName,LName});
        if (c.moveToFirst())
        {
            return c.getInt(0) > 0;
        }
        else {
            return false;
        }
    }
}

