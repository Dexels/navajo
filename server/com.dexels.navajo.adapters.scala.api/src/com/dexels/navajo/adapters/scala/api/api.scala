package com.dexels.navajo.adapters.scala.api

import com.dexels.navajo.scala.document._

import com.dexels.navajo.scala._

trait AdaptersComponent extends com.dexels.navajo.scala.BaseAdapters with com.dexels.navajo.scala.Base { self: com.dexels.navajo.scala.ScalaScript => 
  def GetSequenceValue(arg0: java.lang.Integer, arg1: java.lang.String): java.lang.Integer = {
    val function = this.setupFunction(new com.dexels.navajo.adapter.functions.GetSequenceValue)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.lang.Integer]
  }
  def GetSequenceValue(arg0: java.lang.Integer): java.lang.Integer = {
    val function = this.setupFunction(new com.dexels.navajo.adapter.functions.GetSequenceValue)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Integer]
  }
  def GetSequenceValue(arg0: java.lang.String, arg1: java.lang.String): java.lang.Integer = {
    val function = this.setupFunction(new com.dexels.navajo.adapter.functions.GetSequenceValue)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[java.lang.Integer]
  }
  def GetSequenceValue(arg0: java.lang.String): java.lang.Integer = {
    val function = this.setupFunction(new com.dexels.navajo.adapter.functions.GetSequenceValue)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[java.lang.Integer]
  }
  def GetSequenceValue(): java.lang.Integer = {
    val function = this.setupFunction(new com.dexels.navajo.adapter.functions.GetSequenceValue)
    function.evaluate().asInstanceOf[java.lang.Integer]
  }
  def MultipleValueQuery(arg0: java.lang.Integer, arg1: Any*): List[Any] = {
    val function = this.setupFunction(new com.dexels.navajo.adapter.functions.MultipleValueQuery)
    function.insertOperand(arg0)
    arg1.foreach(x => function.insertOperand(x))
    function.evaluate().asInstanceOf[List[Any]]
  }
  def MultipleValueQuery(arg0: java.lang.String, arg1: Any*): List[Any] = {
    val function = this.setupFunction(new com.dexels.navajo.adapter.functions.MultipleValueQuery)
    function.insertOperand(arg0)
    arg1.foreach(x => function.insertOperand(x))
    function.evaluate().asInstanceOf[List[Any]]
  }
  def MultipleValueQuery(): List[Any] = {
    val function = this.setupFunction(new com.dexels.navajo.adapter.functions.MultipleValueQuery)
    function.evaluate().asInstanceOf[List[Any]]
  }
  def SingleValueQuery(arg0: java.lang.Integer, arg1: Any*): java.lang.Object = {
    val function = this.setupFunction(new com.dexels.navajo.adapter.functions.SingleValueQuery)
    function.insertOperand(arg0)
    arg1.foreach(x => function.insertOperand(x))
    function.evaluate().asInstanceOf[java.lang.Object]
  }
  def SingleValueQuery(arg0: java.lang.String, arg1: Any*): java.lang.Object = {
    val function = this.setupFunction(new com.dexels.navajo.adapter.functions.SingleValueQuery)
    function.insertOperand(arg0)
    arg1.foreach(x => function.insertOperand(x))
    function.evaluate().asInstanceOf[java.lang.Object]
  }
  def SingleValueQuery(): java.lang.Object = {
    val function = this.setupFunction(new com.dexels.navajo.adapter.functions.SingleValueQuery)
    function.evaluate().asInstanceOf[java.lang.Object]
  }
  def testadapter(message: NavajoMessage, f: TESTADAPTER => Unit): Unit = {
    val instance = new TESTADAPTER
    setupMap(message, instance, f)
  }
  def asynctest(message: NavajoMessage, f: ASYNCTEST => Unit): Unit = {
    val instance = new ASYNCTEST
    setupMap(message, instance, f)
  }
  def asyncproxy(message: NavajoMessage, f: ASYNCPROXY => Unit): Unit = {
    val instance = new ASYNCPROXY
    setupMap(message, instance, f)
  }
  def sqldatasourcemap(message: NavajoMessage, f: SQLDATASOURCEMAP => Unit): Unit = {
    val instance = new SQLDATASOURCEMAP
    setupMap(message, instance, f)
  }
  def querymap(message: NavajoMessage, f: QUERYMAP => Unit): Unit = {
    val instance = new QUERYMAP
    setupMap(message, instance, f)
  }
  def runtime(message: NavajoMessage, f: RUNTIME => Unit): Unit = {
    val instance = new RUNTIME
    setupMap(message, instance, f)
  }
  def filemap(message: NavajoMessage, f: FILEMAP => Unit): Unit = {
    val instance = new FILEMAP
    setupMap(message, instance, f)
  }
  def filelinemap(message: NavajoMessage, f: FILELINEMAP => Unit): Unit = {
    val instance = new FILELINEMAP
    setupMap(message, instance, f)
  }
  def filerecordmap(message: NavajoMessage, f: FILERECORDMAP => Unit): Unit = {
    val instance = new FILERECORDMAP
    setupMap(message, instance, f)
  }
  def asyncproxymap(message: NavajoMessage, f: ASYNCPROXYMAP => Unit): Unit = {
    val instance = new ASYNCPROXYMAP
    setupMap(message, instance, f)
  }
  def tokenizer(message: NavajoMessage, f: TOKENIZER => Unit): Unit = {
    val instance = new TOKENIZER
    setupMap(message, instance, f)
  }
  def stringsplit(message: NavajoMessage, f: STRINGSPLIT => Unit): Unit = {
    val instance = new STRINGSPLIT
    setupMap(message, instance, f)
  }
  def databaseinfo(message: NavajoMessage, f: DATABASEINFO => Unit): Unit = {
    val instance = new DATABASEINFO
    setupMap(message, instance, f)
  }
  def oracleadministrator(message: NavajoMessage, f: ORACLEADMINISTRATOR => Unit): Unit = {
    val instance = new ORACLEADMINISTRATOR
    setupMap(message, instance, f)
  }
  def zipmap(message: NavajoMessage, f: ZIPMAP => Unit): Unit = {
    val instance = new ZIPMAP
    setupMap(message, instance, f)
  }
  def description(message: NavajoMessage, f: DESCRIPTION => Unit): Unit = {
    val instance = new DESCRIPTION
    setupMap(message, instance, f)
  }
  def save(message: NavajoMessage, f: SAVE => Unit): Unit = {
    val instance = new SAVE
    setupMap(message, instance, f)
  }
  def load(message: NavajoMessage, f: LOAD => Unit): Unit = {
    val instance = new LOAD
    setupMap(message, instance, f)
  }
  def filelist(message: NavajoMessage, f: FILELIST => Unit): Unit = {
    val instance = new FILELIST
    setupMap(message, instance, f)
  }
  def option(message: NavajoMessage, f: OPTION => Unit): Unit = {
    val instance = new OPTION
    setupMap(message, instance, f)
  }
  def fileentry(message: NavajoMessage, f: FILEENTRY => Unit): Unit = {
    val instance = new FILEENTRY
    setupMap(message, instance, f)
  }
  def dirmap(message: NavajoMessage, f: DIRMAP => Unit): Unit = {
    val instance = new DIRMAP
    setupMap(message, instance, f)
  }
  def arraymessage(message: NavajoMessage, f: ARRAYMESSAGE => Unit): Unit = {
    val instance = new ARRAYMESSAGE
    setupMap(message, instance, f)
  }
  def joinmessage(message: NavajoMessage, f: JOINMESSAGE => Unit): Unit = {
    val instance = new JOINMESSAGE
    setupMap(message, instance, f)
  }
  def copymessage(message: NavajoMessage, f: COPYMESSAGE => Unit): Unit = {
    val instance = new COPYMESSAGE
    setupMap(message, instance, f)
  }
  def csv(message: NavajoMessage, f: CSV => Unit): Unit = {
    val instance = new CSV
    setupMap(message, instance, f)
  }
  def http(message: NavajoMessage, f: HTTP => Unit): Unit = {
    val instance = new HTTP
    setupMap(message, instance, f)
  }
  def mail(message: NavajoMessage, f: MAIL => Unit): Unit = {
    val instance = new MAIL
    setupMap(message, instance, f)
  }
  def mailalternative(message: NavajoMessage, f: MAILALTERNATIVE => Unit): Unit = {
    val instance = new MAILALTERNATIVE
    setupMap(message, instance, f)
  }
  def commonsmailmap(message: NavajoMessage, f: COMMONSMAILMAP => Unit): Unit = {
    val instance = new COMMONSMAILMAP
    setupMap(message, instance, f)
  }
  def message(message: NavajoMessage, f: MESSAGE => Unit): Unit = {
    val instance = new MESSAGE
    setupMap(message, instance, f)
  }
  def navajogroup(message: NavajoMessage, f: NAVAJOGROUP => Unit): Unit = {
    val instance = new NAVAJOGROUP
    setupMap(message, instance, f)
  }
  def navajomap(message: NavajoMessage, f: NAVAJOMAP => Unit): Unit = {
    val instance = new NAVAJOMAP
    setupMap(message, instance, f)
  }
  def messagemap(message: NavajoMessage, f: MESSAGEMAP => Unit): Unit = {
    val instance = new MESSAGEMAP
    setupMap(message, instance, f)
  }
  def rest(message: NavajoMessage, f: REST => Unit): Unit = {
    val instance = new REST
    setupMap(message, instance, f)
  }
  def navajolistener(message: NavajoMessage, f: NAVAJOLISTENER => Unit): Unit = {
    val instance = new NAVAJOLISTENER
    setupMap(message, instance, f)
  }
  def sqlquery(message: NavajoMessage, f: SQLQUERY => Unit): Unit = {
    val instance = new SQLQUERY
    setupMap(message, instance, f)
  }
  def storedproc(message: NavajoMessage, f: STOREDPROC => Unit): Unit = {
    val instance = new STOREDPROC
    setupMap(message, instance, f)
  }
  def auditlog(message: NavajoMessage, f: AUDITLOG => Unit): Unit = {
    val instance = new AUDITLOG
    setupMap(message, instance, f)
  }
  def access(message: NavajoMessage, f: ACCESS => Unit): Unit = {
    val instance = new ACCESS
    setupMap(message, instance, f)
  }
  def xml(message: NavajoMessage, f: XML => Unit): Unit = {
    val instance = new XML
    setupMap(message, instance, f)
  }
  def xmlmap(message: NavajoMessage, f: XMLMAP => Unit): Unit = {
    val instance = new XMLMAP
    setupMap(message, instance, f)
  }
  def tagmap(message: NavajoMessage, f: TAGMAP => Unit): Unit = {
    val instance = new TAGMAP
    setupMap(message, instance, f)
  }
  def tml2xml(message: NavajoMessage, f: TML2XML => Unit): Unit = {
    val instance = new TML2XML
    setupMap(message, instance, f)
  }
  def adminMap(message: NavajoMessage, f: ADMINMAP => Unit): Unit = {
    val instance = new ADMINMAP
    setupMap(message, instance, f)
  }
  def accessMap(message: NavajoMessage, f: ACCESSMAP => Unit): Unit = {
    val instance = new ACCESSMAP
    setupMap(message, instance, f)
  }
  def multipleSQLMap(message: NavajoMessage, f: MULTIPLESQLMAP => Unit): Unit = {
    val instance = new MULTIPLESQLMAP
    setupMap(message, instance, f)
  }
  def selectionMap(message: NavajoMessage, f: SELECTIONMAP => Unit): Unit = {
    val instance = new SELECTIONMAP
    setupMap(message, instance, f)
  }
  def sequencedInsertMap(message: NavajoMessage, f: SEQUENCEDINSERTMAP => Unit): Unit = {
    val instance = new SEQUENCEDINSERTMAP
    setupMap(message, instance, f)
  }
}

