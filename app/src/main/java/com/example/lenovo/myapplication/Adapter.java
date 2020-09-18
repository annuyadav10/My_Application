package com.example.lenovo.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by lenovo on 14-06-2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    List<Table_Data_ModelClass> tableData_modelClasses;
    DatabaseHelper databaseHelper;

    public Adapter(Context context, List<Table_Data_ModelClass> tableData_modelClasses) {
        this.context = context;
        this.tableData_modelClasses = tableData_modelClasses;
        databaseHelper=OpenHelperManager.getHelper(context,DatabaseHelper.class);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.data_list, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.tv_name.setText(tableData_modelClasses.get(position).getName());
        holder.tv_phone.setText(tableData_modelClasses.get(position).getPhone_no());
        holder.button1_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,MainActivity.class);
                intent.putExtra("name",tableData_modelClasses.get(position).getName());
                intent.putExtra("phone_no",tableData_modelClasses.get(position).getPhone_no());
                intent.putExtra("id",tableData_modelClasses.get(position).getId());
                intent.putExtra("update","Update");
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return tableData_modelClasses.size();
    }

    public   void deleteItem(int index) throws SQLException {

        DeleteBuilder<Table_Data_ModelClass, Integer> deleteData = databaseHelper.getTableData_Dao().deleteBuilder();
        deleteData.where().eq("id", tableData_modelClasses.get(index).getId());
        deleteData.delete();
        tableData_modelClasses.remove(index);
        notifyItemRemoved(index);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_phone;
        Button button1_edit, button2_delete;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.name);
            tv_phone = (TextView) itemView.findViewById(R.id.Phone);
            button1_edit = (Button) itemView.findViewById(R.id.button_edit);
            button2_delete = (Button) itemView.findViewById(R.id.button_delete);
            button2_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        deleteItem(getAdapterPosition());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }
}

