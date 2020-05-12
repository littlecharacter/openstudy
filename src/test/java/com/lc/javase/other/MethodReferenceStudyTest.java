package com.lc.javase.other;

import com.alibaba.fastjson.JSON;
import com.lc.javase.other.pojo.Seller;
import com.lc.javase.grow.MethodReferenceStudy;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class MethodReferenceStudyTest {
    MethodReferenceStudy methodReferenceStudy = new MethodReferenceStudy();

    List<Seller> sellers = Arrays.asList(
            new Seller("a", LocalDate.of(1990, 2, 3), Seller.Sex.FEMALE, "123@qq.com"),
            new Seller("c", LocalDate.of(1989, 5, 15), Seller.Sex.FEMALE, "456@qq.com"),
            new Seller("b", LocalDate.of(1991, 12, 24), Seller.Sex.FEMALE, "789@qq.com")
    );

    @Test
    public void testCreateSeller() throws Exception {
        System.out.println(JSON.toJSONString(methodReferenceStudy.createSeller()));
    }

    @Test
    public void testSortSeller() throws Exception {
        methodReferenceStudy.sortSeller(sellers);
        System.out.println(JSON.toJSONString(sellers));
    }

}