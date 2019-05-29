package com.example.colordetect;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private ImageView imageView;
    private TextView textView;
    View mColorView;
    Bitmap bitmap;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @SuppressLint({"ResourceType", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        imageView = (ImageView)findViewById(R.id.imageView);
        textView = findViewById(R.id.resultTv);
        mColorView = findViewById(R.id.colorView);

        int imageResource = getResources().getIdentifier("@drawable/navfamily", null, this.getPackageName());
        imageView.setImageResource(imageResource);

        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache(true);

//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(event )
//            }
//        });

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    if ((int)event.getX() >= 0 && (int)event.getY()>=0){
                        bitmap = imageView.getDrawingCache();
                        Toast.makeText(getApplicationContext(),"Разрешение:" + bitmap.getHeight() + "x" +  bitmap.getWidth(), Toast.LENGTH_SHORT).show();

                        int pixel = bitmap.getPixel((int)event.getX() < bitmap.getWidth() ? (int)event.getX() : 0, (int)event.getY() < bitmap.getHeight() ? (int)event.getY() : 0 );

                        //getting RGB
                        int r = Color.red(pixel);
                        int g = Color.green(pixel);
                        int b = Color.blue(pixel);

                        //getting HEX
                        String hex = "#" + Integer.toHexString(pixel);

                        //устанавливаем background для view
                        mColorView.setBackgroundColor(Color.rgb(r,g,b));

                        //устанавливаем значения в textview
                        textView.setText("RGB: " + r + ", " + g + ", " + b + "\nHEX: " + hex);
                    } else {
                        return false;
                    }
                }
                return true;
            }
        });


    }

}
