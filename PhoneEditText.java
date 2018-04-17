package com.aiche.recharge.view;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;

/**
 * 文件名:    PhoneEditText
 * 创建时间:  2018/4/17 on 14:51
 * 描述:     TODO 一个自动分割手机号码的输入框
 *          eg:132 xxxx xxx
 *          <p>当然中间这个分割符号你可以自己定义
 *          修改这个属性的值即可{@link flag}，（目前支持分隔符只占一个长度）
 *          </p>
 *
 * @author 阿钟
 */

public class PhoneEditText extends AppCompatEditText implements TextWatcher {
    /**
     * 分割符
     */
    private final String flag = " ";

    public PhoneEditText(Context context) {
        super(context);
        init();
    }

    public PhoneEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //设置输入框允许输入的类型（正则）
        setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        //设置输入字符
        addTextChangedListener(this);
        InputFilter.LengthFilter filter = new InputFilter.LengthFilter(13);
        setFilters(new InputFilter[]{filter});
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        StringBuilder sb = new StringBuilder(s);
        int length = sb.length();
        if (before == 0) {
            //输入内容
            if (length == 4) {
                sb.insert(3, flag);
                setText(sb.toString());
            } else if (length == 9) {
                sb.insert(8, flag);
                setText(sb.toString());
            }
        } else if (length > 0) {
            //删除内容
            if (length == 4) {
                sb.deleteCharAt(3);
                setText(sb.toString());
            } else if (length == 9) {
                sb.deleteCharAt(8);
                setText(sb.toString());
            }
        }
        Selection.setSelection(getAllText(), sb.length());
    }

    /**
     * 获取实际的手机号
     *
     * @return 没有分割符号的字符串
     */
    public String getRealPhone() {
        String editable = super.getText().toString();
        String number = editable.replaceAll(flag, "");
        return number;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (text.length() == 11) {
            StringBuilder sb = new StringBuilder(text);
            sb.insert(3, flag).insert(8, flag);
            setText(sb.toString());
        } else {
            super.setText(text, type);
        }
    }

    public Editable getAllText() {
        return super.getText();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
