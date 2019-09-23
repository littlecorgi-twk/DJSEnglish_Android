package com.example.lenovo.englishstudy.viewPageCard

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object LunarCalendar {

    //	private static final Logger logger = LoggerFactory.getLogger(CalendarUtil.class);
    // 计算阴历日期参照1900年到2049年
    private val LUNAR_INFO = intArrayOf(0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2, 0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977, 0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970, 0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950, 0x06aa0, 0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0, 0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6, 0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570, 0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0, 0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0, 0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930, 0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530, 0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45, 0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0)

    private val LUNAR_MONTH = arrayOf("正月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "冬月", "腊月")

    private val LUNAR_DAY = arrayOf("初一", "初二", "初三", "初四", "初五", "初六", "初七", "初八", "初九", "初十", "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十", "廿一", "廿二", "廿三", "廿四", "廿五", "廿六", "廿七", "廿八", "廿九", "三十")

    // 允许输入的最小年份
    private val MIN_YEAR = 1900
    // 允许输入的最大年份
    private val MAX_YEAR = 2049
    // 当年是否有闰月
    private var isLeapYear: Boolean = false
    // 阳历日期计算起点
    private val START_DATE = "19000130"


    /**
     * 计算阴历 `year`年闰哪个月 1-12 , 没闰传回 0
     *
     * @param year 阴历年
     * @return (int)月份
     * @author liu 2015-1-5
     */
    private fun getLeapMonth(year: Int): Int {
        return LUNAR_INFO[year - 1900] and 0xf
    }

    /**
     * 计算阴历`year`年闰月多少天
     *
     * @param year 阴历年
     * @return (int)天数
     * @author liu 2015-1-5
     */
    private fun getLeapMonthDays(year: Int): Int {
        return if (getLeapMonth(year) != 0) {
            if (LUNAR_INFO[year - 1900] and 0xf0000 == 0) {
                29
            } else {
                30
            }
        } else {
            0
        }
    }

    /**
     * 计算阴历`lunarYeay`年`month`月的天数
     *
     * @param lunarYeay 阴历年
     * @param month     阴历月
     * @return (int)该月天数
     * @author liu 2015-1-5
     */
    @Throws(Exception::class)
    private fun getMonthDays(lunarYeay: Int, month: Int): Int {
        if (month > 31 || month < 0) {
            throw Exception("月份有错！")
        }
        // 0X0FFFF[0000 {1111 1111 1111} 1111]中间12位代表12个月，1为大月，0为小月
        val bit = 1 shl 16 - month
        return if (LUNAR_INFO[lunarYeay - 1900] and 0x0FFFF and bit == 0) {
            29
        } else {
            30
        }
    }

    /**
     * 计算阴历`year`年的总天数
     *
     * @param year 阴历年
     * @return (int)总天数
     * @author liu 2015-1-5
     */
    private fun getYearDays(year: Int): Int {
        var sum = 29 * 12
        var i = 0x8000
        while (i >= 0x8) {
            if (LUNAR_INFO[year - 1900] and 0xfff0 and i != 0) {
                sum++
            }
            i = i shr 1
        }
        return sum + getLeapMonthDays(year)
    }

    /**
     * 计算两个阳历日期相差的天数。计算不准确，已经废弃
     *
     * @param startDate 开始时间
     * @param endDate   截至时间
     * @return (int)天数
     * @author liu 2015-1-5
     */
    @Deprecated("")
    private fun daysBetween2(startDate: Date, endDate: Date): Int {
        val between_days = (endDate.time - startDate.time) / (1000 * 3600 * 24)

        return Integer.parseInt(between_days.toString())
    }

    /**
     * 计算两个阳历日期相差的天数。
     *
     * @param startDate 开始时间
     * @param endDate   截至时间
     * @return (int)天数
     * @author liu 2017-3-2
     */
    private fun daysBetween(startDate: Date?, endDate: Date?): Int {
        var days = 0
        //将转换的两个时间对象转换成Calendar对象
        val can1 = Calendar.getInstance()
        can1.time = startDate
        val can2 = Calendar.getInstance()
        can2.time = endDate
        //拿出两个年份
        val year1 = can1.get(Calendar.YEAR)
        val year2 = can2.get(Calendar.YEAR)
        //天数

        var can: Calendar? = null
        //如果can1 < can2
        //减去小的时间在这一年已经过了的天数
        //加上大的时间已过的天数
        if (can1.before(can2)) {
            days -= can1.get(Calendar.DAY_OF_YEAR)
            days += can2.get(Calendar.DAY_OF_YEAR)
            can = can1
        } else {
            days -= can2.get(Calendar.DAY_OF_YEAR)
            days += can1.get(Calendar.DAY_OF_YEAR)
            can = can2
        }
        for (i in 0 until Math.abs(year2 - year1)) {
            //获取小的时间当前年的总天数
            days += can!!.getActualMaximum(Calendar.DAY_OF_YEAR)
            //再计算下一年。
            can.add(Calendar.YEAR, 1)
        }
        return days
    }

    /**
     * 检查阴历日期是否合法
     *
     * @param lunarYear     阴历年
     * @param lunarMonth    阴历月
     * @param lunarDay      阴历日
     * @param leapMonthFlag 闰月标志
     */
    @Throws(Exception::class)
    private fun checkLunarDate(lunarYear: Int, lunarMonth: Int, lunarDay: Int, leapMonthFlag: Boolean) {
        if (lunarYear < MIN_YEAR || lunarYear > MAX_YEAR) {
            throw Exception("非法农历年份！")
        }
        if (lunarMonth < 1 || lunarMonth > 12) {
            throw Exception("非法农历月份！")
        }
        if (lunarDay < 1 || lunarDay > 30) { // 中国的月最多30天
            throw Exception("非法农历天数！")
        }

        val leap = getLeapMonth(lunarYear)// 计算该年应该闰哪个月
        if (leapMonthFlag == true && lunarMonth != leap) {
            throw Exception("非法闰月！")
        }
    }

    /**
     * 阴历转换为阳历
     *
     * @param lunarDate     阴历日期,格式YYYYMMDD
     * @param leapMonthFlag 是否为闰月
     * @return 阳历日期, 格式：YYYYMMDD
     * @author liu 2015-1-5
     */
    @Throws(Exception::class)
    fun lunarToSolar(lunarDate: String, leapMonthFlag: Boolean): String {
        val lunarYear = Integer.parseInt(lunarDate.substring(0, 4))
        val lunarMonth = Integer.parseInt(lunarDate.substring(4, 6))
        val lunarDay = Integer.parseInt(lunarDate.substring(6, 8))

        checkLunarDate(lunarYear, lunarMonth, lunarDay, leapMonthFlag)

        var offset = 0

        for (i in MIN_YEAR until lunarYear) {
            val yearDaysCount = getYearDays(i) // 求阴历某年天数
            offset += yearDaysCount
        }
        //计算该年闰几月
        val leapMonth = getLeapMonth(lunarYear)

        if (leapMonthFlag and (leapMonth != lunarMonth)) {
            throw Exception("您输入的闰月标志有误！")
        }

        //当年没有闰月或月份早于闰月或和闰月同名的月份
        if (leapMonth == 0 || lunarMonth < leapMonth || lunarMonth == leapMonth && !leapMonthFlag) {
            for (i in 1 until lunarMonth) {
                val tempMonthDaysCount = getMonthDays(lunarYear, i)
                offset += tempMonthDaysCount
            }

            // 检查日期是否大于最大天
            if (lunarDay > getMonthDays(lunarYear, lunarMonth)) {
                throw Exception("不合法的农历日期！")
            }
            offset += lunarDay // 加上当月的天数
        } else {//当年有闰月，且月份晚于或等于闰月
            for (i in 1 until lunarMonth) {
                val tempMonthDaysCount = getMonthDays(lunarYear, i)
                offset += tempMonthDaysCount
            }
            if (lunarMonth > leapMonth) {
                val temp = getLeapMonthDays(lunarYear) // 计算闰月天数
                offset += temp // 加上闰月天数

                if (lunarDay > getMonthDays(lunarYear, lunarMonth)) {
                    throw Exception("不合法的农历日期！")
                }
                offset += lunarDay
            } else {    // 如果需要计算的是闰月，则应首先加上与闰月对应的普通月的天数
                // 计算月为闰月
                val temp = getMonthDays(lunarYear, lunarMonth) // 计算非闰月天数
                offset += temp

                if (lunarDay > getLeapMonthDays(lunarYear)) {
                    throw Exception("不合法的农历日期！")
                }
                offset += lunarDay
            }
        }

        val formatter = SimpleDateFormat("yyyyMMdd")
        var myDate: Date? = null
        myDate = formatter.parse(START_DATE)
        val c = Calendar.getInstance()
        c.time = myDate
        c.add(Calendar.DATE, offset)
        myDate = c.time

        return formatter.format(myDate)
    }

    /**
     * 阳历日期转换为阴历日期
     *
     * @param solarDate 阳历日期,格式YYYYMMDD
     * @return 阴历日期
     * @author liu 2015-1-5
     */
    @Throws(Exception::class)
    fun solarToLunar(solarDate: String): String {
        var i: Int
        var temp = 0
        val lunarYear: Int
        val lunarMonth: Int //农历月份
        val lunarDay: Int //农历当月第几天
        var leapMonthFlag = false

        val formatter = SimpleDateFormat("yyyyMMdd")
        var myDate: Date? = null
        var startDate: Date? = null
        try {
            myDate = formatter.parse(solarDate)
            startDate = formatter.parse(START_DATE)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        var offset = daysBetween(startDate, myDate)

        i = MIN_YEAR
        while (i <= MAX_YEAR) {
            temp = getYearDays(i)  //求当年农历年天数
            if (offset - temp < 1) {
                break
            } else {
                offset -= temp
            }
            i++
        }
        lunarYear = i

        val leapMonth = getLeapMonth(lunarYear)//计算该年闰哪个月
        //设定当年是否有闰月
        isLeapYear = leapMonth > 0

        i = 1
        while (i <= 12) {
            if (i == leapMonth + 1 && isLeapYear) {
                temp = getLeapMonthDays(lunarYear)
                isLeapYear = false
                leapMonthFlag = true
                i--
            } else {
                temp = getMonthDays(lunarYear, i)
            }
            offset -= temp
            if (offset <= 0) {
                break
            }
            i++
        }

        offset += temp
        lunarMonth = i
        lunarDay = offset

        return LUNAR_MONTH[lunarMonth - 1] + "月" + LUNAR_DAY[lunarDay - 1]
        // lunarYear+"年"+(leapMonthFlag&(lunarMonth==leapMonth)?"闰":"")+lunarMonth+"月"+lunarDay+"日";
    }
}
