package com.example.deepakrattan.contentproviderwithsqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Deepak Rattan on 05-Nov-17.
 */

public class WordListOpenHelper extends SQLiteOpenHelper {

    public static final String TAG = WordListOpenHelper.class.getSimpleName();
    private Context context;

    //Schema
    public static final String DATABASE_NAME = "word_list";
    public static final int DATABASE_VERSION = 1;
    public static final String WORD_LIST_TABLE = "word_entries";

    //Column names
    public static final String ID = "_id";
    public static final String WORD = "word";

    //String array of columns
    public static final String[] COLUMNS = {ID, WORD};

    //Queries
    public static final String CREATE_TABLE_WORD_LIST = "CREATE TABLE " + WORD_LIST_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + WORD + " TEXT );";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS" + WORD_LIST_TABLE;

    private SQLiteDatabase writableDB;
    private SQLiteDatabase readableDB;

    public WordListOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        Log.d(TAG, "WordListOpenHelper: ");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: ");
        db.execSQL(CREATE_TABLE_WORD_LIST);
        fillDatabaseWithData(db);

    }

    private void fillDatabaseWithData(SQLiteDatabase db) {
        String[] words = {"Android", "Adapter", "ListView", "AsyncTask", "Android Studio",
                "SQLiteDatabase", "SQLOpenHelper", "Data model", "ViewHolder",
                "Android Performance", "OnClickListener"};

        ContentValues cv = new ContentValues();
        for (int i = 0; i < words.length; i++) {
            cv.put(WORD, words[i]);
            db.insert(WORD_LIST_TABLE, null, cv);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: ");
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public WordItem query(int position) {
        String query = "SELECT  * FROM " + WORD_LIST_TABLE +
                " ORDER BY " + WORD + " ASC " +
                "LIMIT " + position + ",1";

        Cursor cursor = null;
        WordItem entry = new WordItem();

        try {
            if (readableDB == null) {
                readableDB = getReadableDatabase();
            }
            cursor = readableDB.rawQuery(query, null);
            cursor.moveToFirst();
            entry.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            entry.setWord(cursor.getString(cursor.getColumnIndex(WORD)));
        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION! " + e.getMessage());
        } finally {
            // Must close cursor and db now that we are done with it.
            cursor.close();
            return entry;
        }
    }


    public long insert(String word) {
        long newId = 0;
        ContentValues values = new ContentValues();
        values.put(WORD, word);
        try {
            if (writableDB == null) {
                writableDB = getWritableDatabase();
            }
            newId = writableDB.insert(WORD_LIST_TABLE, null, values);
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }
        return newId;
    }

    public long count() {
        if (readableDB == null) {
            readableDB = getReadableDatabase();
        }
        return DatabaseUtils.queryNumEntries(readableDB, WORD_LIST_TABLE);
    }

    public int delete(int id) {
        int deleted = 0;
        try {
            if (writableDB == null) {
                writableDB = getWritableDatabase();
            }
            deleted = writableDB.delete(WORD_LIST_TABLE, //table name
                    ID + " = ? ", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.d(TAG, "DELETE EXCEPTION! " + e.getMessage());
        }
        return deleted;
    }

    public int update(int id, String word) {
        int mNumberOfRowsUpdated = -1;
        try {
            if (writableDB == null) {
                writableDB = getWritableDatabase();
            }
            ContentValues values = new ContentValues();
            values.put(WORD, word);

            mNumberOfRowsUpdated = writableDB.update(WORD_LIST_TABLE, //table to change
                    values, // new values to insert
                    ID + " = ?", // selection criteria for row (in this case, the _id column)
                    new String[]{String.valueOf(id)}); //selection args; the actual value of the id

        } catch (Exception e) {
            Log.d(TAG, "UPDATE EXCEPTION! " + e.getMessage());
        }
        return mNumberOfRowsUpdated;
    }


}
