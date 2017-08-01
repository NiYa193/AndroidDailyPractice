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
        super(context, "TallyBook", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists TallyBook(" +
                "_id integer primary key, " +
                "cost_title varchar, " +
                "cost_date varchar, " +
                "cost_money varchar)");
    }

    public void insertCost(CostBean costBean) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("cost_title", costBean.costTitle);
        cv.put("cost_date", costBean.costDate);
        cv.put("cost_money", costBean.costMoney);
        database.insert("TallyBook", null, cv);
    }

    public Cursor getAllCostData() {
        SQLiteDatabase database = getWritableDatabase();
        //select * from TallyBook order by ASC;
        return database.query("TallyBook", null, null, null, null, null, "cost_date " + "ASC");
    }

    public void deleteAllData() {
        SQLiteDatabase database = getWritableDatabase();
        database.delete("TallyBook", null, null);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
