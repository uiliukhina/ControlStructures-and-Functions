/**
 * Был(а)
 * только что или ххх секунд/минут/часов назад
 */


fun main() {
    val time0 = 45
    val time1 = 65
    val time2 = 3602
    val time21 = 10788
    val time3 = 86404
    val time4 = 172850
    val time5 = 300000

    println(agoToText(time0))
    println(agoToText(time1))
    println(agoToText(time2))
    println(agoToText(time21))
    println(agoToText(time3))
    println(agoToText(time4))
    print(agoToText(time5))

}

/**
 * Если количество секунд
 * от 0 до 60, используйте вариант с только что.
 * от 61 до 60 * 60 (один час), вариант с x минут назад.
 * от 60 * 60 + 1 до 24 * 60 * 60 (сутки), вариант с x часов назад.
 * от суток до двух, вариант вчера.
 * от двух суток до трёх, вариант позавчера.
 * больше трёх суток, вариант давно.
 */
fun agoToText(timeInSec: Int): String {
    return "Был(а)" + when (timeInSec) {
        in 1..60 -> " только что"
        in 61..60 * 60 -> minutesToText(timeInSec)
        in 60 * 60 + 1..24 * 60 * 60 -> hoursToText(timeInSec)
        in 24 * 60 * 60 + 1..24 * 60 * 60 * 2 -> " вчера"
        in 24 * 60 * 60 * 2..24 * 60 * 60 * 3 -> " позавчера"
        else -> " давно"
    }
}


fun minutesToText(timeInSec: Int): String {
    val timeInMin: Int = timeInSec / 60
    return " $timeInMin" +
            (if (timeInMin % 10 == 1 && timeInMin != 11) " минут" else " минуту") +
            " назад"
}


fun hoursToText(timeInSec: Int): String {
    val timeInHours = timeInSec / 3600
    return " $timeInHours" + when (timeInHours) {
        1, 21 -> " час"
        in 2..4, in 22..24 -> " часа"
        else -> " часов"
    } + " назад"
}
/**
 * если остаток при делении на 10 равен 1 и количество часов не равно 11 -> "час"
 * если остаток при делении на 10 равен 2, 3, 4 и количество часов не равно [12..14] -> "часа"
 * если остаток при делении на 10 равен 5, 6, 7, 8, 9 или количество часов равно [10..20] -> "часов"
 */

