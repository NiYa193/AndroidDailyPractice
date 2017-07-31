package top.fcc143.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import top.fcc143.bean.Person;

/**
 * Created by FCC on 2017/7/30.
 */

public class DbManager {
//    private static MySQLiteHelper helper;
//    public static  MySQLiteHelper getInstance(Context context){
//        if(helper == null){
//            helper = new MySQLiteHelper(context);
//        }
//        return helper;
//    }

    /**
     * 根据sql语句在数据库中执行语句
     * @param db 数据库对象
     * @param sql sql语句
     */
    public static void execSQL(SQLiteDatabase db, String sql){
        if(db != null){
            if(sql != null && !"".equals(sql)){
                db.execSQL(sql);
            }
        }
    }

    /**
     * 根据sql语句查询获得cursor对象
     * @param db 数据库对象
     * @param sql 查询的sql语句
     * @param selectionArgs 查询条件的占位符
     * @return 返回查询结果
     */
    public static Cursor selectDataBySql(SQLiteDatabase db, String sql, String[] selectionArgs){
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(sql, selectionArgs);
        }
        return cursor;
    }

    /**
     * 将查询的cursor对象转换成list集合
     * @param cursor 游标对象
     * @return 集合对象
     */
    public static List<Person> cursorToList(Cursor cursor){
        List<Person> list = new ArrayList<>();
        //moveTonext()如果返回true表示下一条记录存在，否则表示游标中数据读取完毕
        while(cursor.moveToNext()){
            //getColumnIndex(String columnName)根据参数中指定的字段名称获取字段下标
            int columnIndex = cursor.getColumnIndex(Constant.ID);
            //getInt(int columnIndex)根据参数中指定的字段下标，获取对应int类型的value
            int _id = cursor.getInt(columnIndex);
            String name = cursor.getString(cursor.getColumnIndex(Constant.NAME));
            int age = cursor.getInt(cursor.getColumnIndex(Constant.AGE));

            Person person = new Person(_id, name, age);
            list.add(person);
        }
        return list;
    }

    /**
     * 根据数据库以及数据表名称获取表中数据总条目
     * @param db 数据库对象
     * @param tableName 数据表名称
     * @return 返回总条目
     */
    //查询数据中总条目
    public static int getDataCount(SQLiteDatabase db, String tableName){
        int count= 0;
        if(db != null){
            String selectSql = "select * from " + Constant.TABLE_NAME;
            Cursor cursor = DbManager.selectDataBySql(db, selectSql, null);
            count = cursor.getCount();//获取cursor中的数据总数
        }
        return count;
    }

    /**
     * 根据当前页码查询获取该页码对应的集合数据
     * @param db 数据库对象
     * @param tableName 数据表名称
     * @param currentPage 当前页码
     * @param pageSize 每页展示的条目
     * @return 当前页对应的集合
     *
     * select * from person ?,? ;  如何根据当前页码获取该页数据
     * 0,20     1
     * 20,20    2
     * 40,20    3
     *
     */
    public static List<Person> getListByCurrentPage(SQLiteDatabase db, String tableName, int currentPage, int pageSize){
        int index = (currentPage -1) * pageSize;//获取当前页码第一条数据的下标

        Cursor cursor = null;
        if(db != null){
            String sql = "select * from " +tableName+ " limit ?,?";
            cursor = DbManager.selectDataBySql(db, sql, new String[]{index+"", pageSize+""});
        }
        return cursorToList(cursor);
    }
}
