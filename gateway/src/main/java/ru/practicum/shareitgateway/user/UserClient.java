package ru.practicum.shareitgateway.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareitgateway.client.BaseClient;
import ru.practicum.shareitgateway.request.dto.ItemRequestDto;
import ru.practicum.shareitgateway.user.dto.UserDto;

import java.util.Map;

@Service
public class UserClient extends BaseClient {
    private static final String API_PATH = "/users";

    @Autowired
    public UserClient(@Value("${server.url.test}") String url, RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(url + API_PATH))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new).build());
    }

    public ResponseEntity<Object> create(UserDto userDto) {
        return post("", userDto);
    }

    public ResponseEntity<Object> update(UserDto userDto, long userId) {
        return patch("/" + userId, userDto);
    }

    public ResponseEntity<Object> getById(long userId) {
        return get("/" + userId);
    }

    public ResponseEntity<Object> deleteById(long userId) {
        return delete("/" + userId);
    }

    public ResponseEntity<Object> findAll() {
        return get("");
    }

    public ResponseEntity<Object> getAllRequests(long userId, int from, int size) {
        Map<String, Object> parameters = Map.of("from", from, "size", from);
        return post("/all?from={from}&size={size}", userId, parameters);
    }
}
