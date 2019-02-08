package com.vinsoft.lemi;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tvLabel)
    TextView label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100){
            if(resultCode == RESULT_OK){
                label.setVisibility(View.VISIBLE);
                label.setText(Html.fromHtml("You selected: " + "<b>" + data.getStringExtra("cityname") + "</b>"));
            }
        }
    }

    @OnClick(R.id.toolbar) void onTap(){
        openSearchScreen();
    }

    void openSearchScreen(){
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivityForResult(intent, 100);
    }
}
