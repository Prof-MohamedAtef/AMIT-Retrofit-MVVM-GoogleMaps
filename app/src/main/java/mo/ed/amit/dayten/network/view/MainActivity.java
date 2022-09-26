package mo.ed.amit.dayten.network.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.FrameLayout;

import mo.ed.amit.dayten.network.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FrameLayout frameLayout= findViewById(R.id.frameLayout);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(frameLayout.getId(), MyFragment.newInstance());
    }
}