package org.opensplice.dds.psm.qos.policy;

import org.omg.dds.core.Bootstrap;
import org.omg.dds.core.policy.GroupDataQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableGroupDataQosPolicy;

public class OpenspliceGroupDataPolicy implements ModifiableGroupDataQosPolicy {

    private DDS.GroupDataQosPolicy policy;

    public OpenspliceGroupDataPolicy(DDS.GroupDataQosPolicy thePolicy) {
        policy = thePolicy;
    }

    public DDS.GroupDataQosPolicy getPolicy() {
        return policy;
    }

    @Override
    public int getValue(byte[] value, int offset) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getLength() {
        // TODO Auto-generated method stub
        return policy.value.length;
    }

    @Override
    public org.omg.dds.core.policy.QosPolicy.Id getId() {
        return null;
    }

    @Override
    public ModifiableGroupDataQosPolicy modify() {
        return this;
    }

    @Override
    public Bootstrap getBootstrap() {
        return null;
    }

    @Override
    public ModifiableGroupDataQosPolicy copyFrom(GroupDataQosPolicy other) {
        policy = ((OpenspliceGroupDataPolicy) other).policy;
        return this;
    }

    @Override
    public GroupDataQosPolicy finishModification() {
        return this;
    }

    @Override
    public ModifiableGroupDataQosPolicy setValue(byte[] value, int offset,
            int length) {
        if (offset == 0) {
            policy.value = value;
        } else {

        }
        return this;
    }

    public OpenspliceGroupDataPolicy clone() {
        return new OpenspliceGroupDataPolicy(policy);
    }
}
