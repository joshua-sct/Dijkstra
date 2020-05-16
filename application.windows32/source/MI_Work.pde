// Defining what's an edge
class edge
{
  int initial;  //initial vertex
  int finale;    //final vertex
  float cost;    //vertex cost
  
  edge(int init, int fin, float ct)
  {
    initial = init;
    finale = fin;
    cost = ct;
  }
  
  edge(edge a)
  {
    initial=a.initial;
    finale=a.finale; 
    cost=a.cost;
  }
}

// Graph's implementation
class graph
{
  int n;                   // Number of vertexes in a graph. Let's suppose a graph with n vertexes named 0,1,2,...n-1
  int nbEdges;             // Number of edges in the graph
  edge[] theEdges;         // List of the edges in the graph


  graph(int nbs, int nba, edge[] edges)
  {
    n=nbs;
    nbEdges=nba;
    theEdges=new edge[nba];
    for(int i=0;i<nba;i++)
      theEdges[i]=edges[i];
  }
  
  graph(graph g)
  {
    n=g.n;
    nbEdges=g.nbEdges;
    theEdges=new edge[nbEdges];
    for (int i=0;i<nbEdges;i++)
      theEdges[i]=g.theEdges[i];
  }
}


graph graph_entry()
{
  int nbEdges = askInteger("Total number of edges ?");
  int nbSommets = askInteger("Total number of vertexes ?");
  graph itsName = new graph(nbSommets, nbEdges, edges_entry(nbEdges));
  return itsName;
}

edge[] edges_entry(int nbEdges)
{
  edge[] itsName = new edge[nbEdges];
  for(int i =0; i<nbEdges; i++)
  {
    println("For edge number " + i);
    itsName[i] = new edge (askInteger("Origin vertex number ?"), askInteger("Destination vertex number?"), askFloat("Cost ?"));
  }
  return itsName;
}

void show_graph(graph lol)
{
  println(lol.n +" vertexes and "+ lol.nbEdges + " edges.");
  println("n,i,f,c");
  for (int i =0; i< lol.nbEdges;i++)
  {
    println(i + "," + lol.theEdges[i].initial + "," + lol.theEdges[i].finale + "," + lol.theEdges[i].cost);
  }
}

int[][] adjacency(graph g)
{
  int[][] matrix = new int[g.n][g.n];
  //0 then 1 filling
  for (int i = 0; i<g.n; i++)
    for (int j = 0; j<g.n; j++)
      matrix[i][j]=0;
  for (int i = 0; i<g.nbEdges; i++)
  {
    matrix[g.theEdges[i].initial][g.theEdges[i].finale]=1; // Let's put a 1 for each origin line matching destination column (oriented graph here)
  }
  return matrix;
}

void show_matrix(int[][] matrix, graph g)
{
  for(int i = 0 ; i< g.n ; i++)
    print("   " + i);
  println("");
  for(int i = 0 ; i< g.n ; i++)
  {
    print(i + ":");
    for(int j = 0 ; j< g.n ; j++)
      print("   " + matrix[i][j]);
    println();
  }
}
