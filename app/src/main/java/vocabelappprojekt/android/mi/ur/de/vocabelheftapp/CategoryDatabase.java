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
public class CategoryDatabase {

    private static final String DATABASE_NAME = "VocItem.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_TABLE = "categoryDatabase";

    public static final String KEY_NAME = "name";
    public static final String KEY_ID = "id";

    private FoodieItemDBOpenHelper dbHelper;

    private SQLiteDatabase db;

    public CategoryDatabase(Context context) {
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


    public long insertCategoryItem(String name){
        ContentValues newCategory = new ContentValues();

        newCategory.put(KEY_NAME, name);


        return  db.insert(DATABASE_TABLE, null, newCategory);
    }

    public CategoryItem getCategoryItem(String category_id){
        Cursor cursor = db.query(DATABASE_TABLE, new String[] {KEY_ID, KEY_NAME}, KEY_ID + "=?",
                new String[] { category_id }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return new CategoryItem(
               cursor.getString(0),  Long.parseLong(cursor.getString(0)));
    }

    public ArrayList<CategoryItem> getAllCategoryItems(){
        ArrayList<CategoryItem> category_items = new ArrayList<CategoryItem>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[] {KEY_ID, KEY_NAME}, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                String id = cursor.getString(0);
                String name = cursor.getString(1);

                category_items.add(new CategoryItem(name, Long.valueOf(id)));

            }while (cursor.moveToNext());
        }
        return category_items;
    }
    public long updateName(String category_id, String name){
        ContentValues newCategoryValues = new ContentValues();
        newCategoryValues.put(KEY_ID, name);
        return db.update(DATABASE_TABLE, newCategoryValues,"id=?", new String[] {category_id});
    }

    public int deleteCategoryItem(String category_id){

        return db.delete(DATABASE_TABLE, KEY_ID + " = ? ", new String[] {category_id});
    }

    private class FoodieItemDBOpenHelper extends SQLiteOpenHelper {

        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_ID
                + " text not null, " + KEY_NAME + " text);";

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

