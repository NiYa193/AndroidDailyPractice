package top.fcc143.tallybook;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<CostBean> mCostBeanList;
    private DatabaseHelper mDatabaseHelper;
    private CostListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDatabaseHelper = new DatabaseHelper(this);
        mCostBeanList = new ArrayList<>();
        ListView costList = (ListView) findViewById(R.id.lv_main);
        initCostData();
        adapter = new CostListAdapter(this, mCostBeanList);
        costList.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                //设置该步，才可以找到这个layout里面的控件
                View viewDialog = inflater.inflate(R.layout.new_cost_data, null);
                final EditText title = (EditText) viewDialog.findViewById(R.id.et_cost_title);
                final EditText money = (EditText) viewDialog.findViewById(R.id.et_cost_money);
                final DatePicker date = (DatePicker) viewDialog.findViewById(R.id.dp_cost_date);
                builder.setView(viewDialog);
                builder.setTitle("新建记录");
                builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //加入对title和money的判断，看看是否为空值，空值则提醒用户重新输入
                        if(("".equals(title.getText().toString().trim()))  ||  ("".equals(money.getText().toString().trim()))){
                            Toast.makeText(MainActivity.this, "要输入完整信息哦", Toast.LENGTH_LONG).show();
                        } else {
                            CostBean costBean = new CostBean();
                            costBean.costTitle = title.getText().toString();
                            costBean.costMoney = money.getText().toString();
                            costBean.costDate = date.getYear() + "-" + (date.getMonth() + 1) + "-" +
                                    date.getDayOfMonth();
                            mDatabaseHelper.insertCost(costBean);
                            mCostBeanList.add(costBean);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                builder.setNegativeButton("不要", null);
                builder.create().show();
            }
        });
    }

    //测试用数据
    private void initCostData() {
        //测试用代码
        //创建之前先把之前的数据都清空
//        mDatabaseHelper.deleteAllData();
//        //将初始数据插入数据库
//        for (int i = 1; i <= 6; i++) {
//            CostBean costBean = new CostBean();
//            costBean.costTitle = "豆豆" + i;
//            costBean.costDate = "10-04";
//            costBean.costMoney = "20";
//            mDatabaseHelper.insertCost(costBean);
//        }

        //取出数据
        Cursor cursor = mDatabaseHelper.getAllCostData();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                CostBean costBean = new CostBean();
                costBean.costTitle = cursor.getString(cursor.getColumnIndex("cost_title"));
                costBean.costDate = cursor.getString(cursor.getColumnIndex("cost_date"));
                costBean.costMoney = cursor.getString(cursor.getColumnIndex("cost_money"));
                mCostBeanList.add(costBean);
            }
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, ChartsActivity.class);
            intent.putExtra("cost_list", (Serializable) mCostBeanList);
            startActivity(intent);
            return true;
        }else if(id == R.id.action_delete){
            mDatabaseHelper.deleteAllData();
            clearList(mCostBeanList);
        }
        return super.onOptionsItemSelected(item);
    }

    //清空所有数据后重新加载ListView的方法
    public void clearList(List<CostBean> mCostBeanList) {
        int size = mCostBeanList.size();
        if (size > 0) {
            mCostBeanList.removeAll(mCostBeanList);
            adapter.notifyDataSetChanged();
        }
    }
}