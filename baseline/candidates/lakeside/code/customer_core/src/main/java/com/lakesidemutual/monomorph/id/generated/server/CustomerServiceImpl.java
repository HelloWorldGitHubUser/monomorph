package com.lakesidemutual.monomorph.id.generated.server;

import io.grpc.stub.StreamObserver;
import com.lakesidemutual.monomorph.id.generated.proto.customerservice.*;
import com.lakesidemutual.monomorph.id.shared.RefactoredObjectID;
import com.lakesidemutual.monomorph.id.shared.server.LeaseManager;
import com.lakesidemutual.monomorph.id.shared.server.ServerObjectManager;
import com.lakesidemutual.monomorph.id.generated.helpers.ServiceRegistry;
import com.lakesidemutual.monomorph.id.generated.helpers.ClassIdRegistry;
import com.lakesidemutual.application.CustomerService;
import com.lakesidemutual.application.Page;
import com.lakesidemutual.domain.customer.Address;
import com.lakesidemutual.domain.customer.CustomerAggregateRoot;
import com.lakesidemutual.domain.customer.CustomerId;
import com.lakesidemutual.domain.customer.CustomerProfileEntity;

// DTOs
import com.lakesidemutual.monomorph.dto.generated.proto.customerprofileentity.CustomerProfileEntityDTO;
import com.lakesidemutual.monomorph.dto.generated.proto.address.AddressDTO;
import com.lakesidemutual.monomorph.dto.generated.proto.page.PageDTO;
import com.lakesidemutual.monomorph.dto.generated.proto.customeraggregateroot.CustomerAggregateRootDTO;
import com.lakesidemutual.monomorph.dto.generated.proto.customerid.CustomerIdDTO;

// Mappers
import com.lakesidemutual.monomorph.dto.generated.mappers.CustomerProfileEntityMapper;
import com.lakesidemutual.monomorph.dto.generated.mappers.AddressMapper;
import com.lakesidemutual.monomorph.dto.generated.mappers.PageMapper;
import com.lakesidemutual.monomorph.dto.generated.mappers.CustomerAggregateRootMapper;
import com.lakesidemutual.monomorph.dto.generated.mappers.CustomerIdMapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * gRPC Service implementation for CustomerService.
 * - Handles gRPC requests for CustomerService API.
 * - Creates transient CustomerService instances.
 * - Interacts with LeaseManager for instance registration/retrieval.
 * - Calls business methods on retrieved CustomerService instances.
 */
public class CustomerServiceImpl extends CustomerServiceServiceGrpc.CustomerServiceServiceImplBase implements ServerObjectManager {

    private final LeaseManager leaseManager;
    private final String serviceId;

    public static final String CLASS_ID = ClassIdRegistry.getClassId("CustomerService");

    public CustomerServiceImpl(LeaseManager leaseManager) {
        this.leaseManager = Objects.requireNonNull(leaseManager, "leaseManager");
        this.serviceId = Objects.requireNonNull(ServiceRegistry.getServiceId(), "serviceId");
    }

    // --- createObject gRPC Method Implementation ---

