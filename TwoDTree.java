import java.util.Stack;

public class TwoDTree implements TwoDTreeInterface{

    private TreeNode head;

    // Implementing TreeNode
    private static class TreeNode{
        
        private Point data;
        private TreeNode l,r;
        private Rectangle rect;

        public TreeNode(Point data, int[] c){
            this.data = data;
            rect = new Rectangle(c[0], c[1], c[2], c[3]);
        }
    }

    // Constructor
    public TwoDTree(){
        this.head = null;
    }

    // Check if the tree is empty
    public boolean isEmpty(){
        return head == null;
    }

    // Get the size of the tree
    public int size(){
        return size(head);
    }

    // Helper function to calculate the size of the tree
    private int size(TreeNode node) {
        if (node == null) {
          return 0;
        }
        return 1 + size(node.l) + size(node.r);
      }


      private int compare(Point p, TreeNode node, boolean level) {
        if (level) {
            return p.x() - node.data.x();
        }
        else return p.y() - node.data.y();
    }


      public void insert(Point p) {
        if (p == null) {
            throw new java.lang.NullPointerException("called insert() with a null Point");
        }
        head = insert(head, p, true, new int[] {0, 0, 100, 100});
    }
    
    private TreeNode insert(TreeNode n, Point p, boolean evenLevel, int[] coords) {
        if (n == null) {
            return new TreeNode(p, coords);
        }
        
        double cmp = compare(p, n, evenLevel);
        
        if (cmp < 0 && evenLevel) {
            coords[2] = n.data.x(); // lessen x_max
            n.l = insert(n.l, p, !evenLevel, coords);
        }
        
        // Handle Nodes which should be inserted to the bottom
        else if (cmp < 0 && !evenLevel) {
            coords[3] = n.data.y(); // lessen y_max
            n.l = insert(n.l, p, !evenLevel, coords);
        }
        
        // Handle Nodes which should be inserted to the right
        else if (cmp > 0 && evenLevel) {
            coords[0] = n.data.x(); // increase x_min
            n.r = insert(n.r, p, !evenLevel, coords);
        }
        
        // Handle Nodes which should be inserted to the top
        else if (cmp > 0 && !evenLevel) {
            coords[1] = n.data.y(); // increase y_min
            n.r = insert(n.r, p, !evenLevel, coords);
        }
        
        else if (!n.data.equals(p))
            n.r = insert(n.r, p, !evenLevel, coords);
        
        return n;
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
        if (p == null) {
            throw new java.lang.NullPointerException("called contains() with a null Point2D");
        }
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
        if (rect == null) throw new java.lang.NullPointerException(
                "called range() with a null RectHV");
        
        Stack<Point> points = new Stack<>();
        
        // Handle KdTree without a root node yet
        if (head == null) return points;
        
        Stack<TreeNode> nodes = new Stack<>();
        nodes.push(head);
        while (!nodes.isEmpty()) {
            
            // Examine the next Node
            TreeNode tmp = nodes.pop();
            
            // Add contained points to our points stack
            if (rect.contains(tmp.data)) points.push(tmp.data);
            
            if (tmp.l != null && rect.intersects(tmp.l.rect)) {
                nodes.push(tmp.l);
            }
            if (tmp.r != null && rect.intersects(tmp.r.rect)) {
                nodes.push(tmp.r);
            }
        }
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