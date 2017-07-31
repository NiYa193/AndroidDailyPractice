package top.fcc143.sqlitetransation;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MySQLiteHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new MySQLiteHelper(this);
    }
    //点击按钮，批量插入
    public void insertData(View view){
        SQLiteDatabase db = helper.getWritableDatabase();
        //1、数据库显式开启事务
        db.beginTransaction();
        for(int i = 1; i < 1000000; i++){
            String sql = "insert into " +Constant.TABLE_NAME+ " values("+i+", '闯闯"+i+"', 18)";
            db.execSQL(sql);
        }
        //2、提交当前事务
        db.setTransactionSuccessful();
        //3、关闭事务
        db.endTransaction();
        db.close();
        Toast.makeText(MainActivity.this, "数据插入完成", Toast.LENGTH_LONG).show();
    }
}
