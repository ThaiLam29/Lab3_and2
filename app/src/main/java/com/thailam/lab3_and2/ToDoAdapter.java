package com.thailam.lab3_and2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {
    private final Context context;
    private ArrayList<ToDo>listToDo;
    private final ToDoDao toDoDao;

    public ToDoAdapter(Context context, ArrayList<ToDo> listToDo, ToDoDao toDoDao) {
        this.context = context;
        this.listToDo = listToDo;
        this.toDoDao = toDoDao;
    }




    @NonNull
    @Override   
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=((Activity) context).getLayoutInflater();
        View view=inflater.inflate(R.layout.item_rv, parent, false);
        return new ToDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder holder, int position) {
        holder.tv_title.setText(listToDo.get(position).getTitle());
        holder.tv_date.setText(listToDo.get(position).getDate());

        if (listToDo.get(position).getStatus()==1){
            holder.chk_status.setChecked(true);
            holder.tv_title.setPaintFlags(holder.tv_title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else {
            holder.chk_status.setChecked(false);
            holder.tv_title.setPaintFlags(holder.tv_title.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Canh bao");
                builder.setIcon(R.drawable.baseline_warning_24);
                builder.setMessage("Ban co chac chan xoa cong viec nay khong?");
                builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id=listToDo.get(holder.getAdapterPosition()).getID();
                        long result= toDoDao.deleteToDO(id);
                        if (result>= 0){
                            Toast.makeText(context, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                            listToDo.clear();
                            listToDo=toDoDao.getListToDO();
                            notifyItemRemoved(holder.getAdapterPosition());
                        }else {
                            Toast.makeText(context, "Xoa that bai", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });


        holder.chk_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int id=listToDo.get(holder.getAdapterPosition()).getID();
                boolean result=toDoDao.updateToDo(id, holder.chk_status.isChecked());
                if (result){
                    Toast.makeText(context, "Update status thanh cong", Toast.LENGTH_SHORT).show();
                    listToDo.clear();
                    listToDo=toDoDao.getListToDO();
                    notifyDataSetChanged();
                }else {
                    Toast.makeText(context, "Update stastus that bai", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.img_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDo toDo=listToDo.get(holder.getAdapterPosition());
                DialogUpdateToDo(toDo);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listToDo.size();
    }

    class ToDoViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title, tv_date;
        CheckBox chk_status;
        ImageView img_update, img_delete;

        public ToDoViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_date=itemView.findViewById(R.id.tv_date);
            chk_status=itemView.findViewById(R.id.chk_status);
            img_update=itemView.findViewById(R.id.img_update);
            img_delete=itemView.findViewById(R.id.img_delete);
        }
    }
    public void DialogUpdateToDo(ToDo toDoUpdate){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        LayoutInflater inflater=((Activity) context).getLayoutInflater();
        View view=inflater.inflate(R.layout.item_update, null);
        builder.setView(view);
        AlertDialog alertDialog=builder.create();

        EditText update_title=view.findViewById(R.id.update_title);
        EditText update_conten=view.findViewById(R.id.update_content);
        EditText update_date=view.findViewById(R.id.update_date);
        EditText update_type=view.findViewById(R.id.update_type);
        Button btn_update=view.findViewById(R.id.btn_update);
        Button btn_cancle=view.findViewById(R.id.btn_cancle);

        update_title.setText(toDoUpdate.getTitle());
        update_conten.setText(toDoUpdate.getContent());
        update_date.setText(toDoUpdate.getDate());
        update_type.setText(toDoUpdate.getType());

        update_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] mucdo={"Kho", "Binh thuong", "De"};
                AlertDialog.Builder builder1=new AlertDialog.Builder(context);
                builder1.setTitle("Chon muc do kho cua cong viec");
                builder1.setItems(mucdo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        update_type.setText(mucdo[which]);
                        Toast.makeText(context, mucdo[which], Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=update_title.getText().toString();
                String conten=update_conten.getText().toString();
                String date=update_date.getText().toString();
                String type=update_type.getText().toString();

                ToDo toDo=new ToDo(toDoUpdate.getID(), title, conten, date, type, toDoUpdate.getStatus());
                long result=toDoDao.updateToDo1(toDo);
                if (result>=0){
                    Toast.makeText(context, "Da update thanh cong", Toast.LENGTH_SHORT).show();
                    listToDo.clear();
                    listToDo=toDoDao.getListToDO();
                    notifyDataSetChanged();


                }else {
                    Toast.makeText(context, "Update that bai", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }
}
