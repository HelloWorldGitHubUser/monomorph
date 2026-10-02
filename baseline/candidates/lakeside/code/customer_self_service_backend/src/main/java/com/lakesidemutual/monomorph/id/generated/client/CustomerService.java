package com.lakesidemutual.monomorph.id.generated.client;

import com.lakesidemutual.monomorph.id.shared.client.AbstractRefactoredClient;
import com.lakesidemutual.monomorph.id.generated.helpers.ServiceRegistry;
import com.lakesidemutual.monomorph.id.shared.RefactoredObjectID;
import com.lakesidemutual.monomorph.id.generated.proto.customerservice.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.lakesidemutual.monomorph.dto.generated.client.Address;
import com.lakesidemutual.monomorph.dto.generated.client.CustomerAggregateRoot;
import com.lakesidemutual.monomorph.dto.generated.client.CustomerId;
import com.lakesidemutual.monomorph.dto.generated.client.CustomerProfileEntity;
import com.lakesidemutual.monomorph.dto.generated.client.Page;
import com.lakesidemutual.monomorph.dto.generated.proto.customeraggregateroot.CustomerAggregateRootDTO;

public class CustomerService extends AbstractRefactoredClient {

    private static final String TARGET_SERVICE_ID = "customer_core";

    private ManagedChannel businessChannel;
    private CustomerServiceServiceGrpc.CustomerServiceServiceBlockingStub businessStub;

    /** Public constructor matching the original default no-arg constructor. */
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
        this.businessChannel = ManagedChannelBuilder.forAddress(endpoint.getHost(), endpoint.getPort())
                .usePlaintext()
                .build();
        this.businessStub = CustomerServiceServiceGrpc.newBlockingStub(businessChannel);
    }

    @Override
    protected RefactoredObjectID performRemoteCreateAndGetId(String clientId, Object... args) throws Exception {
        if (businessStub == null) {
            performRpcSetup();
        }

        CreateObjectRequest createRequest = CreateObjectRequest.newBuilder()
                .setClientID(clientId)
                .setConstructorArgs(ConstructorArgs.newBuilder().build())
                .build();

        return this.businessStub.createObject(createRequest);
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
                Thread.currentThread().interrupt();
            }
        }
    }

    public static CustomerService fromID(RefactoredObjectID existingId) {
        return new CustomerService(existingId);
    }

    private CustomerServiceServiceGrpc.CustomerServiceServiceBlockingStub getBusinessStub() {
        if (businessStub == null) {
            try {
                performRpcSetup();
            } catch (Exception e) {
                throw new IllegalStateException("Failed to initialize gRPC stub", e);
            }
        }
        return businessStub;
    }

    public CustomerAggregateRoot createCustomer(CustomerProfileEntity customerProfile) {
        CreateCustomerRequest request = CreateCustomerRequest.newBuilder()
                .setId(this.objectId)
                .setCustomerProfile(customerProfile.toDTO())
                .build();

        return CustomerAggregateRoot.fromDTO(
                getBusinessStub().createCustomer(request).getCustomer()
        );
    }

    public List<CustomerAggregateRoot> getCustomers(String ids) {
        GetCustomersRequest request = GetCustomersRequest.newBuilder()
                .setId(this.objectId)
                .setIds(ids)
                .build();

        List<CustomerAggregateRoot> customers = new ArrayList<>();
        for (CustomerAggregateRootDTO dto : getBusinessStub().getCustomers(request).getCustomersList()) {
            customers.add(CustomerAggregateRoot.fromDTO(dto));
        }
        return customers;
    }

    public Page<CustomerAggregateRoot> getCustomers(String filter, int limit, int offset) {
        GetCustomersByFilterRequest request = GetCustomersByFilterRequest.newBuilder()
                .setId(this.objectId)
                .setFilter(filter)
                .setLimit(limit)
                .setOffset(offset)
                .build();

        return Page.fromDTO(
                getBusinessStub().getCustomersByFilter(request).getPage()
        );
    }

    public Optional<CustomerAggregateRoot> updateAddress(CustomerId customerId, Address updatedAddress) {
        UpdateAddressRequest request = UpdateAddressRequest.newBuilder()
                .setId(this.objectId)
                .setCustomerId(customerId.toDTO())
                .setUpdatedAddress(updatedAddress.toDTO())
                .build();

        UpdateAddressResponse response = getBusinessStub().updateAddress(request);
        if (response.hasCustomer()) {
            return Optional.of(CustomerAggregateRoot.fromDTO(response.getCustomer()));
        }
        return Optional.empty();
    }

    public Optional<CustomerAggregateRoot> updateCustomerProfile(CustomerId customerId,
                                                                 CustomerProfileEntity updatedCustomerProfile) {
        UpdateCustomerProfileRequest request = UpdateCustomerProfileRequest.newBuilder()
                .setId(this.objectId)
                .setCustomerId(customerId.toDTO())
                .setUpdatedCustomerProfile(updatedCustomerProfile.toDTO())
                .build();

        UpdateCustomerProfileResponse response = getBusinessStub().updateCustomerProfile(request);
        if (response.hasCustomer()) {
            return Optional.of(CustomerAggregateRoot.fromDTO(response.getCustomer()));
        }
        return Optional.empty();
    }
}