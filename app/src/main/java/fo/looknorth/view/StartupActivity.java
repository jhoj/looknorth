package fo.looknorth.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import fo.looknorth.app.app.R;
import fo.looknorth.logik.Logik;

public class StartupActivity extends AppCompatActivity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        Logik.instance = new Logik();
        Logik.instance.lavTestData();

        SpannableString s = new SpannableString("Looknorth");
        s.setSpan(new TypefaceSpan("casual"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setTitle(s);

        ImageView logo = (ImageView) findViewById(R.id.logoImageView);
        TextView slogan = (TextView) findViewById(R.id.sloganTextView);

        int DURATION = 1500;
        int OFFSET = 10;

        Animation zoom = AnimationUtils.loadAnimation(this, R.anim.zoom_animation);
        logo.startAnimation(zoom);
        slogan.startAnimation(zoom);

        //if this is a fresh start
        if (savedInstanceState == null) {
            Logik.instance.handler.postDelayed(this, 3500);

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
        Logik.instance.handler.removeCallbacks(this);
    }
}
