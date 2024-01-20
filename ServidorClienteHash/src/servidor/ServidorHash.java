package servidor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;

public class ServidorHash {

	public static void main(String[] args) {
		int PUERTO = 9999;
		try (ServerSocket ss = new ServerSocket(PUERTO)){

			
			System.out.println("Escuchando el puerto 9999...");
			Socket s = ss.accept();

			PrintWriter out = new PrintWriter(s.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(s.getInputStream());

			MessageDigest md = MessageDigest.getInstance("MD5");
			System.out.println("Servidor listo.");

			while (true) {
				Object o = in.readObject();
				byte[] hash;
				try {
					hash = md.digest((byte[]) o);
				}catch (Exception e) {
					hash = deserialize(o);
				}
				StringBuilder result = new StringBuilder();
				for(byte b : hash) result.append(String.format("%02X", b));
				System.out.println(result.toString());
				out.println(result.toString());
				out.flush();
			}

		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	//este método lleva un poco de IA 
	private static byte[] deserialize(Object o) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(o);
			oos.flush();
			return baos.toByteArray();
		} catch (IOException e) {
			System.err.println("El objeto no pudo ser deserializado");
		}
		return null;
	}
}
