@file:Suppress("UNUSED_PARAMETER")
package lesson6.task1

import lesson1.task1.sqr
import java.lang.Math.*

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
        val distance = other.center.distance(center) - (other.radius + radius)
        return if (distance > 0) distance else 0.0
    }

    /**
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean = p.distance(center) <= radius
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
    if (points.size < 2) throw IllegalArgumentException()
    var maxdist = -1.0
    var result = Segment(points[0], points[0])
    for (i in 0..points.size - 2) {
        for (k in i + 1..points.size - 1) {
            val distance = points[i].distance(points[k])
            if (distance > maxdist) {
                maxdist = distance
                result = Segment(points[i], points[k])
            }
        }
    }
    return result
}

/**
 * Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle =
        Circle(
                Point(
                        (diameter.begin.x + diameter.end.x) / 2,
                        (diameter.begin.y + diameter.end.y) / 2
                ),
                diameter.begin.distance(diameter.end) / 2
        )

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
    fun crossPoint(other: Line): Point {
        val x = (b * cos(other.angle) - other.b * cos(angle)) / sin(other.angle - angle)
        val y = if (abs(PI / 2 - angle) > abs(PI / 2 - other.angle))
            (x * sin(this.angle) + this.b) / cos(this.angle)
        else (x * sin(other.angle) + other.b) / cos(other.angle)
        return Point(x, y)
    }

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
fun lineBySegment(s: Segment): Line = Line(s.begin, Math.atan((s.end.y - s.begin.y) / (s.end.x - s.begin.x)))



/**
 * Средняя
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line = lineBySegment(Segment(a, b))


/**
 * Сложная
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line {
    val biX = (a.x + b.x) / 2
    val biY = (a.y + b.y) / 2
    if (a.x == b.x)
        return Line(Point(biX, biY), 0.0)
    var al = lineByPoints(a, b).angle + PI / 2
    if (al > PI) al -= PI
    return Line(Point(biX, biY), al)
}


/**
 * Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {
    if (circles.size < 2) throw IllegalArgumentException()
    var result = Pair(circles[0], circles[1])
    var mindist = circles[0].distance(circles[1])
    for (i in 0..circles.size - 2) {
        for (k in i + 1..circles.size - 1) {
            val dist = circles[i].distance(circles[k])
            if (dist < mindist) {
                mindist = dist
                result = Pair(circles[i], circles[k])
            }
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
    val xofcenter = -1 * ((a.y - b.y) * ((c.x * c.x) + (c.y * c.y)) + (b.y - c.y) * ((a.x * a.x) + (a.y * a.y)) + (c.y -
            a.y) * ((b.y * b.y) + (b.x * b.x))) / (2 * ((a.x - b.x) * (c.y - a.y) - (a.y - b.y) * (c.x - a.x)))
    val yofcenter = ((a.x - b.x) * ((c.x * c.x) + (c.y * c.y)) + (b.x - c.x) * ((a.x * a.x) + (a.y * a.y)) + (c.x - a.x)
            * ((b.y * b.y) + (b.x * b.x))) / (2 * ((a.x - b.x) * (c.y - a.y) - (a.y - b.y) * (c.x - a.x)))
    val centerof = Point(xofcenter, yofcenter)
    val radiusof = sqrt(sqr(xofcenter - a.x) + sqr(yofcenter - a.y))
    val circle = Circle(centerof, radiusof)
    return circle
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
fun minContainingCircle(vararg points: Point): Circle {
    if (points.size == 0) throw IllegalArgumentException()
    if (points.size == 1) return Circle(points[0], 0.0)
    if (points.size == 2) return circleByDiameter(Segment(points[0], points[1]))
    val segment = diameter(*points)
    val max1 = segment.begin
    val max2 = segment.end
    val p = Point((Math.min(max1.x, max2.x) + Math.abs(max1.x - max2.x) / 2), Math.min(max1.y, max2.y)
            + Math.abs(max1.y - max2.y) / 2)
    val radius = max1.distance(p)
    var nmax = radius
    var elementMax = max1
    for (point in points) {
        if (p.distance(point) > nmax) {
            nmax = p.distance(point)
            elementMax = point
        }
    }
    if (Math.abs(elementMax.distance(p) - radius) <= 1e-12) return Circle(p, nmax)
    else return circleByThreePoints(max1, max2, elementMax)
}
