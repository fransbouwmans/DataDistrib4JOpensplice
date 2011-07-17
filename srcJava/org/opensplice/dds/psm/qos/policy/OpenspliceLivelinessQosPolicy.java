package org.opensplice.dds.psm.qos.policy;

import java.util.concurrent.TimeUnit;

import org.omg.dds.core.Bootstrap;
import org.omg.dds.core.Duration;
import org.omg.dds.core.modifiable.ModifiableDuration;
import org.omg.dds.core.policy.LivelinessQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableLivelinessQosPolicy;
import org.opensplice.dds.psm.OpenspliceDuration;

public class OpenspliceLivelinessQosPolicy implements
        ModifiableLivelinessQosPolicy {

    /** The default serialVersionUID. */
    private static final long serialVersionUID = 1L;
    private DDS.LivelinessQosPolicy policy;

    public OpenspliceLivelinessQosPolicy(DDS.LivelinessQosPolicy thepolicy) {
        policy = thepolicy;
    }

    public DDS.LivelinessQosPolicy getPolicy() {
        return policy;
    }

    @Override
    public Kind getKind() {
        if (DDS.LivelinessQosPolicyKind.AUTOMATIC_LIVELINESS_QOS
                .equals(policy.kind)) {
            return Kind.AUTOMATIC;
        } else if (DDS.LivelinessQosPolicyKind.MANUAL_BY_PARTICIPANT_LIVELINESS_QOS
                .equals(policy.kind)) {
            return Kind.MANUAL_BY_PARTICIPANT;
        }
        return Kind.MANUAL_BY_TOPIC;
    }

    @Override
    public org.omg.dds.core.policy.QosPolicy.Id getId() {
        return null;
    }

    @Override
    public ModifiableLivelinessQosPolicy modify() {
        return this;
    }

    @Override
    public Bootstrap getBootstrap() {
        return null;
    }

    @Override
    public ModifiableLivelinessQosPolicy copyFrom(LivelinessQosPolicy other) {
        policy = ((OpenspliceLivelinessQosPolicy) other).getPolicy();
        return this;
    }

    @Override
    public LivelinessQosPolicy finishModification() {
        return this;
    }

    @Override
    public ModifiableLivelinessQosPolicy setKind(Kind kind) {
        if (Kind.AUTOMATIC.equals(kind)) {
            policy.kind = DDS.LivelinessQosPolicyKind.AUTOMATIC_LIVELINESS_QOS;
        } else if (Kind.MANUAL_BY_PARTICIPANT.equals(kind)) {
            policy.kind = DDS.LivelinessQosPolicyKind.MANUAL_BY_PARTICIPANT_LIVELINESS_QOS;
        } else {
            policy.kind = DDS.LivelinessQosPolicyKind.MANUAL_BY_TOPIC_LIVELINESS_QOS;
        }
        return this;
    }

    @Override
    public ModifiableLivelinessQosPolicy setLeaseDuration(Duration leaseDuration) {
        policy.lease_duration = ((OpenspliceDuration) leaseDuration)
                .getDuration();
        return this;
    }

    @Override
    public ModifiableLivelinessQosPolicy setLeaseDuration(long leaseDuration,
            TimeUnit unit) {
        policy.lease_duration = new OpenspliceDuration(leaseDuration, unit)
                .getDuration();
        return this;
    }

    @Override
    public ModifiableDuration getLeaseDuration() {
        return new OpenspliceDuration(policy.lease_duration);
    }

    public OpenspliceLivelinessQosPolicy clone() {
        return new OpenspliceLivelinessQosPolicy(policy);
    }
}
