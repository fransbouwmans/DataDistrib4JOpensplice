package org.opensplice.dds.psm;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.omg.dds.core.Bootstrap;
import org.omg.dds.core.Duration;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.StatusCondition;
import org.omg.dds.core.Time;
import org.omg.dds.core.modifiable.ModifiableInstanceHandle;
import org.omg.dds.core.status.LivelinessLostStatus;
import org.omg.dds.core.status.OfferedDeadlineMissedStatus;
import org.omg.dds.core.status.OfferedIncompatibleQosStatus;
import org.omg.dds.core.status.PublicationMatchedStatus;
import org.omg.dds.core.status.Status;
import org.omg.dds.pub.DataWriter;
import org.omg.dds.pub.DataWriterListener;
import org.omg.dds.pub.DataWriterQos;
import org.omg.dds.pub.Publisher;
import org.omg.dds.topic.SubscriptionBuiltinTopicData;
import org.omg.dds.topic.Topic;
import org.omg.dds.topic.TopicDescription;
import org.opensplice.dds.psm.qos.OpenspliceDataWriterQos;

import DDS.ANY_STATUS;

public class OpenspliceDataWriter<TYPE> implements DataWriter<TYPE> {
    final private OpenspliceTopic<TYPE> topic;
    final private OpensplicePublisher publisher;
    final private Bootstrap bootstrap;
    private DDS.DataWriter dataWriter = null;

    private Method write = null;
    private Method dispose = null;

    private Field listValue = null;
    private Field sampleValue = null;
    private Object[] writeParameters = null;
    private OpenspliceDataWriterQos qos = null;

    public OpenspliceDataWriter(Bootstrap theBootstrap,
            TopicDescription<TYPE> theTopic, Publisher thePublisher,
            DataWriterQos theQos) {
        bootstrap = theBootstrap;
        topic = (OpenspliceTopic<TYPE>) theTopic;
        publisher = (OpensplicePublisher) thePublisher;
        if (theQos != null) {
            qos = (OpenspliceDataWriterQos) theQos;
        } else {
            qos = (OpenspliceDataWriterQos) publisher.getDefaultDataWriterQos();
        }
        createWriter();
    }

