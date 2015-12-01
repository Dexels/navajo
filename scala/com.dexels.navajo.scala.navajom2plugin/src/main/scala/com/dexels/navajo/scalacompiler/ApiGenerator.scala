package com.dexels.navajo.scalacompiler

import java.io.File
import scala.collection.mutable.ListBuffer
import scala.xml.Elem
import scala.xml.XML
import scala.xml.Node
import scala.xml.NodeSeq
import scala.xml.MinimizeMode
import java.io.PrintWriter
import treehugger.forest._
import treehugger.forest.definitions._
import treehugger.forest.treehuggerDSL._
import scala.collection.JavaConversions._
import com.dexels.navajo.document.NavajoFactory
import java.util.jar.Manifest;
import java.util.jar.Attributes;
import java.io.FileWriter
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.Map
import java.util.HashMap

class ApiGenerator {

    def createScalaFragment(sourceManifest: Manifest, sourceProject: File, destination: File) = {
        val fields = getBundleFields(sourceManifest)
        val bundleName = fields.get("Bundle-Name") + " Scala Api"
        val sourceSymbolicName = fields.get("Bundle-SymbolicName")
        val symbolicName = sourceSymbolicName + ".scala.api"
        val importPackages = fields.get("Import-Package")
        val version = fields.get("Bundle-Version")

        val destProject = new File(destination, symbolicName);
        val metainf = new File(destProject, "META-INF");
        metainf.mkdirs();
        

        val manifest = new Manifest()
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        manifest.getMainAttributes().putValue("Bundle-Name", bundleName)
        manifest.getMainAttributes().putValue("Fragment-Host", sourceSymbolicName)
        manifest.getMainAttributes().putValue("Bundle-Vendor", "Dexels")

        manifest.getMainAttributes().putValue("Require-Bundle", "org.scala-lang.scala-library")
        manifest.getMainAttributes().putValue("Bundle-ManifestVersion", "2")

        manifest.getMainAttributes().putValue("Bundle-Version", "1.0.0.qualifier")
        manifest.getMainAttributes().putValue("Bundle-SymbolicName", symbolicName)
        // manifest.getMainAttributes().putValue("Provide-Package", symbolicName)
        manifest.getMainAttributes().putValue("Export-Package", symbolicName)

        createBuildProperties(destProject)
        createProjectDef(symbolicName, new File(destProject, ".project"))
        createClassPathDef(new File(destProject, ".classpath"))
        val packageDirName = "src/" + symbolicName.replace(".", "/")
        val destSource = new File(destProject, packageDirName);

        destSource.mkdirs()

        createPom(symbolicName, new File(destProject, "pom.xml"))
         val extraImports =  createScalaApi(symbolicName, sourceProject, new File(destSource, "api.scala"))
        // version, perhaps?
        val buffer = new StringBuilder
        //buffer.append(importPackages)
        buffer.append(" com.dexels.navajo.scala, com.dexels.navajo.scala.document")

        extraImports.foreach(f => {
            buffer.append(",")
            buffer.append(f + ".scala.api")
        })
        manifest.getMainAttributes().putValue("Import-Package", buffer.toString)
        val fos = new FileOutputStream(new File(metainf, "MANIFEST.MF"))
        manifest.write(fos)
        fos.flush()
        fos.close

    }

    def getBundleFields(manifest: Manifest): Map[String, String] = {
        val result = new HashMap[String, String]
        manifest.getMainAttributes().foreach(f => {
            result.put("" + f._1, "" + f._2)
        })
        return result
    }

    private def versionToMaven(osgiVersion: String): String = {
        val extension = ".qualifier"
        if (osgiVersion.endsWith(extension)) {
            return osgiVersion.split(extension)(0) + "-SNAPSHOT"
        }
        return osgiVersion
    }

    def createPom(artifact: String, file: File): Unit = {
        val template =
            <project xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd" xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <modelVersion>4.0.0</modelVersion>
                <artifactId>{ artifact }</artifactId>
                <version>1.0.0-SNAPSHOT</version>
                <packaging>eclipse-plugin</packaging>
                <parent>
                    <groupId>com.dexels.navajo</groupId>
                    <artifactId>com.dexels.navajo.scala.super</artifactId>
                    <version>1.0.0</version>
                </parent>
            </project>
        val w = new FileWriter(file)
        XML.write(w, template, "UTF-8", false, null, MinimizeMode.Default)
        w.close
    }

