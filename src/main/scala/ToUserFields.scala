import magnolia._
import scala.language.experimental.macros

trait ToUserFields[T] {
  def fields: Seq[UserField]
}

object ToUserFields extends LowPriorityToUserFields {

  type Typeclass[T] = ToUserFields[T]

  def combine[T](ctx: CaseClass[Typeclass, T]): Typeclass[T] = new Typeclass[T] {
    override def fields: Seq[UserField] =
      ctx.parameters
        .map(_.label)
        .flatMap(label => UserField.Values.find(_.toString == label))
  }

  def dispatch[T](ctx: SealedTrait[Typeclass, T]): Typeclass[T] = ???

  implicit def gen[T]: Typeclass[T] = macro Magnolia.gen[T]
}

trait LowPriorityToUserFields {
  implicit def fallback[T]: ToUserFields[T] = new ToUserFields[T] {
    override def fields: Seq[UserField] = Seq.empty
  }
}