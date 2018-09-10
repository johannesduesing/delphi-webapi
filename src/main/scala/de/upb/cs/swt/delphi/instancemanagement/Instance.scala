package de.upb.cs.swt.delphi.instancemanagement

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, JsString, JsValue, JsonFormat}

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val componentTypeFormat = new JsonFormat[InstanceEnums.ComponentType] {
    def write(compType : InstanceEnums.ComponentType) = JsString(compType.toString)

    def read(value: JsValue) = value match {
      case JsString(s) => s match {
        case "Crawler" => InstanceEnums.ComponentType.Crawler
        case "WebApi" => InstanceEnums.ComponentType.WebApi
        case "WebApp" => InstanceEnums.ComponentType.WebApp
        case "DelphiManagement" => InstanceEnums.ComponentType.DelphiManagement
        case "ElasticSearch" => InstanceEnums.ComponentType.ElasticSearch
        case x => throw new RuntimeException(s"Unexpected string value $x for component type.")
      }
      case y => throw new RuntimeException(s"Unexpected type $y while deserializing component type.")
    }
  }
  implicit val instanceFormat = jsonFormat5(Instance)
}

final case class Instance (
                            iD: Option[Long],
                            host: String,
                            portnumber: Int,
                            name: String,
                            /* Component Type */
                            componentType: InstanceEnums.ComponentType
                          )

object InstanceEnums {

  type ComponentType = ComponentType.Value
  object ComponentType extends Enumeration {
    val Crawler = Value("Crawler")
    val WebApi = Value("WebApi")
    val WebApp = Value("WebApp")
    val DelphiManagement = Value("DelphiManagement")
    val ElasticSearch = Value("ElasticSearch")
  }

}