public class Compra{
    private int pedido_Id;
    private String fecha;
    private String nombre;
    private String correo;
    private String telefono;
    private String dni;
    private boolean conPedido;
    private String direccionEnvio;
    private String nombreRecibidor;
    private String medioPago;
    private String producto;
    private int cantidadProducto;
    private double precioUnitario;
    private double precioEnvio;
    private double precioTotal;


    public Compra(int pedido_Id,
                String fecha,
                String nombre,
                String correo,
                String telefono,
                String dni,
                boolean conPedido,
                String direccionEnvio,
                String nombreRecibidor,
                String medioPago,
                String producto,
                int cantidadProducto,
                double precioUnitario,
                double precioEnvio,
                double precioTotal){

        this.pedido_Id = pedido_Id;
        this.fecha = fecha;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.dni = dni;
        this.conPedido = conPedido;
        this.direccionEnvio = direccionEnvio;
        this.nombreRecibidor = nombreRecibidor;
        this.medioPago = medioPago;



    }

    public String toString(){
        String out = "";

        return out;
    }

}
