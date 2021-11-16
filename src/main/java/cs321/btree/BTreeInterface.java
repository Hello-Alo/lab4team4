package cs321.btree;

public interface BTreeInterface<E> {
    
    public BTree<E> BTree();
    public BTree<E> BTree(TreeObject<E> root);

    public TreeObject<E> getRoot();
    public int getHeight();

    public void setRoot(TreeObject<E> root);
    public void setHeight(int height);
}
