package com.bip.funnycamera.lib;

import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

public abstract class HoTimer extends Timer {

    public Handler handle = new Handler() {
        public void handleMessage(Message msg) {
            HoTimer.this.Tick();
        }
    };


    private TimerTask f8tt = new TimerTask() {
        public void run() {
            HoTimer.this.handle.sendMessage(Message.obtain());
        }
    };


    public abstract void Tick();

    public HoTimer(int ms) {
        schedule(this.f8tt, (long) ms, (long) ms);
    }
}
