package com.hui.dict;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 设置的Activity组件.
 */
public class SettingActivity extends AppCompatActivity {

    /**
     * 创建界面.
     * @param savedInstanceState 保存实例状态
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }


    /**
     * 点击事件.
     * @param view 点击的视图
     */
    public void onClick(View view) {
        if(view.getId() == R.id.setting_iv_back){
            finish();
        }
        else if(view.getId() == R.id.setting_tv_about){

        }
        else if(view.getId() == R.id.setting_tv_collect){
            Intent intent = new Intent(this, CollectionActivity.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.setting_tv_feedback){

        }
        else if(view.getId() == R.id.setting_tv_good){

        }
        else if(view.getId() == R.id.setting_tv_share){
            shareSoftware();
        }
    }

    /**
     * 分享软件到其他用户.
     */
    private void shareSoftware() {
        // 分享这个软件到其他用户
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String msg = "想随时查找汉字和成语详细内容么？快来下载中华字典APP吧！";
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        startActivity(Intent.createChooser(intent, "分享到...."));
    }
}
