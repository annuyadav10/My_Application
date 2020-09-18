package com.example.lenovo.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.query.In;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
EditText username,userphone;
Button submit_btn;
TextView viewdetails;
private  DatabaseHelper databaseHelper;
List<Table_Data_ModelClass> list;
int userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=findViewById(R.id.edit_name);
        userphone=findViewById(R.id.edit_phone);
        submit_btn=findViewById(R.id.btn_submit);
        viewdetails=findViewById(R.id.txt_view);
        databaseHelper= OpenHelperManager.getHelper(this,DatabaseHelper.class);
        submit_btn.setOnClickListener(this);
        viewdetails.setOnClickListener(this);

        setValueForEdit();

    }
     public void setValueForEdit()
     {
         Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        String phone=intent.getStringExtra("phone_no");
        userid=intent.getIntExtra("id",0);
        String update=intent.getStringExtra("update");
        username.setText(name);
        userphone.setText(phone);


        if(update==null){
            submit_btn.setText("Submit");
        }
        else {
            submit_btn.setText(update);
        }
         try {
             list=databaseHelper.getTableData_Dao().queryForAll();
             CheckUserId(userid);
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }


    @Override
    public void onClick(View v) {
         if(v==submit_btn)
         {
           if(username.getText().toString().trim().length()==0||userphone.getText().toString().trim().length()==0)
           {
               Toast.makeText(getApplicationContext(),"Please enter field",Toast.LENGTH_SHORT).show();
           }
           else if(CheckUserId(userid))
           {
               try {
                   UpdateBuilder<Table_Data_ModelClass,Integer> updateBuilder =databaseHelper.getTableData_Dao().updateBuilder();
                     updateBuilder.where().eq("id",userid);
                     updateBuilder.updateColumnValue("name",username.getText().toString().trim());
                     updateBuilder.updateColumnValue("phone_no",userphone.getText().toString().trim());
                     updateBuilder.update();
                     Toast.makeText(getApplicationContext(),"Data update successfully",Toast.LENGTH_SHORT).show();
                     clearutil();

               } catch (SQLException e) {
                   e.printStackTrace();
               }

           }
           else {
                Table_Data_ModelClass table_data_modelClass=new Table_Data_ModelClass();
                   table_data_modelClass.setName(username.getText().toString().trim());
                   table_data_modelClass.setPhone_no(userphone.getText().toString().trim());
               try {
                    databaseHelper.getTableData_Dao().create(table_data_modelClass);
                    Toast.makeText(getApplicationContext(),"Save Data Successfully",Toast.LENGTH_SHORT).show();
                   clearutil();
               } catch (SQLException e) {
                   e.printStackTrace();
               }
           }
         }
         else if(v==viewdetails){
             Intent intent=new Intent(getApplicationContext(),UserDetail.class);
             startActivity(intent);
             finish();

         }
    }
    public boolean CheckUserId(int userid) {

        for(int i=0;i< list.size();i++)
        {
            Table_Data_ModelClass tableData_modelClass=list.get(i);
            if(userid==tableData_modelClass.getId())
            {
                return true;
            }

        }

        return false;
    }
    public void clearutil()
    {
        username.setText("");
        userphone.setText("");
    }
}