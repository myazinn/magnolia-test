object Main extends App {

  def fetchUserFields[T: ToUserFields]: Seq[UserField] = implicitly[ToUserFields[T]].fields
  def createUrl[T: ToUserFields](id: String): String = {
    val fieldsToFetch = fetchUserFields[T]

    s"url/to/user/$id?fields=${fieldsToFetch.mkString(",")}"
  }

  println(createUrl[User]("userId")) // url/to/user/userId?fields=userId,password,email
  println(createUrl[UserWithoutPassword]("userIdWithoutPassword")) // url/to/user/userIdWithoutPassword?fields=userId,email

}
