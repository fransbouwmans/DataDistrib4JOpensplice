package org.opensplice.dds.psm.qos.policy;

import org.omg.dds.core.Bootstrap;
import org.omg.dds.core.policy.ResourceLimitsQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableResourceLimitsQosPolicy;

public class OpenspliceResourceLimitsQosPolicy implements
        ModifiableResourceLimitsQosPolicy {

    /** The default serialVersionUID. */
    private static final long serialVersionUID = 1L;
    private DDS.ResourceLimitsQosPolicy policy;

    public OpenspliceResourceLimitsQosPolicy(
            DDS.ResourceLimitsQosPolicy thepolicy) {
        policy = thepolicy;
    }

    public DDS.ResourceLimitsQosPolicy getPolicy() {
        return policy;
    }

    @Override
    public int getMaxSamples() {
        return policy.max_samples;
    }

    @Override
    public int getMaxInstances() {
        return policy.max_instances;
    }

    @Override
    public int getMaxSamplesPerInstance() {
        return policy.max_samples_per_instance;
    }

    @Override
    public org.omg.dds.core.policy.QosPolicy.Id getId() {
        return null;
    }

    @Override
    public ModifiableResourceLimitsQosPolicy modify() {
        return this;
    }

    @Override
    public Bootstrap getBootstrap() {
        return null;
    }

    @Override
    public ModifiableResourceLimitsQosPolicy copyFrom(
            ResourceLimitsQosPolicy other) {
        policy = ((OpenspliceResourceLimitsQosPolicy) other).policy;
        return this;
    }

    @Override
    public ResourceLimitsQosPolicy finishModification() {
        return this;
    }

    @Override
    public ModifiableResourceLimitsQosPolicy setMaxSamples(int maxSamples) {
        policy.max_samples = maxSamples;
        return this;
    }

    @Override
    public ModifiableResourceLimitsQosPolicy setMaxInstances(int maxInstances) {
        policy.max_instances = maxInstances;
        return this;
    }

    @Override
    public ModifiableResourceLimitsQosPolicy setMaxSamplesPerInstance(
            int maxSamplesPerInstance) {
        policy.max_samples_per_instance = maxSamplesPerInstance;
        return this;
    }

    public OpenspliceResourceLimitsQosPolicy clone() {
        return new OpenspliceResourceLimitsQosPolicy(policy);
    }
}
