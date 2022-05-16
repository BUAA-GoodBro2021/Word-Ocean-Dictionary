package com.example.word_ocean_dictionary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class IdentifyImgActivity extends AppCompatActivity {
    GridView gv;
    ArrayList<String>mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_img);
        gv = findViewById(R.id.iden_gv);
//        获取上一个界面传递的数据
        Bundle bundle = getIntent().getExtras();
        mDatas = bundle.getStringArrayList("wordlist");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_search_pgv, R.id.item_sgv_tv, mDatas);
        gv.setAdapter(adapter);
        setGVListener();
    }

    private void setGVListener() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String word = mDatas.get(position);
                Intent intent = new Intent(IdentifyImgActivity.this, WordInfoActivity.class);
                intent.putExtra("zi",word);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        if (view.getId() == R.id.iden_iv_back) {
            finish();
        }
    }
}
