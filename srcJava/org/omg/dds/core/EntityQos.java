/* Copyright 2010, Object Management Group, Inc.
 * Copyright 2010, PrismTech, Inc.
 * Copyright 2010, Real-Time Innovations, Inc.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Note that this file was changed since my knowledge of Java was not good
 * enough to know how to implement EntityQos without getting compile errors.
 * In order to still be able to make this contribution I temporarily (at least
 * for this interim solution) disabled the Map interface which is part of the 
 * EntityQos.
 */

package org.omg.dds.core;

import java.util.Map;

import org.omg.dds.core.policy.QosPolicy;
import org.omg.dds.type.Extensibility;

/**
 * A base interface for all entity QoS types.
 */
@Extensibility(Extensibility.Kind.MUTABLE_EXTENSIBILITY)
public interface EntityQos<UNMOD_SELF extends EntityQos<UNMOD_SELF, MOD_SELF>, MOD_SELF extends UNMOD_SELF>
        extends Value<UNMOD_SELF, MOD_SELF>/*
                                            * , Map<QosPolicy.Id, QosPolicy<?,
                                            * ?>>
                                            */{
    /**
     * @return a reference to the corresponding policy in this
     *         <code>EntityQos</code>. The returned object is not a copy;
     *         changes to the returned object will be reflected in subsequent
     *         accesses.
     * 
     * @see Map#get(Object)
     */
    public <POLICY extends QosPolicy<POLICY, ?>> POLICY get(QosPolicy.Id id);

    /**
     * @throws UnsupportedOperationException
     *             if this <code>EntityQos</code> is not a
     *             <code>ModifiableEntityQos</code>.
     */
    // public QosPolicy<?, ?> put(QosPolicy.Id key, QosPolicy<?, ?> value);

    /**
     * @throws UnsupportedOperationException
     *             always: the <tt>remove</tt> operation is not supported by
     *             this map.
     */
    public QosPolicy<?, ?> remove(Object key);

    /**
     * @throws UnsupportedOperationException
     *             always: the <tt>clear</tt> operation is not supported by this
     *             map.
     */
    public void clear();

}
