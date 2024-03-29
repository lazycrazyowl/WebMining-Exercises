var lang = require("language");
var util = require("utility");


function germanWordlist() {
	return new Array(
		"aber",
		"als",
		"am",
		"an",
		"auch",
		"auf",
		"aus",
		"bei",
		"bin",
		"bis",
		"bist",
		"da",
		"dadurch",
		"daher",
		"darum",
		"das",
		"dass",
		"dein",
		"deine",
		"dem",
		"den",
		"der",
		"des",
		"dessen",
		"deshalb",
		"die",
		"dies",
		"dieser",
		"dieses",
		"doch",
		"dort",
		"du",
		"durch",
		"ein",
		"eine",
		"einem",
		"einen",
		"einer",
		"eines",
		"er",
		"es",
		"euer",
		"eure",
		"für",
		"hatte",
		"hatten",
		"hattest",
		"hattet",
		"hier",
		"hinter",
		"ich",
		"ihr",
		"ihre",
		"im",
		"in",
		"ist",
		"ja",
		"jede",
		"jedem",
		"jeden",
		"jeder",
		"jedes",
		"jener",
		"jenes",
		"jetzt",
		"kann",
		"kannst",
		"können",
		"könnt",
		"machen",
		"mein",
		"meine",
		"mit",
		"muss",
		"musst",
		"musst",
		"müssen",
		"müsst",
		"nach",
		"nachdem",
		"nein",
		"nicht",
		"nun",
		"oder",
		"seid",
		"sein",
		"seine",
		"sich",
		"sie",
		"sind",
		"soll",
		"sollen",
		"sollst",
		"sollt",
		"sonst",
		"soweit",
		"sowie",
		"und",
		"unser",
		"unsere",
		"unter",
		"vom",
		"von",
		"vor",
		"wann",
		"warum",
		"was",
		"weiter",
		"weitere",
		"wenn",
		"wer",
		"werde",
		"werden",
		"werdet",
		"weshalb",
		"wie",
		"wieder",
		"wieso",	
		"wir",
		"wird",
		"wirst",
		"wo",
		"woher",
		"wohin",
		"zu",
		"zum",
		"zur",
		"über");

}

function englishWordlist() {
	return new Array(
		"a",
		"about",
		"above",
		"after",
		"again",
		"against",
		"all",
		"am",
		"an",
		"and",
		"any",
		"are",
		"aren't",
		"as",
		"at",
		"be",
		"because",
		"been",
		"before",
		"being",
		"below",
		"between",
		"both",
		"but",
		"by",
		"can't",
		"cannot",
		"could",
		"couldn't",
		"did",
		"didn't",
		"do",
		"does",
		"doesn't",
		"doing",
		"don't",
		"down",
		"during",
		"each",
		"few",
		"for",
		"from",
		"further",
		"had",
		"hadn't",
		"has",
		"hasn't",
		"have",
		"haven't",
		"having",
		"he",
		"he'd",
		"he'll",
		"he's",
		"her",
		"here",
		"here's",
		"hers",
		"herself",
		"him",
		"himself",
		"his",
		"how",
		"how's",
		"i",
		"i'd",
		"i'll",
		"i'm",
		"i've",
		"if",
		"in",
		"into",
		"is",
		"isn't",
		"it",
		"it's",
		"its",
		"itself",
		"let's",
		"me",
		"more",
		"most",
		"mustn't",
		"my",
		"myself",
		"no",
		"nor",
		"not",
		"of",
		"off",
		"on",
		"once",
		"only",
		"or",
		"other",
		"ought",
		"our",
		"ours",
		"ourselves",
		"out",
		"over",
		"own",
		"same",
		"shan't",
		"she",
		"she'd",
		"she'll",
		"she's",
		"should",
		"shouldn't",
		"so",
		"some",
		"such",
		"than",
		"that",
		"that's",
		"the",
		"their",
		"theirs",
		"them",
		"themselves",
		"then",
		"there",
		"there's",
		"these",
		"they",
		"they'd",
		"they'll",
		"they're",
		"they've",
		"this",
		"those",
		"through",
		"to",
		"too",
		"under",
		"until",
		"up",
		"very",
		"was",
		"wasn't",
		"we",
		"we'd",
		"we'll",
		"we're",
		"we've",
		"were",
		"weren't",
		"what",
		"what's",
		"when",
		"when's",
		"where",
		"where's",
		"which",
		"while",
		"who",
		"who's",
		"whom",
		"why",
		"why's",
		"with",
		"won't",
		"would",
		"wouldn't",
		"you",
		"you'd",
		"you'll",
		"you're",
		"you've",
		"your",
		"yours",
		"yourself",
		"yourselves"
	);
}

function norwegianWordlist() {
	return new Array(
		"alle",
		"andre",
		"arbeid",
		"av",
		"begge",
		"bort",
		"bra",
		"bruke",
		"da",
		"denne",
		"der",
		"deres",
		"det",
		"din",
		"disse",
		"du",
		"eller",
		"en",
		"ene",
		"eneste",
		"enhver",
		"enn",
		"er",
		"et",
		"folk",
		"for",
		"fordi",
		"fors∅ke",
		"fra",
		"få",
		"f∅r",
		"f∅rst",
		"gjorde",
		"gj∅re",
		"god",
		"gå",
		"ha",
		"hadde",
		"han",
		"hans",
		"hennes",
		"her",
		"hva",
		"hvem",
		"hver",
		"hvilken",
		"hvis",
		"hvor",
		"hvordan",
		"hvorfor",
		"i",
		"ikke",
		"inn",
		"innen",
		"kan",
		"kunne",
		"lage",
		"lang",
		"lik",
		"like",
		"makt",
		"mange",
		"med",
		"meg",
		"meget",
		"men",
		"mens",
		"mer",
		"mest",
		"min",
		"mye",
		"må",
		"måte",
		"navn",
		"nei",
		"ny",
		"nå",
		"når",
		"og",
		"også",
		"om",
		"opp",
		"oss",
		"over",
		"part",
		"punkt",
		"på",
		"rett",
		"riktig",
		"samme",
		"sant",
		"si",
		"siden",
		"sist",
		"skulle",
		"slik",
		"slutt",
		"som",
		"start",
		"stille",
		"så",
		"tid",
		"til",
		"tilbake",
		"tilstand",
		"under",
		"ut",
		"uten",
		"var",
		"ved",
		"verdi",
		"vi",
		"vil",
		"ville",
		"vite",
		"vår",
		"være",
		"vært",
		"å"
	);
}

function countOccurences(words, stopwords) {
	var r = 0;
	for(var i in words) {
		if(stopwords.indexOf(words[i]) != -1)
			r++;
	}
	return r;
}

function student(text) {
	
	var wordList = text.split(' ');

	var englishStopwordCount = countOccurences(wordList, englishWordlist());
	var germanStopwordCount = countOccurences(wordList, germanWordlist());
	var norwegianStopwordCount = countOccurences(wordList, norwegianWordlist());

	console.log("german " + germanStopwordCount);
	console.log("norwegian "   + norwegianStopwordCount);
	console.log("english " + englishStopwordCount);

	if(norwegianStopwordCount > germanStopwordCount && norwegianStopwordCount > englishStopwordCount)
		return lang.norwegian;
	else if (germanStopwordCount > englishStopwordCount && germanStopwordCount > norwegianStopwordCount)
		return lang.german;
	else if (englishStopwordCount > germanStopwordCount && englishStopwordCount > norwegianStopwordCount)
		return lang.english;

	return lang.default;
}

exports.student = student;
