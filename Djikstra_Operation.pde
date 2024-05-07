float[][] valuation (float[][] source, int mSize, int edges)
{
  float[][] valuation = new float[mSize][mSize];
  for(int i = 0; i< mSize; i++) //infinity filling
    for(int j = 0; j< mSize ; j++)
      valuation[i][j] = 1000000000.0; //billion = infinity
      
  for(int i = 0; i< edges; i++)    //value filling
    valuation[int(source[i][1])][int(source[i][2])] = source[i][3];

  //for(int i = 0; i< mSize; i++) //read
  //{
  //  println();
  //  for(int j = 0; j< mSize ; j++)
  //    print(valuation[i][j] + "  ");
  //}
  
  return valuation;
}


int[] Dijkstra(float[][] source, int s, int S)
{
  //int s = 0;                 //s the starting vertex (S-1)
  //int S = 6;                 //S the number of vertexes
  float[] L = new float[S];    //L the table of n entries contraining distances from s to other edges
  int[] P = new int[S];        //P the table of n entries containing the pedecessor of each vertex in the shortest path of origin s
  IntList M = new IntList();   //M the liste of processed vertexes (so V\M the list of remaining vertexes to process)
  IntList V = new IntList();   //V the liste of all vertexes to process
  for(int v = 0; v < S;v++)    //fill V
    V.append(v);
  
  /*-----Initialisation-----*/
  
  L[s] = 0;
  P[s] = s;
  for(int i =0; i<S;i++)
  {
    if(i!=s)
    {
      if(source[s][i] != 1000000000)  // If i successor of s (if inequal to infinity(=1billion here))
      {
        L[i] = source[s][i];          // Enter weight of i in the distance table from s to the other vertexes
        P[i] = s;                     // i predecessor of s vertex
      }
      else
      {
        L[i]=1000000000;              //virtually infinity
        P[i]=-1;                      //virtually null
      }
    }
  }
  println(P);
  M.append(s);                        //s has been processed
  
  /*-----Reccurence-----*/
  
  while(!(M.size() == V.size()))      //let's process all vertexes
  {
    for(int v = 0; v<S ;v++)         // let's choose v in V\M 
    {
      if(V.hasValue(v) && !M.hasValue(v))
      {
        for(int i = v; i<S ;i++)     // if i is in V\M such as i is a succesor of x -- this with i in V\M: L[v]≤L[i] (that's why int i=v)
        {
          if(V.hasValue(i) && !M.hasValue(i))//appartenance
          {
            if(source[v][i] != 1000000000);  //succession
            {
              if(L[i]>(L[v] + source[v][i])) 
              {
                L[i] = L[v] + source[v][i];
                P[i] = v; 
              }
            
            }
          }
        }
      M.append(v);
      }
      
      println(P);
      println("m:" + M);
      println(V);
    }
    println(M);
  }
  println(P);
  return(P);
}

void predecessor(int[] P)
{
  for(int x = 0 ; x< P.length;x++)
  {
    println(x + " has for predecessor vertex " + P[x]);
  }
}

void draw_predecessor(int[] P)
{
  textSize(10);
  //textMode(CENTER);
  fill(0, 102, 153, 204);
  int multi=width/(P.length+2);
  for(int x = 0 ; x< P.length;x++)
  {
    text("Here are what edges are to follow to get as quickly as possible from the starting vertex to the end vertex", width/4, multi * 0.3);
    text("vertex "+ x, multi * (x+0.5), multi * 0.2);
    text("has for predecessor vertex "+ x, multi * 0.2, multi * (x+0.5));
    circle(multi * (x+0.5), multi * (P[x]+0.5), 55);     // vertexes
    line(multi * (x+1),0, multi * (x+1), height);        // shape
    line(multi * (x+0.5), multi * (P[x]+0.5), multi * (P[x]+0.5), multi * (P[P[x]]+0.5)); //edge
    //println(x + " has for predecessor vertex " + P[x]);
  }

}