    /**
     * Get the underlying class for a type, or null if the type is a variable
     * type.
     * @param type
     *            the type
     * @return the underlying class
     */
    public static Class<?> getClass(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        } else if (type instanceof ParameterizedType) {
            return getClass(((ParameterizedType) type).getRawType());
        } else if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type)
                    .getGenericComponentType();
            Class<?> componentClass = getClass(componentType);
            if (componentClass != null) {
                return Array.newInstance(componentClass, 0).getClass();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private void createWriter() {
        try {
            dataWriter = publisher.getPublisher().create_datawriter(
                    topic.getTopic(), qos.getQos(), null, ANY_STATUS.value);
            Class<?> dataWriterImplClass = Class.forName(topic.getTypeName()
                    + "DataWriterImpl");

            Class<?> partypes[] = new Class[2];
            partypes[1] = Long.TYPE;
            partypes[0] = topic.getType();

            write = dataWriterImplClass.getMethod("write", partypes);
            dispose = dataWriterImplClass.getMethod("dispose", partypes);
            writeParameters = new Object[2];
            writeParameters[1] = new Long(0);
        } catch (Exception e) {
            System.err.println("Could not create writer for: "
                    + topic.getName() + " " + e.getClass().getName() + "."
                    + e.getMessage());
            dataWriter = null;
        }

    }

    @Override
    public Publisher getParent() {
        return publisher;
    }

    @Override
    public DataWriterListener<TYPE> getListener() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setListener(DataWriterListener<TYPE> listener) {
        // TODO Auto-generated method stub

    }

    @Override
    public DataWriterQos getQos() {
        return qos;
    }

    @Override
    public void setQos(DataWriterQos qos) {
        if (dataWriter != null) {
            dataWriter.set_qos(((OpenspliceDataWriterQos) qos).getQos());
        }
    }

    @Override
    public void setQos(String qosLibraryName, String qosProfileName) {
        // TODO Auto-generated method stub
    }

    @Override
    public void enable() {
        if (dataWriter != null) {
            dataWriter.enable();
        }
    }

    @Override
    public StatusCondition<DataWriter<TYPE>> getStatusCondition() {
        // TODO Auto-generated method stub
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
        // dataWriter.close();
    }

    @Override
    public void retain() {
        // TODO Auto-generated method stub
    }

    @Override
    public Bootstrap getBootstrap() {
        return null;
    }

    @Override
    public Class<TYPE> getType() {
        return topic.getType();
    }

    @Override
    public <OTHER> DataWriter<OTHER> cast() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Topic<TYPE> getTopic() {
        return topic;
    }

    @Override
    public void waitForAcknowledgments(Duration maxWait)
            throws TimeoutException {
        // TODO Auto-generated method stub
    }

    @Override
    public void waitForAcknowledgments(long maxWait, TimeUnit unit)
            throws TimeoutException {
        // TODO Auto-generated method stub
    }

    @Override
    public LivelinessLostStatus<TYPE> getLivelinessLostStatus(
            LivelinessLostStatus<TYPE> status) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OfferedDeadlineMissedStatus<TYPE> getOfferedDeadlineMissedStatus(
            OfferedDeadlineMissedStatus<TYPE> status) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OfferedIncompatibleQosStatus<TYPE> getOfferedIncompatibleQosStatus(
            OfferedIncompatibleQosStatus<TYPE> status) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PublicationMatchedStatus<TYPE> getPublicationMatchedStatus(
            PublicationMatchedStatus<TYPE> status) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void assertLiveliness() {
        // TODO Auto-generated method stub

    }

    @Override
    public Collection<InstanceHandle> getMatchedSubscriptions(
            Collection<InstanceHandle> subscriptionHandles) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SubscriptionBuiltinTopicData getMatchedSubscriptionData(
            SubscriptionBuiltinTopicData subscriptionData,
            InstanceHandle subscriptionHandle) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public InstanceHandle registerInstance(TYPE instanceData)
            throws TimeoutException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public InstanceHandle registerInstance(TYPE instanceData,
            Time sourceTimestamp) throws TimeoutException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public InstanceHandle registerInstance(TYPE instanceData,
            long sourceTimestamp, TimeUnit unit) throws TimeoutException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void unregisterInstance(InstanceHandle handle)
            throws TimeoutException {
        // TODO Auto-generated method stub

    }

    @Override
    public void unregisterInstance(InstanceHandle handle, TYPE instanceData)
            throws TimeoutException {
        // TODO Auto-generated method stub

    }

    @Override
    public void unregisterInstance(InstanceHandle handle, TYPE instanceData,
            Time sourceTimestamp) throws TimeoutException {
        // TODO Auto-generated method stub

    }

    @Override
    public void unregisterInstance(InstanceHandle handle, TYPE instanceData,
            long sourceTimestamp, TimeUnit unit) throws TimeoutException {
        // TODO Auto-generated method stub

    }

    @Override
    public void write(TYPE instanceData) throws TimeoutException {
        writeParameters[0] = instanceData;
        try {
            Object result = write.invoke(dataWriter, writeParameters);
        } catch (Exception e) {
            System.err.println("Write failed " + e.getMessage());
        }
    }

    @Override
    public void write(TYPE instanceData, Time sourceTimestamp)
            throws TimeoutException {
        // TODO Auto-generated method stub

    }

    @Override
    public void write(TYPE instanceData, long sourceTimestamp, TimeUnit unit)
            throws TimeoutException {
        // TODO Auto-generated method stub

    }

    @Override
    public void write(TYPE instanceData, InstanceHandle handle)
            throws TimeoutException {
        // TODO Auto-generated method stub

    }

    @Override
    public void write(TYPE instanceData, InstanceHandle handle,
            Time sourceTimestamp) throws TimeoutException {
        // TODO Auto-generated method stub

    }

    @Override
    public void write(TYPE instanceData, InstanceHandle handle,
            long sourceTimestamp, TimeUnit unit) throws TimeoutException {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose(InstanceHandle instanceHandle) throws TimeoutException {
    }

    @Override
    public void dispose(InstanceHandle instanceHandle, TYPE instanceData)
            throws TimeoutException {
        writeParameters[0] = instanceData;
        try {
            dispose.invoke(dataWriter, writeParameters);
        } catch (Exception e) {
            System.err.println("Write failed " + e.getMessage());
        }
    }

    @Override
    public void dispose(InstanceHandle instanceHandle, TYPE instanceData,
            Time sourceTimestamp) throws TimeoutException {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose(InstanceHandle instanceHandle, TYPE instanceData,
            long sourceTimestamp, TimeUnit unit) throws TimeoutException {
        // TODO Auto-generated method stub

    }

    @Override
    public TYPE getKeyValue(TYPE keyHolder, InstanceHandle handle) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifiableInstanceHandle lookupInstance(
            ModifiableInstanceHandle handle, TYPE keyHolder) {
        // TODO Auto-generated method stub
        return null;
    }

}
