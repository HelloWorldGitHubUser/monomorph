package com.lakesidemutual.monomorph.id.generated.client;

import com.lakesidemutual.monomorph.id.shared.client.AbstractRefactoredClient;
import com.lakesidemutual.monomorph.id.generated.helpers.ServiceRegistry;
import com.lakesidemutual.monomorph.id.shared.RefactoredObjectID;

import com.lakesidemutual.monomorph.id.generated.proto.customerservice.*;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.Optional;

import com.lakesidemutual.monomorph.dto.generated.client.CustomerProfileEntity;
import com.lakesidemutual.monomorph.dto.generated.client.CustomerId;
import com.lakesidemutual.monomorph.dto.generated.client.Address;
import com.lakesidemutual.monomorph.dto.generated.client.CustomerAggregateRoot;
import com.lakesidemutual.monomorph.dto.generated.client.Page;

public class CustomerService extends AbstractRefactoredClient {

    private static final String TARGET_SERVICE_ID = "customer_core";

    private ManagedChannel businessChannel;
    private CustomerServiceServiceGrpc.CustomerServiceServiceBlockingStub businessStub;

    /** Public no-argument constructor. */
    public CustomerService() {
        initialize();
    }

    /** Private constructor used by the fromID factory. */
    private CustomerService(RefactoredObjectID existingId) {
        super(existingId);
    }

    @Override
    protected void performRpcSetup() throws Exception {
        ServiceRegistry.ServiceEndpoint endpoint = ServiceRegistry.getEndpoint(TARGET_SERVICE_ID);
        this.businessChannel = ManagedChannelBuilder.forAddress(endpoint.getHost(), endpoint.getPort()).usePlaintext().build();
        this.businessStub = CustomerServiceServiceGrpc.newBlockingStub(businessChannel);
    }

    @Override
    protected RefactoredObjectID performRemoteCreateAndGetId(String clientId, Object... args) throws Exception {
        performRpcSetup();

        CreateObjectRequest.Builder requestBuilder = CreateObjectRequest.newBuilder()
                .setClientID(clientId)
                .setConstructorArgs(ConstructorArgs.newBuilder().build());

        RefactoredObjectID createResponseProto = this.businessStub.createObject(requestBuilder.build());
        return createResponseProto;
    }

    @Override
    protected void performSubclassRpcCleanup() {
        if (this.businessChannel != null && !this.businessChannel.isShutdown()) {
            try {
                this.businessChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
                if (!this.businessChannel.isTerminated()) {
                    this.businessChannel.shutdownNow();
                }
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

    @Override
    public static CustomerService fromID(RefactoredObjectID existingId) {
        return new CustomerService(existingId);
    }

    // --- Service Methods ---

    public CustomerAggregateRoot createCustomer(CustomerProfileEntity customerProfile) {
        CreateCustomerRequest request = CreateCustomerRequest.newBuilder()
                .setId(this.objectId)
                .setCustomerProfile(customerProfile.toDTO())
                .build();
        CreateCustomerResponse response = businessStub.createCustomer(request);
        return CustomerAggregateRoot.fromDTO(response.getCustomer());
    }

    public List<CustomerAggregateRoot> getCustomers(String ids) {
        GetCustomersRequest request = GetCustomersRequest.newBuilder()
                .setId(this.objectId)
                .setIds(ids)
                .build();
        GetCustomersResponse response = businessStub.getCustomers(request);
        return response.getCustomersList().stream()
                .map(CustomerAggregateRoot::fromDTO)
                .toList();
    }

    public Page<CustomerAggregateRoot> getCustomers(String filter, int limit, int offset) {
        GetCustomersByFilterRequest request = GetCustomersByFilterRequest.newBuilder()
                .setId(this.objectId)
                .setFilter(filter)
                .setLimit(limit)
                .setOffset(offset)
                .build();
        GetCustomersByFilterResponse response = businessStub.getCustomersByFilter(request);
        return Page.fromDTO(response.getPage());
    }

    public Optional<CustomerAggregateRoot> updateAddress(CustomerId customerId, Address updatedAddress) {
        UpdateAddressRequest request = UpdateAddressRequest.newBuilder()
                .setId(this.objectId)
                .setCustomerId(customerId.toDTO())
                .setUpdatedAddress(updatedAddress.toDTO())
                .build();
        UpdateAddressResponse response = businessStub.updateAddress(request);
        if (response.hasCustomer()) {
            return Optional.of(CustomerAggregateRoot.fromDTO(response.getCustomer()));
        } else {
            return Optional.empty();
        }
    }

    public Optional<CustomerAggregateRoot> updateCustomerProfile(CustomerId customerId, CustomerProfileEntity updatedCustomerProfile) {
        UpdateCustomerProfileRequest request = UpdateCustomerProfileRequest.newBuilder()
                .setId(this.objectId)
                .setCustomerId(customerId.toDTO())
                .setUpdatedCustomerProfile(updatedCustomerProfile.toDTO())
                .build();
        UpdateCustomerProfileResponse response = businessStub.updateCustomerProfile(request);
        if (response.hasCustomer()) {
            return Optional.of(CustomerAggregateRoot.fromDTO(response.getCustomer()));
        } else {
            return Optional.empty();
        }
    }
}