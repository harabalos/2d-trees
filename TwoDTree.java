public class TwoDTree{

    private TreeNode head;

    public static class TreeNode{
        
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

}