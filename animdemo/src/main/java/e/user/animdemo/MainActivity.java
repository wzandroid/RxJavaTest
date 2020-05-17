package e.user.animdemo;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClick(@NonNull View v){
        switch (v.getId()){
            case R.id.btn1:
                startActivity(new Intent(this,MotionActivity.class));
                break;
        }
    }
}
