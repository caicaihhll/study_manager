package com.java.managersystem.sqlate;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.java.managersystem.entity.LocalUserInfoBean;

import java.util.ArrayList;
import java.util.List;

public class ContactsDatabase  {
    private final DatabaseHelper dbHelper;

    public ContactsDatabase(Context context) {
        super();
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * 增
     *
     * @param data
     */
    public void insert(LocalUserInfoBean data) {
        String sql = "insert into " + DatabaseHelper.CONTACTS_TABLE_NAME;

        sql += "(userId, account, password,nickname,avatar,role) values(?,?,?,?,?,?)";

        SQLiteDatabase sqlite = dbHelper.getWritableDatabase();
        sqlite.execSQL(sql, new String[] {data.getUserId() + "",data.getAccount() + "", data.getPassword() + "", data.getNickName() + "", data.getAvatar() + "", data.getRole() + ""});
        sqlite.close();
    }

    /**
     * 删
     *
     * @param where
     */
    public void delete(String where) {
        SQLiteDatabase sqlite = dbHelper.getWritableDatabase();
        String sql = "delete from " + DatabaseHelper.CONTACTS_TABLE_NAME + where;
        sqlite.execSQL(sql);
        sqlite.close();
    }

    /**
     * 改
     *
     * @param data
     */
    public void update(LocalUserInfoBean data) {
        SQLiteDatabase sqlite = dbHelper.getWritableDatabase();
        String sql = ("update " + DatabaseHelper.CONTACTS_TABLE_NAME +" set userId=?,account=?,password=?,nickname=?,avatar=?,role=? where userId=?");
        sqlite.execSQL(sql,
                new String[] { data.getUserId() + "",data.getAccount() + "", data.getPassword() + "", data.getNickName() + "", data.getAvatar() + "", data.getRole() + "", data.getUserId()+ ""});
        sqlite.close();
    }
    /**
     * 查一条数据
     *
     * @param where
     * @return
     */
    public LocalUserInfoBean queryContactsInfo(String where) {
        LocalUserInfoBean contactsInfo = null;
        SQLiteDatabase sqlite = dbHelper.getReadableDatabase();

        String sql= "select * from "
                + DatabaseHelper.CONTACTS_TABLE_NAME + where;
        Cursor cursor2 = sqlite.rawQuery(sql, null);
        for (cursor2.moveToFirst(); !cursor2.isAfterLast(); cursor2.moveToNext()) {
            contactsInfo= new LocalUserInfoBean();
            contactsInfo.setUserId(cursor2.getInt(0));
            contactsInfo.setAccount(cursor2.getString(1));
            contactsInfo.setPassword(cursor2.getString(2));
            contactsInfo.setNickName(cursor2.getString(3));
            contactsInfo.setAvatar(cursor2.getInt(4));
            contactsInfo.setRole(cursor2.getInt(5));
        }
        if (!cursor2.isClosed()) {
            cursor2.close();
        }

        sqlite.close();
        return contactsInfo;
    }

    /**
     * 查
     *
     * @param where
     * @return
     */
    public List<LocalUserInfoBean> query(String where) {
        SQLiteDatabase sqlite = dbHelper.getReadableDatabase();
        ArrayList<LocalUserInfoBean> data = null;
        data = new ArrayList<LocalUserInfoBean>();
        String sql="select * from "
                + DatabaseHelper.CONTACTS_TABLE_NAME + where;
        Cursor cursor = sqlite.rawQuery(sql, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            LocalUserInfoBean contactsInfo = new LocalUserInfoBean();
            contactsInfo.setUserId(cursor.getInt(0));
            contactsInfo.setAccount(cursor.getString(1));
            contactsInfo.setPassword(cursor.getString(2));
            contactsInfo.setNickName(cursor.getString(3));
            contactsInfo.setAvatar(cursor.getInt(4));
            contactsInfo.setRole(cursor.getInt(5));
            data.add(contactsInfo);
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        sqlite.close();
        return data;
    }
    /**
     * 查所有数据
     *
     * @return
     */
    public List<LocalUserInfoBean> queryAllData() {
        SQLiteDatabase sqlite = dbHelper.getReadableDatabase();
        ArrayList<LocalUserInfoBean> data = null;
        data = new ArrayList<LocalUserInfoBean>();
        String sql="select * from "
                + DatabaseHelper.CONTACTS_TABLE_NAME;
        Cursor cursor = sqlite.rawQuery(sql, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            LocalUserInfoBean contactsInfo = new LocalUserInfoBean();
            contactsInfo.setUserId(cursor.getInt(0));
            contactsInfo.setAccount(cursor.getString(1));
            contactsInfo.setPassword(cursor.getString(2));
            contactsInfo.setNickName(cursor.getString(3));
            contactsInfo.setAvatar(cursor.getInt(4));
            contactsInfo.setRole(cursor.getInt(5));
            data.add(contactsInfo);
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        sqlite.close();
        return data;
    }

    /**
     * 查总数
     *
     * @param where
     * @return
     */
    public int queryCount(String where) {
        SQLiteDatabase sqlite = dbHelper.getReadableDatabase();
        String sql="select count(*) from "
                + DatabaseHelper.CONTACTS_TABLE_NAME+where ;
        Cursor cursor = sqlite.rawQuery(sql, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        sqlite.close();
        return count;
    }

    /**
     * 重置
     *
     * @param datas
     */
    public void reset(List<LocalUserInfoBean> datas) {
        if (datas != null) {
            SQLiteDatabase sqlite = dbHelper.getWritableDatabase();
            // 删除全部
            sqlite.execSQL("delete from " + DatabaseHelper.CONTACTS_TABLE_NAME);
            // 重新添加
            for (LocalUserInfoBean data : datas) {
                insert(data);
            }
            sqlite.close();
        }
    }

    public int dbCount(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM contacts_table", null);

        if (cursor != null) {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();
            return count;
        }
        return 0;
    }

    public DatabaseHelper getDbHelper() {
        return dbHelper;
    }

    public void destroy() {
        dbHelper.close();
    }
}