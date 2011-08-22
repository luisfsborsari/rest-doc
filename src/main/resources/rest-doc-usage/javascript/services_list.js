$(function(){
    buildServicesList()
    $("#servicesListLoading").hide()
})

function renderTag(tag, name, formatTagText) {
     var htmltag = ""
     if(tag != null) {
        htmltag += "<tr><td rowspan='" + tag.length + "'>" + name +": </td>"
        htmltag += "<td>"
        $.each(tag, function(i, v) {
            if(i > 0)
                htmltag += " </td></tr><tr><td> "
            if(formatTagText != null && (typeof formatTagText === 'function')) {
                htmltag += formatTagText(v)
            } else {
                htmltag += v
            }
        })
        htmltag += "</td></tr>"
        return htmltag;
    }
}

function parameter(value) {
    value = value.trim()
    var nomeParam;
    var descParam;
    var posNomeParam = value.indexOf(" ")
    if (posNomeParam == -1) {
        nomeParam = value
        descParam = ""
    }
    else {
        nomeParam = value.substring(0, posNomeParam)
        descParam = value.substring(posNomeParam + 1, value.length)
    }
    return { "nomeParam": nomeParam, "descParam": descParam};
}
function renderParameters(value) {
    var source = "<span class='nomeParam'>{{nomeParam}} : </span> {{descParam}}";
    var template = Handlebars.compile(source);
    var data = parameter(value);
    var result = template(data);

    return template(data);
}

function renderReturnExample(value) {
    if (value.length <= 1)
        return value;

    var return_value = ''
    $.each(value, function(i, v) {
        return_value += v
        if(v == '[' || v == '}')
         return_value += '<br />'
        })
    return return_value
}

function buildServicesList() {
    sl = $("#servicesList")
    sl.empty()
    $.each(servicesJson, function(index, value) {
        htmltext = "<h3><a href='#'>"
        htmltext += value.serviceMethod
        htmltext += "</a></h3>"
        htmltext += "<div>"
        htmltext += "<table style='width: 100%'>"

        htmltext += "<tr><td width='25%'>Description:</td>"
        htmltext += "<td width='75%'>" + value.description + "</td></tr>"
        htmltext += "<tr><td width='25%'>Request Type:</td>"
        htmltext += "<td width='75%'>" + value.requestType + "</td></tr>"
        htmltext += "<tr><td width='25%'>Path URI:</td>"
        htmltext += "<td width='75%'>" + value.pathURI + "</td></tr>"

        htmltext += renderTag(value.parameters, "Parameters", renderParameters)
        htmltext += "<tr><td width='25%'>Returns:</td>"
        htmltext += "<td width='75%'>" + value.returns + "</td></tr>"
        htmltext += "<tr><td width='25%'>Return Example:</td>"
        htmltext += "<td width='75%'>" + renderReturnExample(value.returnExample) + "</td></tr>"
        htmltext += renderTag(value.exceptionThrows, "Throws", null)
        htmltext += renderTag(value.calls, "Calls", null)

        htmltext += "</table>"
        htmltext += "</div>"
        sl.append(htmltext)
    })
    sl.accordion({ header: "h3" });
}
