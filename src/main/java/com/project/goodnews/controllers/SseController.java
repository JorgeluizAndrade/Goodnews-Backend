package com.project.goodnews.controllers;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class SseController {
	@GetMapping(value = "/sse/ping", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamPing() {
		return Flux.interval(Duration.ofSeconds(58)).map(seq -> "Ping: "  + seq);
	}
}
