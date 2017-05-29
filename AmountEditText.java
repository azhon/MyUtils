package com.zsy.amount;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;

/**
 * 2017年5月29日23:59:32
 * @author 阿钟
 * @version 1.0
 * TODO 金额输入框，只能输入两位小数
 */
public class AmountEditText extends AppCompatEditText implements TextWatcher {


    public AmountEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AmountEditText(Context context) {
        super(context);
        init();
    }

    private void init() {
        //设置输入框允许输入的类型（正则）
        setKeyListener(DigitsKeyListener.getInstance("0123456789."));
        //设置输入字符
        setFilters(new InputFilter[]{inputFilter});
        addTextChangedListener(this);
    }

    private InputFilter inputFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            // 删除等特殊字符，直接返回
            if (TextUtils.isEmpty(source)) {
                return null;
            }
            String dValue = dest.toString();
            String[] splitArray = dValue.split("\\.");
            if (splitArray.length > 1) {
                String dotValue = splitArray[1];
                int diff = dotValue.length() + 1 - 2;//2表示输入框的小数位数
                if (diff > 0) {
                    return source.subSequence(start, end - diff);
                }
            }
            return null;
        }
    };
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(s)) {
            return;
        }
        //第一个字符不为小数点
        if (s.length() == 1 && s.toString().equals(".")) {
            setText("");
            return;
        }
        int counter = counter(s.toString(), '.');
        if (counter > 1) {
            //小数点第一次出现的位置
            int index = s.toString().indexOf('.');
            setText(s.subSequence(0, index + 1));
        }
        setSelection(getText().toString().length());
    }
    /**
     * 统计一个字符在字符串中出现的次数
     *
     * @param s 字符串
     * @param c 字符
     * @return 數量
     */
    public int counter(String s, char c) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }
    @Override
    public void afterTextChanged(Editable s) {

    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

}