    def createProjectDef(name: String, file: File): Unit = {
        val template =
            <projectDescription>
                <name>{ name }</name>
                <comment></comment>
                <projects>
                </projects>
                <buildSpec>
                    <buildCommand>
                        <name>org.scala-ide.sdt.core.scalabuilder</name>
                        <arguments>
                        </arguments>
                    </buildCommand>
                    <buildCommand>
                        <name>org.eclipse.pde.ds.core.builder</name>
                        <arguments>
                        </arguments>
                    </buildCommand>
                </buildSpec>
                <natures>
                    <nature>org.scala-ide.sdt.core.scalanature</nature>
                    <nature>org.eclipse.jdt.core.javanature</nature>
                </natures>
            </projectDescription>
        val w = new FileWriter(file)
        XML.write(w, template, "UTF-8", false, null, MinimizeMode.Default)
        w.close
    }

    def createClassPathDef(file: File): Unit = {
        val template =
            <classpath>
                <classpathentry kind="src" path="src"/>
                <classpathentry kind="con" path="org.scala-ide.sdt.launching.SCALA_CONTAINER"/>
                <classpathentry kind="con" path="org.eclipse.pde.core.requiredPlugins"/>
                <classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER"/>
                <classpathentry kind="output" path="bin"/>
            </classpath>
        val w = new FileWriter(file)
        XML.write(w, template, "UTF-8", false, null, MinimizeMode.Default)
        w.close
    }

    def createBuildProperties(destProject: File) = {
        val buildproperties = new File(destProject, "build.properties")
        val fw = new FileWriter(buildproperties)
        fw.write("source.. = src/\n")
        fw.write("output.. = bin/\n")
        fw.write("bin.includes = META-INF/,.\n")
        fw.close()
    }

    def createScalaApi(name: String, projectFolder: File, destinationSource: File):Iterable[String] = {
        val sourceFolder = new File(projectFolder, "src");
        val manifestFile = new File(projectFolder, "META-INF/MANIFEST.MF")
        val is = new FileInputStream(manifestFile)
        val manifest = new Manifest()
        manifest.read(is)
        is.close()
        val navajoExtensionHeader = manifest.getMainAttributes().getValue("Navajo-Extension")
        val navajoName = manifest.getMainAttributes().getValue("Navajo-Name")
        if (navajoExtensionHeader == null) {
            throw new IllegalArgumentException("No 'Navajo-Extension' header found in manifest of project: " + projectFolder.getAbsolutePath())
        }
        val paths = new ListBuffer[File]
        navajoExtensionHeader.split(",").foreach(path => {
            val f = new File(sourceFolder, path)
            paths.append(f)
        })
        createApi(navajoName, name.toLowerCase(), paths, destinationSource)

    }

    def createApi(name: String, packageDef: String, paths: Iterable[File], destination: File) :Iterable[String] = {
        val lists = new ListBuffer[Elem]
        for (path <- paths) {
            lists.append(XML.loadFile(path))
        }
        createAdaptersTrait(name, packageDef, lists, destination)
    }

