@file:Suppress("UNUSED_PARAMETER")
package lesson3.task1

import java.lang.Math.*

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    for (m in 2..Math.sqrt(n.toDouble()).toInt()) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n/2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 */
fun digitNumber(n: Int): Int {
    var count = 1
    var number = n
    while (number > 0) {
        if (number / 10 > 0) {
            count++
        }
        number /= 10
    }
    return count
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    var a = 1
    var b = 1
    var result = 1
        if((n == 1) || (n == 2)) return 1
        else {
            for ( i in 1..n-2) {
                result = a + b
                a = b
                b = result
            }
            return result
        }
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    var uscln = 1
    var min = if(m > n) m else n
    for (i in min downTo 1)
    {
        if(m % i == 0 && n % i == 0){
            uscln = i
            break
        }
    }
    return m * n / uscln
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    var del = 2
    while (n % del != 0) {
        del += 1
    }
    return del
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    var m = n / 2
    while (n % m != 0) {
        m -= 1
    }
    return m
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    val min = if(m > n) m else n

    for (a in min downTo 2) {
        if (m % a == 0 && n % a ==0)
            return false
    }
            return true

}

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    val x = sqrt(m.toDouble())
    val y = sqrt(n.toDouble())
    if (m == n) return true
    return x.toInt() < y.toInt()
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double): Double {
    var s:Double
    var a:Double=x
    var i=1
    var s1=1.0
    while ((a>0)&&(a>=2*PI)){
        a=a-2*PI
    }
    while ((a<0)&&(a<=-2*PI)){
        a=a+2*PI
    }
    s=a
    var a1=a
    if (a==0.0) return 0.0
    while (abs(s/s1) > abs(eps)){
        s=s*a1*a1*(-1)
        s1=s1*(i+1.0)*(i+2.0)
        a=a+s/s1
        i=i+2
    }
    return a
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double {
    var s: Double
    var a  = 1.0
    var i = 0.0
    var s1 = 1.0
    var a1: Double
    if (x>=0) {a1=x} else a1=-x
    while (a1>=2*PI) {
        a1=a1-2*PI
    }
    s=a
    if (x==0.0) return 1.0
    while (abs(s/s1) >= abs(eps)) {
        s1=s1*(i+1.0)*(i+2.0)
        s=s*a1*a1*(-1.0)
        a=a+s/s1
        i=i+2.0
    }
    return a
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 * Не использовать строки при решении задачи.
 */
fun revert(n: Int): Int {
    var result = 0
    var number = n
    while(number > 0){
        result = (result *10) + number % 10
        number /= 10
    }
    return result
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 */
fun isPalindrome(n: Int): Boolean {
    var number = n
    var invertedNumber = 0
    while (number > 0){
        invertedNumber = ((invertedNumber * 10) + (number % 10))
        number /= 10
    }
    return invertedNumber == n
}

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 */
fun hasDifferentDigits(n: Int): Boolean {
    val x = n % 10
    var number = n
    var result = false
    do {
        if (x != number % 10) result = true
        number /= 10
    } while (number != 0)
    return result
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 */

fun quantity(n: Int): Int {
    var r: Int = 0
    var nn: Int = n
    while (nn > 0) {
        nn = nn / 10
        r++
    }
    return r
}

fun squareSequenceDigit(n: Int): Int {
    var i = 0
    var number = 0
    var result = 0
    while (number < n) {
           i++
           number = number + quantity(i * i)
         }
       result = i * i
       for (i in n..number - 1) {
           result = result / 10
         }
    return (result % 10)
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 */
fun fibSequenceDigit(n: Int): Int {
    var i = 0
    var number = 0
    var result = 0
        while (number < n) {
               i++
               number = number + quantity(fib(i))
        }
        result = fib(i)
        for (i in n..number - 1) {
               result = result / 10
        }
    return (result % 10)
}
