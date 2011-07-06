package org.opensplice.dds.psm;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.omg.dds.core.Bootstrap;
import org.omg.dds.core.Duration;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.StatusCondition;
import org.omg.dds.core.modifiable.ModifiableInstanceHandle;
import org.omg.dds.core.status.DataAvailableStatus;
import org.omg.dds.core.status.LivelinessChangedStatus;
import org.omg.dds.core.status.RequestedDeadlineMissedStatus;
import org.omg.dds.core.status.RequestedIncompatibleQosStatus;
import org.omg.dds.core.status.SampleLostStatus;
import org.omg.dds.core.status.SampleRejectedStatus;
import org.omg.dds.core.status.Status;
import org.omg.dds.core.status.SubscriptionMatchedStatus;
import org.omg.dds.sub.DataReader;
import org.omg.dds.sub.DataReaderListener;
import org.omg.dds.sub.DataReaderQos;
import org.omg.dds.sub.InstanceState;
import org.omg.dds.sub.QueryCondition;
import org.omg.dds.sub.ReadCondition;
import org.omg.dds.sub.Sample;
import org.omg.dds.sub.Sample.Iterator;
import org.omg.dds.sub.SampleState;
import org.omg.dds.sub.Subscriber;
import org.omg.dds.sub.ViewState;
import org.omg.dds.topic.PublicationBuiltinTopicData;
import org.omg.dds.topic.TopicDescription;

import DDS.ANY_STATUS;
import DDS.DataReaderQosHolder;
import DDS.DestinationOrderQosPolicyKind;
import DDS.Duration_t;

public class OpenspliceDataReader<TYPE> implements DataReader<TYPE> {

    final private OpenspliceTopic<TYPE> topic;
    final private OpenspliceSubscriber subscriber;
    final private Bootstrap bootstrap;
    private DataReaderListener<TYPE> listener = null;
    private DDS.DataReader dataReader = null;

    private Method take = null;
    private Method read = null;
    private Method return_loan = null;

    private Field listValue = null;
    private Field sampleValue = null;
    private Object[] takeParameters = null;
    private Object[] readParameters = null;
    private Object[] returnLoanParameters = null;
    private DDS.SampleInfoSeqHolder infoList;
    private Object dataList;
    private Class<?> listClass;

    private class OpenspliceDataAvailableStatus<TYPE> extends
            DataAvailableStatus<TYPE> {
        /** Default serialVersionUID. */
        private static final long serialVersionUID = 1L;

        private DataReader<TYPE> drsource;

        protected OpenspliceDataAvailableStatus(DataReader<TYPE> source) {
            super(source);
            drsource = source;
        }

        @Override
        public DataAvailableStatus<TYPE> copyFrom(
                DataAvailableStatus<TYPE> other) {
            drsource = other.getSource();
            return this;
        }

        @Override
        public DataAvailableStatus<TYPE> finishModification() {
            return null;
        }

        @Override
        public DataAvailableStatus<TYPE> modify() {
            return null;
        }

        @Override
        public Bootstrap getBootstrap() {
            return bootstrap;
        }

        @Override
        public DataReader<TYPE> getSource() {
            return drsource;
        }

        @Override
        public DataAvailableStatus<TYPE> clone() {
            return new OpenspliceDataAvailableStatus<TYPE>(drsource);
        }

    }

    private class OpenspliceLivelinessChangedStatus<TYPE> extends
            LivelinessChangedStatus<TYPE> {
        private DataReader<TYPE> drsource;
        private DDS.LivelinessChangedStatus status;

        protected OpenspliceLivelinessChangedStatus(DataReader<TYPE> source,
                DDS.LivelinessChangedStatus thestatus) {
            super(source);
            drsource = source;
            status = thestatus;
        }

        @Override
        public LivelinessChangedStatus<TYPE> copyFrom(
                LivelinessChangedStatus<TYPE> other) {
            drsource = other.getSource();
            status = ((OpenspliceLivelinessChangedStatus) other).status;
            return this;
        }

        @Override
        public LivelinessChangedStatus<TYPE> finishModification() {
            return null;
        }

        @Override
        public LivelinessChangedStatus<TYPE> modify() {
            return null;
        }

        @Override
        public Bootstrap getBootstrap() {
            return bootstrap;
        }

        @Override
        public int getAliveCount() {
            return status.alive_count;
        }

        @Override
        public int getNotAliveCount() {
            return status.not_alive_count;
        }

        @Override
        public int getAliveCountChange() {
            return status.alive_count_change;
        }

        @Override
        public int getNotAliveCountChange() {
            return status.not_alive_count_change;
        }

        @Override
        public ModifiableInstanceHandle getLastPublicationHandle() {
            return null;
        }

        @Override
        public DataReader<TYPE> getSource() {
            return drsource;
        }

        @Override
        public LivelinessChangedStatus<TYPE> clone() {
            return new OpenspliceLivelinessChangedStatus<TYPE>(drsource, status);
        }
    }

