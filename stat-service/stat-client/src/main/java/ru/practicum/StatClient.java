package ru.practicum;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.dto.HitView;
import ru.practicum.dto.StatView;
import ru.practicum.dto.HitCreateRequest;

/**
 * Клиент для работы со статистикой.
 */
@Service
public class StatClient {
    private final RestTemplate rest;

    @Autowired
    public StatClient(@Value("${stat-server.url}") String serverUrl, RestTemplateBuilder builder) {
        this.rest = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build();
    }

    public HitView create(HitCreateRequest request) {
        HttpEntity<HitCreateRequest> requestEntity = new HttpEntity<>(request);
        ResponseEntity<HitView> responseEntity;

        try {
            responseEntity = rest.exchange("/hit", HttpMethod.POST, requestEntity, HitView.class);
        } catch (HttpStatusCodeException e) {
            throw new StatClientException("При сохранении ресурса возникла ошибка.", e);
        }

        if (responseEntity.getStatusCode() != HttpStatus.CREATED) {
            throw new StatClientException(
                    "Код ответа не 201. Код: " + responseEntity.getStatusCode());
        }

        return responseEntity.getBody();
    }

    public List<StatView> getStats(LocalDateTime start, LocalDateTime end, @Nullable Collection<String> uris,
                                   @Nullable Boolean unique) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start", start.format(formatter));
        parameters.put("end", end.format(formatter));

        StringBuilder path = new StringBuilder();
        path.append("/stats?start={start}&end={end}");

        if (uris != null && !uris.isEmpty()) {
            parameters.put("uris", uris.toArray());
            path.append("&uris={uris}");
        }
        if (unique != null) {
            parameters.put("unique", unique.toString());
            path.append("&unique={unique}");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<List<StatView>> responseEntity;

        try {
            responseEntity = rest.exchange(path.toString(), HttpMethod.GET, requestEntity,
                    new ParameterizedTypeReference<>() {
                    }, parameters);
        } catch (HttpStatusCodeException e) {
            throw new StatClientException(
                    "Произошла ошибка при обращении к : " + path, e);
        }

        return responseEntity.getBody();
    }
}