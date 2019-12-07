package org.turings.turings.index.util;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//工具类，改变关键文字颜色
public class KeyWordUtil {
    public static SpannableString setSpannableString(int color,String text,String[] keyword){
        SpannableString v=new SpannableString(text);
        for(int i=0;i<keyword.length;i++){
//            正则表达式
            Pattern p=Pattern.compile(keyword[i]);
            Matcher m=p.matcher(v);
            while(m.find()){
                int start=m.start();
                int end=m.end();
                v.setSpan(new ForegroundColorSpan(color),start,end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                //SPAN_INCLUSIVE_EXCLUSIVE不包含起始位置和结束位置，只是中间位置内容


            }
        }
        return v;
    }
}
