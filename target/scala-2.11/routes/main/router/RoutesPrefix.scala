
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/dohee/Source/IntelliJ/BA/traffic-model-case-study-editor/conf/routes
// @DATE:Mon Feb 29 10:08:38 CET 2016


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
