package com.lakesidemutual.monomorph.id.generated.client;

import com.lakesidemutual.monomorph.id.shared.client.AbstractRefactoredClient;
import com.lakesidemutual.monomorph.id.generated.helpers.ServiceRegistry;
import com.lakesidemutual.monomorph.id.shared.RefactoredObjectID;

// gRPC imports
import com.lakesidemutual.monomorph.id.generated.proto.customerservice.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

// DTO proxy imports
import com.lakesidemutual.monomorph.dto.generated.client.CustomerProfileEntity;
import com.lakesidemutual.monomorph.dto.generated.client.Address;
import com.lakesidemutual.monomorph.dto.generated.client.Page;
import com.lakesidemutual.monomorph.dto.generated.client.CustomerAggregateRoot;
import com.lakesidemutual.monomorph.dto.generated.client.CustomerId;

// Proto DTO imports
import com.lakesidemutual.monomorph.dto.generated.proto.customerprofileentity.CustomerProfileEntityDTO;
import com.lakesidemutual.monomorph.dto.generated.proto.address.AddressDTO;
import com.lakesidemutual.monomorph.dto.generated.proto.page.PageDTO;
import com.lakesidemutual.monomorph.dto.generated.proto.customeraggregateroot.CustomerAggregateRootDTO;
import com.lakesidemutual.monomorph.dto.generated.proto.customerid.CustomerIdDTO;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class CustomerService extends AbstractRefactoredClient {

    private static final String TARGET_SERVICE_ID = "customer_core";

    // --- gRPC Specific Fields ---
    private ManagedChannel businessChannel;
    private CustomerServiceServiceGrpc.CustomerServiceServiceBlockingStub businessStub;

    /** Public no-arg constructor (original class had no explicit constructor). */
    public CustomerService() {
        initialize(); // no constructor arguments
    }

    /** Private constructor used by the fromID factory. */
    private CustomerService(RefactoredObjectID existingId) {
        super(existingId);
    }

    // --- Implementation of Abstract Methods ---

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
        performRpcSetup(); // ensure channel is ready before creating request

        // Build CreateObjectRequest
        CreateObjectRequest createRequest = CreateObjectRequest.newBuilder()
                .setClientID(clientId)
                .setConstructorArgs(ConstructorArgs.newBuilder().build()) // empty, as no constructor args
                .build();

        RefactoredObjectID createResponseProto = this.businessStub.createObject(createRequest);
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

    /** Factory method for creating proxy from an EXISTING ID. */
    public static CustomerService fromID(RefactoredObjectID existingId) {
        return new CustomerService(existingId);
    }

    // --- Service Methods (only those defined in the proto service) ---

    public CustomerAggregateRoot createCustomer(CustomerProfileEntity customerProfile) {
        ensureRpcSetup();
        CustomerProfileEntityDTO profileDTO = customerProfile.toDTO();
        CreateCustomerRequest request = CreateCustomerRequest.newBuilder()
                .setId(this.objectId)
                .setCustomerProfile(profileDTO)
                .build();
        CreateCustomerResponse response = this.businessStub.createCustomer(request);
        return CustomerAggregateRoot.fromDTO(response.getCustomer());
    }

    public List<CustomerAggregateRoot> getCustomers(String ids) {
        ensureRpcSetup();
        GetCustomersRequest request = GetCustomersRequest.newBuilder()
                .setId(this.objectId)
                .setIds(ids)
                .build();
        GetCustomersResponse response = this.businessStub.getCustomers(request);
        List<CustomerAggregateRoot> customers = new ArrayList<>();
        for (CustomerAggregateRootDTO dto : response.getCustomersList()) {
            customers.add(CustomerAggregateRoot.fromDTO(dto));
        }
        return customers;
    }

    public Page getCustomers(String filter, int limit, int offset) {
        ensureRpcSetup();
        GetCustomersByFilterRequest request = GetCustomersByFilterRequest.newBuilder()
                .setId(this.objectId)
                .setFilter(filter)
                .setLimit(limit)
                .setOffset(offset)
                .build();
        GetCustomersByFilterResponse response = this.businessStub.getCustomersByFilter(request);
        return Page.fromDTO(response.getPage());
    }

    public Optional<CustomerAggregateRoot> updateAddress(CustomerId customerId, Address updatedAddress) {
        ensureRpcSetup();
        UpdateAddressRequest request = UpdateAddressRequest.newBuilder()
                .setId(this.objectId)
                .setCustomerId(customerId.toDTO())
                .setUpdatedAddress(updatedAddress.toDTO())
                .build();
        UpdateAddressResponse response = this.businessStub.updateAddress(request);
        if (response.hasCustomer()) {
            return Optional.of(CustomerAggregateRoot.fromDTO(response.getCustomer()));
        } else {
            return Optional.empty();
        }
    }

    public Optional<CustomerAggregateRoot> updateCustomerProfile(CustomerId customerId,
                                                                 CustomerProfileEntity updatedCustomerProfile) {
        ensureRpcSetup();
        UpdateCustomerProfileRequest request = UpdateCustomerProfileRequest.newBuilder()
                .setId(this.objectId)
                .setCustomerId(customerId.toDTO())
                .setUpdatedCustomerProfile(updatedCustomerProfile.toDTO())
                .build();
        UpdateCustomerProfileResponse response = this.businessStub.updateCustomerProfile(request);
        if (response.hasCustomer()) {
            return Optional.of(CustomerAggregateRoot.fromDTO(response.getCustomer()));
        } else {
            return Optional.empty();
        }
    }

    // --- Helper Methods ---

    /**
     * Ensures the gRPC stub is set up. Called before every RPC to handle cases
     * where the channel was shut down or never initialised (e.g., fromID instances).
     */
    private void ensureRpcSetup() {
        if (this.businessStub == null || this.businessChannel == null || this.businessChannel.isShutdown()) {
            try {
                performRpcSetup();
            } catch (Exception e) {
                throw new RuntimeException("Failed to setup gRPC channel", e);
            }
        }
    }
}