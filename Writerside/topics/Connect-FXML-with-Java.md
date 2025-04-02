# Conectando o arquivo FXML ao código Java

O arquivo `Main.java` é o ponto de entrada da nossa aplicação JavaFX. Ele é responsável por carregar a interface gráfica
e conectar o arquivo FXML ao código Java.

**Importando as bibliotecas necessárias**

O código começa importando as bibliotecas necessárias para trabalhar com JavaFX:

```java
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
```

Essas bibliotecas são responsáveis por:

* `Application`: é a classe base para todas as aplicações JavaFX.
* `FXMLLoader`: é a classe responsável por carregar os arquivos FXML.
* `Parent`: é a classe base para todos os elementos da interface gráfica.
* `Scene`: é a classe responsável por definir a cena da interface gráfica.
* `Stage`: é a classe responsável por definir a janela da interface gráfica.

## **Definindo a classe Main**

A classe `Main` é definida como uma subclasse de `Application`:

```java
public class Main extends Application {
```

Isso significa que a classe `Main` herda todos os métodos e propriedades da classe `Application`.

## **Definindo o método start**

O método `start` é chamado quando a aplicação é iniciada:

```java

@Override
public void start(Stage stage) throws Exception {
```

Esse método é responsável por carregar a interface gráfica e conectar o arquivo FXML ao código Java.

## **Carregando o arquivo FXML**

O arquivo FXML é carregado usando o `FXMLLoader`:

```java
Parent screen = FXMLLoader.load(getClass().getResource("screens/student.fxml"));
```

O `FXMLLoader` carrega o arquivo FXML e retorna um objeto `Parent` que representa a interface gráfica.

## **Definindo a cena**

A cena é definida usando o objeto `Parent` carregado anteriormente:

```java
Scene scene = new Scene(screen);
```

A cena é responsável por definir a estrutura da interface gráfica.

## **Definindo a janela**

A janela é definida usando o objeto `Stage` passado como parâmetro para o método `start`:

```java
window.setScene(scene);
window.

show();
window.

setTitle("Prime Bank");
```

A janela é responsável por exibir a interface gráfica.

## **Conectando o arquivo FXML ao código Java**

O arquivo FXML é conectado ao código Java usando o `FXMLLoader`. O `FXMLLoader` carrega o arquivo FXML e retorna um
objeto `Parent` que representa a interface gráfica. Esse objeto é então usado para definir a cena e a janela da
interface gráfica.

## **Método initialize**

O método `initialize` é chamado quando a interface gráfica é carregada. Ele é usado para inicializar os elementos da
interface e definir os eventos que são disparados quando os elementos são clicados.

No nosso caso, o método `initialize` é definido no arquivo `StudentController.java`:

```java
public class StudentController {
    // ...
    @FXML
    public void initialize() {
        // Inicializar os elementos da interface
        // Definir os eventos que são disparados quando os elementos são clicados
    }
}
```

O método `initialize` é chamado quando a interface gráfica é carregada e é responsável por inicializar os elementos da
interface e definir os eventos que são disparados quando os elementos são clicados.

## Nomeando os elementos da interface gráfica

Para acessarmos os elementos precisamos nomear eles e para isso usamos `ID`:

<tabs>

<tab title="Código" id="code" tabindex="1" selected="true">


Nesta tab, você pode ver o código XML que define a tabela de estudantes. O código é o seguinte:

<code-block lang="xml"><![CDATA[
 <TableView fx:id="tv_estudante" layoutX="431.0" layoutY="353.0" prefHeight="359.0" prefWidth="1146.0" AnchorPane.bottomAnchor="15.0" AnchorPane.leftAnchor="15.0" AnchorPane.rightAnchor="15.0" AnchorPane.topAnchor="353.0">
        <columns>
          <TableColumn fx:id="tc_id" prefWidth="75.0" text="ID"/>
            <TableColumn fx:id="tc_nome" prefWidth="75.0" text="Nome"/>
            <TableColumn fx:id="tc_sexo" prefWidth="75.0" text="Sexo"/>
            <TableColumn fx:id="tc_idade" prefWidth="75.0" text="Idade"/>
        </columns>
    <columnResizePolicy>
        <TableView fx:constant="CONSTRAINED_RESIZE_POLICY"/>
    </columnResizePolicy>
</TableView>
]]></code-block>

</tab>

<tab title="No Scene" id="scene">
   <p>Nesta tab, você pode ver as etapas para definir o ID de um elemento no Scene Builder. As etapas são as seguintes:</p>
        <ol>
            <li><strong>Clique no elemento</strong>: clique no elemento que você deseja definir o ID.</li>
            <li><strong>Selecione o item "Code"</strong>: selecione o item "Code" no menu de contexto do elemento.</li>
            <li><strong>Selecione o item "FX:ID"</strong>: selecione o item "FX:ID" no menu de contexto do elemento.</li>
            <li><strong>Defina o ID</strong>: defina o ID do elemento no campo de texto.</li>
        </ol>
        <img alt="Definindo ID no Scene" src="Definindo ID no Scene"/>
</tab>

</tabs>

