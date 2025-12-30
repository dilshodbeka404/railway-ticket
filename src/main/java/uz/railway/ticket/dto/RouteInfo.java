package uz.railway.ticket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteInfo {

    @JsonProperty("depStationName")
    private String depStationName;

    @JsonProperty("arvStationName")
    private String arvStationName;
}
