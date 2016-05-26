package fo.looknorth.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import fo.looknorth.app.app.R;
import fo.looknorth.logic.Logic;

public class StartupActivity extends AppCompatActivity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("StartupActivity", "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        fetchData();
        Logic.instance.initMqtt(this.getApplicationContext());

        SpannableString s = new SpannableString(getResources().getString(R.string.app_name));
        s.setSpan(new TypefaceSpan("casual"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setTitle(s);

        ImageView logo = (ImageView) findViewById(R.id.logoImageView);
        TextView slogan = (TextView) findViewById(R.id.sloganTextView);

        Animation zoom = AnimationUtils.loadAnimation(this, R.anim.zoom_animation);
        logo.startAnimation(zoom);
        slogan.startAnimation(zoom);

        //if this is a fresh start
        if (savedInstanceState == null) {
            Logic.instance.handler.postDelayed(this, 3000);
        }
    }


    @Override
    public void run() {
        this.startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }

    @Override
    public void finish() {
        super.finish();
        Logic.instance.handler.removeCallbacks(this);
    }

    public void fetchData() {
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    Logic.instance.init();
                    return "Data er hentet!";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Data blev ikke hentet.";
                }
            }

            @Override
            protected void onPostExecute(Object besked) {
                System.out.println(besked);
            }
        }.execute();
    }
}
