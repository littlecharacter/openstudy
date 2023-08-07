package com.lc.mybatis;

import java.util.Date;

import com.lc.mybatis.domain.LabUser;
import com.lc.mybatis.domain.User;
import com.lc.mybatis.mapper.LabUserMapper;
import com.lc.mybatis.mapper.UserMapper;
import com.lc.mybatis.utils.IDGenerator;
import com.lc.mybatis.utils.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author gujixian
 * @since 2023/8/7
 */
public class TEST {
    private static Random random = new Random();

    public static void main(String[] args) throws Exception {
        IDGenerator idGenerator = new IDGenerator();
        SqlSession session = SqlSessionFactoryUtil.getSqlSessionFactory().openSession(true);
        LabUser user = new LabUser();
        try {
            user.setId(idGenerator.getId(16));
            user.setUsername(generateRandomName());
            user.setRealName(generateRandomName());
            user.setIdNumber(generateRandomID());
            user.setPhone(generateRandomPhoneNumber());
            user.setEmail(generateRandomEmail(user.getUsername()));
            user.setSex((byte) (random.nextInt(3) + 1));
            user.setAddress(generateRandomAddress());
            user.setValid((byte) 1);
            user.setCreateBy(1L);
            user.setModifyBy(1L);
            user.setCreateTime(new Date());
            user.setModifyTime(new Date());
            user.setInsertTime(System.currentTimeMillis());
            user.setUpdateTime(System.currentTimeMillis());
            LabUserMapper userMapper = session.getMapper(LabUserMapper.class);
            userMapper.insertSelective(user);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private static String generateRandomName() {
        int length = random.nextInt(13) + 3; // 生成3-15之间的随机长度
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char letter = (char) (random.nextInt(26) + 'a'); // 生成随机字母
            name.append(letter);
        }
        return name.toString();
    }

    private static String generateRandomID() {
        StringBuilder idNumber = new StringBuilder();
        // 生成前17位随机数字
        for (int i = 0; i < 17; i++) {
            int digit = random.nextInt(10);
            idNumber.append(digit);
        }
        idNumber.append(random.nextInt(2) == 0 ? random.nextInt(10) : "X");
        return idNumber.toString();
    }

    private static String generateRandomPhoneNumber() {
        StringBuilder phoneNumber = new StringBuilder();
        // 生成手机号的前三位（号段）
        String[] prefixes = {"130", "131", "132", "133", "134", "135", "136", "137", "138", "139",
                "150", "151", "152", "153", "155", "156", "157", "158", "159",
                "170", "176", "177", "178",
                "180", "181", "182", "183", "184", "185", "186", "187", "188", "189"};
        String prefix = prefixes[random.nextInt(prefixes.length)];
        phoneNumber.append(prefix);
        // 生成手机号的后8位
        for (int i = 0; i < 8; i++) {
            int digit = random.nextInt(10);
            phoneNumber.append(digit);
        }
        return phoneNumber.toString();
    }

    private static String generateRandomAddress() {
        StringBuilder address = new StringBuilder();
        // 生成随机省份
        String[] provinces = {"北京市", "上海市", "广东省", "江苏省", "浙江省", "四川省", "湖北省", "湖南省", "河南省", "河北省"};
        String province = provinces[random.nextInt(provinces.length)];
        address.append(province);
        // 生成随机城市
        String[] cities = {"北京市", "上海市", "广州市", "深圳市", "杭州市", "成都市", "武汉市", "长沙市", "郑州市", "石家庄市"};
        String city = cities[random.nextInt(cities.length)];
        address.append(city);
        // 生成随机区县
        String[] districts = {"朝阳区", "黄浦区", "天河区", "福田区", "西湖区", "锦江区", "江汉区", "岳麓区", "金水区", "新华区"};
        String district = districts[random.nextInt(districts.length)];
        address.append(district);
        // 生成随机街道
        String[] streets = {"人民路", "中山路", "解放路", "建设路", "文化路", "长江路", "湖南路", "河南路", "光明路", "和平路"};
        String street = streets[random.nextInt(streets.length)];
        address.append(street);
        // 生成随机门牌号
        int houseNumber = random.nextInt(9999) + 1;
        address.append(houseNumber).append("号");
        address.append(random.nextInt(9999) + 1);
        return address.toString();
    }

    public static String generateRandomEmail(String name) {
        StringBuilder email = new StringBuilder();
        String[] domains = {"gmail.com", "yahoo.com", "hotmail.com", "outlook.com", "qq.com", "163.com"};
        String domain = domains[random.nextInt(domains.length)];
        email.append(name).append("@").append(domain);
        return email.toString();
    }
}
