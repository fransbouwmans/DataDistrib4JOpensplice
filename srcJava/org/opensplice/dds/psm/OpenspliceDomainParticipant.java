package org.opensplice.dds.psm;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.omg.dds.core.Bootstrap;
import org.omg.dds.core.Duration;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.StatusCondition;
import org.omg.dds.core.modifiable.ModifiableTime;
import org.omg.dds.core.status.Status;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantListener;
import org.omg.dds.domain.DomainParticipantQos;
import org.omg.dds.pub.Publisher;
import org.omg.dds.pub.PublisherListener;
import org.omg.dds.pub.PublisherQos;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.sub.SubscriberListener;
import org.omg.dds.sub.SubscriberQos;
import org.omg.dds.topic.ContentFilteredTopic;
import org.omg.dds.topic.MultiTopic;
import org.omg.dds.topic.ParticipantBuiltinTopicData;
import org.omg.dds.topic.Topic;
import org.omg.dds.topic.TopicBuiltinTopicData;
import org.omg.dds.topic.TopicDescription;
import org.omg.dds.topic.TopicListener;
import org.omg.dds.topic.TopicQos;
import org.omg.dds.type.TypeSupport;
import org.opensplice.dds.psm.qos.OpenspliceTopicQos;
import org.opensplice.dds.psm.qos.policy.OpenspliceDomainParticipantQos;

