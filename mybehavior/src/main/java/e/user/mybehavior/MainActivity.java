package e.user.mybehavior;

import android.content.Intent;
import androidx.annotation.Nullable;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn1;
    private MaterialCardView cardView;
    private ChipGroup chipGroup;
    private Chip chip1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        btn1 = findViewById(R.id.btn1);
        cardView = findViewById(R.id.card_view);
        chipGroup = findViewById(R.id.chip_group);
        chip1 = findViewById(R.id.chip_1);

        btn1.setOnClickListener(this);
        cardView.setOnClickListener(this);
        chip1.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                startActivity(new Intent(this,BehaviorTest1Activity.class));
                break;
            case R.id.card_view:
                startActivity(new Intent(this,BehaviorTest2Activity.class));
                break;
            case R.id.chip_1:
                startActivity(new Intent(this,SlideRecyclerViewActivity.class));
                break;
        }
    }
}
