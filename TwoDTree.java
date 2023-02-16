import java.util.ArrayList;
import java.util.List;

public class TwoDTree{

    private TreeNode head;

    // Implementing TreeNode
    private static class TreeNode{
        
        private Point data;
        private TreeNode l,r;

        public TreeNode(Point data){
            this.data = data;
            this.l = null;
            this.r = null;
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


      public void insert(Point p) {
        // Check if the tree is empty and add the point as the root node if it is
        if (head == null) {
            head = new TreeNode(p);
            return;
        }
        
        // Start searching for the correct position to insert the new point
        TreeNode current = head;
        // Is used to alternate between checkin the x-coordinate and y-coordinate of the points
        boolean isXLevel = true;
    
        // Keep looping until a null child node is found, which indicates the correct place to insert the new point
        while (true) {
            // Check if the point already exists in the tree, and return an error message if it does
            if (current.data.x() == p.x() && current.data.y() == p.y()) {
                System.out.println("Error: Point already exists in the tree.");
                return;
            }
    
            // Compare the x-coordinate of the point to be inserted with the x-coordinate of the current node
            if (isXLevel) {
                // If th point has a smaller x-coordinate, move to the left child node
                if (p.x() < current.data.x()) {
                    if (current.l == null) {
                        current.l = new TreeNode(p);
                        break;
                    }
                    current = current.l;
                } 
                // Otherwise, move to the right child node
                else {
                    if (current.r == null) {
                        current.r = new TreeNode(p);
                        break;
                    }
                    current = current.r;
                }
            } 
            // Compare the y-coordinate of the point to be insetred with the y-coordinate of the current node
            else {
                // If the point has a smaller y-coordinate, move to the left child node
                if (p.y() < current.data.y()) {
                    if (current.l == null) {
                        current.l = new TreeNode(p);
                        break;
                    }
                    current = current.l;
                } 
                // Otherwise, move to the right child node
                else {
                    if (current.r == null) {
                        current.r = new TreeNode(p);
                        break;
                    }
                    current = current.r;
                }
            }
            // Alternate betwween checking the x-coordinate and y-coordinate of the points
            isXLevel = !isXLevel;
        }
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
        if (head == null) {
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

    public List<Point> rangeSearch(Rectangle rect) {
        List<Point> pointsInRange = new ArrayList<>();
        rangeSearch(head, rect, pointsInRange);
        return pointsInRange;
    }
    
    private void rangeSearch(TreeNode node, Rectangle rect, List<Point> pointsInRange) {
        if (node == null) {
            return;
        }
    
        if (rect.contains(node.data)) {
            pointsInRange.add(node.data);
        }
    
    //     if (node.l != null && rect.intersects(node.l.data.rect())) {
    //         rangeSearch(node.l, rect, pointsInRange);
    //     }
    
    //     if (node.r != null && rect.intersects(node.l.data.rect())) {
    //         rangeSearch(node.r, rect, pointsInRange);
    //     }
    // }
    
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