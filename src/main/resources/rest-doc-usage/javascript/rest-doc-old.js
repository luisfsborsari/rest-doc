/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: 3/10/11
 * Time: 4:27 PM
 * To change this template use File | Settings | File Templates.
 * http://stackoverflow.com/questions/4580193/json-result-to-html-newline
 * http://stackoverflow.com/questions/4253367/how-to-escape-a-json-string-containing-newline-characters-using-javascript
 * http://stackoverflow.com/questions/42068/how-do-i-handle-newlines-in-json
 *
 */

json_rest_service =  '(' + '['
    + '{"descriptionTag":"Pesquisa ordens de servico","methodTag":"cls.ServiceOrderController.find(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)","pathURI":"/serviceOrders","returnTag":["lista de ordens de servico no formato json"],"returnExampleTag":["{result:\\n  \\t[{ \\\"idos\\\": 1\\n  \\t, \\\"businessUnit\\\": 1\\n  \\t, \\\"subscricao\\\": 1\\n  \\t, \\\"ctdesigla\\\": 1\\n  \\t, \\\"protocoloos\\\": 1\\n  \\t, \\\"status\\\": 1\\n  \\t, \\\"descservico\\\": 1\\n  \\t, \\\"desctiposervico\\\": 1\\n  \\t, \\\"informacaoos\\\": 1 }]\\n  }"],"parametersTag":["soId o Id da ordem de serviços","businessUnit","subscription o subscription","sigla a sigla","protocol o protocolo","status o status"],"throwsTag":["DataException"]},{"descriptionTag":"Pesquisa ServiceOrderPhone","methodTag":"cls.ServiceOrderController.findByPhone(java.lang.String)","pathURI":"/serviceOrder/{phone}","returnTag":["ServiceOrderPhone"],"returnExampleTag":["{result:[{ \\\"idos\\\": 1, \\\"businessUnit\\\": 1, \\\"subscricao\\\": 1, \\\"ctdesigla\\\": 1, \\\"protocoloos\\\": 1, \\\"status\\\": 1, \\\"descservico\\\": 1, \\\"desctiposervico\\\": 1, \\\"informacaoos\\\": 1 }]}"],"parametersTag":["phone"],"throwsTag":["DataException"]},{"descriptionTag":"testeee","methodTag":"cls.Sample.main(java.lang.String[])","pathURI":"/serviceOrders","returnTag":["a text"],"returnExampleTag":["line 1\\n \\t\\t\\t line 2"],"parametersTag":["args"],"throwsTag":["fim"]}'
    + ']' + ')'