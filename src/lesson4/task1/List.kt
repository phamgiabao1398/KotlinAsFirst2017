@file:Suppress("UNUSED_PARAMETER")
package lesson4.task1

import lesson1.task1.discriminant
import java.lang.Math.pow

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
        when {
            y < 0 -> listOf()
            y == 0.0 -> listOf(0.0)
            else -> {
                val root = Math.sqrt(y)
                // Результат!
                listOf(-root, root)
            }
        }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + Math.sqrt(d)) / (2 * a)
    val y2 = (-b - Math.sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun sqr(m:Double) = m * m
fun abs(v: List<Double>): Double {
    var sum: Double = 0.0
    for(i in 0 .. v.size-1) {
        sum += sqr(v[i])
    }
    return Math.sqrt(sum)
}


/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double {
    var sum = 0.0
    for (i in 0..list.size - 1) {
        sum += list[i]
    }
    if (list.size == 0) { return 0.0
    } else { return sum / list.size
    }
}

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    val medim = mean(list)
    for (i in 0 .. list.size-1){
        list[i] = list [i] - medim
    }
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.0.
 */
fun times(a: List<Double>, b: List<Double>): Double {
    var min = 0
    var sum = 0.0
    if (a.size < b.size) {
        min = a.size - 1
    }
    else {
        min = b.size - 1
    }
    for (i in 0 .. min) {
        sum += a[i] * b[i]
    }
    return sum
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0.0 при любом x.
 */
fun polynom(p: List<Double>, x: Double): Double {
    var pp = 0.0
    var xx = 0.0
    for (element in p) {
        pp = (pp + element * pow(x, xx))
        xx++
    }
    return pp * 1.0
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Double>): MutableList<Double> {
    for (i in 1..list.size - 1) {
        list[i] = list[i] + list[i - 1]
    }
    return list
}


/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var t = 2
    var nn = n
    val x = mutableListOf<Int>()
    while (t <= nn) {
        if (nn % t == 0) {
            x.add(t)
            nn /= t
            t--
        }
        t++
    }
    return x
}

/**
 * Слvar t = 2
var n1 = n
val x = mutableListOf<Int>()
while (t <= n1) {
if (n1 % t == 0) {
x.add(t)
n1 /= t
t--
}
t++ожная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString(separator = "*")

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    var a = n
    val x = mutableListOf<Int>()
    if (n == 0) return listOf(0)
    while (a > 0) {
        x.add(a % base)
        a /= base
    }
    val s = mutableListOf<Int>()
    var t = 1
    while (t <= x.size) {
        s.add(x[x.size - t])
        t++
    }
    return s
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 */
fun convertToString(n: Int, base: Int): String {
    val result = StringBuilder()
    val convert = convert(n, base)
    for (element in convert) {
        if (element > 9) result.append('a' + element - 10)
        else result.append(element)
    }
    return result.toString()
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var m = 1
    var number = 0
    for (i in digits.size - 1 downTo 0) {
        number += digits[i] * m
        m *= base
    }
    return number
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 */
fun decimalFromString(str: String, base: Int): Int {
    var result = listOf<Int>()
    for (char in str) {
        if (char in '0' .. '9') result += char - '0'
        else result += char + 10 - 'a'
    }
    return decimal(result, base)
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    var number = n
    val map1 = mapOf(1 to "I", 4 to "IV", 5 to "V", 9 to "IX", 10 to "X", 40 to "XL", 50 to "L", 90 to "XC",
            100 to "C", 400 to "CD", 500 to "D", 900 to "CM", 1000 to "M")
    val result = StringBuilder()
    while (number > 0) {
        val parse = map1.keys.findLast { it <= number }
        if (parse != null)
        else continue
        number -= parse
        result.append(map1[parse])
    }
    return result.toString()
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String {
    val listUnits = listOf("", "одна", "две", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять")
    val listTens = listOf("", "десять", "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят", "девяносто")
    val listFromTenToTwenty = listOf("", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать")
    val listHundreds = listOf("", "сто", "двести", "триста", "четыреста", "пятьсот" , "шестьсот", "семьсот", "восемьсот", "девятьсот")
    val listResult = mutableListOf<String>()
    val lastDigit = n % 10
    val thousand = when (n / 1000 % 10) {
        1 -> "тысяча"
        in 2 .. 4 -> "тысячи"
        else -> "тысяч"
    }
    val unit = when (lastDigit) {
        1 -> "один"
        2 -> "два"
        else -> listUnits[lastDigit]
    }
    if (n > 999) {
        listResult.add(listHundreds[n / 100000 % 10])
        if (n / 1000 % 100 in 11 .. 19) {
            listResult.add(listFromTenToTwenty[n / 1000 % 10])
            listResult.add("тысяч")
        }
        else {
            listResult.add(listTens[n / 10000 % 10])
            listResult.add(listUnits[n / 1000 % 10])
            listResult.add(thousand)
        }
    }
    listResult.add(listHundreds[n / 100 % 10])
    if (n % 100 in 11 .. 19) listResult.add(listFromTenToTwenty[lastDigit])
    else {
        listResult.add(listTens[n / 10 % 10])
        listResult.add(unit)
    }
    return listResult.filter { it != "" }.joinToString( " ")
}