    def createAdaptersTrait(name: String, packageName: String, rootNodes: Iterable[Node], destination: File) :Iterable[String] = {
        //    val mapnodes = rootNodes(0) \\ "map"

        val functionnodes = new ListBuffer[Node]
        val allMaps = new ListBuffer[Node]
        val allRequires = new ListBuffer[Node]
        
        for (rootNode <- rootNodes) {
            allMaps.appendAll(rootNode \\ "map")
        }
        for (rootNode <- rootNodes) {
            functionnodes.appendAll(rootNode \\ "function")
            System.err.println("funccc:" + functionnodes.size);
        }
        for (rootNode <- rootNodes) {
          allRequires.appendAll(rootNode \\ "require")
        }

        val definitions = new ListBuffer[DefDef]()
        val imports = for (i <- allRequires) yield IMPORT(i.attribute("bundle").get.text + ".scala.api._")
        
        functionnodes.foreach((nodeseq: NodeSeq) => {
            print("><" + nodeseq.iterator.next)

            nodeseq.foreach(node => {
                val name = node.attribute("name").get.text
                System.err.println("name: " + name);
                val clz = node.attribute("class").get.text
                val desc = (node \\ "description").text
                val input = (node \\ "input").text
                val result = (node \\ "result").text
                processFunction(name, clz, desc, input, result, definitions)
            })
        });

        //    val functionTrait = createTrait(name, packageName, functions)
        //    val writer = new PrintWriter(new File(destination, name + ".scala"))

        for (node <- allMaps) {
            val isAbstract = node.attribute("abstract").isDefined
            if (!isAbstract) {
                val maptag = (node \\ "tagname").text
                val clz = (node \\ "object").text
                definitions.append(processMap(maptag, clz))
            }
        }

        val adaptersTrait = BLOCK(
              imports ++
                List[Tree](
                    IMPORT("com.dexels.navajo.scala.document._"),
                    IMPORT("com.dexels.navajo.scala._"),
                    (TRAITDEF(name + "Component") withParents ("com.dexels.navajo.scala.BaseAdapters", "com.dexels.navajo.scala.Base")) withSelf ("self", "com.dexels.navajo.scala.ScalaScript") :=
                        BLOCK(definitions)) ++

                    createAdapters(allMaps)) inPackage (packageName)

        //    if (!destination.exists) {
        //      destination.mkdirs
        //    }
        val writer = new PrintWriter(destination)

        writer.write(treeToString(adaptersTrait))
        writer.close()
        
        return for (i <- allRequires) yield (i.attribute("bundle").get.text)

    }
    def createAdapters(nodeseqlist: Iterable[NodeSeq]): Iterable[Tree] = {
        val result = new ListBuffer[Tree]
        nodeseqlist.foreach(list => {

            list.foreach(f => {
                val maptag = (f \\ "tagname").text
                val isAbstract = f.attribute("abstract").isDefined
                val clz = (f \\ "object").text
                val description = (f \\ "description")

                val values = (f \\ "values")
                val methods = (f \\ "methods")

                if (isAbstract) {
                    result.append(createAbstractClassDef(maptag, clz, values, methods, description))
                } else {
                    result.append(createClassDef(maptag, clz, values, methods, description))

                }

            })
        })

        return result
    }

    private def createClassDef(maptag: String, clz: String, values: NodeSeq, methods: NodeSeq, description: NodeSeq): Tree = {
        val classDef = (CLASSDEF(maptag.toUpperCase()) withParents (TYPE_REF("Adapter") APPLY REF("instance")) withParams ((PARAM("instance") withType (clz)) := NEW(clz)) := BLOCK(
            List[Tree](
                (DEF("instance", UnitClass) withParams (PARAM("f") withType (TYPE_REF(clz) TYPE_=> UnitClass))  := BLOCK(
                    REF("f") APPLY REF("instance")))) ++
                createValues(maptag.toUpperCase(), values) ++
                createMethods(methods)))

        return classDef
    }

    private def createAbstractClassDef(maptag: String, clz: String, values: NodeSeq, methods: NodeSeq, description: NodeSeq): Tree = {
        val classDef = (CLASSDEF(maptag.toUpperCase()) withParents (TYPE_REF("Adapter") APPLY REF("instance")) withParams ((PARAM("instance") withType (clz))) := BLOCK(
            List() ++
                createValues(maptag.toUpperCase(), values) ++
                createMethods(methods)))

        return classDef
    }

