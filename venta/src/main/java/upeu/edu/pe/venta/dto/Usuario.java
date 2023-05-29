package upeu.edu.pe.venta.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Usuario {
    private Long id;


    private String firstName;

    private String lastName;

    private String numberID;

    private String email;


    private String contraseña;

    private String photoUrl;

    private String rol;
}
