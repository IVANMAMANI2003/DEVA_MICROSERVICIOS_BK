package upeu.edu.pe.catalogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import upeu.edu.pe.catalogo.entity.Categoria;
import upeu.edu.pe.catalogo.entity.Producto;
@Repository
public interface ProductoRepository  extends JpaRepository<Producto, Long> {

    public List<Producto> findByCategory(Categoria categoria);
}
