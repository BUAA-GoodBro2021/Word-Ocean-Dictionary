package com.super2021.dict;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.google.gson.Gson;
import com.super2021.dict.bean.StaticData;
import com.super2021.dict.bean.TuWenBean;
import com.super2021.dict.bean.ZiBean;
import com.super2021.dict.utils.FileUtils;
import com.super2021.dict.utils.PatternUtils;
import com.super2021.dict.utils.RecognizeService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView pinyinTv, bushouTv, chengyuTv, tuwenTv, juziTv;
    EditText ziEt;
    private boolean hasGotToken = false;
    private static final int REQUEST_CODE_GENERAL_BASIC = 106;
    private AlertDialog.Builder alertDialog;

    // 创建主页面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        alertDialog = new AlertDialog.Builder(this);
        initAccessTokenWithAkSk();
    }

    // 检查token状态
    private boolean checkTokenStatus() {
        if (!hasGotToken) {
            Toast.makeText(getApplicationContext(), "token还未成功获取", Toast.LENGTH_LONG).show();
        }
        return hasGotToken;
    }

    // 用明文ak，sk初始化token
    private void initAccessTokenWithAkSk() {
        OCR.getInstance(this).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                result.getAccessToken();
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                alertText(error.getMessage());
            }
        }, getApplicationContext(), "MSaY1m8CryxI44ILaMu3e76H", "iqZmCIIWOwTKCrsQP8h7ps54yOS4KSXc");
    }

    // 警告
    private void alertText(final String message) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.setTitle("AK，SK方式获取token失败")
                        .setMessage(message)
                        .setPositiveButton("确定", null)
                        .show();
            }
        });
    }

    // 通用文字识别
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 识别成功回调，通用文字识别
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GENERAL_BASIC && resultCode == Activity.RESULT_OK) {
            RecognizeService.recGeneralBasic(this, FileUtils.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            // result是识别出的字符串，可以将字符串传递给下一个界面
                            TuWenBean wenBean = new Gson().fromJson(result, TuWenBean.class);
                            List<TuWenBean.WordsResultBean> wordsList = wenBean.getWords_result();
                            // 将提取到的有用的汉字存放到集合当中，传递到下一个界面
                            ArrayList<String> list = new ArrayList<>();
                            if (wordsList != null && wordsList.size() != 0) {
                                for (int i = 0; i < wordsList.size(); i++) {
                                    TuWenBean.WordsResultBean bean = wordsList.get(i);
                                    String words = bean.getWords();
                                    String res = PatternUtils.removeAll(words);
                                    // 将字符串当中每一个字符串都添加到集合当中
                                    for (int j = 0; j < res.length(); j++) {
                                        String s = String.valueOf(res.charAt(j));
                                        // 添加集合之前，先判断一下，集合是否包括这个汉字
                                        if (!list.contains(s)) {
                                            list.add(s);
                                        }
                                    }
                                }
                                // 判断是否有可识别的文字
                                if (list.size() == 0) {
                                    Toast.makeText(MainActivity.this, "无法识别图片中的文字！", Toast.LENGTH_SHORT).show();
                                } else {
                                    Intent intent = new Intent(MainActivity.this, IdentifyImgActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putStringArrayList("wordlist", list);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            }
                        }
                    });
        }
    }

    // 销毁界面
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放内存资源
        OCR.getInstance(this).release();
    }

    // 初始化组件
    private void initView() {
        pinyinTv = findViewById(R.id.main_tv_pinyin);
        bushouTv = findViewById(R.id.main_tv_bushou);
        chengyuTv = findViewById(R.id.main_tv_chengyu);
        tuwenTv = findViewById(R.id.main_tv_tuwen);
        juziTv = findViewById(R.id.main_tv_juzi);
        ziEt = findViewById(R.id.main_et);
    }

    // 点击事件
    public void onClick(View view) {
        Intent intent = new Intent();
        if (view.getId() == R.id.main_iv_setting) {
            intent.setClass(this, SettingActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.main_iv_search) {
            String text = ziEt.getText().toString();
            if (!TextUtils.isEmpty(text)) {
                intent.setClass(this, ZiDetailActivity.class);
                intent.putExtra("zi", text);
                ZiBean ziBean = StaticData.ziBeanMap.get(text);
                if (ziBean != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "无此汉字！", Toast.LENGTH_LONG).show();
                }
            }
        } else if (view.getId() == R.id.main_tv_pinyin) {
            intent.setClass(this, SearchPinyinActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.main_tv_bushou) {
            intent.setClass(this, SearchBuShouActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.main_tv_chengyu) {
            intent.setClass(this, SearchChengyuActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.main_tv_tuwen) {
            if (!checkTokenStatus()) {
                return;
            }
            intent.setClass(MainActivity.this, CameraActivity.class);
            intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                    FileUtils.getSaveFile(getApplication()).getAbsolutePath());
            intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                    CameraActivity.CONTENT_TYPE_GENERAL);
            startActivityForResult(intent, REQUEST_CODE_GENERAL_BASIC);
        }
    }
}
