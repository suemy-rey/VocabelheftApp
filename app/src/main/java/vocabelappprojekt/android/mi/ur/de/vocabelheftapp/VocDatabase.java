package vocabelappprojekt.android.mi.ur.de.vocabelheftapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Sümeyye on 25.08.2015.
 */
public class VocDatabase {

    private static final String DATABASE_NAME = "voclist.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_TABLE = "vocfile";

    public static final String KEY_ID = "_id";
    public static final String KEY_LANGUAGE_ONE = "voc_one";
    public static final String KEY_LANGUAGE_TWO = "voc_two";
   // public static final String KEY_NOTES = "voc_notes";

    private VocItemHelper dbHelper;
    private SQLiteDatabase db;

    public VocDatabase (Context context) {
        dbHelper = new VocItemHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() throws SQLException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
        }
    }

    public void close() {
        db.close();
    }

    public long insertVocItem(VocItem vocItem) {

        ContentValues vocValues = new ContentValues();

        vocValues.put(KEY_LANGUAGE_ONE, vocItem.getName());
        vocValues.put(KEY_LANGUAGE_TWO, vocItem.getName_two());
        //vocValues.put(KEY_NOTES, vocItemItem.getNotes());

        return db.insert(DATABASE_TABLE, null, vocValues);
    }


    //Quelle:http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/
    public VocItem getVocItem(String vocItemID) {

        Cursor cursor = db.query(DATABASE_TABLE, new String[]{KEY_ID,
                        KEY_LANGUAGE_ONE, KEY_LANGUAGE_TWO}, KEY_ID + "=?", new String[]{vocItemID},
                null, null, null, null);

        cursor.moveToFirst();

        String name = cursor.getString(1);
        String name_two = cursor.getString(2);

        return new VocItem(Long.parseLong(cursor.getString(0)),name, name_two);
    }

    public ArrayList<VocItem> getAllVocItems() {
        ArrayList<VocItem> vocables = new ArrayList<VocItem>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID,
                KEY_LANGUAGE_ONE, KEY_LANGUAGE_TWO}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String name_two = cursor.getString(2);


                vocables.add(new VocItem(Long.valueOf(id), name, name_two));

            } while (cursor.moveToNext());
        }
        return vocables;
    }

   // public int deleteFoodieItem(String foodieItemID) {

       // return db.delete(DATABASE_TABLE, KEY_LANGUAGE_TWO + "= ?", new String[]{foodieItemID});

   // }

    public long updateTitle(String vocItemID, String title, String title_two){

        ContentValues newTitleValues = new ContentValues();
        newTitleValues.put(KEY_LANGUAGE_ONE, title);
        newTitleValues.put(KEY_LANGUAGE_TWO, title_two);
        return db.update(DATABASE_TABLE, newTitleValues, KEY_ID + "= ?", new String[] {vocItemID});

    }


    private class VocItemHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE =
                "create table " + DATABASE_TABLE + " (" +
                        KEY_ID + " integer primary key autoincrement, " +
                        KEY_LANGUAGE_ONE + " text not null, " +
                        KEY_LANGUAGE_TWO + " text not null);"; //   +
                       // KEY_NOTES + " text);";

        public VocItemHelper(Context c, String dbname,
                                SQLiteDatabase.CursorFactory factory, int version) {
            super(c, dbname, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}



