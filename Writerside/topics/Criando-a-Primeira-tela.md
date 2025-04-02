# Criando a primeira tela

Para criar telas, o comum é usarmos o Scene Builder, que é um programa para manipular FXML

## Tutorial de download:

<tabs group="os">
    <tab id="windows-install" title="Windows" group-key="windows">
        <ol>
            <li>Abra um navegador web e vá para o site da GluonHQ: <a href="https://gluonhq.com/products/scene-builder/">https://gluonhq.com/products/scene-builder/</a></li>
            <li>Clique no botão "Download" no topo da página.</li>
            <li>Selecione a opção "Windows" no menu dropdown.</li>
            <li>Selecione a versão do Scene Builder que você deseja baixar (por exemplo, "Scene Builder 11.0.0").</li>
            <li>Clique no botão "Download" para baixar o arquivo <code>.exe</code>.</li>
            <li>Execute o arquivo baixado e siga as instruções para instalar o Scene Builder.</li>
            <li>Uma vez instalado, você pode encontrar o Scene Builder no menu de aplicativos do seu sistema.</li>
        </ol>
    </tab>
    <tab id="linux-install" title="Linux" group-key="linux">
        <ol>
            <li>Abra um navegador web e vá para o site da GluonHQ: <a href="https://gluonhq.com/products/scene-builder/">https://gluonhq.com/products/scene-builder/</a></li>
            <li>Clique no botão "Download" no topo da página.</li>
            <li>Selecione a opção "Linux" no menu dropdown.</li>
            <li>Selecione a versão do Scene Builder que você deseja baixar (por exemplo, "Scene Builder 11.0.0").</li>
            <li>Clique no botão "Download" para baixar o arquivo <code>.deb</code> (para sistemas baseados em Debian) ou <code>.rpm</code> (para sistemas baseados em RPM).</li>
            <li>Abra o arquivo baixado com um gerenciador de pacotes (como o <code>dpkg</code> ou <code>rpm</code>) e siga as instruções para instalar o Scene Builder.</li>
            <li>Uma vez instalado, você pode encontrar o Scene Builder no menu de aplicativos do seu sistema.</li>
        </ol>
     </tab>
</tabs>



<note>

**Observações**

* Certifique-se de que você tenha o Java 8 ou superior instalado no seu sistema para executar o Scene Builder.
* Se você estiver usando um sistema de 64 bits, certifique-se de baixar a versão de 64 bits do Scene Builder.
* Se você estiver usando um sistema de 32 bits, certifique-se de baixar a versão de 32 bits do Scene Builder.

</note>


<tip>

**Instalação via linha de comando (Linux)**

Se você preferir instalar o Scene Builder via linha de comando, você pode usar os seguintes comandos:

* Para sistemas baseados em Debian:

```
sudo dpkg -i scene-builder-11.0.0.deb
```

* Para sistemas baseados em RPM:

```
sudo rpm -i scene-builder-11.0.0.rpm
```

Substitua `scene-builder-11.0.0.deb` ou `scene-builder-11.0.0.rpm` pelo nome do arquivo que você baixou.

**Instalação via linha de comando (Windows)**

Se você preferir instalar o Scene Builder via linha de comando, você pode usar o seguinte comando:

```
msiexec /i scene-builder-11.0.0.msi
```

Substitua `scene-builder-11.0.0.msi` pelo nome do arquivo que você baixou.


</tip>

## Criando o FXML

Vamos usar o arquivo `student.fxml` como exemplo para entender como funciona o JavaFX.

**Analisando o arquivo `student.fxml`**

O arquivo `student.fxml` é um arquivo XML que define a interface gráfica da nossa aplicação. Aqui está o conteúdo do
arquivo:

```fxml
<?xml version="1.0" encoding="UTF-8"?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.RadioButton?>
<?import javafx.scene.control.Separator?>
<?import javafx.scene.control.TableColumn?>
<?import javafx.scene.control.TableView?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.text.Font?>

<AnchorPane maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="727.0" prefWidth="1176.0" xmlns="http://javafx.com/javafx/22" xmlns:fx="http://javafx.com/fxml/1">
    <!-- Conteúdo do arquivo -->
</AnchorPane>
```

Vamos analisar os elementos do arquivo:

* `AnchorPane`: é o contêiner principal da interface gráfica. Ele é um contêiner que pode conter outros elementos e é
  usado para definir a estrutura da interface.
* `Label`, `Button`, `RadioButton`, `Separator`, `TableColumn`, `TableView`, `TextField`, `GridPane`,
  `ColumnConstraints`, `RowConstraints`, `Font`: são elementos que são usados para criar a interface gráfica da nossa
  aplicação.

**Entendendo os atributos**

Agora que analisamos os elementos, vamos entender os atributos que são usados no arquivo:

* `maxHeight`, `maxWidth`, `minHeight`, `minWidth`, `prefHeight`, `prefWidth`: são atributos que são usados para definir
  o tamanho do contêiner `AnchorPane`.
* `xmlns` e `xmlns:fx`: são atributos que são usados para definir o namespace do arquivo FXML.




## Conectando o arquivo FXML ao código Java

Agora que entendemos o arquivo FXML, vamos conectar ele ao código Java. No nosso caso, o arquivo FXML é conectado ao
arquivo `StudentController.java`.

O arquivo `StudentController.java` é responsável por controlar a interface gráfica da nossa aplicação. Ele tem métodos
que são chamados quando os eventos são disparados.

No nosso caso, o método `initialize` é chamado quando a interface gráfica é carregada. Ele é usado para inicializar os
elementos da interface e definir os eventos que são disparados quando os elementos são clicados.

