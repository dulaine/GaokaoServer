package com.baizhou.util.idgenerator;

import java.util.HashSet;
import java.util.Set;

public class BasicEntityGenerator {
    private static final long START_STMP = 1484754361114L;
    private static final long SEQUENCE_BIT = 10L;
    private static final long MACHINE_BIT = 9L;
    private static final long DATACENTER_BIT = 3L;
    private static final long MAX_DATACENTER_NUM = 7L;
    private static final long MAX_MACHINE_NUM = 511L;
    private static final long MAX_SEQUENCE = 1023L;
    private static final long MACHINE_LEFT = 10L;
    private static final long DATACENTER_LEFT = 19L;
    private static final long TIMESTMP_LEFT = 22L;
    private long datacenterId;
    private long machineId;
    private long sequence = 0L;
    private long lastStmp = -1L;

    public BasicEntityGenerator(long datacenterId, long machineId) {
        if (datacenterId <= 7L && datacenterId >= 0L) {
            if (machineId <= 511L && machineId >= 0L) {
                this.datacenterId = datacenterId;
                this.machineId = machineId;
            } else {
                throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
            }
        } else {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
    }

    public synchronized long nextId() {
        long currStmp = this.getNewstmp();
        if (currStmp < this.lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        } else {
            if (currStmp == this.lastStmp) {
                this.sequence = this.sequence + 1L & 1023L;
                if (this.sequence == 0L) {
                    currStmp = this.getNextMill();
                }
            } else {
                this.sequence = 0L;
            }

            this.lastStmp = currStmp;
            return currStmp - 1484754361114L << 22 | this.datacenterId << 19 | this.machineId << 10 | this.sequence;
        }
    }

    private long getNextMill() {
        long mill;
        for(mill = this.getNewstmp(); mill <= this.lastStmp; mill = this.getNewstmp()) {
            ;
        }

        return mill;
    }

    private long getNewstmp() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) throws InterruptedException {
        Set<Long> tempSet = new HashSet();
        new SnowFlake(1L, 2L);
        BasicEntityGenerator basicEntityGenerator = new BasicEntityGenerator(1L, 3L);
        long start = System.currentTimeMillis();

        for(int i = 0; i <= 1000; ++i) {
            Long temp = basicEntityGenerator.nextId();
            if (tempSet.contains(temp)) {
                System.out.println("repeat :" + temp);
            }

            tempSet.add(temp);
            System.out.println(i + ": " + temp + "  :" + Long.toBinaryString(temp));
        }

        long end = System.currentTimeMillis();
        System.out.println("run time :" + (end - start));
    }
}
