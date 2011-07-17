package org.opensplice.dds.psm.qos.policy;

import org.omg.dds.core.Bootstrap;
import org.omg.dds.core.policy.OwnershipStrengthQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableOwnershipStrengthQosPolicy;

public class OpenspliceOwnershipStrengthQosPolicy implements
        ModifiableOwnershipStrengthQosPolicy {
    /** The default serialVersionUID */
    private static final long serialVersionUID = 1L;
    private DDS.OwnershipStrengthQosPolicy policy;

    public OpenspliceOwnershipStrengthQosPolicy(
            DDS.OwnershipStrengthQosPolicy thepolicy) {
        policy = thepolicy;
    }

    public DDS.OwnershipStrengthQosPolicy getPolicy() {
        return policy;
    }

    @Override
    public int getValue() {
        return policy.value;
    }

    @Override
    public org.omg.dds.core.policy.QosPolicy.Id getId() {
        return null;
    }

    @Override
    public ModifiableOwnershipStrengthQosPolicy modify() {
        return this;
    }

    @Override
    public Bootstrap getBootstrap() {
        return null;
    }

    @Override
    public ModifiableOwnershipStrengthQosPolicy copyFrom(
            OwnershipStrengthQosPolicy other) {
        policy.value = ((OpenspliceOwnershipStrengthQosPolicy) other).policy.value;
        return this;
    }

    @Override
    public OwnershipStrengthQosPolicy finishModification() {
        return this;
    }

    @Override
    public ModifiableOwnershipStrengthQosPolicy setValue(int value) {
        policy.value = value;
        return this;
    }

    public ModifiableOwnershipStrengthQosPolicy clone() {
        return new OpenspliceOwnershipStrengthQosPolicy(policy);
    }
}
