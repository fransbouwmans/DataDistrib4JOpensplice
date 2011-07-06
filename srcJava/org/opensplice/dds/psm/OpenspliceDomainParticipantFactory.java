package org.opensplice.dds.psm;

import java.util.Collection;

import org.omg.dds.core.Bootstrap;
import org.omg.dds.core.status.Status;
import org.omg.dds.domain.DomainParticipant;
import org.omg.dds.domain.DomainParticipantFactory;
import org.omg.dds.domain.DomainParticipantFactoryQos;
import org.omg.dds.domain.DomainParticipantListener;
import org.omg.dds.domain.DomainParticipantQos;

import DDS.DOMAIN_ID_DEFAULT;

public class OpenspliceDomainParticipantFactory extends DomainParticipantFactory {
    private static int myDomain = DOMAIN_ID_DEFAULT.value;

    final private Bootstrap bootstrap;

    public OpenspliceDomainParticipantFactory(Bootstrap thebootstrap) {
        bootstrap = thebootstrap;
    }

    @Override
    public Bootstrap getBootstrap() {
        return bootstrap;
    }

    @Override
    public DomainParticipant createParticipant() {
        return createParticipant(myDomain);
    }

    @Override
    public DomainParticipant createParticipant(int domainId) {
        DDS.DomainParticipantQosHolder dqos = new DDS.DomainParticipantQosHolder();
        DDS.DomainParticipantFactory.get_instance()
                .get_default_participant_qos(dqos);

        DDS.DomainParticipant participant = DDS.DomainParticipantFactory
                .get_instance()
                .create_participant(domainId, dqos.value, null,
                        DDS.STATUS_MASK_NONE.value);
        return new OpenspliceDomainParticipant(bootstrap, participant);
    }

    @Override
    public DomainParticipant createParticipant(int domainId,
            DomainParticipantQos qos, DomainParticipantListener listener,
            Collection<Class<? extends Status<?, ?>>> statuses) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public DomainParticipant createParticipant(int domainId,
            String qosLibraryName, String qosProfileName,
            DomainParticipantListener listener,
            Collection<Class<? extends Status<?, ?>>> statuses) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public DomainParticipant lookupParticipant(int domainId) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public DomainParticipantFactoryQos getQos() {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public void setQos(DomainParticipantFactoryQos qos) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public DomainParticipantQos getDefaultParticipantQos() {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public void setDefaultParticipantQos(DomainParticipantQos qos) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public void setDefaultParticipantQos(String qosLibraryName,
            String qosProfileName) {
        throw new RuntimeException("Not yet implemented");
    }
}
