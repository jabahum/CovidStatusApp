package com.example.covidstatusapp.ui._base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentManager;

import com.example.covidstatusapp.ui.utils.CustomLoader;
import com.example.covidstatusapp.ui.utils.NetworkUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity<T extends ViewDataBinding> extends DaggerAppCompatActivity {

    private static final String TAG = "BaseActivity";

    private T mViewDataBinding;

    private RxPermissions mPermission;


    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract int getBindingVariable();

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performDataBinding();
        mPermission = new RxPermissions(this);
    }

    public RxPermissions getMPermission() {
        return mPermission;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void showKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        }
    }

    public static CustomLoader progressBar = new CustomLoader("PLEASE WAIT");
    private static final String LOADER_TAG = "LOADER_TAG";
    public void showLoading(String message){
        progressBar.setMessage(message);

        FragmentManager ft = getSupportFragmentManager();
        if (ft.findFragmentByTag(LOADER_TAG) == null) {
            progressBar.show(ft, LOADER_TAG);
        }
    }

    public void hideLoading(){
        progressBar.dismiss();
    }

    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(this);
    }


    public <Z> Intent newIntent(Class<Z> clazz)
    {
        return new Intent(this, clazz);
    }

    public <Z> void newActivity(Class<Z> clazz) {
        startActivity(newIntent(clazz));
    }

    public void shareApp(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Lets Be up to date with current Covid-19 Status ");
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }
}
