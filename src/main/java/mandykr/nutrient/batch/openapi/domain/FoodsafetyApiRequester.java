package mandykr.nutrient.batch.openapi.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mandykr.nutrient.batch.openapi.dto.foodsafety.FoodsafetyRequest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Getter
public class FoodsafetyApiRequester<T> implements ApiRequester<T> {
    private static final int NUM_OF_ROWS = 1000;
    private final Class<T> type;
    private final RestTemplate restTemplate;
    private final FoodsafetyRequest request;
    private List<T> responses = new ArrayList<>();

    public void request() {
        HttpEntity<String> httpEntity = setHttpEntity();
        UriComponents uriComponents = null;

        int startIdx = 1;
        do {
            uriComponents = setUri(startIdx);
            log.info("call url: {}", uriComponents.toString());
            ResponseEntity<String> responseEntity = apiCall(uriComponents, httpEntity);
            if (didNotSucceedResponse(responseEntity)) {
                break;
            }
            addSupplement(responseEntity);
            startIdx += NUM_OF_ROWS;
        } while (true);
    }

    private UriComponents setUri(int startIdx) {
        String url = request.getUrl();

        Map<String, String> params = new HashMap<>();
        params.put("keyId", request.getKeyId());
        params.put("serviceId", request.getServiceId());
        params.put("dataType", "json");
        params.put("startIdx", String.valueOf(startIdx));
        params.put("endIdx", String.valueOf(startIdx + NUM_OF_ROWS - 1));

        return UriComponentsBuilder.fromHttpUrl(url).buildAndExpand(params);
    }

    private HttpEntity<String> setHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("KEY", "VALUE");

        return new HttpEntity<>("", headers);
    }

    private ResponseEntity<String> apiCall(UriComponents uriComponents, HttpEntity<String> httpEntity) {
        // TODO: read time out 문제 해결
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, httpEntity, String.class);

        System.out.println("responseEntity = " + responseEntity);
        return responseEntity;
    }

    private boolean didNotSucceedResponse(ResponseEntity<String> responseEntity) {
        return !responseEntity.getStatusCode().equals(HttpStatus.OK) ||
                isEmptyResponseData(responseEntity);
    }

    private boolean isEmptyResponseData(ResponseEntity<String> responseEntity) {
        ObjectMapper objectMapper = new ObjectMapper();
        int totalCount = 0;
        String message = "";
        try {
            JsonNode body = objectMapper.readTree(responseEntity.getBody());

            JsonNode messageNode = body.path(request.getServiceId()).path("RESULT").path("MSG");
            message = objectMapper.writeValueAsString(messageNode);

            JsonNode totalCountNode = body.path(request.getServiceId()).path("total_count");
            String totalCountAsString = objectMapper.writeValueAsString(totalCountNode);
            TypeReference refInteger = new TypeReference<Integer>() {
            };
            totalCount = (int) objectMapper.readValue(totalCountAsString, refInteger);
        } catch (Exception e) {
            log.info("total count json parse error: {}, {}", e.getMessage(), message);
        }
        return totalCount <= 0;
    }

    private void addSupplement(ResponseEntity<String> responseEntity) {
        List<T> resultList = parseTheResponse(responseEntity);
        if (resultList == null || resultList.isEmpty()) {
            throw new NullPointerException("response result list is null");
        }
        responses.addAll(resultList);
    }

    private List<T> parseTheResponse(ResponseEntity<String> responseEntity) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<T> resultList = new ArrayList<>();
        String message = "";
        try {
            JsonNode body = objectMapper.readTree(responseEntity.getBody());

            JsonNode messageNode = body.path(request.getServiceId()).path("RESULT").path("MSG");
            message = objectMapper.writeValueAsString(messageNode);

            JsonNode items = body.path(request.getServiceId()).path("row");
            String itemsAsString = objectMapper.writeValueAsString(items);
            CollectionType javaType = objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, type);
            resultList = objectMapper.readValue(itemsAsString, javaType);
        } catch (Exception e) {
            log.info("supplement json parse error: {}, {}", e.getMessage(), message);
        }

        return resultList;
    }
}
