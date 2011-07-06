package org.opensplice.dds.psm.qos;

import org.omg.dds.core.Bootstrap;
import org.omg.dds.core.policy.EntityFactoryQosPolicy;
import org.omg.dds.core.policy.GroupDataQosPolicy;
import org.omg.dds.core.policy.PartitionQosPolicy;
import org.omg.dds.core.policy.PresentationQosPolicy;
import org.omg.dds.core.policy.QosPolicy;
import org.omg.dds.core.policy.QosPolicy.Id;
import org.omg.dds.core.policy.modifiable.ModifiableEntityFactoryQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableGroupDataQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiablePartitionQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiablePresentationQosPolicy;
import org.omg.dds.sub.SubscriberQos;
import org.omg.dds.sub.modifiable.ModifiableSubscriberQos;
import org.opensplice.dds.psm.qos.policy.OpensplicePartitionQosPolicy;
import org.opensplice.dds.psm.qos.policy.OpensplicePresentationQosPolicy;

public class OpenspliceSubscriberQos
        implements ModifiableSubscriberQos {

    @Override
    public ModifiableGroupDataQosPolicy getGroupData() {
        // TODO Auto-generated method stub
        return null;
    }

    private DDS.SubscriberQos qos;

    public DDS.SubscriberQos getQos() {
        return qos;
    }

    @Override
    public ModifiableSubscriberQos copyFrom(SubscriberQos other) {
        qos = ((OpenspliceSubscriberQos) other).getQos();
        return this;
    }

    @Override
    public ModifiableEntityFactoryQosPolicy getEntityFactory() {
        return null;
    }

    @Override
    public SubscriberQos finishModification() {
        return this;
    }

    @Override
    public ModifiableSubscriberQos setPresentation(
            PresentationQosPolicy presentation) {
        qos.presentation = ((OpensplicePresentationQosPolicy) presentation)
                .getPolicy();
        return this;
    }

    @Override
    public ModifiablePartitionQosPolicy getPartition() {
        return new OpensplicePartitionQosPolicy(qos.partition.name);
    }

    @Override
    public ModifiableSubscriberQos setPartition(PartitionQosPolicy partition) {
        qos.partition.name = ((OpensplicePartitionQosPolicy) partition)
                .getPartition();
        return this;
    }

    @Override
    public ModifiableSubscriberQos setGroupData(GroupDataQosPolicy groupData) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifiableSubscriberQos setEntityFactory(
            EntityFactoryQosPolicy entityFactory) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifiablePresentationQosPolicy getPresentation() {
        return new OpensplicePresentationQosPolicy(qos.presentation);
    }

    public OpenspliceSubscriberQos clone() {
        return null;
    }

    @Override
    public <POLICY extends QosPolicy<POLICY, ?>> POLICY get(Id id) {
        return null;
    }

    @Override
    public QosPolicy<?, ?> remove(Object key) {
        return null;
    }

    @Override
    public void clear() {
    }

    @Override
    public ModifiableSubscriberQos modify() {
        return this;
    }

    @Override
    public Bootstrap getBootstrap() {
        return null;
    }

    @Override
    public <POLICY extends QosPolicy<POLICY, ?>> POLICY put(Id key, POLICY value) {
        return null;
    }
}
