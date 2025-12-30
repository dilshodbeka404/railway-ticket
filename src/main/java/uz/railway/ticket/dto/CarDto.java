package uz.railway.ticket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {

    private String type;

    @JsonProperty("freeSeats")
    private int freeSeats;

    private List<TariffDto> tariffs;
}