class TESTADAPTER(instance: com.dexels.navajo.adapter.TestAdapter = new com.dexels.navajo.adapter.TestAdapter) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.TestAdapter => Unit): Unit = {
    f(instance)
  }
}

class ASYNCTEST(instance: com.dexels.navajo.mapping.AsyncTest = new com.dexels.navajo.mapping.AsyncTest) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.mapping.AsyncTest => Unit): Unit = {
    f(instance)
  }
}

class ASYNCPROXY(instance: com.dexels.navajo.adapter.AsyncProxy = new com.dexels.navajo.adapter.AsyncProxy) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.AsyncProxy => Unit): Unit = {
    f(instance)
  }
}

class SQLDATASOURCEMAP(instance: com.dexels.navajo.adapter.sqlmap.SQLMapDatasourceMap = new com.dexels.navajo.adapter.sqlmap.SQLMapDatasourceMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.sqlmap.SQLMapDatasourceMap => Unit): Unit = {
    f(instance)
  }
}

class QUERYMAP(instance: com.dexels.navajo.adapter.QueryMap = new com.dexels.navajo.adapter.QueryMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.QueryMap => Unit): Unit = {
    f(instance)
  }
}

class RUNTIME(instance: com.dexels.navajo.adapter.RuntimeAdapter = new com.dexels.navajo.adapter.RuntimeAdapter) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.RuntimeAdapter => Unit): Unit = {
    f(instance)
  }
}

