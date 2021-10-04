//Gerardo Demian Mora Hernandez
//22/04/2021
//Topicos Avanzados
//Práctica 2.3 Sockets
package Sockets;

import java.awt.event.*;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.net.*;
import java.util.logging.*;

public class Cliente {
 
    public static void main(String[] args) {
      VentanaCliente ventana= new VentanaCliente();
      ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}
//Ventana Del Cliente
class VentanaCliente extends JFrame{
    public VentanaCliente(){
    setBounds(600,300,280,350);
    PanelVentanaCliente panel=new PanelVentanaCliente();
    add(panel);
    setVisible (true);
    setLocationRelativeTo(null);
    }

   
}
class PanelVentanaCliente extends JPanel implements Runnable {
    private JTextField area,name,ip;
    private JButton send;
    private JTextArea chat;
    
    public PanelVentanaCliente(){
        name =new JTextField (5);
        add(name);
        JLabel txt=new JLabel ("-Chat-");
        add(txt);
        ip =new JTextField(8);
        add(ip);
        chat =new JTextArea(12,20);
        add(chat);
        area=new JTextField(20);
        add(area);
        send=new JButton ("Enviar");
        SendTxt evt_send=new SendTxt();
        send.addActionListener(evt_send);
        add(send);
        
        Thread hilo=new Thread(this);
        hilo.start();

}

    @Override
    public void run() {
        try{
        ServerSocket server_cliente =new ServerSocket(9090);
        Socket cliente;
        sendMsm msmres ;
        
        while(true){
            cliente=server_cliente.accept();
            ObjectInputStream entrada=new ObjectInputStream(cliente.getInputStream());
            msmres=(sendMsm) entrada.readObject();
            chat.append("\n"+ msmres.getname() + " : "+msmres.getmsm());
        
        }
        }catch(Exception ex){
        System.out.println(ex.getMessage());
        }
    }
    //Envia el mensaje
   private class SendTxt implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                Socket socket=new Socket("192.168.56.1",9999);
               //Objeto
                sendMsm date =new sendMsm();
               date.setname(name.getText());
               date.setip(ip.getText());
               date.setmsm(area.getText());
               //Flujo
               ObjectOutputStream pack_date =new ObjectOutputStream(socket.getOutputStream());
               pack_date.writeObject(date);
               socket.close();
                /*DataOutputStream txt_salida=new DataOutputStream(socket.getOutputStream());
                txt_salida.writeUTF(area.getText());
                txt_salida.close();*/
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            
      
          
        }
   
   }
}
//Envio de Mensaje a Diferente IP
class sendMsm implements Serializable{ //el Serializable es para poder conectar por tipo de baite
private String name,ip,msm;

public String getname(){
return name;
}

public void setname(String name){
this.name = name;
}

public String getip(){
return ip;
}
public void setip(String ip ){
this.ip=ip;
}
public String getmsm(){
return msm;
}
public void setmsm(String msm){
this.msm=msm;
}

}
