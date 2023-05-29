package upeu.edu.pe.venta.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import upeu.edu.pe.venta.entity.Venta;
import upeu.edu.pe.venta.service.VentaService;

@Slf4j
@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    VentaService ventaService;

    // -------------------Retrieve All Invoices--------------------------------------------
    @GetMapping
    public ResponseEntity<List<Venta>> listAllInvoices() {
        List<Venta> ventas = ventaService.findVentaAll();
        if (ventas.isEmpty()) {
            return  ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(ventas);
    }

    // -------------------Retrieve Single Venta------------------------------------------
    @GetMapping(value = "/{id}")
    public ResponseEntity<Venta> getVenta(@PathVariable("id") long id) {
        log.info("Fetching Venta with id {}", id);
        Venta venta  = ventaService.getVenta(id);
        if (null == venta) {
            log.error("Venta with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(venta);
    }

    // -------------------Create a Venta-------------------------------------------
    @PostMapping
    public ResponseEntity<Venta> createVenta(@Valid @RequestBody Venta venta, BindingResult result) {
        log.info("Creating Venta : {}", venta);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Venta ventaDB = ventaService.createVenta (venta);

        return  ResponseEntity.status( HttpStatus.CREATED).body(ventaDB);
    }

    // ------------------- Update a Venta ------------------------------------------------
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateVenta(@PathVariable("id") long id, @RequestBody Venta venta) {
        log.info("Updating Venta with id {}", id);

        venta.setId(id);
        Venta currentInvoice=ventaService.updateVenta(venta);

        if (currentInvoice == null) {
            log.error("Unable to update. Venta with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(currentInvoice);
    }

    // ------------------- Delete a Venta-----------------------------------------
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Venta> deleteVenta(@PathVariable("id") long id) {
        log.info("Fetching & Deleting Venta with id {}", id);

        Venta venta = ventaService.getVenta(id);
        if (venta == null) {
            log.error("Unable to delete. Venta with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        venta = ventaService.deleteVenta(venta);
        return ResponseEntity.ok(venta);
    }

    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String> error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