class FILEMAP(instance: com.dexels.navajo.adapter.FileMap = new com.dexels.navajo.adapter.FileMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.FileMap => Unit): Unit = {
    f(instance)
  }
}

class FILELINEMAP(instance: com.dexels.navajo.adapter.filemap.FileLineMap = new com.dexels.navajo.adapter.filemap.FileLineMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.filemap.FileLineMap => Unit): Unit = {
    f(instance)
  }
}

class FILERECORDMAP(instance: com.dexels.navajo.adapter.filemap.FileRecordMap = new com.dexels.navajo.adapter.filemap.FileRecordMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.filemap.FileRecordMap => Unit): Unit = {
    f(instance)
  }
}

class ASYNCPROXYMAP(instance: com.dexels.navajo.adapter.AsyncProxyMap = new com.dexels.navajo.adapter.AsyncProxyMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.AsyncProxyMap => Unit): Unit = {
    f(instance)
  }
}

class TOKENIZER(instance: com.dexels.navajo.adapter.TokenizerMap = new com.dexels.navajo.adapter.TokenizerMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.TokenizerMap => Unit): Unit = {
    f(instance)
  }
}

class STRINGSPLIT(instance: com.dexels.navajo.adapter.StringSplitMap = new com.dexels.navajo.adapter.StringSplitMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.StringSplitMap => Unit): Unit = {
    f(instance)
  }
}

class DATABASEINFO(instance: com.dexels.navajo.adapter.sqlmap.DatabaseInfo = new com.dexels.navajo.adapter.sqlmap.DatabaseInfo) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.sqlmap.DatabaseInfo => Unit): Unit = {
    f(instance)
  }
}

class ORACLEADMINISTRATOR(instance: com.dexels.navajo.adapter.OracleAdministratorMap = new com.dexels.navajo.adapter.OracleAdministratorMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.OracleAdministratorMap => Unit): Unit = {
    f(instance)
  }
}

class ZIPMAP(instance: com.dexels.navajo.adapter.ZipMap = new com.dexels.navajo.adapter.ZipMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.ZipMap => Unit): Unit = {
    f(instance)
  }
}

class DESCRIPTION(instance: com.dexels.navajo.adapter.DescriptionMap = new com.dexels.navajo.adapter.DescriptionMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.DescriptionMap => Unit): Unit = {
    f(instance)
  }
}

class SAVE(instance: com.dexels.navajo.adapter.NavajoSaveAdapter = new com.dexels.navajo.adapter.NavajoSaveAdapter) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.NavajoSaveAdapter => Unit): Unit = {
    f(instance)
  }
}

class LOAD(instance: com.dexels.navajo.adapter.NavajoLoadAdapter = new com.dexels.navajo.adapter.NavajoLoadAdapter) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.NavajoLoadAdapter => Unit): Unit = {
    f(instance)
  }
}

class FILELIST(instance: com.dexels.navajo.adapter.NavajoFileListAdapter = new com.dexels.navajo.adapter.NavajoFileListAdapter) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.NavajoFileListAdapter => Unit): Unit = {
    f(instance)
  }
}

class OPTION(instance: com.dexels.navajo.adapter.OptionMap = new com.dexels.navajo.adapter.OptionMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.OptionMap => Unit): Unit = {
    f(instance)
  }
}

class FILEENTRY(instance: com.dexels.navajo.adapter.dirmap.FileEntryMap = new com.dexels.navajo.adapter.dirmap.FileEntryMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.dirmap.FileEntryMap => Unit): Unit = {
    f(instance)
  }
  def name: java.lang.String = {
    return instance.getName
  }
  def absolutePath(absolutePath: java.lang.String): FILEENTRY = {
    instance.setAbsolutePath(absolutePath)
    return this
  }
  def contents: com.dexels.navajo.document.types.Binary = {
    return instance.getContents
  }
  def mimeType: java.lang.String = {
    return instance.getMimeType
  }
  def size: java.lang.Integer = {
    return instance.getSize
  }
  def fileAge: java.lang.Integer = {
    return instance.getFileAge
  }
  def delete(): Unit = {
    instance.setDelete(true)
  }
}

class DIRMAP(instance: com.dexels.navajo.adapter.DirMap = new com.dexels.navajo.adapter.DirMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.DirMap => Unit): Unit = {
    f(instance)
  }
  def path(path: java.lang.String): DIRMAP = {
    instance.setPath(path)
    return this
  }
  def withEachFileEntries(f: FILEENTRY => Unit): DIRMAP = {
    for (i <- instance.getFileEntries)
      f(new FILEENTRY(i))
    return this
  }
}

