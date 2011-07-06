package org.opensplice.dds.psm.qos.policy;

import org.omg.dds.core.Bootstrap;
import org.omg.dds.core.policy.TopicDataQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableTopicDataQosPolicy;

public class OpenspliceTopicDataQosPolicy implements
        ModifiableTopicDataQosPolicy {
    DDS.TopicDataQosPolicy policy;

    public OpenspliceTopicDataQosPolicy(DDS.TopicDataQosPolicy thepolicy) {
        policy = thepolicy;
    }

    public DDS.TopicDataQosPolicy getPolicy() {
        return policy;
    }

    @Override
    public int getValue(byte[] value, int offset) {
        return 0;
    }

    @Override
    public int getLength() {
        return 0;
    }

    @Override
    public org.omg.dds.core.policy.QosPolicy.Id getId() {
        return null;
    }

    @Override
    public ModifiableTopicDataQosPolicy modify() {
        return this;
    }

    @Override
    public Bootstrap getBootstrap() {
        return null;
    }

    @Override
    public ModifiableTopicDataQosPolicy copyFrom(TopicDataQosPolicy other) {
        return null;
    }

    @Override
    public TopicDataQosPolicy finishModification() {
        return this;
    }

    @Override
    public ModifiableTopicDataQosPolicy setValue(byte[] value, int offset,
            int length) {
        return this;
    }

    public OpenspliceTopicDataQosPolicy clone() {
        return new OpenspliceTopicDataQosPolicy(policy);
    }

}
