package pers.zhouhao.DHNN;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static pers.zhouhao.DHNN.DHNode.Type.INPUT_NODE;
import static pers.zhouhao.DHNN.DHNode.Type.OUTPUT_NODE;
import static pers.zhouhao.DHNN.ErrorInfo.ErrorCode.INIT_NODE_NUM_ERROR;
import static pers.zhouhao.DHNN.ErrorInfo.ErrorCode.SAMPLE_NODE_NUM_ERROR;

@Getter
public class DiscreteHopfield {

    private final int inputNodeNum;
    private final int outputNodeNum;

    private final int minNodeNum = 1;
    private final int maxNodeNum = 20;

    private List<DHNode> inputLayer = new LinkedList<>();
    private List<DHNode> outputLayer = new LinkedList<>();

    public DiscreteHopfield(int nodes) {
        if(nodes >= minNodeNum && nodes <= maxNodeNum) {
            inputNodeNum = outputNodeNum = nodes;
            initNetwork();
        } else {
            inputNodeNum = outputNodeNum = 0;
            ErrorInfo error = new ErrorInfo();
            error.setCode(INIT_NODE_NUM_ERROR);
            System.out.println(error.getInfo());
        }
    }

    private void initNetwork() {

        for (int i = 0; i < inputNodeNum; i++) {
            DHNode node = new DHNode(INPUT_NODE);
            inputLayer.add(node);
        }

        for (int i = 0; i < outputNodeNum; i++) {
            DHNode node = new DHNode(OUTPUT_NODE);
            outputLayer.add(node);
        }

        for(DHNode inputNode: inputLayer) {
            for(DHNode outputNode: outputLayer) {
                DHEdge edge = new DHEdge();
                edge.setNodeU(inputNode);
                edge.setNodeV(outputNode);
                edge.setW(0);
                inputNode.getOutputEdges().add(edge);
                outputNode.getInputEdges().add(edge);
            }
        }
    }

    public void setWeight(List<List<Integer>> samples) {

        ErrorInfo error = new ErrorInfo();
        for(List<Integer> sample: samples) {
            if(sample.size() != inputNodeNum) {
                error.setCode(SAMPLE_NODE_NUM_ERROR);
                System.out.println(sample + error.getInfo());
            } else {
                for(int i = 0;i < inputNodeNum;i ++) {
                    for(int j = 0;j < outputNodeNum;j ++) {
                        if (i != j) {
                            inputLayer.get(i).getOutputEdges().get(j).addW(sample.get(i) * sample.get(j));
                        }
                    }
                }
            }
        }

    }

    private int ThreadholdFunc(DHNode node) {

        List<DHEdge> inputEdges = node.getInputEdges();

        double U = 0;
        for(DHEdge dhEdge: inputEdges) {
            U += dhEdge.getW() * dhEdge.getNodeU().getX();
        }
        //U -= node.getThreadhold();
        node.setU(U);
        node.setX(ActivationFunc(U));
        return ActivationFunc(U);
    }

    private int ActivationFunc(double U) {
        return sgn(U);
    }

    private int sgn(double U) {
        if(U >= 0) return 1;
        else return -1;
    }

    public List<Integer> predict(List<Integer> testInstance) {
        int times = 0;
        List<Integer> lastOutput = initLastOutput();
        List<Integer> thisOutput = testInstance;

        while(times < 10000 && !equals(lastOutput, thisOutput)) {

            for(int i = 0;i < inputNodeNum;i ++) {
                inputLayer.get(i).setX(thisOutput.get(i));
            }
            outputLayer.stream().forEach(dhNode -> ThreadholdFunc(dhNode));

            lastOutput = thisOutput;
            thisOutput = outputLayer.stream().map(DHNode::getX).collect(Collectors.toList());
        }

        return thisOutput;
    }

    private List<Integer> initLastOutput() {
        List<Integer> lastOutput = new LinkedList<>();
        for(int i = 0;i < outputNodeNum;i ++) {
            lastOutput.add(-1);
        }
        return lastOutput;
    }

    private boolean equals(List<Integer> A, List<Integer> B) {
        if(A.size() != B.size()) {
            return false;
        } else {
            int size = A.size();
            for(int i = 0;i < size;i ++) {
                if(A.get(i) != B.get(i)) {
                    return false;
                }
            }
            return true;
        }
    }

}
