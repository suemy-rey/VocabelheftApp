package vocabelappprojekt.android.mi.ur.de.vocabelheftapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by 1 on 2015/8/26.
 */
public class VocableListDatabase {

    private static final String DATABASE_NAME = "VocItem.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_TABLE = "vocableItem";

    public static final String KEY_ORIGINAL_NAME = "name";
    public static final String KEY_TRANSLATION_NAME ="translation_name";
    public static final String KEY_ORIGINAL_SPINNER = "original_spinner";
    public static final String KEY_TRANSLATION_SPINNER = "translation_spinner";
    public static final String KEY_NOTES = "notes";
    public static final String KEY_CATEGORY = "category";


    private FoodieItemDBOpenHelper dbHelper;

    private SQLiteDatabase db;

    public VocableListDatabase(Context context) {
        dbHelper = new FoodieItemDBOpenHelper(context, DATABASE_NAME, null,
                DATABASE_VERSION);
        open();
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


    public long insertVocableItem(String original_name, String translation_name, String original_language_spinner, String translation_language_spinner, String notes, String category){
        ContentValues newVocable = new ContentValues();

        newVocable.put(KEY_ORIGINAL_NAME, original_name);
        newVocable.put(KEY_ORIGINAL_NAME, translation_name);
        newVocable.put(KEY_ORIGINAL_SPINNER, original_language_spinner);
        newVocable.put(KEY_TRANSLATION_SPINNER, translation_language_spinner);
        newVocable.put(KEY_NOTES, notes);
        newVocable.put(KEY_CATEGORY, category);

        return  db.insert(DATABASE_TABLE, null, newVocable);
    }

    public VocItem getVocItem(String name){
        Cursor cursor = db.query(DATABASE_TABLE, new String[] {KEY_ORIGINAL_NAME, KEY_TRANSLATION_NAME, KEY_ORIGINAL_SPINNER, KEY_TRANSLATION_SPINNER, KEY_NOTES,KEY_CATEGORY}, KEY_ORIGINAL_NAME + "=?",
                new String[] { name }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return new VocItem(
                cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
    }

    public ArrayList<VocItem> getAllVocItems(){
        ArrayList<VocItem> voc_items = new ArrayList<VocItem>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ORIGINAL_NAME, KEY_TRANSLATION_NAME,KEY_ORIGINAL_SPINNER, KEY_TRANSLATION_SPINNER, KEY_NOTES, KEY_CATEGORY}, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                String original_name = cursor.getString(0);
                String translation_name = cursor.getString(1);
                String original_language = cursor.getString(2);
                String translation_language = cursor.getString(3);
                String notes = cursor.getString(4);
                String category = cursor.getString(5);

                voc_items.add(new VocItem(original_name, translation_name, original_language, translation_language, notes, category));

            }while (cursor.moveToNext());
        }
        return voc_items;
    }

    public long updateOriginalLanguageSpinner(String original_name, String original_language){
        ContentValues newVocable = new ContentValues();
        newVocable.put(KEY_ORIGINAL_SPINNER,original_language);
        return db.update(DATABASE_TABLE,newVocable, "id=?", new String[]{original_name});
    }

    public long updateTranslationLanguageSpinner(String original_name, String translation_language){
        ContentValues newVocable = new ContentValues();
        newVocable.put(KEY_TRANSLATION_SPINNER,translation_language);
        return db.update(DATABASE_TABLE,newVocable,"id=?", new String[] {original_name});
    }
    public long updateTranslationName(String original_name, String translation_name){
        ContentValues newVocable = new ContentValues();
        newVocable.put(KEY_TRANSLATION_NAME,translation_name);
        return db.update(DATABASE_TABLE,newVocable,"id=?", new String[] {original_name});
    }
    public long updateNotes(String original_name, String notes){
        ContentValues newVocable = new ContentValues();
        newVocable.put(KEY_TRANSLATION_NAME,notes);
        return db.update(DATABASE_TABLE,newVocable,"id=?", new String[] {original_name});
    }
    public long updateCategory(String original_name, String category){
        ContentValues newVocable = new ContentValues();
        newVocable.put(KEY_TRANSLATION_NAME,category);
        return db.update(DATABASE_TABLE,newVocable,"id=?", new String[] {original_name});
    }
    public int deleteVocItem(String name){

        return db.delete(DATABASE_TABLE, KEY_ORIGINAL_NAME + " = ? ", new String[] {name});
    }

    private class FoodieItemDBOpenHelper extends SQLiteOpenHelper {

        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + KEY_ORIGINAL_NAME
                + " integer primary key autoincrement, " + KEY_ORIGINAL_NAME
                + " text not null, " + KEY_TRANSLATION_NAME + " text, " + KEY_ORIGINAL_SPINNER + " text, " + KEY_TRANSLATION_SPINNER + " text, "
                + KEY_NOTES + " text, " + KEY_CATEGORY + " text);";

        public FoodieItemDBOpenHelper(Context c, String dbname,
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

