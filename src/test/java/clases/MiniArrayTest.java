package clases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestMethodOrder;

import clases.helpers.Aleatorios;
import clases.helpers.MyReflection;

import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;

@TestMethodOrder(MethodName.class)
public class MiniArrayTest {

	private final PrintStream standardOut = System.out;
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
	
	@BeforeEach
	void setUp() {
	    System.setOut(new PrintStream(outputStreamCaptor));
	}
	
	@AfterEach
	void tearDown() {
	    System.setOut(standardOut);
	}
	
	@Test
	@DisplayName("MiniArray - Basic Rules")
	void test00Rules() throws Exception{
		String className = MyListArray.class.getName();
		assertEquals(2, MyReflection.cuentaAtributos(className));
		Field campoLista = MyReflection.getField(className, "lista");
		assertEquals("lista", campoLista.getName());
		assertEquals("[Ljava.lang.Object;", campoLista.getType().getName());
		Field campoElementos = MyReflection.getField(className, "numElementos");
		assertEquals("numElementos", campoElementos.getName());
		assertEquals("int", campoElementos.getType().getName());
	}
	
	@Test
	@DisplayName("MiniArray - Nulos No permitidos")
	void test01AddNull() throws Exception{
		test00Rules();
		MiniList<String> lista = new MyListArray<String>();
		
		assertThrows(NullPointerException.class, () -> lista.add(null));
	}
	
	@Test
	@DisplayName("MiniArray - Add y Get")
	void test02AddGet() throws Exception{
		test00Rules();
		MiniList<String> lista = new MyListArray<String>();
		String cadena = "cadena1";
		//Testea add teorico
		assertTrue(lista.add(cadena));
		//Testea Add y Get un elemento
		assertEquals(cadena,lista.get(0));
		assertEquals(1, lista.size());
		
		String cadena2="cadena2";
		
		lista.add(cadena2);
		assertNotEquals(cadena2, lista.get(0));
		assertEquals(cadena2, lista.get(1));
		assertEquals(2, lista.size());
		
		String cadena3="cadena1";
		lista.add(cadena3);
		assertEquals(cadena3, lista.get(0));
		assertNotEquals(cadena3, lista.get(1));
		assertEquals(cadena3, lista.get(2));
		assertEquals(3, lista.size());
		
		int aleatorio = (int)(Math.random()*50) + 51;
		
		for(int i=3;i<aleatorio;i++) {
			String cad = "cadena"+i;
			lista.add(cad);
			testeaNotEqualMenores(i, lista, cad);
			assertEquals(cad, lista.get(i));
			assertEquals(i+1, lista.size());
		}
	}
	
	void testeaNotEqualMenores(int cotaSuperior, MiniList<String> lista, String cad) {
		for(int i = 0; i < cotaSuperior;i++) {
			assertNotEquals(cad, lista.get(i));
		}
	}
	
	@Test
	@DisplayName("MiniArray - Get NonExistent")
	void test03NonExistent() throws Exception{
		test00Rules();
		MiniList<String> lista = new MyListArray<String>();
		
		assertNull(lista.get(-1));
		assertNull(lista.get(0));
		assertNull(lista.get(1));
		assertNull(lista.get((int)(Math.random()*101) +2));
		assertNull(lista.get((int)(Math.random()*101) -102));
		
		String cadena = "cadena1";
		lista.add(cadena);
		
		String cadena2="cadena2";
		lista.add(cadena2);
				
		String cadena3="cadena1";
		lista.add(cadena3);
		
		assertNull(lista.get(-1));
		assertNull(lista.get(3));
		assertNull(lista.get((int)(Math.random()*101) + 4));
		assertNull(lista.get((int)(Math.random()*101) + -102));
		assertNotNull(lista.get(0));
		assertNotNull(lista.get(1));
		assertNotNull(lista.get(2));
	}
	
	@Test
	@DisplayName("MiniArray - Contains")
	void test04Contains() throws Exception{
		test00Rules();
		String cad1 = Aleatorios.cadenaAleatoria(5, 20);
		String cad2 = Aleatorios.cadenaAleatoria(21, 40);
		String cad3 = Aleatorios.cadenaAleatoria(41, 50);
		
		MiniList<String> lista = new MyListArray<String>();
		lista.add(cad1);
		lista.add(cad2);
		
		assertTrue(lista.contains(cad1));
		assertTrue(lista.contains(cad2));
		assertFalse(lista.contains(cad3));
		
	}
	
