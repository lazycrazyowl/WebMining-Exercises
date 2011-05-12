
function extractText(tagName){
	var text =  "";
	var cleantext = "";
	var paragraphs = document.getElementsByTagName(tagName);

	var open = '<';
	var close = '>';
	for(var i=0; i<paragraphs.length; i++) {
		text += paragraphs[i].innerHTML;
	}

	var doAppend = true;
	var tmp = "";
	for(var i=0; i<text.length; i++) {
		tmp = text.charAt(i);
		if( tmp == open ) {
			doAppend = false;
		}
		if(doAppend) {	
			cleantext += tmp;
		}	
		if( tmp == close ) {
			doAppend = true;
		}
	}
	//cleantext = unescape(cleantext);
	return cleantext;
}
postMessage(extractText('p') + extractText('div') + extractText('span'));
