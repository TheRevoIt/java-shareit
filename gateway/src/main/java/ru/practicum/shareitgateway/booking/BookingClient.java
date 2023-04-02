package ru.practicum.shareitgateway.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareitgateway.booking.dto.BookingDto;
import ru.practicum.shareitgateway.client.BaseClient;

import java.util.Map;

@Service
public class BookingClient extends BaseClient {
    private static final String API_PATH = "/bookings";

    @Autowired
    public BookingClient(@Value("${server.url.test}") String url, RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(url + API_PATH))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new).build());
    }

    public ResponseEntity<Object> create(BookingDto bookingDto, long userId) {
        return post("", userId, bookingDto);
    }

    public ResponseEntity<Object> update(long userId, long bookingId, Boolean approved) {
        Map<String, Object> parameters = Map.of("approved", approved);
        return patch("/" + bookingId + "?approved={approved}", userId, parameters, null);
    }

    public ResponseEntity<Object> get(long userId, long bookingId) {
        return get("/" + bookingId, userId);
    }

    public ResponseEntity<Object> getAll(long userId, String state, int from, int size) {
        Map<String, Object> parameters = Map.of("state", state, "from", from, "size", size);
        return get("?state={state}&from={from}&size={size}", userId, parameters);
    }

    public ResponseEntity<Object> getAllForOwner(long userId, String state, int from, int size) {
        Map<String, Object> parameters = Map.of("state", state, "from", from, "size", size);
        return get("/owner?state={state}&from={from}&size={size}", userId, parameters);
    }
}
