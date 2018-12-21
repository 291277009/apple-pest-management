package com.manage.applepestmanagement.arithmetic.d12_20;

import org.junit.Assert;
import org.junit.Test;

/**
 * User: Swang
 * Date: 2018-12-20
 * Time: 15:59
 */
public class Conversion {

    /**
     *
     * @param s
     * @param numRows
     * @return
     */
    public String convert(String s, int numRows) {
        if(numRows<=1)return s;
        StringBuilder[] sb=new StringBuilder[numRows];
        for(int i=0;i<sb.length;i++){
            sb[i]=new StringBuilder("");   //init every sb element **important step!!!!
        }
        int incre=1;
        int index=0;
        for(int i=0;i<s.length();i++){
            sb[index].append(s.charAt(i));
            if(index==0){incre=1;} //从此到下为最关键部分，用来记录循环变量（1->3,3->1)
            if(index==numRows-1){incre=-1;}
            index+=incre;
        }
        String re="";
        for(int i=0;i<sb.length;i++){
            re+=sb[i];
        }
        return re.toString();
    }

    @Test
    public void test() {
        Assert.assertEquals("PINALSIGYAHRPI" ,convert("PAYPALISHIRING",4));
        System.out.println(convert("PAYPALISHIRING",4));
    }
}
