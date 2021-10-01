package com.example.russ.save_v02;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.russ.save_v02.db.DBcontent;
//import com.example.russ.save_v02.dummy.DummyContent;

import java.util.List;

/**
 * An activity representing a list of animals. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link animalDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class animalListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    // DB connection class
    private DBcontent db = null;
    private Context thisContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v("handleDB", "animalListActivity onCreate()");

        // Get DB data in background
        new DatabaseAccessTask().execute();

// Moved to doInBackground()
        // Connect to DB first, because data is needed for recyclerView
        // otherwise, db object is null during recyclerView setup and fails
        // Connect to DB ... move this to background task later but
        // be mindful of the order these tasks are done.
        //db = new DBcontent(this);
        //Log.v("onCreate", "data_is_set=" + db.data_is_set);


// moved to onPostExecute()
//        ///////////////////// back to stock code for fragment setup
//        setContentView(R.layout.activity_animal_list);
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle(getTitle());
//
//        // Floating email sign that says "replace..." when selected
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replaced with my own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//
//        // Set the animal list
//        View recyclerView = findViewById(R.id.animal_list);
//        assert recyclerView != null;
//        setupRecyclerView((RecyclerView) recyclerView);
//
//        // Set the details...if exists
//        if (findViewById(R.id.animal_detail_container) != null) {
//            // The detail container view will be present only in the
//            // large-screen layouts (res/values-w900dp).
//            // If this view is present, then the
//            // activity should be in two-pane mode.
//            mTwoPane = true;
//        }

        // Log it all...verify static and dynamic both work
        // Original sample used static, but no need to keep it that way.
//        Log.v("onCreate", "data_is_set=" + db.data_is_set);
//        Log.v("onCreate", "static ITEMS=" + DBcontent.ITEMS.toString());
//        Log.v("onCreate", "static ITEM_MAP=" + DBcontent.ITEM_MAP.toString());
//        Log.v("onCreate", "ITEM_MAP=" + db.getITEM_MAP().toString());


    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        if (DBcontent.ITEMS == null) {
//            Log.v("setupRecyclerView", "items=null data_is_set=" + db.data_is_set);
        } else {
            Log.v("setupRecyclerView", DBcontent.ITEMS.toString());
        }
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DBcontent.ITEMS));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<DBcontent.AnimalItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<DBcontent.AnimalItem> items) {
            if (items == null) {
                Log.v("SimpleIte...dapter", "items=null");
            } else {
                Log.v("SimpleIte...dapter", items.toString());
            }
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.animal_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).name);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(animalDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        animalDetailFragment fragment = new animalDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.animal_detail_container, fragment)
                                .commit();
                        Log.v("onBindViewHolder", "2 panes");
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, animalDetailActivity.class);
                        intent.putExtra(animalDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        context.startActivity(intent);
                        Log.v("onBindViewHolder", "1 pane");
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return db.getSize();
            //return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public DBcontent.AnimalItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }

    // The views must be updated from inside the owner, the activity
    // that owns it.  AsyncTask's onPostExecute() method runs from
    // the main UI thread, and then calls this method which is in the
    // activity class.
    private void viewUpdateOnPostExecute(){

        ///////////////////// back to stock code for fragment setup
        setContentView(R.layout.activity_animal_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        // Floating email sign that says "replace..." when selected
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replaced with my own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Set the animal list
        View recyclerView = findViewById(R.id.animal_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        // Set the details...if exists
        if (findViewById(R.id.animal_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

    }

    ///////////////////////////////////////
    // Background task to handle DB access in the background
    // NOTE: that onPostExecute() is run at the end, on the UI Thread.
    ///////////////////////////////////////
    private class DatabaseAccessTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... Void) {
            // Connect to DB first, because data is needed for recyclerView
            // otherwise, db object is null during recyclerView setup and fails
            // Connect to DB ... move this to background task later but
            // be mindful of the order these tasks are done.
            db = new DBcontent(thisContext);

            // Log it all...verify static and dynamic both work
            // Original sample used static, but no need to keep it that way.
            Log.v("onCreate", "data_is_set=" + db.data_is_set);
            Log.v("onCreate", "static ITEMS=" + DBcontent.ITEMS.toString());
            Log.v("onCreate", "static ITEM_MAP=" + DBcontent.ITEM_MAP.toString());
            Log.v("onCreate", "ITEM_MAP=" + db.getITEM_MAP().toString());

            return null;
        }

        @Override
        protected void onPostExecute(Void param) {

            // Original sample used static, but no need to keep it that way.
            Log.v("onPostExecute", "data_is_set=" + db.data_is_set);
            Log.v("onPostExecute", "static ITEMS=" + DBcontent.ITEMS.toString());
            Log.v("onPostExecute", "static ITEM_MAP=" + DBcontent.ITEM_MAP.toString());
            Log.v("onPostExecute", "ITEM_MAP=" + db.getITEM_MAP().toString());

            // Update the screen
            viewUpdateOnPostExecute();

        }

    }


}
