object Main extends App {

  val workflow = Workflow("superId", Set(Action("action1"), Action("action2"), Action("action3")))

  println(implicitly[Writer[Workflow]].write(workflow))

}
