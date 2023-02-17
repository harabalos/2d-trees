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

        //insert to the right if odd level and y-value is greater than node y-value
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
    
    


    public boolean search(Point p) {
        //start search from the root node
        TreeNode current = head;
    
        //iterate the tree until the current node is not null
        while (current != null) {
            //compare the x coordinate of the target point with the current node's point
            int compareX = Integer.compare(p.x(), current.data.x());
            //compare the y-coordinate of the target point with the current node's point
            int compareY = Integer.compare(p.y(), current.data.y());
    
            //if both x and y coordinates are equal to the current node's point, return true (point is found)
            if (compareX == 0 && compareY == 0) {
                return true;
            } 
            //if the target points x and y coordinates are both smaller than the current node's point, 
            //search in the left subtree
            else if (compareX <= 0 && compareY <= 0) {
                current = current.l;
            } 
            //otherwise, search in the right subtree
            else {
                current = current.r;
            }
        }
    
        //if the tagret point is not found, return false
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
        
        //if th distance to the current node is less than the distance to the currentt best point, update the best point
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
        
        //return the final best point
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
        TwoDTree tdt = new TwoDTree();
        Point p1 = new Point(70, 20);
        Point p2 = new Point(50, 40);
        Point p3 = new Point(20, 30);
        Point p4 = new Point(40, 70);


        tdt.insert(p1);
        tdt.insert(p2);
        tdt.insert(p3);
        tdt.insert(p4);
        

        

    }

}