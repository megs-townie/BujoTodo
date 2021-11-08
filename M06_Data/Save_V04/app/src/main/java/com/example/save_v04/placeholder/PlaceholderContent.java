package com.example.save_v04.placeholder;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PlaceholderContent {

    // Flag shows DB and ITEMS are set up (Debugging Var)
    public boolean data_is_set = false;

    /**
     * An array of sample (placeholder) items.
     */
    public static List<PlaceholderItem> ITEMS = new ArrayList<PlaceholderItem>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public static Map<String, PlaceholderItem> ITEM_MAP = new HashMap<String, PlaceholderItem>();

    DB_Handler DB;
    /**
     * Constructor to populate data
     */
    public  PlaceholderContent(Context context) {
        Log.v("PlaceholderContent", "constructor");
        DB = new DB_Handler(context, "CarModels.db", null, 1);

        ITEMS = DB.findAll();
        ITEM_MAP = DB.findAllDetails();

        data_is_set = true;

        Log.v("PlaceholderContent", "ITEMS=" + ITEMS.toString());
        Log.v("PlaceholderContent", "ITEM_MAP=" + ITEM_MAP.toString());
    }

//    // Russ Dummy Values
//    // <RS> populates the ITEMS and ITEM_MAP at start (load)
//    // ITEMS and ITEM_MAP are used in the fragments to
//    // list and open specific items.  We just need to populate these
//    // our way to fool the fragments to loading our data.
//    static {
//        addItem( new PlaceholderItem(String.valueOf(1), "Item " + 1, "Details 1"));
//        addItem( new PlaceholderItem(String.valueOf(2), "Item " + 2, "Details 2"));
//        addItem( new PlaceholderItem(String.valueOf(3), "Item " + 3, "Details 3"));
//    }
//
//    private static void addItem(PlaceholderItem item) {
//        ITEMS.add(item);                  // <RS>  adds the item to arraylist
//        ITEM_MAP.put(item.id, item);      // <RS>  adds item to map, with item.id=>item
//    }


    /**
     * A placeholder item representing a piece of content.
     */
    public static class PlaceholderItem {
        public final String id;
        public final String content;
        public final String details;

        public PlaceholderItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}