sealed trait UserField
object UserField {
  case object userId extends UserField
  case object email extends UserField
  case object password extends UserField

  def Values: Seq[UserField] = Seq(userId, email, password)
}