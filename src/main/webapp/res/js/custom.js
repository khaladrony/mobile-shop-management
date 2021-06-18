var HttpClient = function() {
    this.get = function(aUrl, aCallback) {
        var anHttpRequest = new XMLHttpRequest();
        anHttpRequest.onreadystatechange = function() {
            if (anHttpRequest.readyState === 4 && anHttpRequest.status === 200)
                aCallback(anHttpRequest.responseText);
        };

        anHttpRequest.open( "GET", aUrl, true );
        anHttpRequest.send( null );
    };
};

function validateFloatKeyPress(el, evt) {
    // console.log("evt.which=" + evt.which + ",evt.keyCode=" + evt.keyCode + ",evt.ctrlKey=" + evt.ctrlKey)


    if (evt.which == 0 || (evt.which == 118 && evt.ctrlKey == true)) {
        return true;
    }

    var charCode = (evt.which) ? evt.which : evt.keyCode;


    if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    var number = el.value.split('.');
    if (number.length == 2) {
        if (evt.which == 8 && number[1].length == 2) {
            return true;

        }
    }
    //just one dot
    if (number.length > 1 && charCode == 46) {
        return false;
    }
    // console.log(charCode)
    //get the carat position
    var caratPos = getSelectionStart(el);
    var dotPos = el.value.indexOf(".");
    // console.log(caratPos+","+dotPos)

    if (caratPos > dotPos && dotPos > -1 && (number[1].length > 1)) {
        return false;
    }
    return true;
}

function getSelectionStart(o) {
    if (o.createTextRange) {
        var r = document.selection.createRange().duplicate()
        r.moveEnd('character', o.value.length)
        if (r.text == '') return o.value.length
        return o.value.lastIndexOf(r.text)
    } else return o.selectionStart
}

function copyPasteStringRestrict(pastedString) {
    $(pastedString).on("paste", function (e) {
        var $this = $(this);
        setTimeout(function () {
            var val = $this.val(),
                regex = /^(\d+\.?\d*|\.\d+)$/;
            if (regex.test(val)) {
                var mystring;
                var dotSplit = val.split('.');
                if (dotSplit.length > 1) {
                    if (dotSplit[1].length > 2) {
                        val = Number(val).toFixed(2)
                    }
                }

                $this.val(val);
            } else {
                $this.val('');
            }
        }, 0);

    });
}



// 12 digits before and 2 digits after decimal
var iWords = ['Zero', ' One', ' Two', ' Three', ' Four', ' Five', ' Six', ' Seven', ' Eight', ' Nine'];
var ePlace = ['Ten', ' Eleven', ' Twelve', ' Thirteen', ' Fourteen', ' Fifteen', ' Sixteen', ' Seventeen', ' Eighteen', ' Nineteen'];
var tensPlace = ['', ' Ten', ' Twenty', ' Thirty', ' Forty', ' Fifty', ' Sixty', ' Seventy', ' Eighty', ' Ninety'];
var inWords = [];

var numReversed, actNumber, accNoIndex, inWordIndex;

function tensComplication() {
    if (actNumber[accNoIndex] === '0') {
        inWords[inWordIndex] = '';
    } else if (actNumber[accNoIndex] === '1') {
        inWords[inWordIndex] = ePlace[actNumber[accNoIndex - 1]];
    } else {
        inWords[inWordIndex] = tensPlace[actNumber[accNoIndex]];
    }
}

function amountToTextWithDecimal(n) {
    if (n == '') {
        return '';
    }
    var nums = n.toString().split('.');
    var whole = amountToText(nums[0]);
    if (nums.length == 2) {
        inWords = [];
        var fraction = amountToText(nums[1]);
        return 'Taka ' + whole + ' and Paisa ' + fraction + ' Only';
    } else {
        return 'Taka ' + whole + ' Only';
    }
}


