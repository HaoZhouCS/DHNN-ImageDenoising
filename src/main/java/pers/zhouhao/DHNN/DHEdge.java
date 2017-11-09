package pers.zhouhao.DHNN;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DHEdge {

    private DHNode nodeU;
    private DHNode nodeV;
    private double w;

    public void addW(double t) {
        w = w + t;
    }
}


