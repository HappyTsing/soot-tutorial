package com.wang;

public class Foo {
    public static void main(String[] args) {
        HelloWorld hw = new HelloWorld();
        hw.sayHello();
        Foo f = new Foo();
        int a = 7;
        int b = 14;
        int x = (f.bar(21) + a) * b ;
        if ( a == 6){
            System.out.println(6);
        }else {
            System.out.println(7);
        }

        System.out.println(x);
    }
    public int bar(int n) { return n + 42; }
}