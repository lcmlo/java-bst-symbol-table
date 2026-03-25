import java.util.LinkedList;
import java.util.Queue;

public class ST<Key extends Comparable<Key>, Value> {
	private Node root;

	private class Node {
		Key key;
		Value val;
		Node left, right;

		Node(Key k, Value v) {
			key = k;
			val = v;
		}
	}

	public ST() { // Initialise an empty ordered symbol table
		root = null;
	}

	public void put(Key key, Value val) { // Put the key-value pair into this table
		root = put(root, key, val);
	}

	private Node put(Node x, Key key, Value val) { // Put the key-value pair into this table
		if (x == null)
			return new Node(key, val);
		int cmp = key.compareTo(x.key);
		if (cmp == 0)
			x.val = val;
		else if (cmp < 0)
			x.left = put(x.left, key, val);
		else
			x.right = put(x.right, key, val);
		return x;
	}

	public Value get(Key key) { // Get the value paired with key (or null)
		return get(root, key);
	}

	private Value get(Node x, Key key) { // Get the value paired with key (or null)
		if (x == null)
			return null;
		int cmp = key.compareTo(x.key);
		if (cmp == 0)
			return x.val;
		else if (cmp < 0)
			return get(x.left, key);
		else
			return get(x.right, key);
	}

	public void delete(Key key) { // Remove the pair that has this key
		root = delete(root, key);
	}

	private Node delete(Node x, Key key) {
		if (x == null)
			return null;
		int cmp = key.compareTo(x.key);
		if (cmp < 0) {
			x.left = delete(x.left, key);
		} else if (cmp > 0) {
			x.right = delete(x.right, key);
		} else {
			if (x.right == null)
				return x.left;
			if (x.left == null)
				return x.right;
			Node temp = x;
			x = minNode(temp.right);
			x.right = deleteMin(temp.right);
			x.left = temp.left;
		}
		return x;
	}

	public boolean contains(Key key) { // Is there a value paired with the key
		return contains(root, key);
	}

	private boolean contains(Node x, Key key) { // Is there a value paired with the key
		if (x == null)
			return false;
		int cmp = key.compareTo(x.key);
		if (cmp == 0)
			return true;
		else if (cmp < 0)
			return contains(x.left, key);
		else
			return contains(x.right, key);
	}

	public boolean isEmpty() { // Is this symbol table empty?
		return size() == 0;
	}

	public int size() { // Number of key-value pairs in this table
		return size(root);
	}

	private int size(Node x) {
		if (x == null)
			return 0;
		else
			return 1 + size(x.left) + size(x.right);
	}

	public Key min() { // Smallest key
		return min(root);
	}

	private Key min(Node x) {
		if (x == null)
			return null;
		else if (x.left == null)
			return x.key;
		else
			return min(x.left);
	}

	private Node minNode(Node x) { // Node with the smallest key
		if (x == null)
			return null;
		else if (x.left == null)
			return x;
		else
			return minNode(x.left);
	}

	public Key max() { // Largest key
		if (root == null)
			return null;
		return max(root);
	}

	private Key max(Node x) { // Largest key
		if (x.right == null)
			return x.key;
		return max(x.right);
	}

	public Key floor(Key key) { // Largest key less than or equal to key
		Node x = floor(root, key);
		if (x == null)
			return null;
		return x.key;
	}

	private Node floor(Node x, Key key) {
		if (x == null)
			return null;
		int cmp = key.compareTo(x.key);
		if (cmp == 0)
			return x;
		else if (cmp < 0)
			return floor(x.left, key);
		else { // Verificar se floor de key está na subárvore da direita
			Node right = floor(x.right, key);
			if (right != null)
				return right;
			else // Caso contrário, floor de key é a chave do nó
				return x;
		}
	}

	public Key ceiling(Key key) { // Smallest key greater than or equal to key
		Node x = ceiling(root, key);
		if (x == null)
			return null;
		return x.key;
	}

	private Node ceiling(Node x, Key key) {
		if (x == null)
			return null;
		int cmp = key.compareTo(x.key);
		if (cmp == 0)
			return x;
		else if (cmp < 0) {
			Node left = ceiling(x.left, key);
			if (left != null)
				return left;
			return x;
		} else {
			return ceiling(x.right, key);
		}
	}

	public int rank(Key key) { // Number of keys less than key
		return rank(key, root);
	}

	private int rank(Key key, Node x) {
		if (x == null)
			return 0;
		int cmp = key.compareTo(x.key);
		if (cmp < 0)
			return rank(key, x.left);
		else if (cmp > 0)
			return 1 + size(x.left) + rank(key, x.right);
		else // key == x.key
			return size(x.left);
	}

	public Key select(int k) { // Get a key of rank k
		if (k < 0 || k >= size())
			throw new IllegalArgumentException();
		Node x = select(root, k);
		return x.key;
	}

	private Node select(Node x, int k) {
		if (x == null)
			return null;
		int leftSize = size(x.left);
		if (leftSize > k)
			return select(x.left, k);
		else if (leftSize < k)
			return select(x.right, k - leftSize - 1);
		else
			return x;

	}

