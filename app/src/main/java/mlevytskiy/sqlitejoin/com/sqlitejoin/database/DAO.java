package mlevytskiy.sqlitejoin.com.sqlitejoin.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import mlevytskiy.sqlitejoin.com.sqlitejoin.vo.Field;

/**
 * Created by max on 22.03.15.
 */
public class DAO {

    private DataBaseHelper dbHelper;

    public DAO(Context context) {
        dbHelper = DataBaseHelper.getHelper(context);
    }

    public List<Field> getAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor c = db.rawQuery("SELECT * FROM " + DataBaseHelper.TABLE_A, null);
        Cursor c = db.rawQuery("SELECT " + "*" +
                " FROM " + DataBaseHelper.TABLE_A +
                " LEFT JOIN " + DataBaseHelper.TABLE_B +
                " ON " + DataBaseHelper.TABLE_A + "." + DataBaseHelper.ID_COLUMN
                + " = " + DataBaseHelper.TABLE_B + "." + DataBaseHelper.FOREIGN_KEY, null);

        List<Field> result = new ArrayList<>();
        if (c != null && c.moveToFirst()) {
            do {

                Field field = new Field();
                String valueA = c.getString(c.getColumnIndex(DataBaseHelper.A));
                field.setA(valueA);
                String valueB = c.getString(c.getColumnIndex(DataBaseHelper.B));
                field.setB(valueB);

                result.add(field);
            } while (c.moveToNext());
        }
        return result;
    }

    public Field searchFieldForId(String id) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor c = db.rawQuery("SELECT * FROM " + DataBaseHelper.TABLE_A, null);
        Cursor c = db.rawQuery("SELECT " + "*" +
                " FROM " + DataBaseHelper.TABLE_A +
                " LEFT JOIN " + DataBaseHelper.TABLE_B +
                " ON " + DataBaseHelper.TABLE_A + "." + DataBaseHelper.ID_COLUMN
                + " = " + DataBaseHelper.TABLE_B + "." + DataBaseHelper.FOREIGN_KEY
                + " AND " + DataBaseHelper.TABLE_A + "." + DataBaseHelper.ID_COLUMN + "=" + id, null);

        List<Field> result = new ArrayList<>();
        if (c != null && c.moveToFirst()) {
            do {

                Field field = new Field();
                String valueA = c.getString(c.getColumnIndex(DataBaseHelper.A));
                field.setA(valueA);
                String valueB = c.getString(c.getColumnIndex(DataBaseHelper.B));
                field.setB(valueB);

                result.add(field);
            } while (c.moveToNext());
        }
        if (result.size() > 0) {
            return result.get(0);
        }

        return null;
    }

    public boolean add(Field field) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SQLiteStatement sqLiteStatement1 = db.compileStatement("INSERT INTO " + DataBaseHelper.TABLE_A + "(" + DataBaseHelper.A + ") " + "values(?)");
        SQLiteStatement sqLiteStatement2 = db.compileStatement("INSERT INTO " + DataBaseHelper.TABLE_B + "(" + DataBaseHelper.B + "," + DataBaseHelper.FOREIGN_KEY + ") " + "values(?,?)");
        db.beginTransaction();
        try {
            int index = 1;
            sqLiteStatement1.bindString(index, field.getA());
            long id = sqLiteStatement1.executeInsert();

            String b = field.getB();
            if (!TextUtils.isEmpty(b)) {
                sqLiteStatement2.bindString(index, field.getB());
                sqLiteStatement2.bindLong(++index, id);
                sqLiteStatement2.executeInsert();
            }
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            sqLiteStatement1.close();
            sqLiteStatement2.close();
            db.endTransaction();
        }
    }

    private void insert(SQLiteDatabase db, String tableName, String key, String value) {
        SQLiteStatement sqLiteStatement = db.compileStatement("INSERT INTO " + tableName + " (" + key + ") " + "(?)");
        db.beginTransaction();
        try {
            int index = 1;
            sqLiteStatement.bindString(index, value);
            sqLiteStatement.executeInsert();
            sqLiteStatement.close();
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public Field remove(String id) {

        return null;
    }
}
