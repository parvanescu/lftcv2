package lrParser.output;

class TreeNode {
    public Integer index;
    public String info;
    public Integer parentIndex;
    public Integer leftSiblingIndex;

    public TreeNode(Integer index, String info, Integer parentIndex, Integer leftSiblingIndex) {
        this.index = index;
        this.info = info;
        this.parentIndex = parentIndex;
        this.leftSiblingIndex = leftSiblingIndex;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getParentIndex() {
        return parentIndex;
    }

    public void setParentIndex(Integer parentIndex) {
        this.parentIndex = parentIndex;
    }

    public Integer getLeftSiblingIndex() {
        return leftSiblingIndex;
    }

    public void setLeftSiblingIndex(Integer leftSiblingIndex) {
        this.leftSiblingIndex = leftSiblingIndex;
    }
}
