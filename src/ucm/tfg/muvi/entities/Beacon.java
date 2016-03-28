package ucm.tfg.muvi.entities;

public class Beacon  {
	
	private long id_usuario; // de momento lo hacemos con string para mas comodidad
	private long id_beacon;
	private String name;
		
	public Beacon () {}
	
	public Beacon (long id_usuario, long id_beacon,String name) {
		this.id_usuario = id_usuario;
		this.id_beacon = id_beacon;
		this.name= name;
	}
	
	public long getID_usuario() {
		return this.id_usuario;
	}
	
	public void setID_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
	}
	
	public long getID_beacon() {
		return this.id_beacon;
	}
	
	public void setID_beacon(long id_beacon) {
		this.id_beacon = id_beacon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
