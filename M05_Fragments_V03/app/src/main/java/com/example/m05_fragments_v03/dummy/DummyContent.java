package com.example.m05_fragments_v03.dummy;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.  Used to leverage ArrayAdapter to
     * create pick list in ListFragment class.
     */
    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    static {
        // Add 3 sample items.
        addItem(new DummyItem("1", "my Item 1", "Contents for my item 1"));
        addItem(new DummyItem("2", "my Item 2", "Contents for my item 2"));
        addItem(new DummyItem("3", "my Item 3", "Contents for my item 3"));
        addItem(new DummyItem("4", "my Item 4", "Contents for my item 4"));
        addItem(new DummyItem("5", "my Item 5", "Contents for my item 5, Contents for my item 5, Contents for my item 5, Contents for my item 5, Contents for my item 5"));
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId(), item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {

        private String id;
        private String title;
        private String content;

        public DummyItem(String id, String title, String content) {
            this.id = id;
            this.title = title;
            this.content = content;
        }

        // Supplies array of title strings, when ArrayAdapter
        // gets "toString()" of each object.
        @Override
        public String toString() {
            Log.v("Frags_V03", "toString ="+title);
            return title;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            Log.v("Frags_V03", "getTitle ="+title);
            return title;
        }

        // Show title and contents together
        public String getContent() {
            return title + "\n" + content;
        }
    }
}
