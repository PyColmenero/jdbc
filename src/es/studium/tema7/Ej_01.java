package es.studium.tema7;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Ej_01 {

	public static void main(String[] args) {

		// Driver
		// Conection
		// Statement
		// ResultSet

		// DRIVER
		String driver = "com.mysql.cj.jdbc.Driver";
		
		// URL
		String ip = "localhost";
		String port = "3306";
		String database = "quoridor";
		String url = "jdbc:mysql://"+ip+":"+port+"/"+database;
		
		// LOGIN
		String login = "root";
		String password = "Studium2020;";
		
		String sentencia = "SELECT * FROM test";
		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		try{
			
			//Cargar los controladores para el acceso a la BD
			Class.forName(driver);
			
			//Establecer la conexión con la BD Empresa
			connection = DriverManager.getConnection(url, login, password);
			
			//Crear una sentencia
			statement = connection.createStatement();
			
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);

			while (rs.next())
			{
				int idTest = rs.getInt("idTest");
				String nameTest = rs.getString("nameTest");
				int numTest = rs.getInt("numTest");
				
				if(nameTest.charAt(0) == 'c') {
					System.out.println( idTest + " - " + nameTest + " - " + numTest  + " - " + rs.getString("dateTest") );
				} else {
					
				}
				
				
			}
			
		}
		catch (ClassNotFoundException cnfe){
			System.out.println("Error 1-"+cnfe.getMessage());
		}
		catch (SQLException sqle){
			System.out.println("Error 2-"+sqle.getMessage());
		}
		finally{
			try{
				if(connection!=null){
					connection.close();
				}
			}
			catch (SQLException e)
			{
				System.out.println("Error 3-"+e.getMessage());
			}
		}


	}

}
