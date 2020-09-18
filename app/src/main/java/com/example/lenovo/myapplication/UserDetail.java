package com.example.lenovo.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDetail extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        DatabaseHelper databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        List<Table_Data_ModelClass> tableData_modelClasses=new ArrayList<>();
        try {
            tableData_modelClasses = databaseHelper.getTableData_Dao().queryForAll();
            recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(UserDetail.this));
            recyclerView.setAdapter(new Adapter(this,tableData_modelClasses));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}