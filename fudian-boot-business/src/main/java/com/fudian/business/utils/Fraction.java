package com.fudian.business.utils;

import lombok.Data;

@Data
public class Fraction extends FractionOperation {

    /** 分子 */
    private int numerator;
    /** 分母 */
    private int denominator;
    /** 最终值 */
    private String value;

    public String generateNumber() {
        int[] ints = super.generateNumberF();
        this.numerator = ints[0];
        this.denominator = ints[1];
        this.value = ints[0] + "/" + ints[1];
        return this.value;
    }

    public String generateNumber(int type) {
        return generateNumber(type,type);
    }

    public String generateNumber(int numeratorType, int denominatorType) {
        int[] ints = super.generateNumberF(numeratorType, denominatorType);
        this.numerator = ints[0];
        this.denominator = ints[1];
        this.value = ints[0] + "/" + ints[1];
        return this.value;
    }

    public Fraction() {
        this(0);
    }

    public Fraction(int type) {
        this(100,100,type);
    }

    public Fraction(int numeratorType, int denominatorType) {
        this(100,100,numeratorType,denominatorType);
    }

    /**
     *
     * @author hong 2022/1/5
        * @param maxNumerator 分子最大值
        * @param maxDenominator 分母最大值
        * @param type 分子分母类型：0-和数、1-质数、2-偶数、3-奇数、4-任意数
     * @return
     */
    public Fraction(int maxNumerator, int maxDenominator, int type) {
        this(0,maxNumerator,0,maxDenominator,type);
    }

    /**
     *
     * @author hong 2022/1/5
        * @param maxNumerator 分子最大值
        * @param maxDenominator 分母最大值
        * @param numeratorType 分子类型：0-和数、1-质数、2-偶数、3-奇数、4-任意数
        * @param denominatorType 分母类型：0-和数、1-质数、2-偶数、3-奇数、4-任意数
     * @return
     */
    public Fraction(int maxNumerator, int maxDenominator, int numeratorType, int denominatorType) {
        this(0,maxNumerator,0,maxDenominator,numeratorType,denominatorType);
    }

    public Fraction(int minNumerator, int maxNumerator, int minDenominator, int maxDenominator, int type) {
        super(minNumerator,maxNumerator,minDenominator,maxDenominator,type);
        generateNumber(type);
    }

    /**
     *
     * @author hong 2022/1/5
        * @param minNumerator 分子最大值
        * @param maxNumerator 分子最大值
        * @param minDenominator 分母最大值
        * @param maxDenominator 分母最大值
        * @param numeratorType 分子类型：0-和数、1-质数、2-偶数、3-奇数、4-任意数
        * @param denominatorType 分母类型：0-和数、1-质数、2-偶数、3-奇数、4-任意数
     * @return
     */
    public Fraction(int minNumerator, int maxNumerator, int minDenominator, int maxDenominator, int numeratorType
            , int denominatorType) {
        super(minNumerator,maxNumerator,minDenominator,maxDenominator,numeratorType,denominatorType);
        generateNumber(numeratorType,denominatorType);
    }

}
