package uz.railway.ticket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainDto {

    private String number;
    private String brand;

    @JsonProperty("departureDate")
    private String departureDate;

    @JsonProperty("arrivalDate")
    private String arrivalDate;

    @JsonProperty("timeOnWay")
    private String timeOnWay;

    @JsonProperty("subRoute")
    private RouteInfo subRoute;

    private List<CarDto> cars;
}
