import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import javax.swing.JOptionPane; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Djikstra extends PApplet {



public void setup()
{
  
  frameRate(30);
}
public float[][] valuation (float[][] source, int mSize, int edges)
{
  float[][] valuation = new float[mSize][mSize];
  for(int i = 0; i< mSize; i++) //infinity filling
    for(int j = 0; j< mSize ; j++)
      valuation[i][j] = 1000000000.0f; //billion = infinity
      
  for(int i = 0; i< edges; i++)    //value filling
    valuation[PApplet.parseInt(source[i][1])][PApplet.parseInt(source[i][2])] = source[i][3];

  //for(int i = 0; i< mSize; i++) //read
  //{
  //  println();
  //  for(int j = 0; j< mSize ; j++)
  //    print(valuation[i][j] + "  ");
  //}
  
  return valuation;
}


public int[] djikstra(float[][] source, int s, int S)
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

public void predecessor(int[] P)
{
  for(int x = 0 ; x< P.length;x++)
  {
    println(x + " has for predecessor vertex " + P[x]);
  }
}

public void draw_predecessor(int[] P)
{
  textSize(10);
  //textMode(CENTER);
  fill(0, 102, 153, 204);
  int multi=width/(P.length+2);
  for(int x = 0 ; x< P.length;x++)
  {
    text("Here are what edges are to follow to get as quickly as possible from the starting vertex to the end vertex", width/4, multi * 0.3f);
    text("vertex "+ x, multi * (x+0.5f), multi * 0.2f);
    text("has for predecessor vertex "+ x, multi * 0.2f, multi * (x+0.5f));
    circle(multi * (x+0.5f), multi * (P[x]+0.5f), 55);     // vertexes
    line(multi * (x+1),0, multi * (x+1), height);        // shape
    line(multi * (x+0.5f), multi * (P[x]+0.5f), multi * (P[x]+0.5f), multi * (P[P[x]]+0.5f)); //edge
    //println(x + " has for predecessor vertex " + P[x]);
  }

}
// The following short CSV file called "StudiedGraph.txt" is parsed
// in the code below. It must be in the project's "data" folder.
//
//n,i,f,c
//0,1,2,10.0
//1,1,5,20.0

Table StudiedGraph;

public float[][] read_graph()
{
  StudiedGraph = loadTable("StudiedGraph.csv", "header");
  float[][] studiedTable = new float[StudiedGraph.getRowCount()][4];
  println(StudiedGraph.getRowCount() + " total edges in table()");
  println("n_i_f_c");
  
  for (TableRow row : StudiedGraph.rows()) 
  {
    int n = row.getInt("n");
    int i = row.getInt("i");
    int f = row.getInt("f");
    float c = row.getFloat("c");
    studiedTable[n][0]=PApplet.parseFloat(n);
    studiedTable[n][1]=PApplet.parseFloat(i);
    studiedTable[n][2]=PApplet.parseFloat(f);
    studiedTable[n][3]=c;
    println(n + "," + i + "," + f + "," + c);
  }
  
  return studiedTable;
}



public void write_graph(graph g)
{
  StudiedGraph = new Table();
  StudiedGraph.addColumn("n");
  StudiedGraph.addColumn("i");
  StudiedGraph.addColumn("f");
  StudiedGraph.addColumn("c");
  
  TableRow newRow = StudiedGraph.addRow();
  for (int i =0; i< g.nbEdges;i++)
  {
    newRow.setInt("n",i);
    newRow.setInt("i",g.theEdges[i].initial);
    newRow.setInt("f",g.theEdges[i].finale);
    newRow.setFloat("c",g.theEdges[i].cost);
  }
  
  saveTable(StudiedGraph, "data/StudiedGraph.cse");
}



/**
 * Affiche une fenêtre de dialogue demandant de rentrer un entier
 *
 * @param {String} s (optionel) L'optionelle chaîne de caractères à afficher
 *
 * @return {int} Retourne l'entier entré (0 si pas un entier)
 *
 */
public int askInteger () {
  int i = 0;
  String r = JOptionPane.showInputDialog(null, "Entrez un entier", "askInteger", JOptionPane.QUESTION_MESSAGE);
  
  try {
    i = Integer.parseInt(r);
  } 
  catch(NumberFormatException e) {
    println("Note: Ce n'est pas un entier!");
  }

  return i;
}

public int askInteger (String s) {
  int i = 0;
  String r = JOptionPane.showInputDialog(null,s, "askInteger", JOptionPane.QUESTION_MESSAGE);
  
  try {
    i = Integer.parseInt(r);
  } 
  catch(NumberFormatException e) {
    println("Note: Ce n'est pas un entier!");
  }
  return i;
}


/**
 * Affiche une fenêtre de dialogue demandant de rentrer un réel
 *
 * @param {String} s (optionel) L'optionelle chaîne de caractères à afficher
 *
 * @return {float} Retourne le réel entré (0 si pas un réel)
 *
 */
public float askFloat () {
  float i = 0;
  String r = JOptionPane.showInputDialog(null, "Entrez un réel", "askFloat", JOptionPane.QUESTION_MESSAGE);

  try {
    i = Float.parseFloat(r);
  } 
  catch(NumberFormatException e) {
    println("Note: Ce n'est pas un réel!");
  }
  catch(NullPointerException e) {
    println("Note: Aucune valeur entrée.");
  }

  return i;
}

public float askFloat (String s) {
  float i = 0;
  String r = JOptionPane.showInputDialog(null, s, "askFloat", JOptionPane.QUESTION_MESSAGE);

  try {
    i = Float.parseFloat(r);
  } 
  catch(NumberFormatException e) {
    println("Note: Ce n'est pas un réel!");
  }
  catch(NullPointerException e) {
    println("Note: Aucune valeur entrée.");
  }

  return i;
}


/**
 * Affiche une fenêtre de dialogue demandant de rentrer un réel (plus précis/grand que askFloat)
 *
 * @param {String} s (optionel)L'optionelle chaîne de caractères à afficher
 *
 * @return {double} Retourne le réel entré (0 si pas un réel)
 *
 */
public double askDouble () {
  double i = 0;
  String r = JOptionPane.showInputDialog(null, "Entrez un réel", "askDouble", JOptionPane.QUESTION_MESSAGE);

  try {
    i = Double.parseDouble(r);
  } 
  catch(NumberFormatException e) {
    println("Note: Ce n'est pas un réel!");
  }
  catch(NullPointerException e) {
    println("Note: Aucune valeur entrée.");
  }

  return i;
}

public double askDouble (String s) {
  double i = 0;
  String r = JOptionPane.showInputDialog(null, s, "askDouble", JOptionPane.QUESTION_MESSAGE);

  try {
    i = Double.parseDouble(r);
  } 
  catch(NumberFormatException e) {
    println("Note: Ce n'est pas un réel!");
  }
  catch(NullPointerException e) {
    println("Note: Aucune valeur entrée.");
  }

  return i;
}


/**
 * Affiche une fenêtre de dialogue demandant de rentrer un caractère
 *
 * @param {String} s (optionel)L'optionelle chaîne de caractères à afficher
 *
 * @return {char} Retourne le caractère entré (NUL char retouné si pas un caractère (vide))
 *
 */
public char askChar () {
  char i = 0;
  String r = JOptionPane.showInputDialog(null, "Entrez un caractère", "askChar", JOptionPane.QUESTION_MESSAGE);

  try {
    i = r.charAt(0);
  } 
  catch(NumberFormatException e) {
    println("Note: Ce n'est pas un caractère!");
  }
  catch(StringIndexOutOfBoundsException e) {
    println("Note: Aucun caractère entré.");
  }

  return i;
}

public char askChar (String s) {
  char i = 0;
  String r = JOptionPane.showInputDialog(null, s, "askChar", JOptionPane.QUESTION_MESSAGE);

  try {
    i = r.charAt(0);
  } 
  catch(NumberFormatException e) {
    println("Note: Ce n'est pas un caractère!");
  }
  catch(StringIndexOutOfBoundsException e) {
    println("Note: Aucun caractère entré.");
  }

  return i;
}

/**
 * Affiche une fenêtre de dialogue demandant de rentrer un chaîne de caractère
 *
 * @param {String} s (optionel) L'optionelle chaîne de caractères à afficher
 *
 * @return {String} Retourne la chaîne de caractère entrée (null si aucune n'est rentrée)
 *
 */
public String askString () {
  String r = JOptionPane.showInputDialog(null, "Entrez une chaine de caractère", "askString", JOptionPane.QUESTION_MESSAGE);
  return r;
}

public String askString (String s) {
  String r = JOptionPane.showInputDialog(null, s, "askString", JOptionPane.QUESTION_MESSAGE);
  return r;
}
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


public graph graph_entry()
{
  int nbEdges = askInteger("Total number of edges ?");
  int nbSommets = askInteger("Total number of vertexes ?");
  graph itsName = new graph(nbSommets, nbEdges, edges_entry(nbEdges));
  return itsName;
}

public edge[] edges_entry(int nbEdges)
{
  edge[] itsName = new edge[nbEdges];
  for(int i =0; i<nbEdges; i++)
  {
    println("For edge number " + i);
    itsName[i] = new edge (askInteger("Origin vertex number ?"), askInteger("Destination vertex number?"), askFloat("Cost ?"));
  }
  return itsName;
}

public void show_graph(graph lol)
{
  println(lol.n +" vertexes and "+ lol.nbEdges + " edges.");
  println("n,i,f,c");
  for (int i =0; i< lol.nbEdges;i++)
  {
    println(i + "," + lol.theEdges[i].initial + "," + lol.theEdges[i].finale + "," + lol.theEdges[i].cost);
  }
}

public int[][] adjacency(graph g)
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

public void show_matrix(int[][] matrix, graph g)
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
public int[][] product (int[][] M1, int[][] M2)
{
  int[][] M3 = new int[M1.length][M1.length];
  int time=0;
  for (int i =0 ; i < M1.length ; i++)
  {
    for (int j =0; j < M1.length ; j++)
    {
      for (int k =0; k < M1.length ; k++)
      {
        time+= M1[i][k]*M2[k][j];
      }
      M3[i][j]=time;
      time=0;
    }
  }
  return M3;
}

public int[][] sum (int[][] M1, int[][] M2)
{
  int[][] M3 = new int[M1.length][M1.length];
  for (int i =0 ; i < M1.length ; i++)
  {
    for (int j =0; j < M1.length ; j++)
    {
      M3[i][j]=M1[i][j]+M2[i][j];
    }
  }
  return M3;
}

public boolean equality( int[][] M1, int[][] M2)
{
  boolean ok = true;
  for (int i =0 ; i < M1.length ; i++)
  {
    for (int j =0; j < M1.length ; j++)
    {
      if(M1[i][j]!=M2[i][j])
      ok = false;
    }
  }
  return ok;
}

public int[][] transitivity_matrix(graph g)
{
  int[][] multiplied = new int[g.n][g.n];
  int[][] multiplier = new int[g.n][g.n];
  int[][] after = new int[g.n][g.n];
  multiplied = adjacency(g);
  multiplier = multiplied;
  after = multiplied;
  for (int i = 0; i < g.n ; i++)//multiplications qum
  {
    if(i == 0) //Identity matrix
    {
      for (int j = 0; j < g.n ; j++)
        for (int k = 0; k < g.n ; k++)
          after[j][k]=0;
      for (int j = 0; j < g.n ; j++)
      {
        after[i][i]=1;
      }
    }
    else if(i == 1)
    {
      after = sum(multiplied,after);
    }
    else
    {
      multiplied = product(multiplied,multiplier);
      after = sum(multiplied,after);
    }
  }
  return after;
}
class Button
{
  int posX;
  int posY;
  int dimX;
  int dimY;
  int c;
  int c2;
  String str;
  Button(int x, int y, int X, int Y, int c, int c2, String str)
  {
    posX = x;
    posY= y;
    dimX = X;
    dimY = Y;
    this.c = c;
    this.c2 = c2;
    this.str = str;
  }
  
  public void display()
  {
    //rectMode();
    if (pushed())
    {
      fill(c2);
    } 
    else
    {
      fill(c);
    }
    rect(posX, posY, dimX, dimY);
    if (pushed())
    {
      fill(c);
    } 
    else
    {
      fill(c2);
    }
    textSize(dimY/1.5f);
    text(str, posX + dimX /8, posY + (dimY*3)/4);
  }
  
  public boolean pushed()
  {
    return(mousePressed && mouseX > posX && mouseY > posY && mouseX < dimX+posX && mouseY < dimY+posY);
  }
  
}

Button create_graph = new Button(width, PApplet.parseInt(2.3f*height),4*width, height/2, 0xffFF0077, 0xff9605FF, "create new graph");
Button read_graph = new Button(width, PApplet.parseInt(3.0f*height),4*width, height/2, 0xffFF0077, 0xff9605FF, "read new graph");
Button view_license = new Button(width, PApplet.parseInt(3.7f*height),4*width, height/2, 0xffFF0077, 0xff9605FF, "view license");
Button go_back = new Button(3*width, PApplet.parseInt(4.4f*height),2*width, height/2, 0xffFF0077, 0xff9605FF, "go back");
Button quit = new Button(5*width, PApplet.parseInt(4.4f*height),2*width, height/2, 0xffFF0077, 0xff9605FF, "quit");
Button GNUGPL = new Button(width/2, PApplet.parseInt(4*height),3*width, height/4, 0xffFF0077, 0xff9605FF, "View more informations");
int menu = 0;
int[] graphic;
int nVertexes = 0;
int nEdges;
int start;

public void draw()
{  
  background(0xffFFF305);
  textSize(height/15);
  switch(menu) {
  case 0:             // first screen
    println("first");   
    text("DJIKSTRA", width/3, height/5);  //title
    text("Choose what action to do", width/4, (2*height)/5);                    //question
    create_graph.display();                                                            //available cases
    read_graph.display();
    view_license.display();
    quit.display();
    if(create_graph.pushed())
      menu = 1;
    if(read_graph.pushed())
      menu = 2;
    if(view_license.pushed())
      menu = 3;
    if(quit.pushed())
       exit();
    break;
  case 1: 
    println("create");   //create new graph
    char q = askChar("Are you sure ? (y/N)");
    if(str(q)==("y") || str(q)=="Y")
    {
      graph g = graph_entry();
      show_graph(g);
      show_matrix(adjacency(g), g);
      show_matrix(transitivity_matrix(g),g);
      write_graph(g);
    }
    menu = 0;
    break;
  case 2: 
    println("read");   //read djikstra's shortest paths
    if(nVertexes == 0)
    {
      nVertexes = askInteger("Number of vertexes ? ~ points");
      nEdges = askInteger("Number of edges ? ~ lines");
      start = askInteger("Starting vertex ? (0 to " + (nVertexes-1) + ") for now must be 0");
    }
    float[][] edges = read_graph();
    //valuation(edges, nVertexes, nEdges);
    //djikstra(valuation(edges, nVertexes, nEdges));
    //predecesseur(djikstra(valuation(edges, nVertexes, nEdges)));
    graphic = djikstra(valuation(edges, nVertexes, nEdges), start, nVertexes);
    draw_predecessor(graphic);
    go_back.display();
    if(go_back.pushed())
      menu = 0;
    break;
  case 3:            //GNU Public licence
    println("license");
    textSize(height/20);
    text("This is program is under the GNU Public licence", width/20, height/5);
    textSize(height/40);
    text("Quick Summary : You may copy, distribute and modify the software as long as you track changes/dates in source files.", width/15, (2*height)/5);    
    text("Any modifications to or software including (via compiler) GPL-licensed code must also be made available under the GPL", width/15, (2.5f*height)/5);
    text("along with build & install instructions.", width/15, (2.7f*height)/5);
    text("Source : https://tldrlegal.com/license/gnu-general-public-license-v3-(gpl-3)", width/15, (3*height)/5);
    text("Written by Joshua Sacchet on 2020.04, keyboard interactions' code provided by unilim, file interactions' model come from", width/15, (3.5f*height)/5);
    text("processing.org/reference/ and djikstra's algorithm was implemented thanks to some explanations on supinfo.com", width/15, (3.7f*height)/5);
    
    GNUGPL.display();
    if(GNUGPL.pushed())
      link("https://tldrlegal.com/license/gnu-general-public-license-v3-(gpl-3)");
    
    go_back.display();
    if(go_back.pushed())
      menu = 0;
    break;
  }
}



  
  
  


//case gnu

//button go back or quit
  public void settings() {  size(800,500); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Djikstra" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
