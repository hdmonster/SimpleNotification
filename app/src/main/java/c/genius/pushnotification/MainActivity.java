package c.genius.pushnotification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

import c.genius.pushnotification.Helper.SQLHelper;


public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    SQLHelper dbHelper;
    private Button timepick, cancelAlarm, saveAlarm, updateAlarm;
    private TextView textAlarm, setAlarm, Continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new SQLHelper(this);

        Continue = (TextView) findViewById(R.id.txtCont);
        setAlarm = (TextView) findViewById(R.id.alarmSet);
        textAlarm = (TextView) findViewById(R.id.txtAlarm);

        updateAlarm = (Button) findViewById(R.id.btnUpdate);
        saveAlarm = (Button) findViewById(R.id.btnSave);
        cancelAlarm = (Button) findViewById(R.id.btnCancel);
        timepick = (Button) findViewById(R.id.btnTimePicker);
        timepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "Time Picker");
            }
        });

        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });

        saveAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAlarm();
            }
        });

        updateAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAlarm();
            }
        });

        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(i);
            }
        });
    }





    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);

        updateTimeText(cal);
        startAlarm(cal);

    }

    public void updateTimeText(Calendar cal) {
        String timeText = "Alarm Set To:";
        String alarmFinal = DateFormat.getTimeInstance(DateFormat.SHORT).format(cal.getTime());
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(cal.getTime());

        textAlarm.setText(timeText);
        setAlarm.setText(alarmFinal);
    }

    private void startAlarm(Calendar cal) {

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if(cal.before(Calendar.getInstance())){
            cal.add(Calendar.DATE, 1);
        }

        //alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

        /* Repeating on every day interval */
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
        textAlarm.setText("Alarm Dissmised");
    }

    private void saveAlarm(){

        // TODO Auto-generated method stub
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("insert into Data(id, alarm) values('" +
                1 + "','" + setAlarm.getText().toString() + "')");

        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
        Intent i = new Intent(MainActivity.this, MenuActivity.class);
        startActivity(i);
    }

    private void updateAlarm(){
        // TODO Auto-generated method stub
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("update Data set alarm='"+
                setAlarm.getText().toString()+"' where id='" + 1 +"'");
        Toast.makeText(getApplicationContext(), "Jadwal Has Been Updated", Toast.LENGTH_LONG).show();
        Intent i = new Intent(MainActivity.this, MenuActivity.class);
        startActivity(i);
    }

}
