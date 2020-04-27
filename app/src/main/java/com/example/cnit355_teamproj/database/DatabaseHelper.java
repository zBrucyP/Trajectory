package com.example.cnit355_teamproj.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.cnit355_teamproj.database.DbSchema.ScoreTable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "trajectory.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ScoreTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                ScoreTable.Cols.DIFFICULTY + ", " +
                ScoreTable.Cols.SCORE + ", " +
                ScoreTable.Cols.DATE_ACHIEVED +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static Cursor query_table(SQLiteDatabase db, String tableName, String whereClause, String[] whereArgs) {
        Cursor cursor = db.query(
                tableName,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return cursor;
    }
}
