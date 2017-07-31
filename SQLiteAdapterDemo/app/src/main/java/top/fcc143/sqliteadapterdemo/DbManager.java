package top.fcc143.sqliteadapterdemo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by FCC on 2017/7/30.
 */

public class DbManager {
    private static MySQLiteHelper helper;
    public static  MySQLiteHelper getInstance(Context context){
        if(helper == null){
            helper = new MySQLiteHelper(context);
        }
        return helper;
    }

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
}