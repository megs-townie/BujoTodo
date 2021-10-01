package com.example.russ.save_v02_api15;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.russ.save_v02_api15.db.DBcontent;
//import com.example.russ.save_v02.dummy.DummyContent;

import java.util.List;

/**
 * An activity representing a list of animals. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link animalDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 *
 *
 * Activity updated to demonstrate SQLite:
 * 1 - Used Master-Details select to create project (so mostly default code)
 * 2 - Added db package to support DB
 * 3 - refactored DummyContent Class to work with SQLite Helper Class (handleDB)
 * 4 - SQLite: http://developer.android.com/reference/android/database/sqlite/package-summary.html
 * 5 - handleDB shows sample read/writes to DB
 * 6 - onUpgrade() called if DATABASE_VERSION is incremented
 * 7 - follow-up with browsing files created here (use ADM -> File Explorer)
 * 8 - follow-up with moving DB file to PC for SQLite Manager to browse (Firefox plugin)
 */
public class animalListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    // DB connection class
    private DBcontent db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v("handleDB", "animalListActivity onCreate()");

        // Connect to DB first, because data is needed for recyclerView
        // otherwise, db object is null during recyclerView setup and fails
        // Connect to DB ... move this to background task later but
        // be mindful of the order these tasks are done.
        db = new DBcontent(this);

        // Log it all...verify static and dynamic both work
        // Original sample used static, but no need to keep it that way.
        Log.v("onCreate", "data_is_set=" + db.data_is_set);
        Log.v("onCreate", "static ITEMS=" + DBcontent.ITEMS.toString());
        Log.v("onCreate", "static ITEM_MAP=" + DBcontent.ITEM_MAP.toString());
        Log.v("onCreate", "ITEM_MAP=" + db.getITEM_MAP().toString());

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

    @Override
    public void onStop() {
        super.onStop();
        db.onStop();  // Handle closing the DB
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        if (DBcontent.ITEMS == null) {
            Log.v("setupRecyclerView", "items=null data_is_set=" + db.data_is_set);
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
}
