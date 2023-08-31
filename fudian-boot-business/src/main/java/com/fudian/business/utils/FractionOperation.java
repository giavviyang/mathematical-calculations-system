package com.fudian.business.utils;

import lombok.Data;

@Data
public class FractionOperation {

    /** 最小分子 */
    private int minNumerator;
    /** 最大分子 */
    private int maxNumerator;
    /** 最小分母 */
    private int minDenominator;
    /** 最大分母 */
    private int maxDenominator;
    /** 数字类型：0-任意数、1-质数、2-偶数、3-奇数、4-和数 */
    private int numeratorType;
    /** 数字类型：0-任意数、1-质数、2-偶数、3-奇数、4-和数 */
    private int denominatorType;
    /** 0-分子、1-分母 */
    private final int[] ints = new int[2];

    public FractionOperation() {}

    public FractionOperation(int minNumerator, int maxNumerator, int minDenominator, int maxDenominator, int type) {
        this.minNumerator = minNumerator;
        this.maxNumerator = maxNumerator;
        this.minDenominator = minDenominator;
        this.maxDenominator = maxDenominator;
        this.numeratorType = type;
        this.denominatorType = type;
    }

    public FractionOperation(int minNumerator, int maxNumerator, int minDenominator, int maxDenominator
            , int numeratorType, int denominatorType) {
        this.minNumerator = minNumerator+1;
        this.maxNumerator = maxNumerator+1;
        this.minDenominator = minDenominator+1;
        this.maxDenominator = maxDenominator+1;
        this.numeratorType = numeratorType;
        this.denominatorType = denominatorType;
    }

    public int[] generateNumberF() {
        ints[0] = generateNumeratorF();
        ints[0] = ints[0]==0?1:ints[0];
        ints[1] = generateDenominatorF();
        ints[1] = ints[1]<2?2:ints[1];
        return ints;
    }

    public int[] generateNumberF(int numeratorType, int denominatorType) {
        this.numeratorType = numeratorType;
        this.denominatorType = denominatorType;
        ints[0] = generateNumeratorF();
        ints[0] = ints[0]==0?1:ints[0];
        ints[1] = generateDenominatorF();
        ints[1] = ints[1]<2?2:ints[1];
        return ints;
    }

    /**
     * 生成分子
     */
    public int generateNumeratorF() {
        if (this.numeratorType == 4) {
            return generateSumNumber(this.maxNumerator);
        } else if (this.numeratorType == 1) {
            return generatePrime(this.maxNumerator);
        } else if (this.numeratorType == 2) {
            return generateEvenNumbers(this.maxNumerator);
        } else if (this.numeratorType == 3) {
            return generateOddNumber(this.maxNumerator);
        } else {
            return (int) (Math.random()*this.maxNumerator)+1;
        }
    }

    /**
     * 生成分母
     */
    public int generateDenominatorF() {
        if (this.denominatorType == 4) {
            return generateSumNumber(this.maxDenominator);
        } else if (this.denominatorType == 1) {
            return generatePrime(this.maxDenominator);
        } else if (this.denominatorType == 2) {
            return generateEvenNumbers(this.maxDenominator);
        } else if (this.denominatorType == 3) {
            return generateOddNumber(this.maxDenominator);
        } else {
            return (int) (Math.random()*this.maxDenominator);
        }
    }

    /**
     * 生成和数
     */
    public int generateSumNumber(int max) {
        int i = (int) (Math.random()*max);
        if (i>3 && (i%2==0 || i%3==0)) {
            return i;
        } else {
            if (i<=3) {
                return 4;
            }
            return i+1;
        }
    }

    /**
     * 生成质数
     */
    public int generatePrime(int max) {
        int i = (int) (Math.random()*max);
        if (i>1 && (i%2==0 || i%3==0)) {
            i = generatePrime(max);
        }
        return i;
    }

    /**
     * 生成偶数
     */
    public int generateEvenNumbers(int max) {
        int i = (int) (Math.random()*max);
        if (i == 0 && i%2 != 0) {
            i = generateEvenNumbers(max);
        }
        return i;
    }

    /**
     * 生成奇数
     */
    public int generateOddNumber(int max) {
        int i = (int) (Math.random()*max);
        if (i > 1 && i%2 == 0) {
            i = generateOddNumber(max);
        }
        return i;
    }

}