class ARRAYMESSAGE(instance: com.dexels.navajo.adapter.MultipleEmptyMap = new com.dexels.navajo.adapter.MultipleEmptyMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.MultipleEmptyMap => Unit): Unit = {
    f(instance)
  }
  def withEachLoop(f: ARRAYELEMENT => Unit): ARRAYMESSAGE = {
    for (i <- instance.getLoop)
      f(new ARRAYELEMENT(i))
    return this
  }
}

class ARRAYELEMENT(instance: com.dexels.navajo.adapter.EmptyMap) extends Adapter(instance) {
  ()
}

class JOINMESSAGE(instance: com.dexels.navajo.adapter.MessageMap = new com.dexels.navajo.adapter.MessageMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.MessageMap => Unit): Unit = {
    f(instance)
  }
  def join(message1: java.lang.String, message2: java.lang.String, joinCondition: java.lang.String, `type`: java.lang.String, ignoreSource: java.lang.Boolean, suppressProperties: java.lang.String, removeDuplicates: java.lang.Boolean, groupBy: java.lang.String): Unit = {
    instance.setJoinMessage1(message1)
    instance.setJoinMessage2(message2)
    instance.setJoinCondition(joinCondition)
    instance.setJoinType(`type`)
    instance.setRemoveSource(ignoreSource)
    instance.setSuppressProperties(suppressProperties)
    instance.setRemoveDuplicates(removeDuplicates)
    instance.setGroupBy(groupBy)
  }
}

class COPYMESSAGE(instance: com.dexels.navajo.adapter.CopyMessage = new com.dexels.navajo.adapter.CopyMessage) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.CopyMessage => Unit): Unit = {
    f(instance)
  }
  def useOutputDoc(useOutputDoc: java.lang.Boolean): COPYMESSAGE = {
    instance.setUseOutputDoc(useOutputDoc)
    return this
  }
  def useDefinitionMessage(useDefinitionMessage: java.lang.Boolean): COPYMESSAGE = {
    instance.setUseDefinitionMessage(useDefinitionMessage)
    return this
  }
  def copyMessageFrom(copyMessageFrom: java.lang.String): COPYMESSAGE = {
    instance.setCopyMessageFrom(copyMessageFrom)
    return this
  }
  def copyMessageTo(copyMessageTo: java.lang.String): COPYMESSAGE = {
    instance.setCopyMessageTo(copyMessageTo)
    return this
  }
}

class CSV(instance: com.dexels.navajo.adapter.CSVMap = new com.dexels.navajo.adapter.CSVMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.CSVMap => Unit): Unit = {
    f(instance)
  }
  def maximumImportCount(maximumImportCount: java.lang.Integer): CSV = {
    instance.setMaximumImportCount(maximumImportCount)
    return this
  }
  def skipFirstRow(skipFirstRow: java.lang.Boolean): CSV = {
    instance.setSkipFirstRow(skipFirstRow)
    return this
  }
  def fileName(fileName: java.lang.String): CSV = {
    instance.setFileName(fileName)
    return this
  }
  def fileContent(fileContent: com.dexels.navajo.document.types.Binary): CSV = {
    instance.setFileContent(fileContent)
    return this
  }
  def separator(separator: java.lang.String): CSV = {
    instance.setSeparator(separator)
    return this
  }
  def update(update: java.lang.Boolean): CSV = {
    instance.setUpdate(update)
    return this
  }
  def includeEmpty(includeEmpty: java.lang.Boolean): CSV = {
    instance.setIncludeEmpty(includeEmpty)
    return this
  }
  def withEachEntries(f: CSVLINE => Unit): CSV = {
    for (i <- instance.getEntries)
      f(new CSVLINE(i))
    return this
  }
}

class CSVLINE(instance: com.dexels.navajo.adapter.csvmap.CSVEntryMap) extends Adapter(instance) {
  ()
}

class HTTP(instance: com.dexels.navajo.adapter.HTTPMap = new com.dexels.navajo.adapter.HTTPMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.HTTPMap => Unit): Unit = {
    f(instance)
  }
  def url(url: java.lang.String): HTTP = {
    instance.setUrl(url)
    return this
  }
  def method(method: java.lang.String): HTTP = {
    instance.setMethod(method)
    return this
  }
  def textContent(textContent: java.lang.String): HTTP = {
    instance.setTextContent(textContent)
    return this
  }
  def contentType(contentType: java.lang.String): HTTP = {
    instance.setContentType(contentType)
    return this
  }
  def content(content: com.dexels.navajo.document.types.Binary): HTTP = {
    instance.setContent(content)
    return this
  }
  def queuedSend(queuedSend: java.lang.Boolean): HTTP = {
    instance.setQueuedSend(queuedSend)
    return this
  }
  def doSend(): Unit = {
    instance.setDoSend(true)
  }
}

class MAIL(instance: com.dexels.navajo.adapter.MailMap = new com.dexels.navajo.adapter.MailMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.MailMap => Unit): Unit = {
    f(instance)
  }
  def mailServer(mailServer: java.lang.String): MAIL = {
    instance.setMailServer(mailServer)
    return this
  }
  def queuedSend(queuedSend: java.lang.Boolean): MAIL = {
    instance.setQueuedSend(queuedSend)
    return this
  }
  def ignoreFailures(ignoreFailures: java.lang.Boolean): MAIL = {
    instance.setIgnoreFailures(ignoreFailures)
    return this
  }
  def sender(sender: java.lang.String): MAIL = {
    instance.setSender(sender)
    return this
  }
  def subject(subject: java.lang.String): MAIL = {
    instance.setSubject(subject)
    return this
  }
  def text(text: java.lang.String): MAIL = {
    instance.setText(text)
    return this
  }
  def recipients(recipients: java.lang.String): MAIL = {
    instance.setRecipients(recipients)
    return this
  }
  def withAttachment(f: ATTACHMENT => Unit): MAIL = {
    f(new ATTACHMENT(instance.getAttachment))
    return this
  }
  def withEachMultipleAttachments(f: ATTACHMENT => Unit): MAIL = {
    for (i <- instance.getMultipleAttachments)
      f(new ATTACHMENT(i))
    return this
  }
  def smtpUser(smtpUser: java.lang.String): MAIL = {
    instance.setSmtpUser(smtpUser)
    return this
  }
  def smtpPass(smtpPass: java.lang.String): MAIL = {
    instance.setSmtpPass(smtpPass)
    return this
  }
  def useEncryption(useEncryption: java.lang.Boolean): MAIL = {
    instance.setUseEncryption(useEncryption)
    return this
  }
  def port(port: java.lang.Integer): MAIL = {
    instance.setPort(port)
    return this
  }
  def contentType(contentType: java.lang.String): MAIL = {
    instance.setContentType(contentType)
    return this
  }
}

