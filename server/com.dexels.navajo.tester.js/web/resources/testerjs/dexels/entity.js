function processEntity(script, navajoelement) {
  $xml = $( xml )
  var ops = $('<ul>');

  $xml.children('tml').children('operations').children('operation').each(function(){
    ops.append(entityHtml(script, $xml, $(this).attr('method')));
  });

  navajoelement.html(ops);
  navajoelement.append($('<img>', {'src':"/testerimg/beta.png", 'class': 'betaimg'}))

}


function entityHtml(script, xmlObj, method) {
    var li = $('<li>', {'class': 'operation '+method});
    var a = $('<a>', {'href':'#', 'class':'entity'});
    li.append(a);
    var opHeader = $('<div>', {'class': 'operationHeader '+method})
    a.append(opHeader);
    var methodGet = $('<div>', {'class': 'method http'+method });
    methodGet.text(method);
    opHeader.append(methodGet);

    var url = $('<div>', {'class': 'url' });
    url.text(script);
    opHeader.append(url);
    var input = "{\n"
    $xml.children('tml').children('message').children('property').each(function(){

      var key = $(this).attr('key');
      if (typeof key !== typeof undefined && key !== false) {
        input += '    ';
        input += $(this).attr('name');
        input += ' : ';
        input += '"' + $(this).attr('type');
        if (key.indexOf('optional') !== -1) {
        	 input += ',optional';
        }
        input +=  '",\n';
      }
    });
    input = input.substring(0, input.length - 2);
    input += '\n}'
    var inputdiv = $('<textarea>', {'rows':'4', 'cols':'50', 'class': 'entityinput', 'id': 'input'+script+method});
    inputdiv.text(input);
    li.append(inputdiv);
    var rundiv = $('<a>', {'class': 'runentity', 'entity': script, 'method': method});
    rundiv.text('RUN')
    li.append(rundiv);
    return li;
}

$(document).on('click', '.runentity', function() {
  window.alert($(this).attr('entity'));

});
