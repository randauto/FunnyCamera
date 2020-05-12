package com.bip.funnycamera.lib;

public class Random32 {
    private static int SeedCount;
    private int Seed = (((int) System.nanoTime()) + SeedCount);

    public Random32() {
        SeedCount++;
    }

    public int Next(int max) {
        this.Seed = ((this.Seed * 1103515245) + 12345) & Integer.MAX_VALUE;
        return ((this.Seed >> 16) * max) >> 15;
    }

    public float NextFloat() {
        this.Seed = ((this.Seed * 1103515245) + 12345) & Integer.MAX_VALUE;
        return ((float) this.Seed) * 4.656613E-10f;
    }
}
