package com.utsoft.jan.wtalker.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.utsoft.jan.common.app.ToolbarActivity;
import com.utsoft.jan.wtalker.R;
import com.utsoft.jan.wtalker.frags.search.SearchUserFragment;

/**
 * Created by Administrator on 2019/7/8.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker.activities
 */
public class SearchActivity extends ToolbarActivity {

    public static final String KEY_EXTRA = "KEY_EXTRA";
    public static final int TYPE_USER = 1;

    //具体需要显示的类型
    private int type;

    private SearchUserFragment mSearchFragment;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_search;
    }

    public static void show(Context context,int type){
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(KEY_EXTRA,type);
        context.startActivity(intent);
    }

    @Override
    protected boolean initArgs(Bundle extras) {
        type = extras.getInt(KEY_EXTRA);
        return type == TYPE_USER;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        SearchUserFragment searchUserFragment = new SearchUserFragment();
        mSearchFragment = searchUserFragment;

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.lay_container,searchUserFragment);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search,menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        if (searchView!=null)
        {
            //拿到搜素service
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//            searchView.setSearchableInfo

            //设置是否显示搜索框展开时的提交按钮
            searchView.setSubmitButtonEnabled(true);
            //设置输入框提示语
            searchView.setQueryHint("hint");

            //添加搜素监听
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    search(s);
                    searchView.clearFocus();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    if (TextUtils.isEmpty(s)){
                        search("");
                        return true;
                    }
                    return false;
                }
            });

        }
        return super.onCreateOptionsMenu(menu);
    }

    private void search(String text) {
        if (mSearchFragment!=null)
        {
            mSearchFragment.search(text);
        }
    }
}
