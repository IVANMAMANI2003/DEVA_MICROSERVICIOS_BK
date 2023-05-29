package upeu.edu.pe.venta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import upeu.edu.pe.venta.entity.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    public List<Venta> findByUsuarioId(Long usuarioId );
    public Venta findByNumberVenta(String numberVenta);
}
