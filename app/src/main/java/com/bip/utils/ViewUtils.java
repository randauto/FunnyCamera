package com.bip.utils;

import android.view.View;

import com.thekhaeng.pushdownanim.PushDownAnim;

public class ViewUtils {
    public static void clickViewsAnim(View.OnClickListener onClickListener, View... view) {
        PushDownAnim.setPushDownAnimTo(view)
                .setOnClickListener(onClickListener);
    }
}
