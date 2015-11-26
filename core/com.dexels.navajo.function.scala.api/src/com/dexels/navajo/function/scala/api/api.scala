package com.dexels.navajo.function.scala.api

import com.dexels.navajo.scala.document._

import com.dexels.navajo.scala._

trait FunctionComponent extends com.dexels.navajo.scala.BaseAdapters with com.dexels.navajo.scala.Base { self: com.dexels.navajo.scala.ScalaScript => 
  def Abs(arg0: java.lang.Double): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Abs)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[Any]
  }
  def Abs(arg0: java.lang.Integer): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Abs)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[Any]
  }
  def Age(arg0: java.util.Date, arg1: java.util.Date): java.lang.Integer = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Age)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.lang.Integer]
  }
  def Age(arg0: java.util.Date): java.lang.Integer = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Age)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Integer]
  }
  def AppendArray(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.AppendArray)
    function.evaluate().asInstanceOf[Any]
  }
  def ArraySelection(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ArraySelection)
    function.evaluate().asInstanceOf[Any]
  }
  def Base64Encode(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Base64Encode)
    function.evaluate().asInstanceOf[Any]
  }
  def BasicAuthenticationHeader(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.BasicAuthenticationHeader)
    function.evaluate().asInstanceOf[Any]
  }
  def CallService(arg0: java.lang.String, arg1: java.lang.String): java.lang.Object = {
    val function = this.setupFunction(new com.dexels.navajo.functions.CallService)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.lang.Object]
  }
  def CallService(arg0: java.lang.String): java.lang.Object = {
    val function = this.setupFunction(new com.dexels.navajo.functions.CallService)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Object]
  }
  def CheckDate(arg0: java.lang.Object): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.CheckDate)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def CheckEmail(arg0: java.lang.String): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.CheckEmail)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def CheckFloat(arg0: java.lang.Object): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.CheckFloat)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def CheckInteger(arg0: java.lang.Object): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.CheckInteger)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def CheckRange(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.CheckRange)
    function.evaluate().asInstanceOf[Any]
  }
  def CheckUniqueness(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.CheckUniqueness)
    function.evaluate().asInstanceOf[Any]
  }
  def CheckUrl(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.CheckUrl)
    function.evaluate().asInstanceOf[Any]
  }
  def Contains(arg0: Any, arg1: java.lang.Object): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Contains)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def CreateExpression(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.CreateExpression)
    function.evaluate().asInstanceOf[Any]
  }
  def CurrentTimeMillis(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.CurrentTimeMillis)
    function.evaluate().asInstanceOf[Any]
  }
  def Date(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Date)
    function.evaluate().asInstanceOf[Any]
  }
  def DateAdd(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.DateAdd)
    function.evaluate().asInstanceOf[Any]
  }
  def DateField(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.DateField)
    function.evaluate().asInstanceOf[Any]
  }
  def DateSubstract(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.DateSubstract)
    function.evaluate().asInstanceOf[Any]
  }
  def DayOfWeek(arg0: java.lang.Integer, arg1: java.lang.Boolean): java.util.Date = {
    val function = this.setupFunction(new com.dexels.navajo.functions.DayOfWeek)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.util.Date]
  }
  def DecimalChar(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.DecimalChar)
    function.evaluate().asInstanceOf[Any]
  }
  def DecryptString(arg0: java.lang.String, arg1: java.lang.String): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.DecryptString)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def EmptyBinary(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.EmptyBinary)
    function.evaluate().asInstanceOf[Any]
  }
  def EncryptString(arg0: java.lang.String, arg1: java.lang.String): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.EncryptString)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def EqualsIgnoreCase(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.EqualsIgnoreCase)
    function.evaluate().asInstanceOf[Any]
  }
  def EqualsPattern(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.EqualsPattern)
    function.evaluate().asInstanceOf[Any]
  }
  def Euro(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Euro)
    function.evaluate().asInstanceOf[Any]
  }
  def EvaluateExpression(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.EvaluateExpression)
    function.evaluate().asInstanceOf[Any]
  }
  def EvaluateParameters(arg0: java.lang.String): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.EvaluateParameters)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def Exists(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Exists)
    function.evaluate().asInstanceOf[Any]
  }
  def ExistsProperty(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ExistsProperty)
    function.evaluate().asInstanceOf[Any]
  }
  def ExistsSelectionValue(arg0: com.dexels.navajo.document.Property, arg1: java.lang.String): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ExistsSelectionValue)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def File(arg0: java.lang.String): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.File)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[Any]
  }
  def FileExists(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.FileExists)
    function.evaluate().asInstanceOf[Any]
  }
  def FileSize(arg0: com.dexels.navajo.document.types.Binary): java.lang.Integer = {
    val function = this.setupFunction(new com.dexels.navajo.functions.FileSize)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Integer]
  }
  def FileString(arg0: java.lang.String): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.FileString)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def FindElement(arg0: java.lang.String, arg1: java.lang.Object, arg2: com.dexels.navajo.document.Message): com.dexels.navajo.document.Message = {
    val function = this.setupFunction(new com.dexels.navajo.functions.FindElement)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.insertOperand(arg2)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.Message]
  }
  def FindElement(arg0: java.lang.String, arg1: java.lang.Object): com.dexels.navajo.document.Message = {
    val function = this.setupFunction(new com.dexels.navajo.functions.FindElement)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.Message]
  }
  def ForAll(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ForAll)
    function.evaluate().asInstanceOf[Any]
  }
  def FormatDate(arg0: java.util.Date, arg1: java.lang.String, arg2: java.lang.String): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.FormatDate)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.insertOperand(arg2)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def FormatDate(arg0: java.util.Date, arg1: java.lang.String): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.FormatDate)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def FormatDate(arg0: com.dexels.navajo.document.types.ClockTime, arg1: java.lang.String, arg2: java.lang.String): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.FormatDate)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.insertOperand(arg2)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def FormatDate(arg0: com.dexels.navajo.document.types.ClockTime, arg1: java.lang.String): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.FormatDate)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def FormatDecimal(arg0: java.lang.Object, arg1: java.lang.String): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.FormatDecimal)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def FormatStringList(arg0: Any, arg1: java.lang.String): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.FormatStringList)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def FormatStringList(arg0: java.lang.String, arg1: java.lang.String): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.FormatStringList)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def FormatZipCode(arg0: java.lang.String): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.FormatZipCode)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def GetBinary(arg0: java.lang.String): com.dexels.navajo.document.types.Binary = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetBinary)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.types.Binary]
  }
  def GetBinaryPath(arg0: com.dexels.navajo.document.types.Binary): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetBinaryPath)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def GetCents(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetCents)
    function.evaluate().asInstanceOf[Any]
  }
  def GetContextResource(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetContextResource)
    function.evaluate().asInstanceOf[Any]
  }
  def GetCurrentMessage(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetCurrentMessage)
    function.evaluate().asInstanceOf[Any]
  }
  def GetDescription(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetDescription)
    function.evaluate().asInstanceOf[Any]
  }
  def GetFileExtension(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetFileExtension)
    function.evaluate().asInstanceOf[Any]
  }
  def GetInitials(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetInitials)
    function.evaluate().asInstanceOf[Any]
  }
  def GetHeader(arg0: java.lang.String): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetHeader)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def GetLogoImage(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetLogoImage)
    function.evaluate().asInstanceOf[Any]
  }
  def GetMessage(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetMessage)
    function.evaluate().asInstanceOf[Any]
  }
  def GetMimeType(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetMimeType)
    function.evaluate().asInstanceOf[Any]
  }
  def GetProperty(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetProperty)
    function.evaluate().asInstanceOf[Any]
  }
  def GetPropertyAttribute(arg0: java.lang.String, arg1: java.lang.String): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetPropertyAttribute)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def GetPropertyDirection(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetPropertyDirection)
    function.evaluate().asInstanceOf[Any]
  }
  def GetPropertySubType(arg0: com.dexels.navajo.document.Property, arg1: java.lang.String): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetPropertySubType)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def GetPropertySubType(arg0: java.lang.String, arg1: java.lang.String): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetPropertySubType)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def GetPropertyType(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetPropertyType)
    function.evaluate().asInstanceOf[Any]
  }
  def GetPropertyValue(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetPropertyValue)
    function.evaluate().asInstanceOf[Any]
  }
  def GetReportFile(arg0: java.lang.String): com.dexels.navajo.document.types.Binary = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetReportFile)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.types.Binary]
  }
  def GetRequest(): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetRequest)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def GetSelectedName(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetSelectedName)
    function.evaluate().asInstanceOf[Any]
  }
  def GetSelectedValue(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetSelectedValue)
    function.evaluate().asInstanceOf[Any]
  }
  def GetUrlMimeType(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetUrlMimeType)
    function.evaluate().asInstanceOf[Any]
  }
  def GetUrlModificationTime(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetUrlModificationTime)
    function.evaluate().asInstanceOf[Any]
  }
  def GetUrlSize(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetUrlSize)
    function.evaluate().asInstanceOf[Any]
  }
  def GetUrlTime(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetUrlTime)
    function.evaluate().asInstanceOf[Any]
  }
  def GetUTF8Length(arg0: java.lang.String): java.lang.Integer = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetUTF8Length)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Integer]
  }
  def GetValue(): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetValue)
    function.evaluate().asInstanceOf[Any]
  }
  def GetVersionInfo(arg0: java.lang.String): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetVersionInfo)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def GetWeekDayDate(arg0: java.lang.Integer, arg1: java.lang.Boolean, arg2: java.util.Date): java.util.Date = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetWeekDayDate)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.insertOperand(arg2)
    function.evaluate().asInstanceOf[java.util.Date]
  }
  def GetWeekDayDate(arg0: java.lang.Integer, arg1: java.lang.Boolean): java.util.Date = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetWeekDayDate)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.util.Date]
  }
  def GetWeekDayDate(arg0: java.lang.Integer, arg1: java.lang.String, arg2: java.util.Date): java.util.Date = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetWeekDayDate)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.insertOperand(arg2)
    function.evaluate().asInstanceOf[java.util.Date]
  }
  def GetWeekDayDate(arg0: java.lang.Integer, arg1: java.lang.String): java.util.Date = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetWeekDayDate)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.util.Date]
  }
  def GetWeekDayDate(arg0: java.lang.String, arg1: java.lang.Boolean, arg2: java.util.Date): java.util.Date = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetWeekDayDate)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.insertOperand(arg2)
    function.evaluate().asInstanceOf[java.util.Date]
  }
  def GetWeekDayDate(arg0: java.lang.String, arg1: java.lang.Boolean): java.util.Date = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetWeekDayDate)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.util.Date]
  }
  def GetWeekDayDate(arg0: java.lang.String, arg1: java.lang.String, arg2: java.util.Date): java.util.Date = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetWeekDayDate)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.insertOperand(arg2)
    function.evaluate().asInstanceOf[java.util.Date]
  }
  def GetWeekDayDate(arg0: java.lang.String, arg1: java.lang.String): java.util.Date = {
    val function = this.setupFunction(new com.dexels.navajo.functions.GetWeekDayDate)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.util.Date]
  }
  def IfElse(arg0: java.lang.Boolean, arg1: java.lang.Object, arg2: java.lang.Object): java.lang.Object = {
    val function = this.setupFunction(new com.dexels.navajo.functions.IfElse)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.insertOperand(arg2)
    function.evaluate().asInstanceOf[java.lang.Object]
  }
  def InMonthTurnInterval(arg0: java.util.Date, arg1: java.lang.Integer, arg2: java.lang.Boolean): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.InMonthTurnInterval)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.insertOperand(arg2)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def IsArrayMessage(arg0: java.lang.String): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.IsArrayMessage)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def IsDate(arg0: java.util.Date): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.IsDate)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def IsDate(arg0: java.lang.String): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.IsDate)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def IsEmpty(arg0: java.lang.String): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.IsEmpty)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def IsEmpty(arg0: com.dexels.navajo.document.types.Binary): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.IsEmpty)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def IsEmpty(arg0: Any): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.IsEmpty)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def IsEmpty(arg0: com.dexels.navajo.document.Message): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.IsEmpty)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def IsEmpty(arg0: java.lang.Integer): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.IsEmpty)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def IsEmpty(arg0: java.lang.Boolean): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.IsEmpty)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def IsEmpty(arg0: java.util.Date): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.IsEmpty)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def IsEmpty(arg0: com.dexels.navajo.document.types.ClockTime): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.IsEmpty)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def IsEmpty(arg0: com.dexels.navajo.document.types.Memo): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.IsEmpty)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def IsEmpty(arg0: com.dexels.navajo.document.types.Money): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.IsEmpty)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def IsEmpty(arg0: com.dexels.navajo.document.types.Percentage): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.IsEmpty)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def IsEmpty(arg0: com.dexels.navajo.document.types.StopwatchTime): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.IsEmpty)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def IsEmpty(arg0: java.lang.Double): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.IsEmpty)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def IsEmpty(arg0: com.dexels.navajo.document.Selection): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.IsEmpty)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def IsFutureDate(arg0: java.util.Date): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.IsFutureDate)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def IsFutureDate(arg0: java.lang.String): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.IsFutureDate)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def IsNull(arg0: java.lang.Object): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.IsNull)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def IsNumeric(arg0: java.lang.String): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.IsNumeric)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def IsNumeric(arg0: java.lang.Integer): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.IsNumeric)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def IsNumeric(arg0: java.lang.Double): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.IsNumeric)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def IsServiceCached(arg0: java.lang.String, arg1: java.lang.String): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.IsServiceCached)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def KeyValueMap(arg0: java.lang.String, arg1: java.lang.String, arg2: java.lang.String): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.KeyValueMap)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.insertOperand(arg2)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def Max(arg0: java.lang.Integer, arg1: java.lang.Integer): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Max)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[Any]
  }
  def Max(arg0: java.lang.Integer, arg1: java.lang.Double): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Max)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[Any]
  }
  def Max(arg0: java.lang.Double, arg1: java.lang.Integer): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Max)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[Any]
  }
  def Max(arg0: java.lang.Double, arg1: java.lang.Double): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Max)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[Any]
  }
  def MD5Sum(arg0: java.lang.Object): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.MD5Sum)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def MergeNavajo(arg0: com.dexels.navajo.document.Navajo, arg1: com.dexels.navajo.document.Navajo): com.dexels.navajo.document.Navajo = {
    val function = this.setupFunction(new com.dexels.navajo.functions.MergeNavajo)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.Navajo]
  }
  def Min(arg0: java.lang.Integer, arg1: java.lang.Integer): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Min)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[Any]
  }
  def Min(arg0: java.lang.Integer, arg1: java.lang.Double): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Min)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[Any]
  }
  def Min(arg0: java.lang.Double, arg1: java.lang.Integer): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Min)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[Any]
  }
  def Min(arg0: java.lang.Double, arg1: java.lang.Double): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Min)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[Any]
  }
  def NavajoRequestToString(): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.NavajoRequestToString)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def NextMonth(arg0: java.lang.Integer): java.util.Date = {
    val function = this.setupFunction(new com.dexels.navajo.functions.NextMonth)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.util.Date]
  }
  def Now(): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Now)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def OffsetDate(arg0: java.util.Date, arg1: java.lang.Integer, arg2: java.lang.Integer, arg3: java.lang.Integer, arg4: java.lang.Integer, arg5: java.lang.Integer, arg6: java.lang.Integer): java.util.Date = {
    val function = this.setupFunction(new com.dexels.navajo.functions.OffsetDate)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.insertOperand(arg2)
    function.insertOperand(arg3)
    function.insertOperand(arg4)
    function.insertOperand(arg5)
    function.insertOperand(arg6)
    function.evaluate().asInstanceOf[java.util.Date]
  }
  def ParameterList(arg0: java.lang.Integer): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ParameterList)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def ParseDate(arg0: java.util.Date, arg1: java.lang.String): java.util.Date = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ParseDate)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.util.Date]
  }
  def ParseDate(arg0: java.util.Date): java.util.Date = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ParseDate)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.util.Date]
  }
  def ParseDate(arg0: java.lang.String, arg1: java.lang.String): java.util.Date = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ParseDate)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.util.Date]
  }
  def ParseDate(arg0: java.lang.String): java.util.Date = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ParseDate)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.util.Date]
  }
  def ParseSelection(arg0: java.lang.String): java.lang.Object = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ParseSelection)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Object]
  }
  def ParseStringList(arg0: java.lang.String, arg1: java.lang.String): List[Any] = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ParseStringList)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[List[Any]]
  }
  def Random(): java.lang.Integer = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Random)
    function.evaluate().asInstanceOf[java.lang.Integer]
  }
  def RandomColor(arg0: java.lang.Integer): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.RandomColor)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def RandomColor(): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.RandomColor)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def RandomInt(arg0: java.lang.Integer, arg1: java.lang.Integer): java.lang.Integer = {
    val function = this.setupFunction(new com.dexels.navajo.functions.RandomInt)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.lang.Integer]
  }
  def RandomString(arg0: java.lang.Integer, arg1: java.lang.String): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.RandomString)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def RandomString(arg0: java.lang.Integer): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.RandomString)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def Round(arg0: java.lang.Double, arg1: java.lang.Integer): java.lang.Double = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Round)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.lang.Double]
  }
  def ScaleImageCentered(arg0: com.dexels.navajo.document.types.Binary, arg1: java.lang.Integer, arg2: java.lang.Integer): com.dexels.navajo.document.types.Binary = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ScaleImageCentered)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.insertOperand(arg2)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.types.Binary]
  }
  def ScaleImageCropped(arg0: com.dexels.navajo.document.types.Binary, arg1: java.lang.Integer, arg2: java.lang.Integer): com.dexels.navajo.document.types.Binary = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ScaleImageCropped)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.insertOperand(arg2)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.types.Binary]
  }
  def ScaleImageFree(arg0: com.dexels.navajo.document.types.Binary, arg1: java.lang.Integer, arg2: java.lang.Integer): com.dexels.navajo.document.types.Binary = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ScaleImageFree)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.insertOperand(arg2)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.types.Binary]
  }
  def ScaleImageMax(arg0: com.dexels.navajo.document.types.Binary, arg1: java.lang.Integer, arg2: java.lang.Integer): com.dexels.navajo.document.types.Binary = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ScaleImageMax)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.insertOperand(arg2)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.types.Binary]
  }
  def ScaleImageMin(arg0: com.dexels.navajo.document.types.Binary, arg1: java.lang.Integer, arg2: java.lang.Integer): com.dexels.navajo.document.types.Binary = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ScaleImageMin)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.insertOperand(arg2)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.types.Binary]
  }
  def SetAllProperties(arg0: com.dexels.navajo.document.Message, arg1: java.lang.String, arg2: java.lang.Object): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.SetAllProperties)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.insertOperand(arg2)
    function.evaluate().asInstanceOf[Any]
  }
  def SetMimeType(arg0: com.dexels.navajo.document.types.Binary, arg1: java.lang.String): com.dexels.navajo.document.types.Binary = {
    val function = this.setupFunction(new com.dexels.navajo.functions.SetMimeType)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.types.Binary]
  }
  def Size(arg0: java.lang.String): java.lang.Integer = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Size)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Integer]
  }
  def Size(arg0: com.dexels.navajo.document.types.Binary): java.lang.Integer = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Size)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Integer]
  }
  def Size(arg0: Any): java.lang.Integer = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Size)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Integer]
  }
  def Size(arg0: com.dexels.navajo.document.Message): java.lang.Integer = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Size)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Integer]
  }
  def StoreBinary(arg0: java.lang.String, arg1: com.dexels.navajo.document.types.Binary): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.StoreBinary)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def StringDistance(arg0: java.lang.String, arg1: java.lang.String): java.lang.Integer = {
    val function = this.setupFunction(new com.dexels.navajo.functions.StringDistance)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.lang.Integer]
  }
  def StringField(arg0: java.lang.String, arg1: java.lang.String, arg2: java.lang.Integer): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.StringField)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.insertOperand(arg2)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def StringFunction(arg0: java.lang.String, arg1: java.lang.String, arg2: Any*): java.lang.Object = {
    val function = this.setupFunction(new com.dexels.navajo.functions.StringFunction)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    arg2.foreach(x => function.insertOperand(x))
    function.evaluate().asInstanceOf[java.lang.Object]
  }
  def StringPadding(arg0: java.lang.String, arg1: java.lang.Integer, arg2: java.lang.String, arg3: java.lang.Boolean): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.StringPadding)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.insertOperand(arg2)
    function.insertOperand(arg3)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def StringPadding(arg0: java.lang.String, arg1: java.lang.Integer, arg2: java.lang.String): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.StringPadding)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.insertOperand(arg2)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def StringPadding(arg0: java.lang.String, arg1: java.lang.Integer): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.StringPadding)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def StripTime(arg0: java.util.Date, arg1: java.lang.String): java.util.Date = {
    val function = this.setupFunction(new com.dexels.navajo.functions.StripTime)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.util.Date]
  }
  def StripTime(arg0: java.util.Date): java.util.Date = {
    val function = this.setupFunction(new com.dexels.navajo.functions.StripTime)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.util.Date]
  }
  def StripTime(arg0: java.lang.String, arg1: java.lang.String): java.util.Date = {
    val function = this.setupFunction(new com.dexels.navajo.functions.StripTime)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.util.Date]
  }
  def StripTime(arg0: java.lang.String): java.util.Date = {
    val function = this.setupFunction(new com.dexels.navajo.functions.StripTime)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.util.Date]
  }
  def Sum(arg0: Any): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Sum)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[Any]
  }
  def SumExpressions(arg0: java.lang.String, arg1: java.lang.String, arg2: java.lang.String): java.lang.Object = {
    val function = this.setupFunction(new com.dexels.navajo.functions.SumExpressions)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.insertOperand(arg2)
    function.evaluate().asInstanceOf[java.lang.Object]
  }
  def SumExpressions(arg0: java.lang.String, arg1: java.lang.String): java.lang.Object = {
    val function = this.setupFunction(new com.dexels.navajo.functions.SumExpressions)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.lang.Object]
  }
  def SumMessage(arg0: com.dexels.navajo.document.Message): java.lang.Object = {
    val function = this.setupFunction(new com.dexels.navajo.functions.SumMessage)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Object]
  }
  def SumMessage(arg0: java.lang.String): java.lang.Object = {
    val function = this.setupFunction(new com.dexels.navajo.functions.SumMessage)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Object]
  }
  def SumProperties(arg0: java.lang.String, arg1: java.lang.String, arg2: java.lang.String): java.lang.Object = {
    val function = this.setupFunction(new com.dexels.navajo.functions.SumProperties)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.insertOperand(arg2)
    function.evaluate().asInstanceOf[java.lang.Object]
  }
  def SumProperties(arg0: java.lang.String, arg1: java.lang.String): java.lang.Object = {
    val function = this.setupFunction(new com.dexels.navajo.functions.SumProperties)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.lang.Object]
  }
  def Switch(arg0: java.lang.Object, arg1: java.lang.Object, arg2: java.lang.Object, arg3: java.lang.Object, arg4: Any*): java.lang.Object = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Switch)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.insertOperand(arg2)
    function.insertOperand(arg3)
    arg4.foreach(x => function.insertOperand(x))
    function.evaluate().asInstanceOf[java.lang.Object]
  }
  def Sysdate(): java.util.Date = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Sysdate)
    function.evaluate().asInstanceOf[java.util.Date]
  }
  def ToBinary(arg0: java.lang.String): com.dexels.navajo.document.types.Binary = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ToBinary)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.types.Binary]
  }
  def ToBinary(arg0: com.dexels.navajo.document.types.Binary): com.dexels.navajo.document.types.Binary = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ToBinary)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.types.Binary]
  }
  def ToBinaryFromPath(arg0: java.lang.String): com.dexels.navajo.document.types.Binary = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ToBinaryFromPath)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.types.Binary]
  }
  def ToBinaryFromUrl(arg0: java.lang.String): com.dexels.navajo.document.types.Binary = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ToBinaryFromUrl)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.types.Binary]
  }
  def ToClockTime(arg0: java.util.Date): com.dexels.navajo.document.types.ClockTime = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ToClockTime)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.types.ClockTime]
  }
  def ToClockTime(arg0: java.lang.String): com.dexels.navajo.document.types.ClockTime = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ToClockTime)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.types.ClockTime]
  }
  def ToClockTime(arg0: com.dexels.navajo.document.types.ClockTime): com.dexels.navajo.document.types.ClockTime = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ToClockTime)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.types.ClockTime]
  }
  def Today(): java.util.Date = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Today)
    function.evaluate().asInstanceOf[java.util.Date]
  }
  def ToDouble(arg0: java.lang.Object): java.lang.Double = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ToDouble)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Double]
  }
  def ToInteger(arg0: java.lang.Object): java.lang.Integer = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ToInteger)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Integer]
  }
  def ToLong(arg0: java.lang.Object): java.lang.Long = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ToLong)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Long]
  }
  def ToLower(arg0: java.lang.String): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ToLower)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def ToMemo(arg0: java.lang.String): com.dexels.navajo.document.types.Memo = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ToMemo)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.types.Memo]
  }
  def ToMilliseconds(arg0: com.dexels.navajo.document.types.StopwatchTime): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ToMilliseconds)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[Any]
  }
  def ToMilliseconds(arg0: com.dexels.navajo.document.types.ClockTime): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ToMilliseconds)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[Any]
  }
  def ToMoney(arg0: java.lang.String): com.dexels.navajo.document.types.Money = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ToMoney)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.types.Money]
  }
  def ToMoney(arg0: java.lang.Double): com.dexels.navajo.document.types.Money = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ToMoney)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.types.Money]
  }
  def ToMoney(arg0: java.lang.Integer): com.dexels.navajo.document.types.Money = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ToMoney)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.types.Money]
  }
  def ToMoney(arg0: com.dexels.navajo.document.types.Money): com.dexels.navajo.document.types.Money = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ToMoney)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.types.Money]
  }
  def ToMoneyInternal(arg0: com.dexels.navajo.document.types.Money): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ToMoneyInternal)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def ToPercentage(arg0: java.lang.String): com.dexels.navajo.document.types.Percentage = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ToPercentage)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.types.Percentage]
  }
  def ToPercentage(arg0: java.lang.Double): com.dexels.navajo.document.types.Percentage = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ToPercentage)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.types.Percentage]
  }
  def ToPercentage(arg0: java.lang.Integer): com.dexels.navajo.document.types.Percentage = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ToPercentage)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.types.Percentage]
  }
  def ToSecureImage(arg0: java.lang.String): com.dexels.navajo.document.types.Binary = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ToSecureImage)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.types.Binary]
  }
  def ToStopwatchTime(arg0: java.lang.Integer): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ToStopwatchTime)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[Any]
  }
  def ToStopwatchTime(arg0: java.util.Date): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ToStopwatchTime)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[Any]
  }
  def ToString(arg0: java.lang.Object): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ToString)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def ToUpper(arg0: java.lang.String): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ToUpper)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def Trim(arg0: java.lang.String): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Trim)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def Trunc(arg0: java.util.Date): java.util.Date = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Trunc)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.util.Date]
  }
  def Unicode(arg0: java.lang.String): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Unicode)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def URLEncode(arg0: java.lang.String): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.URLEncode)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def ValidatePhoneNumber(arg0: java.lang.String): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ValidatePhoneNumber)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def ValidatePhoneNumber(arg0: java.lang.Long): java.lang.Boolean = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ValidatePhoneNumber)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Boolean]
  }
  def Wait(arg0: java.lang.Integer): Any = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Wait)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[Any]
  }
  def WeekDay(arg0: java.util.Date): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.WeekDay)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def WeekDay(): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.WeekDay)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def XmlEscape(arg0: java.lang.String): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.XmlEscape)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def XmlUnescape(arg0: java.lang.String): java.lang.String = {
    val function = this.setupFunction(new com.dexels.navajo.functions.XmlUnescape)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.String]
  }
  def Zip(arg0: com.dexels.navajo.document.types.Binary, arg1: java.lang.String): com.dexels.navajo.document.types.Binary = {
    val function = this.setupFunction(new com.dexels.navajo.functions.Zip)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.types.Binary]
  }
  def ZipArchive(arg0: java.lang.String): com.dexels.navajo.document.types.Binary = {
    val function = this.setupFunction(new com.dexels.navajo.functions.ZipArchive)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.types.Binary]
  }
}