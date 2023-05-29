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
import upeu.edu.pe.venta.feing.ProductoFeing;
import upeu.edu.pe.venta.feing.UsuarioFeing;
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
        Venta invoiceDB = ventaRepository.findBySerie ( venta.getSerie () );
        if (invoiceDB !=null){
            return  invoiceDB;
        }
        venta.setState("CREATED");
        invoiceDB = ventaRepository.save(venta);
        invoiceDB.getDetalles().forEach( invoiceItem -> {
            productoFeing.updateStockProducto( invoiceItem.getProductId(), invoiceItem.getQuantity() * -1);
        });

        return invoiceDB;
    }


    @Override
    public Venta updateVenta(Venta venta) {
        Venta invoiceDB = getVenta(venta.getId());
        if (invoiceDB == null){
            return  null;
        }
        invoiceDB.setUsuarioId(venta.getUsuarioId());
        invoiceDB.setDescription(venta.getDescription());
        invoiceDB.setSerie(venta.getSerie());
        invoiceDB.getDetalles().clear();
        invoiceDB.setDetalles(venta.getDetalles());
        return ventaRepository.save(invoiceDB);
    }


    @Override
    public Venta deleteVenta(Venta venta) {
        Venta invoiceDB = getVenta(venta.getId());
        if (invoiceDB == null){
            return  null;
        }
        invoiceDB.setState("DELETED");
        return ventaRepository.save(invoiceDB);
    }

    @Override
    public Venta getVenta(Long id) {

        Venta venta= ventaRepository.findById(id).orElse(null);
        if (null != venta ){
            Usuario usuario = usuarioFeing.getUsuario(venta.getUsuarioId()).getBody();
            venta.setUsuario(usuario);
            List<VentaDetalle> listItem=venta.getDetalles().stream().map(invoiceItem -> {
                Producto producto = productoFeing.getProducto(invoiceItem.getProductId()).getBody();
                invoiceItem.setProduct(producto);
                return invoiceItem;
            }).collect(Collectors.toList());
            venta.setDetalles(listItem);
        }
        return venta ;
    }
}
