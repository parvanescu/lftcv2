package lrParser.parsing;

class WorkingStackObject {
    public String token;
    public Integer stateNo;

    public WorkingStackObject(Integer stateNo, String token) {
        this.stateNo = stateNo;
        this.token = token;
    }

    @Override
    public String toString() {
        return token + " " + stateNo;
    }
}
