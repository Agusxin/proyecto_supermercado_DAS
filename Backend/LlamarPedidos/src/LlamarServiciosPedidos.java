import java.net.HttpURLConnection;
import java.net.URL;

public class LlamarServiciosPedidos {
	public static void main(String[] args) {
		try {
			URL url = new URL("http://localhost:8086/supermercado/registrarPedidos");
			System.out.print(url);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println(" Llamada exitosa para registrar pedido.");
            } else {
                System.out.println(" Error al llamar al servicio. Código de respuesta: " + responseCode);
            }
		} catch (Exception e) {
            e.printStackTrace();
        }
		
		try {
			URL url = new URL("http://localhost:8086/supermercado/consultarPedidos");
			System.out.print(url);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println(" Llamada exitosa para registrar pedido.");
            } else {
                System.out.println(" Error al llamar al servicio. Código de respuesta: " + responseCode);
            }
		} catch (Exception e) {
            e.printStackTrace();
        }
	}
}
