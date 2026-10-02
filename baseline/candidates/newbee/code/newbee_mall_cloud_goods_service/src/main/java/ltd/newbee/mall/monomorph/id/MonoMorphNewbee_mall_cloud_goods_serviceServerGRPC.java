package ltd.newbee.mall.monomorph.id;

// gRPC imports
import io.grpc.Server;
import io.grpc.ServerBuilder;

// Service implementation imports


// Leasing imports
import ltd.newbee.mall.monomorph.id.shared.server.LeaseManager;
import ltd.newbee.mall.monomorph.id.shared.server.CaffeineLeaseManager;
import ltd.newbee.mall.monomorph.id.shared.server.LeasingServiceImpl;

// Helper imports
import ltd.newbee.mall.monomorph.id.generated.helpers.IDMapper;

// Java imports
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Generated gRPC Server MonoMorphNewbee_mall_cloud_goods_serviceServerGRPC.
 * Hosts the leasing service and the following services:
 * 
 */
public class MonoMorphNewbee_mall_cloud_goods_serviceServerGRPC {

    private Server server;
    private final int port;
    private final LeaseManager leaseManager; // Use interface type
    private final ScheduledExecutorService leaseScheduler; // Scheduler instance

    public MonoMorphNewbee_mall_cloud_goods_serviceServerGRPC(int port) throws Exception {
        this.port = port;
        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(port);

        // 1. Create the Shared Scheduler for Leasing
        this.leaseScheduler = createLeaseScheduler();

        // 2. Create the LeaseManager Implementation
        this.leaseManager = createLeaseManager(this.leaseScheduler);

        // 3. Add the leasing service
        serverBuilder.addService(new LeasingServiceImpl(this.leaseManager));

        // 4. Register all known proxies
        IDMapper.registerProxies();

        // 5. Add service implementations
        

        this.server = serverBuilder.build();
    }

    /**
     * Creates and configures the shared ScheduledExecutorService for the LeaseManager.
     *
     * @return A configured ScheduledExecutorService instance.
     */
    private ScheduledExecutorService createLeaseScheduler() {
        // Use a ThreadFactory for naming and daemon status
        ThreadFactory leaseSchedulerThreadFactory = new ThreadFactory() {
            private final AtomicInteger threadNumber = new AtomicInteger(1);
            private final String namePrefix = "lease-manager-scheduler-";

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r, namePrefix + threadNumber.getAndIncrement());
                t.setDaemon(true); // Allow JVM exit even if this thread runs
                return t;
            }
        };
        return Executors.newSingleThreadScheduledExecutor(leaseSchedulerThreadFactory);
    }

    /**
     * Creates a LeaseManager instance.
     * This method should be replaced with the actual implementation.
     * @return A new LeaseManager instance.
     */
    private LeaseManager createLeaseManager(ScheduledExecutorService scheduler) {
        // 1. Determine lease duration from environment or default
        long defaultDuration = 60000;
        String durationEnvValue = System.getenv("MM_LEASE_DURATION");
        long leaseDuration = defaultDuration;

        if (durationEnvValue != null && !durationEnvValue.isEmpty()) {
            try {
                long envDuration = Long.parseLong(durationEnvValue);
                if (envDuration > 0) {
                    leaseDuration = envDuration;
                }
            } catch (NumberFormatException e) {
            }
        }
        // 2. Instantiate the concrete class
        final CaffeineLeaseManager caffeineLeaseManager = new CaffeineLeaseManager(
            leaseDuration,
            leaseScheduler
        );
        return caffeineLeaseManager;
    }

    /**
     * Starts the gRPC server.
     * @throws IOException if unable to bind to the port.
     */
    public void start() throws IOException {
        server.start();
        System.out.println("Microservice 'newbee_mall_cloud_goods_service' Server started, listening on " + port);

        // Add a shutdown hook to gracefully terminate the server
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** Shutting down gRPC server since JVM is shutting down");
            try {
                MonoMorphNewbee_mall_cloud_goods_serviceServerGRPC.this.stop();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Preserve interrupt status
                System.err.println("*** Server shutdown interrupted: " + e.getMessage());
                e.printStackTrace(System.err);
            }
            System.out.println("*** Server shut down");
        }));
    }

    /**
     * Stops the gRPC server.
     * @throws InterruptedException if server termination is interrupted.
     */
    public void stop() throws InterruptedException {
        // 1. Shutdown Lease Manager
        if (this.leaseManager != null) {
            this.leaseManager.shutdown(); // Call shutdown via interface
        }

        // 2. Shutdown Lease Scheduler
        if (this.leaseScheduler != null && !this.leaseScheduler.isShutdown()) {
            this.leaseScheduler.shutdown();
            try {
                long timeout = 5; TimeUnit units = TimeUnit.SECONDS;
                if (!this.leaseScheduler.awaitTermination(timeout, units)) {
                    this.leaseScheduler.shutdownNow(); // Force shutdown
                     if (!this.leaseScheduler.awaitTermination(timeout, units)) {
                          System.err.println("ERROR: Lease Scheduler did not terminate even after forcing.");
                     }
                }
            } catch (InterruptedException ie) {
                this.leaseScheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }

        // 3. Shutdown gRPC Server
        if (this.server != null && !this.server.isShutdown()) {
            try {
                this.server.shutdown().awaitTermination(30, TimeUnit.SECONDS); // Wait for gRPC calls to finish
            } catch (InterruptedException e) {
                 this.server.shutdownNow(); // Force immediate shutdown if interrupted
                 Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     * @throws InterruptedException if awaiting termination is interrupted.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Determines the port to use, checking environment variables first.
     * @return The port number.
     */
    private static int getPort() {
        int defaultPort = 50052;
        String portEnvVarName = "NEWBEE_MALL_CLOUD_GOODS_SERVICE_PORT";
        String portEnvVarValue = System.getenv(portEnvVarName);

        if (portEnvVarValue != null && !portEnvVarValue.isEmpty()) {
            try {
                int envPort = Integer.parseInt(portEnvVarValue);
                return envPort;
            } catch (NumberFormatException e) {
                System.err.println("WARN: Invalid port value '" + portEnvVarValue + "' in environment variable "
                        + portEnvVarName + ". Falling back to default port " + defaultPort + ".");
                // Fall through to return default port
            }
        } 
        return defaultPort;
    }


    /**
     * Main method to launch the server.
     */
    public static void main(String[] args) throws Exception {
        final int serverPort = getPort();
        final MonoMorphNewbee_mall_cloud_goods_serviceServerGRPC server = new MonoMorphNewbee_mall_cloud_goods_serviceServerGRPC(serverPort);

        try {
            server.start();
            server.blockUntilShutdown();
        } catch (IOException | InterruptedException e) {
            System.err.println("ERROR: Server failed to start on port " + serverPort);
            e.printStackTrace(System.err);
            System.exit(1); // Indicate failure
        }
    }
}