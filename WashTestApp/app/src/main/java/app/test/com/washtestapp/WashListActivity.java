package app.test.com.washtestapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import app.test.com.washtestapp.adapters.WashAdapter;
import app.test.com.washtestapp.common.IntentHelper;
import app.test.com.washtestapp.models.Wash;

public class WashListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wash_list);

        RecyclerView rvWashes=findViewById(R.id.rvWashes);
        rvWashes.setHasFixedSize(true);
        rvWashes.setLayoutManager(new LinearLayoutManager(this));
        rvWashes.setAdapter(new WashAdapter(Wash.getAllWashes(this), new WashAdapter.OnWashClickListener() {
            @Override
            public void onWashClick(int washPos) {
                startActivity(new Intent(WashListActivity.this, WashDetailActivity.class)
                    .putExtra(IntentHelper.EXTRA_WASH_POS, washPos));
            }
        }));
        rvWashes.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL ));
    }
}
