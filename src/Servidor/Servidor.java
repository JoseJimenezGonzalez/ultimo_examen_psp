package Servidor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor extends Thread{
    private Socket socketCliente;
    public Servidor(Socket socket){
        this.socketCliente = socket;
    }
    @Override
    public void run(){
        try {
            System.out.println("Arrancando hilo");
            //Creamos los flujos para pasar datos
            OutputStream salida = socketCliente.getOutputStream();
            InputStream entrada = socketCliente.getInputStream();
            //Recibimos el mensaje del cliente
            byte[] mensajeRecibido = new byte[1024];
            entrada.read(mensajeRecibido);
            String mensajeRecibidoStr = new String(mensajeRecibido).trim();
            System.out.println("Hemos recibido mensaje de cliente:");
            System.out.println(mensajeRecibidoStr);
            //Hacemos tratamiento al mensaje
            String mensajeEnviarStr = mensajeRecibidoStr.toUpperCase();
            //Enviamos el mensaje
            salida.write(mensajeEnviarStr.getBytes());
            System.out.println("Mensaje enviado");
            //Cerramos la conexion del subsocket
            socketCliente.close();
            System.out.println("Se ha cerrado la conexión del subsocket");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        //Creamos el socket de servidor
        ServerSocket socketServidor = new ServerSocket();
        InetSocketAddress direccionServidor = new InetSocketAddress("localhost", 55555);
        socketServidor.bind(direccionServidor);
        System.out.println("Se ha creado el socker server");
        System.out.println("Aceptando conexiones");
        while(socketServidor != null){
            //Creacion del subsocket
            Socket subSocket = socketServidor.accept();
            Servidor hilo = new Servidor(subSocket);
            hilo.start();
        }
        System.out.println("Ha finalizado la aplicacion");
    }
}
