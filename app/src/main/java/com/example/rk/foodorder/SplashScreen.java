package com.example.rk.foodorder;

        import android.accessibilityservice.AccessibilityService;
        import android.content.Intent;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.content.*;
        import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {
    TextView textView1,textView2;
    ImageView imageView1,imageView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        textView1=findViewById(R.id.txtView1);
        textView2=findViewById(R.id.txtView2);
        imageView1=findViewById(R.id.imageView1);
        //imageView2=findViewById(R.id.imageView2);
        Animation animation= AnimationUtils.loadAnimation(this, R.anim.mytransation);
        textView1.startAnimation(animation);
        textView2.startAnimation(animation);
        imageView1.startAnimation(animation);
        //imageView2.startAnimation(animation);
        ConnectivityManager cm =(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        //Toast.makeText(this, ""+isConnected, Toast.LENGTH_SHORT).show();
        if(isConnected) {
            final Intent intent = new Intent(this, MainActivity.class);
            Thread thread = new Thread() {
                public void run() {
                    try {
                        sleep(4000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        startActivity(intent);
                        finish();
                    }

                }
            };
            thread.start();
        }
        else
        {
            Toast.makeText(this, "Internet Connection is required", Toast.LENGTH_SHORT).show();
            Thread thread=new Thread()
            {
                public void run()
                {
                    try {
                        sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finally {
                        finish();
                    }
                }
            };
            thread.start();
        }
    }
}
