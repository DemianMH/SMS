package Sockets;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.logging.*;
import javax.swing.*;

public class Servidor {
     public static void main(String[] args) {
      VentanaServidor ventana2= new VentanaServidor();
      ventana2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
     
}
//Ventana Servidor
class VentanaServidor extends JFrame implements Runnable{
    private JTextArea areatxt;
    public VentanaServidor(){
        setBounds (1200,300,280,350);
        JPanel panel =new JPanel();
        panel.setLayout(new BorderLayout());
        areatxt =new JTextArea();
        panel.add(areatxt,BorderLayout.CENTER);
        add(panel);
        setVisible(true);
        setLocationRelativeTo(null);
        Thread hilo=new Thread(this);
        hilo.start();
    }

    @Override
    //Escucha
    public void run() {
        try {
            ServerSocket servidor=new ServerSocket(9999);
            String name,ip,msm;
            sendMsm msmres ;
            while (true){
            Socket socket =servidor.accept();
            ObjectInputStream pack_date =new ObjectInputStream (socket.getInputStream());
            msmres=(sendMsm) pack_date.readObject();
            name=msmres.getname();
            ip=msmres.getip();
            msm=msmres.getmsm();
            /*DataInputStream txt_entrada =new DataInputStream(socket.getInputStream());
            String mensaje_txt = txt_entrada.readUTF();
            areatxt.append("\n"+mensaje_txt);*/
            areatxt.append("\n"+name+": "+msm+" para "+ip);
            //Se crea el puente
            Socket sendDest=new Socket(ip,9090);
            ObjectOutputStream send_pack =new ObjectOutputStream (sendDest.getOutputStream());
            send_pack.writeObject(msmres);
            sendDest.close();
            socket.close();}
            
        } catch (IOException  ex) {
            Logger.getLogger(VentanaServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VentanaServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
}
