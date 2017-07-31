package top.fcc143.sqliteadapterdemo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import static top.fcc143.sqliteadapterdemo.R.id.parent;

public class CursorAdapterActivity extends AppCompatActivity {

    private ListView lv;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lv);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "info.db";
        Toast.makeText(CursorAdapterActivity.this, path, Toast.LENGTH_LONG).show();
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        String selectSql = "select * from " + Constant.TABLE_NAME;
        //Toast.makeText(CursorAdapterActivity.this, selectSql, Toast.LENGTH_LONG).show();
        Cursor cursor = DbManager.selectDataBySql(db, selectSql, null);
        //Cursor cursor = db.rawQuery("select * from" + Constant.DATABASE_NAME, null);

        //如下对应两种方法，只有SimpleCursorAdapter可以用得上，另外一个不能使用
        /*SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.layout, cursor,
                new String[]{Constant.ID, Constant.NAME, Constant.AGE},
                new int[]{R.id.tv_id, R.id.tv_name, R.id.tv_age},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lv.setAdapter(adapter);

*/

        //MyCursorAdapter adapter = new MyCursorAdapter(this, cursor, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        //lv.setAdapter(adapter);
    }


    public class MyCursorAdapter extends CursorAdapter{
        public MyCursorAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }

        /**
         * 表示创建适配器控件中每个item对应的view对象
         * @param context 上下文
         * @param cursor 数据源cursor对象
         * @param viewGroup 当前item的父布局
         * @return 每项item的view对象
         */
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            //View view = LayoutInflater.from(CursorAdapterActivity.this).inflate(R.layout.layout,null);
            View view = LayoutInflater.from(CursorAdapterActivity.this).inflate(R.layout.layout, lv, false);
            return view;//当回调view时，就知道每一项布局是什么样的
        }

        /**
         * 通过newView方法确定了每个item展示的view对象，在bindView中对布局的控件进行填充
         * @param view 由newView返回的每项view对象
         * @param context 上下文
         * @param cursor 数据源cursor对象
         */
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView tv_id = (TextView) view.findViewById(R.id.tv_id);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_age = (TextView) view.findViewById(R.id.tv_age);

            tv_id.setText(cursor.getInt(cursor.getColumnIndex(Constant.ID)) + "");
            //这里最后必须加一个双引号，不加的话就会被认为是资源ID去查找
            tv_id.setText(cursor.getInt(cursor.getColumnIndex(Constant.NAME)));
            tv_id.setText(cursor.getInt(cursor.getColumnIndex(Constant.AGE)) + "");


        }
    }
}
