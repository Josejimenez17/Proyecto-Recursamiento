package net.jose;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import net.jose.model.Categoria;
import net.jose.repository.CategoriasRepository;

@SpringBootApplication
public class JpaDemoApplication implements CommandLineRunner{

	@Autowired
	private CategoriasRepository repo;
	
	
	public static void main(String[] args) {
		SpringApplication.run(JpaDemoApplication.class, args);
	}
 
	@Override  
	public void run(String... args) throws Exception {
		buscarTodosPaginacionOrdenados();
	}
	
	
	/** metodo 5
	 * Metodo findAll [Con paginacion y Ordenados] - Interfaz PagingAndSortingRepository
	 */
	private void buscarTodosPaginacionOrdenados() {
		Page<Categoria> page = repo.findAll(PageRequest.of(0, 5,Sort.by("nombre").descending()));
		System.out.println("Total Registros: " + page.getTotalElements());
		System.out.println("Total Paginas: " + page.getTotalPages());
		for (Categoria c : page.getContent()) {
			System.out.println(c.getId() + " " + c.getNombre());
		}
	}
	
	/** metodo 4
	 * Metodo findAll [Con Paginación] - Interfaz PagingAndSortingRepository
	 */
	private void buscarTodosPaginacion() {
		Page<Categoria> page = repo.findAll(PageRequest.of(0, 5));
		System.out.println("Total Registros: " + page.getTotalElements());
		System.out.println("Total Paginas: " + page.getTotalPages());
		for (Categoria c : page.getContent()) {
			System.out.println(c.getId() + " " + c.getNombre());
		}
	}
	
	/**metodo 3
	 * Metodo findAll [Ordenados por un campo] - Interfaz PagingAndSortingRepository
	 */
	private void buscarTodosOrdenados() {
		List<Categoria> categorias = repo.findAll(Sort.by("nombre").descending());
		for (Categoria c : categorias) {
			System.out.println(c.getId() + " " + c.getNombre());
		}
	}
	
	/**metodo 2
	 * Metodo deleteAllInBatch [Usar con precaución] - Interfaz JpaRepository 
	 */
	private void borrarTodoEnBloque() {
		repo.deleteAllInBatch(); 
	} 
	
	/** metodo 1
	 * Metodo findAll - Interfaz JpaRepository
	 */
	private void buscarTodosJpa() {
		List<Categoria> categorias = repo.findAll();
		for (Categoria c : categorias) {
			System.out.println(c.getId() + " " + c.getNombre());
		}
	}
	
	//guardar vaias entidades en una sola operacion
	private void guardarTodas() {
		 List<Categoria> categorias= getListaCategorias();
		    repo.saveAll(categorias);
			}
	
	//regresar verdadero o falso si existe una entidad con cierto id
	private void existeId() {
	    boolean existe= repo.existsById(50);
	    System.out.println("¿La categoría existe?  "+existe);
		}
	
	//buscar todas entidades o registros de nuestra tabla
	private void buscarTodos() {
		 Iterable<Categoria>categorias = repo.findAll();
		 for(Categoria cat: categorias) {
			 System.out.println(cat);
		 }
		}
	
	//encontrar por id
	private void encontrarPorIds() {
		List<Integer> ids= new LinkedList<Integer>();
		ids.add(1);
		ids.add(4);
		ids.add(10);
		Iterable<Categoria> categorias= repo.findAllById(ids);
		for(Categoria cat: categorias) {
			System.out.println(cat);
		}
		
		}
	
	//eliminar todas
	private void eliminarTodos() {
		 repo.deleteAll();
		}
	
	//conteo
	private void conteo() {
		Long count=repo.count();
		System.out.println("Total de categorias: "+count); 
	}
	
	//eliminar
	private void eliminar() {
		int idCategoria= 1;
		repo.deleteById(idCategoria);
	}
	
	//actualizar
	private void modificar() {
		Optional<Categoria> optional= repo.findById(2);
		if(optional.isPresent()) {
			System.out.println(optional.get());
			Categoria catTmp= optional.get();
			catTmp.setNombre("Ing. de software");
			catTmp.setDescripcion("Desarrollo de sistemas");
			repo.save(catTmp);
			System.out.println(optional.get());
			
		}else {
			System.out.println("Categoria no encontrada");
		}
	}
	
	
	private void buscarPorId() {
		Optional<Categoria> optional= repo.findById(5);
		if(optional.isPresent()) {
			System.out.println(optional.get());
		}else {
			System.out.println("Categoria no encontrada");
		}
	}
	
	//guardar
	private void guardar() {
		Categoria cat = new Categoria();
		cat.setNombre("Fiananzas");
		cat.setDescripcion("Trabajos relacionado scon finanzas y contabilidad");
		repo.save(cat);
		System.out.println(cat);
		
	}
	
	private List<Categoria> getListaCategorias() {
		   List<Categoria> lista= new LinkedList<Categoria>();
		   
		   Categoria cat1= new Categoria();
		    cat1.setNombre("Desarrollo web");
			cat1.setDescripcion("Define la creación de sitios web para Internet o una intranet");
			
			Categoria cat2= new Categoria();
		    cat2.setNombre("Marketing");
			cat2.setDescripcion("Conjunto de técnicas y estudios que tienen como objeto mejorar la comercialización de un producto.");
			
		  
		    lista.add(cat1);
		    lista.add(cat2);
		  
		    return lista;
		}
	
	
	
}
