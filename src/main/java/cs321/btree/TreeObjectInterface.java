package cs321.btree;
import java.util.ArrayList;

public interface TreeObjectInterface<E> {
    
    public TreeObject<E> TreeObject();
    public TreeObject<E> TreeObject(E key, int freq);
    public TreeObject<E> TreeObject(ArrayList<E> keyList, ArrayList<Integer> freqList);

    public E getKey(int index);
    public int getFreq(int index);
    public TreeObject<E> getChild(int index);
    public TreeObject<E> getParent();
    public boolean isLeaf();

    public ArrayList<E> getAllKeys();
    public ArrayList<Integer> getAllFreqs();
    public ArrayList<TreeObject<E>> getAllChildren();

    public void setKey(int index, E key);
    public void setFreq(int index, int freq);
    public void setChild(int index, TreeObject<E> child);
    public void setParent(TreeObject<E> parent);
    public void setIsLeaf(boolean isLeaf);

    public void setAllKeys(ArrayList<E> keyList);
    public void setAllFreqs(ArrayList<Integer> freqList);
    public void setAllChildren(ArrayList<TreeObject<E>> childList);

    public void incrementFreq(int index);
    public boolean equals(TreeObject<E> comparable);
    public String toString();
}
