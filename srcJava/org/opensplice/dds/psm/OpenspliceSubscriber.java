package org.opensplice.dds.psm;

import java.util.Collection;

import org.omg.dds.core.Bootstrap;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.StatusCondition;
import org.omg.dds.core.status.Status;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.DataReaderListener;
import org.omg.dds.sub.DataReaderQos;
import org.omg.dds.sub.InstanceState;
import org.omg.dds.sub.SampleState;
import org.omg.dds.sub.SubscriberListener;
import org.omg.dds.sub.SubscriberQos;
import org.omg.dds.sub.ViewState;
import org.omg.dds.topic.TopicDescription;
import org.omg.dds.topic.TopicQos;
import org.omg.dds.type.builtin.BytesDataReader;
import org.omg.dds.type.builtin.KeyedBytes;
import org.omg.dds.type.builtin.KeyedBytesDataReader;
import org.omg.dds.type.builtin.KeyedString;
import org.omg.dds.type.builtin.KeyedStringDataReader;
import org.omg.dds.type.builtin.StringDataReader;
import org.opensplice.dds.psm.qos.OpenspliceDataReaderQos;

public class OpenspliceSubscriber implements org.omg.dds.sub.Subscriber {

    final private Bootstrap bootstrap;
    private DDS.Subscriber subscriber = null;
    private SubscriberListener thelistener = null;
    private OpenspliceDomainParticipant participant = null;
    private SubscriberQos theQos = null;

    public OpenspliceSubscriber(Bootstrap thebootstrap, DDS.Subscriber impl) {
        bootstrap = thebootstrap;
        subscriber = impl;
    }

    public DDS.Subscriber getSubscriber() {
        return subscriber;
    }

    @Override
    public DomainParticipant getParent() {
        return participant;
    }

    @Override
    public SubscriberListener getListener() {
        return thelistener;
    }

    @Override
    public void setListener(SubscriberListener listener) {
        thelistener = listener;
    }

    @Override
    public SubscriberQos getQos() {
        return theQos;
    }

    @Override
    public void setQos(SubscriberQos qos) {
        theQos = qos;
    }

    @Override
    public void setQos(String qosLibraryName, String qosProfileName) {
    }

    @Override
    public void enable() {
        if (subscriber != null) {
            subscriber.enable();
        }
    }

    @Override
    public StatusCondition<org.omg.dds.sub.Subscriber> getStatusCondition() {
        return null;
    }

