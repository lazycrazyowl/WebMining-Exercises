const data = require("self").data;
const widgets = require("widget");
const pageMod = require("page-mod");
const store = require("simple-storage");
const selection = require("selection");
const contextMenu = require("context-menu");
const pref = require("preferences-service");
const windows = require("windows").browserWindows;
const model = require("model");
var widget = null;
var workers = new Array()

initPreferences();
initWidget();
initItemStore();
initPageMod();

/*
* Initialize default extension settings.
*/
function initPreferences() {
	var values = {
		'extensions.ke.learn': true
	};

	var keys = Object.keys(values);
	for(i=0; i<keys.length; i++) {
		if(!pref.has(keys[i])) {
			pref.set(keys[i], values[keys[i]]);
		}
	}
}

/*
* Initialize Widget
*/
function initWidget() {
	widget = widgets.Widget({
		id:'LR-Widget',
		label:'LR-Widget',
		contentURL:data.url('keicon.png'),
		onClick: function() {
			store.storage.model = model.induce(store.storage.items);
			for each (w in workers) {
					w.port.emit('reload');
			}
			if (pref.get('extensions.ke.learn')) {
				pref.set('extensions.ke.learn',false);
				widget.contentURL = data.url('keicon_red.png');
			} else {
				pref.set('extensions.ke.learn',true);
				resetModel();
				widget.contentURL = data.url('keicon_green.png');
			}
		}
	});

	if (pref.get('extensions.ke.learn')) {
		widget.contentURL = data.url('keicon_green.png');
	} else {
		widget.contentURL = data.url('keicon_red.png');
	}
}

/*
* Initialize storage for model
*/
function initItemStore() {
	if(typeof store.storage.items=='undefined') {
		store.storage.items = new Array();
	}
	if(typeof store.storage.model=='undefined') {
		store.storage.model = null;	
	}
}

/*
* Clear the model
*/
function resetModel() {
	store.storage.model = new Array();
	store.storage.items = new Array();
}

/*
* Add an item to the model.
*/
function addItem(item) {
	store.storage.items.push(item);
}

/*
* Clear detached workers from memory
*/
function detachWorker(worker, workerArray) {
	var index = workerArray.indexOf(worker);
	if(index != -1) {
		workerArray.splice(index, 1);
	}
}

/*
* Initialize workers
*/
function initWorker(worker) {
	worker.port.on("addItem", addItem);
	worker.on('detach', function() {
		detachWorker(worker, workers);
	});
	workers.push(worker);
	if ( pref.get('extensions.ke.learn') ) {
		worker.port.emit("doElementSelect");
	} else  {
		//store.storage.model = store.storage.items;
		worker.port.emit("doExecute", store.storage.model);
	}
}

/*
* Initialize the page mod
*/
function initPageMod() {
	pageMod.PageMod({
		include:["*"],
		contentScriptWhen:'end',
		contentScriptFile: [data.url('jquery.js'),data.url('utility.js'), data.url('contextSelect.js'), data.url('contextStudent.js')],
		onAttach: initWorker
	})
}
