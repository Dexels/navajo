
		function writeHeader( name, metadata, component  ) {
			var items = [];
			items.push('<tr>');
			$.each(metadata, function(key,val) {
				console.log(val);
				items.push('<td>'+val.description+'</td>\n');
			});
			items.push('</tr>');
			$(component+'> thead:last').append(items.join(''));
		}

		
		function writeRow( component, row ) {
			var items = [];
			items.push('<tr>');
			$.each(row, function(key,val) {
				items.push('<td>'+val+'</td>\n');
			});
			items.push('</tr>');
			$(component).append(items.join(''));
		}
		
		
		function writeTableBody(key, data, table) {
			$.each(data, function(key, val) {
				writeRow(table,val);
			});
		}

		function writeTable(name,data,metadata, selector) {
			//console.log(selector);
			$(selector+' tr').remove();
			writeHeader(name,metadata,selector);
			writeTableBody(name,data,selector);
		}
		
		function loadarticle(url,selector) {
			$.getJSON(url, function(data) {
				$.each(data.data, function(key,val) {
					console.log(key);
					writeTable(key,val,data.metadata[key],selector);
				});
			});
		}

		  $.ajaxSetup({"error":function(XMLHttpRequest,textStatus, errorThrown) {   
		      alert(textStatus);
		      alert(errorThrown);
		      alert(XMLHttpRequest.responseText);
		  }});