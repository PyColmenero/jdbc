package es.studium.tema7;


import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
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
import java.util.ArrayList;
import java.util.List;

public class MySoftware implements WindowListener, ActionListener {
	Frame ventana = new Frame("Mi Software");

	
	MenuBar mnBar = new MenuBar();
	
	// MENUS
	Menu mnuEmpleados = new Menu("Empleados");
	Menu mnuProyectos = new Menu("Proyectos");
	Menu mnuAsignaciones = new Menu("Asignaciones");
	Menu mnuClientes = new Menu("Clientes");
	
	// MENUS ITEMS
	MenuItem mniAltaCliente = new MenuItem("Alta");
	MenuItem mniBajaCliente = new MenuItem("Baja");
	MenuItem mniModificacionCliente = new MenuItem("Modificación");
	MenuItem mniConsultaCliente = new MenuItem("Consulta");
	MenuItem mniAltaEmpleado = new MenuItem("Alta");
	MenuItem mniBajaEmpleado = new MenuItem("Baja");
	MenuItem mniModificacionEmpleado = new MenuItem("Modificación");
	MenuItem mniConsultaEmpleado = new MenuItem("Consulta");
	MenuItem mniAltaProyecto = new MenuItem("Alta");
	MenuItem mniBajaProyecto = new MenuItem("Baja");
	MenuItem mniModificacionProyecto = new MenuItem("Modificación");
	MenuItem mniConsultaProyecto = new MenuItem("Consulta");
	MenuItem mniAltaAsignacion = new MenuItem("Alta");
	MenuItem mniBajaAsignacion = new MenuItem("Baja");
	MenuItem mniModificacionAsignacion = new MenuItem("Modificación");
	MenuItem mniConsultaAsignacion = new MenuItem("Consulta");
	
	// PANEL TABLE	
	Panel pnl_table = new Panel();
	
	// PANEL MANAGE
	Panel pnl_manage = new Panel();
	Label lbl_manage_title = new Label("Alta Cliente",1);
	Panel pnl_txtfields = new Panel();
	TextField tf_id_cliente = new TextField();
	TextField tf_name_cliente = new TextField();
	TextField tf_cif_cliente = new TextField();
	TextField tf_mail_cliente = new TextField();
	Label lbl_info = new Label();
	
	 List<String> clientes_data = new ArrayList<>();
	  
	

	static String driver = "com.mysql.cj.jdbc.Driver";
	static String url = "jdbc:mysql://localhost:3306/mysoftware?serverTimezone=UTC";
	static String login = "root";
	static String password = "Studium2020;";
	static String sentencia = "";
	public static Connection connection = null;
	public static Statement statement = null;
	public ResultSet rs = null;

	public MySoftware() {
		
		System.out.println(Integer.MAX_VALUE);
				
		
		ventana.setLayout(new FlowLayout());
		ventana.setBackground( Color.DARK_GRAY );
		mniAltaCliente.addActionListener(this);
		mniBajaCliente.addActionListener(this);
		mnuClientes.add(mniAltaCliente);
		mnuClientes.add(mniBajaCliente);
		mnuClientes.add(mniModificacionCliente);
		mnuClientes.add(mniConsultaCliente);
		mnBar.add(mnuClientes);

		mnuEmpleados.add(mniAltaEmpleado);
		mnuEmpleados.add(mniBajaEmpleado);
		mnuEmpleados.add(mniModificacionEmpleado);
		mnuEmpleados.add(mniConsultaEmpleado);
		mnBar.add(mnuEmpleados);

		mnuProyectos.add(mniAltaProyecto);
		mnuProyectos.add(mniBajaProyecto);
		mnuProyectos.add(mniModificacionProyecto);
		mnuProyectos.add(mniConsultaProyecto);
		mnBar.add(mnuProyectos);

		mnuAsignaciones.add(mniAltaAsignacion);
		mnuAsignaciones.add(mniBajaAsignacion);
		mnuAsignaciones.add(mniModificacionAsignacion);
		mnuAsignaciones.add(mniConsultaAsignacion);
		mnBar.add(mnuAsignaciones);

		ventana.setMenuBar(mnBar);
		
		// PANEL TABLE
		pnl_table.setBackground( Color.DARK_GRAY);
		pnl_table.setPreferredSize( new Dimension(300,450) );
		
		load_clientes_table("");
		
		ventana.add( pnl_table );
		
		
		// PANEL MANAGE
		pnl_manage.setLayout( new GridLayout(3,1) );
		pnl_manage.setBackground( Color.gray);
		
		lbl_manage_title.setFont( new Font("Comic Sans", 0, 40) );
		pnl_manage.add(lbl_manage_title);
		
		pnl_txtfields.add(tf_id_cliente);
		pnl_txtfields.add(tf_name_cliente);
		pnl_txtfields.add(tf_cif_cliente);
		pnl_txtfields.add(tf_mail_cliente);
		tf_id_cliente.setPreferredSize( new Dimension( 70 , 25) );
		tf_name_cliente.setPreferredSize( new Dimension( 250 , 25) );
		tf_cif_cliente.setPreferredSize( new Dimension( 70 , 25) );
		tf_mail_cliente.setPreferredSize( new Dimension( 250 , 25) );
		pnl_manage.add(pnl_txtfields);
		
		pnl_manage.add( lbl_info );
		pnl_manage.setPreferredSize( new Dimension(674,450));
		ventana.add( pnl_manage );
		
		

		ventana.setSize(1000, 500);
		//ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.addWindowListener(this);
		ventana.setVisible(true);
		
	}

