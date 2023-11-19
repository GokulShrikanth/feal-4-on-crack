package com.blaze;

public class solver {
    static int rounds = 4;

    private static void splitPairs(int wordIndex, data input) {
        input.L0 = (int) Long.parseLong(input.plaintext[wordIndex].substring(0,8), 16);
        input.R0 = (int) Long.parseLong(input.plaintext[wordIndex].substring(8), 16);
        input.L4 = (int) Long.parseLong(input.cyphertext[wordIndex].substring(0,8), 16);
        input.R4 = (int) Long.parseLong(input.cyphertext[wordIndex].substring(8), 16);
    }

    private static int getBit(int num, int n) {
        return (num >> (31-n)) & 1;
    }

    private static int f(int num) {
        return Integer.reverseBytes(f1(Integer.reverseBytes(num)));
    }

    static int f1(int input) {
        byte[] x = new byte[4];
        byte[] y = new byte[4];

        unpack(input,x,0);
        y[1]=g1((byte)((x[0]^x[1])&255),(byte)((x[2]^x[3])&255));
        y[0]=g0((byte)(x[0]&255),(byte)(y[1]&255));
        y[2]=g0((byte)(y[1]&255),(byte)((x[2]^x[3])&255));
        y[3]=g1((byte)(y[2]&255),(byte)(x[3]&255));
        return pack(y,0);
    }

    static byte rot2(byte x) {
        return (byte)(((x&255)<<2)|((x&255)>>>6));
    }

    static byte g0(byte a,byte b) {
        return rot2((byte)((a+b)&255));
    }

    static byte g1(byte a,byte b) {
        return rot2((byte)((a+b+1)&255));
    }

    static int pack(byte[] b,int startindex) {
        return (((b[startindex+3]&255)<<24)|((b[startindex+2]&255)<<16)|((b[startindex+1]&255)<<8)|(b[startindex]&255));
     }

    static void unpack(int a,byte[] b,int startindex) {
        b[startindex]=(byte)a;
        b[startindex+1]=(byte)(a>>>8);
        b[startindex+2]=(byte)(a>>>16);
        b[startindex+3]=(byte)(a>>>24);
    }

    public static int get12BitKeyForInnerBytes(int k1) {
        return (((k1 >> 6) & 0x3F) << 16) + ((k1 & 0x3F) << 8);
    }

    public static int calcConstOutteBytesk0(int wordIndex, int key, data input) {
        splitPairs(wordIndex, input);
        int a1 = getBit(input.L0^input.R0^input.L4, 5)^getBit(input.L0^input.R0^input.L4, 13)^getBit(input.L0^input.R0^input.L4, 21);
        int a2 = getBit(input.L0^input.L4^input.R4, 15);
        int a3 = getBit(f(input.L0^input.R0^key), 15);

        return a1^a2^a3;
    }

    public static int get20BitKeyForOutterBytes(int k2, int keyTilda) {
        int a0 = (((k2 & 0xF) >> 2) << 6) + ((keyTilda >> 16) & 0xFF);
        int a1 = ((k2 & 0x3) << 6) + ((keyTilda >> 8) & 0xFF);
        int b0 = (k2 >> 12) & 0xFF;
        int b3 = (k2 >> 4) & 0xFF;
        int b1 = b0^a0;
        int b2 = b3^a1;

        return (b0 << 24)  + (b1 << 16) + (b2 << 8) + b3;
    }

    public static int calcConstInnerBytesk0(int i, int keyTilda, data input) {
        splitPairs(i, input);
        int a1 = getBit(input.L0^input.R0^input.L4, 13);
        int a2 = getBit(input.L0^input.L4^input.R4, 7)^getBit(input.L0^input.L4^input.R4, 15)^getBit(input.L0^input.L4^input.R4, 23)^getBit(input.L0^input.L4^input.R4, 31);
        int y0 = f(input.L0^input.R0^keyTilda);
        int a3 = getBit(y0, 7)^getBit(y0, 15)^getBit(y0, 23)^getBit(y0, 31);

        return a1^a2^a3;
    }

    static int calcConstOutteBytesk1(int wordIndex, int k0, int k1, data input) {
        splitPairs(wordIndex, input);
        int a1 = getBit(input.L0^input.L4^input.R4, 13);
        int y0 = f(input.L0^input.R0^k0);
        int y1 = f(input.L0^y0^k1);
        int a2 = getBit(y1, 7)^getBit(y1, 15)^getBit(y1, 23)^getBit(y1, 31);

        return a1^a2;
    }

