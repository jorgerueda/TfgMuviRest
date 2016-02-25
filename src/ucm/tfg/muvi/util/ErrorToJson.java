package ucm.tfg.muvi.util;

public class ErrorToJson {
	
	private String mensaje;
	
	public ErrorToJson() {
		
	}
	
	public ErrorToJson(String mensaje) {
		this.mensaje = mensaje;
	}
	
	public String getMensaje() {
		return this.mensaje;
	}
	
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}
