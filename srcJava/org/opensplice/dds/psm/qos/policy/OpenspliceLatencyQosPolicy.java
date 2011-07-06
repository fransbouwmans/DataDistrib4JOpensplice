package org.opensplice.dds.psm.qos.policy;

import java.util.concurrent.TimeUnit;

import org.omg.dds.core.Bootstrap;
import org.omg.dds.core.Duration;
import org.omg.dds.core.modifiable.ModifiableDuration;
import org.omg.dds.core.policy.LatencyBudgetQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableLatencyBudgetQosPolicy;
import org.opensplice.dds.psm.OpenspliceDuration;

public class OpenspliceLatencyQosPolicy implements
        ModifiableLatencyBudgetQosPolicy {

    private DDS.LatencyBudgetQosPolicy policy;

    public OpenspliceLatencyQosPolicy(DDS.LatencyBudgetQosPolicy thepolicy) {
        policy = thepolicy;
    }

    public DDS.LatencyBudgetQosPolicy getPolicy() {
        return policy;
    }

    @Override
    public org.omg.dds.core.policy.QosPolicy.Id getId() {
        return null;
    }

    @Override
    public ModifiableLatencyBudgetQosPolicy modify() {
        return this;
    }

    @Override
    public Bootstrap getBootstrap() {
        return null;
    }

    @Override
    public ModifiableLatencyBudgetQosPolicy copyFrom(
            LatencyBudgetQosPolicy other) {
        policy = ((OpenspliceLatencyQosPolicy) other).getPolicy();
        return this;
    }

    @Override
    public LatencyBudgetQosPolicy finishModification() {
        return this;
    }

    @Override
    public ModifiableLatencyBudgetQosPolicy setDuration(Duration duration) {
        policy.duration = ((OpenspliceDuration) duration).getDuration();
        return this;
    }

    @Override
    public ModifiableLatencyBudgetQosPolicy setDuration(long duration,
            TimeUnit unit) {
        OpenspliceDuration theduration = new OpenspliceDuration(duration,
                unit);
        policy.duration = theduration.getDuration();
        return this;
    }

    @Override
    public ModifiableDuration getDuration() {
        return new OpenspliceDuration(policy.duration);
    }

    public OpenspliceLatencyQosPolicy clone() {
        return new OpenspliceLatencyQosPolicy(policy);
    }
}
