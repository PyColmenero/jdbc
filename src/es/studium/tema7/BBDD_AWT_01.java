package es.studium.tema7;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BBDD_AWT_01 implements WindowListener, ActionListener {
        // Atributos, objetos, variables
        Frame ventana = new Frame("MisAmigos");
        TextField txtId = new TextField(20);
        TextField txtNombre = new TextField(20);
        TextField txtTelefono = new TextField(20);
        TextField txtDate = new TextField(20);
        Button btnSiguiente = new Button(">");
        Button btnAnterior = new Button("<");
        Button btnUltimo = new Button(">>");
        Button btnPrimer = new Button("<<");
        Button btnNew = new Button("New");
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://192.168.0.67:3306/ejemploTema7?serverTimezone=UTC";
        String login = "remotoDaw";
        String password = "Studium2019;";
        String sentencia = "SELECT * FROM amigos";
        Connection connection = null;
        Statement statement = null;
        Statement statementAlta = null;
        ResultSet rs = null;

        public BBDD_AWT_01() {
                ventana.setLayout(new FlowLayout());
                try {
                        //Cargar los controladores para el acceso a la BD
                        Class.forName(driver);
                        
                        //Establecer la conexión con la BD Empresa
                        connection = DriverManager.getConnection(url, login, password);
                        
                        //Crear una sentencia
                        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                        statementAlta = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                        
                        //Crear un objeto ResultSet para guardar lo obtenido
                        //y ejecutar la sentencia SQL
                        rs = statement.executeQuery(sentencia);
                        rs.next();
                        txtId.setText(rs.getInt("idAmigo")+"");
                        txtNombre.setText(rs.getString("nombreAmigo"));
                        txtTelefono.setText(rs.getInt("telefonoAmigo")+"");
                        txtDate.setText(rs.getDate("fechaNacimientoAmigo") + "" );
                        

                }
                catch (ClassNotFoundException cnfe) {
                        System.out.println("Error 1-"+cnfe.getMessage());
                }
                catch (SQLException sqle) {
                        System.out.println("Error 2-"+sqle.getMessage());
                }

                ventana.add(txtId);
                ventana.add(txtNombre);
                ventana.add(txtTelefono);
                ventana.add(txtDate);
                btnPrimer.addActionListener(this);
                btnAnterior.addActionListener(this);
                btnSiguiente.addActionListener(this);
                btnUltimo.addActionListener(this);
                btnNew.addActionListener(this);
                ventana.add(btnPrimer);
                ventana.add(btnAnterior);
                ventana.add(btnSiguiente);
                ventana.add(btnUltimo);
                ventana.add(btnNew);
                ventana.setSize(200,190);
                ventana.setResizable(false);
                ventana.setLocationRelativeTo(null);
                ventana.addWindowListener(this);
                ventana.setVisible(true);
        }

        public static void main(String[] args){
                new BBDD_AWT_01();
        }

        public void windowActivated(WindowEvent we) {}
        public void windowClosed(WindowEvent we) {}
        public void windowClosing(WindowEvent we) {
                System.exit(0);
        }
        public void windowDeactivated(WindowEvent we) {}
        public void windowDeiconified(WindowEvent we) {}
        public void windowIconified(WindowEvent we) {}
        public void windowOpened(WindowEvent we) {}
        public void actionPerformed(ActionEvent evento){
                if(evento.getSource().equals(btnSiguiente)){
                        try{
                        		if(rs.next()){
                                        txtId.setText(rs.getInt("idAmigo")+"");
                                        txtNombre.setText(rs.getString("nombreAmigo"));
                                        txtTelefono.setText(rs.getInt("telefonoAmigo")+"");
                                        txtDate.setText(rs.getDate("fechaNacimientoAmigo") + "" );
                                }
                                else{
                                        rs.last();
                                }
                        } 
                        catch (SQLException e) {
                                e.printStackTrace();
                        }
                }
                else if(evento.getSource().equals(btnAnterior)) {
                        try {
                                if(rs.previous()) {
                                        txtId.setText(rs.getInt("idAmigo")+"");
                                        txtNombre.setText(rs.getString("nombreAmigo"));
                                        txtTelefono.setText(rs.getInt("telefonoAmigo")+"");
                                        txtDate.setText(rs.getDate("fechaNacimientoAmigo") + "" );
                                }
                                else {
                                        rs.first();
                                }
                        } 
                        catch (SQLException e) {
                                e.printStackTrace();
                        }
                }
                else if(evento.getSource().equals(btnPrimer)) {
                        try {
                                if(rs.first()) {
                                        txtId.setText(rs.getInt("idAmigo")+"");
                                        txtNombre.setText(rs.getString("nombreAmigo"));
                                        txtTelefono.setText(rs.getInt("telefonoAmigo")+"");
                                        txtDate.setText(rs.getDate("fechaNacimientoAmigo") + "" );
                                }
                        } 
                        catch (SQLException e) {
                                e.printStackTrace();
                        }
                } else if(evento.getSource().equals(btnUltimo)) {
                        try {
                                if(rs.last()) {
                                        txtId.setText(rs.getInt("idAmigo")+"");
                                        txtNombre.setText(rs.getString("nombreAmigo"));
                                        txtTelefono.setText(rs.getInt("telefonoAmigo")+"");
                                        txtDate.setText(rs.getString("fechaNacimientoAmigo")  );
                                }
                        } 
                        catch (SQLException e) {
                                e.printStackTrace();
                        }
                } else if(evento.getSource().equals(btnNew)) {
	            
                	try {
                		
                		
                		
                		String name = txtNombre.getText();
                		int num = Integer.parseInt( txtTelefono.getText() );
                		String date = txtDate.getText();
                		String sentenciaInsert = "INSERT INTO amigos VALUES(NULL, '"+name+"', '"+date+"', "+num+" );";
                		
                		System.out.println(sentenciaInsert);
                		
                		statementAlta.execute(sentenciaInsert);
                		
                		rs = statement.executeQuery(sentencia);
                		
                		if(rs.last()) {
                            txtId.setText(rs.getInt("idAmigo")+"");
                            txtNombre.setText(rs.getString("nombreAmigo"));
                            txtTelefono.setText(rs.getInt("telefonoAmigo")+"");
                            txtDate.setText(rs.getString("fechaNacimientoAmigo")  );
                		}
                		
                	} catch(SQLException e) {
                		
                		e.printStackTrace();
                		
                	}
                }
        }
}


