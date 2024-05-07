class Button
{
  int posX;
  int posY;
  int dimX;
  int dimY;
  color c;
  color c2;
  String str;
  Button(int x, int y, int X, int Y, color c, color c2, String str)
  {
    posX = x;
    posY= y;
    dimX = X;
    dimY = Y;
    this.c = c;
    this.c2 = c2;
    this.str = str;
  }
  
  void display()
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
    textSize(dimY/1.5);
    text(str, posX + dimX /8, posY + (dimY*3)/4);
  }
  
  boolean pushed()
  {
    return(mousePressed && mouseX > posX && mouseY > posY && mouseX < dimX+posX && mouseY < dimY+posY);
  }
  
}

Button create_graph = new Button(width, int(2.3*height),4*width, height/2, #FF0077, #9605FF, "create new graph");
Button read_graph = new Button(width, int(3.0*height),4*width, height/2, #FF0077, #9605FF, "read new graph");
Button view_license = new Button(width, int(3.7*height),4*width, height/2, #FF0077, #9605FF, "view license");
Button go_back = new Button(3*width, int(4.4*height),2*width, height/2, #FF0077, #9605FF, "go back");
Button quit = new Button(5*width, int(4.4*height),2*width, height/2, #FF0077, #9605FF, "quit");
Button GNUGPL = new Button(width/2, int(4*height),3*width, height/4, #FF0077, #9605FF, "View more informations");
int menu = 0;
int[] graphic;
int nVertexes = 0;
int nEdges;
int start;

void draw()
{  
  background(#FFF305);
  textSize(height/15);
  switch(menu) {
  case 0:             // first screen
    println("first");   
    text("Dijkstra", width/3, height/5);  //title
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
    println("read");   //read Dijkstra's shortest paths
    if(nVertexes == 0)
    {
      nVertexes = askInteger("Number of vertexes ? ~ points");
      nEdges = askInteger("Number of edges ? ~ lines");
      start = askInteger("Starting vertex ? (0 to " + (nVertexes-1) + ") for now must be 0");
    }
    float[][] edges = read_graph();
    //valuation(edges, nVertexes, nEdges);
    //Dijkstra(valuation(edges, nVertexes, nEdges));
    //predecesseur(Dijkstra(valuation(edges, nVertexes, nEdges)));
    graphic = Dijkstra(valuation(edges, nVertexes, nEdges), start, nVertexes);
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
    text("Any modifications to or software including (via compiler) GPL-licensed code must also be made available under the GPL", width/15, (2.5*height)/5);
    text("along with build & install instructions.", width/15, (2.7*height)/5);
    text("Source : https://tldrlegal.com/license/gnu-general-public-license-v3-(gpl-3)", width/15, (3*height)/5);
    text("Written by Joshua Sacchet on 2020.04, keyboard interactions' code provided by unilim, file interactions' model come from", width/15, (3.5*height)/5);
    text("processing.org/reference/ and Dijkstra's algorithm was implemented thanks to some explanations on supinfo.com", width/15, (3.7*height)/5);
    
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
