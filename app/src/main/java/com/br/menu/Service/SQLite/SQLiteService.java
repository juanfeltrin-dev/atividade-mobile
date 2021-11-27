package com.br.menu.Service.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.br.menu.Model.ItemModel;

import java.util.ArrayList;

public class SQLiteService extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DB_NAME = "db_items";

    public SQLiteService(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE items (" +
                "id INTEGER PRIMARY KEY," +
                "photo VARCHAR(255)," +
                "title VARCHAR(255)," +
                "description VARCHAR(255)," +
                "gluten TINYINT(1)," +
                "calorie INTEGER," +
                "price VARCHAR(255)" +
                ")";

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insert(ArrayList<ItemModel> items) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        for (ItemModel item : items) {
            if (this.find(item.getId()) != null) {
                continue;
            }

            ContentValues values = new ContentValues();

            values.put("id", item.getId());
            values.put("photo", item.getPhoto());
            values.put("title", item.getTitle());
            values.put("description", item.getDescription());
            values.put("gluten", item.getGluten() ? 1 : 0);
            values.put("calorie", item.getCalorie());
            values.put("price", item.getPrice());

            sqLiteDatabase.insert("items", null, values);
        }

        sqLiteDatabase.close();
    }

    public ArrayList<ItemModel> all() {
        ArrayList<ItemModel> items = new ArrayList<>();

        try {
            String query = "SELECT photo, title, description, gluten, calorie, price, id FROM items";

            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    ItemModel item = new ItemModel();
                    item.setPhotoBitmap(
                        BitmapFactory.decodeFile(cursor.getString(0))
                    );
                    item.setTitle(cursor.getString(1));
                    item.setDescription(cursor.getString(2));
                    item.setGluten(cursor.getInt(3) == 1);
                    item.setCalorie(cursor.getInt(4));
                    item.setPrice(cursor.getString(5));
                    item.setId(cursor.getInt(6));

                    items.add(item);
                } while (cursor.moveToNext());
            }

            return items;
        } catch (Exception exception) {
            return items;
        }
    }

    public ItemModel find(int id) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

            Cursor cursor = sqLiteDatabase.query(
                    "items",
                    new String[] {"id", "photo", "title", "description", "gluten", "calorie", "price"},
                    "id = ?",
                    new String[] {String.valueOf(id)},
                    null,
                    null,
                    null,
                    null
            );

            if (cursor != null) {
                cursor.moveToFirst();
            }

            ItemModel item = new ItemModel();
            item.setId(cursor.getInt(0));
            item.setPhotoBitmap(
                    BitmapFactory.decodeFile(cursor.getString(1))
            );
            item.setTitle(cursor.getString(2));
            item.setDescription(cursor.getString(3));
            item.setGluten(cursor.getInt(4) == 1);
            item.setCalorie(cursor.getInt(5));
            item.setPrice(cursor.getString(6));

            return item;
        } catch (Exception exception) {
            return null;
        }
    }
}
