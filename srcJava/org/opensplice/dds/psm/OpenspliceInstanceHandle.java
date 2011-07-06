package org.opensplice.dds.psm;

import org.omg.dds.core.Bootstrap;
import org.omg.dds.core.InstanceHandle;
import org.omg.dds.core.modifiable.ModifiableInstanceHandle;

public class OpenspliceInstanceHandle extends ModifiableInstanceHandle {
    /** default serialVersionUID */
    private static final long serialVersionUID = 1L;

    private long handle = 0L;

    public OpenspliceInstanceHandle(long thehandle) {
        handle = thehandle;
    }

    public long getHandle() {
        return handle;
    }

    @Override
    public ModifiableInstanceHandle copyFrom(InstanceHandle other) {
        handle = ((OpenspliceInstanceHandle) other).getHandle();
        return this;
    }

    @Override
    public InstanceHandle finishModification() {
        return this;
    }

    @Override
    public ModifiableInstanceHandle modify() {
        return this;
    }

    @Override
    public Bootstrap getBootstrap() {
        return null;
    }

    @Override
    public ModifiableInstanceHandle clone() {
        return new OpenspliceInstanceHandle(handle);
    }

    @Override
    public boolean isNil() {
        return handle == 0;
    }
}
