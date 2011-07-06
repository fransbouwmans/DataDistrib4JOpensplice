package org.opensplice.dds.psm.qos.policy;

import org.omg.dds.core.Bootstrap;
import org.omg.dds.core.policy.EntityFactoryQosPolicy;
import org.omg.dds.core.policy.QosPolicy;
import org.omg.dds.core.policy.QosPolicy.Id;
import org.omg.dds.core.policy.modifiable.ModifiableEntityFactoryQosPolicy;
import org.omg.dds.domain.DomainParticipantFactoryQos;
import org.omg.dds.domain.modifiable.ModifiableDomainParticipantFactoryQos;

public class OpenspliceDomainParticipantFactoryQos implements
        ModifiableDomainParticipantFactoryQos {
    private DDS.DomainParticipantFactoryQos qos;

    public OpenspliceDomainParticipantFactoryQos(
            DDS.DomainParticipantFactoryQos theqos) {
        qos = theqos;
    }

    public DDS.DomainParticipantFactoryQos getQos() {
        return qos;
    }

    @Override
    public <POLICY extends QosPolicy<POLICY, ?>> POLICY get(Id id) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public QosPolicy<?, ?> remove(Object key) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void clear() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public ModifiableDomainParticipantFactoryQos modify() {
        return this;
    }

    @Override
    public Bootstrap getBootstrap() {
        return null;
    }

    @Override
    public <POLICY extends QosPolicy<POLICY, ?>> POLICY put(Id key, POLICY value) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public ModifiableDomainParticipantFactoryQos copyFrom(
            DomainParticipantFactoryQos other) {
        qos = ((OpenspliceDomainParticipantFactoryQos) other).getQos();
        return this;
    }

    @Override
    public DomainParticipantFactoryQos finishModification() {
        return this;
    }

    @Override
    public ModifiableDomainParticipantFactoryQos setEntityFactory(
            EntityFactoryQosPolicy entityFactory) {
        qos.entity_factory = ((OpenspliceEntityFactoryQosPolicy) entityFactory)
                .getPolicy();
        return this;
    }

    @Override
    public ModifiableEntityFactoryQosPolicy getEntityFactory() {
        return new OpenspliceEntityFactoryQosPolicy(qos.entity_factory);
    }

    public OpenspliceDomainParticipantFactoryQos clone() {
        return new OpenspliceDomainParticipantFactoryQos(qos);
    }
}