/*
 * package es.studium.BaseDatos;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MisAmigosAWT implements WindowListener, ActionListener
{
        // Atributos, objetos, variables
        Frame ventana = new Frame("MisAmigos");
        TextField txtId = new TextField(20);
        TextField txtNombre = new TextField(20);
        TextField txtTelefono = new TextField(20);
        Button btnSiguiente = new Button(">");
        Button btnAnterior = new Button("<");
        Button btnPrimero = new Button("<<");
        Button btnUltimo = new Button(">>");
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/amigos?serverTimezone=UTC";
        String login = "root";
        String password = "Studium2019;";
        String sentencia = "SELECT * FROM misamigos";
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        public MisAmigosAWT()
        {
                ventana.setLayout(new FlowLayout());
                txtId.setEditable(false);
                txtNombre.setEditable(false);
                txtTelefono.setEditable(false);
                try
                {
                        //Cargar los controladores para el acceso a la BD
                        Class.forName(driver);
                        //Establecer la conexión con la BD Empresa
                        connection = DriverManager.getConnection(url, login, password);
                        //Crear una sentencia
                        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                        ResultSet.CONCUR_READ_ONLY);
                        //Crear un objeto ResultSet para guardar lo obtenido
                        //y ejecutar la sentencia SQL
                        rs = statement.executeQuery(sentencia);
                        rs.next();
                        txtId.setText(rs.getInt("idAmigo")+"");
                        txtNombre.setText(rs.getString("nombreAmigo"));
                        txtTelefono.setText(rs.getInt("telefonoAmigo")+"");
                }
                catch (ClassNotFoundException cnfe)
                {
                        System.out.println("Error 1-"+cnfe.getMessage());
                }
                catch (SQLException sqle)
                {
                        System.out.println("Error 2-"+sqle.getMessage());
                }
                ventana.add(txtId);
                ventana.add(txtNombre);
                ventana.add(txtTelefono);
                btnSiguiente.addActionListener(this);
                btnAnterior.addActionListener(this);
                btnPrimero.addActionListener(this);
                btnUltimo.addActionListener(this);
                ventana.add(btnPrimero);
                ventana.add(btnAnterior);
                ventana.add(btnSiguiente);
                ventana.add(btnUltimo);
                ventana.setSize(200,150);
                ventana.setResizable(false);
                ventana.setLocationRelativeTo(null);
                ventana.addWindowListener(this);
                ventana.setVisible(true);
        }

        public static void main(String[] args)
        {
                new MisAmigosAWT();
        }

        public void windowActivated(WindowEvent we) {}
        public void windowClosed(WindowEvent we) {}
        public void windowClosing(WindowEvent we)
        {
                try
                {
                        if(connection!=null)
                        {
                                connection.close();
                        }
                }
                catch (SQLException error)
                {
                        System.out.println("Error 3-"+error.getMessage());
                }
                System.exit(0);
        }
        public void windowDeactivated(WindowEvent we) {}
        public void windowDeiconified(WindowEvent we) {}
        public void windowIconified(WindowEvent we) {}
        public void windowOpened(WindowEvent we) {}
        public void actionPerformed(ActionEvent evento)
        {
                if(evento.getSource().equals(btnSiguiente))
                {
                        try
                        {
                                if(rs.next())
                                {
                                        txtId.setText(rs.getInt("idAmigo")+"");
                                        txtNombre.setText(rs.getString("nombreAmigo"));
                                        txtTelefono.setText(rs.getInt("telefonoAmigo")+"");
                                }
                                else
                                {
                                        rs.last();
                                }
                        } 
                        catch (SQLException e)
                        {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
                }
                else if(evento.getSource().equals(btnAnterior))
                {
                        try
                        {
                                if(rs.previous())
                                {
                                        txtId.setText(rs.getInt("idAmigo")+"");
                                        txtNombre.setText(rs.getString("nombreAmigo"));
                                        txtTelefono.setText(rs.getInt("telefonoAmigo")+"");
                                }
                                else
                                {
                                        rs.first();
                                }
                        } 
                        catch (SQLException e)
                        {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
                }
                else if(evento.getSource().equals(btnPrimero))
                {
                        try
                        {
                                if(rs.first())
                                {
                                        txtId.setText(rs.getInt("idAmigo")+"");
                                        txtNombre.setText(rs.getString("nombreAmigo"));
                                        txtTelefono.setText(rs.getInt("telefonoAmigo")+"");
                                }
                        } 
                        catch (SQLException e)
                        {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
                }
                else if(evento.getSource().equals(btnUltimo))
                {
                        try
                        {
                                if(rs.last())
                                {
                                        txtId.setText(rs.getInt("idAmigo")+"");
                                        txtNombre.setText(rs.getString("nombreAmigo"));
                                        txtTelefono.setText(rs.getInt("telefonoAmigo")+"");
                                }
                        } 
                        catch (SQLException e)
                        {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
                }
        }
}
 * 
 * */
