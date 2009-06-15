<HTML>
	<HEAD>
		<TITLE>post tester</TITLE>
	</HEAD>
	<BODY>
<?
$filename = "posttester.log";
$handle = fopen($filename, "a");

echo "<H1>POST Values:</H1>\n";
foreach ($_POST as $name => $value) {
	$currentline =  $name.':'.$value."<BR>\n";
	echo $currentline;
	fwrite($handle, $currentline);
}

echo "<H1>GET Values:</H1>\n";
foreach ($_GET as $name => $value) {
	$currentline =  $name.':'.$value."<BR>\n";
	echo $currentline;
	fwrite($handle, $currentline);
}

fclose($handle);
?>
	</BODY>
</HTML>