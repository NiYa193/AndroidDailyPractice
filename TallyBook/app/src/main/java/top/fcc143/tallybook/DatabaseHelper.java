package top.fcc143.tallybook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by FCC on 2017/8/1.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, Constant.TALLY_BOOK, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库
        db.execSQL("create table if not exists TallyBook(" +
                "_id integer primary key, " +
                "cost_title varchar, " +
                "cost_date varchar, " +
                "cost_money varchar)");
    }

    public void insertCost(CostBean costBean) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constant.COST_TITLE, costBean.costTitle);
        cv.put(Constant.COST_DATE, costBean.costDate);
        cv.put(Constant.COST_MONEY, costBean.costMoney);
        database.insert(Constant.TALLY_BOOK, null, cv);
    }

    public Cursor getAllCostData() {
        SQLiteDatabase database = getWritableDatabase();
        //select * from TallyBook order by ASC;
        return database.query(Constant.TALLY_BOOK, null, null, null, null, null, Constant.COST_DATE + " ASC");
    }

    public void deleteAllData() {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(Constant.TALLY_BOOK, null, null);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
