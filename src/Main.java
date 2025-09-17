import entidades.*;
import repositorios.InMemoryRepository;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public class Main {
    public static void main(String[] args) {
        // Inicializar repositorios
        // Directamente lo hacemos del tipo empresa ya que al aplicar la navegabilidad al guardar el objeto empresa vamos a guardar tambientodo lo que este relacionada a ella
        InMemoryRepository<Empresa> empresaRepository = new InMemoryRepository<>();

        //Creamos el pais
        Pais argentina = Pais.builder()
                .nombre("Argentina").
                build();

        // Crear provincias y localidades
        Provincia buenosAires = Provincia.builder()
                .id(1L)
                .nombre("Buenos Aires")
                .pais(argentina)
                .build();

        Localidad caba = Localidad.builder()
                .id(1L)
                .nombre("CABA")
                .provincia(buenosAires)
                .build();

        Domicilio domicilio1 = Domicilio.builder()
                .id(1L)
                .calle("Calle 1")
                .numero(100)
                .cp(1000)
                .piso(1)
                .nroDpto(1)
                .localidad(caba)
                .build();

        Localidad laPlata = Localidad.builder()
                .id(2L)
                .nombre("La Plata")
                .provincia(buenosAires)
                .build();

        Domicilio domicilio2 = Domicilio.builder()
                .id(2L)
                .calle("Calle 2")
                .numero(200)
                .cp(2000)
                .piso(2)
                .nroDpto(2)
                .localidad(laPlata)
                .build();


        Provincia cordoba = Provincia.builder()
                .id(2L)
                .nombre("Córdoba")
                .pais(argentina)
                .build();

        Localidad cordobaCapital = Localidad.builder()
                .id(3L)
                .nombre("Córdoba Capital")
                .provincia(cordoba)
                .build();

        Domicilio domicilio3 = Domicilio.builder()
                .id(3L)
                .calle("Calle 3")
                .numero(300)
                .cp(3000)
                .piso(3)
                .nroDpto(3)
                .localidad(cordobaCapital)
                .build();


        Localidad villaCarlosPaz = Localidad.builder()
                .id(4L)
                .nombre("Villa Carlos Paz")
                .provincia(cordoba)
                .build();


        Domicilio domicilio4 = Domicilio.builder()
                .id(4L)
                .calle("Calle 4")
                .numero(400)
                .cp(4000)
                .piso(4)
                .nroDpto(4)
                .localidad(villaCarlosPaz)
                .build();


        // Crear sucursales para Buenos Aires
        Sucursal sucursal1 = Sucursal.builder()
                .id(1L)
                .nombre("Sucursal 1")
                .horarioApertura(LocalTime.of(9, 0))
                //ver como usa el .of para asignar la hora
                .horarioCierre(LocalTime.of(18, 0))
                .esCasaMatriz(true)
                .domicilio(domicilio1)
                .build();

        Sucursal sucursal2 = Sucursal.builder()
                .id(2L)
                .nombre("Sucursal 2")
                .horarioApertura(LocalTime.of(9, 0))
                .horarioCierre(LocalTime.of(18, 0))
                .esCasaMatriz(false)
                .domicilio(domicilio2)
                .build();


        // Crear Sucursales Para Cordoba
        Sucursal sucursal3 = Sucursal.builder()
                .id(3L)
                .nombre("Sucursal 3")
                .horarioApertura(LocalTime.of(8, 0))
                .horarioCierre(LocalTime.of(20, 30))
                .esCasaMatriz(true)
                .domicilio(domicilio3)
                .build();

        Sucursal sucursal4 = Sucursal.builder()
                .id(4L)
                .nombre("Sucursal 4")
                .horarioApertura(LocalTime.of(8, 0))
                .horarioCierre(LocalTime.of(20, 30))
                .esCasaMatriz(false)
                .domicilio(domicilio4)
                .build();


        // Crear empresas y asociar sucursales
        Empresa empresa1 = Empresa.builder()
                .nombre("Empresa 1")
                .razonSocial("Razon Social 1")
                .cuit("30847267463")
                .cuil(12345678901L)
                //inicializá el atributo sucursales con un conjunto mutable (HashSet) que contiene sucursal1 y sucursal2."
                //el set solo no permitiria modificar la lista
                //al castearlo con new HashSet pertime que sea mutable
                .sucursales(new HashSet<>(Set.of(sucursal1, sucursal2)))
                .build();

        Empresa empresa2 = Empresa.builder()
                .nombre("Empresa 2")
                .razonSocial("Razon Social 2")
                .cuit("30394829008")
                .cuil(22225678901L)
                .sucursales(new HashSet<>(Set.of(sucursal3, sucursal4)))
                .build();

        // Guardar empresas en el repositorio
        empresaRepository.save(empresa1);
        empresaRepository.save(empresa2);

        // Mostrar todas las empresas
        System.out.println("Todas las empresas:");
        List<Empresa> todasLasEmpresas = empresaRepository.findAll();
        todasLasEmpresas.forEach(System.out::println);//estilo de programacion funcional
        //Acá "empresa -> System.out.println(empresa)" es una función anónima (lambda) que recibe un empresa y ejecuta System.out.println(empresa)
        //directamente no pongo empresa -->, y con los :: digo por cada elemento que me pasa el forEach, llamá al metodo println de System.out
        //Equivale a pasar una función que reciba un objeto y lo imprima con System.out.println.
        //Por cada empresa en la lista todasLasEmpresas, llamá a System.out.println para imprimirla en consola.


        // Buscar empresa por ID
        Optional<Empresa> empresaEncontrada = empresaRepository.findById(1L);
        empresaEncontrada.ifPresent(e -> System.out.println("\nEmpresa encontrada por ID 1: " + e));
        //ifPresente en un metodo de la clase Optional
        //"e" es el parametro de tipo Empresa
        //seria lo mismo que hacer esto(sin mensaje extra): empresaEncontrada.ifPresent(System.out::println);

        // Buscar empresa por nombre
        List<Empresa> empresasPorNombre = empresaRepository.genericFindByField("nombre", "Empresa 1");
        System.out.println("\nEmpresas con nombre 'Empresa 1':");
        empresasPorNombre.forEach(System.out::println);

        // Actualizar empresa por ID
        Empresa empresaActualizada = Empresa.builder()
                .id(1L)
                .nombre("Empresa 1 Actualizada")
                .razonSocial("Razon Social 1 Actualizada")
                .cuit("30847267463")
                .cuil(90876543211L)
                .sucursales(empresa1.getSucursales())
                .build();

        empresaRepository.genericUpdate(1L, empresaActualizada);
        Optional<Empresa> empresaVerificada = empresaRepository.findById(1L);
        empresaVerificada.ifPresent(e -> System.out.println("\nEmpresa después de la actualización: " + e));

        // Eliminar empresa por ID
        empresaRepository.genericDelete(1L);
        Optional<Empresa> empresaEliminada = empresaRepository.findById(1L);
        //Como ya fue eliminada, findById(1L) devuelve un Optional.empty()
        if (empresaEliminada.isEmpty()) {
            System.out.println("\nLa empresa con ID 1 ha sido eliminada.");
        }


        //si hacia lo siguiente, podia mostrar la empresa que fue eliminada del HashMap:
        //Optional<Empresa> empresaEliminada = empresaRepository.genericDelete(1L);
        //empresaEliminada.ifPresent(e ->
        //    System.out.println("Se eliminó la empresa: " + e)
        //);



        // Mostrar todas las empresas restantes
        System.out.println("\nTodas las empresas después de la eliminación:");
        List<Empresa> empresasRestantes = empresaRepository.findAll();
        empresasRestantes.forEach(System.out::println);


        // Mostrar las sucursales de una empresa determinada
        Optional<Empresa> empresa = empresaRepository.findById(empresa2.getId());
        if (empresa.isPresent()) {
            System.out.println("\nSucursales de la empresa con ID " + empresa2.getId() + ":");

            Set<Sucursal> sucursales = empresa.get().getSucursales();
            //empresa.get() devuelve la empresa real dentro del Optional y getSucursales() devuelve el conjunto de sucursales asociadas a esa empresa.

            sucursales.forEach(System.out::println);
        } else {
            System.out.println("\nEmpresa con ID " + empresa2.getId() + " no encontrada.");
        }

    }
}