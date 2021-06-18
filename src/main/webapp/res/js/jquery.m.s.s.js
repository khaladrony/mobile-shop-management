
function drawLineChart(_dataArray, _options, _htmlElement) {

    var data = new google.visualization.arrayToDataTable( eval('(' + _dataArray + ')') );
    var chart = new google.visualization.LineChart(document.getElementById(_htmlElement));
    chart.draw(data, _options);
}

function drawLineChartWithEvent(_dataArray, _options, _htmlElement) {

    var data = new google.visualization.arrayToDataTable( eval('(' + _dataArray + ')') );
    var chart = new google.visualization.LineChart(document.getElementById(_htmlElement));
    
    google.visualization.events.addListener(chart, 'select', selectHandler);
    
    chart.draw(data, _options);
    
    function selectHandler() {
        var selectedItem = chart.getSelection()[0];
        if (selectedItem) {
            //var value = data.getValue(selectedItem.row, selectedItem.column);
            var disease = data.getValue(selectedItem.row, 0);
            var obj = angular.element(document.getElementById('TopDiseasesCrtl')).scope().getDisease(disease);
            
            angular.element(document.getElementById('TopIntervendedDiseaseCrtl')).scope().getTopIntervendedDiseases(obj);
        }
    }
}




function drawComboChart(_dataArray, _options, _htmlElement) {

    var data = new google.visualization.arrayToDataTable( eval('(' + _dataArray + ')') );
    var chart = new google.visualization.ComboChart(document.getElementById(_htmlElement));
    chart.draw(data, _options);
}

function drawColumnChart(_dataArray, _options, _htmlElement) {
    var data = new google.visualization.arrayToDataTable( eval('(' + _dataArray + ')') );

    var view = new google.visualization.DataView(data);
    view.setColumns([0, 1,
                     { calc: "stringify",
                       sourceColumn: 1,
                       type: "string",
                       role: "annotation" },
                     2]);

      var chart = new google.visualization.ColumnChart(document.getElementById(_htmlElement));
      chart.draw(view, _options);
}


function drawPieChart(_dataArray, _options, _htmlElement) {
    var data = new google.visualization.arrayToDataTable(eval('(' + _dataArray + ')'));

    var chart = new google.visualization.PieChart(document.getElementById(_htmlElement));
    chart.draw(data, _options);
}

function drawGaugeChart(_dataArray, _options, _htmlElement, _topVal) {
    
    var data = google.visualization.arrayToDataTable(eval('(' + _dataArray + ')'));
    
    var chart = new google.visualization.Gauge(document.getElementById(_htmlElement));
    chart.draw(data, _options);
    
    // the following portion for animation
    console.log("TOP VALUES: " + JSON.stringify(_topVal));
    /*for( var i = 0; i < _topVal.length; i++ ){
        setInterval(function() {
            data.setValue(i, 1, _topVal[i]); // i == position of chart, 1 for start from anim, _topVal[i] for end of anim
            chart.draw(data, _options);
        }, 100);
    }*/
    
    setInterval(function() {
        data.setValue(0, 1, _topVal[0]); // i == position of chart, 1 for start from anim, _topVal[i] for end of anim
        chart.draw(data, _options);
    }, 100);
    
}




function drawStackedChart(_dataArray, _options, _htmlElement, TYPE) {

    var data = google.visualization.arrayToDataTable( eval('(' + _dataArray + ')') );
     var chart ;
    if( TYPE === "BAR" ){
         chart = new google.visualization.BarChart(document.getElementById(_htmlElement));

    } else if(TYPE === "COLUMN"){
         chart = new google.visualization.ColumnChart(document.getElementById(_htmlElement));
       
    }else {
        return null;
    }
     chart.draw(data, _options);
}

function getMonthIndex( _month ){
    if( _month === "Jan" || _month === "January" ) return "01";
    if( _month === "Feb" || _month === "February" ) return "02";
    if( _month === "Mar" || _month === "March" ) return "03";
    if( _month === "Apr" || _month === "April" ) return "04";
    if( _month === "May" || _month === "May" ) return "05";
    if( _month === "Jun" || _month === "June" ) return "06";
    if( _month === "Jul" || _month === "July" ) return "07";
    if( _month === "Aug" || _month === "August" ) return "08";
    if( _month === "Sep" || _month === "September" ) return "09";
    if( _month === "Oct" || _month === "October" ) return "10";
    if( _month === "Nov" || _month === "November" ) return "11";
    if( _month === "Dec" || _month === "December" ) return "12";
}

function getMonthName( _number ){
    
    if( _number === 1 ) return "Jan";
    if( _number === 2 ) return "Feb";
    if( _number === 3 ) return "Mar";
    if( _number === 4 ) return "Apr";
    if( _number === 5 ) return "May";
    if( _number === 6 ) return "Jun";
    if( _number === 7 ) return "Jul";
    if( _number === 8 ) return "Aug";
    if( _number === 9 ) return "Sep";
    if( _number === 10 ) return "Oct";
    if( _number === 11 ) return "Nov";
    if( _number === 12 ) return "Dec";
}


