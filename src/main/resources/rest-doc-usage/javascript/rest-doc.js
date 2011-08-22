servicesJson = [ {
	"pathURI" : "/person/{id}",
	"description" : "Get the person by id",
	"serviceMethod" : "br.com.bla.controllers.PersonController.getPersonById(java.lang.Integer)",
	"requestType" : "Get",
	"returns" : "Person object on JSON format",
	"returnExample" : "{ \"name\": \"John\", \"lastName\": \"Lee\", \"age\": 40}",
	"parameters" : [ "person id" ],
	"exceptionThrows" : [],
	"calls" : []
},
{
	"pathURI" : "/person/name/{name}/age/{age}",
	"description" : "Consulta histórico resumido do dia",
	"serviceMethod" : "br.com.bla.controllers.PersonController.getPersonByNameAndAge(java.lang.String, java.lang.Integer)",
	"requestType" : "Get",
	"returns" : "A list of person objects on JSON format",
	"returnExample" : "[ { \"name\": \"John\", \"lastName\": \"Lee\", \"age\": 40}, { \"name\": \"John\", \"lastName\": \"Joker\", \"age\": 40} ]",
	"parameters" : [ "person name", "person age" ],
	"exceptionThrows" : ["DataException"],
	"calls" : ["br.com.bla.controllers.PersonController.findPersonByName(java.lang.String)"]

}]
