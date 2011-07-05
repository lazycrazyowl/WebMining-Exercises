/* 
Put your program code here. 

Element is of type HTMLElement which is documented here ->
http://www.w3schools.com/jsref/dom_obj_all.asp

You can use jQuery(element) to get access to jQuery functions ->
http://docs.jquery.com/Main_Page, which is useful for highlighting elements via
css call.
*/
function collect(element) {
	var item = ['L_i','R_i'];
	//Get your DOM Element
	item = element.nodeName;	
	//Get your Text
	var itemText = element.innerHTML; //Get html from Element
	
	//Get Parent 
	var context = element.parentNode.innerHTML
	
	// Note1: Only search in the body
	var html = document.getElementsByTagName('body')[0].innerHTML; //Get entire HTML
	
	//and generate a context
	var startPos = html.indexOf(itemText)
	var endPos = startPos + itemText.length
	
	var startContext = html.substring(startPos - 30, startPos);
	var endContext = html.substring(endPos, endPos + 30);

	console.log("Search for " + itemText);
	console.log("Start context is " + startContext);
	console.log("End context is " + endContext);

	//You need to add items to the persistent part of the script so don't remove
	//this line. Think of it as a return statement.
	self.port.emit("addItem", [startContext, endContext]);
}


function execute(model) {

	//Example for marking nodes with jQuery
	console.log("execute")
	
	for(i=0; i<model.length;i++) {
		var nodeName = model[i];
		console.log("start " + nodeName[0]);
		console.log("end " + nodeName[1]);
		//jQuery(nodeName).css('border', '2px solid ' + colorExtracted);
	}

	//Example for marking text. Basically you need to identify your text an then
	//mark it with a css style. Feel free to replace your text with <div
	//style='background:#red'> TEXT </div> Or just put brackets around it.
	var htmlElement = document.getElementsByTagName('html')[0];
	var htmlText = htmlElement.innerHTML;
	htmlText = htmlText.replace('body', 'body style="background:#CC99ff"');
	htmlElement.innerHTML=htmlText;

}

self.port.on('doExecute', execute);
