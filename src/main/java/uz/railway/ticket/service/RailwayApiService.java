package uz.railway.ticket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;
import uz.railway.ticket.dto.TrainDto;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RailwayApiService {

    private final RestClient restClient = RestClient.create();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String API_URL = "https://eticket.railway.uz/api/v3/handbook/trains/list";

    public List<TrainDto> searchTickets(String depStationCode, String arvStationCode, String date) {
        Map<String, Object> payload = new HashMap<>();
        Map<String, Object> directions = new HashMap<>();
        Map<String, String> forward = new HashMap<>();

        forward.put("date", date);
        forward.put("depStationCode", depStationCode);
        forward.put("arvStationCode", arvStationCode);
        directions.put("forward", forward);
        payload.put("directions", directions);

        try {
            Map response = restClient.post()
                    .uri(API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Accept", "application/json")
                    .header("Accept-Language", "uz")
                    .header("Device-Type", "BROWSER")
                    .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36")
                    .header("Cookie", "_ga=GA1.1.2063689773.1762924315; __stripe_mid=443c28a3-1758-4684-b611-4024a96ff8a6d8bad5; XSRF-TOKEN=2873521f-2233-4d00-849f-82fddb45e68a; _ga_R5LGX7P1YR=GS2.1.s1762929964$o2$g0$t1762929964$j60$l0$h0; __stripe_sid=ff6d691a-eb6b-4c85-84f5-b559d7a50b9a0b8f5e")
                    .header("X-xsrf-token", "2873521f-2233-4d00-849f-82fddb45e68a")
                    .body(payload)
                    .retrieve()
                    .body(Map.class);

            if (response != null) {
                Map<String, Object> data = (Map<String, Object>) response.get("data");
                if (data != null) {
                    Map<String, Object> dirs = (Map<String, Object>) data.get("directions");
                    if (dirs != null) {
                        Map<String, Object> fwd = (Map<String, Object>) dirs.get("forward");
                        if (fwd != null) {
                            List<Map<String, Object>> trains = (List<Map<String, Object>>) fwd.get("trains");
                            if (trains != null) {
                                return trains.stream()
                                        .map(t -> objectMapper.convertValue(t, TrainDto.class))
                                        .toList();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("API so'rov xatosi: {}", e.getMessage());
        }

        return Collections.emptyList();
    }
}
