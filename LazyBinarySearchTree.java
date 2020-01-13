public class LazyBinarySearchTree {
    
    // Constructor
    public LazyBinarySearchTree(){
        this.root = null;
        this.deletedSize = 0;
        this.size = 0;
    }
  
    // insert in to tree, throws IllegalArgumentException if insert number out of bound
    public boolean insert(int key) throws IllegalArgumentException{
        if(key < lowerRange){
            throw new IllegalArgumentException("Error in insert: inserted key range should be greater than 1. IllegalArgumentException raised");
        }
        if(key > upperRange){
            throw new IllegalArgumentException("Error in insert: inserted key range should be smaller than 99. IllegalArgumentException raised");
        }
        // if root is empty, create a new root to the tree
        if(this.root == null){
            this.root = new TreeNode(key);
            return true;
        }
        // elsee, call the insert function
        else{
            return insert(this.root, key);
        }

    }
    
    // insert the key into the tree
    private boolean insert(TreeNode tempNode,int key){
       boolean flag = false; //flag
       if(key < tempNode.getKey()){
           if(tempNode.getLeft() != null){
               flag = insert(tempNode.getLeft(),key);
           }
           else{
               tempNode.setLeft(new TreeNode(key));
               flag = true;
           }
       }
       else if(key > tempNode.getKey()){
           if(tempNode.getRight() != null){
               flag = insert(tempNode.getRight(), key);
           }
           else{
               tempNode.setRight(new TreeNode(key));
               flag = true;
           }
       }
       else{
           if(tempNode.isDeleted()){
               tempNode.setDeleted(false);
               flag = true;
           }
           else{
               flag = false;
           }
       }
       tempNode.setHeight(1+Math.max(height(tempNode.getLeft()), (height(tempNode.getRight()))));
       return flag;
    }
    
    
    private boolean delete(TreeNode tempNode, int key){
        boolean flag = true; //flag to mark the node, true if it's "deleted", false if it's not "deleted"
        
        if(key < tempNode.getKey()){
            if(tempNode.getLeft() != null){
                flag = delete(tempNode.getLeft(), key);
            }
            else{
                flag = false;
            }
        }
        else if(key > tempNode.getKey()){
            if(tempNode.getRight() != null){
                flag = delete(tempNode.getRight(), key);
            }
            else{
                flag = false;
            }
        }
        else{
            if(tempNode.isDeleted()){
                flag = false;
            }
            else{
                tempNode.setDeleted(true);
                flag = true;
            }
        }
        return flag;
    }
    
    /**
     * Internal method to remove from a subtree (lazily delete)
     * @Param key the item to remove
     */
    public boolean delete(int key) throws IllegalArgumentException{
       if(key < lowerRange){
            throw new IllegalArgumentException("Error in insert: inserted key range should be greater than 1. IllegalArgumentException raised");
       }
       if(key > upperRange){
            throw new IllegalArgumentException("Error in insert: inserted key range should be smaller than 99. IllegalArgumentException raised");
       }
       if(this.root != null){
           return delete(this.root, key);
       }
       else{
           return false;
       }
    }
    
    
    public int findMin(){
        if(this.root != null){
            int min = this.root.getKey();
            
            if(this.root.getLeft() != null){
                return findMin(this.root.getLeft(),min);
            }
            else{
                return min;
            }
        }
        return -1;
    }
    /**
     * Internal method to find the smallest item in a subtree
     */
    private int findMin(TreeNode currentNode, int min){
        if(currentNode.getLeft() != null){
            if(!currentNode.isDeleted() && !currentNode.getLeft().isDeleted()){
                min = Math.min(currentNode.getLeft().getKey(), min);
            }
            return findMin(currentNode.getLeft(), min);
        }
        else{
            return min;
        }
    }
    
    public int findMax(){
        if(this.root != null){
            int max = this.root.getKey();
            if(this.root.getRight() != null){
                return findMax(this.root.getRight(), max);
            }
            else{
                 return max;
            }
        }
        return -1;
    }
    
    private int findMax(TreeNode currentNode, int max){
        if(currentNode.getRight() != null){
            if(!currentNode.isDeleted() && !currentNode.getRight().isDeleted()){
                max = Math.max(currentNode.getRight().getKey(), max);
            }
            return findMax(currentNode.getRight(),max);
        }
        else{
            return max;
        }
    }
    
    public boolean contains(int key) throws IllegalArgumentException{
        if(key < lowerRange){
            throw new IllegalArgumentException("Error in insert: inserted key range should be greater than 1. IllegalArgumentException raised");
       }
       if(key > upperRange){
            throw new IllegalArgumentException("Error in insert: inserted key range should be smaller than 99. IllegalArgumentException raised");
       }
       if(this.root != null){
           return contains(this.root, key);
       }
       else{
           return false;
       }
    }
    
    private boolean contains(TreeNode tempNode, int key){
        boolean flag = true;
        
        if(key < tempNode.getKey()){
            if(tempNode.getLeft() != null){
                flag = contains(tempNode.getLeft(), key);
            }
            else{
                flag = false;
            }
        }
        else if(key > tempNode.getKey()){
            if(tempNode.getRight() != null){
                flag = contains(tempNode.getRight(), key);
            }
            else{
                tempNode.setRight(new TreeNode(key));
                flag = false;
            }
        }
        else{
            if(tempNode.isDeleted()){
                flag = false;
            }
            else{
                flag = true;
            }
        }
        return flag;
    }
    
    public int size(){
        if(this.root != null){
            return size(this.root, 0);
        }
        return 0;
    }
    
    private int size(TreeNode node, int size){
        if(node != null){
            size+=1;
            size = size(node.getLeft(), size);
            size = size(node.getRight(), size);
        }
        return size;
    }
    
    // return preorder tree
    public StringBuffer preorderString(TreeNode node, StringBuffer buff){
        // if there exist a root node 
        if(node != null){
            // if the node mark as deleted, then add * before the printed number, e.g, if we delete 6, then print *6 in the output file
            if(node.isDeleted()){
                buff.append("*" + node.getKey() + " ");
            }
            else{
                buff.append(node.getKey() + " ");
            }
            preorderString(node.getLeft(), buff);
            preorderString(node.getRight(), buff);
        }
        return buff;
    }
    
    // return the preorder string
    public String toString(){
        StringBuffer buffer = new StringBuffer();
        buffer = preorderString(this.root, buffer);
        return buffer.toString();
    }
    
    public int height(){
        return this.root.getHeight();
    }
    
    /**
     * Internal method to compute height of a subtree
     * @param node the node that roots the subtree
     */
    private int height(TreeNode node){
        if(node == null){
            return 0;
        }
        else{
            return node.getHeight();
        }
    }
    
    //Basic node stored in unbalanced binary search trees
    private static class TreeNode{ 
        int key; // The data in the node
        private TreeNode left; // Left child
        private TreeNode right; // Right child
        boolean deleted;
        private int height;
        // Constructors
        public TreeNode(int key){
            this.key = key; 
            this.left = null;
            this.right = null;
            this.deleted = false;
            this.height = 1; // initialize height to 1
        }
        
        
        public int getKey(){
            return key;
       }
        
        // set the key(element) of the tree
        public void setKey(int key){
            this.key = key;
        }
    
        public TreeNode getLeft(){
            return left;
       }
        
        // set the left child node of the tree
        public void setLeft(TreeNode theLeft){
            this.left = theLeft;
        }
         
        public TreeNode getRight(){
            return right;
        }
        
        // set the right child node of the tree 
        public void setRight(TreeNode theRight){
            this.right = theRight;
        }
        
        public boolean isDeleted(){
            return deleted;
        }
        
        // set the deleted node
        public void setDeleted(boolean deleted){
            this.deleted = deleted;
        }
        
        public int getHeight(){
            return height;
        }
        
        // set the height of the tree
        public void setHeight(int height){
            this.height = height;
        }
        
        @Override
        public String toString(){
            return String.valueOf(this.key);
        }
    }
   
    // size of all nodes
    private int size;
    // size of deleted node
    private int deletedSize;
    /** The tree root. */
    private TreeNode root;
    // range[1,99]
    private static final int lowerRange = 1;
    private static final int upperRange = 99;
   
}
