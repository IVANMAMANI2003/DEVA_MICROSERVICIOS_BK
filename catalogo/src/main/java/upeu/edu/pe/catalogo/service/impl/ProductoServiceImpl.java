package upeu.edu.pe.catalogo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import upeu.edu.pe.catalogo.entity.Categoria;
import upeu.edu.pe.catalogo.entity.Producto;
import upeu.edu.pe.catalogo.repository.ProductoRepository;
import upeu.edu.pe.catalogo.service.ProductoService;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl  implements ProductoService{


    private final ProductoRepository productoRepository;

    @Override
    public List<Producto> listAllProducto() {
        return productoRepository.findAll();
    }

    @Override
    public Producto getProducto(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public Producto createProducto(Producto product) {
        product.setStatus("CREATED");
        product.setCreateAt(new Date());

        return productoRepository.save(product);
    }

    @Override
    public Producto updateProducto(Producto product) {
        Producto productoDB = getProducto(product.getId());
        if (null == productoDB){
            return null;
        }
        productoDB.setName(product.getName());
        productoDB.setDescription(product.getDescription());
        productoDB.setCategory(product.getCategory());
        productoDB.setPrice(product.getPrice());
        return productoRepository.save(productoDB);
    }

    @Override
    public Producto deleteProducto(Long id) {
        Producto productoDB = getProducto(id);
        if (null == productoDB){
            return null;
        }
        productoDB.setStatus("DELETED");
        return productoRepository.save(productoDB);
    }

    @Override
    public List<Producto> findByCategoria(Categoria categoria) {
        return productoRepository.findByCategory(categoria);
    }

    @Override
    public Producto updateStock(Long id, Double quantity) {
        Producto productoDB = getProducto(id);
        if (null == productoDB){
            return null;
        }
        Double stock =  productoDB.getStock() + quantity;
        productoDB.setStock(stock);
        return productoRepository.save(productoDB);
    }
}
