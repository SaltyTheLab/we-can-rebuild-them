package org.example;

public class Main {
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        Node root = null;

        root = tree.insert(root, 3);
        root = tree.insert(root, 4);
        root = tree.insert(root, 5);
        root = tree.insert(root, 6);
        root = tree.insert(root, 7);
        root = tree.insert(root, 8);
        root = tree.insert(root, 9);
        root = tree.insert(root, 10);
        String serialized = tree.serialize(root);
        System.out.println(serialized);

        System.out.println("AVL Tree:");
        tree.printTree(root, "", false);

        tree.delete(root, 6);

        System.out.println("AVL Tree after deletion:");
        tree.printTree(root, "", false);
        // Serialize the AVL tree
        String serializedTree = tree.serialize(root);
        System.out.println("Serialized Tree: " + serializedTree);

        
        // Deserialize the tree back into a new AVL tree
        Node deserializedRoot = tree.deserialize(serializedTree);
        System.out.println("\nDeserialized Tree Structure:");
        tree.printTree(deserializedRoot, "", false);

        // Verify by serializing the deserialized tree again
        String reSerializedTree = tree.serialize(deserializedRoot);
        System.out.println("\nRe-Serialized Tree: " + reSerializedTree);

        // Check if the serialized output matches
        if (serializedTree.equals(reSerializedTree)) {
            System.out.println("\nSuccess! The AVL tree was deserialized correctly.");
        } else {
            System.out.println("\nError: The deserialization did not produce the correct tree.");
        }

    }
}