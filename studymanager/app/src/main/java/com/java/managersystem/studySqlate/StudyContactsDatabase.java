package com.java.managersystem.studySqlate;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.java.managersystem.entity.StudyBean;

import java.util.ArrayList;
import java.util.List;

public class StudyContactsDatabase  {
    private final StudyDatabaseHelper dbHelper;

    public StudyContactsDatabase(Context context) {
        super();
        dbHelper = new StudyDatabaseHelper(context);
    }

    /**
     * 增
     *
     * @param data
     */
    public void insert(StudyBean data) {
        String sql = "insert into " + StudyDatabaseHelper.CONTACTS_TABLE_NAME;

        sql += "(studyCode, name, course,score) values(?,?,?,?)";

        SQLiteDatabase sqlite = dbHelper.getWritableDatabase();
        sqlite.execSQL(sql, new String[] {data.getStudyCode() + "",data.getName() + "", data.getCourse() + "", data.getScore() + ""});
        sqlite.close();

    }

    /**
     * 删
     *
     */
    public void delete(StudyBean data) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String whereClause = "studyCode=?";
        String[] whereArgs = {String.valueOf(data.getStudyCode())};
        db.delete(StudyDatabaseHelper.CONTACTS_TABLE_NAME, whereClause, whereArgs);
        db.close();

    }

    /**
     * 改
     *
     * @param data
     */
    public void update(StudyBean data) {
        SQLiteDatabase sqlite = dbHelper.getWritableDatabase();
        String sql = ("update " + StudyDatabaseHelper.CONTACTS_TABLE_NAME +" set studyCode=?,name=?,course=?,score=? where studyCode=?");
        sqlite.execSQL(sql,
                new String[] { data.getStudyCode() + "",data.getName() + "", data.getCourse() + "", data.getScore() + "", data.getStudyCode()+ ""});
        sqlite.close();
    }
    /**
     * 查一条数据
     *
     * @param where
     * @return
     */
    public StudyBean queryContactsInfo(String where) {
        StudyBean contactsInfo = null;
        SQLiteDatabase sqlite = dbHelper.getReadableDatabase();

        String sql= "select * from "
                + StudyDatabaseHelper.CONTACTS_TABLE_NAME + where;
        Cursor cursor2 = sqlite.rawQuery(sql, null);
        for (cursor2.moveToFirst(); !cursor2.isAfterLast(); cursor2.moveToNext()) {
            contactsInfo= new StudyBean();
            contactsInfo.setStudyCode(cursor2.getString(0));
            contactsInfo.setName(cursor2.getString(1));
            contactsInfo.setCourse(cursor2.getString(2));
            contactsInfo.setScore(cursor2.getString(3));
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
    public List<StudyBean> query(String where) {
        SQLiteDatabase sqlite = dbHelper.getReadableDatabase();
        ArrayList<StudyBean> data = null;
        data = new ArrayList<StudyBean>();
        String sql="select * from "
                + StudyDatabaseHelper.CONTACTS_TABLE_NAME + where;
        Cursor cursor = sqlite.rawQuery(sql, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            StudyBean contactsInfo = new StudyBean();
            contactsInfo.setStudyCode(cursor.getString(0));
            contactsInfo.setName(cursor.getString(1));
            contactsInfo.setCourse(cursor.getString(2));
            contactsInfo.setScore(cursor.getString(3));
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
    public List<StudyBean> queryAllData() {
        SQLiteDatabase sqlite = dbHelper.getReadableDatabase();
        ArrayList<StudyBean> data = null;
        data = new ArrayList<StudyBean>();
        String sql="select * from "
                + StudyDatabaseHelper.CONTACTS_TABLE_NAME;
        Cursor cursor = sqlite.rawQuery(sql, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            StudyBean contactsInfo = new StudyBean();
            contactsInfo.setStudyCode(cursor.getString(0));
            contactsInfo.setName(cursor.getString(1));
            contactsInfo.setCourse(cursor.getString(2));
            contactsInfo.setScore(cursor.getString(3));
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
                + StudyDatabaseHelper.CONTACTS_TABLE_NAME+where ;
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
    public void reset(List<StudyBean> datas) {
        if (datas != null) {
            SQLiteDatabase sqlite = dbHelper.getWritableDatabase();
            // 删除全部
            sqlite.execSQL("delete from " + StudyDatabaseHelper.CONTACTS_TABLE_NAME);
            // 重新添加
            for (StudyBean data : datas) {
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

    public StudyDatabaseHelper getDbHelper() {
        return dbHelper;
    }

    public void destroy() {
        dbHelper.close();
    }
}