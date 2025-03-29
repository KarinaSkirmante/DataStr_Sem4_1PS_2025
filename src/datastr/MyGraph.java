package datastr;

import java.util.ArrayList;
import java.util.Stack;

public class MyGraph <Ttype> {

	private Ttype[] vertices;
	private boolean[] isVerticeVisited;
	private int[][] edges;
	private final int DEFAULT_SIZE = 10;
	private int size = DEFAULT_SIZE;
	private int counter = 0;
	
	public MyGraph()
	{
		vertices = (Ttype[])new Object[size];
		edges = new int[size][size];
		isVerticeVisited = new boolean[size];
	}
	
	public MyGraph(int inputSize) {
		if(inputSize > 0) {
			size = inputSize;
		}
		vertices = (Ttype[])new Object[size];
		edges = new int[size][size];
		isVerticeVisited = new boolean[size];
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
		boolean[] newIsVerticeVisited = new boolean[size];
		
		
		for(int i = 0; i < counter; i++) {
			newVertices[i] = vertices[i];
			newIsVerticeVisited[i] = isVerticeVisited[i];
		}
		
		for(int i = 0; i < counter; i++) {
			for(int j = 0; j < counter; j++) {
				newEdges[i][j] = edges[i][j];
			}
		}
		
		vertices = newVertices;
		edges = newEdges;
		isVerticeVisited = newIsVerticeVisited;
		System.gc();
		
	}

	
	//addVertice
	public void addVertice(Ttype element) throws Exception{
		if(element == null)
		{
			throw new Exception("Padotais elements nevar būt bez references.");
		}
		
		if(isVerticeExist(element))
		{
			throw new Exception("Tāda virsotne jau eksistē");
		}
		
		if(isFull()) {
			resize();
		}
		
		
		vertices[counter] = element;
		counter++;
		
	}
	
	//TODO var veikt optimizācija, kur apvieno šo funkciju ar getIndexOfVertice un meklē abus elementu vienā for ciklā
	private boolean isVerticeExist(Ttype element) {
		
		for(int i = 0; i < counter; i++)
		{
			if(vertices[i].equals(element)) {
				return true;
			}
		}
		return false;
		
	}
	
	
	//addEdge
	public void addEdge(Ttype elementFrom, Ttype elementTo, int weigth) throws Exception {
		if(elementFrom == null || elementTo == null || weigth <= 0)
		{
			throw new Exception("Kāds no ievades parametriem nav atbilstošs");
		}
		
		if(!isVerticeExist(elementFrom))
		{
			throw new Exception("Virsotne, no kura vēlas veidot saiti, neeksistē grafā");
		}
		
		if(!isVerticeExist(elementTo))
		{
			throw new Exception("Virsotne, uz kuru vēlas veidot saiti, neeksistē grafā");
		}
		
		int indexOfElementFrom = getIndexOfVertice(elementFrom);
		int indexOfElementTo = getIndexOfVertice(elementTo);
		
		//TODO var apdomā, vai neļaut vairākus ceļus starp vienām un tam pašām divām pilsētām
		int possibleExistingEdge = edges[indexOfElementFrom][indexOfElementTo];
		if(possibleExistingEdge > 0)
		{
			throw new Exception
			("Savienojums starp " + elementFrom + " un " + elementTo + " jau eksistē");
		}
		
		edges[indexOfElementFrom][indexOfElementTo] = weigth;
		
	}
	
	
	private int getIndexOfVertice(Ttype element) {
		for(int i = 0; i < counter; i++)
		{
			if(vertices[i].equals(element)) {
				return i;
			}
		}
		return -1;
	}
	
	
	//print
	public void print() throws Exception
	{
		if(isEmpty())
		{
			throw new Exception("Grafs ir tukšs un nav elementu, ko izprintēt");
		}
		
		
		for(int i = 0; i < counter; i++) //rindas
		{
			System.out.print(vertices[i] + " -> ");
			
			for(int j = 0; j < counter ; j++) {//kolonnas
				if(edges[i][j] > 0)//eksistē saite (ceļš)
				{
					System.out.print( vertices[j] + " (" + edges[i][j] + "); ");
				}
			}
			System.out.println();
			
		}
		
	}
	

	public void makeEmpty() {
		if(!isEmpty()) {
			size = DEFAULT_SIZE;
			counter = 0;
			vertices = (Ttype[])new Object[size];
			edges = new int[size][size];
			isVerticeVisited = new boolean[size];
			System.gc();
		}
	}
	
	public String searchPathUsingDepthFirst(Ttype elementFrom, Ttype elementTo) throws Exception{
		if(elementFrom == null || elementTo == null)
		{
			throw new Exception("Kāds no ievades parametriem nav atbilstošs");
		}
		
		if(!isVerticeExist(elementFrom))
		{
			throw new Exception("Virsotne, no kura vēlas veidot saiti, neeksistē grafā");
		}
		
		if(!isVerticeExist(elementTo))
		{
			throw new Exception("Virsotne, uz kuru vēlas veidot saiti, neeksistē grafā");
		}
		
		
		if(elementFrom.equals(elementTo))
		{
			throw new Exception("Abas virsotnes sakrīt, tāpēc ceļu nav iespejams atrast");
		}
		
		for(int i = 0; i < counter ;i++)
		{
			isVerticeVisited[i] = false;
		}
		
		
		Stack<Ttype> verticesInStack = new Stack<>();
		verticesInStack.push(elementFrom);
		int indexOfVertice = getIndexOfVertice(elementFrom);
		isVerticeVisited[indexOfVertice] = true;
		String resultPath = "PATH";
		
		do
		{
			
			Ttype elementFromStack = verticesInStack.pop();//iedos elementu, kurš tiks izdēst
			if(elementFromStack.equals(elementTo)) {
				resultPath += " -> " + elementFromStack + "\n";
				return resultPath;
			}
			else
			{
				resultPath += " -> " + elementFromStack;
				indexOfVertice = getIndexOfVertice(elementFromStack);
				isVerticeVisited[indexOfVertice] = true;
				
				//atrast visus kaimiņu virsotnes un ielikt stekā
				ArrayList<Ttype> allNeighbours = getNeighbours(indexOfVertice);
				
				for(Ttype tempV : allNeighbours) {
					int indexOfNeighbour = getIndexOfVertice(tempV);
					if(isVerticeVisited[indexOfNeighbour] == false)
					{
						verticesInStack.push(tempV);
					}
				}
				
			}
	
		}
		while(!verticesInStack.isEmpty());
		
		return "Ceļš nav atrasts";
		
		
	}
	
	//ejam cauri 2D saišu masīvam un mēginām atrast visas saites konkrētajai virsotnei un atgriežam virsotnes, uz kurām ir šīs saites
	private ArrayList<Ttype> getNeighbours(int indexOfVertice)
	{
		ArrayList<Ttype> result = new ArrayList<>();
		
		for(int i = 0; i < counter; i++)
		{
			if(edges[indexOfVertice][i] > 0)//pārbaudām, vai eksistē saite ar padoto pilsētu
			{
				Ttype verticeWithEdge = vertices[i];
				result.add(verticeWithEdge);
			}
		}
		return result;
		
	}
	
	
}
