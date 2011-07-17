package org.opensplice.dds.psm.qos.policy;

import org.omg.dds.core.Bootstrap;
import org.omg.dds.core.policy.TransportPriorityQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableTransportPriorityQosPolicy;

public class OpenspliceTransportPriorityQosPolicy implements
        ModifiableTransportPriorityQosPolicy {

    private DDS.TransportPriorityQosPolicy policy;

    public OpenspliceTransportPriorityQosPolicy(
            DDS.TransportPriorityQosPolicy thepolicy) {
        policy = thepolicy;
    }

    public DDS.TransportPriorityQosPolicy getPpolicy() {
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
    public ModifiableTransportPriorityQosPolicy modify() {
        return this;
    }

    @Override
    public Bootstrap getBootstrap() {
        return null;
    }

    @Override
    public ModifiableTransportPriorityQosPolicy copyFrom(
            TransportPriorityQosPolicy other) {
        policy.value = other.getValue();
        return this;
    }

    @Override
    public TransportPriorityQosPolicy finishModification() {
        return this;
    }

    @Override
    public ModifiableTransportPriorityQosPolicy setValue(int value) {
        policy.value = value;
        return this;
    }

    public OpenspliceTransportPriorityQosPolicy clone() {
        return new OpenspliceTransportPriorityQosPolicy(policy);
    }
}
