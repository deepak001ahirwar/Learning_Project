package com.example.test.ratelimit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.github.bucket4j.Bucket;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
@RequestMapping(value = "/api")
public class RateLimiterApiController {


    // 1- Using Bucket4j (Recommended for Local In-Memory Limiting)
    public  final Map<String,Bucket> cache = new ConcurrentHashMap<>();


    @GetMapping(value = "/limit")
    public ResponseEntity<String> ratelimitApi(@RequestHeader(value = "X-Client-id") String clientId) {
        Bucket bucket = resolveBucket(clientId);
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok("Request Sucessfully ");
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many Requests");
        }
    }

    public Bucket resolveBucket(String clientId) {
        return cache.computeIfAbsent(clientId, k -> {
            // 2 request  per min
            Refill refill = Refill.greedy(2, Duration.ofMinutes(1));
            Bandwidth limit = Bandwidth.classic(5, refill);
            return Bucket.builder().addLimit(limit).build();
        });
    }


    // 2- Using Spring Cloud Gateway (If Using Gateway for APIs)

    @GetMapping(value = "/cloud/limit")
    public ResponseEntity<String> rateLimitByCloudGateway() {
        return ResponseEntity.ok("Successfully Responce ");
    }

    // 3- Resilience4j Rate Limiting Example:
    @RateLimiter(name = "backendA" ,fallbackMethod = "fallback")
    @GetMapping("/resilience")
    public String myApi() {
        return "Request successful!";
    }

    public String fallback(Throwable t) {
        return "Rate limit hit - Fallback triggered!";
    }



}
