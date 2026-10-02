package com.passjava.monomorph;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.passjava.PassJavaApplication;

import com.passjava.monomorph.id.MonoMorphPassjava_searchServerGRPC;


/**
 * Generated Main class to concurrently run the main methods of
 * PassJavaApplication and MonoMorphPassjava_searchServerGRPC within the same JVM.
 *
 * NOTE: Graceful shutdown relies on the individual main methods handling
 * Thread interruption correctly (e.g., catching InterruptedException,
 * checking Thread.currentThread().isInterrupted()) to perform their own cleanup.
 */
public class MonoMorphPassjava_searchMain {

    private ExecutorService executorService;

    public static void main(String[] args) {
        MonoMorphPassjava_searchMain combinedMain = new MonoMorphPassjava_searchMain();
        combinedMain.start(args);
    }

    /**
     * Starts the execution of both main methods concurrently.
     * @param args Command line arguments passed to this MonoMorphPassjava_searchMain.
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
        }, "MonoMorphPassjava_searchMain-ShutdownHook")); 


        // --- Arguments for the target mains ---
        final String[] oldMainArgs = args;
        final String[] grpcServerArgs = args;
        
        // Submit OldMain using an Anonymous Inner Class
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                PassJavaApplication.main(oldMainArgs);
            }
        });

         // Submit NewGrpcServer using an Anonymous Inner Class
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                MonoMorphPassjava_searchServerGRPC.main(grpcServerArgs);
            }
        });

        // The main thread of MonoMorphPassjava_searchMain can exit now.
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
                !exec.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                // Force shutdown again if interrupted during waiting
                exec.shutdownNow();
                // Preserve interrupt status
                Thread.currentThread().interrupt();
            }
        }
    }
}