@file:Suppress("UNUSED_PARAMETER")
package lesson5.task1

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        }
        else {
            println("Прошло секунд с начала суток: $seconds")
        }
    }
    else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку
 */
val months = listOf<String>("января", "февраля", "марта", "апреля", "мая", "июня",
        "июля", "августа", "сентября", "октября", "ноября", "декабря")

fun dateStrToDigit(str: String): String {
    val parts = str.split(" ")
    if (parts.size != 3) return ""
    else {
        try {
            val day = parts[0].toInt()
            val year = parts[2].toInt()
            val month = months.indexOf(parts[1]) + 1
            if (month == 0) return ""
            return String.format("%02d.%02d.%d", day, month, year)
        } catch (e: NumberFormatException) {
            return ""
        }
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 */
fun dateDigitToStr(digital: String): String {
    val parts = digital.split(".")
    if (parts.size != 3) return ""
    else {
        try {
            val day = parts[0].toInt()
            val monthNumber = parts[1].toInt()
            if (monthNumber !in 1..12) return ""
            val month = months[monthNumber - 1]
            return String.format("%d %s %s", day, month, parts[2])
        } catch (e: NumberFormatException) {
            return ""
        }
    }
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -98 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String {
    val numberPhoneFilter = phone.filter { it != ' ' && it != '-' }
    val superNumber = Regex("""(\+\d+)?(\(\d+\))?\d+""")
    if (!superNumber.matches(numberPhoneFilter)) return ""
    return numberPhoneFilter.filter { it != '(' && it != ')' }
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    if (jumps.matches(Regex("[-%[0-9] ]+"))) {
        if (Regex("[0-9]+").findAll(jumps).count() > 0)
            return Regex("[0-9]+").findAll(jumps).map { it.value.toInt() }.max().toString().toInt()
        else return -1
    } else return -1
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int{
    var maxHeight = 0
    var count = 0
    val parts = jumps.split(" ")
    if (parts.size == 1) return -1
    for (i in 0..parts.size - 1 step 2) {
        val heightJumpFilter = Regex("""\d+(\+|\%|\-)+""")
        if (!heightJumpFilter.matches(parts[i] + parts[i + 1])) return -1
        if ('+' in parts[i + 1] && parts[i].toInt() >= maxHeight) {
            maxHeight = parts[i].toInt()
            count++
        }
    }
    if (count > 0) {
        return maxHeight
    }
    else
        return -1
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    val filterString = Regex("""((\d+\ +(\+|\-)\ +)+)?\d+""")
    try {
        if (!filterString.matches(expression)) {
            throw IllegalArgumentException("IllegalArgumentException")
        } else {
            val parts = expression.split(" ")
            var answer = parts[0].toInt()
            if (parts.size == 1) return answer
            for (i in 0..parts.size - 3 step 2) {
                if (parts[i +1] == "+") {
                    answer += parts[i + 2].toInt()
                }  else {
                    answer -= parts[i +2].toInt()
                }
            }
            return answer
        }
    }catch (e: NumberFormatException){
        throw NumberFormatException("IllegalArgumentException")
    }
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    var found: Boolean = false
    var last: String = ""
    var passed: Int = 0
    for (w in Regex("[а-яА-Я]+").findAll(str)) {
        if (w.groupValues[0].toLowerCase() == last) {
            found = true
            passed -= (w.groupValues[0].length+1)
            break
        }
        passed+=w.groupValues[0].length+1
        last = w.groupValues[0].toLowerCase()
    }
    return if (found) passed else -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62.5; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть положительными
 */
fun mostExpensive(description: String): String {
    var most: String = ""
    var value: Double = -1.0
    if (description.matches(Regex("([а-яА-Я]+ [0-9]+.[0-9](;[ ])?)+"))) {
        Regex("([а-яА-Я]+) ([0-9]+.[0-9]);?").findAll(description).forEach {
            if(it.groupValues[2].toDouble() > value) { most = it.groupValues[1]; value = it.groupValues[2].toDouble() }
        }
    }
    return most
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    var count: Int = roman.length-1
    var num: Int = 0;
    while (count >= 0) {
        when (roman[count]) {
            'I' -> {num++ ; count--}
            'V' -> {
                if (count != 0 && roman[count-1] == 'I') {
                    num += 4
                    count -= 2
                } else {
                    num += 5
                    count--
                }
            }
            'X' -> {
                if (count != 0 && roman[count-1] == 'I') {
                    num += 9
                    count -= 2
                } else {
                    num += 10
                    count--
                }
            }
            'L' -> {
                if (count != 0 && roman[count-1] == 'X') {
                    num += 40
                    count -= 2
                } else {
                    num += 50
                    count--
                }
            }
            'C' -> {
                if (count != 0 && roman[count-1] == 'X') {
                    num += 90
                    count -= 2
                } else {
                    num += 100
                    count--
                }
            }
            'D' -> {
                if (count != 0 && roman[count-1] == 'C') {
                    num += 400
                    count -= 2
                } else {
                    num += 500
                    count--
                }
            }
            'M' -> {
                if (count != 0 && roman[count-1] == 'C') {
                    num += 900
                    count -= 2
                } else {
                    num += 1000
                    count--
                }
            }
            else -> {count = -1; num = -1}
        }
    }
    return num
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */


fun computeDeviceCells (cells:Int, commands: String ,limit:Int) : List<Int> = TODO()