    def processFunction(name: String, clz: String, desc: String, input: String, result: String, functions: ListBuffer[DefDef]): Unit = {
        //    if (input.indexOf("...") != -1) {
        //      System.err.println("Currying! TODO implement");
        //      return
        //    }
        val expandedParams = expandParams(input)
        print(s"Params: ${expandedParams}\n")
        var curry: Int = -1

        expandedParams.foreach(params => {
            var i: Int = 0
            val paramList = new ListBuffer[ValDef]
            params.foreach(f => {
                val dataType = NavajoFactory.getInstance.getJavaType(f)
                val dataTypeGeneric = NavajoFactory.getInstance().getJavaTypeGeneric(f)
                if (dataTypeGeneric != null) {
                    if ("*".equals(dataTypeGeneric)) {
                        paramList.append(PARAM("arg" + i, dataType.getName()).withType("Any"))

                    } else {
                        paramList.append(PARAM("arg" + i, dataType.getName()).withType(dataTypeGeneric))
                    } 

                } else {
                    if (dataType == null) {
                        paramList.append(PARAM("arg" + i, "Any*"))
                        curry = i
                    } else {
                        paramList.append(PARAM("arg" + i, dataType.getName()))
                    }
                }
                i += 1
            })

            i = 0
            val insertOperandList = new ListBuffer[Tree]
            insertOperandList.append(VAL("function") := (THIS DOT "setupFunction")(NEW(clz)))
            // arg1.foreach(f=>function.insertOperand(f))

            params.foreach(f => {
                if (i == curry) {
                    // foreach or something
                    insertOperandList.append(REF("arg" + i) FOREACH (LAMBDA(PARAM("x")) ==> (REF("function") DOT "insertOperand" APPLY REF("x"))))
                } else {
                    insertOperandList.append((REF("function") DOT "insertOperand") APPLY REF("arg" + i))
                }
                i += 1
            })
            functions.append(
                DEFINFER(name) withType evaluateReturnType(result) withParams (paramList) := BLOCK(
                    insertOperandList ++ List[Tree](REF("function") DOT "evaluate" APPLY () AS evaluateReturnType(result))))
        })
    }

    def processMap(name: String, clz: String): DefDef = {
        return DEFINFER(name) withParams (PARAM("message") withType ("NavajoMessage"), PARAM("f") withType (TYPE_REF(name.toUpperCase()) TYPE_=> UnitClass)) withType (UnitClass) := BLOCK(
            VAL("instance") := NEW(name.toUpperCase()),
            REF("setupMap") APPLY (REF("message"), REF("instance"), REF("f")) //    		setupMap
            )
    }

    def createMethods(methods: NodeSeq): Iterator[Tree] = {
        val result = new ListBuffer[Tree]
        (methods \\ "method").foreach(f => {
            val name = f.attribute("name").get.text
            val valueField = f.attribute("valueField")
            val paramNodes = f \\ "param"
            val params = createParams(paramNodes)
            if (valueField.isDefined) {
                val valueType = NavajoFactory.getInstance().getJavaType(f.attribute("type").get.text).getName()
                result.append(
                    DEFINFER(name) withType valueType withParams (params) := BLOCK(
                        createMethodBody(paramNodes, valueField)))
            } else {
                result.append(
                    DEFINFER(name) withType UnitClass withParams (params) := BLOCK(
                        createMethodBody(paramNodes, valueField)))
            }
        })
        return result.iterator;

    }

    def createParams(params: NodeSeq): Iterable[ValDef] = {
        val result = new ListBuffer[ValDef]
        (params \\ "param").foreach(f => {
            val name = f.attribute("name").get.text
            val field = f.attribute("field").get.text
            val valueType = f.attribute("type").get.text
            val required = f.attribute("required").get.text
            if (required.equals("automatic")) {

            } else {
                result.append(PARAM(name) withType ((NavajoFactory.getInstance().getJavaType(valueType).getName())))
            }
        })
        return result
    }

    ////  			<method name="setAttribute">
    //				<param name="tag" field="childName" type="string" required="true" />
    //				<param name="attribute" field="attributeName" type="string" required="true" />
    //				<param name="value" field="attributeText" type="string" required="true" />
    //			</method>
    def createMethodBody(values: NodeSeq, valueField: Option[Seq[Node]]): Iterable[Tree] = {
        val result = new ListBuffer[Tree]
        values.foreach(f => {
            val name = f.attribute("name").get.text
            val fieldName = "set" + f.attribute("field").get.text.capitalize
            val required = f.attribute("required").get.text
            if (required.equals("automatic")) {
                val valueType = f.attribute("type").get.text
                val value = f.attribute("value").get.text
                val literal = instantiateLiteral(valueType, value);
                result.append(REF("instance") DOT fieldName APPLY literal)

            } else {
                result.append(REF("instance") DOT fieldName APPLY REF(name))
            }
            if (valueField.isDefined) {
                result.append(RETURN(REF("instance") DOT valueField.get.text))
            }
            //      valueField="columnValue"

        })
        return result
    }

    def instantiateLiteral(valueType: String, value: String): Literal = {
        if ("boolean".equals(valueType)) {
            if (("true").equals(value)) {
                return LIT(true)
            }
            if (("false").equals(value)) {
                return LIT(false)
            }
        }
        return LIT("monkey")
    }

