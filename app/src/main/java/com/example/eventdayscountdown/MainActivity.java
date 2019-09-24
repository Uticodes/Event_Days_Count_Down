package com.example.eventdayscountdown;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView displayInfor;
    private TextView daysLeft, hour_tv, minutestv, secondstv;
    private String END_DATE_TIME = "2019-11-16 8:00:00";
    private String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private Handler handler = new Handler();
    private Runnable runnable;
    private ImageView mealBtn, profileBtn, ticketBtn;
    private long backpressedTime; //to implement backpressed with notification

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setElevation(0);

        displayInfor = findViewById(R.id.userInfo);
        daysLeft = findViewById(R.id.textView_days_rem);
        hour_tv = findViewById(R.id.num_text_hr);
        minutestv = findViewById(R.id.num_minutes);
        secondstv = findViewById(R.id.num_seconds);

        mealBtn = findViewById(R.id.meal_imageView);
        profileBtn = findViewById(R.id.profile_imagV);
        ticketBtn = findViewById(R.id.ticket_imageView);

        //retrieve shared preference
        SharedPreferences result = getSharedPreferences("DEVFESTPREFS", Context.MODE_PRIVATE);
        String display = result.getString("Value", "Data not found");

        displayInfor.setText(display);


        mealBtn.setOnClickListener(this);
        profileBtn.setOnClickListener(this);
        ticketBtn.setOnClickListener(this);

        initCountdown(); //initialize countdown
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.meal_imageView:
                Toast.makeText(this, "Get your meal ticket for the event!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.profile_imagV:
                userInfo();
                break;

            case R.id.ticket_imageView:
                Toast.makeText(this, "Book a ticket to the event", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;

        }


    }

    private void userInfo() {
        SharedPreferences result = getSharedPreferences("DEVFESTPREFS", Context.MODE_PRIVATE);
        String display = result.getString("allInfo", null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(display);
        builder.setTitle("ABOUT USER");
        builder.setPositiveButton(R.string.user_confirmation_ok, null);
        builder.create().show();


    }

    private void initCountdown() {
        runnable = new Runnable() {

            @Override
            public void run() {

                try {
                    handler.postDelayed(this, 1000);
                    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                    Date devf_end_date = dateFormat.parse(END_DATE_TIME);
                    Date current_date = new Date();
                    if (!current_date.after(devf_end_date)) {
                        long diff = devf_end_date.getTime() - current_date.getTime();
                        long Days = diff / (24 * 60 * 60 * 1000);
                        long Hours = diff / (60 * 60 * 1000) % 24;
                        long Minutes = diff / (60 * 1000) % 60;
                        long Seconds = diff / 1000 % 60;

                        daysLeft.setText(String.format("%02d", Days));
                        hour_tv.setText(String.format("%02d", Hours));
                        minutestv.setText(String.format("%02d", Minutes));
                        secondstv.setText(String.format("%02d", Seconds));


                    } else {
                        handler.removeCallbacks(runnable);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        };
        handler.postDelayed(runnable, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.btn_logout).setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_about:
                aboutApp();
                break;

            case R.id.btn_logout:
                logout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        finish();
    }

    private void aboutApp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.about_app_builder);
        builder.setTitle(R.string.about_app);
        builder.setPositiveButton(R.string.user_confirmation_ok, null);
        builder.create().show();
    }

    @Override
    public void onBackPressed() {
        if (backpressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();

        } else {
            Toast.makeText(this, "Press back again to exit!", Toast.LENGTH_SHORT).show();
        }
        backpressedTime = System.currentTimeMillis();
    }


}