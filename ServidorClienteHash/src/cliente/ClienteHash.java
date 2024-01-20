package cliente;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.nio.file.Files;

public class ClienteHash {

	public static void main(String[] args) {
		int PUERTO = 9999;
		try (Socket s = new Socket("localhost", PUERTO);){
			
			ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			System.out.println("Resumen:\n");
			
			//String
			String str = "String de prueba";
			out.writeObject(str.getBytes());
			out.flush();
			System.out.println("Hash del string: " + in.readLine());
			Thread.sleep(2000);
			
			//Archivo binario
			File f = new File(".\\recursos\\datos.dat");
			byte[] fileData = Files.readAllBytes(f.toPath());
			out.writeObject(fileData);
			out.flush();
			System.out.println("Hash del archivo binario: " + in.readLine());
			Thread.sleep(500);
			
			//Objeto
			ObjetoPrueba objeto = new ObjetoPrueba("Un Objeto de prueba", 19);
				out.writeObject(objeto);
				out.flush();
				System.out.println("Hash del objeto de prueba: " + in.readLine());
			
		}catch (Exception e) { 
			System.err.println(e);
		}
	}
}

class ObjetoPrueba implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String string1;
	int numero1;
	
	public ObjetoPrueba (String string, int numero) {
		string1 = string;
		numero1 = numero;
	}
}