class MAILALTERNATIVE(instance: com.dexels.navajo.adapter.MailMapAlternative = new com.dexels.navajo.adapter.MailMapAlternative) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.MailMapAlternative => Unit): Unit = {
    f(instance)
  }
  def mailServer(mailServer: java.lang.String): MAILALTERNATIVE = {
    instance.setMailServer(mailServer)
    return this
  }
  def queuedSend(queuedSend: java.lang.Boolean): MAILALTERNATIVE = {
    instance.setQueuedSend(queuedSend)
    return this
  }
  def ignoreFailures(ignoreFailures: java.lang.Boolean): MAILALTERNATIVE = {
    instance.setIgnoreFailures(ignoreFailures)
    return this
  }
  def sender(sender: java.lang.String): MAILALTERNATIVE = {
    instance.setSender(sender)
    return this
  }
  def subject(subject: java.lang.String): MAILALTERNATIVE = {
    instance.setSubject(subject)
    return this
  }
  def text(text: java.lang.String): MAILALTERNATIVE = {
    instance.setText(text)
    return this
  }
  def recipients(recipients: java.lang.String): MAILALTERNATIVE = {
    instance.setRecipients(recipients)
    return this
  }
  def withAttachment(f: ATTACHMENT => Unit): MAILALTERNATIVE = {
    f(new ATTACHMENT(instance.getAttachment))
    return this
  }
  def withRelatedBodyPart(f: ATTACHMENT => Unit): MAILALTERNATIVE = {
    f(new ATTACHMENT(instance.getRelatedBodyPart))
    return this
  }
  def withEachMultipleAttachments(f: ATTACHMENT => Unit): MAILALTERNATIVE = {
    for (i <- instance.getMultipleAttachments)
      f(new ATTACHMENT(i))
    return this
  }
}

class COMMONSMAILMAP(instance: com.dexels.navajo.adapter.CommonsMailMap = new com.dexels.navajo.adapter.CommonsMailMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.CommonsMailMap => Unit): Unit = {
    f(instance)
  }
  def mailServer(mailServer: java.lang.String): COMMONSMAILMAP = {
    instance.setMailServer(mailServer)
    return this
  }
  def queuedSend(queuedSend: java.lang.Boolean): COMMONSMAILMAP = {
    instance.setQueuedSend(queuedSend)
    return this
  }
  def from(from: java.lang.String): COMMONSMAILMAP = {
    instance.setFrom(from)
    return this
  }
  def subject(subject: java.lang.String): COMMONSMAILMAP = {
    instance.setSubject(subject)
    return this
  }
  def bodyText(bodyText: java.lang.String): COMMONSMAILMAP = {
    instance.setBodyText(bodyText)
    return this
  }
  def to(to: java.lang.String): COMMONSMAILMAP = {
    instance.setTo(to)
    return this
  }
  def withAttachment(f: ATTACHMENT => Unit): COMMONSMAILMAP = {
    f(new ATTACHMENT(instance.getAttachment))
    return this
  }
  def withEachMultipleAttachments(f: ATTACHMENT => Unit): COMMONSMAILMAP = {
    for (i <- instance.getMultipleAttachments)
      f(new ATTACHMENT(i))
    return this
  }
  def smtpUser(smtpUser: java.lang.String): COMMONSMAILMAP = {
    instance.setSmtpUser(smtpUser)
    return this
  }
  def smtpPass(smtpPass: java.lang.String): COMMONSMAILMAP = {
    instance.setSmtpPass(smtpPass)
    return this
  }
  def useEncryption(useEncryption: java.lang.Boolean): COMMONSMAILMAP = {
    instance.setUseEncryption(useEncryption)
    return this
  }
  def mailPort(mailPort: java.lang.String): COMMONSMAILMAP = {
    instance.setMailPort(mailPort)
    return this
  }
  def debug(debug: java.lang.Boolean): COMMONSMAILMAP = {
    instance.setDebug(debug)
    return this
  }
}

class ATTACHMENT(instance: com.dexels.navajo.adapter.mailmap.AttachementMap) extends Adapter(instance) {
  def attachFileName(attachFileName: java.lang.String): ATTACHMENT = {
    instance.setAttachFileName(attachFileName)
    return this
  }
  def attachFileContent(attachFileContent: com.dexels.navajo.document.types.Binary): ATTACHMENT = {
    instance.setAttachFileContent(attachFileContent)
    return this
  }
}

class MESSAGE(instance: com.dexels.navajo.adapter.CreateMessage = new com.dexels.navajo.adapter.CreateMessage) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.CreateMessage => Unit): Unit = {
    f(instance)
  }
  def create(name: java.lang.String): Unit = {
    instance.setName(name)
  }
}

class NAVAJOGROUP(instance: com.dexels.navajo.adapter.NavajoMapGroupAdapter = new com.dexels.navajo.adapter.NavajoMapGroupAdapter) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.NavajoMapGroupAdapter => Unit): Unit = {
    f(instance)
  }
  def appendResponse(id: java.lang.String, appendTo: java.lang.String, append: java.lang.String): Unit = {
    instance.setId(id)
    instance.setAppendTo(appendTo)
    instance.setAppend(append)
  }
  def setMessagePointer(id: java.lang.String, messagePointer: java.lang.String): Unit = {
    instance.setId(id)
    instance.setMessagePointer(messagePointer)
  }
}

