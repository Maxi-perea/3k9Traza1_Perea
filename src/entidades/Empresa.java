package entidades;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder //en este caso no implementamos herencia pero lo coloco para recordarlo
public class Empresa {
    private Long id;
    private String nombre;
    private String razonSocial;

    //Calificamos el cuit como String porque el mismo tiene 11 digitos, lo cual con el Integer no nos alcanza. Ademas es una buena practica, pues el cuit no se usa para hacer calculos
    private String cuit;
    private String logo;

    //atributo que no estaba en el diagrama de clases pero sí en el código del profesor
    private Long cuil;

    @Builder.Default //sirve para que los valores por defecto que pongas en los campos de una clase se respeten cuando construís el objeto con Lombok Builder.
    private Set<Sucursal> sucursales = new HashSet<>();

}
