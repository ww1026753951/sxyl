package com.sxyl.joker.toumi.robot.common.http;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by zhangziliang on 2017/5/9.
 */
public class MurmurHash {
    public MurmurHash() {
    }

    public static int hash(byte[] data, int seed) {
        return hash(ByteBuffer.wrap(data), seed);
    }

    public static int hash(byte[] data, int offset, int length, int seed) {
        return hash(ByteBuffer.wrap(data, offset, length), seed);
    }

    public static int hash(ByteBuffer buf, int seed) {
        ByteOrder byteOrder = buf.order();
        buf.order(ByteOrder.LITTLE_ENDIAN);
        int m = 1540483477;
        int r = 24;

        int h;
        int k;
        for(h = seed ^ buf.remaining(); buf.remaining() >= 4; h ^= k) {
            k = buf.getInt();
            k *= m;
            k ^= k >>> r;
            k *= m;
            h *= m;
        }

        if(buf.remaining() > 0) {
            ByteBuffer finish = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
            finish.put(buf).rewind();
            h ^= finish.getInt();
            h *= m;
        }

        h ^= h >>> 13;
        h *= m;
        h ^= h >>> 15;
        buf.order(byteOrder);
        return h;
    }

    public static long hash64A(byte[] data, int seed) {
        return hash64A(ByteBuffer.wrap(data), seed);
    }

    public static long hash64A(byte[] data, int offset, int length, int seed) {
        return hash64A(ByteBuffer.wrap(data, offset, length), seed);
    }

    public static long hash64A(ByteBuffer buf, int seed) {
        ByteOrder byteOrder = buf.order();
        buf.order(ByteOrder.LITTLE_ENDIAN);
        long m = -4132994306676758123L;
        int r = 47;

        long h;
        for(h = (long)seed ^ (long)buf.remaining() * m; buf.remaining() >= 8; h *= m) {
            long k = buf.getLong();
            k *= m;
            k ^= k >>> r;
            k *= m;
            h ^= k;
        }

        if(buf.remaining() > 0) {
            ByteBuffer finish = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
            finish.put(buf).rewind();
            h ^= finish.getLong();
            h *= m;
        }

        h ^= h >>> r;
        h *= m;
        h ^= h >>> r;
        buf.order(byteOrder);
        return h;
    }

    public static long hash(String key) {
        return hash64A(key.getBytes(), 305441741);
    }

    public static void main(String args[]){
        String routerId ="10005378063";

        int tableIndex = (int)(Math.abs(MurmurHash.hash(String.valueOf(routerId))) % 256L) + 1;


        tableIndex =2 ;

    }
}
