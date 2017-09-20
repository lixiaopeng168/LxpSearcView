package com.lxp.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class MainActivity extends AppCompatActivity {

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnSearchContent(this, new SearchView.OnSearchContent() {
            @Override
            public void onContent(String text) {
                UIUtils.showToast(text+"   内容");
            }
        });


        ((CheckBox) findViewById(R.id.checkboxLeft)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    searchView.setFlag(2,R.dimen.dimen_30,R.dimen.dimen_30);
                }else {
                    searchView.setFlag(1,R.dimen.radius_10,R.dimen.dimen_30);
                }
            }
        });
    }
}
