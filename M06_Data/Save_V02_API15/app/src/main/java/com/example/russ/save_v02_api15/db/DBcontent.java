package com.example.russ.save_v02_api15.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Russ on 2/5/2016.
 */
public class DBcontent {

    public boolean data_is_set = false;

    /**
     * An array of items.
     */
//    public static List<AnimalItem> ITEMS = new ArrayList<AnimalItem>();
    public static List<AnimalItem> ITEMS;
    public List<AnimalItem> getITEMS() {
        return ITEMS;
    }

    /**
     * A map of items, by ID.
     */
//    public static Map<String, AnimalItem> ITEM_MAP = new HashMap<String, AnimalItem>();
    public static Map<String, AnimalItem> ITEM_MAP;
    public Map<String, AnimalItem> getITEM_MAP() {
        return ITEM_MAP;
    }


    // If you update DATABASE_VERSION, then the DB's onUpgrade() will be called
    private static final String DATABASE_NAME = "AnimalDB";
    private static final int DATABASE_VERSION = 2;

    Context context;
    handleDB dbRaw;

    // Empty Constructor
    public DBcontent() {
    }

    // Constructor to create DB
    public DBcontent(Context context) {
        Log.v("DBcontent", "DBcontent constructor");
        dbRaw = new handleDB(context, DATABASE_NAME, null, DATABASE_VERSION);

        // Add some sample items.
        if (dbRaw.getCount() > 1) {
            Log.v("DBcontent", "DBcontent already rows in DB");

        } else {
            Log.v("DBcontent", "DBcontent no rows in DB...add some");
            AnimalItem a = new AnimalItem("1", "dog", "Tucker is an awesome puppy.");
            dbRaw.addAnimal(a);
            a = new AnimalItem("2", "cat", "Pumkin is an awesome cat.");
            dbRaw.addAnimal(a);
        }

        ITEMS = dbRaw.getAllAnimalItems();
        ITEM_MAP = dbRaw.getAllAnimalItemDetails();

        data_is_set = true;

        Log.v("DBcontent", "ITEMS=" + ITEMS.toString());
        Log.v("DBcontent", "ITEM_MAP=" + ITEM_MAP.toString());
    }

    // Handle closing the DB
    public void onStop() {
        dbRaw.close();
    }


    public int getSize() {
        return dbRaw.getCount();
    }

    /**
     * A Animal item representing a piece of content.
     */
    public class AnimalItem {
        public final String id;
        public final String name;
        public final String details;

        public AnimalItem(String id, String name, String details) {
            this.id = id;
            this.name = name;
            this.details = details;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