class NAVAJOMAP(instance: com.dexels.navajo.adapter.NavajoMap = new com.dexels.navajo.adapter.NavajoMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.NavajoMap => Unit): Unit = {
    f(instance)
  }
  def trigger(trigger: java.lang.String): NAVAJOMAP = {
    instance.setTrigger(trigger)
    return this
  }
  def sendThrough(sendThrough: java.lang.Boolean): NAVAJOMAP = {
    instance.setSendThrough(sendThrough)
    return this
  }
  def server(server: java.lang.String): NAVAJOMAP = {
    instance.setServer(server)
    return this
  }
  def serverTimeout(serverTimeout: java.lang.Integer): NAVAJOMAP = {
    instance.setServerTimeout(serverTimeout)
    return this
  }
  def id(id: java.lang.String): NAVAJOMAP = {
    instance.setId(id)
    return this
  }
  def block(block: java.lang.Boolean): NAVAJOMAP = {
    instance.setBlock(block)
    return this
  }
  def username(username: java.lang.String): NAVAJOMAP = {
    instance.setUsername(username)
    return this
  }
  def password(password: java.lang.String): NAVAJOMAP = {
    instance.setPassword(password)
    return this
  }
  def tenant(tenant: java.lang.String): NAVAJOMAP = {
    instance.setTenant(tenant)
    return this
  }
  def resource(resource: java.lang.String): NAVAJOMAP = {
    instance.setResource(resource)
    return this
  }
  def useCurrentOutDoc(useCurrentOutDoc: java.lang.Boolean): NAVAJOMAP = {
    instance.setUseCurrentOutDoc(useCurrentOutDoc)
    return this
  }
  def useCurrentMessages(useCurrentMessages: java.lang.String): NAVAJOMAP = {
    instance.setUseCurrentMessages(useCurrentMessages)
    return this
  }
  def copyInputMessages(copyInputMessages: java.lang.String): NAVAJOMAP = {
    instance.setCopyInputMessages(copyInputMessages)
    return this
  }
  def messagePointer(messagePointer: java.lang.String): NAVAJOMAP = {
    instance.setMessagePointer(messagePointer)
    return this
  }
  def selectionPointer(selectionPointer: java.lang.String): NAVAJOMAP = {
    instance.setSelectionPointer(selectionPointer)
    return this
  }
  def withMessage(f: MESSAGEMAP => Unit): NAVAJOMAP = {
    f(new MESSAGEMAP(instance.getMessage))
    return this
  }
  def withEachMessages(f: MESSAGEMAP => Unit): NAVAJOMAP = {
    for (i <- instance.getMessages)
      f(new MESSAGEMAP(i))
    return this
  }
  def createproperty(name: java.lang.String, `type`: java.lang.String, value: java.lang.Object): Unit = {
    instance.setPropertyName(name)
    instance.setPropertyType(`type`)
    instance.setProperty(value)
  }
  def deleteproperty(name: java.lang.String): Unit = {
    instance.setDeleteProperty(name)
  }
  def deletemessage(name: java.lang.String): Unit = {
    instance.setDeleteMessage(name)
  }
  def suppressproperty(name: java.lang.String): Unit = {
    instance.setPropertyId(name)
    instance.setPropertyDirective("monkey")
  }
  def showproperty(name: java.lang.String, direction: java.lang.String): Unit = {
    instance.setPropertyId(name)
    instance.setPropertyDirective(direction)
    instance.setPropertyDirective("monkey")
  }
  def setdirection(name: java.lang.String, direction: java.lang.String): Unit = {
    instance.setPropertyId(name)
    instance.setPropertyDirective(direction)
  }
  def callwebservice(breakOnConditionError: java.lang.Boolean, breakOnException: java.lang.Boolean, server: java.lang.String, serverTimeout: java.lang.Integer, username: java.lang.String, password: java.lang.String, tenant: java.lang.String, showProperties: java.lang.String, suppressProperties: java.lang.String, inputProperties: java.lang.String, outputProperties: java.lang.String, block: java.lang.Boolean, name: java.lang.String, appendTo: java.lang.String, append: java.lang.String): Unit = {
    instance.setBreakOnConditionError(breakOnConditionError)
    instance.setBreakOnException(breakOnException)
    instance.setServer(server)
    instance.setServerTimeout(serverTimeout)
    instance.setUsername(username)
    instance.setPassword(password)
    instance.setTenant(tenant)
    instance.setSuppressProperties(showProperties)
    instance.setSuppressProperties(suppressProperties)
    instance.setInputProperties(inputProperties)
    instance.setOutputProperties(outputProperties)
    instance.setBlock(block)
    instance.setDoSend(name)
    instance.setAppendTo(appendTo)
    instance.setAppend(append)
  }
}

class MESSAGEMAP(instance: com.dexels.navajo.adapter.navajomap.MessageMap = new com.dexels.navajo.adapter.navajomap.MessageMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.navajomap.MessageMap => Unit): Unit = {
    f(instance)
  }
}