	private void load_clientes_table(String string) {

		connection = conectar();
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			sentencia = "SELECT * FROM clientes";
			rs = statement.executeQuery(sentencia);
			
			while (rs.next())
			{
				int idCliente = rs.getInt("idCliente");
				String nameCliente = rs.getString("nombreCliente");
				String cifCliente = rs.getString("cifCliente");
				String mailCliente = rs.getString("mailCliente");
				
				String total_cliente = idCliente + " - " + nameCliente + " - " + cifCliente  + " - " + mailCliente ;
				
				clientes_data.add(total_cliente);
				
				Button lbl_current_cliente = new Button( nameCliente + ": " + cifCliente  + " - " + mailCliente );
				lbl_current_cliente.setPreferredSize( new Dimension(280,20));
				lbl_current_cliente.addActionListener(this);
				/*lbl_current_cliente.setBackground( Color.white );*/

				/*lbl_current_cliente.setFont( new Font("Arial", 1, 10) );
				lbl_current_cliente.setForeground( Color.white );*/
				
				pnl_table.add( lbl_current_cliente );

				System.out.println(  );

			}
		} catch (SQLException sqle) {
			System.out.println("Error 2-" + sqle.getMessage());
		}
		
		
	}

	public static void main(String[] args) {
		new MySoftware();
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

	public void actionPerformed(ActionEvent evento) {
		
		if (evento.getSource().equals(mniAltaCliente)) {
			
			lbl_manage_title.setText("Alta Cliente");
			
		} else if(evento.getSource().equals(mniBajaCliente)) {
			
			lbl_manage_title.setText("Baja Cliente");
			
		} else if(evento.getSource().equals(mniModificacionCliente)) {
					
			lbl_manage_title.setText("Baja Cliente");
			
		} else {
			
			String id_cliente = evento.getActionCommand().split("-")[0];
			String name_cliente = evento.getActionCommand().split("-")[1];
			String cif_cliente = evento.getActionCommand().split("-")[2];
			String mail_cliente = evento.getActionCommand().split("-")[3];
			
			tf_id_cliente.setText(id_cliente);
			tf_name_cliente.setText(name_cliente);
			tf_cif_cliente.setText(cif_cliente);
			tf_mail_cliente.setText(mail_cliente);

		}
	}

	public static Connection conectar() {
		try {
			
			//Cargar los controladores para el acceso a la BD
			Class.forName(driver);
			//Establecer la conexión con la BD Empresa
			connection = DriverManager.getConnection(url, login, password);
			
		} catch (ClassNotFoundException cnfe) {
			
			System.out.println("Error 1-" + cnfe.getMessage());
			
		} catch (SQLException sqle) {
			
			System.out.println("Error 2-" + sqle.getMessage());
			
		}
		
		return connection;
	}
}
