package com.lxp.search;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * 作者：lixiaopeng on 2017/9/20 0020.
 * 邮箱：lixiaopeng186@163.com
 * 描述：公用的搜索框
 */

public class SearchView extends RelativeLayout {
    private EditText searchEdit;
    private ImageView cancel;//清空内容
    private ImageView left;
    private boolean isCancel;//显示删除按钮
    private boolean isInit;//显示初始化的状态
    private int flag;//1是左侧，2是右侧
    private int dimenLeft; //左侧边距
    private int dimenRight;//右侧边距
    private int dimenDefault;//默认边距

    private OnSearchContent onSearchContent;

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        dimenDefault = UIUtils.getDiment(R.dimen.radius_10) + 5;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SearchView);
        String hintText = array.getString(R.styleable.SearchView_searchHintText);
        flag = array.getInt(R.styleable.SearchView_searchFlag, 1);
        dimenLeft = (int) array.getDimension(R.styleable.SearchView_searchPaddingLeft, dimenDefault);
        dimenRight = (int) array.getDimension(R.styleable.SearchView_searchPaddingRight, dimenDefault * 2 + 10);

        View view = LayoutInflater.from(context).inflate(R.layout.current_search, null);
        searchEdit = (EditText) view.findViewById(R.id.searchEdit);
        cancel = (ImageView) view.findViewById(R.id.searchIconRight);
        left = (ImageView) view.findViewById(R.id.searchIconLeft);
        searchEdit.addTextChangedListener(textWatcher);
        searchEdit.setOnEditorActionListener(onEditorActionListener);
        cancel.setOnClickListener(onClickListener);
        view.findViewById(R.id.searchRelative).setOnClickListener(onClickListener);
        searchEdit.setOnTouchListener(onTouchListener);

        isCancel = false;
        isInit = false;
        showCancel();

        if (!TextUtils.isEmpty(hintText)) {
            searchEdit.setHint(hintText);
        }
        searchEdit.setPadding(dimenLeft, 0, dimenRight, 0);
        if (flag == 1) {
            left.setVisibility(View.GONE);
            cancel.setVisibility(View.VISIBLE);

        } else if (flag == 2) {
            left.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.GONE);
        }

        this.addView(view);
    }

    private OnTouchListener onTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
//            log("点击获取焦点");
            editFoceble(true);
            return false;
        }
    };
    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.searchIconRight:
                        if (isCancel) {
                            searchEdit.setText("");
                        }
                        break;

                }

        }
    };
    private TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {//修改回车键功能
                // 隐藏键盘
                UIUtils.hindKeybord(searchEdit);
                hindEdit();
//                log("textview--" + v.getText().toString().trim());
                if (onSearchContent != null) {
                    onSearchContent.onContent(searchEdit.getText().toString().trim());
                }

                return true;
            }
            return false;
        }


    };

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //输入文本之前的状态
            //如果flag为1，将
            if (flag == 2) {
                //将左侧图标隐藏，并且设置边距为10
//                left.setVisibility(View.GONE);
//                cancel.setVisibility(View.GONE);
                searchEdit.setPadding(dimenDefault, 0, dimenRight, 0);
            } else if (flag == 1) {

                searchEdit.setPadding(dimenDefault, 0, dimenRight, 0);
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //输入文字中的状态
        }

        @Override
        public void afterTextChanged(Editable s) {
            //输入文字后的状态
            //将图片变为删除
//            UIUtils.log("输入后?"+isCancel);
            if (TextUtils.isEmpty(s.toString())) {
                isCancel = false;
                isInit = false;
            } else {
                isCancel = true;
                isInit = true;
            }

            showCancel();

        }
    };
    //显示逻辑，自己可以自定义
    private void showCancel() {
        if (flag == 1) {
            Log.e("hhh", "isCancle:" + isCancel + "------" + isInit);
            if (isCancel) {
                cancel.setImageResource(R.mipmap.current_cancel02);
                cancel.setVisibility(View.VISIBLE);
            } else {
                if (!isInit) {
                    cancel.setImageResource(R.mipmap.current_search_gray);
                    cancel.setVisibility(View.VISIBLE);
                } else {
                    cancel.setVisibility(View.GONE);
                }
            }
            left.setVisibility(View.GONE);
            searchEdit.setPadding(dimenLeft, 0, dimenRight, 0);


        } else if (flag == 2) {
            if (isCancel) {
                cancel.setImageResource(R.mipmap.current_cancel02);
                cancel.setVisibility(View.VISIBLE);
                searchEdit.setPadding(dimenDefault, 0, dimenRight, 0);
            } else {
                left.setImageResource(R.mipmap.current_search_gray);
                searchEdit.setPadding(dimenLeft, 0, dimenRight, 0);
            }
            if (!isInit) {

                cancel.setVisibility(View.GONE);
                left.setVisibility(View.VISIBLE);

            } else {
                left.setVisibility(View.GONE);

            }
        }
    }



    //当键盘消失或者搜索后还原为原来状态
    private void hindEdit() {
        isCancel = false;
        isInit = false;
        searchEdit.setText("");
        editFoceble(false);
        showCancel();
    }
    //设置editext焦点问题
    private void editFoceble(boolean facuble) {
        if (facuble) {
            searchEdit.setFocusable(true);
            searchEdit.setFocusableInTouchMode(true);
            searchEdit.requestFocus();
            searchEdit.requestFocusFromTouch();
        } else {
            searchEdit.setFocusable(false);
        }
    }

    public interface OnSearchContent {
        void onContent(String text);
    }


    public void setFlag(int flag,int dimenLeft,int dimenRight){
        this.flag = flag;
        this.dimenLeft = UIUtils.getDiment(dimenLeft);
        this.dimenRight = UIUtils.getDiment(dimenRight);
    }

    /**
     * 对外调用方法
     * @param activity
     * @param onSearchContent
     */
    public void setOnSearchContent(final Activity activity, OnSearchContent onSearchContent) {
        this.onSearchContent = onSearchContent;
        monitor(activity);

    }


    /**
     * 监听键盘隐藏显示方法
     * @param activity
     */
    private void monitor(Activity activity) {
        //监听键盘隐藏显示
        final View decorView = activity.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //1、获取main在窗体的可视区域
                decorView.getWindowVisibleDisplayFrame(rect);
                //2、获取main在窗体的不可视区域高度，在键盘没有弹起时，main.getRootView().getHeight()调节度应该和rect.bottom高度一样
                int mainInvisibleHeight = decorView.getRootView().getHeight() - rect.bottom;
                int screenHeight = decorView.getRootView().getHeight();//屏幕高度
                //3、不可见区域大于屏幕本身高度的1/4：说明键盘弹起了
                if (mainInvisibleHeight > screenHeight / 4) {
                    editFoceble(true);
//                    Log.e("hhh", "show------------" + rect.bottom + "----" + decorView.getRootView().getHeight());
                } else {
//                    Log.e("hhh", "hind------------" + rect.bottom + "----" + decorView.getRootView().getHeight());
                    hindEdit();
                }
            }
        });
    }
}














