package org.wangxm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Test {
    public static void main(String args[]) throws Exception
    {

        List<String> all = new ArrayList<String>();
        all.add("a");
        all.add("b");
        all.add("c");

        Iterator<String> iterator=all.iterator();//实例化迭代器
        while(iterator.hasNext()){
            String str=iterator.next();//读取当前集合数据元素
            if("b".equals(str)){
               // all.remove(str);
                iterator.remove();
            }else{
                System.out.println( str+" ");
            }
           // System.out.println( str+" ");
           // iterator.remove();
        }
        System.out.println("\n删除\"b\"之后的集合当中的数据为:"+all);

        Iterator<String> iterator2=all.iterator();//实例化迭代器
        while(iterator2.hasNext()){
            String str=iterator2.next();//读取当前集合数据元素
            System.out.println( str+" ");
            // System.out.println( str+" ");
            // iterator.remove();
        }
    }
}
