package top.fcc143.sqlitetransation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 *
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    /**
     * 构造函数
     * @param context 上下文对象
     * @param name 当前创建数据库的名称
     * @param factory 游标工厂
     * @param version 表示创建数据库的版本，要求>=1
     */
    public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //普通创建时，可以采用这个简单的方法
    public MySQLiteHelper(Context context){
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
    }

    /**
     * 当数据库创建时回调的函数
     * @param db 数据库对象
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("tag", "--------------onCreate-----------");
        String sql = "create table person(_id int primary key , name varchar(30), age int(10))";
        db.execSQL(sql);
    }


    /**
     * 当数据库版本更新时回调的函数
     * @param db 数据库对象
     * @param i 数据库旧版本
     * @param i1 数据库新版本
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i("tag", "----------------onUpgrade-----------");
    }

    /**
     * 当数据库打开回调的函数
     * @param db 数据库对象
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        Log.i("tag", "--------------onOpen--------------");
    }
}
