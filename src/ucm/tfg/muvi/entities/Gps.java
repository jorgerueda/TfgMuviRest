package ucm.tfg.muvi.entities;

public class Gps {

	private long id_usuario; // de momento lo hacemos con string para mas comodidad
	private String name;
	private Loc loc;
	
		
	public Gps () {
		this.loc = new Loc();
	}
	
	public Gps (long id_usuario, double latitud,double longitud,String name) {
		this.id_usuario = id_usuario;
		this.loc = new Loc (latitud, longitud);
		this.name= name;
	}

	public long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Loc getLoc() {
		return loc;
	}

	public void setLoc(Loc loc) {
		this.loc = loc;
	}


	public class Loc {
		
		private double lat;
		private double lon;
		
		public Loc(){}
		
		public Loc (double lat, double lon){
			this.lat = lat;
			this.lon = lon;
		}
		
		public double getLatitud() {
			return lat;
		}

		public void setLatitud(double latitud) {
			this.lat = latitud;
		}

		public double getLongitud() {
			return lon;
		}

		public void setLongitud(double longitud) {
			this.lon = longitud;
		}
		
		
	}
}
