package com.pack.colorchat.impl

import com.pack.colorchat.service.UserService
import groovy.util.logging.Slf4j
import spock.lang.Specification

import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

import static com.pack.colorchat.model.Color.GREEN

@Slf4j
class UserServiceKtImplTestSpec extends Specification {
    UserService userService = new UserServiceKtImpl()
//    UserService userService = new UserServiceJavaImpl()

    def "concurrentTest"() {
        when:
        ExecutorService threadPool = Executors.newCachedThreadPool()
//        ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1)
        final AtomicBoolean success = new AtomicBoolean(true)
        try {
            int poolSize = 500
            poolSize = 1
            poolSize = 10
            poolSize = 100
            poolSize = 70
            poolSize = 20
            poolSize = 13
            poolSize = 12
            final CountDownLatch latch = new CountDownLatch(poolSize)
            for (int i = 0; i < poolSize; i++) {
                threadPool.execute({
                    try {
                        userService.addUser("conc_name" + i, GREEN)
                    } catch (Exception e) {
                        success.set(false)
                        println("error!" + e)
                        log.error("error!", e)
                        throw new RuntimeException(e)
                    } finally {
                        latch.countDown()
                    }
                })
            }
            try {
                latch.await()
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e)
                success.set(false)
                Thread.currentThread().interrupt()
            }
        } finally {
            threadPool.shutdown()
        }
        if (success.get()) {
            log.info("CompletedWithNoErrors")
        } else {
            log.info("CompletedWithErrors")
        }

        then:
        success.get()
    }
}
