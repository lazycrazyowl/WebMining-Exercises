var lang = require("language");
var util = require("utility");

function student(text) {
	var start = new Date().getTime()

	
	syllableHistogram = new SyllableHistogram(text)	

	letterLang = selectLanguageByLetterHistogram(new LetterHistogram(text))
	syllableLang = selectLanguageBySyllableHistogram(new SyllableHistogram(text))

	console.log("Letter lang: " + letterLang+". Syllable lang: " + syllableLang);

	console.log("Took " + (new Date().getTime() - start) +"ms")

	return letterLang
}

function LetterHistogram(text) {
	text = text.toUpperCase()
	
	for(i = 0; i < text.length; i++){
		if(text[i] in this)
			this[text[i]] += 1;
		else 
			this[text[i]] = 1;
	}
	this.filter = filter
}

function SyllableHistogram(text) {
	text = text.toUpperCase()
	
	for(i = 0; i < text.length-1; i++){
		syllabel = text[i]+text[i+1]
		if(syllabel in this)
			this[syllabel] += 1;
		else 
			this[syllabel] = 1;
	}
	
	//console.log(prettyPrint(this))

	this.filter = filter
}

function selectLanguageByLetterHistogram(histogram) {

	deRankL = rankHist('DE', histogram, lettersGerman())
	enRankL = rankHist('EN', histogram, lettersEnglish())
	frRankL = rankHist('FR', histogram, lettersFrench())

	return selectLang(deRankL, enRankL, frRankL)
}

function selectLanguageBySyllableHistogram(histogram) {
	
	deRankL = rankHist('DE', histogram, syllableGerman())
	enRankL = rankHist('EN', histogram, syllableEnglish())
	frRankL = rankHist('FR', histogram, syllableFrench())

	return selectLang(deRankL, enRankL, frRankL)
}

function selectLang(deRank, enRank, frRank) {
	if(deRank < enRank && deRank < frRank)
		return lang.german;
	else if(enRank < deRank && enRank < frRank)
		return lang.english;
	else if(frRank < enRank && frRank < deRank)
		return lang.french;
	return lang.default;
}

function rankHist(lang, histogram, alphabet) {
	
	localeHistogram = normalize(histogram.filter(alphabet))
	de = sortKeys(alphabet)
	is = sortKeys(localeHistogram)

	rnk = rank(is, de)

	console.log("IS: "+sortKeys(normalize(localeHistogram)))
	console.log(lang + ": "+sortKeys(alphabet))
	console.log(lang + " Rank is " + rnk);
	return rnk
}

function rank(isList, langList) {
	similarity = 0
	for(i = 0; i < isList.length; i++) {
		pos = langList.indexOf(isList[i])
		similarity += Math.abs(i - pos)/Math.log(i+2)
	}
	return similarity
}

function filter(availableLetters) {
	result = {}
	for(key in this) {
		if(key in availableLetters)
			result[key] = this[key]
	}
	return result
}

function normalize(dict) {
	result = {}	
	sum = 0.0
	for(key in dict)
		sum += dict[key]

	for(key in dict)
		result[key] = dict[key]/sum
	return result
}
	
function prettyPrint(dict) {
	var result = '{'
	for(letter in dict) {
		result += '\''+letter+'\':'+dict[letter]+',';
	}
	return result + '}';
}

function sortKeys(arr){

	// Setup Arrays
	var sortedKeys = [];
	var sortedObj = {};

	// Separate keys and sort them
	for (var i in arr){
		sortedKeys.push(i);
	}
	
	sortedKeys.sort( function(a, b) {
  		if ( arr[a] < arr[b] ) return -1;
  		else if ( arr[a] > arr[b] ) return 1;
  		else return 0;
	});

	return sortedKeys.reverse()
}


function syllableFrench() {
	return {
		'AI':0.0194,
		'AN':0.0133,
		'AR':0.0084,
		'AU':0.0087,
		'CE':0.0078,
		'CH':0.0082,
		'CO':0.0095,
		'DE':0.0231,
		'EM':0.0077,
		'EN':0.0245,
		'ER':0.014,
		'ES':0.0243,
		'ET':0.0128,
		'EU':0.0095,
		'IE':0.0081,
		'IL':0.01,
		'IN':0.0089,
		'IS':0.0122,
		'IT':0.0133,
		'LA':0.0123,
		'LE':0.026,
		'MA':0.0087,
		'ME':0.0123,
		'NE':0.0091,
		'NS':0.0082,
		'NT':0.0187,
		'ON':0.0169,
		'OU':0.0192,
		'PA':0.0087,
		'QU':0.0115,
		'RA':0.0092,
		'RE':0.0206,
		'SE':0.0103,
		'TE':0.0129,
		'TI':0.008,
		'TR':0.0079,
		'UE':0.0098,
		'UN':0.0076,
		'UR':0.0138,
		'US':0.0097,
		'VE':0.0088,
	};
}

