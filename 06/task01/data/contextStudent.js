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
	console.log("execute");
	
	var left = model[0],
	    right = model[1],
	    htmlElement = document.getElementsByTagName('body')[0],
	    text = htmlElement.innerHTML,
	    newText = htmlElement.innerHTML,
	    start = text.indexOf(left)+left.length,
        end = text.indexOf(right),
        offsetText = 0;
        offsetNewText = 0;
	
	console.log(start);
	console.log(end);
	while (start >= 0 && end >= 0) {
	    console.log("Found: " + text.substring(start, end));
	    
        newText = newText.substring(0, start+offsetNewText) + 
            '<div style="background:#C9f">' +
            text.substring(start, end) + 
            '</div>' + 
            newText.substring(end+offsetNewText, newText.length);
	    
	    // Next
	    offsetNewText += '<div style="background:#C9f"></div>'.length + end + right.length;
	    text = text.substring(end+right.length, text.length);
	    start = text.indexOf(left)+left.length,
        end = text.indexOf(right);
        
        console.log(start +" " + end + " " + text.length);
	}
	
	//Example for marking text. Basically you need to identify your text an then
	//mark it with a css style. Feel free to replace your text with <div
	//style='background:#red'> TEXT </div> Or just put brackets around it.
	htmlElement.innerHTML=newText;

}

self.port.on('doExecute', execute);
