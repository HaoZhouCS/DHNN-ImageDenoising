package pers.zhouhao.ImageDenoising;

import pers.zhouhao.DHNN.DiscreteHopfield;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {

    private static final int matrixHeight = 4;
    private static final int matrixWidth = 4;
    private static final int vectorSize = matrixHeight * matrixWidth;


    private static Integer[][] sampleArray = {
            {        1, -1,  1, -1,
                     1, -1,  1, -1,
                     1,  1,  1,  1,
                    -1, -1,  1, -1
            }
    };

    private static Integer[][] testArray = {
            {       -1,  1, -1,  1,
                     1, -1,  1, -1,
                    -1,  1,  1,  1,
                     1, -1,  1, -1
            },
            {       -1,  1,  1, -1,
                     1, -1,  1, -1,
                     1,  1,  1,  1,
                    -1, -1,  1, -1
            },
            {       -1,  1, -1,  1,
                     1, -1,  1, -1,
                     1,  1,  1,  1,
                    -1, -1,  1, -1
            }
    };


    private static List<List<Integer>> sampleList = arrayToList(sampleArray);
    private static List<List<Integer>> testList = arrayToList(testArray);

    public static void main(String[] args) {
        DiscreteHopfield DH = new DiscreteHopfield(vectorSize);
        DH.setWeight(sampleList);

        for(List<Integer> testcase: testList) {
            List<Integer> target = DH.predict(testcase);
            outputList("testcase", testcase);
            outputList("target", target);
        }
    }

    private static List<List<Integer>> arrayToList(Integer[][] array) {

        List<List<Integer>> list = new LinkedList<>();
        for(Integer[] element: array) {
            list.add(Arrays.asList(element));
        }
        return list;
    }

    private static void outputList(String meaning, List<Integer> list) {

        System.out.println(meaning + ":");
        for(int i = 0;i < matrixHeight;i ++) {
            for(int j = 0;j < matrixWidth;j ++) {
                System.out.print(list.get(i * matrixWidth + j) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
