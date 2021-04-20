import magnolia.{CaseClass, Magnolia, SealedTrait}

import java.lang.System.lineSeparator
import scala.language.experimental.macros

trait Writer[T] {
  def write(obj: T): String
}

object Writer extends DefaultInstances {

  type Typeclass[T] = Writer[T]

  def combine[T](ctx: CaseClass[Typeclass, T]): Typeclass[T] = new Typeclass[T] {
    override def write(obj: T): String = {
      val params = ctx.parameters.map { param =>
        param.label+"="+param.typeclass.write(param.dereference(obj))
      }
      ctx.typeName.short + params.mkString("(" + lineSeparator, "," + lineSeparator, lineSeparator + ")")
    }
  }

  def dispatch[T](ctx: SealedTrait[Typeclass, T]): Typeclass[T] = ???

  implicit def gen[T]: Typeclass[T] = macro Magnolia.gen[T]
}

trait DefaultInstances {
  implicit def stringWriter: Writer[String] = str => "\"" + str + "\""

  implicit def setWriter[T: Writer]: Writer[Set[T]] = (set: Set[T]) => {
    val tWriter = implicitly[Writer[T]]
    set.map(tWriter.write).mkString("Set(" + lineSeparator, "," + lineSeparator, lineSeparator + ")")
  }
}
