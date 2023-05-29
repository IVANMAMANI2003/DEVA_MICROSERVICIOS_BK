package upeu.edu.pe.catalogo.service;

import java.util.List;

import upeu.edu.pe.catalogo.entity.Categoria;
import upeu.edu.pe.catalogo.entity.Producto;

public interface ProductoService {
    public List<Producto> listAllProducto();
    public Producto getProducto(Long id);

    public Producto createProducto(Producto producto);
    public Producto updateProducto(Producto producto);
    public  Producto deleteProducto(Long id);
    public List<Producto> findByCategoria(Categoria categoria);
    public Producto updateStock(Long id, Double quantity);
}
