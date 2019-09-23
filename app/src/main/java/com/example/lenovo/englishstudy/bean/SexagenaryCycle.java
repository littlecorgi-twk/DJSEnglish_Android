package com.example.lenovo.englishstudy.bean;

public class SexagenaryCycle {
    @Override
    public String toString() {
        return "SexagenaryCycle{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    /**
     * code : 1
     * msg : 数据返回成功
     * data : {"date":"2019-05-18","weekDay":6,"yearTips":"己亥","type":1,"typeDes":"休息日","chineseZodiac":"猪","solarTerms":"立夏后","avoid":"开市.入宅.探病.出火.造屋","lunarCalendar":"四月十四","suit":"嫁娶.祭祀.祈福.求嗣.斋醮.订盟.纳采.解除.出行.动土.破土.习艺.针灸.理发.会亲友.起基.修造.动土.竖柱.定磉.安床.拆卸.纳畜.牧养.放水.破土.除服.成服.修坟.立碑","dayOfYear":138,"weekOfYear":20,"constellation":"金牛座"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "date='" + date + '\'' +
                    ", weekDay=" + weekDay +
                    ", yearTips='" + yearTips + '\'' +
                    ", type=" + type +
                    ", typeDes='" + typeDes + '\'' +
                    ", chineseZodiac='" + chineseZodiac + '\'' +
                    ", solarTerms='" + solarTerms + '\'' +
                    ", avoid='" + avoid + '\'' +
                    ", lunarCalendar='" + lunarCalendar + '\'' +
                    ", suit='" + suit + '\'' +
                    ", dayOfYear=" + dayOfYear +
                    ", weekOfYear=" + weekOfYear +
                    ", constellation='" + constellation + '\'' +
                    '}';
        }

        /**
         * date : 2019-05-18
         * weekDay : 6
         * yearTips : 己亥
         * type : 1
         * typeDes : 休息日
         * chineseZodiac : 猪
         * solarTerms : 立夏后
         * avoid : 开市.入宅.探病.出火.造屋
         * lunarCalendar : 四月十四
         * suit : 嫁娶.祭祀.祈福.求嗣.斋醮.订盟.纳采.解除.出行.动土.破土.习艺.针灸.理发.会亲友.起基.修造.动土.竖柱.定磉.安床.拆卸.纳畜.牧养.放水.破土.除服.成服.修坟.立碑
         * dayOfYear : 138
         * weekOfYear : 20
         * constellation : 金牛座
         */

        private String date;
        private int weekDay;
        private String yearTips;
        private int type;
        private String typeDes;
        private String chineseZodiac;
        private String solarTerms;
        private String avoid;
        private String lunarCalendar;
        private String suit;
        private int dayOfYear;
        private int weekOfYear;
        private String constellation;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getWeekDay() {
            return weekDay;
        }

        public void setWeekDay(int weekDay) {
            this.weekDay = weekDay;
        }

        public String getYearTips() {
            return yearTips;
        }

        public void setYearTips(String yearTips) {
            this.yearTips = yearTips;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTypeDes() {
            return typeDes;
        }

        public void setTypeDes(String typeDes) {
            this.typeDes = typeDes;
        }

        public String getChineseZodiac() {
            return chineseZodiac;
        }

        public void setChineseZodiac(String chineseZodiac) {
            this.chineseZodiac = chineseZodiac;
        }

        public String getSolarTerms() {
            return solarTerms;
        }

        public void setSolarTerms(String solarTerms) {
            this.solarTerms = solarTerms;
        }

        public String getAvoid() {
            return avoid;
        }

        public void setAvoid(String avoid) {
            this.avoid = avoid;
        }

        public String getLunarCalendar() {
            return lunarCalendar;
        }

        public void setLunarCalendar(String lunarCalendar) {
            this.lunarCalendar = lunarCalendar;
        }

        public String getSuit() {
            return suit;
        }

        public void setSuit(String suit) {
            this.suit = suit;
        }

        public int getDayOfYear() {
            return dayOfYear;
        }

        public void setDayOfYear(int dayOfYear) {
            this.dayOfYear = dayOfYear;
        }

        public int getWeekOfYear() {
            return weekOfYear;
        }

        public void setWeekOfYear(int weekOfYear) {
            this.weekOfYear = weekOfYear;
        }

        public String getConstellation() {
            return constellation;
        }

        public void setConstellation(String constellation) {
            this.constellation = constellation;
        }
    }
}