    @Override
    public Collection<Class<? extends Status<?, ?>>> getStatusChanges(
            Collection<Class<? extends Status<?, ?>>> statuses) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public InstanceHandle getInstanceHandle() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub

    }

    @Override
    public void retain() {
        // TODO Auto-generated method stub

    }

    @Override
    public Bootstrap getBootstrap() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <TYPE> DataReader<TYPE> createDataReader(TopicDescription<TYPE> topic) {
        DataReader<TYPE> reader = new OpenspliceDataReader<TYPE>(bootstrap,
                topic, this);
        return reader;
    }

    @Override
    public <TYPE> DataReader<TYPE> createDataReader(
            TopicDescription<TYPE> topic, DataReaderQos qos,
            DataReaderListener<TYPE> listener,
            Collection<Class<? extends Status<?, ?>>> statuses) {
        DataReader<TYPE> reader = createDataReader(topic);
        reader.setListener(listener);
        return reader;
    }

    @Override
    public <TYPE> DataReader<TYPE> createDataReader(
            TopicDescription<TYPE> topic, String qosLibraryName,
            String qosProfileName, DataReaderListener<TYPE> listener,
            Collection<Class<? extends Status<?, ?>>> statuses) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BytesDataReader createBytesDataReader(TopicDescription<byte[]> topic) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BytesDataReader createBytesDataReader(
            TopicDescription<byte[]> topic, DataReaderQos qos,
            DataReaderListener<byte[]> listener,
            Collection<Class<? extends Status<?, ?>>> statuses) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BytesDataReader createBytesDataReader(
            TopicDescription<byte[]> topic, String qosLibraryName,
            String qosProfileName, DataReaderListener<byte[]> listener,
            Collection<Class<? extends Status<?, ?>>> statuses) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KeyedBytesDataReader createKeyedBytesDataReader(
            TopicDescription<KeyedBytes> topic) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KeyedBytesDataReader createKeyedBytesDataReader(
            TopicDescription<KeyedBytes> topic, DataReaderQos qos,
            DataReaderListener<KeyedBytes> listener,
            Collection<Class<? extends Status<?, ?>>> statuses) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KeyedBytesDataReader createKeyedBytesDataReader(
            TopicDescription<KeyedBytes> topic, String qosLibraryName,
            String qosProfileName, DataReaderListener<KeyedBytes> listener,
            Collection<Class<? extends Status<?, ?>>> statuses) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StringDataReader createStringDataReader(
            TopicDescription<String> topic) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StringDataReader createStringDataReader(
            TopicDescription<String> topic, DataReaderQos qos,
            DataReaderListener<String> listener,
            Collection<Class<? extends Status<?, ?>>> statuses) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StringDataReader createStringDataReader(
            TopicDescription<String> topic, String qosLibraryName,
            String qosProfileName, DataReaderListener<String> listener,
            Collection<Class<? extends Status<?, ?>>> statuses) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KeyedStringDataReader createKeyedStringDataReader(
            TopicDescription<KeyedString> topic) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KeyedStringDataReader createKeyedStringDataReader(
            TopicDescription<KeyedString> topic, DataReaderQos qos,
            DataReaderListener<KeyedString> listener,
            Collection<Class<? extends Status<?, ?>>> statuses) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KeyedStringDataReader createKeyedStringDataReader(
            TopicDescription<KeyedString> topic, String qosLibraryName,
            String qosProfileName, DataReaderListener<KeyedString> listener,
            Collection<Class<? extends Status<?, ?>>> statuses) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <TYPE> DataReader<TYPE> lookupDataReader(String topicName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <TYPE> DataReader<TYPE> lookupDataReader(
            TopicDescription<TYPE> topicName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BytesDataReader lookupBytesDataReader(
            TopicDescription<byte[]> topicName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KeyedBytesDataReader lookupKeyedBytesDataReader(
            TopicDescription<KeyedBytes> topicName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StringDataReader lookupStringDataReader(
            TopicDescription<String> topicName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KeyedStringDataReader lookupKeyedStringDataReader(
            TopicDescription<KeyedString> topicName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void closeContainedEntities() {
        // TODO Auto-generated method stub

    }

    @Override
    public Collection<DataReader<?>> getDataReaders(
            Collection<DataReader<?>> readers) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<DataReader<?>> getDataReaders(
            Collection<DataReader<?>> readers,
            Collection<SampleState> sampleStates,
            Collection<ViewState> viewStates,
            Collection<InstanceState> instanceStates) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void notifyDataReaders() {
        // TODO Auto-generated method stub

    }

    @Override
    public void beginAccess() {
        // TODO Auto-generated method stub

    }

    @Override
    public void endAccess() {
        // TODO Auto-generated method stub
    }

    @Override
    public DataReaderQos getDefaultDataReaderQos() {
        DDS.DataReaderQosHolder holder = new DDS.DataReaderQosHolder();
        subscriber.get_default_datareader_qos(holder);
        return new OpenspliceDataReaderQos(holder.value, bootstrap);
    }

    @Override
    public void setDefaultDataReaderQos(DataReaderQos qos) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setDefaultDataReaderQos(String qosLibraryName,
            String qosProfileName) {
        // TODO Auto-generated method stub

    }

    @Override
    public void copyFromTopicQos(DataReaderQos dst, TopicQos src) {
        // TODO Auto-generated method stub

    }

}
