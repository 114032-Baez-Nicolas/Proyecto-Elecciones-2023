package ar.edu.utn.frc.tup.lc.iv.clients.dtos.hard;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AgrupacionesClientDTO {

    @JsonProperty("agrupacionId")
    private Long id;

    @JsonProperty("agrupacionNombre")
    private String nombre;
}
