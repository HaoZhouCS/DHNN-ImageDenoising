package pers.zhouhao.DHNN;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class DHNode {

    private Type type;          //节点类型
    private int x;           //节点的输出值
    private double u;           //节点未经激励函数处理的加权和
    private double threadhold;  //阈值
    private List<DHEdge> inputEdges = new LinkedList<DHEdge>();
    private List<DHEdge> outputEdges = new LinkedList<DHEdge>();

    public DHNode(Type _type) {
        type = _type;
    }

    public void addThreadhold(double t) {
        threadhold += t;
    }

    public enum Type {
        INPUT_NODE,
        OUTPUT_NODE;
    }

}

