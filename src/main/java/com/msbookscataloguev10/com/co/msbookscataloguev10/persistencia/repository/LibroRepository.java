//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev10.com.co.msbookscataloguev10.persistencia.repository;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev10.com.co.msbookscataloguev10.persistencia.entity.Libro;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @Autor PD04. HERNAN ADOLFO NUÑEZ GONZALEZ.
 * @Since 02/02/2026.
 * Declaración de la entidad.
 * @Actualizacion David Paez 06/02/2026.
* DECLARACIÓN DE LA CLASE INTERFACE DEL REPOSITORIO QUIEN ES EL QUE HACE EL ENLACE DIRECTO HACIA LA BASE DE DATOS.
*/

public interface LibroRepository extends JpaRepository<Libro,Long>,
        JpaSpecificationExecutor<Libro> {


    List<Libro> findByEstadoLibro(String estado);

    //MÉTODO PARA BUSCAR LIBRO POR ID:
    Optional<Libro> findByIdLibro(Long idLibro);
    
    //LISTAR TODOS LOS LIBROS CON ORDENAMIENTO DINÁMICO:
    @Query(value = "SELECT * FROM tbl_libros ORDER BY " +
          "CASE WHEN :orderBy = 'titulo' THEN tbl_libros.titulo_libro END ASC, " +
          "CASE WHEN :orderBy = 'titulo' AND :orderMode = 'desc' THEN tbl_libros.titulo_libro END DESC, " +
          "CASE WHEN :orderBy = 'fecha' THEN tbl_libros.fecha_publicacion_libro END ASC, " +
          "CASE WHEN :orderBy = 'fecha' AND :orderMode = 'desc' THEN tbl_libros.fecha_publicacion_libro END DESC, " +
          "CASE WHEN :orderBy = 'precio' THEN tbl_libros.precio_libro END ASC, " +
          "CASE WHEN :orderBy = 'precio' AND :orderMode = 'desc' THEN tbl_libros.precio_libro END DESC, " +
          "CASE WHEN :orderBy IS NULL OR :orderBy = 'id' THEN tbl_libros.id_libro END ASC, " +
          "CASE WHEN :orderBy = 'id' AND :orderMode = 'desc' THEN tbl_libros.id_libro END DESC",
          nativeQuery = true)
    Slice<Libro> findAllLibrosWithOrder(@Param("orderBy") String orderBy, @Param("orderMode") String orderMode, Pageable pageable);
    
    //BUSCAR LIBROS POR KEYWORD CON ORDENAMIENTO DINÁMICO:
    @Query(value = "SELECT * FROM tbl_libros WHERE " +
          "(tbl_libros.titulo_libro LIKE CONCAT('%', :keyword, '%') OR " +
          "tbl_libros.fecha_publicacion_libro LIKE CONCAT('%', :keyword, '%') OR " +
          "tbl_libros.sinopsis_libro LIKE CONCAT('%', :keyword, '%') OR " +
          "tbl_libros.codigo_isbn_libro LIKE CONCAT('%', :keyword, '%') OR " +
          "CAST(tbl_libros.precio_libro AS CHAR) LIKE CONCAT('%', :keyword, '%') OR " +
          "tbl_libros.formato_libro LIKE CONCAT('%', :keyword, '%') OR " +
          "tbl_libros.estado_libro LIKE CONCAT('%', :keyword, '%') OR " +
          "tbl_libros.id_autor IN (SELECT id_autor FROM tbl_autores WHERE " +
          "nombres_autor LIKE CONCAT('%', :keyword, '%') OR " +
          "primer_apellido_autor LIKE CONCAT('%', :keyword, '%') OR " +
          "segundo_apellido_autor LIKE CONCAT('%', :keyword, '%'))) " +
          "ORDER BY " +
          "CASE WHEN :orderBy = 'titulo' THEN tbl_libros.titulo_libro END ASC, " +
          "CASE WHEN :orderBy = 'titulo' AND :orderMode = 'desc' THEN tbl_libros.titulo_libro END DESC, " +
          "CASE WHEN :orderBy = 'fecha' THEN tbl_libros.fecha_publicacion_libro END ASC, " +
          "CASE WHEN :orderBy = 'fecha' AND :orderMode = 'desc' THEN tbl_libros.fecha_publicacion_libro END DESC, " +
          "CASE WHEN :orderBy = 'precio' THEN tbl_libros.precio_libro END ASC, " +
          "CASE WHEN :orderBy = 'precio' AND :orderMode = 'desc' THEN tbl_libros.precio_libro END DESC, " +
          "CASE WHEN :orderBy IS NULL OR :orderBy = 'id' THEN tbl_libros.id_libro END ASC, " +
          "CASE WHEN :orderBy = 'id' AND :orderMode = 'desc' THEN tbl_libros.id_libro END DESC",
          nativeQuery = true)
    Slice<Libro> findLibrosByKeywordWithOrder(@Param("keyword") String keyword, @Param("orderBy") String orderBy, @Param("orderMode") String orderMode, Pageable pageable);
    
    //FILTRAR LIBROS POR CATEGORIA CON ORDENAMIENTO DINÁMICO:
    @Query(value =
            "SELECT * FROM tbl_libros " +
            "WHERE tbl_libros.id_libro IN (SELECT id_libro FROM tbl_libros_x_categorias WHERE id_categoria = :idCategoria) " +
            "ORDER BY " +
            "CASE WHEN :orderBy = 'titulo' THEN tbl_libros.titulo_libro END ASC, " +
            "CASE WHEN :orderBy = 'titulo' AND :orderMode = 'desc' THEN tbl_libros.titulo_libro END DESC, " +
            "CASE WHEN :orderBy = 'fecha' THEN tbl_libros.fecha_publicacion_libro END ASC, " +
            "CASE WHEN :orderBy = 'fecha' AND :orderMode = 'desc' THEN tbl_libros.fecha_publicacion_libro END DESC, " +
            "CASE WHEN :orderBy = 'precio' THEN tbl_libros.precio_libro END ASC, " +
            "CASE WHEN :orderBy = 'precio' AND :orderMode = 'desc' THEN tbl_libros.precio_libro END DESC, " +
            "CASE WHEN :orderBy IS NULL OR :orderBy = 'id' THEN tbl_libros.id_libro END ASC, " +
            "CASE WHEN :orderBy = 'id' AND :orderMode = 'desc' THEN tbl_libros.id_libro END DESC",
            nativeQuery = true)
    Slice<Libro> findLibrosByCategoriaWithOrder(
            @Param("idCategoria") Long idCategoria,
            @Param("orderBy") String orderBy,
            @Param("orderMode") String orderMode,
            Pageable pageable
    );
    
    //FILTRAR LIBROS POR KEYWORD Y CATEGORIA CON ORDENAMIENTO DINÁMICO:
    @Query(value =
            "SELECT * FROM tbl_libros " +
            "WHERE tbl_libros.id_libro IN (SELECT id_libro FROM tbl_libros_x_categorias WHERE id_categoria = :idCategoria) AND " +
            "(" +
            "tbl_libros.titulo_libro LIKE CONCAT('%', :keyword, '%') OR " +
            "tbl_libros.fecha_publicacion_libro LIKE CONCAT('%', :keyword, '%') OR " +
            "tbl_libros.sinopsis_libro LIKE CONCAT('%', :keyword, '%') OR " +
            "tbl_libros.codigo_isbn_libro LIKE CONCAT('%', :keyword, '%') OR " +
            "CAST(tbl_libros.precio_libro AS CHAR) LIKE CONCAT('%', :keyword, '%') OR " +
            "tbl_libros.formato_libro LIKE CONCAT('%', :keyword, '%') OR " +
            "tbl_libros.estado_libro LIKE CONCAT('%', :keyword, '%') OR " +
            "tbl_libros.id_autor IN (SELECT id_autor FROM tbl_autores WHERE " +
            "nombres_autor LIKE CONCAT('%', :keyword, '%') OR " +
            "primer_apellido_autor LIKE CONCAT('%', :keyword, '%') OR " +
            "segundo_apellido_autor LIKE CONCAT('%', :keyword, '%')" +
            ")" +
            ") " +
            "ORDER BY " +
            "CASE WHEN :orderBy = 'titulo' THEN tbl_libros.titulo_libro END ASC, " +
            "CASE WHEN :orderBy = 'titulo' AND :orderMode = 'desc' THEN tbl_libros.titulo_libro END DESC, " +
            "CASE WHEN :orderBy = 'fecha' THEN tbl_libros.fecha_publicacion_libro END ASC, " +
            "CASE WHEN :orderBy = 'fecha' AND :orderMode = 'desc' THEN tbl_libros.fecha_publicacion_libro END DESC, " +
            "CASE WHEN :orderBy = 'precio' THEN tbl_libros.precio_libro END ASC, " +
            "CASE WHEN :orderBy = 'precio' AND :orderMode = 'desc' THEN tbl_libros.precio_libro END DESC, " +
            "CASE WHEN :orderBy IS NULL OR :orderBy = 'id' THEN tbl_libros.id_libro END ASC, " +
            "CASE WHEN :orderBy = 'id' AND :orderMode = 'desc' THEN tbl_libros.id_libro END DESC",
            nativeQuery = true)
    Slice<Libro> findLibrosByKeywordAndCategoriaWithOrder(
            @Param("keyword") String keyword,
            @Param("idCategoria") Long idCategoria,
            @Param("orderBy") String orderBy,
            @Param("orderMode") String orderMode,
            Pageable pageable
    );
    
    //LISTAR LIBROS POR CRITERIOS INDIVIDUALES Y COMBINADOS CON LIKE Y PAGINACIÓN:
    @Query(value =
            "SELECT * FROM tbl_libros WHERE " +
            "(:tituloLibro IS NULL OR tbl_libros.titulo_libro LIKE CONCAT('%', :tituloLibro, '%')) AND " +
            "(:fechaPublicacionLibro IS NULL OR tbl_libros.fecha_publicacion_libro LIKE CONCAT('%', :fechaPublicacionLibro, '%')) AND " +
            "(:sinopsisLibro IS NULL OR tbl_libros.sinopsis_libro LIKE CONCAT('%', :sinopsisLibro, '%')) AND " +
            "(:codigoIsbnLibro IS NULL OR tbl_libros.codigo_isbn_libro LIKE CONCAT('%', :codigoIsbnLibro, '%')) AND " +
            "(:precioLibro IS NULL OR CAST(tbl_libros.precio_libro AS CHAR) LIKE CONCAT('%', :precioLibro, '%')) AND " +
            "(:formatoLibro IS NULL OR tbl_libros.formato_libro LIKE CONCAT('%', :formatoLibro, '%')) AND " +
            "(:estadoLibro IS NULL OR tbl_libros.estado_libro LIKE CONCAT('%', :estadoLibro, '%')) " +
            "ORDER BY " +
            "CASE WHEN :orderBy = 'titulo' THEN tbl_libros.titulo_libro END ASC, " +
            "CASE WHEN :orderBy = 'titulo' AND :orderMode = 'desc' THEN tbl_libros.titulo_libro END DESC, " +
            "CASE WHEN :orderBy = 'fecha' THEN tbl_libros.fecha_publicacion_libro END ASC, " +
            "CASE WHEN :orderBy = 'fecha' AND :orderMode = 'desc' THEN tbl_libros.fecha_publicacion_libro END DESC, " +
            "CASE WHEN :orderBy = 'precio' THEN tbl_libros.precio_libro END ASC, " +
            "CASE WHEN :orderBy = 'precio' AND :orderMode = 'desc' THEN tbl_libros.precio_libro END DESC, " +
            "CASE WHEN :orderBy IS NULL OR :orderBy = 'id' THEN tbl_libros.id_libro END ASC, " +
            "CASE WHEN :orderBy = 'id' AND :orderMode = 'desc' THEN tbl_libros.id_libro END DESC",
            nativeQuery = true)
    Slice<Libro> findLibrosByCriteriosIndividuales(
            @Param("tituloLibro") String tituloLibro,
            @Param("fechaPublicacionLibro") String fechaPublicacionLibro,
            @Param("sinopsisLibro") String sinopsisLibro,
            @Param("codigoIsbnLibro") String codigoIsbnLibro,
            @Param("precioLibro") String precioLibro,
            @Param("formatoLibro") String formatoLibro,
            @Param("estadoLibro") String estadoLibro,
            @Param("orderBy") String orderBy,
            @Param("orderMode") String orderMode,
            Pageable pageable
    );
}
