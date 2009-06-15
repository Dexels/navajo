<?PHP

if ($GetFiles == "true") {
	// first line: url-prefix for links (in this case empty as we have chosen to use
	// absolute links.
	print "\n";

	// further lines: <linkname>;<linkurl>
	// where linkname is a string and linkurl contains the relative or absolute url as string.
	// if url-prefix is not empty, the further lines can only contain relative urls to that prefix
	// (see GetImages)
	print "heise.de;;http://www.heise.de\n";
	print "google.de;;http://www.google.de\n";
	print "xeinfach;;http://www.kafenio.org\n";
	print "spiegel.de;;http://www.spiegel.de\n";
	print "screenshots/applet_text_chinese_kyrillic_german.gif;;http://www.kafenio.org/media/kafenio/screenshots/applet_text_chinese_kyrillic_german.gif\n";
	print "screenshots/dedi_javaeditor.gif;;http://www.kafenio.org/media/kafenio/screenshots/dedi_javaeditor.gif\n";
	print "screenshots/dedi_javaeditor_detached.gif;;http://www.kafenio.org/media/kafenio/screenshots/dedi_javaeditor_detached.gif\n";
}

if ($GetImages == "true") {
	// first line: url-prefix for images
	print "http://www.kafenio.org/media/kafenio/screenshots/\n";


	print "applet_text_chinese_kyrillic_german.gif\n";
	print "applet_text_chinese_kyrillic_german_dedi_thumb.gif\n";
	print "kafenio-20040706-113803.jpg\n";
	print "kafenio-20040706-113803_dedi_thumb.jpg\n";
	print "kafenio-20040706-113826.jpg\n";
	print "kafenio-20040706-113826_dedi_thumb.jpg\n";
	print "kafenio-20040706-113936.jpg\n";
	print "kafenio-20040706-113936_dedi_thumb.jpg\n";
	print "kafenio-20040706-114013.jpg\n";
	print "kafenio-20040706-114013_dedi_thumb.jpg\n";
	print "kafenio-20040706-114033.jpg\n";
	print "kafenio-20040706-114033_dedi_thumb.jpg\n";
	print "kafenio-20040706-114051.jpg\n";
	print "kafenio-20040706-114051_dedi_thumb.jpg\n";
	print "kafenio-20040706-114126.jpg\n";
	print "kafenio-20040706-114126_dedi_thumb.jpg\n";
	print "kafenio-20040706-114153.jpg\n";
	print "kafenio-20040706-114153_dedi_thumb.jpg\n";
	print "kafenio-20040706-131258.jpg\n";
	print "kafenio-20040706-131258_dedi_thumb.jpg\n";
	print "kafenio-20040706-131328.jpg\n";
	print "kafenio-20040706-131328_dedi_thumb.jpg\n";
	print "kafenio-20040706-131353.jpg\n";
	print "kafenio-20040706-131353_dedi_thumb.jpg\n";
	print "kafenio-20040706-131419.jpg\n";
	print "kafenio-20040706-131419_dedi_thumb.jpg\n";
	print "kafenio-20040706-131510.jpg\n";
	print "kafenio-20040706-131510_dedi_thumb.jpg\n";
	print "kafenio-20040706-131515.jpg\n";
	print "kafenio-20040706-131515_dedi_thumb.jpg\n";
	print "kafenio-20040706-131519.jpg\n";
	print "kafenio-20040706-131519_dedi_thumb.jpg\n";
	print "kafenio-20040706-131523.jpg\n";
	print "kafenio-20040706-131523_dedi_thumb.jpg\n";
	print "kafenio-20040706-131531.jpg\n";
	print "kafenio-20040706-131531_dedi_thumb.jpg\n";
	print "kafenio-20040706-131536.jpg\n";
	print "kafenio-20040706-131536_dedi_thumb.jpg\n";
	print "kafenio-20040706-131541.jpg\n";
	print "kafenio-20040706-131541_dedi_thumb.jpg\n";
	print "kafenio-20040706-131545.jpg\n";
	print "kafenio-20040706-131545_dedi_thumb.jpg\n";
	print "kafenio-20040706-131549.jpg\n";
	print "kafenio-20040706-131549_dedi_thumb.jpg\n";
	print "kafenio-20040706-131554.jpg\n";
	print "kafenio-20040706-131554_dedi_thumb.jpg\n";
	print "kafenio-20040706-131822.jpg\n";
	print "kafenio-20040706-131822_dedi_thumb.jpg\n";
	print "kafenio-20040706-133710.jpg\n";
	print "kafenio-20040706-133710_dedi_thumb.jpg\n";
}
?>