	public void deleteMin() {// Delete the pair with the smallest key
		deleteMin(root);
	}

	private Node deleteMin(Node x) {
		if (x.left == null)
			return x.right;
		x.left = deleteMin(x.left);
		return x;
	}

	public void deleteMax() { // Delete the pair with the largest key
		deleteMax(root);
	}

	private Node deleteMax(Node x) {
		if (x.right == null)
			return x.left;
		x.right = deleteMax(x.right);
		return x;
	}

	public int size(Key lo, Key hi) { // Number of keys in [lo, hi]
		if (lo.compareTo(hi) > 0)
			return 0;
		if (contains(hi))
			return rank(hi) - rank(lo) + 1;
		else
			return rank(hi) - rank(lo);
	}

	public Iterable<Key> keys(Key lo, Key hi) { // Keys in [lo, hi] in sorted order
		Queue<Key> keysQueue = new LinkedList<>();
		inorder(root, lo, hi, keysQueue);
		return keysQueue;
	}

	private void inorder(Node x, Key lo, Key hi, Queue<Key> keysQueue) {
		if (x == null)
			return;
		int cmpLo = lo.compareTo(x.key);
		int cmpHi = hi.compareTo(x.key);
		if (cmpLo < 0)
			inorder(x.left, lo, hi, keysQueue);
		if (cmpLo <= 0 && cmpHi >= 0)
			keysQueue.add(x.key);
		if (cmpHi > 0)
			inorder(x.right, lo, hi, keysQueue);
	}

	public Iterable<Key> keys() { // All keys in the table, in sorted order
		Queue<Key> q = new LinkedList<>();
		inorder(root, q);
		return q;
	}

	private void inorder(Node x, Queue<Key> q) {
		if (x == null)
			return;
		inorder(x.left, q);
		q.add(x.key);
		inorder(x.right, q);
	}

	public String toString() {
		return toString(root) + "\n";
	}

	private String toString(Node x) {
		if (x == null)
			return "";
		return toString(x.left) + " " + x.key + " " + toString(x.right);
	}

	public static void main(String[] args) {
		ST<Integer, String> st = new ST<Integer, String>();

		// Inserir pares chave-valor
		st.put(1, "um");
		st.put(9, "nove");
		st.put(5, "cinco");
		st.put(2, "dois");
		st.put(10, "dez");
		st.put(3, "três");
		st.put(8, "oito");
		st.put(4, "quatro");
		st.put(7, "sete");
		st.put(6, "seis");

		// Recuperar valores
		for (int i = 1; i < 11; i++) {
			System.out.print(st.get(i) + " "); // Output: "um,dois,tres..."
		}
		System.out.println("\n");

		// Encontrar chaves mínima e máxima
		System.out.println(st.min()); // Output: 1
		System.out.println(st.max()); // Output: 10
		System.out.println();

		// Verificar se determinadas chaves existem
		System.out.println(st.contains(2)); // Output: true
		System.out.println(st.contains(5)); // Output: true
		System.out.println(st.contains(7)); // Output: false
		System.out.println();

		// Apagar pares chave-valor
		st.delete(3);
		st.delete(6);

		System.out.print("Contém 3? ");
		System.out.println(st.contains(3)); // Output: false
		System.out.print("Contém 6? ");
		System.out.println(st.contains(6)); // Output: false
		System.out.println();

		System.out.print("Tamanho atual da st: ");
		System.out.println(st.size()); // Output: 8
		System.out.println();

		// Obter todas as chaves no intervalo [2,5]
		System.out.print("chaves no intervalo [2,5] : ");
		Iterable<Integer> keysInRange = st.keys(2, 5);
		for (Integer key : keysInRange) {
			System.out.print(key + " ");
		}
		// Output: 2 4 5

		System.out.println();

		// Obter todas as chaves em ordem
		System.out.print("chaves em ordem : ");
		Iterable<Integer> allKeys = st.keys();
		for (Integer key : allKeys) {
			System.out.print(key + " ");
		}
		// Output: 1 2 4 5 7 8 9 10

		System.out.println();
		System.out.println();

		// Apagar o par com a chave mínima
		st.deleteMin();
		System.out.print("Chave mínima após apagar o min: ");
		System.out.println(st.min()); // Output: 2

		// Apagar o par com a chave máxima
		st.deleteMax();
		System.out.print("Chave máxima após apagar o max: ");
		System.out.println(st.max()); // Output: 9

		System.out.println();

		// size, rank, select, ceiling e floor
		System.out.println("Tamanho das chaves no intervalo [3, 8]: " + st.size(3, 8)); // Output: 4
		System.out.println();
		System.out.println("Rank da chave 5: " + st.rank(5)); // Output: 3
		System.out.println();
		System.out.println("Chave no rank 3: " + st.select(3)); // Output: 4
		System.out.println();
		System.out.println("Ceiling da chave 6: " + st.ceiling(6)); // Output: 7
		System.out.println();
		System.out.println("Floor da chave 6: " + st.floor(6)); // Output: 5

	}
}
