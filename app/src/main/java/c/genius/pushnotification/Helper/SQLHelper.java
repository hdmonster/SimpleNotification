package c.genius.pushnotification.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Haydar Dzaky S on 3/24/2018.
 */

public class SQLHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Crud_SQLite";
    private static final int DATABASE_VERSION = 1;


    public SQLHelper(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table Data(id integer primary key, alarm timestamp null);";
        Log.d("Data", "onCreate:" +sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }
}