	@Test
	@DisplayName("MiniArray - Delete int pos")
	void test05DeletePos() throws Exception{
		test00Rules();
		
		String cad1 = Aleatorios.cadenaAleatoria(5, 20);
		String cad2 = Aleatorios.cadenaAleatoria(21, 40);
		String cad3 = Aleatorios.cadenaAleatoria(41, 50);
		String cad4 = Aleatorios.cadenaAleatoria(51, 54);
		
		MiniList<String> lista = new MyListArray<String>();
		
		//Vacia
		assertFalse(lista.delete(0));
		assertFalse(lista.delete(Aleatorios.numeroAleatorio(-50, 50)));
		
		//1 Solo elemento
		lista.add(cad1);
		assertTrue(lista.delete(0));
		assertEquals(0, lista.size());
		
		//Varios  elementos
		lista.add(cad2);
		lista.add(cad3);
		lista.add(cad4);
		
		//Elimina el primero
		assertTrue(lista.delete(0));
		assertEquals(cad3,lista.get(0));
		assertEquals(2, lista.size());
		
		//Elimina el último
		assertTrue(lista.delete(1));
		assertEquals(cad3,lista.get(0));
		assertEquals(1, lista.size());
		
		//Elimina el único
		assertFalse(lista.delete(1));
		assertTrue(lista.delete(0));
		assertEquals(0, lista.size());
		
		//Varios elementos
		lista.add(cad1);
		lista.add(cad2);
		lista.add(cad3);
		lista.add(cad4);
		
		//Elimina intermedio (cad2)
		assertTrue(lista.delete(1));
		assertEquals(cad1,lista.get(0));
		assertEquals(cad3,lista.get(1));
		assertEquals(cad4,lista.get(2));
		assertEquals(3, lista.size());
		
		//Elimina intermedio
		lista.add(cad2);
		assertTrue(lista.delete(2));
		assertEquals(cad1,lista.get(0));
		assertEquals(cad3,lista.get(1));
		assertEquals(cad2,lista.get(2));
		assertEquals(3, lista.size());
		
	}
	
	@Test
	@DisplayName("MiniArray - Delete Object")
	void test06DeleteObject() throws Exception{
		test00Rules();
		
		String cad1 = Aleatorios.cadenaAleatoria(5, 20);
		String cad2 = Aleatorios.cadenaAleatoria(21, 40);
		String cad3 = Aleatorios.cadenaAleatoria(41, 50);
		String cad4 = Aleatorios.cadenaAleatoria(51, 54);
		
		MiniList<String> lista = new MyListArray<String>();
		
		//Vacia
		assertFalse(lista.delete(null));
		assertFalse(lista.delete("papafrita"));
		
		//1 Solo elemento
		lista.add(cad1);
		assertTrue(lista.delete(cad1));
		assertEquals(0, lista.size());
		
		//Varios  elementos
		lista.add(cad2);
		lista.add(cad3);
		lista.add(cad4);
		
		//Elimina el primero
		assertTrue(lista.delete(cad2));
		assertEquals(cad3,lista.get(0));
		assertEquals(2, lista.size());
		
		//Elimina el último
		assertTrue(lista.delete(cad4));
		assertEquals(cad3,lista.get(0));
		assertEquals(1, lista.size());
		
		//Elimina el único
		assertFalse(lista.delete(cad4));
		assertTrue(lista.delete(cad3));
		assertEquals(0, lista.size());
		
		//Varios elementos
		lista.add(cad1);
		lista.add(cad2);
		lista.add(cad3);
		lista.add(cad4);
		
		//Elimina intermedio (cad2)
		assertTrue(lista.delete(cad2));
		assertEquals(cad1,lista.get(0));
		assertEquals(cad3,lista.get(1));
		assertEquals(cad4,lista.get(2));
		assertEquals(3, lista.size());
		
		//Elimina intermedio
		lista.add(cad2);
		assertTrue(lista.delete(cad4));
		assertEquals(cad1,lista.get(0));
		assertEquals(cad3,lista.get(1));
		assertEquals(cad2,lista.get(2));
		assertEquals(3, lista.size());
		
	}
	
	@Test
	@DisplayName("MiniArray - Set Object in pos")
	void test07SetInPos() throws Exception{
		test00Rules();
		
		String cad1 = Aleatorios.cadenaAleatoria(5, 20);
		String cad2 = Aleatorios.cadenaAleatoria(21, 40);
		String cad3 = Aleatorios.cadenaAleatoria(41, 50);
		String cad4 = Aleatorios.cadenaAleatoria(51, 54);
		
		MiniList<String> lista = new MyListArray<String>();
		
		//Indice no valido
		assertThrows(IndexOutOfBoundsException.class, () -> lista.set(0,cad1));
		
		lista.add(cad1);
		lista.add(cad2);
		lista.add(cad3);
		lista.add(cad4);

		//Nulos
		assertThrows(NullPointerException.class, () -> lista.set(0,null));
		//Indice no valido
		assertThrows(IndexOutOfBoundsException.class, () -> lista.set(4,cad1));
		
		assertFalse(lista.set(0, cad1));
		assertTrue(lista.set(0,cad2));
		assertTrue(lista.set(0,cad1));
		assertFalse(lista.set(0, cad1));
		assertFalse(lista.set(3, cad4));
		assertTrue(lista.set(3, cad3));
		
	}
	
	@Test
	@DisplayName("MiniArray - Clear")
	void test08Clear() throws Exception{
		test00Rules();
		String cad1 = Aleatorios.cadenaAleatoria(5, 20);
		String cad2 = Aleatorios.cadenaAleatoria(21, 40);
		String cad3 = Aleatorios.cadenaAleatoria(41, 50);
		String cad4 = Aleatorios.cadenaAleatoria(51, 54);
		
		MiniList<String> lista = new MyListArray<String>();
		
		assertEquals(0, lista.size());
		
		lista.add(cad1);
		lista.add(cad2);
		lista.add(cad3);
		lista.add(cad4);
		
		assertNotNull(lista.get(0));
		
		assertEquals(4, lista.size());
		
		lista.clear();

		assertEquals(0, lista.size());
		
		assertNull(lista.get(0));
	}

}
