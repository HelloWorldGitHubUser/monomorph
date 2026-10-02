package com.youlai.mall.monomorph;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.youlai.mall.MonolithApplication;

import com.youlai.mall.monomorph.id.MonoMorphMall_pmsServerGRPC;


/**
 * Generated Main class to concurrently run the main methods of
 * MonolithApplication and MonoMorphMall_pmsServerGRPC within the same JVM.
 *
 * NOTE: Graceful shutdown relies on the individual main methods handling
 * Thread interruption correctly (e.g., catching InterruptedException,
 * checking Thread.currentThread().isInterrupted()) to perform their own cleanup.
 */
public class MonoMorphMall_pmsMain {

    private ExecutorService executorService;

    public static void main(String[] args) {
        MonoMorphMall_pmsMain combinedMain = new MonoMorphMall_pmsMain();
        combinedMain.start(args);
    }

    /**
     * Starts the execution of both main methods concurrently.
     * @param args Command line arguments passed to this MonoMorphMall_pmsMain.
     *             These are currently *not* passed down to the individual mains,
     *             but could be split and passed if necessary.
     */
    public void start(String[] args) {

        // Use a fixed thread pool with 2 threads.
        executorService = Executors.newFixedThreadPool(2);

        // Register a shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                // Call the stop method of the enclosing instance
                stop();
            }
        }, "MonoMorphMall_pmsMain-ShutdownHook")); 


        // --- Arguments for the target mains ---
        final String[] oldMainArgs = args;
        final String[] grpcServerArgs = args;
        
        // Submit OldMain using an Anonymous Inner Class
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                MonolithApplication.main(oldMainArgs);
            }
        });

         // Submit NewGrpcServer using an Anonymous Inner Class
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    MonoMorphMall_pmsServerGRPC.main(grpcServerArgs);
                } catch (Exception e) {
                    System.err.println("ERROR: gRPC server main method failed.");
                    e.printStackTrace(System.err);
                }
            }
        });

        // The main thread of MonoMorphMall_pmsMain can exit now.
        // The application stays alive due to the non-daemon threads in the ExecutorService.
    }

    /**
     * Initiates the shutdown sequence for the executor service.
     * This will attempt to interrupt the threads running the main methods.
     */
    public void stop() {
        // Use a temporary variable for thread-safety check
        ExecutorService exec = executorService;
        if (exec != null && !exec.isShutdown()) {

            // Use shutdownNow() to interrupt the threads running the main methods.
            exec.shutdownNow();

            try {
                // Wait a bit for tasks to terminate after interruption.
                exec.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                // Force shutdown again if interrupted during waiting
                exec.shutdownNow();
                // Preserve interrupt status
                Thread.currentThread().interrupt();
            }
        }
    }
}

