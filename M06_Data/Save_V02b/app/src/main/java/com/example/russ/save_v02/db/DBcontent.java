package com.example.russ.save_v02.db;

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


//    private static final int COUNT = 25;
    private static final String DATABASE_NAME = "AnimalDB";
    private static final int DATABASE_VERSION = 2;

    Context context;
    handleDB dbRaw;

    // Empty Constructor
    public DBcontent() {
    }

    // Constructor to create DB
    public DBcontent(Context context) {

        Log.v("handleDB", "DBcontent constructor");


        // Sleep and ensure a threading issue
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dbRaw = new handleDB(context, DATABASE_NAME, null, DATABASE_VERSION);

        // Add some sample items.
        if (dbRaw.getCount() > 3) {
            Log.v("handleDB", "DBcontent already rows in DB");

        } else {
            Log.v("handleDB", "DBcontent no rows in DB...add some");

            // NOTE: id not used on insert, auto-increment takes care of it
            dbRaw.addAnimal(new AnimalItem("1", "dog", "Tucker is an awesome puppy."));
            dbRaw.addAnimal(new AnimalItem("1", "dog", "Tucker is an awesome puppy."));
            dbRaw.addAnimal(new AnimalItem("1", "dog", "Tucker is an awesome puppy."));
            dbRaw.addAnimal(new AnimalItem("1", "dog", "Tucker is an awesome puppy."));
            dbRaw.addAnimal(new AnimalItem("1", "dog", "Tucker is an awesome puppy."));
            dbRaw.addAnimal(new AnimalItem("1", "dog", "Tucker is an awesome puppy."));
            dbRaw.addAnimal(new AnimalItem("1", "dog", "Tucker is an awesome puppy."));
            dbRaw.addAnimal(new AnimalItem("1", "dog", "Tucker is an awesome puppy."));
            dbRaw.addAnimal(new AnimalItem("1", "dog", "Tucker is an awesome puppy."));
            dbRaw.addAnimal(new AnimalItem("1", "dog", "Tucker is an awesome puppy."));
            dbRaw.addAnimal(new AnimalItem("1", "dog", "Tucker is an awesome puppy."));
            dbRaw.addAnimal(new AnimalItem("1", "dog", "Tucker is an awesome puppy."));
            dbRaw.addAnimal(new AnimalItem("1", "dog", "Tucker is an awesome puppy."));
            dbRaw.addAnimal(new AnimalItem("1", "dog", "Tucker is an awesome puppy."));
            dbRaw.addAnimal(new AnimalItem("1", "dog", "Tucker is an awesome puppy."));
            dbRaw.addAnimal(new AnimalItem("1", "dog", "Tucker is an awesome puppy."));
            dbRaw.addAnimal(new AnimalItem("1", "dog", "Tucker is an awesome puppy."));
            dbRaw.addAnimal(new AnimalItem("1", "dog", "Tucker is an awesome puppy."));
            dbRaw.addAnimal(new AnimalItem("1", "dog", "Tucker is an awesome puppy."));
            dbRaw.addAnimal(new AnimalItem("1", "dog", "Tucker is an awesome puppy."));
            dbRaw.addAnimal(new AnimalItem("1", "dog", "Tucker is an awesome puppy."));
        }

        ITEMS = dbRaw.getAllAnimalItems();
        ITEM_MAP = dbRaw.getAllAnimalItemDetails();

        data_is_set = true;

        Log.v("handleDB", ITEMS.toString());
        Log.v("handleDB", ITEM_MAP.toString());

//        for (int i = 1; i <= COUNT; i++) {
//            addItem(createDummyItem(i));
//        }
    }

    public int getSize(){
        return dbRaw.getCount();
    }


    // class was using static method to initialize
//    static {
//        // Add some sample items.
//        for (int i = 1; i <= COUNT; i++) {
//            addItem(createDummyItem(i));
//        }
//    }

//    private void addItem(AnimalItem item) {
//        ITEMS.add(item);
//        ITEM_MAP.put(item.id, item);
//    }

//    private AnimalItem createDummyItem(int position) {
//        return new AnimalItem(String.valueOf(position), "Animal " + position, makeDetails(position));
//    }

//    private String makeDetails(int position) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("Details about Animal: ").append(position);
//        for (int i = 0; i < position; i++) {
//            builder.append("\nMore animal details.");
//        }
//        return builder.toString();
//    }

    /**
     * A dummy item representing a piece of content.
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
