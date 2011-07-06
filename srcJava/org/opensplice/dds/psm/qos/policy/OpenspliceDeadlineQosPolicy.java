package org.opensplice.dds.psm.qos.policy;

import java.util.concurrent.TimeUnit;

import org.omg.dds.core.Bootstrap;
import org.omg.dds.core.Duration;
import org.omg.dds.core.modifiable.ModifiableDuration;
import org.omg.dds.core.policy.DeadlineQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableDeadlineQosPolicy;
import org.opensplice.dds.psm.OpenspliceDuration;

public class OpenspliceDeadlineQosPolicy implements ModifiableDeadlineQosPolicy {

    private DDS.DeadlineQosPolicy policy;

    public OpenspliceDeadlineQosPolicy(DDS.DeadlineQosPolicy thepolicy) {
        policy = thepolicy;
    }

    public DDS.DeadlineQosPolicy getPolicy() {
        return policy;
    }

    @Override
    public org.omg.dds.core.policy.QosPolicy.Id getId() {
        return null;
    }

    @Override
    public ModifiableDeadlineQosPolicy modify() {
        return this;
    }

    @Override
    public Bootstrap getBootstrap() {
        return null;
    }

    @Override
    public ModifiableDeadlineQosPolicy copyFrom(DeadlineQosPolicy other) {
        policy = ((OpenspliceDeadlineQosPolicy) other).getPolicy();
        return this;
    }

    @Override
    public DeadlineQosPolicy finishModification() {
        return this;
    }

    @Override
    public ModifiableDeadlineQosPolicy setPeriod(Duration period) {
        policy.period = ((OpenspliceDuration) period).getDuration();
        return this;
    }

    @Override
    public ModifiableDeadlineQosPolicy setPeriod(long period, TimeUnit unit) {
        OpenspliceDuration duration = new OpenspliceDuration(period, unit);
        policy.period = duration.getDuration();
        return this;
    }

    @Override
    public ModifiableDuration getPeriod() {
        return new OpenspliceDuration(policy.period);
    }

    public OpenspliceDeadlineQosPolicy clone() {
        return new OpenspliceDeadlineQosPolicy(policy);
    }

}
