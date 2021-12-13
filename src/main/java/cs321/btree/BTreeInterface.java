package cs321.btree;

import java.util.ArrayList;

public interface BTreeInterface<E extends Comparable<E>> {
    
    //public BTree<E> BTree();
    //public BTree<E> BTree(TreeObject<E> root);

    public TreeObject<E> getRoot();
    public int getHeight();
    public int getDegree();
    public ArrayList<Integer> contains(E element);

    public void setRoot(TreeObject<E> root);
    public void setDegree(int deg);

    public void insert(E element);
}
