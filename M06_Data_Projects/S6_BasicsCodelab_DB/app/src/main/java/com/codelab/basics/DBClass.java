package com.codelab.basics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class DBClass extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 5; // Updated version
    private static final String DATABASE_NAME = "PokemonDB.db";
    private final Context context;

    public DBClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DBClass", "Creating new database...");

        db.execSQL("CREATE TABLE pokemon_table (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR(256), number INTEGER, type TEXT, evolution TEXT, SpriteName TEXT, access_count INTEGER DEFAULT 0)");

        populateDatabaseFromJSON(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DBClass", "Upgrading database from version " + oldVersion + " to " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS pokemon_table");
        onCreate(db);
    }

    private void populateDatabaseFromJSON(SQLiteDatabase db) {
        try {
            String jsonString = loadJSONFromAsset("Pokedex.json");
            JSONArray pokemons = new JSONArray(jsonString);

            for (int i = 0; i < pokemons.length(); i++) {
                JSONObject pokemon = pokemons.getJSONObject(i);
                String name = pokemon.getString("Name");
                int number = pokemon.getInt("Number");
                String type = pokemon.getString("Type");
                String evolution = pokemon.optString("Evolution", "Does not evolve");
                String SpriteName = "p" + number;

                ContentValues values = new ContentValues();
                values.put("name", name);
                values.put("number", number);
                values.put("type", type);
                values.put("evolution", evolution);
                values.put("SpriteName", SpriteName);

                db.insert("pokemon_table", null, values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String loadJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void incrementAccessCount() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE pokemon_table SET access_count = access_count + 1");
        Log.d("DBClass", "Access count incremented");
    }

    public String[][] getPokemonRecords() {
        incrementAccessCount();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {"id", "name", "number", "type", "evolution", "SpriteName", "access_count"};
        Cursor c = db.query(
                "pokemon_table",
                projection,
                null,
                null,
                null,
                null,
                "number ASC"
        );

        String[][] records = new String[c.getCount()][projection.length];
        int i = 0;
        while (c.moveToNext()) {
            for (int j = 0; j < projection.length; j++) {
                records[i][j] = c.getString(j);
            }
            i++;
        }
        c.close();
        return records;
    }
}
