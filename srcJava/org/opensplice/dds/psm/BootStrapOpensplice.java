package org.opensplice.dds.psm;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.omg.dds.core.Bootstrap;
import org.omg.dds.core.Duration;
import org.omg.dds.core.GuardCondition;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.Time;
import org.omg.dds.core.WaitSet;
import org.omg.dds.core.modifiable.ModifiableDuration;
import org.omg.dds.core.modifiable.ModifiableInstanceHandle;
import org.omg.dds.core.modifiable.ModifiableTime;
import org.omg.dds.core.policy.QosPolicy;
import org.omg.dds.core.policy.QosPolicy.Id;
import org.omg.dds.core.status.DataAvailableStatus;
import org.omg.dds.core.status.DataOnReadersStatus;
import org.omg.dds.core.status.InconsistentTopicStatus;
import org.omg.dds.core.status.LivelinessChangedStatus;
import org.omg.dds.core.status.LivelinessLostStatus;
import org.omg.dds.core.status.OfferedDeadlineMissedStatus;
import org.omg.dds.core.status.OfferedIncompatibleQosStatus;
import org.omg.dds.core.status.PublicationMatchedStatus;
import org.omg.dds.core.status.RequestedDeadlineMissedStatus;
import org.omg.dds.core.status.RequestedIncompatibleQosStatus;
import org.omg.dds.core.status.SampleLostStatus;
import org.omg.dds.core.status.SampleRejectedStatus;
import org.omg.dds.core.status.Status;
import org.omg.dds.core.status.SubscriptionMatchedStatus;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.sub.InstanceState;
import org.omg.dds.sub.SampleState;
import org.omg.dds.sub.ViewState;
import org.omg.dds.topic.BuiltinTopicKey;
import org.omg.dds.topic.ParticipantBuiltinTopicData;
import org.omg.dds.topic.PublicationBuiltinTopicData;
import org.omg.dds.topic.SubscriptionBuiltinTopicData;
import org.omg.dds.topic.TopicBuiltinTopicData;
import org.omg.dds.type.TypeSupport;
import org.omg.dds.type.dynamic.DynamicDataFactory;
import org.omg.dds.type.dynamic.DynamicTypeFactory;

/**
 * Use this implementation for opensplice by defining the following:
 * -Dorg.omg.dds.serviceClassName=org.opensplice.dds.psm.BootStrapOpensplice
 */
public class BootStrapOpensplice extends Bootstrap {

    ServiceProvider serviceProvider = new ServiceProvider();
    OpenspliceDomainParticipantFactory factory = new OpenspliceDomainParticipantFactory(
            this);

    @Override
    public ServiceProviderInterface getSPI() {
        return serviceProvider;
    }

    private class ServiceProvider implements ServiceProviderInterface {

        @Override
        public DomainParticipantFactory getParticipantFactory() {
            return factory;
        }

        @Override
        public DynamicTypeFactory getTypeFactory() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public DynamicDataFactory getDataFactory() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public <TYPE> TypeSupport<TYPE> newTypeSupport(Class<TYPE> type,
                String registeredName) {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public ModifiableDuration newDuration(long duration, TimeUnit unit) {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public Duration infiniteDuration() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public Duration zeroDuration() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public ModifiableTime newTime(long time, TimeUnit units) {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public Time invalidTime() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public ModifiableInstanceHandle newInstanceHandle() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public InstanceHandle nilHandle() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public GuardCondition newGuardCondition() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public WaitSet newWaitSet() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public BuiltinTopicKey newBuiltinTopicKey() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public ParticipantBuiltinTopicData newParticipantBuiltinTopicData() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public PublicationBuiltinTopicData newPublicationBuiltinTopicData() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public SubscriptionBuiltinTopicData newSubscriptionBuiltinTopicData() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public TopicBuiltinTopicData newTopicBuiltinTopicData() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public Id getQosPolicyId(Class<? extends QosPolicy<?, ?>> policyClass) {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public Set<Class<? extends Status<?, ?>>> allStatusKinds() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public Set<Class<? extends Status<?, ?>>> noStatusKinds() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public <TYPE> LivelinessLostStatus<TYPE> newLivelinessLostStatus() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public <TYPE> OfferedDeadlineMissedStatus<TYPE> newOfferedDeadlineMissedStatus() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public <TYPE> OfferedIncompatibleQosStatus<TYPE> newOfferedIncompatibleQosStatus() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public <TYPE> PublicationMatchedStatus<TYPE> newPublicationMatchedStatus() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public <TYPE> LivelinessChangedStatus<TYPE> newLivelinessChangedStatus() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public <TYPE> RequestedDeadlineMissedStatus<TYPE> newRequestedDeadlineMissedStatus() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public <TYPE> RequestedIncompatibleQosStatus<TYPE> newRequestedIncompatibleQosStatus() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public <TYPE> SampleLostStatus<TYPE> newSampleLostStatus() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public <TYPE> SampleRejectedStatus<TYPE> newSampleRejectedStatus() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public <TYPE> SubscriptionMatchedStatus<TYPE> newSubscriptionMatchedStatus() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public <TYPE> DataAvailableStatus<TYPE> newDataAvailableStatus() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public DataOnReadersStatus newDataOnReadersStatus() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public <TYPE> InconsistentTopicStatus<TYPE> newInconsistentTopicStatus() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public Set<InstanceState> anyInstanceStateSet() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public Set<InstanceState> notAliveInstanceStateSet() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public Set<SampleState> anySampleStateSet() {
            throw new RuntimeException("Not yet implemented");
        }

        @Override
        public Set<ViewState> anyViewStateSet() {
            throw new RuntimeException("Not yet implemented");
        }
    }
}
