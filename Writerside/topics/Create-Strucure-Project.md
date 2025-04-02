# Criando Estrutura de Projeto

## Estrutura de Projeto JavaFX

```
+-- src
|  +-- main
|  |  +-- java
|  |  |  +-- br
|  |  |  |  +-- com
|  |  |  |  |  +-- exemplo
|  |  |  |  |  |  +-- Main.java
|  |  |  |  |  |  +-- Controller.java
|  |  |  |  |  |  +-- Model.java
|  |  |  |  |  |  +-- View.java
|  |  |  |  |  |  +-- ...
|  |  +-- resources
|  |  |  +-- css
|  |  |  |  +-- estilo.css
|  |  |  +-- images
|  |  |  |  +-- imagem.png
|  |  |  +-- fxml
|  |  |  |  +-- tela.fxml
|  +-- test
|  |  +-- java
|  |  |  +-- br
|  |  |  |  +-- com
|  |  |  |  |  +-- exemplo
|  |  |  |  |  |  +-- Teste.java
|  +-- module-info.java
+-- pom.xml (ou build.gradle)
+-- README.md
```

### **Explicação:**

* `src`: diretório fonte do projeto
	+ `main`: diretório principal do projeto
		- `java`: diretório de código Java
			- `br/com/exemplo`: pacote do projeto
				- `Main.java`: classe principal do projeto
				- `Controller.java`: classe de controle do projeto
				- `Model.java`: classe de modelo do projeto
				- `View.java`: classe de visualização do projeto
		- `resources`: diretório de recursos do projeto
			- `css`: diretório de folhas de estilo CSS
			- `images`: diretório de imagens
			- `fxml`: diretório de arquivos FXML
	+ `test`: diretório de testes do projeto
		- `java`: diretório de código Java de testes
			- `br/com/exemplo`: pacote de testes do projeto
				- `Teste.java`: classe de teste do projeto
	+ `module-info.java`: arquivo de informações do módulo do projeto
* `pom.xml` (ou `build.gradle`): arquivo de configuração do projeto Maven (ou Gradle)
* `README.md`: arquivo de documentação do projeto

Essa é a estrutura básica de um projeto JavaFX. O diretório `src` contém o código fonte do projeto, o diretório `main` contém o código principal do projeto, e o diretório `test` contém os testes do projeto. O arquivo `module-info.java` contém as informações do módulo do projeto, e o arquivo `pom.xml` (ou `build.gradle`) contém as configurações do projeto Maven (ou Gradle).


## FXML

FXML é uma linguagem de marcação XML (Extensible Markup Language) utilizada para criar interfaces gráficas de usuário (GUIs) em aplicações JavaFX. É uma forma de descrever a estrutura e o layout de uma interface gráfica de usuário de maneira declarativa, sem a necessidade de escrever código Java.

Um arquivo FXML é um arquivo XML que contém elementos que representam componentes gráficos, como botões, labels, text fields, etc. Esses elementos são organizados em uma hierarquia, onde cada elemento pode conter outros elementos.

Aqui está um exemplo simples de um arquivo FXML:
```fxml
<?xml version="1.0" encoding="UTF-8"?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.Button?>

<VBox xmlns="http://javafx.com/javafx" xmlns:fx="http://javafx.com/fxml">
    <Label text="Olá, mundo!" />
    <Button text="Clique aqui" onAction="#handleButtonClicked" />
</VBox>
```
Nesse exemplo, temos:

* Uma declaração de namespace (`xmlns`) que especifica a linguagem FXML e o namespace de JavaFX.
* Uma importação de componentes (`<?import ...?>`) que permite usar componentes específicos de JavaFX, como `VBox`, `Label` e `Button`.
* Um elemento `VBox` que é o contêiner principal da interface gráfica.
* Um elemento `Label` que exibe o texto "Olá, mundo!".
* Um elemento `Button` que tem o texto "Clique aqui" e um evento `onAction` que é disparado quando o botão é clicado.

Quando o arquivo FXML é carregado em uma aplicação JavaFX, o framework JavaFX cria os componentes gráficos correspondentes e os organiza de acordo com a hierarquia definida no arquivo FXML.

O FXML é uma forma poderosa de criar interfaces gráficas de usuário em JavaFX, pois permite:

* Descrever a estrutura e o layout da interface gráfica de maneira declarativa.
* Separar a lógica de apresentação da lógica de negócios.
* Reutilizar componentes gráficos em diferentes partes da aplicação.

