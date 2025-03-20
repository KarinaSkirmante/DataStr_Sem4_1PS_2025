package datastr;

public class MyGraph <Ttype> {

	private Ttype[] vertices;
	private int[][] edges;
	private final int DEFAULT_SIZE = 10;
	private int size = DEFAULT_SIZE;
	private int counter = 0;
	
	public MyGraph()
	{
		vertices = (Ttype[])new Object[size];
		edges = new int[size][size];
	}
	
	public MyGraph(int inputSize) {
		if(inputSize > 0) {
			size = inputSize;
		}
		vertices = (Ttype[])new Object[size];
		edges = new int[size][size];
	}
	public boolean isFull()
	{
		return (counter == size);	
	}
	
	public boolean isEmpty() {
		return (counter == 0);
	}
	
	public int howManyElements() {
		return counter;
	}
	
	
	private void resize()
	{
		size = (counter < 100) ? size * 2 : (int)(size * 1.5);
		Ttype[] newVertices = (Ttype[])new Object[size];
		int[][] newEdges = new int[size][size];
		
		for(int i = 0; i < counter; i++) {
			newVertices[i] = vertices[i];
		}
		
		for(int i = 0; i < counter; i++) {
			for(int j = 0; j < counter; j++) {
				newEdges[i][j] = edges[i][j];
			}
		}
		
		vertices = newVertices;
		edges = newEdges;
		System.gc();
		
	}

	
	//addVertice
	//addEdge
	//print
	//makeEmpty
	public void makeEmpty() {
		if(!isEmpty()) {
			size = DEFAULT_SIZE;
			counter = 0;
			vertices = (Ttype[])new Object[size];
			edges = new int[size][size];
			System.gc();
		}
	}
	
	
	
}
