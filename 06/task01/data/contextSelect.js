/*
* Highlight the the current HTMLElement. On click calls the collect function.
*/
function doElementSelect() {
	var currentNode = null;
	$('body').mousemove(function(event) {
	  if (jQuery(event.target) !== currentNode) {
		if(currentNode != null) {
			currentNode.css('background', '');
		}
		currentNode=$(event.target);
		currentNode.css('background', colorHighlight);
	  }
	});

	$('body').click(function(event) {
		//event.altKey
		//event.shiftKey
		if(event.which == 1 && event.ctrlKey) {
			var node = event.target;
			collect(node)
			jQuery(node).css('border', '2px solid ' + colorSelected );
		}
	});
}

self.port.on('doElementSelect', doElementSelect);
