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
    public static final String KEY_ORIGINAL_SPINNER = "spinner_language_one";
    public static final String KEY_TRANSLATION_SPINNER = "translation_spinner";
    public static final String KEY_NOTES = "notes";
    public static final String KEY_CATEGORY = "category";

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

        vocValues.put(KEY_LANGUAGE_ONE, vocItem.getVocab());
        vocValues.put(KEY_LANGUAGE_TWO, vocItem.getVocabTranslation());
        vocValues.put(KEY_ORIGINAL_SPINNER, vocItem.getVocabLanguageSetting());
        vocValues.put(KEY_TRANSLATION_SPINNER, vocItem.getVocabTranslationLanguage());
        vocValues.put(KEY_NOTES, vocItem.getNotes());
        vocValues.put(KEY_CATEGORY, vocItem.getCategory());

        return db.insert(DATABASE_TABLE, null, vocValues);
    }


    //Quelle:http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/
    public VocItem getVocItem(String vocItemID) {

        Cursor cursor = db.query(DATABASE_TABLE, new String[]{KEY_ID,
                        KEY_LANGUAGE_ONE, KEY_LANGUAGE_TWO, KEY_ORIGINAL_SPINNER, KEY_TRANSLATION_SPINNER,
                 KEY_NOTES, KEY_CATEGORY}, KEY_ID + "=?", new String[]{vocItemID},
                null, null, null, null);

        cursor.moveToFirst();

        String name = cursor.getString(1);
        String name_two = cursor.getString(2);
        String spinner_original_language = cursor.getString(3);
        String spinner_translated_language = cursor.getString(4);
        String notes = cursor.getString(5);
        String category = cursor.getString(6);

        return new VocItem(Long.parseLong(cursor.getString(0)),name, name_two, spinner_original_language,
                spinner_translated_language, notes, category);
    }

    public ArrayList<VocItem> getAllVocItems() {
        ArrayList<VocItem> vocables = new ArrayList<VocItem>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID,
                KEY_LANGUAGE_ONE, KEY_LANGUAGE_TWO, KEY_ORIGINAL_SPINNER, KEY_TRANSLATION_SPINNER,
                KEY_NOTES, KEY_CATEGORY }, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String name_two = cursor.getString(2);
                String spinner_original_language = cursor.getString(3);
                String spinner_translated_language = cursor.getString(4);
                String notes = cursor.getString(5);
                String category = cursor.getString(6);



                vocables.add(new VocItem(Long.valueOf(id), name, name_two, spinner_original_language,
                        spinner_translated_language, notes, category));

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

    public long updateTitleTwo (String vocItemID, String title_two) {
        ContentValues newTitleValues = new ContentValues();
        newTitleValues.put(KEY_LANGUAGE_TWO,title_two);
        return db.update(DATABASE_TABLE, newTitleValues, KEY_ID + "= ?", new String[] {vocItemID});
    }


    private class VocItemHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE =
                //"drop table " + DATABASE_TABLE + "; " +
                "create table " + DATABASE_TABLE + " (" +
                        KEY_ID + " integer primary key autoincrement, " +
                        KEY_LANGUAGE_ONE + " text not null, " +
                        KEY_LANGUAGE_TWO + " text not null, "   +
                        KEY_ORIGINAL_SPINNER + " text not null, " +
                        KEY_TRANSLATION_SPINNER + " text not null, " +
                        KEY_NOTES + " text not null, " +
                        KEY_CATEGORY + " text not null);";

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



