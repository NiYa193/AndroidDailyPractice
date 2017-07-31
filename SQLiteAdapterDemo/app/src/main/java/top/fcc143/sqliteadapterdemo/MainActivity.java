package top.fcc143.sqliteadapterdemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;

/**
 * 演示查询sdcard中数据库表中的数据适配到listview中
 */
public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.lv);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()
               + File.separator + "info.db";
        /*
        * 1、打开数据库
        * String path 打开数据库的指定路径
        * CursorFactory factory 游标，为空即可
        * int flags 表示打开数据库的操作模式
        * public static SQLiteDatabase openDatabase(String path, SQLiteDatabase.CursorFactory factory, int flags) {
        *    throw new RuntimeException("Stub!");
        *}
        */
        Toast.makeText(MainActivity.this, path, Toast.LENGTH_LONG).show();
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        //Cursor cursor = db.rawQuery("select * from" + Constant.DATABASE_NAME, null);该语句查询失败
        String selectSql = "select * from " + Constant.TABLE_NAME;
        Toast.makeText(MainActivity.this, selectSql, Toast.LENGTH_LONG).show();
        Cursor cursor = DbManager.selectDataBySql(db, selectSql, null);

        //2、将数据源数据加载到适配器当中，与SimpleAdapter只是接收类型不一样
        /*
        * Context context 上下文对象
        * int layout 表示适配器控件中每项item的布局id
        * Cursor c 表示Cursor数据源
        * String [] from 表示cursor中数据表字段的数组
        * int [] to表示展示字段对应值的控件资源id
        * int flags 设置适配器的标记
        *
        * public SimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        *    super(context, layout, c);
        *    mTo = to;
        *    mOriginalFrom = from;
        *    findColumns(c, from);
        * }
        */
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.layout, cursor,
                new String[]{Constant.ID, Constant.NAME, Constant.AGE},
                new int[]{R.id.tv_id, R.id.tv_name, R.id.tv_age},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lv.setAdapter(adapter);
    }
}
