public class RedBlackTree {
    private final RedBlackTreeNode nil = new RedBlackTreeNode(null); // Sentinel node for leaves
    private RedBlackTreeNode root;

    public RedBlackTree() {
        nil.color = false; // nil nodes are black
        root = nil; // Initialize root to nil
    }

    public void insert(Product product) {
        RedBlackTreeNode newNode = new RedBlackTreeNode(product);
        newNode.left = nil;
        newNode.right = nil;
        RedBlackTreeNode parent = null;
        RedBlackTreeNode current = root;

        while (current != nil) {
            parent = current;
            if (product.productId.compareTo(current.product.productId) < 0) {
                current = current.left;
            } else if (product.productId.compareTo(current.product.productId) > 0) {
                current = current.right;
            } else {
                System.out.println("Error: Product with ID " + product.productId + " already exists.");
                return;
            }
        }

        newNode.parent = parent;
        if (parent == null) {
            root = newNode;
        } else if (product.productId.compareTo(parent.product.productId) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }

        newNode.color = true; // new nodes are red by default
        insertFix(newNode);
    }

    private void insertFix(RedBlackTreeNode node) {
        while (node != root && node.parent.color) {
            if (node.parent == node.parent.parent.left) {
                RedBlackTreeNode uncle = node.parent.parent.right;
                if (uncle.color) {
                    node.parent.color = false;
                    uncle.color = false;
                    node.parent.parent.color = true;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.right) {
                        node = node.parent;
                        rotateLeft(node);
                    }
                    node.parent.color = false;
                    node.parent.parent.color = true;
                    rotateRight(node.parent.parent);
                }
            } else {
                RedBlackTreeNode uncle = node.parent.parent.left;
                if (uncle.color) {
                    node.parent.color = false;
                    uncle.color = false;
                    node.parent.parent.color = true;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rotateRight(node);
                    }
                    node.parent.color = false;
                    node.parent.parent.color = true;
                    rotateLeft(node.parent.parent);
                }
            }
        }
        root.color = false;
    }

    private void rotateLeft(RedBlackTreeNode node) {
        RedBlackTreeNode temp = node.right;
        node.right = temp.left;
        if (temp.left != nil) temp.left.parent = node;
        temp.parent = node.parent;
        if (node.parent == null) {
            root = temp;
        } else if (node == node.parent.left) {
            node.parent.left = temp;
        } else {
            node.parent.right = temp;
        }
        temp.left = node;
        node.parent = temp;
    }

    private void rotateRight(RedBlackTreeNode node) {
        RedBlackTreeNode temp = node.left;
        node.left = temp.right;
        if (temp.right != nil) temp.right.parent = node;
        temp.parent = node.parent;
        if (node.parent == null) {
            root = temp;
        } else if (node == node.parent.right) {
            node.parent.right = temp;
        } else {
            node.parent.left = temp;
        }
        temp.right = node;
        node.parent = temp;
    }

    public Product search(String productId) {
        RedBlackTreeNode current = root;
        while (current != nil && !productId.equals(current.product.productId)) {
            if (productId.compareTo(current.product.productId) < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return current != nil ? current.product : null;
    }

    // Print tree method to display all nodes and their colors
    public void printTree() {
        printTreeHelper(root, "", true);
    }

    private void printTreeHelper(RedBlackTreeNode node, String indent, boolean isLast) {
        if (node != nil) {
            System.out.print(indent);
            if (isLast) {
                System.out.print("R----");
                indent += "   ";
            } else {
                System.out.print("L----");
                indent += "|  ";
            }

            // Display product details and color
            System.out.println(node.product + " (" + (node.color ? "Red" : "Black") + ")");
            printTreeHelper(node.left, indent, false);
            printTreeHelper(node.right, indent, true);
        }
    }
}
