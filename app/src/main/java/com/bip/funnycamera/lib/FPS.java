package com.bip.funnycamera.lib;

public class FPS {
    long dtime = 0;
    double fps = 0.0d;
    long oldt = System.nanoTime();

    int f7tc = 0;

    public double Update() {
        this.f7tc++;
        long tmp = System.nanoTime();
        this.dtime = tmp - this.oldt;
        double d = ((double) this.dtime) * 1.0E-9d;
        if (d > 0.7d) {
            this.fps = (((double) this.f7tc) + (this.fps / 7.0d)) / (0.14285714285714285d + d);
            this.f7tc = 0;
            this.dtime = 0;
            this.oldt = tmp;
        }
        return this.fps;
    }
}
