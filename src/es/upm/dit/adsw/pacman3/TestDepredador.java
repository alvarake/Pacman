package es.upm.dit.adsw.pacman3;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.sun.javafx.collections.SetListenerHelper;

public class TestDepredador {

	private Depredador depredador;
	private Jugador jugador;
	public static final int N = 5;
	private Terreno terreno;
	private Paredes paredes;
	private Casilla c11;
	private Casilla c30;
	private Casilla c20;
	private Casilla c00;
	private Casilla c02;
	private Casilla c12;
	private Casilla c23;
	private Casilla c31;
	private Casilla c41;
	private Casilla c22;
	@Before
	public void setup() {
		terreno = new Terreno(N);
		paredes = terreno.getParedes();
		depredador = new Depredador(terreno);
		paredes.reset();
		c00 = new Casilla(terreno, 0 , 0);
		c20 = new Casilla(terreno, 2 , 0);
		c11 = new Casilla(terreno, 1 , 1);
		c30 = new Casilla(terreno, 3 , 0);
		c02 = new Casilla(terreno, 0 , 2);
		c12 = new Casilla(terreno, 1 , 2);
		c23 = new Casilla(terreno, 2 , 3);
		c31 = new Casilla(terreno, 3 , 1);
		c41 = new Casilla(terreno, 4 , 1);
		c22 = new Casilla(terreno, 2 , 2);
		paredes.add(c00, Direccion.ESTE);
		paredes.add(c11, Direccion.ESTE);
		paredes.add(c11, Direccion.NORTE);
		paredes.add(c20, Direccion.NORTE);
		paredes.add(c30, Direccion.NORTE);
		paredes.add(c02, Direccion.NORTE);
		paredes.add(c12, Direccion.NORTE);
		paredes.add(c23, Direccion.NORTE);
		paredes.add(c31, Direccion.NORTE);
		paredes.add(c41, Direccion.NORTE);
		paredes.add(c23, Direccion.ESTE);
		paredes.add(c23, Direccion.OESTE);
		paredes.add(c22, Direccion.ESTE);
		




	}

	@Test
	public void testBFSx() {
		Casilla a = terreno.getCasilla(2, 3);
		
		Casilla b = terreno.getCasilla(1, 1);
		
		
		Casilla c = terreno.getCasilla(2,2);
		
		
		assertEquals(c, depredador.primerPaso(b,a));
		
		
		
		
		
	}
	@Test
	public void testTrampas(){
		
		c23.setMovil(depredador);
		Casilla m = depredador.getCasilla();
				
		assertEquals(depredador.puedoMoverme(Direccion.SUR), 2);
		
	}
	@Test
	public void testBfsf(){
		Casilla a = terreno.getCasilla(2, 4);

		Casilla b = terreno.getCasilla(1, 1);
		assertEquals(null, depredador.primerPaso(b,a));

	}
	
	@Test
	public void testBfs2(){
		assertEquals(terreno.getCasilla(0, 3), depredador.primerPaso(terreno.getCasilla(0, 3), terreno.getCasilla(0, 4)));
		
		
	}
	
	

}
