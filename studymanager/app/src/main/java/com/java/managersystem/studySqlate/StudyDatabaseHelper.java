package com.java.managersystem.studySqlate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StudyDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "StudyManager.db";//数据库名字

    private static final int DATABASE_VERSION = 1; // 数据库的版本号

    public static final String CONTACTS_TABLE_NAME = "contacts_table";//联系人表名

    //创建联系人表SQL语句
    public static final String CREATE_CONTACTS_TABLE =
            "create table " +
                    CONTACTS_TABLE_NAME +
                    "(" +
                    "studyCode integer primary key autoincrement," +
                    "name varchar(50)," +
                    "course varchar(50)," +
                    "score integer" +
                    ")";


    /**
     * DatabaseHelper构造函数，传参数据库名，数据库版本，会自动创建数据库
     * @param context
     */
    public StudyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * onCreate 回调SQLiteDatabase对象，自动执行创建表语句
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    /**
     * 升级数据库。执行表结构变更语句
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        if (newVersion > 1) {
//            //Android的ALTER命令不支持一次添加多列，只能分多次添加
//            String alter_sql = "ALTER TABLE " + CONTACTS_TABLE_NAME + " ADD COLUMN " + "phone_new2 VARCHAR;";
//            db.execSQL(alter_sql);
//            alter_sql = "ALTER TABLE " + CONTACTS_TABLE_NAME + " ADD COLUMN " + "phone_new3 VARCHAR;";
//            db.execSQL(alter_sql); // 执行完整的SQL语句
//        }
    }
}