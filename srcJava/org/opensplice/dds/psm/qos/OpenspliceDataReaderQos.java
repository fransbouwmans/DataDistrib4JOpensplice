package org.opensplice.dds.psm.qos;

import org.omg.dds.core.Bootstrap;
import org.omg.dds.core.policy.DataRepresentationQosPolicy;
import org.omg.dds.core.policy.DeadlineQosPolicy;
import org.omg.dds.core.policy.DestinationOrderQosPolicy;
import org.omg.dds.core.policy.DurabilityQosPolicy;
import org.omg.dds.core.policy.HistoryQosPolicy;
import org.omg.dds.core.policy.LatencyBudgetQosPolicy;
import org.omg.dds.core.policy.LivelinessQosPolicy;
import org.omg.dds.core.policy.OwnershipQosPolicy;
import org.omg.dds.core.policy.QosPolicy;
import org.omg.dds.core.policy.QosPolicy.Id;
import org.omg.dds.core.policy.ReaderDataLifecycleQosPolicy;
import org.omg.dds.core.policy.ResourceLimitsQosPolicy;
import org.omg.dds.core.policy.TimeBasedFilterQosPolicy;
import org.omg.dds.core.policy.TypeConsistencyEnforcementQosPolicy;
import org.omg.dds.core.policy.UserDataQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableDataRepresentationQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableDeadlineQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableDestinationOrderQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableDurabilityQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableHistoryQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableLatencyBudgetQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableLivelinessQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableOwnershipQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableReaderDataLifecycleQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableResourceLimitsQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableTimeBasedFilterQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableTypeConsistencyEnforcementQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableUserDataQosPolicy;
import org.omg.dds.sub.DataReaderQos;
import org.omg.dds.sub.modifiable.ModifiableDataReaderQos;
import org.omg.dds.topic.TopicQos;
import org.opensplice.dds.psm.qos.policy.OpenspliceDeadlineQosPolicy;
import org.opensplice.dds.psm.qos.policy.OpenspliceDestinationOrderQosPolicy;
import org.opensplice.dds.psm.qos.policy.OpenspliceDurabilityQosPolicy;
import org.opensplice.dds.psm.qos.policy.OpenspliceHistoryQosPolicy;
import org.opensplice.dds.psm.qos.policy.OpenspliceLatencyBudgetQosPolicy;
import org.opensplice.dds.psm.qos.policy.OpenspliceLivelinessQosPolicy;
import org.opensplice.dds.psm.qos.policy.OpenspliceOwnershipQosPolicy;
import org.opensplice.dds.psm.qos.policy.OpenspliceResourceLimitsQosPolicy;

public class OpenspliceDataReaderQos implements ModifiableDataReaderQos {

    private DDS.DataReaderQos qos;
    private Bootstrap bootStrap;

    public OpenspliceDataReaderQos(DDS.DataReaderQos theqos,
            Bootstrap thebootStrap) {
        bootStrap = thebootStrap;
        qos = theqos;
    }

