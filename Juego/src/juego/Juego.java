/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author andre
 */
public class Juego extends Canvas implements Runnable{

    private static final long serialVersionUID=1L;
    
    private static final int ANCHO  =800;
    private static final int ALTO  =600;
    private static volatile boolean enFuncionamiento=false;
    private static final String NOMBRE  ="juego";
    private static JFrame ventana;
    private static Thread thread;
    
    private Juego(){
        setPreferredSize(new Dimension (ANCHO,ALTO)); 
        ventana= new JFrame(NOMBRE);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setLayout(new BorderLayout());
        ventana.add(this,BorderLayout.CENTER);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
          
    }
    
    
    public static void main(String[] args) {
        Juego juego= new Juego();
        juego.iniciar();
        
    }
    private synchronized void iniciar(){
        enFuncionamiento=true;
        thread = new Thread(this, "Graficos"); 
        thread.start();
    
    }
    private synchronized void detener(){
        enFuncionamiento=false;
        
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void actualizar(){
    }
    private void mostrar(){
    }
    @Override
    public void run() {
        
        final int NS_POR_SEGUNDO = 1000000000;
        final byte APS_OBJETIVO =60;
        final double NS_POR_ACTUALIZACION = NS_POR_SEGUNDO/APS_OBJETIVO;
        
        long referenciaActualizacion = System.nanoTime();
        
        double tiempoTranscurrido;
        double delta=0;
        
        while (enFuncionamiento)  {
            
            final long inicioBucle= System.nanoTime();
            tiempoTranscurrido= inicioBucle-referenciaActualizacion;
            referenciaActualizacion= inicioBucle;
            
            delta+= tiempoTranscurrido/NS_POR_ACTUALIZACION;
            while(delta>=1){
                actualizar();
                delta--;
            
            }
            
            
            mostrar();
                
        }
    }
}
