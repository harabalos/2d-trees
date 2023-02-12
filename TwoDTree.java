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
    

}