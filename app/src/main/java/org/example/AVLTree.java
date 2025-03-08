package org.example;

// Node class representing each node in the AVL tree
class Node {
    int key, height;
    Node left, right;

    Node(int key) {
        this.key = key;
        this.height = 1; // New nodes have height 1
    }
}

// AVLTree class implementing all required methods
class AVLTree {

    // Insert a key into the AVL tree
    public Node insert(Node root, int key) {
        if (root == null) {
            return new Node(key);
        }
        if (key < root.key) {
            root.left = insert(root.left, key);
        } else if (key > root.key) {
            root.right = insert(root.right, key);
        } else {
            return root; // Duplicate keys are not allowed
        }

        // Update height
        root.height = 1 + Math.max(height(root.left), height(root.right));

        // Balance the tree
        return balance(root, key);
    }

    // Delete a key from the AVL tree
    public Node delete(Node root, int key) {
        if (root == null) {
            return root;
        }
        if (key < root.key) {
            root.left = delete(root.left, key);
        } else if (key > root.key) {
            root.right = delete(root.right, key);
        } else {
            // Node with one or no children
            if (root.left == null || root.right == null) {
                root = (root.left != null) ? root.left : root.right;
            } else {
                Node temp = findMin(root.right);
                root.key = temp.key;
                root.right = delete(root.right, temp.key);
            }
        }
        if (root == null)
            return root;

        // Update height
        root.height = 1 + Math.max(height(root.left), height(root.right));

        // Balance the tree
        return balanceAfterDelete(root);
    }

    // Serialize the AVL tree into a string
    public String serialize(Node root) {
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString();
    }

    private void serializeHelper(Node root, StringBuilder sb) {
        if (root == null) {
            sb.append("null,");
            return;
        }
        sb.append(root.key).append(",");
        serializeHelper(root.left, sb);
        serializeHelper(root.right, sb);
    }

    // Deserialize a string into an AVL tree
    public Node deserialize(String data) {
        String[] values = data.split(",");
        int[] index = { 0 }; // Keep track of the current index in the array
        return deserializeHelper(values, index);
    }

    private Node deserializeHelper(String[] values, int[] index) {
        if (index[0] >= values.length || values[index[0]].equals("null")) {
            index[0]++;
            return null;
        }
        Node root = new Node(Integer.parseInt(values[index[0]++]));
        root.left = deserializeHelper(values, index);
        root.right = deserializeHelper(values, index);
        return root;
    }

    // visualisation
    public void printTree(Node root, String prefix, boolean isLeft) {
        if (root != null) {
            System.out.println(prefix + (isLeft ? "├── " : "└── ") + root.key);
            printTree(root.left, prefix + (isLeft ? "│   " : "    "), true);
            printTree(root.right, prefix + (isLeft ? "│   " : "    "), false);
        }
    }

    // Utility methods
    private int height(Node node) {
        return (node == null) ? 0 : node.height;
    }

    private int getBalanceFactor(Node node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private Node balance(Node root, int key) {
        int balance = getBalanceFactor(root);

        // Left Left Case
        if (balance > 1 && key < root.left.key) {
            return rightRotate(root);
        }

        // Right Right Case
        if (balance < -1 && key > root.right.key) {
            return leftRotate(root);
        }

        // Left Right Case
        if (balance > 1 && key > root.left.key) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Right Left Case
        if (balance < -1 && key < root.right.key) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    private Node balanceAfterDelete(Node root) {
        int balance = getBalanceFactor(root);

        // Left Heavy
        if (balance > 1 && getBalanceFactor(root.left) >= 0) {
            return rightRotate(root);
        }

        if (balance > 1 && getBalanceFactor(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Right Heavy
        if (balance < -1 && getBalanceFactor(root.right) <= 0) {
            return leftRotate(root);
        }

        if (balance < -1 && getBalanceFactor(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }
}
