package day03;

/**
 * @author Shelly An
 * @create 2020/8/1 17:55
 */
public class Spark01_RDD_Exception {
    public static void main(String[] args) {
        /**
         * 空指针异常：调用一个为空（null）对象的属性或方法，会发生空指针异常
         *
         * 1. 属性是成员，出现空指针异常
         *  对象为null，调用成员属性，就会发生空指针异常
         * 2. 属性是静态，出现空指针异常
         *  对象为null，调用静态属性，不会发生空指针异常（只要该属性有赋值）
         *  因此，这里出现空指针异常的原因是，静态属性为null，
         *  属性为包装类型，但是方法参数是基本类型，
         *  那么属性为null时进行拆箱操作，就会发生空指针异常
         *
         * 上面两个情况虽然都出现空指针异常，但是是两种完全不同的操作
         */

        /**
         * sleep , wait的区别
         *
         * sleep 静态方法 哪个线程调用，哪个线程休眠（调用）
         * wait 成员方法 哪个线程的方法，哪个线程等待（所属）
         */
        User user = null;
        test(user.age);

    }

    public static void test(int age){
        System.out.println("年龄："+age);
    }
}

class User{
    public Integer age;
}
