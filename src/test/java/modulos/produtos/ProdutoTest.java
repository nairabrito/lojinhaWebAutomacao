package modulos.produtos;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import paginas.LoginPage;

import java.time.Duration;

@DisplayName("Testes web do módulo de Produtos")
public class ProdutoTest {

    private WebDriver navegador;

    @BeforeEach
    public void beforeEach() {
        System.setProperty("webdriver.chrome.driver", "/home/naira/drivers/chromedriver_linux64/chromedriver");
        this.navegador = new ChromeDriver();
        this.navegador.manage().window().maximize();
        this.navegador.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        this.navegador.get("http://165.227.93.41/lojinha-web/v2/");
    }
    @Test
    @DisplayName("Não é permitido registrar um produto com valor igual a zero")
    public void testNaoEPermitidoRegistrarProdutoComValorIgualAZero() {

        String mensagemApresentada = new LoginPage(navegador)
                .informarOUsuario("admin")
                .informarASenha("admin")
                .submeterFormularioDeLogin()
                .acessarFormularioAdicaoNovoProduto()
                .informarNomeDoProduto("Macbook pro")
                .informarValorDoProduto("000")
                .informarCoresDoProduto("preto, branco")
                .submeterFormularioDeAdicaoComErro()
                .capturarMensagemApresentada();
        Assertions.assertEquals("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00", mensagemApresentada);

    }

    @Test
    @DisplayName("Não é permitido registrar um produto com valor maior que sete mil")
    public void testNaoEPermitidoRegistrarProdutoComValorMaiorSeteMil() {

        String mensagemApresentada = new LoginPage(navegador)
                .informarOUsuario("admin")
                .informarASenha("admin")
                .submeterFormularioDeLogin()
                .acessarFormularioAdicaoNovoProduto()
                .informarNomeDoProduto("notebook pro")
                .informarValorDoProduto("700001")
                .informarCoresDoProduto("preto, cinza")
                .submeterFormularioDeAdicaoComErro()
                .capturarMensagemApresentada();
        Assertions.assertEquals("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00", mensagemApresentada);

    }

    @Test
    @DisplayName("Posso adicionar produtos que estejam no limite de R$ 0,01")
    public void testPossoAdicionarProdutosComValorDeUmCentavo() {
        String mensagemApresentada = new LoginPage(navegador)
                .informarOUsuario("admin")
                .informarASenha("admin")
                .submeterFormularioDeLogin()
                .acessarFormularioAdicaoNovoProduto()
                .informarNomeDoProduto("notebook pro")
                .informarValorDoProduto("001")
                .informarCoresDoProduto("preto")
                .submeterFormularioDeEdicaoComSucesso()
                .capturarMensagemApresentada();

        Assertions.assertEquals("Produto adicionado com sucesso", mensagemApresentada);

    }

    @Test
    @DisplayName("Posso adicionar produtos que estejam no limite de R$ 7.000,00")
    public void testPossoAdicionarProdutosComValorDeSeteMilReais() {
        String mensagemApresentada = new LoginPage(navegador)
                .informarOUsuario("admin")
                .informarASenha("admin")
                .submeterFormularioDeLogin()
                .acessarFormularioAdicaoNovoProduto()
                .informarNomeDoProduto("notebook pro")
                .informarValorDoProduto("700000")
                .informarCoresDoProduto("preto")
                .submeterFormularioDeEdicaoComSucesso()
                .capturarMensagemApresentada();

        Assertions.assertEquals("Produto adicionado com sucesso", mensagemApresentada);

    }

    @AfterEach
    public void afterEach() {
        navegador.quit();
    }
}