    public DDS.DataReaderQos getQos() {
        return qos;
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
    public ModifiableDataReaderQos modify() {
        return this;
    }

    @Override
    public Bootstrap getBootstrap() {
        return bootStrap;
    }

    @Override
    public ModifiableDurabilityQosPolicy getDurability() {
        return new OpenspliceDurabilityQosPolicy(qos.durability);
    }

    @Override
    public ModifiableDeadlineQosPolicy getDeadline() {
        return new OpenspliceDeadlineQosPolicy(qos.deadline);
    }

    @Override
    public ModifiableLatencyBudgetQosPolicy getLatencyBudget() {
        return new OpenspliceLatencyBudgetQosPolicy(qos.latency_budget);
    }

    @Override
    public ModifiableLivelinessQosPolicy getLiveliness() {
        return new OpenspliceLivelinessQosPolicy(qos.liveliness);
    }

    @Override
    public ModifiableDestinationOrderQosPolicy getDestinationOrder() {
        return new OpenspliceDestinationOrderQosPolicy(qos.destination_order);
    }

    @Override
    public ModifiableHistoryQosPolicy getHistory() {
        return new OpenspliceHistoryQosPolicy(qos.history);
    }

    @Override
    public ModifiableResourceLimitsQosPolicy getResourceLimits() {
        return new OpenspliceResourceLimitsQosPolicy(qos.resource_limits);
    }

    @Override
    public ModifiableUserDataQosPolicy getUserData() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifiableOwnershipQosPolicy getOwnership() {
        return new OpenspliceOwnershipQosPolicy(qos.ownership);
    }

    @Override
    public ModifiableDataRepresentationQosPolicy getRepresentation() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifiableTypeConsistencyEnforcementQosPolicy getTypeConsistency() {
        // TODO Auto-generated method stub
        return null;
    }

    public OpenspliceDataReaderQos clone() {
        return new OpenspliceDataReaderQos(qos, bootStrap);
    }

    @Override
    public ModifiableDataReaderQos copyFrom(DataReaderQos other) {
        bootStrap = (other.getBootstrap());
        qos = ((OpenspliceDataReaderQos) other).qos;
        return this;
    }

    @Override
    public DataReaderQos finishModification() {
        return this;
    }

    @Override
    public ModifiableDataReaderQos setDurability(DurabilityQosPolicy durability) {
        qos.durability = ((OpenspliceDurabilityQosPolicy) durability)
                .getPolicy();
        return this;
    }

    @Override
    public ModifiableDataReaderQos setDeadline(DeadlineQosPolicy deadline) {
        qos.deadline = ((OpenspliceDeadlineQosPolicy) deadline).getPolicy();
        return this;
    }

    @Override
    public ModifiableDataReaderQos setLatencyBudget(
            LatencyBudgetQosPolicy latencyBudget) {
        qos.latency_budget = ((OpenspliceLatencyBudgetQosPolicy) latencyBudget)
                .getPolicy();
        return this;
    }

    @Override
    public ModifiableDataReaderQos setLiveliness(LivelinessQosPolicy liveliness) {
        qos.liveliness =
                ((OpenspliceLivelinessQosPolicy) liveliness).getPolicy();
        return this;
    }

    @Override
    public ModifiableDataReaderQos setDestinationOrder(
            DestinationOrderQosPolicy destinationOrder) {
        qos.destination_order = ((OpenspliceDestinationOrderQosPolicy) destinationOrder)
                .getPolicy();
        return this;
    }

    @Override
    public ModifiableDataReaderQos setHistory(HistoryQosPolicy history) {
        qos.history = ((OpenspliceHistoryQosPolicy) history).getPolicy();
        return this;
    }

    @Override
    public ModifiableDataReaderQos setResourceLimits(
            ResourceLimitsQosPolicy resourceLimits) {
        qos.resource_limits = ((OpenspliceResourceLimitsQosPolicy) resourceLimits)
                .getPolicy();
        return this;
    }

    @Override
    public ModifiableDataReaderQos setUserData(UserDataQosPolicy userData) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public ModifiableDataReaderQos setOwnership(OwnershipQosPolicy ownership) {
        qos.ownership = ((OpenspliceOwnershipQosPolicy) ownership).getPolicy();
        return this;
    }

    @Override
    public ModifiableDataReaderQos setRepresentation(
            DataRepresentationQosPolicy representation) {
        // policy.presentation = ((OpensplicePresentationQosPolicy)
        // presentation)
        // .getPolicy();
        return this;
    }

    @Override
    public ModifiableDataReaderQos setTypeConsistency(
            TypeConsistencyEnforcementQosPolicy typeConsistency) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifiableDataReaderQos copyFrom(TopicQos src) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <POLICY extends QosPolicy<POLICY, ?>> POLICY put(Id key, POLICY value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifiableDataReaderQos setTimeBasedFilter(
            TimeBasedFilterQosPolicy timeBasedFilter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifiableTimeBasedFilterQosPolicy getTimeBasedFilter() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifiableDataReaderQos setReaderDataLifecycle(
            ReaderDataLifecycleQosPolicy readerDataLifecycle) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifiableReaderDataLifecycleQosPolicy getReaderDataLifecycle() {
        // TODO Auto-generated method stub
        return null;
    }
}
