import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class TwoDTree implements TwoDTreeInterface{

    private TreeNode head;

    //mplementing TreeNode
    private static class TreeNode{
        
        private Point data;
        private TreeNode l,r;
        private Rectangle rect;

        public TreeNode(Point data, int[] c){
            this.data = data;
            rect = new Rectangle(c[0], c[1], c[2], c[3]);
        }
    }

    //constructor
    public TwoDTree(){
        this.head = null;
    }

    //check if the tree is empty
    public boolean isEmpty(){
        return head == null;
    }

    //get the size of the tree
    public int size(){
        return size(head);
    }

    //helper function to calculate the size of the tree
    private int size(TreeNode node) {
        if (node == null) {
          return 0;
        }
        return 1 + size(node.l) + size(node.r);
      }


      private int compare(Point p, TreeNode node, boolean level) {
        //if the given level is even, compare x-coordinates of the point and node's data
        if (level) {
            return p.x() - node.data.x();
        }
        //if the given level is odd, compare y-coordinates of the point and node's data
        else {
            return p.y() - node.data.y();
        }
    }
    

    //method that inserts points
    public void insert(Point p) {
        head = insert(head, p, true, new int[] {0, 0, 100, 100});
    }
    

    //Helper function
    private TreeNode insert(TreeNode node, Point p, boolean level, int[] c) {
        //create new node if at null node
        if (node == null) {
            return new TreeNode(p, c);
        }
    
        //get comparison value based on even or odd level
        double comp = compare(p, node, level);
    
        //insert to the right if even level and x-value is greater than node x-value
        if (comp > 0 && level) {
            //update x_min for right child
            c[0] = node.data.x(); 
            //recursive call for right child
            node.r = insert(node.r, p, !level, c); 
        }

        //insert to the right if odd level and yvalue is greater than node y-value
        else if (comp > 0 && !level) {
             //update y_min for right child
            c[1] = node.data.y();
            node.r = insert(node.r, p, !level, c);
        }

        //insert to the left if even level and x-value is less than node x-value
        else if (comp < 0 && level) {
            //update x_max for left child
            c[2] = node.data.x(); 
            node.l = insert(node.l, p, !level, c); 
        }

        //insert to the left if odd level and y-value is less than node y-value
        else if (comp < 0 && !level) {
            //update y_max for left child
            c[3] = node.data.y(); 
            node.l = insert(node.l, p, !level, c);
        }

        //no duplicate points are added to the tree.
        else if (!node.data.equals(p))
            node.r = insert(node.r, p, !level, c);
    
        // return the node
        return node;
    }
    
    //method that returns true if it finds the point we are looking for
    public boolean search(Point p) {
        TreeNode current = head;
        boolean level = true;

        //traverse the tree until the node is found or we reach the end of the tree
        while (current != null) {

            //check if the current node matches the point p
            if (current.data.equals(p)) {
                return true;
            }

            //compare the point p with the current node to determine which direction to move in
            if (compare(p, current, level) < 0) {
                //move to the left chil node
                current = current.l;  
            } else {
                //move to the right child node
                current = current.r;  
            }

            level = !level;  //toggle the level flag
        }

        //if we reac here, the point was not found in the tree
        return false;
    }



    public Point nearestNeighbor(Point p) {
        //if the tree is empty, return null
        if (isEmpty()) {
            return null;
        }
        
        //cal the private helper function starting at the root node and using the root as the initial best point
        return nearestNeighbor(head, p, head.data);
    }
    
    private Point nearestNeighbor(TreeNode node, Point p, Point best) {
        //if we've reached a null node, return best
        if (node == null) {
            return best;
        }
        
        //calculate the distances
        double distToBest = p.distanceTo(best);
        double distToNode = p.distanceTo(node.data);
        
        //if th distance to the current node is les than the distance to the currentt best point, update the best point
        if (distToNode < distToBest) {
            best = node.data;
        }
        
        //of we ve reached a leaf node, return the current best point
        if (node.l == null && node.r == null) {
            return best;
        }
        
        //determine which child to explore first based on their distances
        if (node.l == null || (node.r != null && node.r.data.squareDistanceTo(p) < node.l.data.squareDistanceTo(p))) {
            //explore the right child first, updating the best point as we go
            best = nearestNeighbor(node.r, p, best);
            //if the distance from the query point to the left child's bounding box is less than the current best distance, explore the left child as well
            if (node.l != null && node.l.data.squareDistanceTo(p) < best.squareDistanceTo(p)) {
                best = nearestNeighbor(node.l, p, best);
            }
        } else {
            //explore the left child first, updating the best point as we go
            best = nearestNeighbor(node.l, p, best);
            //if the distance from the query point to the right child's bounding box is less than the current best distance, explore the right child as well
            if (node.r != null && node.r.data.squareDistanceTo(p) < best.squareDistanceTo(p)) {
                best = nearestNeighbor(node.r, p, best);
            }
        }
        
        //return the final  best point
        return best;
    }


public Stack<Point> rangeSearch(Rectangle rect) {

    //Create new stack for the points
    Stack<Point> points = new Stack<>();
    
    //if the tree is empty, return an empty stack
    if (head == null) {
        return points;
    }
    
    //create new stack for nodes that contain points
    Stack<TreeNode> PointNodes = new Stack<>();
    
    //add the head node to the stack
    PointNodes.push(head);
    
    //iterat through all nodes in the stack
    while (!PointNodes.isEmpty()) {
        
        //get the next node in the stack
        TreeNode node = PointNodes.pop();
        
        //if the current node contains a point within the rectangle, add it to the stack
        if (rect.contains(node.data)) points.push(node.data);
        
        //if the rectangle intersects with the left subtree of the current node, add the left node to the stack
        if (node.l != null && rect.intersects(node.l.rect)) {
            PointNodes.push(node.l);
        }
        
        //if the rectangle intersects with the right subtree of the current node, add the right node to the stack
        if (node.r != null && rect.intersects(node.r.rect)) {
            PointNodes.push(node.r);
        }
    }
    
    //return the stack of points found
    return points;
}

public static void main(String[] args) {
    try {
        Scanner scan = new Scanner(new File("input.txt"));
        Scanner scanCount = new Scanner(new File("input.txt"));

            
        TwoDTree tdt = new TwoDTree();
        int size = Integer.parseInt(scan.nextLine());
        int count = -1;

        while(scanCount.hasNextLine()){
            count++;
            scanCount.nextLine();
        }
        String[][] coords = new String[count][2];
        
        for (int i = 0; i < coords.length; i++) {
            coords[i] = scan.nextLine().split(" ");
        }

        
        for (int i = 0; i < coords.length; i++) {
            for (int j = 0; j < coords[i].length; j++) {
                if(Integer.parseInt(coords[i][j]) > 100 || Integer.parseInt(coords[i][j]) < 0 || size!=count){
                    System.out.println("Invalid inputs...");
                    System.exit(0);
                }
            }
        }

        for (int i = 0; i < coords.length; i++) {
            tdt.insert(new Point(Integer.parseInt(coords[i][0]), Integer.parseInt(coords[i][1])));
        }

        scan.close();
        scanCount.close();

        Scanner iscan = new Scanner(System.in);
        while(true){
            System.out.println("\nPlease select one of these options: ");
            System.out.println("\n1.\tCompute the size of the tree\n2.\tInsert a new point\n3.\tSearch if a given point exists in the tree\n4.\tProvide a query rectangle\n5.\tProvide a query point\n\n0.\tEnd Program");
            System.out.print("Option : ");
            int choice = iscan.nextInt(); 
            iscan.nextLine();
            while (choice < 0 || choice > 5) { 
                System.out.println("Please type a number between 1 - 5 or 0 to end the program!");
                System.out.print("Option : ");
                choice = iscan.nextInt();
                iscan.nextLine();
            }

            switch (choice){
                case 1:
                    System.out.println("\nSize: "+tdt.size()+"\n");
                    System.out.print("Press enter to continue: ");
                    iscan.nextLine();
                    break;
                case 2:
                    System.out.println("Type the two coordinates of the new point you want to insert.");
                    System.out.print("x: ");
                    int x = iscan.nextInt();
                    System.out.print("y: ");
                    int y = iscan.nextInt();
                    tdt.insert(new Point(x, y));
                    iscan.nextLine();

                    System.out.println("\nPoint inserted!");
                    System.out.print("Press enter to continue: ");
                    iscan.nextLine();

                    break;
                
                case 3:

                    System.out.println("Type the two coordinates of the point you want to search.");
                    System.out.print("x: ");
                    int xs = iscan.nextInt();
                    System.out.print("y: ");
                    int ys = iscan.nextInt();
                    if(tdt.search(new Point(xs, ys))) {
                        System.out.println("Point Found!");
                    }else{
                        System.out.println("Point Not Found!");
                    }
                    iscan.nextLine();
                    System.out.print("Press enter to continue: ");
                    iscan.nextLine();

                    break;

                case 4:
                    System.out.println("Please type the coordinates of the rectangle: ");
                    System.out.print("xmin: ");
                    int xmin = iscan.nextInt();
                    System.out.print("xmax: ");
                    int xmax = iscan.nextInt();
                    System.out.print("ymin: ");
                    int ymin = iscan.nextInt();
                    System.out.print("ymax: ");
                    int ymax = iscan.nextInt();
                    iscan.nextLine();

                    Rectangle r = new Rectangle(xmin, ymin, xmax, ymax);

                    System.out.print("The points are: ");
                    System.out.println(tdt.rangeSearch(r));
                    System.out.print("Press enter to continue: ");
                    iscan.nextLine();

                    break;
                
                case 5:
                    System.out.println("Type the two coordinates of the query point. ");
                    System.out.print("x: ");
                    int xn = iscan.nextInt();
                    System.out.print("y: ");
                    int yn = iscan.nextInt();
                    Point p = new Point(xn, yn);
                    System.out.println("\nThe nearest neighbor is: "+tdt.nearestNeighbor(p));

                    System.out.println("The distance between those two points is: "+tdt.nearestNeighbor(p).distanceTo(p));
                    iscan.nextLine();
                    System.out.print("\nPress enter to continue: ");
                    iscan.nextLine();


                    break;

                case 0:
                    iscan.close();
                    System.exit(0);
                default:
                    break;

            }
        }




    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
}
}