function amountToText(val) {
    var junkVal = val;
    junkVal = Math.floor(junkVal);
    var obStr = junkVal.toString();
    numReversed = obStr.split('');
    actNumber = numReversed.reverse();

    if (Number(junkVal) >= 0) {
        //do nothing
    } else {
        swal("", "Invalid number cannot be converted!");
        return false;
    }
    if (Number(junkVal) === 0) {
//            document.getElementById('amountFooter').innerHTML = obStr + '' + 'Taka Zero Only';
//            return false;
        return 'Zero';
    }


    var iWordsLength = numReversed.length;
    var finalWord = '';
    inWordIndex = 0;
    for (accNoIndex = 0; accNoIndex < iWordsLength; accNoIndex++) {
        switch (accNoIndex) {
            case 0:
                if (actNumber[accNoIndex] === '0' || actNumber[accNoIndex + 1] === '1') {
                    inWords[inWordIndex] = '';
                } else {
                    inWords[inWordIndex] = iWords[actNumber[accNoIndex]];
                }
                inWords[inWordIndex] = inWords[inWordIndex];
                break;
            case 1:
                tensComplication();
                break;
            case 2:
                if (actNumber[accNoIndex] === '0') {
                    inWords[inWordIndex] = '';
                } else if (actNumber[accNoIndex - 1] !== '0' && actNumber[accNoIndex - 2] !== '0') {
                    inWords[inWordIndex] = iWords[actNumber[accNoIndex]] + ' Hundred and';
                } else {
                    inWords[inWordIndex] = iWords[actNumber[accNoIndex]] + ' Hundred';
                }
                break;
            case 3:
                if (actNumber[accNoIndex] === '0' || actNumber[accNoIndex + 1] === '1') {
                    inWords[inWordIndex] = '';
                } else {
                    inWords[inWordIndex] = iWords[actNumber[accNoIndex]];
                }
                if (actNumber[accNoIndex + 1] !== '0' || actNumber[accNoIndex] > '0') {
                    inWords[inWordIndex] = inWords[inWordIndex] + ' Thousand';
                }
                break;
            case 4:
                tensComplication();
                break;
            case 5:
                if (actNumber[accNoIndex] === '0' || actNumber[accNoIndex + 1] === '1') {
                    inWords[inWordIndex] = '';
                } else {
                    inWords[inWordIndex] = iWords[actNumber[accNoIndex]];
                }
                if (actNumber[accNoIndex + 1] !== '0' || actNumber[accNoIndex] > '0') {
                    inWords[inWordIndex] = inWords[inWordIndex] + ' Lakh';
                }
                break;
            case 6:
                tensComplication();
                break;
            case 7:

                if (actNumber[accNoIndex] === '0' || actNumber[accNoIndex + 1] === '1') {
                    inWords[inWordIndex] = '';
                } else {
                    inWords[inWordIndex] = iWords[actNumber[accNoIndex]];
                }
                inWords[inWordIndex] = inWords[inWordIndex] + ' Crore';
                break;
            case 8:
                tensComplication();
                break;
            case 9:
                if (actNumber[accNoIndex] === '0') {
                    inWords[inWordIndex] = '';
                } else if (actNumber[accNoIndex - 1] !== '0' && actNumber[accNoIndex - 2] !== '0') {
                    inWords[inWordIndex] = iWords[actNumber[accNoIndex]] + ' Hundred and';
                } else {
                    inWords[inWordIndex] = iWords[actNumber[accNoIndex]] + ' Hundred';
                }
                break;
            case 10:
                if (actNumber[accNoIndex] === '0' || actNumber[accNoIndex + 1] === '1') {
                    inWords[inWordIndex] = '';
                } else {
                    inWords[inWordIndex] = iWords[actNumber[accNoIndex]];
                }
                if (actNumber[accNoIndex + 1] !== '0' || actNumber[accNoIndex] > '0') {
                    inWords[inWordIndex] = inWords[inWordIndex] + ' Thousand';
                }
                break;
            case 11:
                tensComplication();
                break;
            default:
                break;
        }
        inWordIndex++;
    }

    inWords.reverse();
    for (accNoIndex = 0; accNoIndex < inWords.length; accNoIndex++) {
        finalWord += inWords[accNoIndex];
    }
    for (accNoIndex = 0; accNoIndex < inWords.length; accNoIndex++) {
        inWords.pop();
    }
//        document.getElementById('amount').innerHTML = obStr + '  ' + finalWord;
    return finalWord;
//        $('#amountFooter').val(finalWord);
}

