package arduinoswitch;

import Clases.Conexion;
import Clases.contact;
import Clases.mail;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.awt.Color;
import java.awt.Font;
import static java.lang.Thread.sleep;
import java.sql.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import panamahitek.Arduino.PanamaHitek_Arduino;


public class Window extends javax.swing.JFrame {

    PanamaHitek_Arduino Arduino = new PanamaHitek_Arduino();
    Controls c = new Controls();
    String estado = "";
    boolean fina = false;
    boolean finas = false;
    Conexion conn = new Conexion();
    Timer time = new Timer();
    ArrayList<contact> contact = new ArrayList<>();
    String mensaje = "";
    contact contss =new contact();
    boolean p = true;
    static String gene = "";
    SerialPortEventListener listener = new SerialPortEventListener() {
        @Override
        public void serialEvent(SerialPortEvent spe) {
            //To change body of generated methods, choose Tools | Templates.
            if (Arduino.isMessageAvailable()) {
                estado = Arduino.printMessage().toLowerCase().toString();
                if (estado.toString().equals("send")) {
                    
                    try {
                        fina = true;
                        int c = mensajeCompleto();
                        if(c != 0)
                        {
                         conn.InsertRegistros(c);
                         System.out.println("El Spam Finalizo");
                         System.out.println("Envios: " + c );
                         Arduino.killArduinoConnection();
                         System.exit(0);
                        }
                       
                    } catch (Exception ex) {
                       
                        Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
               
                System.out.println(estado);


            }
        }
        
    };
  
    public Window() throws ParseException, Exception {
        initComponents();
        if(gene != null)
        {       contact = conn.cargarContacto();
                cargarTablaTodos(contact);
            try {
            Arduino.arduinoRXTX("COM3", 9600, listener);
             } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,"NO SE ENCUENTRA SIM900");
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            }
     
        }

            

    }
    ////////////Cargar Tablas //////////////
    private void cargarTablaTodos(ArrayList<contact> contact) {
        DefaultTableModel dtm = new DefaultTableModel();
        String[] nombreDeColumnas = {"Telefono","Mensaje","estado","id"};
        dtm.setColumnIdentifiers(nombreDeColumnas);

        for (contact v : contact) {
            Object[] row = {v.getNumeroTel(),v.getMensaje(),v.isEstado(),v.getId()};
            dtm.addRow(row);
        }

        tbtContactos.setModel(dtm);

    }
    ////////////Cargar Mensajes //////////////
    public int mensajeCompleto() throws Exception {
      
        String numer = "";
        String text = "";
        int comp  = 0;
        short count = 0;
        int cont = 0;
            for (int i = 0; i < tbtContactos.getRowCount() ; i++) {
            try {
                        numer= tbtContactos.getValueAt(i,0).toString();
                        text = tbtContactos.getValueAt(i,1).toString();
                        mensaje = numer + "#" + text;
                        if(count == 4){
                            try{
                        Arduino.killArduinoConnection();
                        count = 0;
                        sleep(5000);
                        Arduino.arduinoRXTX("COM3", 9600, listener);
                        sleep(20000);
                                 }
                        catch (Exception e){
                         comp = conn.UpdateLog((int) tbtContactos.getValueAt(i,3),e.getMessage());                            
                                             }
                        }
                        if(tbtContactos.getValueAt(i,2).toString().equals("false")){
                        System.out.println(mensaje);
                        System.out.println(estado);
                        Arduino.sendData(mensaje);
                        conn.UpdatenNumero((int) tbtContactos.getValueAt(i,3));
                        cont++;
                        numer="";
                        text="";
                        count ++;
                        sleep(25000);
                        String es = "Este numero ya recibio su mensaje";
                        comp = conn.UpdateLog((int) tbtContactos.getValueAt(i,3), es); 
                        }
                        else{
  
                        }
                        
                        
                    } catch (Exception ex) {
                        conn.UpdateLog((int) tbtContactos.getValueAt(i,3),ex.getMessage());    
                    }
                }
          
       
        
        return cont;
    }
       ///////////Obtener Puertos //////////////
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbtContactos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FBCO");
        setBackground(new java.awt.Color(51, 51, 51));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel1MouseMoved(evt);
            }
        });

        tbtContactos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tbtContactos);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 709, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseMoved

    }//GEN-LAST:event_jPanel1MouseMoved

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
      String a = args[0];
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    if(a.equals("1"))
                    {
                         gene = a;
                    }
                    break;
                    
                   
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Window().setVisible(true);
                } catch (ParseException ex) {
                    Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbtContactos;
    // End of variables declaration//GEN-END:variables

}