    private class MyDataReaderListener implements DDS.DataReaderListener {

        final private DataReaderListener<TYPE> listener;
        final private OpenspliceDataReader<TYPE> reader;

        public MyDataReaderListener(OpenspliceDataReader<TYPE> thereader,
                DataReaderListener<TYPE> thelistener) {
            reader = thereader;
            listener = thelistener;
        }

        @Override
        public void on_data_available(DDS.DataReader arg0) {
            DataAvailableStatus<TYPE> status =
                    new OpenspliceDataAvailableStatus(reader);
            listener.onDataAvailable(status);
        }

        @Override
        public void on_liveliness_changed(DDS.DataReader arg0,
                DDS.LivelinessChangedStatus arg1) {
            LivelinessChangedStatus<TYPE> status =
                    new OpenspliceLivelinessChangedStatus(reader, arg1);
            listener.onLivelinessChanged(status);
        }

        @Override
        public void on_requested_deadline_missed(DDS.DataReader arg0,
                DDS.RequestedDeadlineMissedStatus arg1) {
            // TODO implement status
            listener.onRequestedDeadlineMissed(null);
        }

        @Override
        public void on_requested_incompatible_qos(DDS.DataReader arg0,
                DDS.RequestedIncompatibleQosStatus arg1) {
            // TODO implement status
            listener.onRequestedIncompatibleQos(null);
        }

        @Override
        public void on_sample_lost(DDS.DataReader arg0,
                DDS.SampleLostStatus arg1) {
            // TODO implement status
            listener.onSampleLost(null);
        }

        @Override
        public void on_sample_rejected(DDS.DataReader arg0,
                DDS.SampleRejectedStatus arg1) {
            // TODO implement status
            listener.onSampleRejected(null);
        }

        @Override
        public void on_subscription_matched(DDS.DataReader arg0,
                DDS.SubscriptionMatchedStatus arg1) {
            // TODO implement status
            listener.onSubscriptionMatched(null);
        }

    }

    public OpenspliceDataReader(Bootstrap theBootstrap,
            TopicDescription<TYPE> theTopic, Subscriber theSubscriber) {
        bootstrap = theBootstrap;
        topic = (OpenspliceTopic<TYPE>) theTopic;
        subscriber = (OpenspliceSubscriber) theSubscriber;
        createReader();
    }

