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
import org.omg.dds.pub.PublisherQos;
import org.omg.dds.pub.modifiable.ModifiablePublisherQos;
import org.opensplice.dds.psm.qos.policy.OpenspliceGroupDataPolicy;
import org.opensplice.dds.psm.qos.policy.OpensplicePartitionQosPolicy;
import org.opensplice.dds.psm.qos.policy.OpensplicePresentationQosPolicy;

public class OpensplicePublisherQos implements ModifiablePublisherQos {

    private DDS.PublisherQos qos;
    private Bootstrap bootStrap;

    public DDS.PublisherQos getQos() {
        return qos;
    }

    public OpensplicePublisherQos(DDS.PublisherQos theqos,
            Bootstrap thebootStrap) {
        bootStrap = thebootStrap;
        qos = theqos;

    }

    @Override
    public <POLICY extends QosPolicy<POLICY, ?>> POLICY get(Id id) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public QosPolicy<?, ?> remove(Object key) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public void clear() {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public ModifiablePublisherQos modify() {
        return this;
    }

    @Override
    public Bootstrap getBootstrap() {
        return bootStrap;
    }

    @Override
    public <POLICY extends QosPolicy<POLICY, ?>> POLICY put(Id key, POLICY value) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public ModifiablePublisherQos copyFrom(PublisherQos other) {
        bootStrap = (other.getBootstrap());
        qos = ((OpensplicePublisherQos) other).qos;
        return this;
    }

    @Override
    public PublisherQos finishModification() {
        return this;
    }

    @Override
    public ModifiablePublisherQos setPresentation(
            PresentationQosPolicy presentation) {
        qos.presentation = ((OpensplicePresentationQosPolicy) presentation)
                .getPolicy();
        return this;
    }

    @Override
    public ModifiablePresentationQosPolicy getPresentation() {
        return new OpensplicePresentationQosPolicy(qos.presentation);
    }

    @Override
    public ModifiablePublisherQos setPartition(PartitionQosPolicy partition) {
        qos.partition.name = ((OpensplicePartitionQosPolicy) partition)
                .getPartition();
        return this;
    }

    @Override
    public ModifiablePartitionQosPolicy getPartition() {
        return new OpensplicePartitionQosPolicy(qos.partition.name);
    }

    @Override
    public ModifiablePublisherQos setGroupData(GroupDataQosPolicy groupData) {
        qos.group_data = ((OpenspliceGroupDataPolicy) groupData).getPolicy();
        return this;
    }

    @Override
    public ModifiableGroupDataQosPolicy getGroupData() {
        return new OpenspliceGroupDataPolicy(qos.group_data);
    }

    @Override
    public ModifiablePublisherQos setEntityFactory(
            EntityFactoryQosPolicy entityFactory) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public ModifiableEntityFactoryQosPolicy getEntityFactory() {
        throw new RuntimeException("Not yet implemented");
    }

    public OpensplicePublisherQos clone() {
        return new OpensplicePublisherQos(qos, bootStrap);
    }
}
