package org.opensplice.dds.psm.qos.policy;

import java.util.concurrent.TimeUnit;

import org.omg.dds.core.Bootstrap;
import org.omg.dds.core.Duration;
import org.omg.dds.core.modifiable.ModifiableDuration;
import org.omg.dds.core.policy.ReliabilityQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableReliabilityQosPolicy;
import org.opensplice.dds.psm.OpenspliceDuration;

public class OpenspliceReliabilityQosPolicy implements
        ModifiableReliabilityQosPolicy {

    private DDS.ReliabilityQosPolicy policy;

    public OpenspliceReliabilityQosPolicy(DDS.ReliabilityQosPolicy thepolicy) {
        policy = thepolicy;
    }

    public DDS.ReliabilityQosPolicy getPolicy() {
        return policy;
    }

    @Override
    public org.omg.dds.core.policy.QosPolicy.Id getId() {
        return null;
    }

    @Override
    public ModifiableReliabilityQosPolicy modify() {
        return this;
    }

    @Override
    public Bootstrap getBootstrap() {
        return null;
    }

    @Override
    public Kind getKind() {
        switch (policy.kind.value()) {
        case DDS.ReliabilityQosPolicyKind._BEST_EFFORT_RELIABILITY_QOS:
            return Kind.BEST_EFFORT;
        case DDS.ReliabilityQosPolicyKind._RELIABLE_RELIABILITY_QOS:
            return Kind.RELIABLE;
        }
        return null;
    }

    @Override
    public ModifiableDuration getMaxBlockingTime() {
        return new OpenspliceDuration(policy.max_blocking_time);
    }

    public OpenspliceReliabilityQosPolicy clone() {
        return new OpenspliceReliabilityQosPolicy(policy);
    }

    @Override
    public ModifiableReliabilityQosPolicy copyFrom(
            ReliabilityQosPolicy other) {
        policy = ((OpenspliceReliabilityQosPolicy) other).getPolicy();
        return this;
    }

    @Override
    public ReliabilityQosPolicy finishModification() {
        return this;
    }

    @Override
    public ModifiableReliabilityQosPolicy setKind(Kind kind) {
        if (Kind.BEST_EFFORT.equals(kind)) {
            policy.kind = DDS.ReliabilityQosPolicyKind.BEST_EFFORT_RELIABILITY_QOS;
        } else {
            policy.kind = DDS.ReliabilityQosPolicyKind.RELIABLE_RELIABILITY_QOS;
        }
        return this;
    }

    @Override
    public ModifiableReliabilityQosPolicy setMaxBlockingTime(
            Duration maxBlockingTime) {
        policy.max_blocking_time = ((OpenspliceDuration) maxBlockingTime)
                .getDuration();
        return this;
    }

    @Override
    public ModifiableReliabilityQosPolicy setMaxBlockingTime(
            long maxBlockingTime, TimeUnit unit) {
        OpenspliceDuration duration = new OpenspliceDuration(
                maxBlockingTime, unit);
        policy.max_blocking_time = duration.getDuration();
        return this;
    }
}
