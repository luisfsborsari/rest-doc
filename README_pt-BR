# REST-DOC - Gerador de documentação para serviços REST criados com VRaptor

Este projeto consiste em um plugin do Maven para geração automática de documentação de serviços REST criados com VRaptor.
A documentação é gerada no fomato JSON.


## Documentação dos serviços

Os métodos são identificados como serviço pelo REST-DOC por possuírem as seguintes anotações:
- br.com.caelum.vraptor.Path
- br.com.caelum.vraptor.Get
- br.com.caelum.vraptor.Put
- br.com.caelum.vraptor.Delete
- br.com.caelum.vraptor.Post

Cada método que possua uma destas anotações tem as seguintes informações extraídas:
-Descrição: a descrição do javadoc
-Parametros: a tag @param do javadoc, com uma tag para cada parametro e o nome do parametro como a primeira palavra.
-Retorno: Descrição do retorno do serviço identificado pela tag @return do javadoc. 
-Exemplo de retorno: Um exemplo de retorno no formato Json, identificado pela tag @returnExample.
-Exceções: As exceções lançadas pelo serviço identificadas pela tag @throws, com uma tag para cada exceção.
-URI do serviço: a URI que está definida na annotation
-Chamadas a outros serviços: com a anotação @calls, pode-se informar quais outros serviços são invocados pelo serviço em questão. Os nomes 
dos serviços devem ser escritos entre vírgulas. Não há necessidade de escrever o nome completo e nem os parâmetros.

Exemplo:
<code>
	/**
	 * Busca cliente por id
	 * @param id
	 * @return cliente no formato json
	 * @returnExample
	 * { "name": "Paulo", "profissao": "Jornalista", idade: 40 } 
	 * @throws DataException
	   @calls br.com.pacote1.nomeDoServico, outroServico() , pacote2.umOutroServico(), com.pacote2.esseEhOutroServico(String param)
	 */
@Get("/get/client/{id}")
public Client getClient(Integer Id) throws DataException {
...
}
</code>


## Resultado REST-DOC:
[
  {
    "pathURI":"/get/client/{id}",
    "descriptionTag":"Busca cliente por id",
    "methodTag":"br.com.blah.controllers.ClientController.getClient(Integer)",
    "methodRequestType":"Get",
    "returnTag":["cliente no formato json"],
    "returnExampleTag":	{ "name": "Paulo", "profissao": "Jornalista", idade: 40 }
    "parametersTag":["id"],
    "throwsTag":["DataException"],
    "callsTag": ["br.com.pacote1.nomeDoServico, outroServico() , pacote2.umOutroServico(), com.pacote2.esseEhOutroServico(String param)"]
  }
]


## Configuração do plugin

Após a instalação do plugin no repositório local, o plugin deve ser adicionado ao POM.xml do projeto onde se encontram as classes que serão documentadas:

<code>
	<plugins>
            <plugin>
                <groupId>rest-doc</groupId>
                <artifactId>rest-doc</artifactId>
                <version>1.0.0-SNAPSHOT</version>
                <configuration>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>rest-doc</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
</code>

Parametros opcionais de configuração:
- outputDirectory: caminho onde o arquivo contendo o JSON deve ser salvo (Valor Padrão: "${project.basedir}/")
- outputFileName: nome do arquivo contendo o JSON (Valor Padrão: "rest-doc.js")	
- sourceDirectory: caminho onde os arquivos fontes que serão documentados se encontram (Valor Padrão: "${project.basedir}/src/main")
- serviceFileNamePattern: expressão regular para filtrar os arquivos java a serem documentados (Valor Padrão: "^.*Controller\.java$")


## Exemplo de uso

Na pasta src/main/resources/rest-doc-usage contem um arquivo chamado rest-doc.html, que mostra a utilização da documentação 
gerada pelo rest-doc (o arquivo "rest-doc.js" foi alterado, atribuindo a lista de serviços a uma variável, para tornar possível 
a manipulação da lista e apresentação da mesma).