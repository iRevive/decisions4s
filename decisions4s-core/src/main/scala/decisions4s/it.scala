package decisions4s

import decisions4s.exprs.*

import scala.annotation.targetName

// Syntax for defining predicates
object it {

  @targetName("equalsTo")
  def ===[T](value: T)(using LiteralShow[T]): MatchingExpr[T] = Equal(Literal(value))

  def equalsAnyOf[T](values: T*)(using LiteralShow[T]): MatchingExpr[T] = Or(values.map(v => Equal(Literal(v))))

  @targetName("greaterThan")
  def >[T](value: T)(using LiteralShow[T], Ordering[T]): MatchingExpr[T] = GreaterThan(Literal(value))

  @targetName("lessThan")
  def <[T](value: T)(using LiteralShow[T], Ordering[T]): MatchingExpr[T] = LessThan(Literal(value))

  def catchAll[T]: MatchingExpr[T] = True
}
