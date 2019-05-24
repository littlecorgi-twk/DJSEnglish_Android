package com.example.lenovo.englishstudy.viewPageCard;

public class TianGanDiZhiShengXiao {
    private final static String[][] tgdz = new String[][]{
            {
                    "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"
            },//10天干
            {
                    "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"
            }
    };//12地支;12生肖，（注：12生肖对应12地支，即子鼠，丑牛,寅虎依此类推）
    private final static String[] animalYear = new String[]{
            "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};

    private final static int startYear = 1804;//定义起始年，1804年为甲子年属鼠

    /**
     * 获取当前年份与起始年之间的差值
     **/
    public static int subtractYear(int year) {
        int jiaziYear = startYear;
        if (year < jiaziYear) {//如果年份小于起始的甲子年(startYear = 1804),则起始甲子年往前偏移
            jiaziYear = jiaziYear - (60 + 60 * ((jiaziYear - year) / 60));//60年一个周期
        }
        return year - jiaziYear;
    }

    /**
     * 获取该年的天干名称
     **/
    public static String getTianGanName(int year) {
        String name = tgdz[0][subtractYear(year) % 10];
        return name;
    }

    /**
     * 获取该年的地支名称
     **/
    public static String getDiZhiName(int year) {
        String name = tgdz[1][subtractYear(year) % 12];
        return name;
    }

    /**
     * 获取该年的天干、地支名称
     */
    public static String getTGDZName(int year) {
        String name = getTianGanName(year) + getDiZhiName(year);

        return name;

    }

    /**
     * 获取该年的生肖名称
     */
    public static String getAnimalYearName(int year) {
        String name = animalYear[subtractYear(year) % 12];
        return name;
    }
}