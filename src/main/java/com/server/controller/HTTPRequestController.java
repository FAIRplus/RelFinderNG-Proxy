package com.server.controller;

import java.net.URI;
import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bordercloud.sparql.Method;
import com.bordercloud.sparql.MimeType;
import com.bordercloud.sparql.SparqlResult;

@RestController
public class HTTPRequestController {

	private static final Logger log = LogManager.getLogger(HTTPRequestController.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/", method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> wikidata(@RequestParam(name = "endpoint", required = true) String endpointURI,
			@RequestParam(name = "query", required = true) String query,
			@RequestParam(name = "method", required = false) String httpMethod,
			@RequestParam(name = "default-graph-uri", required = false) String defaultGraphUri,
			@RequestParam(name = "format", required = false) String format) {
		try {
//			String decodedEndpoint = this.decodeBase64Content(endpointURI);
//			String decodedQuery = this.decodeBase64Content(query);
			String decodedEndpoint = endpointURI;
			String decodedQuery = query;

			log.info("Decoded URL: " + ((decodedEndpoint != null) ? decodedEndpoint : "null"));
			log.info("Decoded Query: " + ((decodedQuery != null) ? decodedQuery : "null"));
			log.info("method: " + ((httpMethod != null) ? httpMethod : "null"));
			log.info("format: " + ((format != null) ? format : "null"));
			log.info("default-graph-uri: " + ((defaultGraphUri != null) ? defaultGraphUri : "null"));

			if (decodedQuery == null || decodedQuery.equalsIgnoreCase("undefined")) {
				log.info("Recieved empty query := " + decodedQuery);
				return new ResponseEntity("Recieved query=undefined", HttpStatus.BAD_REQUEST);
			}

			URI endpoint = new URI(decodedEndpoint);
			String querySelect = decodedQuery;
//			querySelect = querySelect+"%26format="+format;
			MySparqlClient sc = new MySparqlClient(false);
			if (httpMethod != null && httpMethod.equalsIgnoreCase("POST")) {
				sc.setMethodHTTPRead(Method.POST);
			} else {
				sc.setMethodHTTPRead(Method.GET);
			}

			sc.setEndpointRead(endpoint);
			SparqlResult sr = null;

			if (format != null && format.equalsIgnoreCase("xml"))
				sr = sc.query(querySelect, MimeType.xml);
			else
				sr = sc.query(querySelect, MimeType.json);

			if (sr != null && sr.resultRaw != null) {
				log.info(sr.resultRaw);
				return new ResponseEntity(sr.resultRaw, HttpStatus.OK);
			} else {
				log.info("Recieved empty response");
				return new ResponseEntity("{} ", HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	private String decodeBase64Content(String value) {
		Base64.Decoder decoder = Base64.getDecoder();
		String dStr = new String(decoder.decode(value));
		return dStr;
	}

	/*
	 * apache httpclient lib code
	 * 
	 * @GetMapping("/proxy") public ResponseEntity<?>
	 * getProxyResponse(@RequestParam(name = "endpoint", required = true) String
	 * endpointURI,
	 * 
	 * @RequestParam(name = "query", required = true) String query,
	 * 
	 * @RequestParam(name = "method") String httpMethod) {
	 * 
	 * CloseableHttpClient httpClient =
	 * HttpClients.custom().disableContentCompression().build(); try {
	 * 
	 * String decodedEndpoint = this.decodeBase64Content(endpointURI); String
	 * decodedQuery = this.decodeBase64Content(query); String domainName =
	 * this.getDomainName(decodedEndpoint);
	 * 
	 * HttpUriRequest request =
	 * RequestBuilder.get().setUri(decodedEndpoint).addParameter("query",
	 * decodedQuery) .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
	 * .setHeader(HttpHeaders.ACCEPT,
	 * "application/json").setHeader(HttpHeaders.REFERER, domainName) //
	 * .setHeader(HttpHeaders.HOST, domainName) .setHeader(HttpHeaders.USER_AGENT,
	 * "SpringBoot API") .setHeader(HttpHeaders.CACHE_CONTROL, "no-cache").build();
	 * 
	 * HttpResponse response = httpClient.execute(request); StatusLine statusLine =
	 * response.getStatusLine(); System.out.println(statusLine.getStatusCode() + " "
	 * + statusLine.getReasonPhrase()); String responseBody =
	 * EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
	 * System.out.println(responseBody);
	 * 
	 * System.out.println(response); logger.info(response); return new
	 * ResponseEntity(responseBody, HttpStatus.OK); } catch (Exception e) {
	 * e.printStackTrace(); return new ResponseEntity(e.getMessage(),
	 * HttpStatus.BAD_REQUEST); } finally { if (httpClient != null) { try {
	 * httpClient.close(); } catch (IOException e) { e.printStackTrace(); } } } }
	 */

	/*
	 * private String getDomainName(String url) throws URISyntaxException { URI uri
	 * = new URI(url); String domain = uri.getHost(); return
	 * domain.startsWith("www.") ? domain.substring(4) : domain; }
	 */
}
