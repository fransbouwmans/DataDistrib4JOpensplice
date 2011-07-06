package org.opensplice.dds.psm;

import java.util.concurrent.TimeUnit;

import org.omg.dds.core.Bootstrap;
import org.omg.dds.core.Duration;
import org.omg.dds.core.modifiable.ModifiableDuration;

import DDS.Duration_t;

public class OpenspliceDuration extends ModifiableDuration {

    private static long NANOSECONDSPERSECOND = 1000000000L;
    private long durationNs;
    private DDS.Duration_t durationDDS = null;

    public long getNanosec() {
        if (durationDDS != null) {
            durationNs = durationDDS.nanosec + durationDDS.sec
                    * NANOSECONDSPERSECOND;
            durationDDS = null;
        }
        return durationNs;
    }

    public OpenspliceDuration(Duration theduration) {
        durationNs = theduration.getDuration(TimeUnit.NANOSECONDS);
    }

    public OpenspliceDuration(DDS.Duration_t theduration) {
        durationDDS = theduration;
    }

    public OpenspliceDuration(long maxBlockingTime, TimeUnit unit) {
        durationNs = TimeUnit.NANOSECONDS.convert(durationNs, unit);
    }

    public DDS.Duration_t getDuration() {
        if (durationDDS == null) {
            durationDDS = new Duration_t();
            durationDDS.sec = (int) (durationNs / NANOSECONDSPERSECOND);
            durationDDS.nanosec = (int) (durationNs % NANOSECONDSPERSECOND);
        }
        return durationDDS;
    }

    @Override
    public ModifiableDuration copyFrom(Duration other) {
        durationDDS = ((OpenspliceDuration) other).getDuration();
        return this;
    }

    @Override
    public Duration finishModification() {
        return this;
    }

    @Override
    public ModifiableDuration modify() {
        return this;
    }

    @Override
    public Bootstrap getBootstrap() {
        return null;
    }

    @Override
    public ModifiableDuration setDuration(long duration, TimeUnit unit) {
        durationDDS = null;
        durationNs = TimeUnit.NANOSECONDS.convert(duration, unit);
        return this;
    }

    @Override
    public ModifiableDuration add(Duration duration) {
        durationNs = getNanosec()
                + ((OpenspliceDuration) duration).getNanosec();
        return this;
    }

    @Override
    public ModifiableDuration add(long duration, TimeUnit unit) {
        durationNs = getNanosec()
                + TimeUnit.NANOSECONDS.convert(duration, unit);
        return this;
    }

    @Override
    public ModifiableDuration subtract(Duration duration) {
        durationNs = getNanosec()
                - ((OpenspliceDuration) duration).getNanosec();
        return this;
    }

    @Override
    public ModifiableDuration subtract(long duration, TimeUnit unit) {
        durationNs = getNanosec()
                - TimeUnit.NANOSECONDS.convert(duration, unit);
        return this;
    }

    @Override
    public ModifiableDuration clone() {
        return new OpenspliceDuration(getDuration());
    }

    @Override
    public long getDuration(TimeUnit inThisUnit) {
        return inThisUnit.convert(getNanosec(), TimeUnit.NANOSECONDS);
    }

    @Override
    public long getRemainder(TimeUnit primaryUnit, TimeUnit remainderUnit) {
        return 0;
    }

    @Override
    public boolean isZero() {
        if (durationDDS != null) {
            return ((durationDDS.sec == 0) && (durationDDS.nanosec == 0));
        }
        return durationNs == 0;
    }

    @Override
    public boolean isInfinite() {
        if (durationDDS != null) {
            return DDS.DURATION_INFINITE.value.equals(durationDDS);
        }
        return false;
    }
}
