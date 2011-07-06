package org.opensplice.dds.psm.qos.policy;

import org.omg.dds.core.Bootstrap;
import org.omg.dds.core.policy.DurabilityQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableDurabilityQosPolicy;

public class OpenspliceDurabilityQosPolicy implements
        ModifiableDurabilityQosPolicy {
    private DDS.DurabilityQosPolicy policy;

    public OpenspliceDurabilityQosPolicy(DDS.DurabilityQosPolicy thepolicy) {
        policy = thepolicy;
    }

    public DDS.DurabilityQosPolicy getPolicy() {
        return policy;
    }

    @Override
    public Kind getKind() {
        switch (policy.kind.value()) {
        case DDS.DurabilityQosPolicyKind._PERSISTENT_DURABILITY_QOS:
            return Kind.PERSISTENT;
        case DDS.DurabilityQosPolicyKind._TRANSIENT_DURABILITY_QOS:
            return Kind.TRANSIENT;
        case DDS.DurabilityQosPolicyKind._TRANSIENT_LOCAL_DURABILITY_QOS:
            return Kind.TRANSIENT_LOCAL;
        case DDS.DurabilityQosPolicyKind._VOLATILE_DURABILITY_QOS:
            return Kind.VOLATILE;
        }
        return null;
    }

    @Override
    public org.omg.dds.core.policy.QosPolicy.Id getId() {
        return null;
    }

    @Override
    public ModifiableDurabilityQosPolicy modify() {
        return this;
    }

    @Override
    public Bootstrap getBootstrap() {
        return null;
    }

    @Override
    public ModifiableDurabilityQosPolicy copyFrom(DurabilityQosPolicy other) {
        policy = ((OpenspliceDurabilityQosPolicy) other).getPolicy();
        return this;
    }

    @Override
    public DurabilityQosPolicy finishModification() {
        return this;
    }

    @Override
    public ModifiableDurabilityQosPolicy setKind(Kind kind) {
        if (kind.equals(Kind.PERSISTENT)) {
            policy.kind = DDS.DurabilityQosPolicyKind.PERSISTENT_DURABILITY_QOS;
        } else if (kind.equals(Kind.TRANSIENT)) {
            policy.kind = DDS.DurabilityQosPolicyKind.TRANSIENT_DURABILITY_QOS;
        } else if (kind.equals(Kind.TRANSIENT_LOCAL)) {
            policy.kind = DDS.DurabilityQosPolicyKind.TRANSIENT_LOCAL_DURABILITY_QOS;
        } else {
            policy.kind = DDS.DurabilityQosPolicyKind.VOLATILE_DURABILITY_QOS;
        }
        return this;
    }

    public OpenspliceDurabilityQosPolicy clone() {
        return new OpenspliceDurabilityQosPolicy(policy);
    }

}
