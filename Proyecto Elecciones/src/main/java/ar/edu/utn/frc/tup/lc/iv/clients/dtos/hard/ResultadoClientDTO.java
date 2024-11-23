package ar.edu.utn.frc.tup.lc.iv.clients.dtos.hard;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class ResultadoClientDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("distritoId")
    private Long distritoId;

    @JsonProperty("seccionId")
    private Long seccionId;

    @JsonProperty("cargoId")
    private Long cargoId;

    @JsonProperty("agrupacionId")
    private Long agrupacionId;

    @JsonProperty("votosTipo")
    private String votosTipo;

    @JsonProperty("votosCantidad")
    private Long votosCantidad;
}