class REST(instance: com.dexels.navajo.adapter.RESTAdapter = new com.dexels.navajo.adapter.RESTAdapter) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.RESTAdapter => Unit): Unit = {
    f(instance)
  }
  def sendThrough(sendThrough: java.lang.Boolean): REST = {
    instance.setSendThrough(sendThrough)
    return this
  }
  def method(method: java.lang.String): REST = {
    instance.setMethod(method)
    return this
  }
  def useCurrentOutDoc(useCurrentOutDoc: java.lang.Boolean): REST = {
    instance.setUseCurrentOutDoc(useCurrentOutDoc)
    return this
  }
  def useCurrentMessages(useCurrentMessages: java.lang.String): REST = {
    instance.setUseCurrentMessages(useCurrentMessages)
    return this
  }
  def copyInputMessages(copyInputMessages: java.lang.String): REST = {
    instance.setCopyInputMessages(copyInputMessages)
    return this
  }
  def messagePointer(messagePointer: java.lang.String): REST = {
    instance.setMessagePointer(messagePointer)
    return this
  }
  def selectionPointer(selectionPointer: java.lang.String): REST = {
    instance.setSelectionPointer(selectionPointer)
    return this
  }
  def withMessage(f: MESSAGEMAP => Unit): REST = {
    f(new MESSAGEMAP(instance.getMessage))
    return this
  }
  def withEachMessages(f: MESSAGEMAP => Unit): REST = {
    for (i <- instance.getMessages)
      f(new MESSAGEMAP(i))
    return this
  }
  def username(username: java.lang.String): REST = {
    instance.setUsername(username)
    return this
  }
  def password(password: java.lang.String): REST = {
    instance.setPassword(password)
    return this
  }
  def dateformat(dateformat: java.lang.String): REST = {
    instance.setDateformat(dateformat)
    return this
  }
  def appendTo(appendTo: java.lang.String): REST = {
    instance.setAppendTo(appendTo)
    return this
  }
  def append(append: java.lang.String): REST = {
    instance.setAppend(append)
    return this
  }
  def topMessage(topMessage: java.lang.String): REST = {
    instance.setTopMessage(topMessage)
    return this
  }
  def messagesPerRequest(messagesPerRequest: java.lang.Integer): REST = {
    instance.setMessagesPerRequest(messagesPerRequest)
    return this
  }
  def createproperty(name: java.lang.String, `type`: java.lang.String, value: java.lang.Object): Unit = {
    instance.setPropertyName(name)
    instance.setPropertyType(`type`)
    instance.setProperty(value)
  }
  def deleteproperty(name: java.lang.String): Unit = {
    instance.setDeleteProperty(name)
  }
  def deletemessage(name: java.lang.String): Unit = {
    instance.setDeleteMessage(name)
  }
  def addparameter(name: java.lang.String, value: java.lang.String): Unit = {
    instance.setParameterName(name)
    instance.setParameterValue(value)
  }
  def addheader(name: java.lang.String, value: java.lang.String): Unit = {
    instance.setHeaderKey(name)
    instance.setHeaderValue(value)
  }
  def callservice(breakOnException: java.lang.Boolean, removeTopMessage: java.lang.Boolean, username: java.lang.String, password: java.lang.String, url: java.lang.String, appendTo: java.lang.String, append: java.lang.String): Unit = {
    instance.setBreakOnException(breakOnException)
    instance.setRemoveTopMessage(removeTopMessage)
    instance.setUsername(username)
    instance.setPassword(password)
    instance.setDoSend(url)
    instance.setAppendTo(appendTo)
    instance.setAppend(append)
  }
}

class NAVAJOLISTENER(instance: com.dexels.navajo.adapter.NavajoMap = new com.dexels.navajo.adapter.NavajoMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.NavajoMap => Unit): Unit = {
    f(instance)
  }
  def taskId(taskId: java.lang.String): NAVAJOLISTENER = {
    instance.setTaskId(taskId)
    return this
  }
}

class SQLQUERY(instance: com.dexels.navajo.adapter.SQLMap = new com.dexels.navajo.adapter.SQLMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.SQLMap => Unit): Unit = {
    f(instance)
  }
  def query(query: java.lang.String): SQLQUERY = {
    instance.setQuery(query)
    return this
  }
  def update(update: java.lang.String): SQLQUERY = {
    instance.setUpdate(update)
    return this
  }
  def binaryQuery(binaryQuery: com.dexels.navajo.document.types.Binary): SQLQUERY = {
    instance.setBinaryQuery(binaryQuery)
    return this
  }
  def binaryUpdate(binaryUpdate: com.dexels.navajo.document.types.Binary): SQLQUERY = {
    instance.setBinaryUpdate(binaryUpdate)
    return this
  }
  def debug(debug: java.lang.Boolean): SQLQUERY = {
    instance.setDebug(debug)
    return this
  }
  def transactionContext: java.lang.Integer = {
    return instance.getTransactionContext
  }
  def transactionContext(transactionContext: java.lang.Integer): SQLQUERY = {
    instance.setTransactionContext(transactionContext)
    return this
  }
  def datasource(datasource: java.lang.String): SQLQUERY = {
    instance.setDatasource(datasource)
    return this
  }
  def username(username: java.lang.String): SQLQUERY = {
    instance.setUsername(username)
    return this
  }
  def parameter(parameter: java.lang.Object): SQLQUERY = {
    instance.setParameter(parameter)
    return this
  }
  def withEachResultSet(f: RESULTROW => Unit): SQLQUERY = {
    for (i <- instance.getResultSet)
      f(new RESULTROW(i))
    return this
  }
  def addParameter(value: java.lang.Object): Unit = {
    instance.setParameter(value)
  }
  def doUpdate(): Unit = {
    instance.setDoUpdate(true)
  }
  def rollback(): Unit = {
    instance.setKill(true)
  }
}

class STOREDPROC(instance: com.dexels.navajo.adapter.SPMap = new com.dexels.navajo.adapter.SPMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.SPMap => Unit): Unit = {
    f(instance)
  }
  def query(query: java.lang.String): STOREDPROC = {
    instance.setQuery(query)
    return this
  }
  def update(update: java.lang.String): STOREDPROC = {
    instance.setUpdate(update)
    return this
  }
  def debug(debug: java.lang.Boolean): STOREDPROC = {
    instance.setDebug(debug)
    return this
  }
  def transactionContext(transactionContext: java.lang.Integer): STOREDPROC = {
    instance.setTransactionContext(transactionContext)
    return this
  }
  def datasource(datasource: java.lang.String): STOREDPROC = {
    instance.setDatasource(datasource)
    return this
  }
  def username(username: java.lang.String): STOREDPROC = {
    instance.setUsername(username)
    return this
  }
  def withEachResultSet(f: RESULTROW => Unit): STOREDPROC = {
    for (i <- instance.getResultSet)
      f(new RESULTROW(i))
    return this
  }
  def addParameter(value: java.lang.Object): Unit = {
    instance.setParameter(value)
  }
  def addOutputParameter(`type`: java.lang.String): Unit = {
    instance.setOutputParameterType(`type`)
  }
  def doUpdate(): Unit = {
    instance.setDoUpdate(true)
  }
  def rollback(): Unit = {
    instance.setKill(true)
  }
}

