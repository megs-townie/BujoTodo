package com.example.save_v04.placeholder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DB_Handler extends SQLiteOpenHelper implements DB_Interface {

    // If you change the database schema, you must increment the database version.
//    public static final int DATABASE_VERSION = 1;
//    public static final String DATABASE_NAME = "CarModels.db";  // <RS> Car Models DB
    private static final String TABLE_NAME = "carmodels";
    private static final String TEXT_TYPE = " TEXT";
    private static final String NUM_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String _ID = "_ID";
    private static final String _COL_1 = "str_col";
    private static final String _COL_2 = "num_col";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened untiljava.lang.String one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use to open or create the database
     * @param name    of the database file, or null for an in-memory database
     * @param factory to use for creating cursor objects, or null for the default
     * @param version number of the database (starting at 1); if the database is older,
     *                {@link #onUpgrade} will be used to upgrade the database; if the database is
     *                newer, {@link #onDowngrade} will be used to downgrade the database
     */
    public DB_Handler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.v("DB_Handler", "Constructor");

        // Add some sample items.
        if (this.count() > 1) {
            Log.v("DB_Handler", "already rows in DB");

        } else {
            Log.v("DB_Handler", "no rows in DB...add some");
            CarModel a = new CarModel(1, "Ford", 101);
            this.save(a);
            a = new CarModel(2, "GM", 201);
            this.save(a);
            a = new CarModel(2, "Tesla", 301);
            this.save(a);
            a = new CarModel(2, "Toyota", 401);
            this.save(a);
        }
    }

    @Override
    public int count() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        Log.v("DB_Handler", "getCount=" + cnt);
        return cnt;
    }

    @Override
    public int save(CarModel carModel) {
        //String command = "INSERT INTO CarModels(str_col,num_col) VALUES('" + carModel.getModelName() + "', " + carModel.getModelNumber() + ")";

        Log.v("DB_Handler", "add=>  " + carModel.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(_COL_1, carModel.getModelName());
        values.put(_COL_2, carModel.getModelNumber());

        // 3. insert
        db.insert(TABLE_NAME, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();

        // debug output to see what we're doing
        dump();

        return 0;
    }

    @Override
    public int update(CarModel carModel) {
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }

    @Override
    public List<PlaceholderContent.PlaceholderItem> findAll() {

        List<PlaceholderContent.PlaceholderItem> temp = new ArrayList<PlaceholderContent.PlaceholderItem>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_NAME;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build and add it to list
        CarModel car = null;
        PlaceholderContent.PlaceholderItem item;
        if (cursor.moveToFirst()) {
            do {
                // This code puts a carModel object into the PlaceHolder for the fragment
                // if you had more columns in the DB, you'd format  them in the non-details
                // list here
                car = new CarModel(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
                item = new PlaceholderContent.PlaceholderItem(
                        Long.toString(car.getId()),
                        "Content: " + car.getModelName(),
                        "Details: " + Integer.toString(car.getModelNumber()));
                temp.add(item);
            } while (cursor.moveToNext());
        }

        Log.v("DB_Handler", "findAll=> " + temp.toString());

        // return all
        return temp;
    }


    public Map<String, PlaceholderContent.PlaceholderItem> findAllDetails() {
        Map<String, PlaceholderContent.PlaceholderItem> temp = new HashMap<String, PlaceholderContent.PlaceholderItem>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_NAME;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);  //rawQuery returns cursor for results

        // 3. go over each row, build and add it to list
        CarModel car = null;
        PlaceholderContent.PlaceholderItem item = null;
        if (cursor.moveToFirst()) {          // moveToFirst returns false if no rows
            do {
                // This code puts a carModel object into the PlaceHolder for the fragment
                // if you had more columns in the DB, you'd format  them in the details
                // here
                car = new CarModel(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
                item = new PlaceholderContent.PlaceholderItem(
                        Long.toString(car.getId()),
                        "Content: " + car.getModelName(),
                        "Details: " + Integer.toString(car.getModelNumber()));
                temp.put(Long.toString(car.getId()), item);
            } while (cursor.moveToNext());
        }

        Log.v("DB_Handler", "findAllDetails " + temp.toString());

        return temp;
    }


    @Override
    public String getNameById(Long id) {
        return null;
    }

    // Dump the DB as a test
    private void dump() {
    }  // oops, never got around to this.

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param sqLiteDatabase The database.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String db_com = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY, " +
                _COL_1 + TEXT_TYPE + COMMA_SEP +
                _COL_2 + NUM_TYPE + " )";
        Log.v("handleDB", "onCreate DB =" + db_com);
        sqLiteDatabase.execSQL(db_com);

        Log.v("DB_Handler", "onCreate DB called");
    }


    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param sqLiteDatabase The database.
     * @param oldVersion     The old database version.
     * @param newVersion     The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.v("handleDB", "onUpgrade: Old=" + oldVersion + " New=" + newVersion);
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);

        Log.v("DB_Handler", "onUpgrade DB called");
    }
}
