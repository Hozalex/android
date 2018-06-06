package app.test.com.washtestapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import app.test.com.washtestapp.common.IntentHelper;
import app.test.com.washtestapp.models.Wash;

public class WashDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wash_detail);

        ImageView ivMain=findViewById(R.id.ivMain);
        TextView tvName=findViewById(R.id.tvName);
        TextView tvDescription=findViewById(R.id.tvDescription);

        Wash wash=Wash.getAllWashes(this)[getIntent().getIntExtra(IntentHelper.EXTRA_WASH_POS, 0)];
        setTitle(wash.name);
        ivMain.setImageResource(wash.imageId);
        tvName.setText(wash.name);
        tvDescription.setText(wash.descriptionId);
    }
}
