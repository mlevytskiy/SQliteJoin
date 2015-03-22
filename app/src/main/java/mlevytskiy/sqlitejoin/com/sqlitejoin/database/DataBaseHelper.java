package mlevytskiy.sqlitejoin.com.sqlitejoin.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "employeedb5";
	private static final int DATABASE_VERSION = 1;

	public static final String TABLE_A = "tableA";
	public static final String TABLE_B = "tableB";

	public static final String ID_COLUMN = "_id";
    public static final String FOREIGN_KEY = "aId";
	public static final String A = "a";
	public static final String B = "b";

	public static final String CREATE_TABLE_A = "CREATE TABLE "
			+ TABLE_A + "(" + ID_COLUMN + " INTEGER PRIMARY KEY, "
			+ A + " TEXT"  + ");";

	public static final String CREATE_TABLE_B = "CREATE TABLE "
			+ TABLE_B + "(" + ID_COLUMN + " INTEGER PRIMARY KEY, "
            + B + " TEXT, "
            + FOREIGN_KEY + " INTEGER, "
			+ "FOREIGN KEY" + "(" + FOREIGN_KEY + ")" + " REFERENCES "
            + TABLE_A + "(" + ID_COLUMN + ")" + ");";

	private static DataBaseHelper instance;

	public static synchronized DataBaseHelper getHelper(Context context) {
		if (instance == null)
			instance = new DataBaseHelper(context);
		return instance;
	}

	private DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		if (!db.isReadOnly()) {
			db.execSQL("PRAGMA foreign_keys=ON;");
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_A);
		db.execSQL(CREATE_TABLE_B);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
