package com.lc.javase.grow;

import com.lc.javase.other.pojo.Seller;

import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class MethodReferenceStudy {
    public Seller createSeller() {
        //常规写法
        Seller seller = new Seller();

        //lambda表达式写法
        Supplier<Seller> supplier1 = () -> new Seller();
        seller = supplier1.get();

        //方法引用写法
        Supplier<Seller> supplier2 = Seller::new;
        seller = supplier2.get();
        Function<String, Seller> supplier3 = Seller::new;
        seller = supplier3.apply("zhangsan");
        BiFunction<String, Seller.Sex, Seller> supplier4 = Seller::new;
        seller = supplier4.apply("lisi", Seller.Sex.MALE);

        return seller;
    }

    public void sortSeller(List<Seller> sellers) {
        List<Seller> sellerList;
        //常规写法
        sellers.sort(new Comparator<Seller>() {
            @Override
            public int compare(Seller person1, Seller person2) {
                return person1.getBirthday().compareTo(person2.getBirthday());
            }
        });
        //lambda表达式常规写法
        sellers.sort((s1, s2) -> s1.getBirthday().compareTo(s2.getBirthday()));
        //lambda表达式方法引用写法
        sellers.sort(Comparator.comparing(Seller::getBirthday));
        //lambda表达式自定义方法引用写法
        sellers.sort(Seller::compareByBirthday);
        sellers.sort(new Seller()::compareByName);
    }
}
