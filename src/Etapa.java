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
	private Long id;
	private Double horaInicial;
	private Double horaFinal;
	private Integer vehiculosDisponiblesInicio;
	private Integer serviciosFinalizados;
	private Integer nuevasSolicitudes;
	private Integer solicitudesAsignadas;
	private Integer solicitudesNoAsignadas;
	private Integer clientesPerdidos;
	private Double tiempoEsperaPromedio;
	//-------------------------------------------------------------------------------
	//Constructor--------------------------------------------------------------------
	//-------------------------------------------------------------------------------
	public Etapa(Double ventanaTiempo,Long id,Double horaInicial,Double horaFinal){
		this.ventanaTiempo=ventanaTiempo;
		asignacion=new HashMap<Pasajero,Conductor>();
		pasajeros=new ArrayList<Pasajero>();
		conductores=new ArrayList<Conductor>();
		this.id=id;
		this.horaInicial=horaInicial;
		this.horaFinal=horaFinal;
		vehiculosDisponiblesInicio=0;
		serviciosFinalizados=0;
		nuevasSolicitudes=0;
		solicitudesAsignadas=0;
		solicitudesNoAsignadas=0;
		clientesPerdidos=0;
		tiempoEsperaPromedio=0.0;
	}
	
	//-------------------------------------------------------------------------------
	//Getters and Setters------------------------------------------------------------
	//-------------------------------------------------------------------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getHoraInicial() {
		return horaInicial;
	}

	public void setHoraInicial(Double horaInicial) {
		this.horaInicial = horaInicial;
	}

	public Double getHoraFinal() {
		return horaFinal;
	}

	public void setHoraFinal(Double horaFinal) {
		this.horaFinal = horaFinal;
	}

	public Integer getVehiculosDisponiblesInicio() {
		return vehiculosDisponiblesInicio;
	}

	public void setVehiculosDisponiblesInicio(Integer vehiculosDisponiblesInicio) {
		this.vehiculosDisponiblesInicio = vehiculosDisponiblesInicio;
	}

	public Integer getServiciosFinalizados() {
		return serviciosFinalizados;
	}

	public void setServiciosFinalizados(Integer serviciosFinalizados) {
		this.serviciosFinalizados = serviciosFinalizados;
	}

	public Integer getNuevasSolicitudes() {
		return nuevasSolicitudes;
	}

	public void setNuevasSolicitudes(Integer nuevasSolicitudes) {
		this.nuevasSolicitudes = nuevasSolicitudes;
	}

	public Integer getSolicitudesAsignadas() {
		return solicitudesAsignadas;
	}

	public void setSolicitudesAsignadas(Integer solicitudesAsignadas) {
		this.solicitudesAsignadas = solicitudesAsignadas;
	}

	public Integer getSolicitudesNoAsignadas() {
		return solicitudesNoAsignadas;
	}

	public void setSolicitudesNoAsignadas(Integer solicitudesNoAsignadas) {
		this.solicitudesNoAsignadas = solicitudesNoAsignadas;
	}

	public Integer getClientesPerdidos() {
		return clientesPerdidos;
	}

	public void setClientesPerdidos(Integer clientesPerdidos) {
		this.clientesPerdidos = clientesPerdidos;
	}

	public Double getTiempoEsperaPromedio() {
		return tiempoEsperaPromedio;
	}

	public void setTiempoEsperaPromedio(Double tiempoEsperaPromedio) {
		this.tiempoEsperaPromedio = tiempoEsperaPromedio;
	}

	
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
