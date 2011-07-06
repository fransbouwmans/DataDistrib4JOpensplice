package org.opensplice.dds.psm.qos.policy;

import org.omg.dds.core.Bootstrap;
import org.omg.dds.core.policy.PresentationQosPolicy;
import org.omg.dds.core.policy.modifiable.ModifiablePresentationQosPolicy;

public class OpensplicePresentationQosPolicy implements
        ModifiablePresentationQosPolicy {

    private DDS.PresentationQosPolicy policy;

    public OpensplicePresentationQosPolicy(
            DDS.PresentationQosPolicy thepolicy) {
        policy = thepolicy;
    }

    public DDS.PresentationQosPolicy getPolicy() {
        return policy;
    }

    @Override
    public AccessScopeKind getAccessScope() {
        switch (policy.access_scope.value()) {
        case DDS.PresentationQosPolicyAccessScopeKind._GROUP_PRESENTATION_QOS:
            return AccessScopeKind.GROUP;
        case DDS.PresentationQosPolicyAccessScopeKind._TOPIC_PRESENTATION_QOS:
            return AccessScopeKind.TOPIC;
        case DDS.PresentationQosPolicyAccessScopeKind._INSTANCE_PRESENTATION_QOS:
            return AccessScopeKind.INSTANCE;
        }
        return null;
    }

    @Override
    public boolean isCoherentAccess() {
        return policy.coherent_access;
    }

    @Override
    public boolean isOrderedAccess() {
        return policy.ordered_access;
    }

    @Override
    public org.omg.dds.core.policy.QosPolicy.Id getId() {
        return null;
    }

    @Override
    public ModifiablePresentationQosPolicy modify() {
        return this;
    }

    @Override
    public Bootstrap getBootstrap() {
        return null;
    }

    @Override
    public ModifiablePresentationQosPolicy copyFrom(
            PresentationQosPolicy other) {
        policy = ((OpensplicePresentationQosPolicy) other).getPolicy();
        return this;
    }

    @Override
    public PresentationQosPolicy finishModification() {
        return this;
    }

    @Override
    public ModifiablePresentationQosPolicy setAccessScope(
            AccessScopeKind accessScope) {
        if (AccessScopeKind.GROUP.equals(accessScope)) {
            policy.access_scope = DDS.PresentationQosPolicyAccessScopeKind.GROUP_PRESENTATION_QOS;
        } else if (AccessScopeKind.INSTANCE.equals(accessScope)) {
            policy.access_scope = DDS.PresentationQosPolicyAccessScopeKind.INSTANCE_PRESENTATION_QOS;
        } else if (AccessScopeKind.TOPIC.equals(accessScope)) {
            policy.access_scope = DDS.PresentationQosPolicyAccessScopeKind.TOPIC_PRESENTATION_QOS;
        }
        return this;
    }

    @Override
    public ModifiablePresentationQosPolicy setCoherentAccess(
            boolean coherentAccess) {
        policy.coherent_access = coherentAccess;
        return this;
    }

    @Override
    public ModifiablePresentationQosPolicy setOrderedAccess(
            boolean orderedAccess) {
        policy.ordered_access = orderedAccess;
        return this;
    }

    public OpensplicePresentationQosPolicy clone() {
        return new OpensplicePresentationQosPolicy(policy);
    }
}
