package c.genius.pushnotification;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import c.genius.pushnotification.Helper.SQLHelper;

public class MenuActivity extends AppCompatActivity {

    protected Cursor cursor;
    SQLHelper dbHelper;
    TextView jadwal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        dbHelper = new SQLHelper(this);
        jadwal = (TextView) findViewById(R.id.txtJadwal);

        Refresh();
    }

    private void Refresh(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM Data", null);
        cursor.moveToFirst();
        for (int cc = 0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            jadwal.setText("Jadwal Belajar: " + cursor.getString(1).toString());
        }
    }
}