    private void createReader() {
        try {
            DataReaderQosHolder holder = new DataReaderQosHolder();
            subscriber.getSubscriber().get_default_datareader_qos(holder);
            DDS.DataReaderQos drQos = holder.value;
            drQos.destination_order.kind =
                    DestinationOrderQosPolicyKind.BY_SOURCE_TIMESTAMP_DESTINATIONORDER_QOS;
            drQos.latency_budget.duration.sec = 0;
            drQos.latency_budget.duration.nanosec = 40000000;

            dataReader = subscriber.getSubscriber().create_datareader(
                    topic.getTopic(), drQos, null, ANY_STATUS.value);

            DataReaderQosHolder drQosHolder = new DataReaderQosHolder();
            dataReader.get_qos(drQosHolder);

            listClass = Class.forName(topic.getTypeName() + "SeqHolder");
            Class partypes[] = new Class[6];
            partypes[0] = listClass;
            partypes[1] = DDS.SampleInfoSeqHolder.class;
            partypes[2] = Integer.TYPE;
            partypes[3] = Integer.TYPE;
            partypes[4] = Integer.TYPE;
            partypes[5] = Integer.TYPE;
            Class<?> dataReaderImplClass = Class.forName(topic.getTypeName()
                    + "DataReaderImpl");
            take = dataReaderImplClass.getMethod("take", partypes);
            read = dataReaderImplClass.getMethod("read", partypes);
            partypes = new Class[2];
            partypes[0] = listClass;
            partypes[1] = DDS.SampleInfoSeqHolder.class;
            return_loan = dataReaderImplClass.getMethod("return_loan",
                    partypes);

            infoList = new DDS.SampleInfoSeqHolder();
            dataList = listClass.newInstance();

            takeParameters = new Object[6];
            takeParameters[0] = dataList;
            takeParameters[1] = infoList;
            takeParameters[2] = new Integer(1000);
            takeParameters[3] = new Integer(DDS.ANY_SAMPLE_STATE.value);
            takeParameters[4] = new Integer(DDS.ANY_VIEW_STATE.value);
            takeParameters[5] = new Integer(DDS.ANY_INSTANCE_STATE.value);

            returnLoanParameters = new Object[2];
            returnLoanParameters[0] = dataList;
            returnLoanParameters[1] = infoList;

            listValue = listClass.getField("value");
            sampleValue = DDS.SampleInfoSeqHolder.class.getField("value");
        } catch (Exception e) {
            System.err.println("Could not create reader for: "
                    + topic.getName() + " " + e.getClass().getName() + "."
                    + e.getMessage());
            dataReader = null;
        }

    }

    @Override
    public Subscriber getParent() {
        return subscriber;
    }

    @Override
    public DataReaderListener<TYPE> getListener() {
        return listener;
    }

    @Override
    public void setListener(DataReaderListener<TYPE> thelistener) {
        listener = thelistener;
        if (thelistener == null) {
            dataReader.set_listener(null, 0);
        } else {
            MyDataReaderListener mylistener = new MyDataReaderListener(this,
                    thelistener);
            dataReader.set_listener(mylistener, DDS.ANY_STATUS.value);
        }
    }

    @Override
    public DataReaderQos getQos() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setQos(DataReaderQos qos) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setQos(String qosLibraryName, String qosProfileName) {
    }

    @Override
    public void enable() {

    }

    @Override
    public StatusCondition<DataReader<TYPE>> getStatusCondition() {
        return null;
    }

    @Override
    public Collection<Class<? extends Status<?, ?>>> getStatusChanges(
            Collection<Class<? extends Status<?, ?>>> statuses) {
        return null;
    }

    @Override
    public InstanceHandle getInstanceHandle() {
        return null;
    }