class RESULTROW(instance: com.dexels.navajo.adapter.sqlmap.ResultSetMap) extends Adapter(instance) {
  def columnName(columnName: java.lang.String): RESULTROW = {
    instance.setColumnName(columnName)
    return this
  }
  def columnValue: java.lang.Object = {
    return instance.getColumnValue
  }
  def value(name: java.lang.String): java.lang.Object = {
    instance.setColumnName(name)
    return instance.columnValue
  }
}

class AUDITLOG(instance: com.dexels.navajo.util.AuditLog = new com.dexels.navajo.util.AuditLog) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.util.AuditLog => Unit): Unit = {
    f(instance)
  }
}

class ACCESS(instance: com.dexels.navajo.adapter.NavajoAccess = new com.dexels.navajo.adapter.NavajoAccess) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.NavajoAccess => Unit): Unit = {
    f(instance)
  }
}

class XML(instance: com.dexels.navajo.adapter.XMLStreamMap = new com.dexels.navajo.adapter.XMLStreamMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.XMLStreamMap => Unit): Unit = {
    f(instance)
  }
  def content: com.dexels.navajo.document.types.Binary = {
    return instance.getContent
  }
  def indent(indent: java.lang.Integer): XML = {
    instance.setIndent(indent)
    return this
  }
  def startElement(name: java.lang.String): Unit = {
    instance.setStartElement(name)
  }
  def endElement(): Unit = {
    instance.setEndElement(true)
  }
  def newline(): Unit = {
    instance.setNewline(true)
  }
  def setAttribute(name: java.lang.String, value: java.lang.String): Unit = {
    instance.setAttributeName(name)
    instance.setAttributeValue(value)
  }
  def setValue(value: java.lang.String): Unit = {
    instance.setValue(value)
  }
}

class XMLMAP(instance: com.dexels.navajo.adapter.XMLMap = new com.dexels.navajo.adapter.XMLMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.XMLMap => Unit): Unit = {
    f(instance)
  }
  def stringContent(stringContent: java.lang.String): XMLMAP = {
    instance.setStringContent(stringContent)
    return this
  }
  def indent(indent: java.lang.Integer): XMLMAP = {
    instance.setIndent(indent)
    return this
  }
  def withChild(f: TAGMAP => Unit): XMLMAP = {
    f(new TAGMAP(instance.getChild))
    return this
  }
  def start(start: java.lang.String): XMLMAP = {
    instance.setStart(start)
    return this
  }
  def setContent(tag: java.lang.String, content: com.dexels.navajo.document.types.Binary): Unit = {
    instance.setChildName(tag)
    instance.setInsert(content)
  }
  def setText(tag: java.lang.String, value: java.lang.String): Unit = {
    instance.setChildName(tag)
    instance.setChildText(value)
  }
  def setAttribute(tag: java.lang.String, attribute: java.lang.String, value: java.lang.String): Unit = {
    instance.setChildName(tag)
    instance.setAttributeName(attribute)
    instance.setAttributeText(value)
  }
}

class TAGMAP(instance: com.dexels.navajo.adapter.xmlmap.TagMap = new com.dexels.navajo.adapter.xmlmap.TagMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.xmlmap.TagMap => Unit): Unit = {
    f(instance)
  }
  def withChild(f: TAGMAP => Unit): TAGMAP = {
    f(new TAGMAP(instance.getChild))
    return this
  }
  def name(name: java.lang.String): TAGMAP = {
    instance.setName(name)
    return this
  }
  def text(text: java.lang.String): TAGMAP = {
    instance.setText(text)
    return this
  }
  def setText(tag: java.lang.String, value: java.lang.String): Unit = {
    instance.setChildName(tag)
    instance.setChildText(value)
  }
  def setAttribute(tag: java.lang.String, attribute: java.lang.String, value: java.lang.String): Unit = {
    instance.setChildName(tag)
    instance.setAttributeName(attribute)
    instance.setAttributeText(value)
  }
}

class TML2XML(instance: com.dexels.navajo.adapter.TmlToXmlMap = new com.dexels.navajo.adapter.TmlToXmlMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.TmlToXmlMap => Unit): Unit = {
    f(instance)
  }
  def rootPath(rootPath: java.lang.String): TML2XML = {
    instance.setRootPath(rootPath)
    return this
  }
  def content: com.dexels.navajo.document.types.Binary = {
    return instance.getContent
  }
  def addAttribute(path: java.lang.String, name: java.lang.String, value: java.lang.String): Unit = {
    instance.setAttributePath(path)
    instance.setAttributeName(name)
    instance.setAttributeValue(value)
  }
  def dumpObject(): Unit = {
    instance.setDumpObject(true)
  }
  def buildContent(): Unit = {
    instance.setBuildContent(true)
  }
}

class ADMINMAP(instance: com.dexels.navajo.adapter.AdminMap = new com.dexels.navajo.adapter.AdminMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.AdminMap => Unit): Unit = {
    f(instance)
  }
}

class ACCESSMAP(instance: com.dexels.navajo.adapter.AccessMap = new com.dexels.navajo.adapter.AccessMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.AccessMap => Unit): Unit = {
    f(instance)
  }
}

class MULTIPLESQLMAP(instance: com.dexels.navajo.adapter.MultipleSQLMap = new com.dexels.navajo.adapter.MultipleSQLMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.MultipleSQLMap => Unit): Unit = {
    f(instance)
  }
}

class SELECTIONMAP(instance: com.dexels.navajo.adapter.SelectionMap = new com.dexels.navajo.adapter.SelectionMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.SelectionMap => Unit): Unit = {
    f(instance)
  }
}

class SEQUENCEDINSERTMAP(instance: com.dexels.navajo.adapter.SequencedInsertMap = new com.dexels.navajo.adapter.SequencedInsertMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.SequencedInsertMap => Unit): Unit = {
    f(instance)
  }
}