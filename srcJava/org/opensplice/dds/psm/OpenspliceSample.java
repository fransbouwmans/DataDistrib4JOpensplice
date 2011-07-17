package org.opensplice.dds.psm;

import org.omg.dds.core.Bootstrap;
import org.omg.dds.core.modifiable.ModifiableInstanceHandle;
import org.omg.dds.core.modifiable.ModifiableTime;
import org.omg.dds.sub.InstanceState;
import org.omg.dds.sub.Sample;
import org.omg.dds.sub.SampleState;
import org.omg.dds.sub.ViewState;

import DDS.NEW_VIEW_STATE;
import DDS.READ_SAMPLE_STATE;

public class OpenspliceSample<TYPE> implements Sample<TYPE> {

    private final TYPE value;
    private final SampleState sampleState;
    private final ViewState viewState;
    private final InstanceState instanceState;
    private final ModifiableTime time;
    private final int disposedGenerationCount;
    private final int noWritersGenerationCount;
    private final int sampleRank;
    private final int generationRank;
    private final int absoluteGenerationRank;

    private OpenspliceSample(final TYPE value,
            final SampleState sampleState,
            final ViewState viewState,
            final InstanceState instanceState,
            final ModifiableTime time,
            final int disposedGenerationCount,
            final int noWritersGenerationCount,
            final int sampleRank,
            final int generationRank,
            final int absoluteGenerationRank) {
        this.value = value;
        this.sampleState = sampleState;
        this.viewState = viewState;
        this.instanceState = instanceState;
        this.time = time;
        this.disposedGenerationCount = disposedGenerationCount;
        this.noWritersGenerationCount = noWritersGenerationCount;
        this.sampleRank = sampleRank;
        this.generationRank = generationRank;
        this.absoluteGenerationRank = absoluteGenerationRank;
    }

    public OpenspliceSample(DDS.SampleInfo sampleInfo, TYPE theValue) {
        value = theValue;

        if (sampleInfo.sample_state == READ_SAMPLE_STATE.value) {
            sampleState = SampleState.READ;
        } else {
            sampleState = SampleState.NOT_READ;
        }
        if (sampleInfo.view_state == NEW_VIEW_STATE.value) {
            viewState = ViewState.NEW;
        } else {
            viewState = ViewState.NOT_NEW;
        }
        switch (sampleInfo.instance_state) {
        case DDS.ALIVE_INSTANCE_STATE.value:
            instanceState = InstanceState.ALIVE;
            break;
        case DDS.NOT_ALIVE_INSTANCE_STATE.value:
            instanceState = InstanceState.NOT_ALIVE_DISPOSED;
            break;
        case DDS.NOT_ALIVE_NO_WRITERS_INSTANCE_STATE.value:
            instanceState = InstanceState.NOT_ALIVE_NO_WRITERS;
            break;
        default:
            instanceState = InstanceState.ALIVE;

        }
        time = new OpenspliceTime(sampleInfo.source_timestamp);
        disposedGenerationCount = sampleInfo.disposed_generation_count;
        noWritersGenerationCount = sampleInfo.no_writers_generation_count;
        sampleRank = sampleInfo.sample_rank;
        generationRank = sampleInfo.generation_rank;
        absoluteGenerationRank = sampleInfo.absolute_generation_rank;
    }

    @Override
    public Sample<TYPE> copyFrom(Sample<TYPE> other) {
        return null;
    }

    @Override
    public Sample<TYPE> finishModification() {
        return null;
    }

    @Override
    public Sample<TYPE> modify() {
        return null;
    }

    @Override
    public Bootstrap getBootstrap() {
        return null;
    }

    @Override
    public TYPE getData() {
        return value;
    }

    @Override
    public SampleState getSampleState() {
        return sampleState;
    }

    @Override
    public ViewState getViewState() {
        return viewState;
    }

    @Override
    public InstanceState getInstanceState() {
        return instanceState;
    }

    @Override
    public ModifiableTime getSourceTimestamp() {
        return time;
    }

    @Override
    public ModifiableInstanceHandle getInstanceHandle() {
        return null;
    }

    @Override
    public ModifiableInstanceHandle getPublicationHandle() {
        return null;
    }

    @Override
    public int getDisposedGenerationCount() {
        return disposedGenerationCount;
    }

    @Override
    public int getNoWritersGenerationCount() {
        return noWritersGenerationCount;
    }

    @Override
    public int getSampleRank() {
        return sampleRank;
    }

    @Override
    public int getGenerationRank() {
        return generationRank;
    }

    @Override
    public int getAbsoluteGenerationRank() {
        return absoluteGenerationRank;
    }

    public OpenspliceSample<TYPE> clone() {
        return new OpenspliceSample<TYPE>(
                value, sampleState, viewState,
                 instanceState, time, disposedGenerationCount,
                 noWritersGenerationCount, sampleRank,
                 generationRank, absoluteGenerationRank);
    }

    public static class SampleIterator<TYPE> implements Iterator<TYPE> {

        final private OpenspliceDataReader<TYPE> reader;
        private int index = 0;
        final private DDS.SampleInfoSeqHolder sampleInfoList;
        final private Object sampleDataList;
        final private TYPE[] list;

        public SampleIterator(OpenspliceDataReader<TYPE> theReader,
                Object dataList,
                TYPE[] thelist, DDS.SampleInfoSeqHolder theSampleInfoList) {
            reader = theReader;
            list = thelist;
            sampleDataList = dataList;
            sampleInfoList = theSampleInfoList;
        }

        @Override
        public boolean hasNext() {
            return list.length > index;
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        @Override
        public Sample<TYPE> next() {
            Sample<TYPE> sample = new OpenspliceSample<TYPE>(
                    sampleInfoList.value[index], list[index]);
            index++;
            return sample;
        }

        @Override
        public int nextIndex() {
            return ++index;
        }

        @Override
        public Sample<TYPE> previous() {
            index--;
            Sample<TYPE> sample = new OpenspliceSample<TYPE>(
                    sampleInfoList.value[index], list[index]);
            return sample;
        }

        @Override
        public int previousIndex() {
            return --index;
        }

        @Override
        public void returnLoan() {
            reader.returnLoan(sampleDataList, sampleInfoList);
        }

        @Override
        public void remove() {
            throw new RuntimeException("remove sample not supported");
        }

        @Override
        public void set(Sample<TYPE> o) {
            throw new RuntimeException("Set sample not supported");
        }

        @Override
        public void add(Sample<TYPE> o) {
            throw new RuntimeException("Add sample not supported");
        }

    }
}
