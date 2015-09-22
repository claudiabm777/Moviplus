import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Etapa {
	//-------------------------------------------------------------------------------
	//Atributos----------------------------------------------------------------------
	//-------------------------------------------------------------------------------
	private Double ventanaTiempo;
	private HashMap<Pasajero,Conductor>asignacion;
	private List<Pasajero>pasajeros;
	private List<Conductor>conductores;
	
	//-------------------------------------------------------------------------------
	//Constructor--------------------------------------------------------------------
	//-------------------------------------------------------------------------------
	public Etapa(Double ventanaTiempo){
		this.ventanaTiempo=ventanaTiempo;
		asignacion=new HashMap<Pasajero,Conductor>();
		pasajeros=new ArrayList<Pasajero>();
		conductores=new ArrayList<Conductor>();
	}
	
	//-------------------------------------------------------------------------------
	//Getters and Setters------------------------------------------------------------
	//-------------------------------------------------------------------------------
	public Double getVentanaTiempo() {
		return ventanaTiempo;
	}

	public void setVentanaTiempo(Double ventanaTiempo) {
		this.ventanaTiempo = ventanaTiempo;
	}

	public HashMap<Pasajero, Conductor> getAsignacion() {
		return asignacion;
	}

	public void setAsignacion(HashMap<Pasajero, Conductor> asignacion) {
		this.asignacion = asignacion;
	}

	public List<Pasajero> getPasajeros() {
		return pasajeros;
	}

	public void setPasajeros(List<Pasajero> pasajeros) {
		this.pasajeros = pasajeros;
	}

	public List<Conductor> getConductores() {
		return conductores;
	}

	public void setConductores(List<Conductor> conductores) {
		this.conductores = conductores;
	}

	//-------------------------------------------------------------------------------
	//Metodos------------------------------------------------------------------------
	//-------------------------------------------------------------------------------
	

}
