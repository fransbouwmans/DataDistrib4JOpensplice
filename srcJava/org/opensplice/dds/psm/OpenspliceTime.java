package org.opensplice.dds.psm;

import java.util.concurrent.TimeUnit;

import org.omg.dds.core.Bootstrap;
import org.omg.dds.core.Duration;
import org.omg.dds.core.Time;
import org.omg.dds.core.modifiable.ModifiableTime;

import DDS.Time_t;

public class OpenspliceTime extends ModifiableTime {

    private static long NANOSECONDSPERSECOND = 1000000000L;
    private Time_t ddsTime;
    private long ddsNs;

    private long getNanosec() {
        if (ddsTime != null) {
            ddsNs = ddsTime.nanosec + ddsTime.sec
                    * NANOSECONDSPERSECOND;
            ddsTime = null;
        }
        return ddsNs;
    }

    public OpenspliceTime(Time_t theTime) {
        ddsTime = theTime;
    }

    public Time_t getTime() {
        if (ddsTime == null) {
            ddsTime = new Time_t();
            ddsTime.sec = (int) (ddsNs / NANOSECONDSPERSECOND);
            ddsTime.nanosec = (int) (ddsNs % NANOSECONDSPERSECOND);
        }
        return ddsTime;
    }

    @Override
    public ModifiableTime copyFrom(Time other) {
        return new OpenspliceTime(((OpenspliceTime) other).getTime());
    }

    @Override
    public Time finishModification() {
        return this;
    }

    @Override
    public ModifiableTime modify() {
        return this;
    }

    @Override
    public Bootstrap getBootstrap() {
        return null;
    }

    @Override
    public ModifiableTime setTime(long time, TimeUnit unit) {
        ddsTime = null;
        ddsNs = TimeUnit.NANOSECONDS.convert(time, unit);
        return this;
    }

    @Override
    public ModifiableTime add(Duration duration) {
        ddsNs = getNanosec()
                + ((OpenspliceDuration) duration).getNanosec();
        return this;
    }

    @Override
    public ModifiableTime add(long duration, TimeUnit unit) {
        ddsNs = getNanosec()
                + TimeUnit.NANOSECONDS.convert(duration, unit);
        return this;
    }

    @Override
    public ModifiableTime subtract(Duration duration) {
        ddsNs = getNanosec()
                - ((OpenspliceDuration) duration).getNanosec();
        return this;
    }

    @Override
    public ModifiableTime subtract(long duration, TimeUnit unit) {
        ddsNs = getNanosec()
                - TimeUnit.NANOSECONDS.convert(duration, unit);
        return this;
    }

    @Override
    public ModifiableTime clone() {
        return new OpenspliceTime(getTime());
    }

    @Override
    public long getTime(TimeUnit inThisUnit) {
        return inThisUnit.convert(getNanosec(), TimeUnit.NANOSECONDS);
    }

    @Override
    public long getRemainder(TimeUnit primaryUnit, TimeUnit remainderUnit) {
        return 0;
    }

    @Override
    public boolean isValid() {
        return true;
    }

}
