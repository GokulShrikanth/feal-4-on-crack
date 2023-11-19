package com.blaze;

import io.vertx.core.AbstractVerticle;
public class feal4 extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        data input = ReadInputs.inputs();
        System.out.println("Starting the decyption");

        for(int k1=0; k1<4096; k1++) {
            System.out.println("Running intial loop for value :" + k1 + " of 4096");
            int keyTilda = solver.get12BitKeyForInnerBytes(k1);
            int firstA1 = solver.calcConstInnerBytesk0(0, keyTilda, input);

            for(int w1=1; w1<data.pairs; w1++) {
                if(firstA1 != solver.calcConstInnerBytesk0(w1, keyTilda, input))
                    break;

                if(w1 == data.pairs-1) {
                    for(int k2=0; k2<1048576; k2++) {
                        System.out.println("Running secondary loop for value :" + k2 + " of 1048576");
                        int key0 = solver.get20BitKeyForOutterBytes(k2, keyTilda);
                        int firstA2 = solver.calcConstOutteBytesk0(0, key0, input);

                        for(int w2=1; w2<data.pairs; w2++) {
                            if(firstA2 != solver.calcConstOutteBytesk0(w2, key0, input))
                                break;

                            if(w2 == data.pairs-1)
                            solver.solveForK1(key0, input);
                        }
                    }
                }
            }
        }
    }
}
