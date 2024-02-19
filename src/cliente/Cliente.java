package cliente;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) throws IOException {
        Scanner teclado = new Scanner(System.in);
        //Creamos el socket de cliente
        System.out.println("Creamos el socket de cliente");
        Socket socketCliente = new Socket();
        InetSocketAddress direccionServidor = new InetSocketAddress("localhost", 55555);
        socketCliente.connect(direccionServidor);
        System.out.println("Conexión con servidor establecida");
        //Creamos los flujos de datos
        OutputStream salida = socketCliente.getOutputStream();
        InputStream entrada = socketCliente.getInputStream();
        //Enviar mensaje a servidor
        System.out.println("Introduce mensaje a enviar al servidor");
        String mensajeEnviarStr = teclado.nextLine();
        salida.write(mensajeEnviarStr.getBytes());
        //Recibir respuesta de servidor
        System.out.println("Respuesta de servidor:");
        byte[] mensajeRecibido = new byte[1024];
        entrada.read(mensajeRecibido);
        String mensajeRecibidoStr = new String(mensajeRecibido).trim();
        System.out.println(mensajeRecibidoStr);
        //Cerramos el cliente
        socketCliente.close();
        System.out.println("Se ha cerrado el socket de cliente");
    }
}
