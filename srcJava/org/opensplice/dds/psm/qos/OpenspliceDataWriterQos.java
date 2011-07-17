package org.opensplice.dds.psm.qos;

import org.omg.dds.core.Bootstrap;
import org.omg.dds.core.policy.DataRepresentationQosPolicy;
import org.omg.dds.core.policy.DeadlineQosPolicy;
import org.omg.dds.core.policy.DestinationOrderQosPolicy;
import org.omg.dds.core.policy.DurabilityQosPolicy;
import org.omg.dds.core.policy.DurabilityServiceQosPolicy;
import org.omg.dds.core.policy.HistoryQosPolicy;
import org.omg.dds.core.policy.LatencyBudgetQosPolicy;
import org.omg.dds.core.policy.LifespanQosPolicy;
import org.omg.dds.core.policy.LivelinessQosPolicy;
import org.omg.dds.core.policy.OwnershipQosPolicy;
import org.omg.dds.core.policy.OwnershipStrengthQosPolicy;
import org.omg.dds.core.policy.QosPolicy;
import org.omg.dds.core.policy.QosPolicy.Id;
import org.omg.dds.core.policy.ReliabilityQosPolicy;
import org.omg.dds.core.policy.ResourceLimitsQosPolicy;
import org.omg.dds.core.policy.TransportPriorityQosPolicy;
import org.omg.dds.core.policy.TypeConsistencyEnforcementQosPolicy;
import org.omg.dds.core.policy.UserDataQosPolicy;
import org.omg.dds.core.policy.WriterDataLifecycleQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableDataRepresentationQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableDeadlineQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableDestinationOrderQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableDurabilityQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableDurabilityServiceQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableHistoryQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableLatencyBudgetQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableLifespanQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableLivelinessQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableOwnershipQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableOwnershipStrengthQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableReliabilityQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableResourceLimitsQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableTransportPriorityQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableTypeConsistencyEnforcementQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableUserDataQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiableWriterDataLifecycleQosPolicy;
import org.omg.dds.pub.DataWriterQos;
import org.omg.dds.pub.modifiable.ModifiableDataWriterQos;
import org.omg.dds.topic.TopicQos;
import org.opensplice.dds.psm.qos.policy.OpenspliceDeadlineQosPolicy;
import org.opensplice.dds.psm.qos.policy.OpenspliceDestinationOrderQosPolicy;
import org.opensplice.dds.psm.qos.policy.OpenspliceDurabilityQosPolicy;
import org.opensplice.dds.psm.qos.policy.OpenspliceHistoryQosPolicy;
import org.opensplice.dds.psm.qos.policy.OpenspliceLatencyBudgetQosPolicy;
import org.opensplice.dds.psm.qos.policy.OpenspliceLifespanQosPolicy;
import org.opensplice.dds.psm.qos.policy.OpenspliceLivelinessQosPolicy;
import org.opensplice.dds.psm.qos.policy.OpenspliceOwnershipQosPolicy;
import org.opensplice.dds.psm.qos.policy.OpenspliceOwnershipStrengthQosPolicy;
import org.opensplice.dds.psm.qos.policy.OpenspliceReliabilityQosPolicy;
import org.opensplice.dds.psm.qos.policy.OpenspliceResourceLimitsQosPolicy;
import org.opensplice.dds.psm.qos.policy.OpenspliceTransportPriorityQosPolicy;

public class OpenspliceDataWriterQos implements ModifiableDataWriterQos {

    private DDS.DataWriterQos qos;
    private Bootstrap bootStrap;

    public OpenspliceDataWriterQos(DDS.DataWriterQos theqos,
            Bootstrap thebootStrap) {
        bootStrap = thebootStrap;
        qos = theqos;
    }

