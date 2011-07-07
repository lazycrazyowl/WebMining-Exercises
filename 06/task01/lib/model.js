
/**
 * Find the shortest matching delimiters
 */
function induce(items) {
    var leftDel = items[0][0],
        rightDel = item[0][1];

    // Find the shortest matching left delimiter suffix
    // Find the shortest matching right delimiter prefix
    for(i=1; i<items.length;i++) {
		var item = items[i], 
		    left = item[0], 
		    right = item[1],
		    commRight = commonPrefix(rightDel, right),
		    commLeft = reverseStr(commonPrefix(reverseStr(leftDel), reverseStr(left)));
		
		if (commRight.length > 0) {
		    rightDel = commRight;
		}
		
		if (commLeft.length > 0) {
		    leftDel = commLeft;
		}
	}
	
	// Best length should be 20
	if (leftDel.length > 20) {
	    leftDel = leftDel.substring(leftDel.length-20, leftDel.length)
	}
	
	// Best length should be 20
	if (rightDel.length > 20) {
	    rightDel = rightDel.substring(0,20);
	}

	//Create your model here. In my simple case the items are my model. The
	//return type is free for you to chose. It is the same you will 
	//expect in your execute method.
	return [leftDel, rightDel]
}

function commonPrefix(str1, str2) {
    var len = Math.min(str.length, str2.length);
    
    var common = 0;
    for (i=0; i<len; i++) {
        if (str1[i] == str2[i]) {
            common = i
        } else {
            break;
        }
    }
    
    return str1.substring(0, common);
}

function reverseStr(str) {
    var splitext = str.split(""),
        rev = splittext.reverse();
        
    return rev.join("");
}

exports.induce = induce;
