package vocabbook.android.mi.ur.de.vocabbook.MyVocabList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import vocabbook.android.mi.ur.de.vocabbook.Log.Log;

public class VocabDatabase
{
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

    public VocabDatabase(Context context)
    {
        dbHelper = new VocItemHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() throws SQLException
    {
        try
        {
            db = dbHelper.getWritableDatabase();
        }
        catch (SQLException e)
        {
            db = dbHelper.getReadableDatabase();
        }
    }

    public void close()
    {
        db.close();
    }

    public long insertVocItem(VocabItem vocabItem)
    {
        ContentValues vocValues = new ContentValues();

        vocValues.put(KEY_LANGUAGE_ONE, vocabItem.getVocab());
        vocValues.put(KEY_LANGUAGE_TWO, vocabItem.getTranslation());
        vocValues.put(KEY_ORIGINAL_SPINNER, vocabItem.getVocabLanguage());
        vocValues.put(KEY_TRANSLATION_SPINNER, vocabItem.getTranslationLanguage());
        vocValues.put(KEY_NOTES, vocabItem.getNotes());
        vocValues.put(KEY_CATEGORY, vocabItem.getCategory());

        return db.insert(DATABASE_TABLE, null, vocValues);
    }

    public VocabItem getVocItem(String vocItemID)
    {
        Cursor cursor = db.query(DATABASE_TABLE, new String[]{KEY_ID,
                        KEY_LANGUAGE_ONE, KEY_LANGUAGE_TWO, KEY_ORIGINAL_SPINNER, KEY_TRANSLATION_SPINNER,
                        KEY_NOTES, KEY_CATEGORY}, KEY_ID + "=?", new String[]{vocItemID},
                null, null, null, null);
              if(cursor != null)
                cursor.moveToFirst();
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String name_two = cursor.getString(2);
                String spinner_original_language = cursor.getString(3);
                String spinner_translated_language = cursor.getString(4);
                String notes = cursor.getString(5);
                String category = cursor.getString(6);

                return new VocabItem(Integer.parseInt(id),name, name_two, spinner_original_language,
                        spinner_translated_language, notes, category);
    }

    public ArrayList<VocabItem> getVocItemsFromCategory(String category)
    {
        ArrayList<VocabItem> categoryItems = new ArrayList<>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[]{KEY_ID,
                KEY_LANGUAGE_ONE, KEY_LANGUAGE_TWO, KEY_ORIGINAL_SPINNER, KEY_TRANSLATION_SPINNER,
                KEY_NOTES, KEY_CATEGORY}, KEY_CATEGORY + "=?", new String[]{category},
                null, null, null, null);
        if (cursor.moveToFirst())
        {
            do
            {
                categoryItems.add(createItem(cursor));
            }
            while (cursor.moveToNext());
        }
        return categoryItems;
    }

    public ArrayList<VocabItem> getVocItemsByMotherLanguage(String text)
    {
        ArrayList<VocabItem> items = new ArrayList<>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[]{KEY_ID,
                        KEY_LANGUAGE_ONE, KEY_LANGUAGE_TWO, KEY_ORIGINAL_SPINNER, KEY_TRANSLATION_SPINNER,
                        KEY_NOTES, KEY_CATEGORY}, KEY_LANGUAGE_ONE + " like ?", new String[]{"%"+text+"%"},
                null, null, null, null);
        if (cursor.moveToFirst())
        {
            do
            {
                items.add(createItem(cursor));
            }
            while (cursor.moveToNext());
        }
        return items;
    }

    public ArrayList<VocabItem> getAllVocItems()
    {
        ArrayList<VocabItem> vocables = new ArrayList<VocabItem>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[]{KEY_ID,
                KEY_LANGUAGE_ONE, KEY_LANGUAGE_TWO, KEY_ORIGINAL_SPINNER, KEY_TRANSLATION_SPINNER,
                KEY_NOTES, KEY_CATEGORY}, null, null, null, null, null, null);
        if (cursor.moveToFirst())
        {
            do
            {
                vocables.add(createItem(cursor));
            }
            while (cursor.moveToNext());
        }
        return vocables;
    }


    private VocabItem createItem(Cursor cursor)
    {
        VocabItem item;

        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        String name_two = cursor.getString(2);
        String spinner_original_language = cursor.getString(3);
        String spinner_translated_language = cursor.getString(4);
        String notes = cursor.getString(5);
        String category = cursor.getString(6);

        item = new VocabItem(id, name, name_two, spinner_original_language, spinner_translated_language, notes, category);

        return item;
    }

    public long updateVocab(String vocItemID, String voc)
    {
        Log.e("updating word");
        ContentValues newVocab = new ContentValues();
        newVocab.put(KEY_LANGUAGE_ONE, voc);
        return db.update(DATABASE_TABLE, newVocab, KEY_ID + "= ?", new String[]{vocItemID});
    }

    public long updateTranslation(String vocItemID, String translation)
    {
        ContentValues newTranslation = new ContentValues();
        newTranslation.put(KEY_LANGUAGE_TWO, translation);
        return  db.update(DATABASE_TABLE, newTranslation, KEY_ID + "= ?", new String[]{vocItemID});
    }

    public long updateOriginalLanguageSpinner(String vocItemID, String original_language)
    {
        ContentValues newVocable = new ContentValues();
        newVocable.put(KEY_ORIGINAL_SPINNER, original_language);
        return db.update(DATABASE_TABLE, newVocable, KEY_ID + "= ?", new String[]{vocItemID});
    }

    public long updateTranslationLanguageSpinner(String vocItemID, String translation_language)
    {
        ContentValues newVocable = new ContentValues();
        newVocable.put(KEY_TRANSLATION_SPINNER, translation_language);
        return db.update(DATABASE_TABLE, newVocable, KEY_ID + "= ?", new String[]{vocItemID});
    }

    public long updateNotes(String vocItemID, String notes)
    {
        ContentValues newNotes = new ContentValues();
        newNotes.put(KEY_NOTES, notes);
        return db.update(DATABASE_TABLE, newNotes, KEY_ID + " =? ", new String[]{vocItemID});
    }

    public long updateCategory(String vocItemID, String category)
    {
        Log.e("updating category");
        ContentValues newVocable = new ContentValues();
        newVocable.put(KEY_CATEGORY, category);
        return db.update(DATABASE_TABLE, newVocable, KEY_ID + "= ?", new String[]{vocItemID});
    }

    public int deleteVocItem(String id)
    {
        return db.delete(DATABASE_TABLE, KEY_ID + " = ? ", new String[]{id});
    }



    private class VocItemHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_CREATE = "create table " +
                        DATABASE_TABLE + " (" +
                        KEY_ID + " integer primary key autoincrement, " +
                        KEY_LANGUAGE_ONE + " text not null, " +
                        KEY_LANGUAGE_TWO + " text not null, " +
                        KEY_ORIGINAL_SPINNER + " text not null, " +
                        KEY_TRANSLATION_SPINNER + " text not null, " +
                        KEY_NOTES + " text not null, " +
                        KEY_CATEGORY + " text not null);";

        public VocItemHelper(Context c, String dbname,
                             SQLiteDatabase.CursorFactory factory, int version)
        {
            super(c, dbname, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {

        }
    }
}