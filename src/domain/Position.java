package domain;

public class Position {
    public int hashPos;
    public int linkedListPos;

    public Position(int hashPos, int linkedListPos) {
        this.hashPos = hashPos;
        this.linkedListPos = linkedListPos;
    }

    @Override
    public String toString() {
        return "("+hashPos+","+linkedListPos+")";
    }
}