    @Override
    public void createObject(CreateObjectRequest request, StreamObserver<RefactoredObjectID> responseObserver) {
        try {
            String clientId = request.getClientID();
            // ConstructorArgs is empty because CustomerService has no explicit constructor arguments.
            CustomerService newInstance = new CustomerService();

            RefactoredObjectID responseProto = toID(newInstance, clientId);
            responseObserver.onNext(responseProto);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void createCustomer(CreateCustomerRequest request, StreamObserver<CreateCustomerResponse> responseObserver) {
        try {
            CustomerService instance = fromID(request.getId());

            CustomerProfileEntityDTO profileDTO = request.getCustomerProfile();
            CustomerProfileEntity profile = CustomerProfileEntityMapper.INSTANCE.fromDTO(profileDTO);

            CustomerAggregateRoot customer = instance.createCustomer(profile);
            CustomerAggregateRootDTO customerDTO = CustomerAggregateRootMapper.INSTANCE.toDTO(customer);

            CreateCustomerResponse response = CreateCustomerResponse.newBuilder()
                    .setCustomer(customerDTO)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getCustomers(GetCustomersRequest request, StreamObserver<GetCustomersResponse> responseObserver) {
        try {
            CustomerService instance = fromID(request.getId());
            List<CustomerAggregateRoot> customers = instance.getCustomers(request.getIds());

            GetCustomersResponse.Builder responseBuilder = GetCustomersResponse.newBuilder();
            for (CustomerAggregateRoot customer : customers) {
                responseBuilder.addCustomers(CustomerAggregateRootMapper.INSTANCE.toDTO(customer));
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getCustomersByFilter(GetCustomersByFilterRequest request, StreamObserver<GetCustomersByFilterResponse> responseObserver) {
        try {
            CustomerService instance = fromID(request.getId());
            Page<CustomerAggregateRoot> page = instance.getCustomers(
                    request.getFilter(),
                    request.getLimit(),
                    request.getOffset()
            );

            PageDTO pageDTO = PageMapper.INSTANCE.toDTO(page);
            GetCustomersByFilterResponse response = GetCustomersByFilterResponse.newBuilder()
                    .setPage(pageDTO)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void updateAddress(UpdateAddressRequest request, StreamObserver<UpdateAddressResponse> responseObserver) {
        try {
            CustomerService instance = fromID(request.getId());

            CustomerIdDTO customerIdDTO = request.getCustomerId();
            CustomerId customerId = CustomerIdMapper.INSTANCE.fromDTO(customerIdDTO);

            AddressDTO addressDTO = request.getUpdatedAddress();
            Address updatedAddress = AddressMapper.INSTANCE.fromDTO(addressDTO);

            Optional<CustomerAggregateRoot> optCustomer = instance.updateAddress(customerId, updatedAddress);

            UpdateAddressResponse.Builder responseBuilder = UpdateAddressResponse.newBuilder();
            if (optCustomer.isPresent()) {
                CustomerAggregateRootDTO customerDTO = CustomerAggregateRootMapper.INSTANCE.toDTO(optCustomer.get());
                responseBuilder.setCustomer(customerDTO);
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void updateCustomerProfile(UpdateCustomerProfileRequest request, StreamObserver<UpdateCustomerProfileResponse> responseObserver) {
        try {
            CustomerService instance = fromID(request.getId());

            CustomerIdDTO customerIdDTO = request.getCustomerId();
            CustomerId customerId = CustomerIdMapper.INSTANCE.fromDTO(customerIdDTO);

            CustomerProfileEntityDTO profileDTO = request.getUpdatedCustomerProfile();
            CustomerProfileEntity updatedProfile = CustomerProfileEntityMapper.INSTANCE.fromDTO(profileDTO);

            Optional<CustomerAggregateRoot> optCustomer = instance.updateCustomerProfile(customerId, updatedProfile);

            UpdateCustomerProfileResponse.Builder responseBuilder = UpdateCustomerProfileResponse.newBuilder();
            if (optCustomer.isPresent()) {
                CustomerAggregateRootDTO customerDTO = CustomerAggregateRootMapper.INSTANCE.toDTO(optCustomer.get());
                responseBuilder.setCustomer(customerDTO);
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    // --- ServerObjectManager method implementations ---

    @Override
    public RefactoredObjectID toID(Object instance, String clientId) throws Exception {
        String instanceId = leaseManager.findInstanceIdForInstance(instance);
        if (instanceId == null) {
            instanceId = UUID.randomUUID().toString();
        }

        boolean registered = leaseManager.registerInstanceAndGrantLease(instanceId, CLASS_ID, instance, clientId);
        if (!registered) {
            throw new RuntimeException("Failed to register new instance ID: " + instanceId);
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
    public CustomerService fromID(RefactoredObjectID id) throws Exception {
        if (id.getClassID() == null || !id.getClassID().equals(CLASS_ID)) {
            throw new IllegalArgumentException(
                    "class ID mismatch: expected " + CLASS_ID + ", got " + id.getClassID()
            );
        }

        CustomerService instance = (CustomerService) leaseManager.getInstance(id.getInstanceID());
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
}