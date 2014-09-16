import play.api._
import play.api.mvc._
import play.filters.csrf._

/**
 * Created by mastakeu on 2014/09/16.
 */
object Global extends WithFilters(CSRFFilter()) with GlobalSettings {
}
