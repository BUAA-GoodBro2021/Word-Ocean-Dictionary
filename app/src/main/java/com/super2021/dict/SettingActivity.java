package com.super2021.dict;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {

    // 创建界面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        TextView tv =findViewById(R.id.setting_tv_about);
        SpannableString link = new SpannableString("关于我们");
        link.setSpan(new URLSpan("https://github.com/BUAA-GoodBro2021/Word-Ocean-Dictionary"),
                0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        NoUnderlineSpan noUnderlineSpan = new NoUnderlineSpan();
        link.setSpan(noUnderlineSpan, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#000000"));
        link.setSpan(foregroundColorSpan, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(link);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }


    // 点击事件
    public void onClick(View view) {
        if (view.getId() == R.id.setting_iv_back) {
            finish();
        } else if (view.getId() == R.id.setting_tv_about) {

        } else if (view.getId() == R.id.setting_tv_collect) {
            Intent intent = new Intent(this, CollectionActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.setting_tv_feedback) {

        } else if (view.getId() == R.id.setting_tv_good) {

        } else if (view.getId() == R.id.setting_tv_share) {
            shareSoftware();
        }
    }

    // 分享软件
    private void shareSoftware() {
        // 分享这个软件到其他用户
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String msg = "https://github.com/BUAA-GoodBro2021/Word-Ocean-Dictionary";
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        startActivity(Intent.createChooser(intent, "分享链接"));
    }

    static class NoUnderlineSpan extends UnderlineSpan {
        @Override
        public void updateDrawState(TextPaint tp) {
            tp.setColor(tp.linkColor);
            tp.setUnderlineText(false);
        }
    }
}
