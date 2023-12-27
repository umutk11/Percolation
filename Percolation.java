import java.util.Random;

public class Percolation {

    boolean[][] grid = new boolean[4][4];
    int gridSize = 4;
    int gridSquared = 18;
    WeightedQuickUnionUF wquFind;
    int virtualTop;
    int virtualBottom;

    
    // given an n-by-n matrix of open sites, return an n-by-n matrix
    // of sites reachable from the top
    private static boolean[][] flow(boolean[][] grid) {
        int n = grid.length;
        boolean[][] isFull = new boolean[n][n];
        for (int j = 0; j < n; j++) {
            flow(grid, isFull, 0, j);
        }
        return isFull;
    }

    // determine set of full sites using depth first search
    private static void flow(boolean[][] grid, boolean[][] isFull, int i, int j) {
        int n = grid.length;

        // base cases
        if (i < 0 || i >= n) return;    // invalid row
        if (j < 0 || j >= n) return;    // invalid column
        if (!grid[i][j]) return;      // not an open site
        if (isFull[i][j]) return;       // already marked as full

        // mark i-j as full
        isFull[i][j] = true;

        flow(grid, isFull, i+1, j);   // down
        flow(grid, isFull, i, j+1);   // right
        flow(grid, isFull, i, j-1);   // left
        flow(grid, isFull, i-1, j);   // up
    }

    // draw the n-by-n boolean matrix to standard draw
    private static void show(boolean[][] a, boolean which) {
        int n = a.length;
        StdDraw.setXscale(-1, n);
        StdDraw.setYscale(-1, n);
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (a[i][j] == which)
                    StdDraw.filledSquare(j, n-i-1, 0.5);
    }

    // return a random n-by-n boolean matrix, where each entry is
    // true with probability p
    private static boolean[][] random(int n, double p) {
        boolean[][] a = new boolean[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                a[i][j] = StdRandom.bernoulli(p);
        return a;
    }

    //it checks whether virtual top and virtual bottom connected. if connected gives true, else false
    private static String percolationCheck(boolean[][] grid) {
        int n = grid.length;
        boolean[][] isFull = flow(grid);
        for (int j = 0; j < n; j++) {
            if (isFull[n-1][j]) {
                return "system percolates";
                
            }
        }
        return "system does not percolates";
    }

    private static boolean booleanPercolationCheck(boolean[][] grid) {
        int n = grid.length;
        boolean[][] isFull = flow(grid);
        for (int j = 0; j < n; j++) {
            if (isFull[n-1][j]) {
                return true;
                
            }
        }
        return false;
    }

    //if the given grid location's value is false, it changes it to true
    private static void openSite(int r, int c, boolean[][] a){
        if(a[r][c] = false){
            a[r][c] = true;
        }
    }

    private static void openAllSites(boolean[][] a){
        for(int i = 0; i < a.length; i++){
            for(int j = 0; j < a.length; j++){
                if(a[i][j] = false){
            a[i][j] = true;
        }
            }
        }
    }

    //prints a grid which blue means true and black means false
    private static void displayGrid(boolean[][] a){
        double xloc= 0.10; //initial x axis location of first element
        double yloc= 0.85; //initial y axis location of first element
        double radius = 0.045; //size of blocks
        int counter = 0; //i used it for getting a square shape
        int len = a.length;
        
        for(int i = 0; i < len; i++){
            for(int j = 0; j < len; j++){
                if(a[i][j] == true){ //it checks each block whether if its true or false, depending on the value, generates a blue or black box
                    if(counter == len){
                        yloc = yloc - 0.09;
                        xloc = 0.10;
                        counter = 0; 
                    } //after n blocks, the next row starts
                    StdDraw.setPenColor(StdDraw.BOOK_BLUE);
                    StdDraw.filledSquare(xloc, yloc, radius); 
                    xloc = xloc + 0.09;
                    counter++; //after each element, it updates counter so program makes it a square shape
                }

                if(a[i][j] == false){
                    if(counter == len){
                        yloc = yloc - 0.09;
                        xloc = 0.10;
                        counter = 0;
                    }
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.filledSquare(xloc, yloc, radius);
                    xloc = xloc + 0.09;
                    counter++;
                }
            }

            if(booleanPercolationCheck(a) == true){ //if system percolates, it prints a green text in bottom that says it percolates
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.filledRectangle(0.5, 0, 1, 0.1);
                StdDraw.setPenColor(StdDraw.GREEN);
                StdDraw.text(0.5, 0.06, "system percolates");
            }

            if(booleanPercolationCheck(a) == false){ //if system percolates, it prints a red text in bottom that says it does not percolates
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.filledRectangle(0.5, 0, 1, 0.1);
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.text(0.5, 0.06, "system does not percolates");
            }
        }

        
    }

    // test client
    public static void main(String[] args) {
        //boolean[][] grid = StdArrayIO.readBoolean2D(); this is an alternative version which creates array with inputs from user
        
        int i = 0;
        while(i < 10){ //produces and prints a new grid in every 2 seconds.
            try{
                boolean[][] a = random(6, 0.5); //each block has %50 chance of true or false
                System.out.println("Grid Number: "+(i+1)); 
                System.out.println(percolationCheck(a));
                displayGrid(a);
                Thread.sleep(2000); //it stops loop for 2 secs so we can check the grid
                i++;
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }   
        }
    }
}
