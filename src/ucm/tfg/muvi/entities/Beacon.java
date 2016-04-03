package ucm.tfg.muvi.entities;

public class Beacon  {
	
	private long id_usuario;
	private String id_beacon;
	private String name;
		
	public Beacon () {}
	
	public Beacon (long id_usuario, String id_beacon,String name) {
		this.id_usuario = id_usuario;
		this.id_beacon = id_beacon;
		this.name = name;
	}
	
	public long getID_usuario() {
		return this.id_usuario;
	}
	
	public void setID_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
	}
	
	public String getID_beacon() {
		return this.id_beacon;
	}
	
	public void setID_beacon(String id_beacon) {
		this.id_beacon = id_beacon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
