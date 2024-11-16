public class RedBlackTreeNode {
    Product product;
    RedBlackTreeNode left, right, parent;
    boolean color; // true for red, false for black

    public RedBlackTreeNode(Product product) {
        this.product = product;
        this.color = true; // new nodes are red by default
    }
}

