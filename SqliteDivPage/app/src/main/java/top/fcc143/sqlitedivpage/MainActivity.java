package top.fcc143.sqlitedivpage;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.widget.AbsListView;
import android.widget.ListView;

import java.io.File;
import java.util.List;

import top.fcc143.adapter.MyBaseAdapter;
import top.fcc143.bean.Person;
import top.fcc143.utils.Constant;
import top.fcc143.utils.DbManager;


/**
 * 演示数据库分页
 * select * from person limit 0,15; 当前页码第一条数据下标，每页显示的数据条目
 *
 * 总条目
 * 每页一共展示几条
 * 总页数
 * 页码
 *
 * 1、当页码为1时在listview中展示相应数据
 * 2、当listview加载完本页数据分页加载下一页数据
 */
public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private SQLiteDatabase db;
    private int totalNum; //表示当前控件加载数据的总条目
    private int pageSize = 20;//表示每页展示的条目
    private int pageNum;//表示总页码，是计算得来的
    private int currentPage = 1;//当前代码
    private List<Person> totalList; //表示数据源
    private MyBaseAdapter adapter;
    private boolean isDivPage;//是否分页
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.lv);

        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+
                File.separator+"info.db";
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        //获取数据表数据总条目
        totalNum = DbManager.getDataCount(db, Constant.TABLE_NAME);
        //根据总条目与每页展示数据条目，获得总页数
        pageNum = (int) Math.ceil(totalNum / (double)pageSize);//ceil是向上取整函数，要求double
        if(currentPage ==1){
            totalList = DbManager.getListByCurrentPage(db, Constant.TABLE_NAME, currentPage, pageSize);
        }
        adapter = new MyBaseAdapter(this, totalList);
        lv.setAdapter(adapter);


        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(isDivPage && AbsListView.OnScrollListener.SCROLL_STATE_IDLE==scrollState){
                    if(currentPage < pageNum){
                        currentPage++;
                        //根据最新的页码加载获取集合存储到数据源中
                        totalList.addAll(DbManager.getListByCurrentPage(db, Constant.TABLE_NAME, currentPage,pageSize));
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isDivPage = ((firstVisibleItem + visibleItemCount) == totalItemCount);
            }
        });

    }
}
