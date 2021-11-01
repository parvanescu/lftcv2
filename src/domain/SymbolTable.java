package domain;

import java.util.ArrayList;
import java.util.List;

public class SymbolTable<V> {
    private final List<Node<V>> map;
    private int capacity;
    private int size = 0;


    public SymbolTable(int capacity){
        this.map = new ArrayList<>();
        for(int i=0;i<capacity;i++){
            this.map.add(null);
        }
        this.capacity = capacity;
    }

    private Position put(V value){
        Node<V> newNode = new Node<>(value,null);
        int bucket = hashFunction(value);

        Node<V> firstNode = this.map.get(bucket);
        if(firstNode == null){
            this.map.set(bucket,newNode);
            size++;
            return new Position(bucket,0);
        }else{
            int linkedListPosition = 0;
            while(firstNode.next!=null){
                firstNode = firstNode.next;
                linkedListPosition++;
            }
            firstNode.setNext(newNode);
            size++;
            return new Position(bucket,linkedListPosition);
        }
    }

    public Position pos(V value){
        int bucketIndex = hashFunction(value);
        Position initialPosition = new Position(bucketIndex,-1);
        Node<V> nodeEl = this.map.get(bucketIndex);
        int linkedListIndex = 0;
        while(nodeEl!=null){
            if(nodeEl.value.equals(value)){
                initialPosition.linkedListPos = linkedListIndex;
                return initialPosition;
            }
            linkedListIndex++;
            nodeEl = nodeEl.next;
        }
        if(initialPosition.linkedListPos == -1)
            return this.put(value);

        return null;
    }

    private int hashFunction(V value){
        byte[] bytes = value.toString().getBytes();
        int sum = 0;
        for (byte aByte : bytes) {
            sum += aByte;
        }
        return sum % capacity;
    }

    public int getSize() {
        return size;
    }

    public int getCapacity(){
        return capacity;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("|  IDX  ");
        for(int j = 0;j<map.size()/2;j++){
            builder.append(centerString(10,String.valueOf(j)));
        }
        builder.append("|\n");
        builder.append("|-------|").append("----------|".repeat(map.size() / 2));
        builder.append("\n");
        for (int i = 0;i<map.size();i++){
            builder.append(centerString(7,String.valueOf(i)));
            Node<V> node = map.get(i);
            while(node != null){
                builder.append(centerString(10,node.value.toString()));
                node = node.next;
            }
            builder.append("|\n");
            builder.append("|-------|").append("----------|".repeat(map.size() / 2));
            builder.append("\n");
        }
        return builder.toString();
    }

    public static String centerString (int width, String s) {
        return String.format("|%-" + width  + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }
}