function initMap(teams) {

    var centerLatLng = {lat: 21.196029, lng: 92.155621};  //Gundum 21.196029, 92.155621

    var map = new google.maps.Map(document.getElementById('map_canvas'), {
            zoom: 13,
            center: centerLatLng
    });


    var i;

    for (i = 0; i < teams.length; i++) {

        if( teams[i].LAT > 0 && teams[i].LNG > 0 ){
            
            var marker = new google.maps.Marker({
                    position: new google.maps.LatLng(teams[i].LAT, teams[i].LNG),
                    map: map,
                    draggable: false,
                    animation: google.maps.Animation.DROP,
                    icon:{
                        url: (teams[i].TYPE === 'TEAM')? _baseurl_ + 'img/hospital.png':_baseurl_ + 'img/education.png',
                        scaledSize: new google.maps.Size(16, 16)
                    },
                    //title: teams[i].TEAM,
                    title: (teams[i].TYPE === 'TEAM')? teams[i].SHORT_NAME + ' (' + teams[i].SERVICE + ')': teams[i].SHORT_NAME,
                    label: {
                        //text: teams[i].SERVICE > 0? teams[i].TEAM + ' (' + teams[i].SERVICE + ')' : teams[i].TEAM ,
                        //text: (teams[i].TYPE === 'TEAM')? teams[i].SHORT_NAME + ' (' + teams[i].SERVICE + ')': teams[i].SHORT_NAME,
                        text: ' ',
                        color: '#793905',
                        fontSize: "12px",
                        //fontWeight: "bold",
                        shadow: "2px 2px #ffffff"
                    }
            });
            var infowindow = new google.maps.InfoWindow({}); // uncomment this line to show the info window
            google.maps.event.addListener(marker, 'click', (function(marker, i) {
                return function() {
                    var sts = (teams[i].STATUS===true)? 'Active':'Inactive';
                    
                    if( teams[i].TYPE === 'TEAM' ){
                        var contentString = '<div id="content">'+
                        '<div id="siteNotice"><b style="color:#0F1985;">'+ teams[i].TEAM +'</b></div>'+
                        '<div id="bodyContent" style="border-top:1px solid #e1e1e1;border-bottom:1px solid #e1e1e1;">'+
                        '<table>' +
                        '<tr><td>Total service: '+ teams[i].SERVICE +'</td></tr>'+
                        '<tr><td>Started: '+ teams[i].STARTED +'</td></tr>'+
                        '<tr><td>Current status: '+ sts +'</td></tr></table>'+
                        '</div>'+
                        '</div>';
                    } else{
                        var contentString = '<div id="content">'+
                        '<div id="siteNotice"><b style="color:#0F1985;">'+ teams[i].TEAM +'</b></div>'+
                        '<div id="bodyContent" style="border-top:1px solid #e1e1e1;border-bottom:1px solid #e1e1e1;">'+
                        '<table>' +
                        '<tr><td>Started: '+ teams[i].STARTED +'</td></tr>'+
                        '<tr><td>Current status: '+ sts +'</td></tr></table>'+
                        '</div>'+
                        '</div>';
                    }
                    
                    infowindow.setContent(contentString);
                    infowindow.open(map, marker);
                }
            })(marker, i));
        }
    }

}

function initMapInfoWindow(teams) {

    if( teams.length < 1 ){
        /*teams = [
            {"name":"Ukhia Refugee Camp", "lat":21.234454, "lng":92.1574991, "value":450, "desc":"this is test 1" },
            {"name":"Kutupalong Refugee Camp", "lat":21.2109614, "lng":92.1605087, "value":350, "desc":"this is test 2" }
        ];*/
        
        /*var child = document.getElementById("googlemapdiv");
        child.parentNode.removeChild(child);
        return;*/
    }

    var centerLatLng = {lat: 21.215175, lng: 92.154216};  //Kutupalong Refugee

    var map = new google.maps.Map(document.getElementById('map_canvas'), {
            zoom: 13,
            center: centerLatLng
    });


    var marker, i;

    for (i = 0; i < teams.length; i++) {

        if( teams[i].LAT > 0 && teams[i].LNG > 0 ){
            
            var contentString = '<div id="content">'+
                    '<div id="siteNotice"><b style="color:#0F1985;">'+ teams[i].TEAM +'</b></div>'+
                    /*'<h4 id="firstHeading" class="firstHeading">Health Service</h4>'+*/
                    '<div id="bodyContent" style="border-top:1px solid #e1e1e1;border-bottom:1px solid #e1e1e1;">'+
                    '<table><tr>' +
                    '<td rowspan="2"><img src="../img/logo.png" style="width:60px; height:25px;" /></td>'+
                    '<td>&nbsp;</td></tr>'+
                    '<tr><td>&nbsp;</td></tr></table>'+
                    '</div>'+
                    '<div id="siteNotice">Copyright @ <a href="https://friendship.ngo/" target="_blank">Friendship</a></div>'+
                    '</div>';

            var infoWindow = new google.maps.InfoWindow({
                    content: contentString,
                    map: map,
                    position: new google.maps.LatLng(teams[i].LAT, teams[i].LNG) 
            });
            infoWindow.open(map);
        }
    }

}


function setFocus(elementId){
    $(document).find("input#"+elementId).focus();
}

function triggerSparkLineChart(){
    $('.inlinesparkline').sparkline('html', {width: '140px', height:'26px'});
}

function triggerDataTable(){
    $('.data_table').DataTable({
        'paging'      : true,
        'pageLength'  : 10,
        'lengthChange': false,
        'searching'   : false,
        'ordering'    : true,
        'info'        : true,
        'autoWidth'   : false
    });
}

function addDays(theDate, days) {
    return new Date(theDate.getTime() + days*24*60*60*1000);
}