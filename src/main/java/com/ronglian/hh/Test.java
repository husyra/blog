package com.ronglian.hh;

/**
 * 练习
 */
public class Test {

    public static void main(String[] args){
        triangle();
//        picture();
//        multi99();
    }

    private static void multi99(){
        for(int i=1; i<=9; i++){
            for(int j=1; j<=i; j++){
                System.out.print(j+"*"+i+"="+i*j+"\t");
            }
            System.out.println();
        }
    }

    /**
     * 使用常数变易法打印以下三角形图形
     *
     *      *
     *     * *
     *   * * * *
     *  * * * * *
     */
    private static void triangle(){
        //1。用最简单原始的方法打印
        /*System.out.println("         *");
        System.out.println("        * *");
        System.out.println("       * * *");
        System.out.println("      * * * *");*/
        //从最后一行开始使用循环来代替传统打印
        //先打印5个空格

        /*for(int j=0; j<9; j++) System.out.print(" ");
        for(int j=0; j<1; j++)System.out.print("* ");
        System.out.println();

        for(int j=0; j<8; j++) System.out.print(" ");
        for(int j=0; j<2; j++)System.out.print("* ");
        System.out.println();

        for(int j=0; j<7; j++) System.out.print(" ");
        for(int j=0; j<3; j++)System.out.print("* ");
        System.out.println();

        for(int j=0; j<6; j++) System.out.print(" ");
        for(int j=0; j<4; j++)System.out.print("* ");
        System.out.println();

        for(int j=0; j<5; j++) System.out.print(" ");
        for(int j=0; j<5; j++)System.out.print("* ");
        System.out.println();*/

        for(int i=0; i<5; i++){
            for(int j=0; j<9-i; j++) System.out.print(" ");
            for(int j=0; j<i+1; j++)System.out.print("* ");
            System.out.println();
        }
    }

    /**
     * 画图
     *     *
     *    ***
     *   *****
     *    ***
     *     *
     */
    private static void diamond(){

    }

    /**
     * $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
     * $   $   $   $   $   $   $   $   $
     * $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
     * $   $   $   $   $   $   $   $   $
     * $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
     * $   $   $   $   $   $   $   $   $
     * $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
     */
    public static void picture(){
        //由于控制台和网页是以流的方式输出内容的，输出完一行没法再回去修改上一行信息
        //所以画复杂的图形时，需要用到一个缓冲数组，先按分类为数组赋值，最后再输出此数组即可。
        char[][] cache = new char[10][40];

//        line_h(cache, 0, 0,32);

        for(int i=0; i<7; i=i+2){
            line_h(cache, i, 0,31);
        }

        for(int i=0; i<32; i=i+3){
            for(int j=1; j<6; j=j+2){
                line_v(cache, i, j,j+1);
            }
        }

        //打印
        for(int i=0; i<cache.length; i++){
            for (int j=0; j<cache[i].length; j++){
                if(cache[i][j]==0){
                    System.out.print(" ");
                }else{
                    System.out.print(cache[i][j]);
                }
            }
            System.out.println();
        }
    }

    private static void line_h(char[][] cache, int row, int col1, int col2){
        for (int i=col1; i<col2; i++){
            cache[row][i]='$';
        }
    }

    private static void line_v(char[][] cache, int col, int row1, int row2){
        for(int i=row1; i<row2; i++){
            cache[i][col]='$';
        }
    }

}
