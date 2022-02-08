package clases;

public interface MiniList<T> {
	
	/**
	 * El metodo introduce un elemento al final de la lista
	 * @param elemento a introducir
	 * @return true si el elemento fue insertado
	 * @throws NullPointerException en caso de intentar insertar un valor nulo
	 */
	boolean add(T elemento) throws NullPointerException; 

	/**
	 * Metodo que comprueba la existencia de un elemento en la lista
	 * @param elemento a buscar
	 * @return verdadero si esta, falso si no esta
	 */
	boolean contains(T elemento);
	
	/**
	 * Elimina el primer elemento que encuentre en la lista que sea igual
	 * al elemento que se pasa como parametro
	 * @param elemento a eliminar
	 * @return verdadero si elimino, falso si no
	 */
	boolean delete(T elemento);
	
	/**
	 * Elimina el elemento que ocupe la posicion que se indica como parametro
	 * @param posicion del elemento a eliminar
	 * @return verdadero si se elimino, falso si no
	 */
	boolean delete(int posicion);
	
	/**
	 * Cambia el elemento que esta en la posicion que se pasa como parametro
	 * @param posicion a cambiar
	 * @param elemento elemento a sustituir
	 * @return verdadero si se cambió (Un elemento cambia si no 
	 * es igual al que había), falso si no.
	 * @throws IndexOutOfBoundsException Si el indice no se corresponde con un índice válido
	 * @throws NullPointerException en caso de intentar insertar un valor nulo
	 */
	boolean set(int posicion, T elemento) throws IndexOutOfBoundsException;
	
	/**
	 * Devuelve el elemento de la posicion que se pasa como parametro
	 * @param posicion a devolver
	 * @return el elemento que estaba en la posicion, null si no existe
	 */
	T get(int posicion);
		
	/**
	 * Indica el numero de elementos que tiene la lista
	 * @return
	 */
	int size();
	
	/**
	 * Pone la lista a 0 elementos
	 */
	void clear();

	
}
