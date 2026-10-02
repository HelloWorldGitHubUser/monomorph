

package com.passjava.monomorph.id.generated.helpers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * ServiceRegistry
 * 
 * This class provides a registry for service endpoints, allowing for dynamic resolution of service addresses.
 * It supports environment variable overrides for host and port configuration.
 * Should be replaced with a more robust service discovery mechanism in the future.
 * 
 * Usage:
 *      Use `ServiceRegistry.getEndpoint(serviceName)` to retrieve the endpoint for a specific service.
 */
public final class ServiceRegistry { 

    private static final String SERVICE_ID = getThisServiceId(); // The ID for this service

    public static final class ServiceEndpoint {
        private final String host;
        private final int port;

        public ServiceEndpoint(String host, int port) {
            this.host = host;
            this.port = port;
        }

        public String getHost() {
            return host;
        }

        public int getPort() {
            return port;
        }

        @Override
        public String toString() {
            return "ServiceEndpoint{" +
                   "host='" + host + '\'' +
                   ", port=" + port +
                   '}';
        }
    }

    private static final Map<String, ServiceEndpoint> registry = new ConcurrentHashMap<String, ServiceEndpoint>();
    private static final Object initLock = new Object();
    private static volatile boolean initialized = false;

    public static void initialize() {
        if (initialized) return;
        synchronized (initLock) {
            if (initialized) return;
            // --- Register known services ---
            
            registerServiceInternal("passjava_search", "localhost", "50055");
            
            registerServiceInternal("passjava_study", "localhost", "50056");
            
            // --- End of known services ---

            initialized = true;
        }
    }

    private static void registerServiceInternal(String serviceName, String defaultHost, String defaultPortStr) {
        String envHostKey = serviceName.toUpperCase() + "_HOST";
        String envPortKey = serviceName.toUpperCase() + "_PORT";

        String host = System.getenv(envHostKey);
        String portStr = System.getenv(envPortKey);

        if (host == null || host.trim().isEmpty()) {
            host = defaultHost;
        }

        int port;
        if (portStr == null || portStr.trim().isEmpty()) {
            port = Integer.parseInt(defaultPortStr);
        } else {
            try {
                port = Integer.parseInt(portStr);
            } catch (NumberFormatException e) {
                port = Integer.parseInt(defaultPortStr);
            }
        }
        registry.put(serviceName, new ServiceEndpoint(host, port));
    }

    public static ServiceEndpoint getEndpoint(String serviceName) {
        if (!initialized) {
            initialize();
        }
        ServiceEndpoint endpoint = registry.get(serviceName);
        if (endpoint == null) {
            throw new IllegalArgumentException("Service '" + serviceName + "' not found in registry.");
        }
        return endpoint;
    }

    private ServiceRegistry() { // Prevent instantiation
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String getThisServiceId() {
        String thisServiceId = System.getenv("PASSJAVA_MEMBER_SID");
        if (thisServiceId == null || thisServiceId.trim().isEmpty()) {
            thisServiceId = "passjava_member";
        }
        return thisServiceId;
    }

    public static String getServiceId() {
        return SERVICE_ID;
    }
}