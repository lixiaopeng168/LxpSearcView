package com.lxp.search;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：lixiaopeng on 2017/9/8 0008.
 * 邮箱：lixiaopeng186@163.com
 * 描述：
 */

public class UIUtils {


    public static Context getContext(){
        return AppContext.getInstance();
    }

    public static String getString(int text){
       return getContext().getString(text);
    }
    public static List<String> getStringArray(int array){
        List<String> list = new ArrayList<>();
        String[] stringArray = getContext().getResources().getStringArray(array);
        for (int i = 0; i < stringArray.length; i++) {
            list.add(stringArray[i]);
        }
        return list;
    }
    public static int getDiment(int id){
        return (int) getContext().getResources().getDimension(id);
    }
    public static int getColor(int color){
        return getContext().getResources().getColor(color);
    }
    private static Toast toast = null;
    public static void showToast(String text){
        if (toast == null){
            toast = Toast.makeText(getContext(),text,Toast.LENGTH_SHORT);
        }else {
            toast.setText(text);
        }
        toast.show();
    }
    public static void showToastString(int text){
        UIUtils.showToast(getString(text));
    }

    public static void log(Class cls,String text){
        Log.i(cls.getName(),text);
    }
    public static void log(String text){
        Log.i("test",text);
    }
    public static void log(String TAG,String text){
        Log.i(TAG,text);
    }

    public static View getRootView(Activity context)
    {
        return ((ViewGroup)context.findViewById(android.R.id.content)).getChildAt(0);
    }

    /**
     * 隐藏键盘
     */
    public static void hindKeybord(EditText editText){
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText,InputMethodManager.SHOW_FORCED);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0); //强制隐藏键盘
//
//        ((InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
//                .hideSoftInputFromWindow(.getCurrentFocus().getWindowToken(),
//                InputMethodManager.HIDE_NOT_ALWAYS);
    }


}
