
function getTextSelection() {
	var t = '';
	window.selection.getRange
	t = window.getSelection();
	return t;
}

var colorHighlight = '#ffff00';
var colorSelected = '#00ff00';
var colorExtracted = '#ff0000';

function reload() {
	window.location.reload();
}

self.port.on('reload', reload);
