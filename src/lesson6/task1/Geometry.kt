@file:Suppress("UNUSED_PARAMETER")
package lesson6.task1

import lesson1.task1.sqr

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = Math.sqrt(sqr(x - other.x) + sqr(y - other.y))
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point): this(linkedSetOf(a, b, c))
    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return Math.sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle): Double {
        val distance = center.distance(other.center) - (radius + other.radius)
        if (distance < 0) return 0.0
        else return distance
    }

    /**
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean =  center.distance(p) <= radius
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    override fun equals(other: Any?) =
            other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
            begin.hashCode() + end.hashCode()
}

/**
 * Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
fun diameter(vararg points: Point): Segment {
    var max = -1.0
    var maxPoint = Pair(0, 0)
    if (points.size < 2) {
        throw IllegalArgumentException()
    }
    for (i in 0..points.size - 1)
        for (j in i + 1..points.size - 1)
            if (points[i].distance(points[j]) > max) {
                max = points[i].distance(points[j])
                maxPoint = Pair(i, j)
            }
    return Segment(points[maxPoint.first], points[maxPoint.second])
}

/**
 * Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle {
    val halfX = (diameter.begin.x + diameter.end.x) / 2
    val halfY = (diameter.begin.y + diameter.end.y) / 2
    val radius = diameter.end.distance(diameter.begin) / 2
    return Circle(Point(halfX, halfY), radius)
}

/**
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        assert(angle >= 0 && angle < Math.PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double): this(point.y * Math.cos(angle) - point.x * Math.sin(angle), angle)

    /**
     * Средняя
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line): Point = TODO()

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${Math.cos(angle)} * y = ${Math.sin(angle)} * x + $b)"
}

/**
 * Средняя
 *
 * Построить прямую по отрезку
 */
fun lineBySegment(s: Segment): Line {
    val anglee = Math.atan((s.begin.y - s.end.y) / (s.begin.x - s.end.x))
    if (((s.end.x >= s.begin.x) && (s.end.y >= s.begin.y)) || ((s.end.x <= s.begin.x) && (s.end.y <= s.begin.y)))
        return (Line(s.begin, Math.abs(anglee))) else return (Line(s.begin, -Math.abs(anglee)))
}


/**
 * Средняя
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line {
    val anglee = Math.atan((a.y - b.y) / (a.x - b.x))
    if (((a.x >= a.x) && (b.y >= b.y)) || ((a.x <= a.x) && (b.y <= b.y)))
        return (Line(Segment(a,b).begin, Math.abs(anglee))) else return (Line(Segment(a,b).begin, -Math.abs(anglee)))
}

/**
 * Сложная
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line =  Line(Point((a.x + b.x) / 2.0, (a.y + b.y) / 2.0),
        (lineBySegment(Segment(a, b)).angle + (Math.PI / 2)) % (Math.PI))

/**
 * Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {
    if (circles.size < 2) throw IllegalArgumentException()
    var distance = circles[0].distance(circles[1])
    var result = Pair(circles[0], circles[1])
    for (i in 0..circles.size - 2)
        for (k in i + 1..circles.size - 1) {
            val distanceCircle = circles[i].distance(circles[k])
            if (distanceCircle < distance) {
                result = Pair(circles[i], circles[k])
                distance = distanceCircle
            }
        }
    return result
}

/**
 * Сложная
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle {
    val x12 = a.x - b.x
    val x23 = b.x - c.x
    val x31 = c.x - a.x
    val y12 = a.y - b.y
    val y23 = b.y - c.y
    val y31 = c.y - a.y
    val z1 = sqr(a.x) + sqr(a.y)
    val z2 = sqr(b.x) + sqr(b.y)
    val z3 = sqr(c.x) + sqr(c.y)
    val zx = y12 * z3 + y23 * z1 + y31 * z2
    val zy = x12 * z3 + x23 * z1 + x31 * z2
    val z = x12 * y31 - y12 * x31
    val x = -zx / (2 * z)
    val y = zy / (2 * z)
    val r = Math.sqrt(sqr(x - a.x) + sqr(y - a.y))
    return Circle(center = Point(x, y), radius = r)
}

/**
 * Очень сложная
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
fun minContainingCircle(vararg points: Point): Circle = TODO()