public class OpenspliceDomainParticipant
        implements DomainParticipant {

    final private Bootstrap bootstrap;
    final private DDS.DomainParticipant openspliceParticipant;
    private DomainParticipantListener dlistener = null;

    public OpenspliceDomainParticipant(Bootstrap thebootstrap,
            DDS.DomainParticipant participant) {
        bootstrap = thebootstrap;
        openspliceParticipant = participant;
        openspliceParticipant.enable();
    }

    public DDS.DomainParticipant getOpenspliceParticipant() {
        return openspliceParticipant;
    }

    @Override
    public DomainParticipantListener getListener() {
        return dlistener;
    }

    @Override
    public void setListener(DomainParticipantListener listener) {
        dlistener = listener;
    }

    @Override
    public DomainParticipantQos getQos() {
        DDS.DomainParticipantQosHolder holder = new DDS.DomainParticipantQosHolder();
        openspliceParticipant.get_qos(holder);
        return new OpenspliceDomainParticipantQos(holder.value);
    }

    @Override
    public void setQos(DomainParticipantQos qos) {
        openspliceParticipant.set_qos(((OpenspliceDomainParticipantQos) qos)
                .getQos());
    }

    @Override
    public void setQos(String qosLibraryName, String qosProfileName) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public StatusCondition<DomainParticipant> getStatusCondition() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Collection<Class<? extends Status<?, ?>>> getStatusChanges(
            Collection<Class<? extends Status<?, ?>>> statuses) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public InstanceHandle getInstanceHandle() {
        return new OpenspliceInstanceHandle(
                openspliceParticipant.get_instance_handle());
    }

    @Override
    public void close() {
        openspliceParticipant.delete_contained_entities();
    }

    @Override
    public void retain() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Bootstrap getBootstrap() {
        return bootstrap;
    }

    @Override
    public Publisher createPublisher() {
        DDS.PublisherQosHolder holder = new DDS.PublisherQosHolder();
        openspliceParticipant.get_default_publisher_qos(holder);
        DDS.Publisher publisher = openspliceParticipant.create_publisher(
                holder.value, null, 0);
        return new OpensplicePublisher(bootstrap, publisher, this);
    }

    @Override
    public Publisher createPublisher(PublisherQos qos,
            PublisherListener listener,
            Collection<Class<? extends Status<?, ?>>> statuses) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Publisher createPublisher(String qosLibraryName,
            String qosProfileName, PublisherListener listener,
            Collection<Class<? extends Status<?, ?>>> statuses) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Subscriber createSubscriber() {
        DDS.SubscriberQosHolder holder = new DDS.SubscriberQosHolder();
        openspliceParticipant.get_default_subscriber_qos(holder);
        // holder.value.partition.name = new String[] {partition};
        DDS.Subscriber subscriber = openspliceParticipant.create_subscriber(
                holder.value, null, 0);
        subscriber.enable();
        OpenspliceSubscriber subscriberOpensplice = new OpenspliceSubscriber(
                bootstrap, subscriber);
        return subscriberOpensplice;
    }

    @Override
    public Subscriber createSubscriber(SubscriberQos qos,
            SubscriberListener listener,
            Collection<Class<? extends Status<?, ?>>> statuses) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Subscriber createSubscriber(String qosLibraryName,
            String qosProfileName, SubscriberListener listener,
            Collection<Class<? extends Status<?, ?>>> statuses) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Subscriber getBuiltinSubscriber() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <TYPE> Topic<TYPE> createTopic(String topicName, Class<TYPE> type) {
        return new OpenspliceTopic<TYPE>(this, topicName, type, null);
    }

    @Override
    public <TYPE> Topic<TYPE> createTopic(String topicName, Class<TYPE> type,
            TopicQos qos, TopicListener<TYPE> listener,
            Collection<Class<? extends Status<?, ?>>> statuses) {
        Topic<TYPE> topic = new OpenspliceTopic<TYPE>(this, topicName, type,
                qos);
        topic.setListener(listener);
        return topic;
    }

    @Override
    public <TYPE> Topic<TYPE> createTopic(String topicName, Class<TYPE> type,
            String qosLibraryName, String qosProfileName,
            TopicListener<TYPE> listener,
            Collection<Class<? extends Status<?, ?>>> statuses) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public <TYPE> Topic<TYPE> createTopic(String topicName,
            TypeSupport<TYPE> type) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public <TYPE> Topic<TYPE> createTopic(String topicName,
            TypeSupport<TYPE> type, TopicQos qos, TopicListener<TYPE> listener,
            Collection<Class<? extends Status<?, ?>>> statuses) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public <TYPE> Topic<TYPE> createTopic(String topicName,
            TypeSupport<TYPE> type, String qosLibraryName,
            String qosProfileName, TopicListener<TYPE> listener,
            Collection<Class<? extends Status<?, ?>>> statuses) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public <TYPE> Topic<TYPE> findTopic(String topicName, Duration timeout)
            throws TimeoutException {
        OpenspliceDuration theTimeout = new OpenspliceDuration(timeout);
        DDS.Topic thetopic = openspliceParticipant.find_topic(topicName,
                theTimeout.getDuration());
        DDS.TopicQosHolder holder = new DDS.TopicQosHolder();
        thetopic.get_qos(holder);
        try {
            Class<TYPE> theType = (Class<TYPE>) (Class.forName(thetopic
                    .get_type_name()));
            return new OpenspliceTopic<TYPE>(this, topicName,
                    theType,
                    new OpenspliceTopicQos(holder.value));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found for topic type");
        }
    }

    @Override
    public <TYPE> Topic<TYPE> findTopic(String topicName, long timeout,
            TimeUnit unit) throws TimeoutException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <TYPE> TopicDescription<TYPE> lookupTopicDescription(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <TYPE> ContentFilteredTopic<TYPE> createContentFilteredTopic(
            String name, Topic<? extends TYPE> relatedTopic,
            String filterExpression, List<String> expressionParameters) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <TYPE> MultiTopic<TYPE> createMultiTopic(String name,
            String typeName, String subscriptionExpression,
            List<String> expressionParameters) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void closeContainedEntities() {
        // TODO Auto-generated method stub

    }

    @Override
    public void ignoreParticipant(InstanceHandle handle) {
        // TODO Auto-generated method stub

    }

    @Override
    public void ignoreTopic(InstanceHandle handle) {
        // TODO Auto-generated method stub

    }

    @Override
    public void ignorePublication(InstanceHandle handle) {
        // TODO Auto-generated method stub

    }

    @Override
    public void ignoreSubscription(InstanceHandle handle) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getDomainId() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void assertLiveliness() {
        // TODO Auto-generated method stub

    }

    @Override
    public PublisherQos getDefaultPublisherQos() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setDefaultPublisherQos(PublisherQos qos) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setDefaultPublisherQos(String qosLibraryName,
            String qosProfileName) {
        // TODO Auto-generated method stub

    }

    @Override
    public SubscriberQos getDefaultSubscriberQos() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setDefaultSubscriberQos(SubscriberQos qos) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setDefaultSubscriberQos(String qosLibraryName,
            String qosProfileName) {
        // TODO Auto-generated method stub

    }

    @Override
    public TopicQos getDefaultTopicQos() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setDefaultTopicQos(TopicQos qos) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setDefaultTopicQos(String qosLibraryName, String qosProfileName) {
        // TODO Auto-generated method stub

    }

    @Override
    public Collection<InstanceHandle> getDiscoveredParticipants(
            Collection<InstanceHandle> participantHandles) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ParticipantBuiltinTopicData getDiscoveredParticipantData(
            ParticipantBuiltinTopicData participantData,
            InstanceHandle participantHandle) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<InstanceHandle> getDiscoveredTopics(
            Collection<InstanceHandle> topicHandles) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TopicBuiltinTopicData getDiscoveredTopicData(
            TopicBuiltinTopicData topicData, InstanceHandle topicHandle) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean containsEntity(InstanceHandle handle) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ModifiableTime getCurrentTime(ModifiableTime currentTime) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void enable() {
        // TODO Auto-generated method stub

    }
}
