package com.w.school_herper_front.listener;
/**
 * CONTENT:listen text change
 * DEVELOPDER:Zhangxixian
 * Date:18/12/18
 */
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class TextChange implements TextWatcher {
    private EditText editText1 = null;
    private EditText editText2 = null;
    private TextView textView = null;
    private Button btn = null;
    static final int THEME_COLOR = Color.parseColor("#F8B511");

    public TextChange(EditText editText1,EditText editText2,TextView textView,Button btn){
        this.editText1 = editText1;
        this.editText2 = editText2;
        this.textView = textView;
        this.btn = btn;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String etStr1 = editText1.getText().toString();
        String etStr2 = editText2.getText().toString();
        String tvStr = textView.getText().toString();
        if(!etStr1.isEmpty() && !etStr2.isEmpty()&& !tvStr.isEmpty()){
            btn.setBackgroundColor(THEME_COLOR);
        }

    }

}