    @Override
    public void close() {
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
    public Class<TYPE> getType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <OTHER> DataReader<OTHER> cast() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ReadCondition<TYPE> createReadCondition() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ReadCondition<TYPE> createReadCondition(
            Collection<SampleState> sampleStates,
            Collection<ViewState> viewStates,
            Collection<InstanceState> instanceStates) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public QueryCondition<TYPE> createQueryCondition(String queryExpression,
            List<String> queryParameters) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public QueryCondition<TYPE> createQueryCondition(
            Collection<SampleState> sampleStates,
            Collection<ViewState> viewStates,
            Collection<InstanceState> instanceStates, String queryExpression,
            List<String> queryParameters) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void closeContainedEntities() {
        // TODO Auto-generated method stub

    }

    @Override
    public TopicDescription<TYPE> getTopicDescription() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SampleRejectedStatus<TYPE> getSampleRejectedStatus(
            SampleRejectedStatus<TYPE> status) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LivelinessChangedStatus<TYPE> getLivelinessChangedStatus(
            LivelinessChangedStatus<TYPE> status) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RequestedDeadlineMissedStatus<TYPE> getRequestedDeadlineMissedStatus(
            RequestedDeadlineMissedStatus<TYPE> status) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RequestedIncompatibleQosStatus<TYPE> getRequestedIncompatibleQosStatus(
            RequestedIncompatibleQosStatus<TYPE> status) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SubscriptionMatchedStatus<TYPE> getSubscriptionMatchedStatus(
            SubscriptionMatchedStatus<TYPE> status) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SampleLostStatus<TYPE> getSampleLostStatus(
            SampleLostStatus<TYPE> status) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void waitForHistoricalData(Duration maxWait) throws TimeoutException {
        // TODO Auto-generated method stub

    }

    @Override
    public void waitForHistoricalData(long maxWait, TimeUnit unit)
            throws TimeoutException {
        Duration_t waittime = new Duration_t(10, 0);
        long starttime = System.currentTimeMillis();
        int result = dataReader.wait_for_historical_data(waittime);
        long donetime = System.currentTimeMillis();

        // if (result != 0) {
        // logger.debug("Wait_for_historical_data failed: " + topic.get_name() +
        // " " + result + " " + (donetime - starttime));
        // } else {
        // logger.debug("Wait for historical succeeded: " + topic.get_name() +
        // " " + (donetime - starttime));
        // }
    }

    @Override
    public Collection<InstanceHandle> getMatchedPublications(
            Collection<InstanceHandle> publicationHandles) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PublicationBuiltinTopicData getMatchedPublicationData(
            PublicationBuiltinTopicData publicationData,
            InstanceHandle publicationHandle) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Sample<TYPE> createSample() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterator<TYPE> read() {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterator<TYPE> read(Collection<SampleState> sampleStates,
            Collection<ViewState> viewStates,
            Collection<InstanceState> instanceStates) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void read(List<Sample<TYPE>> samples) {
        // TODO Auto-generated method stub

    }

    @Override
    public void read(List<Sample<TYPE>> samples, int maxSamples,
            Collection<SampleState> sampleStates,
            Collection<ViewState> viewStates,
            Collection<InstanceState> instanceStates) {
        // TODO Auto-generated method stub

    }

    @Override
    public Iterator<TYPE> take() {
        Object data;
        try {
            data = listClass.newInstance();
            DDS.SampleInfoSeqHolder sampleInfoHolder = new DDS.SampleInfoSeqHolder();
            private_take_samples(data, sampleInfoHolder, 1000);
            TYPE[] list = (TYPE[]) listValue.get(data);
            OpenspliceSample.SampleIterator<TYPE> iterator =
                    new OpenspliceSample.SampleIterator<TYPE>(this, data, list,
                            sampleInfoHolder);
            return iterator;
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public Iterator<TYPE> take(Collection<SampleState> sampleStates,
            Collection<ViewState> viewStates,
            Collection<InstanceState> instanceStates) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void take(List<Sample<TYPE>> samples) {

        // TODO Auto-generated method stub

    }

    @Override
    public void take(List<Sample<TYPE>> samples, int maxSamples,
            Collection<SampleState> sampleStates,
            Collection<ViewState> viewStates,
            Collection<InstanceState> instanceStates) {
        // TODO Auto-generated method stub

    }

    @Override
    public Iterator<TYPE> read(ReadCondition<TYPE> condition) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void read(List<Sample<TYPE>> samples, ReadCondition<TYPE> condition) {
        // TODO Auto-generated method stub

    }

    @Override
    public void read(List<Sample<TYPE>> samples, int maxSamples,
            ReadCondition<TYPE> condition) {
        // TODO Auto-generated method stub

    }

    @Override
    public Iterator<TYPE> take(ReadCondition<TYPE> condition) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void take(List<Sample<TYPE>> samples, ReadCondition<TYPE> condition) {
        // TODO Auto-generated method stub

    }

    @Override
    public void take(List<Sample<TYPE>> samples, int maxSamples,
            ReadCondition<TYPE> condition) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean readNext(Sample<TYPE> sample) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean takeNext(Sample<TYPE> sample) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Iterator<TYPE> read(InstanceHandle handle) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterator<TYPE> read(InstanceHandle handle,
            Collection<SampleState> sampleStates,
            Collection<ViewState> viewStates,
            Collection<InstanceState> instanceStates) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void read(List<Sample<TYPE>> samples, InstanceHandle handle) {
        // TODO Auto-generated method stub

    }

    @Override
    public void read(List<Sample<TYPE>> samples, InstanceHandle handle,
            int maxSamples, Collection<SampleState> sampleStates,
            Collection<ViewState> viewStates,
            Collection<InstanceState> instanceStates) {
        // TODO Auto-generated method stub

    }

    @Override
    public Iterator<TYPE> take(InstanceHandle handle) {
        return null;
    }

    @Override
    public Iterator<TYPE> take(InstanceHandle handle,
            Collection<SampleState> sampleStates,
            Collection<ViewState> viewStates,
            Collection<InstanceState> instanceStates) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void take(List<Sample<TYPE>> samples, InstanceHandle handle) {
        // TODO Auto-generated method stub

    }

    @Override
    public void take(List<Sample<TYPE>> samples, InstanceHandle handle,
            int maxSamples, Collection<SampleState> sampleStates,
            Collection<ViewState> viewStates,
            Collection<InstanceState> instanceStates) {
        // TODO Auto-generated method stub

    }

    @Override
    public Iterator<TYPE> readNext(InstanceHandle previousHandle) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterator<TYPE> readNext(InstanceHandle previousHandle,
            Collection<SampleState> sampleStates,
            Collection<ViewState> viewStates,
            Collection<InstanceState> instanceStates) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void readNext(List<Sample<TYPE>> samples,
            InstanceHandle previousHandle) {
        // TODO Auto-generated method stub

    }

    @Override
    public void readNext(List<Sample<TYPE>> samples,
            InstanceHandle previousHandle, int maxSamples,
            Collection<SampleState> sampleStates,
            Collection<ViewState> viewStates,
            Collection<InstanceState> instanceStates) {
        // TODO Auto-generated method stub

    }

    @Override
    public Iterator<TYPE> takeNext(InstanceHandle previousHandle) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterator<TYPE> takeNext(InstanceHandle previousHandle,
            Collection<SampleState> sampleStates,
            Collection<ViewState> viewStates,
            Collection<InstanceState> instanceStates) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void takeNext(List<Sample<TYPE>> samples,
            InstanceHandle previousHandle) {
        // TODO Auto-generated method stub

    }

    @Override
    public void takeNext(List<Sample<TYPE>> samples,
            InstanceHandle previousHandle, int maxSamples,
            Collection<SampleState> sampleStates,
            Collection<ViewState> viewStates,
            Collection<InstanceState> instanceStates) {
        // TODO Auto-generated method stub

    }

    @Override
    public Iterator<TYPE> readNext(InstanceHandle previousHandle,
            ReadCondition<TYPE> condition) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void readNext(List<Sample<TYPE>> samples,
            InstanceHandle previousHandle, ReadCondition<TYPE> condition) {
        // TODO Auto-generated method stub

    }

    @Override
    public void readNext(List<Sample<TYPE>> samples,
            InstanceHandle previousHandle, int maxSamples,
            ReadCondition<TYPE> condition) {
        // TODO Auto-generated method stub

    }

    @Override
    public Iterator<TYPE> takeNext(InstanceHandle previousHandle,
            ReadCondition<TYPE> condition) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void takeNext(List<Sample<TYPE>> samples,
            InstanceHandle previousHandle, ReadCondition<TYPE> condition) {
        // TODO Auto-generated method stub

    }

    @Override
    public void takeNext(List<Sample<TYPE>> samples,
            InstanceHandle previousHandle, int maxSamples,
            ReadCondition<TYPE> condition) {
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

    private void private_take_samples(Object dataList,
            DDS.SampleInfoSeqHolder sampleInfo,
            int count) {
        try {
            takeParameters[0] = dataList;
            takeParameters[1] = sampleInfo;
            takeParameters[2] = Integer.valueOf(count);
            take.invoke(dataReader, takeParameters);
        } catch (InvocationTargetException ie) {
        } catch (IllegalAccessException iae) {
        }
    }

    private void private_take(List<TYPE> list, int count) {
        try {
            takeParameters[2] = Integer.valueOf(count);
            take.invoke(dataReader, takeParameters);
            TYPE[] slist = (TYPE[]) listValue.get(dataList);
            count = slist.length;
            if (count != 0) {
                for (int j = 0; j < count; j++) {
                    list.add(slist[j]);
                }
            }
            return_loan.invoke(dataReader, returnLoanParameters);
        } catch (InvocationTargetException ie) {

        } catch (IllegalAccessException iae) {

        }
    }

    public void returnLoan(Object dataList, DDS.SampleInfoSeqHolder infoList) {
        try {
            returnLoanParameters[0] = dataList;
            returnLoanParameters[0] = infoList;
            return_loan.invoke(dataReader, returnLoanParameters);
        } catch (InvocationTargetException ie) {

        } catch (IllegalAccessException iae) {

        }
    }

}
