package org.opensplice.dds.psm.qos.policy;

import org.omg.dds.core.Bootstrap;
import org.omg.dds.core.policy.DestinationOrderQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableDestinationOrderQosPolicy;

public class OpenspliceDestinationOrderQosPolicy implements
        ModifiableDestinationOrderQosPolicy {

    /** The default serialVersionUID. */
    private static final long serialVersionUID = 1L;
    DDS.DestinationOrderQosPolicy policy;

    public OpenspliceDestinationOrderQosPolicy(
            DDS.DestinationOrderQosPolicy thePolicy) {
        policy = thePolicy;
    }

    public DDS.DestinationOrderQosPolicy getPolicy() {
        return policy;
    }

    @Override
    public Kind getKind() {
        if (DDS.DestinationOrderQosPolicyKind.BY_RECEPTION_TIMESTAMP_DESTINATIONORDER_QOS
                .equals(policy.kind)) {
            return Kind.BY_RECEPTION_TIMESTAMP;
        }
        return Kind.BY_SOURCE_TIMESTAMP;
    }

    @Override
    public org.omg.dds.core.policy.QosPolicy.Id getId() {
        return null;
    }

    @Override
    public ModifiableDestinationOrderQosPolicy modify() {
        return this;
    }

    @Override
    public Bootstrap getBootstrap() {
        return null;
    }

    @Override
    public ModifiableDestinationOrderQosPolicy copyFrom(
            DestinationOrderQosPolicy other) {
        policy = ((OpenspliceDestinationOrderQosPolicy) other).policy;
        return this;
    }

    @Override
    public DestinationOrderQosPolicy finishModification() {
        return this;
    }

    @Override
    public ModifiableDestinationOrderQosPolicy setKind(Kind kind) {
        if (Kind.BY_RECEPTION_TIMESTAMP.equals(kind)) {
            policy.kind = DDS.DestinationOrderQosPolicyKind.BY_RECEPTION_TIMESTAMP_DESTINATIONORDER_QOS;
        } else {
            policy.kind = DDS.DestinationOrderQosPolicyKind.BY_SOURCE_TIMESTAMP_DESTINATIONORDER_QOS;
        }
        return this;
    }

    public OpenspliceDestinationOrderQosPolicy clone() {
        return new OpenspliceDestinationOrderQosPolicy(policy);
    }
}
