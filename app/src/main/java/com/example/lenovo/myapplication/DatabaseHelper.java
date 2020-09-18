package com.example.lenovo.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    public static final String DATABASE_NAME = "userdata";
    private static final
    int VERSION_NAME = 1;
    public Context mcontext;

    private Dao<Table_Data_ModelClass, Integer> tableData_Dao = null;


    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, VERSION_NAME);
        mcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Table_Data_ModelClass.class);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        try {
            TableUtils.dropTable(connectionSource, Table_Data_ModelClass.class, false);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public Dao<Table_Data_ModelClass, Integer> getTableData_Dao() throws SQLException {
        if (tableData_Dao == null) {
            tableData_Dao = getDao(Table_Data_ModelClass.class);
        }
        return tableData_Dao;
    }

    public void close() {
        super.close();
        tableData_Dao = null;
    }
}