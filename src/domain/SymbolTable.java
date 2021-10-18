package domain;

import java.util.ArrayList;
import java.util.List;

public class SymbolTable<V> {
    private final List<Node<V>> map;
    private static final int capacity = 10;
    private int size = 0;


    public SymbolTable(){
        this(capacity);
    }

    public SymbolTable(int capacity){
        this.map = new ArrayList<>();
        for(int i=0;i<capacity;i++){
            this.map.add(null);
        }
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

    @Override
    public String toString() {
        return "SymbolTable{" +
                "map=" + map +
                ", size=" + size +
                '}';
    }
}
