public class TwoDTree{

    private TreeNode head;

    private static class TreeNode{
        
        private Point data;
        private TreeNode l,r;

        public TreeNode(Point data){
            this.data = data;
            this.l = null;
            this.r = null;
        }
    }

    public TwoDTree(){
        this.head = null;
    }

    public boolean isEmpty(){
        return head == null;
    }

    public int size(){
        return size(head);
    }

    private int size(TreeNode node) {
        if (node == null) {
          return 0;
        }
        return 1 + size(node.l) + size(node.r);
      }


      public void insert(Point p) {
        if (head == null) {
            head = new TreeNode(p);
            return;
        }
        
        TreeNode current = head;
        boolean isXLevel = true;
        while (true) {
            if (current.data.x() == p.x() && current.data.y() == p.y()) {
                System.out.println("Error: Point already exists in the tree.");
                return;
            }
    
            if (isXLevel) {
                if (p.x() < current.data.x()) {
                    if (current.l == null) {
                        current.l = new TreeNode(p);
                        break;
                    }
                    current = current.l;
                } else {
                    if (current.r == null) {
                        current.r = new TreeNode(p);
                        break;
                    }
                    current = current.r;
                }
            } else {
                if (p.y() < current.data.y()) {
                    if (current.l == null) {
                        current.l = new TreeNode(p);
                        break;
                    }
                    current = current.l;
                } else {
                    if (current.r == null) {
                        current.r = new TreeNode(p);
                        break;
                    }
                    current = current.r;
                }
            }
            isXLevel = !isXLevel;
        }
    }


    public boolean search(Point p) {
        TreeNode current = head;
    
        while (current != null) {
            int compareX = Integer.compare(p.x(), current.data.x());
            int compareY = Integer.compare(p.y(), current.data.y());
    
            if (compareX == 0 && compareY == 0) {
                return true;
            } else if (compareX <= 0 && compareY <= 0) {
                current = current.l;
            } else {
                current = current.r;
            }
        }
    
        return false;
    }

}