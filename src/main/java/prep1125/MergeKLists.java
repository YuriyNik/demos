package prep1125;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class MergeKLists {

    static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    public static Node merge(List<Node> lists) {
        // Min-heap to always get the smallest current element.
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.value));

        for (Node head : lists) {
            if (head != null) pq.add(head);
        }

        Node dummy = new Node(0);
        Node tail = dummy;

        while (!pq.isEmpty()) {
            Node smallest = pq.poll();
            tail.next = smallest;
            tail = tail.next;

            if (smallest.next != null) {
                pq.add(smallest.next);
            }
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        Node a = new Node(1); a.next = new Node(4); a.next.next = new Node(5);
        Node b = new Node(1); b.next = new Node(3); b.next.next = new Node(4);
        Node c = new Node(2); c.next = new Node(6);
        Node result = merge(List.of(a, b, c));
        System.out.print(result.value);
        while (result.next!=null) {
            System.out.print("->" + result.next.value);
            result=result.next;
        }
        System.out.print(";");
    }
}
