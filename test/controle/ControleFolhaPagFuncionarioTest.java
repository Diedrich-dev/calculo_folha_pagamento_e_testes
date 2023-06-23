package controle;
import modelo.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import util.NumberUtils;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class ControleFolhaPagFuncionarioTest {

    private ControleFolhaPagFuncionario controleFolha = new ControleFolhaPagFuncionario();
    private FolhaPagFuncionario folPagFuncionario;
    private FolhaPagFuncionario folPagFuncionarioTest;

    public ControleFolhaPagFuncionarioTest(){

    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        Endereco end = new Endereco(null, "Cascavel", "Santa Cruz", "Av. Tito Muffato", "2317");
        Cargo cargo = new Cargo(null, "Vendedor", 220);
        ConfiguracaoFolhaPag configuracaoFolhaPag = new ConfiguracaoFolhaPag(null, 2023, true, 20.0, 10.0, 0.06, 0.10 );
        Funcionario funcionario = new Funcionario(
                null, //id
                "João",
                "11111111111",
                "joao@gmail.com",
                "45999999999",
                end,
                "954.80864.59-0", //ctps
                LocalDate.of(2021,3,1), //dataAdmissao;
                null, //dataDemissao;
                cargo, //cargo
                2000.0, //salario;
                false, //recebeValeTransporte;
                true, //recebeValeAlimentacao;
                0 // numeroDependentes;
        );


        folPagFuncionario = new FolhaPagFuncionario(
                null,
                2023,
                05, //mesReferencia;
                LocalDate.of(2023, 5, 5),
                220,
                0,
                funcionario,
                configuracaoFolhaPag
        );

        folPagFuncionarioTest  = new FolhaPagFuncionario(
                null,
                2023,
                05, //mesReferencia;
                LocalDate.of(2023, 5, 5),
                220,
                0,
                funcionario,
                configuracaoFolhaPag
        );
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testeExemplo1(){

        folPagFuncionario.setFaltasSemJustificativa(1);
        folPagFuncionario.setHorasTrabalhadas(211);
        folPagFuncionario.setSalarioBase(2000);
        folPagFuncionario.setTotalProventos(1866.66);
        folPagFuncionario.setValorINSS(148.2);


        folPagFuncionarioTest.setFaltasSemJustificativa(1);
        folPagFuncionarioTest.setHorasTrabalhadas(211);

        controleFolha.calcularFolhadePagamento(folPagFuncionarioTest);

        System.out.println(folPagFuncionario);
        System.out.println(folPagFuncionarioTest);
        assertEquals(folPagFuncionario, folPagFuncionarioTest);
    }

    @Test
    public void testeExemplo2(){

        folPagFuncionario.setHorasTrabalhadas(230);
        folPagFuncionario.setSalarioBase(2000);
        folPagFuncionario.setTotalProventos(2136.35);
        folPagFuncionario.setValorHorasExtra(136.35);
        folPagFuncionario.setValorINSS(172.47);

        folPagFuncionarioTest.setHorasTrabalhadas(230);

        controleFolha.calcularFolhadePagamento(folPagFuncionarioTest);

        System.out.println(folPagFuncionario);
        System.out.println(folPagFuncionarioTest);
        assertEquals(folPagFuncionario, folPagFuncionarioTest);
    }
}
