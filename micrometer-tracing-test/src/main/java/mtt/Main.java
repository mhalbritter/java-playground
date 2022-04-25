package mtt;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brave.Tracing;
import brave.http.HttpTracing;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.transport.http.HttpClientRequest;
import io.micrometer.observation.transport.http.HttpClientResponse;
import io.micrometer.observation.transport.http.HttpServerRequest;
import io.micrometer.observation.transport.http.HttpServerResponse;
import io.micrometer.observation.transport.http.context.HttpServerContext;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.brave.bridge.BraveBaggageManager;
import io.micrometer.tracing.brave.bridge.BraveCurrentTraceContext;
import io.micrometer.tracing.brave.bridge.BraveHttpServerHandler;
import io.micrometer.tracing.brave.bridge.BraveTracer;
import io.micrometer.tracing.handler.HttpServerTracingObservationHandler;

class Main {
	public static void main(String[] args) throws InterruptedException {
		ObservationRegistry observationRegistry = ObservationRegistry.create();
		try (Tracing tracing = Tracing.newBuilder().build()) {
			Tracer tracer = new BraveTracer(tracing.tracer(), new BraveCurrentTraceContext(tracing.currentTraceContext()), new BraveBaggageManager());
			HttpTracing httpTracing = HttpTracing.newBuilder(tracing).build();
//			observationRegistry.observationConfig().observationHandler(new HttpClientTracingObservationHandler(tracer, new BraveHttpClientHandler(brave.http.HttpClientHandler.create(httpTracing))));
			observationRegistry.observationConfig().observationHandler(new HttpServerTracingObservationHandler(tracer, new BraveHttpServerHandler(brave.http.HttpServerHandler.create(httpTracing))));

//			HttpClientContext clientContext = new HttpClientContext();
			HttpServerContext serverContext = new HttpServerContext();

			DummyHttpServerRequest serverRequest = new DummyHttpServerRequest();
			serverContext.setRequest(serverRequest);
//			DummyHttpClientRequest clientRequest = new DummyHttpClientRequest();
//			clientContext.setRequest(clientRequest);
			Observation observation = Observation.start("test-1", serverContext, observationRegistry);
//			clientContext.setResponse(new DummyHttpClientResponse(clientRequest));
			Thread.sleep(1000);
			RuntimeException boom = new RuntimeException("boom");
			serverContext.setResponse(new DummyHttpServerResponse(serverRequest, boom));
			observation.error(boom);
			observation.stop();
		}
	}

	private static class DummyHttpServerResponse implements HttpServerResponse {

		private final DummyHttpServerRequest request;

		private final Throwable error;

		private DummyHttpServerResponse(DummyHttpServerRequest request, Throwable error) {
			this.request = request;
			this.error = error;
		}

		@Override
		public Throwable error() {
			return this.error;
		}

		@Override
		public int statusCode() {
			return 200;
		}

		@Override
		public Collection<String> headerNames() {
			return List.of();
		}

		@Override
		public Object unwrap() {
			return null;
		}

		@Override
		public HttpServerRequest request() {
			return this.request;
		}
	}

	private static class DummyHttpServerRequest implements HttpServerRequest {
		@Override
		public String method() {
			return "GET";
		}

		@Override
		public String path() {
			return "/hello";
		}

		@Override
		public String url() {
			return "http://localhost/server";
		}

		@Override
		public String header(String name) {
			System.out.printf("header(%s)%n", name);
			return switch (name) {
				case "X-B3-Sampled" -> "1";
				case "X-B3-TraceId" -> "0000000000000001";
				case "X-B3-SpanId" -> "0000000000000001";
				// case "X-B3-ParentSpanId" -> "0000000000000002";
				default -> null;
			};
		}

		@Override
		public Collection<String> headerNames() {
			System.out.printf("headerNames()%n");
			return List.of();
		}

		@Override
		public Object unwrap() {
			return null;
		}
	}


	private static class DummyHttpClientResponse implements HttpClientResponse {

		private final DummyHttpClientRequest request;

		private DummyHttpClientResponse(DummyHttpClientRequest request) {
			this.request = request;
		}

		@Override
		public int statusCode() {
			return 200;
		}

		@Override
		public Collection<String> headerNames() {
			System.out.printf("headerNames()%n");
			return List.of();
		}

		@Override
		public Object unwrap() {
			return null;
		}

		@Override
		public HttpClientRequest request() {
			return this.request;
		}
	}

	private static class DummyHttpClientRequest implements HttpClientRequest {

		private final Map<String, String> headers = new HashMap<>();

		@Override
		public void header(String name, String value) {
			System.out.printf("header(%s, %s)%n", name, value);
			headers.put(name, value);
		}

		@Override
		public String method() {
			return "GET";
		}

		@Override
		public String path() {
			return "/";
		}

		@Override
		public String url() {
			return "http://localhost/";
		}

		@Override
		public String header(String name) {
			System.out.printf("header(%s)%n", name);
			return this.headers.get(name);
		}

		@Override
		public Collection<String> headerNames() {
			System.out.printf("headerNames()%n");
			return this.headers.keySet();
		}

		@Override
		public Object unwrap() {
			return null;
		}

		@Override
		public String toString() {
			return "DummyHttpRequest{" +
					"headers=" + headers +
					'}';
		}
	}
}
