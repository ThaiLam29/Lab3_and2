package com.thailam.lab3_and2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper( Context context) {
        super(context, "TODO.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create= "CREATE TABLE TODO(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT, date text, type text, status INTEGER)\n";
        db.execSQL(create);

        String insert="INSERT INTO TODO VALUES \n" +
                "(1, 'Hoc Java', 'Hoc Java co ban', '27/2/2023', 'Binh thuong', 1),\n" +
                "(2, 'Hoc React Native', 'Hoc React Native co ban', '24/3/2023', 'Kho', 0),\n" +
                "(3, 'Hoc Kotlin', 'Hoc Kotlin co ban', '1/4/2023', 'De', 2)";
        db.execSQL(insert);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion){
            db.execSQL("drop table if exists TODO ");
            onCreate(db);
        }
    }
}
