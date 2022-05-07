package com.example.word_ocean_dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView pinyinTv, bushouTv, chengyuTv, tuwenTv, yijuTv;
    EditText searchEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        pinyinTv = findViewById(R.id.main_tv_pinyin);
        bushouTv = findViewById(R.id.main_tv_bushou);
        chengyuTv = findViewById(R.id.main_tv_chengyu);
        tuwenTv = findViewById(R.id.main_tv_tuwen);
        yijuTv = findViewById(R.id.main_tv_yiju);
        searchEt = findViewById(R.id.main_et_search);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_iv_setting:
                break;
            case R.id.main_iv_search:
                break;
            case R.id.main_et_search:
                break;
            case R.id.main_tv_pinyin:
                break;
            case R.id.main_tv_bushou:
                break;
            case R.id.main_tv_chengyu:
                break;
            case R.id.main_tv_tuwen:
                break;
            case R.id.main_tv_yiju:
                break;
            default:
                break;
        }
    }
}