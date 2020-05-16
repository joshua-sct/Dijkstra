int[][] product (int[][] M1, int[][] M2)
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

int[][] sum (int[][] M1, int[][] M2)
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

boolean equality( int[][] M1, int[][] M2)
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

int[][] transitivity_matrix(graph g)
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
