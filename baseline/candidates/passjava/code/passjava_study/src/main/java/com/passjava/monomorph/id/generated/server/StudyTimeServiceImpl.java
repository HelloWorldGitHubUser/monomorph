package com.passjava.monomorph.id.generated.server;

import io.grpc.stub.StreamObserver;
import com.passjava.monomorph.id.generated.proto.studytimeservice.*;
import com.passjava.monomorph.id.shared.server.LeaseManager;
import com.passjava.monomorph.id.shared.server.ServerObjectManager;
import com.passjava.monomorph.id.shared.RefactoredObjectID;
import com.passjava.monomorph.id.generated.helpers.ServiceRegistry;
import com.passjava.monomorph.id.generated.helpers.ClassIdRegistry;
import com.passjava.service.StudyTimeService;
import com.passjava.monomorph.dto.generated.client.R;
import com.passjava.monomorph.dto.generated.proto.r.RDTO;

import java.util.Objects;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.UUID;
import java.util.function.Supplier;

public class StudyTimeServiceImpl extends StudyTimeServiceServiceGrpc.StudyTimeServiceServiceImplBase
        implements ServerObjectManager {

    private final LeaseManager leaseManager;
    private final String serviceId;
    private final Supplier<StudyTimeService> instanceFactory;

    public static final String CLASS_ID = ClassIdRegistry.getClassId("StudyTimeService");

    public StudyTimeServiceImpl(LeaseManager leaseManager) {
        this(leaseManager, StudyTimeServiceImpl::createDefaultStudyTimeService);
    }

    public StudyTimeServiceImpl(LeaseManager leaseManager, Supplier<StudyTimeService> instanceFactory) {
        this.leaseManager = Objects.requireNonNull(leaseManager, "leaseManager");
        this.instanceFactory = Objects.requireNonNull(instanceFactory, "instanceFactory");
        this.serviceId = Objects.requireNonNull(ServiceRegistry.getServiceId(), "serviceId");
    }

    private static StudyTimeService createDefaultStudyTimeService() {
        try {
            ServiceLoader<StudyTimeService> loader = ServiceLoader.load(StudyTimeService.class);
            for (StudyTimeService implementation : loader) {
                return implementation;
            }

            String configuredImpl = System.getProperty("com.passjava.service.StudyTimeService.implementation");
            if (configuredImpl != null && !configuredImpl.trim().isEmpty()) {
                Class<?> implementationClass = Class.forName(configuredImpl.trim());
                if (!StudyTimeService.class.isAssignableFrom(implementationClass)) {
                    throw new IllegalArgumentException(
                            "Configured implementation does not implement StudyTimeService: "
                                    + implementationClass.getName());
                }
                return (StudyTimeService) implementationClass.getDeclaredConstructor().newInstance();
            }

            throw new IllegalStateException(
                    "No concrete implementation found for StudyTimeService. "
                            + "Provide an implementation via ServiceLoader or set the system property "
                            + "'com.passjava.service.StudyTimeService.implementation'.");
        } catch (ServiceConfigurationError e) {
            throw new IllegalStateException("Failed to load StudyTimeService implementation via ServiceLoader", e);
        } catch (IllegalStateException | IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to create StudyTimeService instance", e);
        }
    }

    @Override
    public void createObject(CreateObjectRequest request, StreamObserver<RefactoredObjectID> responseObserver) {
        try {
            String clientId = request.getClientID();
            StudyTimeService newInstance = Objects.requireNonNull(instanceFactory.get(),
                    "StudyTimeService factory returned null");
            RefactoredObjectID responseProto = toID(newInstance, clientId);
            responseObserver.onNext(responseProto);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public RefactoredObjectID toID(Object instance, String clientId) throws Exception {
        String instanceId = leaseManager.findInstanceIdForInstance(instance);
        if (instanceId == null) {
            instanceId = UUID.randomUUID().toString();
        }
        boolean registered = leaseManager.registerInstanceAndGrantLease(instanceId, CLASS_ID, instance, clientId);
        if (!registered) {
            throw new RuntimeException("Failed to register instance ID: " + instanceId);
        }
        return RefactoredObjectID.newBuilder()
                .setInstanceID(instanceId)
                .setClassID(CLASS_ID)
                .setServiceID(this.serviceId)
                .build();
    }

    @Override
    public RefactoredObjectID toID(Object instance) throws Exception {
        return toID(instance, serviceId);
    }

    @Override
    public StudyTimeService fromID(RefactoredObjectID id) throws Exception {
        if (id == null) {
            throw new IllegalArgumentException("RefactoredObjectID must not be null");
        }
        if (id.getClassID() == null || !id.getClassID().equals(CLASS_ID)) {
            throw new IllegalArgumentException("class ID mismatch: expected " + CLASS_ID + ", got " + id.getClassID());
        }
        if (id.getServiceID() == null || !id.getServiceID().equals(this.serviceId)) {
            throw new IllegalArgumentException(
                    "service ID mismatch: expected " + this.serviceId + ", got " + id.getServiceID());
        }
        if (id.getInstanceID() == null || id.getInstanceID().trim().isEmpty()) {
            throw new IllegalArgumentException("instance ID must not be null or empty");
        }
        StudyTimeService instance = (StudyTimeService) leaseManager.getInstance(id.getInstanceID());
        if (instance == null) {
            throw new IllegalArgumentException("No instance found for ID: " + id.getInstanceID());
        }
        return instance;
    }

    @Override
    public String getManagedClassId() {
        return CLASS_ID;
    }

    @Override
    public String getServiceId() {
        return serviceId;
    }

    @Override
    public void getMemberStudyTimeListTest(GetMemberStudyTimeListTestRequest request,
                                           StreamObserver<RDTO> responseObserver) {
        try {
            RefactoredObjectID instanceId = request.getRefactoredObjectId();
            StudyTimeService instance = fromID(instanceId);

            long id = request.getId();
            R result = instance.getMemberStudyTimeListTest(id);
            if (result == null) {
                throw new IllegalStateException("StudyTimeService.getMemberStudyTimeListTest returned null");
            }
            RDTO response = result.toDTO();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
