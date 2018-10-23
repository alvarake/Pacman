package es.upm.dit.adsw.pacman3;



import java.awt.Image;
import java.net.URL;

import java.util.*;

import javax.swing.ImageIcon;

public class Depredador extends Movil implements Runnable {
	 private  Terreno terreno;

	    /**
	     * Casilla en la que esta.
	     */
	    private Casilla casilla;

	    /**
	     * TRUE si no ha muerto.
	     */
	    private boolean vivo;
	     private Casilla move;
	  
	    
	    
	
	    public Depredador(Terreno terreno){
	    	  this.terreno = terreno;
	          setImage("Anibal.png");
	          vivo = true;
	    }
	@Override
	public void run() {
		while(vivo){
			
			Casilla c= primerPaso(terreno.getJugador().getCasilla(), casilla);
			terreno.move(this, casilla.getDireccion(c));
			pendientes.clear();
			visitadas.clear();
			try{ 
				Thread.sleep(300);
			}
			catch (InterruptedException e){
				return;
			}
		}

		
	}
	
	private List<Casilla> pendientes =  new ArrayList<Casilla>(); 
	private Map<Casilla, Casilla> visitadas =  new HashMap<Casilla, Casilla>(); 

	public Casilla primerPaso(Casilla origen, Casilla destino){
		pendientes.add(origen);
		if (bfs(destino, pendientes, visitadas)){
			return visitadas.get(destino);
			
		}
		return null;
		
	
	}
	
	private boolean bfs(Casilla destino, List<Casilla> pendientes,  Map<Casilla, Casilla> visitadas) { 

		while (!pendientes.isEmpty()){ //		si no hay pendientes, devuelve FALSE 

			Casilla c1 = pendientes.remove(0);

			if (c1 == destino){
				return true;
			}

			for (Direccion direccion : Direccion.values()) { 
				Casilla c2 =  c1.siguiente(direccion);


				if ( !terreno.hayPared(c1, direccion) && !visitadas.containsKey(c2) && (c2 != null) ){ 


					visitadas.put(c1.siguiente(direccion), c1);
					pendientes.add(c1.siguiente(direccion));

				}

				
				
				this.pendientes=pendientes;
				this.visitadas=visitadas;

			}
//			pendientes.remove(pendientes.get(0));

		}
		return false;

	}

	@Override
	public Casilla getCasilla() {
		
		return casilla;
	}

	@Override
	public void setCasilla(Casilla casilla) {
	    this.casilla = casilla;
        if (casilla.isObjetivo()) {
            terreno.setEstado(EstadoJuego.GANA_JUGADOR);
            vivo = false;
        }
		
	}

	@Override
	public void muere(boolean devorado) {
		if (terreno.getEstadoJuego()==EstadoJuego.PIERDE_JUGADOR )
            terreno.setEstado(EstadoJuego.PIERDE_JUGADOR);
        vivo = false;
		
	}

	@Override
	public int puedoMoverme(Direccion direccion) {
		 if (!vivo)
	            return 2;
	        if (terreno.hayPared(casilla, direccion))
	            return 2;
	        Casilla micasilla = casilla.siguiente(direccion);
	        Movil fantasma = micasilla.getMovil();
	        if (fantasma instanceof Fantasma00 || (casilla.isTrampa() ))
	        	return 1;
	        
	        return 0;
	}
	
}

		