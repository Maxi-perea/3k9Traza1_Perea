package entidades;

import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
public class Domicilio {
    private Long id;
    private String calle;
    private Integer numero;
    private Integer cp;

    //atributos que no estaban en el diagrama de clases pero sí en el código del profesor
    private Integer piso;
    private Integer nroDpto;

    private Localidad localidad;


}
