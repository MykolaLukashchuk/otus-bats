package me.chuwy.otusbats


trait Show[A] {
  def show(a: A): String
}

object Show {

  // 1.1 Instances (`Int`, `String`, `Boolean`)
  implicit def intShow(implicit ev: Show[Int]): Show[Int] = (a: Int) => a.toString

  implicit def stringShow(implicit ev: Show[String]): Show[String] = (a: String) => a
  implicit def booleanShow(implicit ev: Show[Boolean]): Show[Boolean] = (a: Boolean) => a.toString

  // 1.2 Instances with conditional implicit

  implicit def listShow[A](implicit ev: Show[A]): Show[List[A]] = (a: List[A]) => a.toString
  implicit def setShow[A](implicit ev: Show[A]): Show[Set[A]] = (a: Set[A]) => a.toString


  // 2. Summoner (apply)

  implicit def apply(implicit ev: Show[Int]): Show[Int] = ev

  // 3. Syntax extensions

  implicit class ShowOps[A](a: A) {
    def show(implicit ev: Show[A]): String =
      ev.show(a)

    /** Transform list of `A` into `String` with custom separator, beginning and ending.
     *  For example: "[a, b, c]" from `List("a", "b", "c")`
     *
     *  @param separator. ',' in above example
     *  @param begin. '[' in above example
     *  @param end. ']' in above example
     */
    def mkString_(list: List[A], separator: String, begin: String, end: String)(implicit ev: Show[A]): String =
      list.mkString(begin, separator, end)

  }

  // 4. Helper constructors

  /** Just use JVM `toString` implementation, available on every object */
  def fromJvm[A]: Show[A] = (a: A) => a.toString
  
  /** Provide a custom function to avoid `new Show { ... }` machinery */
  def fromFunction[A](f: A => String): Show[A] = (a: A) => f(a)

  def main(args: Array[String]): Unit = {

  }
}
