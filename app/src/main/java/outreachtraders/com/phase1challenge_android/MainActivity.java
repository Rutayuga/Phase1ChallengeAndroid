package outreachtraders.com.phase1challenge_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    //widgets
    private Button about;
    private Button profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started.");
        about=findViewById(R.id.btn_about_alc);
        profile=findViewById(R.id.my_profile);

        about.setOnClickListener(this);
        profile.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btn_about_alc){
            Log.d(TAG,"onClick: Open about ALC");
            Intent intent=new Intent(MainActivity.this, about_alc.class);
            startActivity(intent);
        }
        else if (view.getId()==R.id.my_profile){
            Log.d(TAG,"onClick: Open my profile");
            Intent intent=new Intent(MainActivity.this, my_profile.class);
            startActivity(intent);
        }
    }
}
