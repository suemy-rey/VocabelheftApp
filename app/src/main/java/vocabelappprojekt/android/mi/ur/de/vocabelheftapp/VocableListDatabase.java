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

    public static final String KEY_NAME = "name";
    public static final String KEY_ORIGINAL = "original_spinner";
    public static final String KEY_TRANSLATION = "translation_spinner";


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


    public long insertVocableItem(String name, String original_language_spinner, String translation_language_spinner){
        ContentValues newVocable = new ContentValues();

        newVocable.put(KEY_NAME, name);
        newVocable.put(KEY_ORIGINAL, original_language_spinner);
        newVocable.put(KEY_TRANSLATION, translation_language_spinner);
        return  db.insert(DATABASE_TABLE, null, newVocable);
    }

    public VocItem getVocItem(String name){
        Cursor cursor = db.query(DATABASE_TABLE, new String[] {KEY_NAME, KEY_ORIGINAL,KEY_TRANSLATION }, KEY_NAME + "=?",
                new String[] { name }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return new VocItem(
                cursor.getString(0), cursor.getString(1), cursor.getString(2));
    }

    public ArrayList<VocItem> getAllFoodieItems(){
        ArrayList<VocItem> voc_items = new ArrayList<VocItem>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_NAME, KEY_ORIGINAL, KEY_TRANSLATION}, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                String name= cursor.getString(0);
                String original_language = cursor.getString(1);
                String translation_language = cursor.getString(2);


                //Log.e("score" ,score);
                voc_items.add(new VocItem(name, original_language, translation_language));

            }while (cursor.moveToNext());
        }
        return voc_items;
    }

    public long updateOriginalLanguageSpinner(String name, String original_language){
        ContentValues newVocable = new ContentValues();
        newVocable.put(KEY_ORIGINAL,original_language);
        return db.update(DATABASE_TABLE,newVocable, "id=?", new String[]{name});
    }

    public long updateTranslationLanguageSpinner(String name, String translation_language){
        ContentValues newVocable = new ContentValues();
        newVocable.put(KEY_TRANSLATION,translation_language);
        return db.update(DATABASE_TABLE,newVocable,"id=?", new String[] {name});
    }

    public int deleteVocItem(String name){
        // Log.e("id", foodieItemID);
        return db.delete(DATABASE_TABLE, KEY_NAME + " = ? ", new String[] {name});
    }

    private class FoodieItemDBOpenHelper extends SQLiteOpenHelper {

        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + KEY_NAME
                + " integer primary key autoincrement, " + KEY_NAME
                + " text not null, " + KEY_ORIGINAL + " text, " + KEY_TRANSLATION + " text);";

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

