<%@ page contentType="text/html; charset=iso-8859-1" %>
<HTML>
<HEAD>
<TITLE>
Navajo administrator
</TITLE>
<link rel="stylesheet" href="/thispas/demo.css" type="text/css"/>
</HEAD>
<BODY>
<%
  String error = request.getParameter("error");
%>
       <table border="0" cellspacing="0" width="800">
        <tr>
        <td bgcolor="#FEFAE5" colspan="2">
          <A HREF="/thispas/admin.jsp" BORDER="0">
          <IMG SRC="/thispas/dexels_logo.gif" BORDER="0" ALIGN="LEFT"/></A>
          <A HREF="/thispas/servlet/NavajoClient?command=afmelden" BORDER="0">
          <IMG SRC="/thispas/logo.gif"  BORDER="0" ALIGN="RIGHT" /></A>
       </td>
       </tr>
     <tr><td class="resultheader" colspan="2"><FONT size="4"><b>&nbsp;Navajo Administrator</b></FONT></td></tr>
   <tr><td colspan="2">&nbsp;</td></tr>

<%    if (error != null) { %>
    <tr><td>  <FONT size="4" COLOR="#FF0000"><%=error%></FONT> </tr></td>
<%    } %>

    <tr>
    <td class="resultheader" colspan="2"><FONT size="3">&nbsp;&nbsp;Upload adapter jar</font></td>
    </tr>
    <tr>
    <td class="result" colspan="2">&nbsp;
    <form method='post' action='servlet/Admin' enctype='multipart/form-data'>
    <input type='hidden' name='forward' value='/thispas/admin.jsp'>
    <input type='file' name='jar'>
    <input type='checkbox' name='beta'> (beta)
    <input type='hidden' name='command' value='upload_jar'>
    <input type='submit' name='button' value=' OK '>
    </form>
    </td>
    </tr>
    <tr>
    <td class="result" colspan="2"><FONT size="3">&nbsp;&nbsp;</font></td>
    </tr>
   <tr><td colspan="2">&nbsp;</td></tr>

    <tr>
    <td class="resultheader" colspan="2"><FONT size="3">
    &nbsp;&nbsp;Reload all adapters</font></td>
    </tr>
    <tr>
    <td class="result" colspan="2">&nbsp;
    <form method='post' action='servlet/Admin' enctype='multipart/form-data'>
    <input type='hidden' name='forward' value='/thispas/admin.jsp'>
    <input type='hidden' name='command' value='reload'>
    <input type='submit' name='button' value=' OK '>
    </form>
    </td>
    </tr>
    <tr>
    <td class="result" colspan="2"><FONT size="3">&nbsp;&nbsp;</font></td>
    </tr>

<tr><td colspan="2">&nbsp;</td></tr>

<tr>
    <td class="resultheader" colspan="2"><FONT size="3">
    &nbsp;&nbsp;Upload script</font></td>
    </tr>
    <tr>
    <td class="result" colspan="2">&nbsp;
    <form method='post' action='servlet/Admin' enctype='multipart/form-data'>
    <input type='hidden' name='forward' value='/thispas/admin.jsp'>
    <input type='file' name='script'>
    <input type='checkbox' name='beta'> (beta)
    <input type='hidden' name='command' value='upload_script'>
    <input type='submit' name='button' value=' OK '>
    </form>
  </td>
    </tr>
    <tr>
    <td class="result" colspan="2"><FONT size="3">&nbsp;&nbsp;</font></td>
    </tr>

<tr><td colspan="2">&nbsp;</td></tr>

<tr>
    <td class="resultheader" colspan="2"><FONT size="3">
    &nbsp;&nbsp;Update repository class</font></td>
    </tr>
    <tr>
    <td class="result" colspan="2">&nbsp;
    <form method='post' action='servlet/Admin' enctype='multipart/form-data'>
    <input type='hidden' name='forward' value='/thispas/admin.jsp'>
    <input type='text' name='repository' size=50>
    <input type='hidden' name='command' value='update_repository'>
    <input type='submit' name='button' value=' OK '>
    </form>
 </td>
    </tr>
    <tr>
    <td class="result" colspan="2"><FONT size="3">&nbsp;&nbsp;</font></td>
    </tr>

<tr><td colspan="2">&nbsp;</td></tr>


<tr>
    <td class="resultheader" colspan="2"><FONT size="3">
    &nbsp;&nbsp;Publish beta script </font></td>
    </tr>
    <tr>
    <td class="result" colspan="2">&nbsp;
    <form method='post' action='servlet/Admin' enctype='multipart/form-data'>
    <input type='hidden' name='forward' value='/thispas/admin.jsp'>
    <input type='text' name='script' size=50>
    <input type='hidden' name='command' value='beta_script'>
    <input type='submit' name='button' value=' OK '>
    </form>
</td>
    </tr>
    <tr>
    <td class="result" colspan="2"><FONT size="3">&nbsp;&nbsp;</font></td>
    </tr>

<tr><td colspan="2">&nbsp;</td></tr>

<tr>
    <td class="resultheader" colspan="2"><FONT size="3">
    &nbsp;&nbsp;Add Navajo webservice </font></td>
    </tr>
    <tr>
    <td class="result" colspan="2">&nbsp;
    <form method='post' action='servlet/Admin' enctype='multipart/form-data'>
    <input type='hidden' name='forward' value='/thispas/admin.jsp'>
    Name: <input type='text' name='name' size=50>
    Groupid: <input type='text' name='group' size=50>
    <input type='hidden' name='command' value='add_service'>
    <input type='submit' name='button' value=' OK '>
    </form>
</td>
    </tr>
    <tr>
    <td class="result" colspan="2"><FONT size="3">&nbsp;&nbsp;</font></td>
    </tr>

<tr><td colspan="2">&nbsp;</td></tr>

<tr>
    <td class="resultheader" colspan="2"><FONT size="3">
     &nbsp;&nbsp;Add Navajo webservice group </font></td>
    </tr>
    <tr>
    <td class="result" colspan="2">&nbsp;
    <form method='post' action='servlet/Admin' enctype='multipart/form-data'>
    <input type='hidden' name='forward' value='/thispas/admin.jsp'>
    Name: <input type='text' name='name' size=50>
    Handler (leave empty to use default handler): <input type='text' name='handler' size=50>
    <input type='hidden' name='command' value='add_group'>
    <input type='submit' name='button' value=' OK '>
    </form>
</td>
    </tr>
    <tr>
    <td class="result" colspan="2"><FONT size="3">&nbsp;&nbsp;</font></td>
    </tr>
</table>


</BODY>
</HTML>
