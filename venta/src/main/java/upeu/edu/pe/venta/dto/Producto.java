package upeu.edu.pe.venta.dto;

import lombok.Data;

@Data
public class Producto {
    private Long id;

    private String name;
    private String description;

    private Double stock;
    private Double price;
    private String status;

    private Categoria categoria;
    private String imagen;
    private String material;
    private String largo;
    private String ancho;
    private String alto;

    
}
