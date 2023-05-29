package upeu.edu.pe.venta.service;

import java.util.List;

import upeu.edu.pe.venta.entity.Venta;

public interface VentaService {
    public List<Venta> findVentaAll();

    public Venta createVenta(Venta venta);
    public Venta updateVenta(Venta venta);
    public Venta deleteVenta(Venta venta);

    public Venta getVenta(Long id);
}
