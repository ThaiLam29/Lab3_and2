package com.thailam.lab3_and2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ToDoDao {
    private final DBHelper dbHelper;
    private final SQLiteDatabase database;
    private Context context;

    public ToDoDao(Context context) {
        this.context = context;
        dbHelper=new DBHelper(context);
        database=dbHelper.getWritableDatabase();
    }

    public long addToDo(ToDo toDo){
        ContentValues values=new ContentValues();
        values.put("title", toDo.getTitle());
        values.put("content", toDo.getContent());
        values.put("date", toDo.getDate());
        values.put("type", toDo.getType());
        values.put("status", toDo.getStatus());

        long result=database.insert("TODO", null, values);
        if (result <= 0){
            return -1;
        }
        return 1;

    }

    public boolean updateToDo(Integer id, boolean check){
        int statusValue= check ?1:0;
        ContentValues values=new ContentValues();
        values.put("status", statusValue);
        long result=database.update("TODO", values, "id=?", new String[]{String.valueOf(id)});
        return result !=-1;
    }

    public long updateToDo1(ToDo toDo){
        ContentValues values=new ContentValues();
        values.put("title", toDo.getTitle());
        values.put("content", toDo.getContent());
        values.put("date", toDo.getDate());
        values.put("type", toDo.getType());
        long result=database.update("TODO", values, "id=" +
                "?", new String[]{String.valueOf(toDo.getID())});
        if (result<=0){
            return -1;
        }
        return 1;
    }

    public long deleteToDO(Integer id){
        long result=database.delete("TODO", "id=?", new String[]{String.valueOf(id)});
        if (result <= 0){
            return -1;
        }
        return 1;
    }

    public ArrayList<ToDo> getListToDO(){
        ArrayList<ToDo> list=new ArrayList<>();
        Cursor cursor=database.query("TODO", null, null,null,null,null,null);
        cursor.moveToFirst();
        while (cursor.isAfterLast()==false){
            ToDo toDo=new ToDo();
            toDo.setID(cursor.getInt(0));
            toDo.setTitle(cursor.getString(1));
            toDo.setContent(cursor.getString(2));
            toDo.setDate(cursor.getString(3));
            toDo.setType(cursor.getString(4));
            toDo.setStatus(cursor.getInt(5));

            list.add(toDo);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }



}
