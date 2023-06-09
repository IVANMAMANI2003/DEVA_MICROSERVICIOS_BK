package upeu.edu.pe.venta.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import upeu.edu.pe.venta.dto.Producto;

@FeignClient(name = "catalogo-service", path = "/producto")
public interface ProductoFeing {

    @GetMapping(value = "/{id}")
    public ResponseEntity<Producto> getProducto(@PathVariable("id") Long id);

    @GetMapping(value = "/{id}/stock")
    public ResponseEntity<Producto> updateStockProducto(@PathVariable  Long id ,@RequestParam(name = "quantity", required = true) Double quantity);
    }
