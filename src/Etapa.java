import gurobi.GRB;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Etapa {
	//-------------------------------------------------------------------------------
	//Atributos----------------------------------------------------------------------
	//-------------------------------------------------------------------------------
	private Double ventanaTiempo;
	public HashMap<Pasajero,Conductor>asignacion;
	public List<Pasajero>pasajeros;
	public List<Conductor>conductores;
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
	
	private List<Nodo>nodosPasajeros;
	private List<Nodo>nodosConductores;
	public Arco[][]arcos;
	public GRBVar[][]variables;
	//-------------------------------------------------------------------------------
	//Constructor--------------------------------------------------------------------
	//-------------------------------------------------------------------------------
	public Etapa(Double ventanaTiempo,Long id,Double horaInicial,Double horaFinal){
		this.ventanaTiempo=ventanaTiempo;
		asignacion=new HashMap<Pasajero,Conductor>();
		pasajeros=new ArrayList<Pasajero>();
		conductores=new ArrayList<Conductor>();
		nodosPasajeros=new ArrayList<Nodo>();
		nodosConductores=new ArrayList<Nodo>();
		
		this.id=id;
		this.horaInicial=horaInicial;
		this.horaFinal=horaFinal;
		vehiculosDisponiblesInicio=null;
		serviciosFinalizados=null;
		nuevasSolicitudes=null;
		solicitudesAsignadas=null;
		solicitudesNoAsignadas=null;
		clientesPerdidos=null;
		tiempoEsperaPromedio=null;
		
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
	
	public void generarModelo(){
		crearNodos();
		int numeroFicticeos=pasajeros.size()-conductores.size();
		if(numeroFicticeos>0){
			agregarConductoresFicticeos(Math.abs(numeroFicticeos));
		}
		else if(numeroFicticeos<0){
			agregarPasajerosFicticeos(Math.abs(numeroFicticeos));
		}
		//ya estan niveladas las variables de oferta y demanda
		System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHH NP: "+nodosPasajeros.size()+" NC: "+nodosConductores.size());
		crearArcos();
		 GRBEnv env;
		try {
			env = new GRBEnv("mip1.log");
			GRBModel  model = new GRBModel(env);
			variables=new GRBVar[nodosPasajeros.size()][nodosConductores.size()];
			for(int i=0;i<nodosPasajeros.size();i++){
				for(int j=0;j<nodosConductores.size();j++){
					GRBVar x = model.addVar(0.0, 1.0, 0.0, GRB.INTEGER, "x"+nodosPasajeros.get(i).nombre+","+nodosConductores.get(j).nombre);
					variables[i][j]=x;
				}
			}
			model.update();
			GRBLinExpr expr = new GRBLinExpr();
			for(int i=0;i<nodosPasajeros.size();i++){
				for(int j=0;j<nodosConductores.size();j++){
					expr.addTerm(arcos[i][j].c, variables[i][j]);
				}
			}
			model.setObjective(expr, GRB.MINIMIZE);
			
			for(int i=0;i<nodosPasajeros.size();i++){
				expr = new GRBLinExpr();
				for(int j=0;j<nodosConductores.size();j++){
					expr.addTerm(-1.0, variables[i][j]);
					
				}
				model.addConstr(expr, GRB.EQUAL, (double)nodosPasajeros.get(i).b, "c_P"+i);
			}
			for(int i=0;i<nodosConductores.size();i++){
				expr = new GRBLinExpr();
				for(int j=0;j<nodosPasajeros.size();j++){
					expr.addTerm(1.0, variables[j][i]);
					
				}
				model.addConstr(expr, GRB.EQUAL, (double)nodosConductores.get(i).b, "c_C"+i);
			}
			model.optimize();
			for(int i=0;i<nodosPasajeros.size();i++){
				for(int j=0;j<nodosConductores.size();j++){
					System.out.println(variables[i][j].get(GRB.StringAttr.VarName)+ " " +variables[i][j].get(GRB.DoubleAttr.X));
				}
			}
			model.dispose();
			env.dispose();
		    
		} catch (GRBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
	}
	
	public void crearNodos(){
		for(int i=0; i<pasajeros.size();i++){
			Nodo n=new Nodo(-1.0,pasajeros.get(i).getId()+"",pasajeros.get(i),null);
			nodosPasajeros.add(n);
		}
		
		for(int i=0; i<conductores.size();i++){
			Nodo n=new Nodo(1.0,conductores.get(i).getId()+"",null,conductores.get(i));
			nodosConductores.add(n);
		}
	}
	
	public void agregarPasajerosFicticeos(int numeroFicticeos){
		for(int i=0;i<numeroFicticeos;i++){
			Nodo n=new Nodo(-1.0,"NodoFiciticeoP_"+i,null,null);
			nodosPasajeros.add(n);
		}
	}
	
	public void agregarConductoresFicticeos(int numeroFicticeos){
		for(int i=0;i<numeroFicticeos;i++){
			Nodo n=new Nodo(1.0,"NodoFiciticeoC_"+i,null,null);
			nodosConductores.add(n);
		}
	}
	public void crearArcos(){
		arcos=new Arco[nodosPasajeros.size()][nodosConductores.size()];
		for(int i=0;i<nodosPasajeros.size();i++){
			for(int j=0;j<nodosConductores.size();j++){
				Double costo=null;
				if(nodosPasajeros.get(i).conductor==null && nodosPasajeros.get(i).pasajero==null){
					costo=0.0;
				}
				else if(nodosConductores.get(j).conductor==null && nodosConductores.get(j).pasajero==null){
					costo=0.0;
				}
				else{
					int distanciaC=Math.abs(nodosPasajeros.get(i).pasajero.getCalleInicial()-nodosConductores.get(j).conductor.getCalle());
					int distanciaCr=Math.abs(nodosPasajeros.get(i).pasajero.getCarreraInicial()-nodosConductores.get(j).conductor.getCarrera());
					
					costo=(double)distanciaC+distanciaCr;
				}
				arcos[i][j]=new Arco(costo,nodosPasajeros.get(i),nodosConductores.get(j));
			}
		}
	}
//	 public static void main(String[] args) {
//		 try {
//			 GRBEnv    env   = new GRBEnv("mip1.log");
//			 GRBModel  model = new GRBModel(env);
//
//			 // Create variables
//
//			 GRBVar x = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "x");
//			 GRBVar y = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "y");
//			 GRBVar z = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "z");
//
//			 // Integrate new variables
//
//			 model.update();
//
//			 // Set objective: maximize x + y + 2 z
//
//			 GRBLinExpr expr = new GRBLinExpr();
//			 expr.addTerm(1.0, x); expr.addTerm(1.0, y); expr.addTerm(2.0, z);
//			 model.setObjective(expr, GRB.MAXIMIZE);
//
//			 // Add constraint: x + 2 y + 3 z <= 4
//
//			 expr = new GRBLinExpr();
//			 expr.addTerm(1.0, x); expr.addTerm(2.0, y); expr.addTerm(3.0, z);
//			 model.addConstr(expr, GRB.LESS_EQUAL, 4.0, "c0");
//
//			 // Add constraint: x + y >= 1
//
//			 expr = new GRBLinExpr();
//			 expr.addTerm(1.0, x); expr.addTerm(1.0, y);
//			 model.addConstr(expr, GRB.GREATER_EQUAL, 1.0, "c1");
//
//			 // Optimize model
//
//			 model.optimize();
//
//			 System.out.println(x.get(GRB.StringAttr.VarName)
//					 + " " +x.get(GRB.DoubleAttr.X));
//			 System.out.println(y.get(GRB.StringAttr.VarName)
//					 + " " +y.get(GRB.DoubleAttr.X));
//			 System.out.println(z.get(GRB.StringAttr.VarName)
//					 + " " +z.get(GRB.DoubleAttr.X));
//
//			 System.out.println("Obj: " + model.get(GRB.DoubleAttr.ObjVal));
//
//			 // Dispose of model and environment
//
//			 model.dispose();
//			 env.dispose();
//
//		 } catch (GRBException e) {
//			 System.out.println("Error code: " + e.getErrorCode() + ". " +
//					 e.getMessage());
//		 }
//		  }	

}
