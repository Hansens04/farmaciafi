package com.farmaciafinal.utils;
import com.farmaciafinal.models.*;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<Proveedor> listaProveedores = new ArrayList<>(
            List.of(
                    new Proveedor("Leterago","098681175","1","Carcelen",List.of("Paracetamol")),
                    new Proveedor("Acromax","0985498498","2","La Tola",List.of("Ibuprofeno"))
            )
    );
    public static List<EncabezadoFactura> listaEncabezadoFactura=new ArrayList<>();
    public static List<EncabezadoPedido> listaEncabezadoPedido=new ArrayList<>();

    public static List<Producto> listaProdcuto = new ArrayList<>();
    public static List<Cliente> listaCliente=new ArrayList<>();

}