    public DDS.DataWriterQos getQos() {
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
    public ModifiableDataWriterQos modify() {
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
    public ModifiableDurabilityServiceQosPolicy getDurabilityService() {
        // return new
        // OpenspliceDurabilityServiceQosPolicy(qos.durabilityService);
        return null;
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
    public ModifiableReliabilityQosPolicy getReliability() {
        return new OpenspliceReliabilityQosPolicy(qos.reliability);
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
    public ModifiableTransportPriorityQosPolicy getTransportPriority() {
        return new OpenspliceTransportPriorityQosPolicy(qos.transport_priority);
    }

    @Override
    public ModifiableLifespanQosPolicy getLifespan() {
        return new OpenspliceLifespanQosPolicy(qos.lifespan);
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
    public ModifiableOwnershipStrengthQosPolicy getOwnershipStrength() {
        return new OpenspliceOwnershipStrengthQosPolicy(qos.ownership_strength);
    }

    @Override
    public ModifiableWriterDataLifecycleQosPolicy getWriterDataLifecycle() {
        // TODO Auto-generated method stub
        return null;
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

    public OpenspliceDataWriterQos clone() {
        return new OpenspliceDataWriterQos(qos, bootStrap);
    }

    @Override
    public <POLICY extends QosPolicy<POLICY, ?>> POLICY put(Id key, POLICY value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifiableDataWriterQos copyFrom(DataWriterQos other) {
        bootStrap = (other.getBootstrap());
        qos = ((OpenspliceDataWriterQos) other).qos;
        return this;
    }

    @Override
    public DataWriterQos finishModification() {
        return this;
    }

    @Override
    public ModifiableDataWriterQos setDurability(DurabilityQosPolicy durability) {
        qos.durability = ((OpenspliceDurabilityQosPolicy) durability)
                .getPolicy();
        return this;
    }

    @Override
    public ModifiableDataWriterQos setDurabilityService(
            DurabilityServiceQosPolicy durabilityService) {
        // qos.durability_service =
        // ((OpenspliceDurabilityServiceQosPolicy) durabilityService)
        // .getPolicy();
        return this;
    }

    @Override
    public ModifiableDataWriterQos setDeadline(DeadlineQosPolicy deadline) {
        qos.deadline = ((OpenspliceDeadlineQosPolicy) deadline).getPolicy();
        return this;
    }

    @Override
    public ModifiableDataWriterQos setLatencyBudget(
            LatencyBudgetQosPolicy latencyBudget) {
        qos.latency_budget = ((OpenspliceLatencyBudgetQosPolicy) latencyBudget)
                .getPolicy();
        return this;
    }

    @Override
    public ModifiableDataWriterQos setLiveliness(LivelinessQosPolicy liveliness) {
        // policy.liveliness =
        // ((OpenspliceLivelynessQosPolicy)liveliness).getPolicy();
        return this;
    }

    @Override
    public ModifiableDataWriterQos setReliability(
            ReliabilityQosPolicy reliability) {
        qos.reliability = ((OpenspliceReliabilityQosPolicy) reliability)
                .getPolicy();
        return this;
    }

    @Override
    public ModifiableDataWriterQos setDestinationOrder(
            DestinationOrderQosPolicy destinationOrder) {
        qos.destination_order = ((OpenspliceDestinationOrderQosPolicy) destinationOrder)
                .getPolicy();
        return this;
    }

    @Override
    public ModifiableDataWriterQos setHistory(HistoryQosPolicy history) {
        qos.history = ((OpenspliceHistoryQosPolicy) history).getPolicy();
        return this;
    }

    @Override
    public ModifiableDataWriterQos setResourceLimits(
            ResourceLimitsQosPolicy resourceLimits) {
        qos.resource_limits = ((OpenspliceResourceLimitsQosPolicy) resourceLimits)
                .getPolicy();
        return this;
    }

    @Override
    public ModifiableDataWriterQos setTransportPriority(
            TransportPriorityQosPolicy transportPriority) {
        qos.transport_priority = ((OpenspliceTransportPriorityQosPolicy) transportPriority)
                .getPpolicy();
        return this;
    }

    @Override
    public ModifiableDataWriterQos setLifespan(LifespanQosPolicy lifespan) {
        qos.lifespan = ((OpenspliceLifespanQosPolicy) lifespan).getPolicy();
        return this;
    }

    @Override
    public ModifiableDataWriterQos setUserData(UserDataQosPolicy userData) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public ModifiableDataWriterQos setOwnership(OwnershipQosPolicy ownership) {
        qos.ownership = ((OpenspliceOwnershipQosPolicy) ownership).getPolicy();
        return this;
    }

    @Override
    public ModifiableDataWriterQos setOwnershipStrength(
            OwnershipStrengthQosPolicy ownershipStrength) {
        qos.ownership_strength = ((OpenspliceOwnershipStrengthQosPolicy) ownershipStrength)
                .getPolicy();
        return this;
    }

    @Override
    public ModifiableDataWriterQos setWriterDataLifecycle(
            WriterDataLifecycleQosPolicy writerDataLifecycle) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifiableDataWriterQos setRepresentation(
            DataRepresentationQosPolicy representation) {
        // policy.presentation = ((OpensplicePresentationQosPolicy)
        // presentation)
        // .getPolicy();
        return this;
    }

    @Override
    public ModifiableDataWriterQos setTypeConsistency(
            TypeConsistencyEnforcementQosPolicy typeConsistency) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifiableDataWriterQos copyFrom(TopicQos src) {
        // TODO Auto-generated method stub
        return null;
    }
}
