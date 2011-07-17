package org.opensplice.dds.psm.qos.policy;

import java.util.concurrent.TimeUnit;

import org.omg.dds.core.Bootstrap;
import org.omg.dds.core.Duration;
import org.omg.dds.core.modifiable.ModifiableDuration;
import org.omg.dds.core.policy.LifespanQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableLifespanQosPolicy;
import org.opensplice.dds.psm.OpenspliceDuration;

public class OpenspliceLifespanQosPolicy implements ModifiableLifespanQosPolicy {

    /** The default serialVersionUID. */
    private static final long serialVersionUID = 1L;
    private DDS.LifespanQosPolicy policy;

    public OpenspliceLifespanQosPolicy(DDS.LifespanQosPolicy thepolicy) {
        policy = thepolicy;
    }

    public DDS.LifespanQosPolicy getPolicy() {
        return policy;
    }

    @Override
    public org.omg.dds.core.policy.QosPolicy.Id getId() {
        return null;
    }

    @Override
    public ModifiableLifespanQosPolicy modify() {
        return this;
    }

    @Override
    public Bootstrap getBootstrap() {
        return null;
    }

    @Override
    public ModifiableLifespanQosPolicy copyFrom(LifespanQosPolicy other) {
        policy = ((OpenspliceLifespanQosPolicy) other).getPolicy();
        return this;
    }

    @Override
    public LifespanQosPolicy finishModification() {
        return this;
    }

    @Override
    public ModifiableLifespanQosPolicy setDuration(Duration duration) {
        policy.duration = ((OpenspliceDuration) duration).getDuration();
        return this;
    }

    @Override
    public ModifiableLifespanQosPolicy setDuration(long duration, TimeUnit unit) {
        policy.duration = new OpenspliceDuration(duration, unit).getDuration();
        return this;
    }

    @Override
    public ModifiableDuration getDuration() {
        return new OpenspliceDuration(policy.duration);
    }

    public OpenspliceLifespanQosPolicy clone() {
        return new OpenspliceLifespanQosPolicy(policy);
    }
}
