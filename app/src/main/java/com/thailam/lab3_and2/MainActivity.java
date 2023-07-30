package com.thailam.lab3_and2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText edt_title, edt_content, edt_date, edt_type;
    Button btn_them;
    RecyclerView recyclerView;

    List<String> list = new ArrayList<>();
    ArrayList<ToDo> listTodo = new ArrayList<>();
    ToDoAdapter adapter;
    ToDoDao toDoDao;
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_title = findViewById(R.id.edt_title);
        edt_content = findViewById(R.id.edt_content);
        edt_date = findViewById(R.id.edt_date);
        edt_type = findViewById(R.id.edt_type);
        btn_them = findViewById(R.id.btn_them);
        recyclerView = findViewById(R.id.rv_todo);


        toDoDao = new ToDoDao(context);
        list.clear();
        listTodo.clear();
        listTodo = toDoDao.getListToDO();
        adapter = new ToDoAdapter(context, listTodo, toDoDao);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        edt_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] muccongviec = {"Kho", "Binh thuong", "De"};

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Chon muc do kho cua cong viec");
                builder.setItems(muccongviec, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edt_type.setText(muccongviec[which]);
                        Toast.makeText(context, muccongviec[which], Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sTitle = edt_title.getText().toString();
                String sContent = edt_content.getText().toString();
                String sDate = edt_date.getText().toString();
                String sType = edt_type.getText().toString();
                ToDo toDo = new ToDo();
                toDo.setTitle(sTitle);
                toDo.setContent(sContent);
                toDo.setDate(sDate);
                toDo.setType(sType);
                toDoDao = new ToDoDao(context);
                long result = toDoDao.addToDo(toDo);
                if (result == 1) {
                    Toast.makeText(context, "Them thanh cong", Toast.LENGTH_SHORT).show();
                    listTodo = toDoDao.getListToDO();
                    adapter = new ToDoAdapter(context, listTodo, toDoDao);
                    recyclerView.setAdapter(adapter);
                }
                if (result == -1) {
                    Toast.makeText(context, "Them that bai", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}