package lrParser.output;

import grammar.ProductionRule;

import java.util.ArrayList;
import java.util.List;

public class ParserOutput {
    private final List<TreeNode> treeDs;

    public ParserOutput() {
        this.treeDs = new ArrayList<>();
    }

    public void generateTree(List<Integer> productionOrder, List<ProductionRule> productionRules){
        TreeNode root = new TreeNode(0,productionRules.get(productionOrder.get(0)).getNonTerminal(),-1,-1);
        treeDs.add(root);
        int parentIndex = 0;
        int index = 1;
        for(Integer productionNo:productionOrder){
            ProductionRule productionRule = productionRules.get(productionNo);
            int leftSibling = -1;
            for(String symbol:productionRule.getRules()){
                TreeNode newNode = new TreeNode(index,symbol,parentIndex,leftSibling);
                index++;
                leftSibling=index-1;
                treeDs.add(newNode);
            }
            parentIndex+=productionRule.getRules().size();
        }
    }

    private static String centerString(String index, String info,String parent,String leftSibling) {
        return String.format("|%-" + 10 + "s|%-" + 14 +"s|%-" + 10 + "s|%-" + 15 +"s|\n",
                String.format("%" + (index.length() + (13 - index.length()) / 2) + "s", index),
                String.format("%" + (info.length() + (13 - info.length()) / 2) + "s", info),
                String.format("%" + (parent.length() + (13 - parent.length()) / 2) + "s", parent),
                String.format("%" + (leftSibling.length() + (13 - leftSibling.length()) / 2) + "s", leftSibling)
        );
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("|  index   |     info     |  parent  |  leftSibling  |\n");
        treeDs.forEach(node->builder.append(centerString(node.index.toString(),node.info.toString(),node.parentIndex.toString(),node.leftSiblingIndex.toString())));
        return builder.toString();
    }
}
