
public class Conductor {
	
	//-------------------------------------------------------------------------------
	//Atributos----------------------------------------------------------------------
	//-------------------------------------------------------------------------------
	private Integer calle;
	private Integer carrera;
	private Long id;
	
	//-------------------------------------------------------------------------------
	//Constructor--------------------------------------------------------------------
	//-------------------------------------------------------------------------------
	public Conductor(Integer calle, Integer carrera,Long id){
		this.calle=calle;
		this.carrera=carrera;
		this.id=id;
	}
	
	//-------------------------------------------------------------------------------
	//Getters and Setters------------------------------------------------------------
	//-------------------------------------------------------------------------------
	public Integer getCalle() {
		return calle;
	}

	public void setCalle(Integer calle) {
		this.calle = calle;
	}

	public Integer getCarrera() {
		return carrera;
	}

	public void setCarrera(Integer carrera) {
		this.carrera = carrera;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	//-------------------------------------------------------------------------------
	//Metodos------------------------------------------------------------------------
	//-------------------------------------------------------------------------------

}