    def createValues(adapterType: String, values: NodeSeq): Iterator[Tree] = {
        val result = new ListBuffer[Tree]

        (values \\ "value").foreach(f => {
            val name = f.attribute("name").get.text
            val required: Boolean = !f.attribute("required").isEmpty && f.attribute("required").equals("true")
            val isIn: Boolean = f.attribute("direction").get.text.equals("in")
            val isInOut: Boolean = f.attribute("direction").get.text.equals("inout")
            if (f.attribute("map").isDefined) {
                val map: String = f.attribute("map").get.text
                // does [] occuur
                val parts = map.split("\\[\\]")
                val str = parts(0).trim()
                //        
                val isArray = map.indexOf("[]") != -1
                // only getters
                if (isArray)
                    result.append(
                        DEFINFER("withEach" + name.capitalize) withType TYPE_REF(adapterType) withParams (PARAM("f") withType (TYPE_REF(str.toUpperCase()) TYPE_=> UnitClass)) := BLOCK(
                            FOR(VALFROM("i") := REF("instance") DOT "get" + name.capitalize) DO (
                                REF("f") APPLY (NEW(str.toUpperCase(), REF("i")))),
                            RETURN(THIS)))
                else {
                    result.append(
                        DEFINFER("with" + name.capitalize) withType TYPE_REF(adapterType) withParams (PARAM("f") withType (TYPE_REF(str.toUpperCase()) TYPE_=> UnitClass)) := BLOCK(
                            REF("f") APPLY (NEW(str.toUpperCase(), REF("instance") DOT "get" + name.capitalize)), RETURN(THIS)))

                }

            } else {
                if (f.attribute("type").isEmpty) {
                    System.err.println("weird: " + f);
                } else {
                    val valueType = f.attribute("type").get.text
                    // create getter
                    if (!isIn || isInOut) {
                        result.append(
                            DEFINFER(name) withType (NavajoFactory.getInstance().getJavaType(valueType).getName()) := BLOCK(
                                RETURN(REF("instance") DOT "get" + name.capitalize)))
                    }
                    if (isIn || isInOut) {
                        result.append(
                            DEFINFER(name) withType TYPE_REF(adapterType) withParams (PARAM(name) withType (NavajoFactory.getInstance().getJavaType(valueType).getName())) := BLOCK(
                                REF("instance") DOT "set" + name.capitalize APPLY REF(name),
                                RETURN(THIS)))
                    }
                    // create setter
                }
            }

            //    	val s: String = f.attribute("name").get.text
            //      result.append(
            //        DEF(f.attribute("name").get.text) withParams (PARAM("message") withType ("NavajoMessage"), PARAM("f") withType (IntClass TYPE_=> UnitClass)) withType (UnitClass) := BLOCK())
        })
        return result.iterator
    }

    protected def evaluateReturnType(xmlValue: String): String = {
        if (xmlValue == null || "".equals(xmlValue)) {
            return "Any"
        }
        if (xmlValue.indexOf("|") != -1) {
            return "Any"
        }
        if (xmlValue.equals("list")) {
            return "List[Any]"
        }

        val valueType = NavajoFactory.getInstance().getJavaType(xmlValue)
        if (valueType != null) {
            return valueType.getName();
        }
        return "Any"

    }

    def expandParams(parameters: String): ListBuffer[List[String]] = {
        val result = new ListBuffer[List[String]]
        if (parameters == null || "".equals(parameters)) {
            result.append(List())
            return result
        }
        val params = parameters.split(",")
        val matrix = new Array[Array[String]](params.length)
        var i: Int = 0;
        params.foreach(element => {
            val alternatives = element.split("\\|")
            matrix(i) = alternatives
            i += 1
        })
        buildParams(List[String](), matrix, result)
        return result
    }

    private def buildParams(selec: List[String], matrix: Array[Array[String]], result: ListBuffer[List[String]]): Unit = {
        val startAt = selec.length
        if (startAt == matrix.length) {
            // done
            result.append(selec)
            return
        }
        val current = matrix(startAt)
        current.foreach(alt => {
            if ("empty".equals(alt)) {
                // continue
                result.append(selec)
            } else {
                buildParams(selec :+ alt.trim, matrix, result)
            }

        })
    }

}