function syllableGerman() {
	return {
		'AN':0.0126,
		'AU':0.0093,
		'BE':0.0137,
		'CH':0.0358,
		'DA':0.0082,
		'DE':0.0254,
		'DI':0.0106,
		'EI':0.0244,
		'EL':0.0076,
		'EN':0.0487,
		'ER':0.0418,
		'ES':0.0138,
		'GE':0.0188,
		'HE':0.013,
		'HT':0.0079,
		'IC':0.0149,
		'IE':0.0215,
		'IN':0.0214,
		'IT':0.008,
		'LE':0.0085,
		'LI':0.0075,
		'ND':0.0168,
		'NE':0.0128,
		'NG':0.009,
		'RE':0.0111,
		'SC':0.0099,
		'SE':0.011,
		'SI':0.0089,
		'ST':0.0129,
		'TE':0.0209,
		'UN':0.0169,
	};
}

function syllableEnglish() {
	return {
		'AL':0.0093,
		'AN':0.0217,
		'AR':0.0106,
		'AS':0.0109,
		'AT':0.0117,
		'EA':0.0084,
		'ED':0.0129,
		'EN':0.0137,
		'ER':0.0211,
		'ES':0.01,
		'HA':0.0117,
		'HE':0.0365,
		'HI':0.0107,
		'IN':0.021,
		'IS':0.0099,
		'IT':0.0124,
		'LE':0.0095,
		'ME':0.0083,
		'ND':0.0162,
		'NE':0.0075,
		'NG':0.0099,
		'NT':0.0077,
		'ON':0.0136,
		'OR':0.0109,
		'OU':0.0141,
		'RE':0.0164,
		'SE':0.0085,
		'ST':0.0096,
		'TE':0.01,
		'TH':0.0399,
		'TI':0.0092,
		'TO':0.0124,
		'VE':0.0111,
		'WA':0.0084,
	};
}

function lettersFrench() {
	return {
		'A':0.0813,
		'B':0.0093,
		'C':0.0315,
		'D':0.0355,
		'E':0.151,
		'F':0.0096,
		'G':0.0097,
		'H':0.0108,
		'I':0.0694,
		'J':0.0071,
		'K':0.0016,
		'L':0.0568,
		'M':0.0323,
		'N':0.0642,
		'O':0.0527,
		'P':0.0303,
		'Q':0.0089,
		'R':0.0643,
		'S':0.0791,
		'T':0.0711,
		'U':0.0605,
		'V':0.0183,
		'W':0.0004,
		'X':0.0042,
		'Y':0.0019,
		'Z':0.0021,
		'Œ':0.0001,
		'À':0.0054,
		'Â':0.0003,
		'Ç':0.0,
		'È':0.0035,
		'É':0.0213,
		'Ê':0.0024,
		'Ë':0.0001,
		'Î':0.0003,
		'Ï':0.0,
		'Ô':0.0007,
		'Ù':0.0002,
		'Û':0.0005,
		'Ü':0.0002,
	};
}

function lettersEnglish() {
	return {
		'A':0.0834,
		'B':0.0154,
		'C':0.0273,
		'D':0.0414,
		'E':0.126,
		'F':0.0203,
		'G':0.0192,
		'H':0.0611,
		'I':0.0671,
		'J':0.0023,
		'K':0.0087,
		'L':0.0424,
		'M':0.0253,
		'N':0.068,
		'O':0.077,
		'P':0.0166,
		'Q':0.0009,
		'R':0.0568,
		'S':0.0611,
		'T':0.0937,
		'U':0.0285,
		'V':0.0106,
		'W':0.0234,
		'X':0.002,
		'Y':0.0204,
		'Z':0.0006,
	};
}

function lettersGerman() {
	return {
		'A':0.0558,
		'B':0.0196,
		'C':0.0316,
		'D':0.0498,
		'E':0.1693,
		'F':0.0149,
		'G':0.0302,
		'H':0.0498,
		'I':0.0802,
		'J':0.0024,
		'K':0.0132,
		'L':0.036,
		'M':0.0255,
		'N':0.1053,
		'O':0.0224,
		'P':0.0067,
		'Q':0.0002,
		'R':0.0689,
		'S':0.0642,
		'T':0.0579,
		'U':0.0383,
		'V':0.0084,
		'W':0.0178,
		'X':0.0005,
		'Y':0.0005,
		'Z':0.0121,
		'Ä':0.0054,
		'Ö':0.003,
		'Ü':0.0065,
		'ß':0.0037,
	};
}

exports.student = student;
