package upeu.edu.pe.venta.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import lombok.extern.slf4j.Slf4j;
import upeu.edu.pe.venta.dto.Producto;
import upeu.edu.pe.venta.dto.Usuario;
import upeu.edu.pe.venta.entity.Venta;
import upeu.edu.pe.venta.entity.VentaDetalle;
import upeu.edu.pe.venta.feign.ProductoFeing;
import upeu.edu.pe.venta.feign.UsuarioFeing;
import upeu.edu.pe.venta.repository.VentaDetalleRepository;
import upeu.edu.pe.venta.repository.VentaRepository;
import upeu.edu.pe.venta.service.VentaService;

//@Slf4j
@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    VentaRepository ventaRepository;

    @Autowired
    VentaDetalleRepository ventaDetallesRepository;
    @Autowired
    UsuarioFeing usuarioFeing;

    @Autowired
    ProductoFeing productoFeing;

    @Override
    public List<Venta> findVentaAll() {
        return  ventaRepository.findAll();
    }


    @Override
    public Venta createVenta(Venta venta) {
        Venta ventaDB = ventaRepository.findBySerie ( venta.getSerie () );
        if (ventaDB !=null){
            return  ventaDB;
        }
        venta.setState("CREATED");
        ventaDB = ventaRepository.save(venta);
        ventaDB.getDetalles().forEach( ventaDetalle -> {
            productoFeing.updateStockProducto( ventaDetalle.getProductId(), ventaDetalle.getQuantity() * -1);
        });

        return ventaDB;
    }


    @Override
    public Venta updateVenta(Venta venta) {
        Venta ventaDB = getVenta(venta.getId());
        if (ventaDB == null){
            return  null;
        }
        ventaDB.setUsuarioId(venta.getUsuarioId());
        ventaDB.setDescription(venta.getDescription());
        ventaDB.setSerie(venta.getSerie());
        ventaDB.getDetalles().clear();
        ventaDB.setDetalles(venta.getDetalles());
        return ventaRepository.save(ventaDB);
    }


    @Override
    public Venta deleteVenta(Venta venta) {
        Venta ventaDB = getVenta(venta.getId());
        if (ventaDB == null){
            return  null;
        }
        ventaDB.setState("DELETED");
        return ventaRepository.save(ventaDB);
    }

    @Override
    public Venta getVenta(Long id) {

        Venta venta= ventaRepository.findById(id).get();
        if (null != venta ){
            Usuario usuario = usuarioFeing.getUsuario(venta.getUsuarioId()).getBody();
            venta.setUsuario(usuario);
            List<VentaDetalle> listDetalle=venta.getDetalles().stream().map(ventaDetalle -> {
                Producto producto = productoFeing.getProducto(ventaDetalle.getProductId()).getBody();
                ventaDetalle.setProduct(producto);
                return ventaDetalle;
            }).collect(Collectors.toList());
            venta.setDetalles(listDetalle);
        }
        return venta ;
    }
}
