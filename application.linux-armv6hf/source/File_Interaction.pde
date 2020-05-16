// The following short CSV file called "StudiedGraph.txt" is parsed
// in the code below. It must be in the project's "data" folder.
//
//n,i,f,c
//0,1,2,10.0
//1,1,5,20.0

Table StudiedGraph;

float[][] read_graph()
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
    studiedTable[n][0]=float(n);
    studiedTable[n][1]=float(i);
    studiedTable[n][2]=float(f);
    studiedTable[n][3]=c;
    println(n + "," + i + "," + f + "," + c);
  }
  
  return studiedTable;
}



void write_graph(graph g)
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