    static int calculateConstInnerBytesk1(int wordIndex, int key, int k0, data input) {
        splitPairs(wordIndex, input);
        int a1 = getBit(input.L0^input.L4^input.R4, 5)^getBit(input.L0^input.L4^input.R4, 13)^getBit(input.L0^input.L4^input.R4, 21);
        int y0 = f(input.L0^input.R0^k0);
        int a2 = getBit(f(input.L0^y0^key), 15);

        return a1^a2;
    }

    private static int generate20BitKeyForOutterBytes(int k, int key_tilda) {
        int a0 = (((k & 0xF) >> 2) << 6) + ((key_tilda >> 16) & 0xFF);
        int a1 = ((k & 0x3) << 6) + ((key_tilda >> 8) & 0xFF);
        int b0 = (k >> 12) & 0xFF;
        int b3 = (k >> 4) & 0xFF;
        int b1 = b0^a0;
        int b2 = b3^a1;

        return (b0 << 24)  + (b1 << 16) + (b2 << 8) + b3;
    }

    static int calcConstInnerBytesk2(int wordIndex, int key, int k0, int k1, data input) {
        splitPairs(wordIndex, input);
        int a1 = getBit(input.L0^input.R0^input.L4, 5)^getBit(input.L0^input.R0^input.L4, 13)^getBit(input.L0^input.R0^input.L4, 21);
        int y0 = f(input.L0^input.R0^k0);
        int y1 = f(input.L0^y0^k1);
        int a2 = getBit(f(input.L0^input.R0^y1^key), 15);

        return a1^a2;
    }

    static int calcConstOutteBytesk2(int wordIndex, int k0, int k1, int k2, data input) {
        splitPairs(wordIndex, input);
        int a1 = getBit(input.L0^input.R0^input.L4, 13);
        int y0 = f(input.L0^input.R0^k0);
        int y1 = f(input.L0^y0^k1);
        int y2 = f(input.L0^input.R0^y1^k2);
        int a2 = getBit(y2, 7)^getBit(y2, 15)^getBit(y2, 23)^getBit(y2, 31);

        return a1^a2;
    }

    public static void solveForK1(int k, data input) {
        for(int k1=0; k1<4096; k1++) {
            int key_tilda = get12BitKeyForInnerBytes(k1);
            int first_a1 = calculateConstInnerBytesk1(0, key_tilda, k, input);

            for(int w1=1; w1<data.pairs; w1++) {
                if(first_a1 != calculateConstInnerBytesk1(w1, key_tilda,  k, input))
                    break;

                if(w1 == data.pairs-1) {
                    for(int k2=0; k2<1048576; k2++) {
                        int key1 = generate20BitKeyForOutterBytes(k2, key_tilda);
                        int first_a2 = calcConstOutteBytesk1(0, k, key1, input);

                        for(int w2=1; w2<data.pairs; w2++) {
                            if(first_a2 != calcConstOutteBytesk1(w2, k, key1, input))
                                break;

                            if(w2 == data.pairs-1)
                                solveForK2(k, key1, input);
                        }
                    }
                }
            }
        }
    }

    private static void solveForK2(int key0, int key1, data input) {
        for(int k1=0; k1<4096; k1++) {
            int key_tilda = get12BitKeyForInnerBytes(k1);
            int first_a1 = calcConstInnerBytesk2(0, key_tilda, key0, key1, input);

            for(int w1=1; w1<data.pairs; w1++) {
                if(first_a1 != calcConstInnerBytesk2(w1, key_tilda,  key0, key1, input))
                    break;

                if(w1 == data.pairs-1) {
                    for(int k2=0; k2<1048576; k2++) {
                        int key2 = generate20BitKeyForOutterBytes(k2, key_tilda);
                        int first_a2 = calcConstOutteBytesk2(0, key0, key1, key2, input);

                        for(int w2=1; w2<data.pairs; w2++) {
                            if(first_a2 != calcConstOutteBytesk2(w2, key0, key1, key2, input))
                                break;

                            if(w2 == data.pairs-1)
                                solveForK3(key0, key1, key2, input);
                        }
                    }
                }
            }
        }
    }

    private static void solveForK3(int key0, int key1, int key2, data input) {
    }

}
