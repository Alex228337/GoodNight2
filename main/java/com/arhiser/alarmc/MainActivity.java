package com.arhiser.alarmc;

import android.app.Activity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import java.util.Calendar;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    int stat ;
    int af ;
    AudioManager audioManager;
    MediaPlayer mPlayer;
    Dialog dialog1;
    Dialog dialog;
    Button setAlarm;
    ImageView condition;
    Button soVet;
    Button information;
    Button statistic;
    TextView ststext;
    ImageButton gbtn;


    @Override
    public void onBackPressed() {
        af++;
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        condition.setImageResource(R.drawable. angry);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                condition.setImageResource(R.drawable.relax);
            }
        }, 300);

        mPlayer=MediaPlayer.create(this, R.raw.audiomonstrr);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
            }
        });
        mPlayer.start();
    }
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());

        setAlarm = findViewById(R.id.alarm_button);
        condition = findViewById(R.id.monSleep);
        setAlarm.setOnClickListener(v -> {
            MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(12)
                    .setMinute(0)
                    .setTitleText("Выберите время для будильника")
                    .build();

            materialTimePicker.addOnPositiveButtonClickListener(view -> {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.set(Calendar.MINUTE, materialTimePicker.getMinute());
                calendar.set(Calendar.HOUR_OF_DAY, materialTimePicker.getHour());

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(), getAlarmInfoPendingIntent());

                alarmManager.setAlarmClock(alarmClockInfo, getAlarmActionPendingIntent());
                Toast.makeText(this, "Будильник установлен на " + sdf.format(calendar.getTime()), Toast.LENGTH_SHORT).show();
                condition.setImageResource(R.drawable.sleep);
                System.out.println("hello world");

                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(this)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("Title")
                                .setContentText("Notification text")
                                .setWhen(System.currentTimeMillis());
                Notification notification = builder.build();

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(1, notification);

            });
            materialTimePicker.show(getSupportFragmentManager(), "tag_picker");
            condition.setImageResource(R.drawable.relax);

        });

        dialog1 = new Dialog(this);
        information = (Button) findViewById(R.id.inf);
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setContentView(R.layout.read_dial);
                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog1.setCancelable(false);
                dialog1.show();
                //закрыт начало
                TextView btnclose = (TextView)dialog1.findViewById(R.id. btni);
                btnclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //обработка нач
                        try{
                            //закрыть нач
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                            //закрыть конец
                        }catch (Exception e){
                            //koda netu
                        }
                        //обработка кон
                    }
                });
                //закрыт конец
            }
        });
        gbtn = findViewById(R.id. minigame);

        statistic = findViewById(R.id. stbtn);
        dialog = new Dialog(this);
        soVet = (Button) findViewById(R.id.lbtn);
        soVet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.show();
                //закрыт начало
                TextView btnclose = (TextView)dialog.findViewById(R.id. btnclose);
                btnclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //обработка нач
                        try{
                            //закрыть нач
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                            //закрыть конец
                        }catch (Exception e){
                            //koda netu
                        }

                        //обработка кон

                    }

                });

                //закрыт конец




            }
        });
        //вызов диал. окна
    }



    public void finishGame(View vuw){
        stat++;
        if(af>5){
            setAlarm.setVisibility(View.INVISIBLE);
            condition.setVisibility(View.INVISIBLE);
            soVet.setVisibility(View.INVISIBLE);
            information.setVisibility(View.INVISIBLE);
            statistic.setVisibility(View.INVISIBLE);
            gbtn.setVisibility(View.VISIBLE);
            Toast.makeText(MainActivity.this, "Ты попытался выйти более 5 раз, тебе прилдется нажать на меня 10 раз ", Toast.LENGTH_SHORT).show();
            if (stat == 10 ){
                setAlarm.setVisibility(View.VISIBLE);
                condition.setVisibility(View.VISIBLE);
                soVet.setVisibility(View.VISIBLE);
                information.setVisibility(View.VISIBLE);
                statistic.setVisibility(View.VISIBLE);
                af = 0;
            }

        }
    }
    public void displayToast(View v){
        Toast.makeText(MainActivity.this, "Вы разозлили монстра " + af + " раз(а)", Toast.LENGTH_SHORT).show();

    }

    private PendingIntent getAlarmInfoPendingIntent() {
        Intent alarmInfoIntent = new Intent(this, MainActivity.class);
        alarmInfoIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(this, 0, alarmInfoIntent, PendingIntent.FLAG_UPDATE_CURRENT);

    }

    private PendingIntent getAlarmActionPendingIntent() {
        Intent intent = new Intent(this, AlarmActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private class Builder {
        public Builder(Activity activity) {
        }
    }


}