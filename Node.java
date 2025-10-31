
//The Node class represents one unit (or box) in your linked list.
public class Node<T> {
    T data;  //actual information (for example, one Patient or one Doctor)
    Node<T> next; //pointer or reference to the next node in the list.

    public Node(T data) {
        this.data = data;
        this.next = null;
    }
}
