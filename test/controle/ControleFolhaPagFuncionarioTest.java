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
        folPagFuncionario.setValorValeAlimentacao(100);
        folPagFuncionario.setValorFGTS(160);
        folPagFuncionario.setTotalDescontos(381.54);
        folPagFuncionario.setSalarioLiquido(1485.1);


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
        folPagFuncionario.getFuncionario().setRecebeValeAlimentacao(false);
        folPagFuncionario.setSalarioLiquido(1963.88);
        folPagFuncionario.setTotalDescontos(172.47);
        folPagFuncionario.setValorFGTS(160);

        folPagFuncionarioTest.setHorasTrabalhadas(230);

        controleFolha.calcularFolhadePagamento(folPagFuncionarioTest);

        System.out.println(folPagFuncionario);
        System.out.println(folPagFuncionarioTest);
        assertEquals(folPagFuncionario, folPagFuncionarioTest);
    }

    @Test
    public void salarioAlto(){

        folPagFuncionario.setHorasTrabalhadas(240);
        folPagFuncionario.setSalarioBase(8000);
        folPagFuncionario.setTotalProventos(9090.8);
        folPagFuncionario.setValorHorasExtra(1090.8);
        folPagFuncionario.setValorINSS(876.97);
        folPagFuncionario.getFuncionario().setRecebeValeAlimentacao(false);
        folPagFuncionario.setSalarioLiquido(7124.36);
        folPagFuncionario.setTotalDescontos(1966.44);
        folPagFuncionario.setValorFGTS(640.0);
        folPagFuncionario.setDescontoIR(1089.47);

        folPagFuncionarioTest.setHorasTrabalhadas(240);
        folPagFuncionarioTest.getFuncionario().setSalario(8000.0);
        folPagFuncionarioTest.setFaltasSemJustificativa(0);

        controleFolha.calcularFolhadePagamento(folPagFuncionarioTest);

        System.out.println(folPagFuncionario);
        System.out.println(folPagFuncionarioTest);
        assertEquals(folPagFuncionario, folPagFuncionarioTest);
    }

    @Test
    public void salarioIntermediario(){

        folPagFuncionario.setHorasTrabalhadas(235);
        folPagFuncionario.setSalarioBase(5000);
        folPagFuncionario.setTotalProventos(4844.745);
        folPagFuncionario.setValorHorasExtra(511.425);
        folPagFuncionario.setValorINSS(504.18);
        folPagFuncionario.getFuncionario().setRecebeValeAlimentacao(false);
        folPagFuncionarioTest.getFuncionario().setRecebeValeTransporte(true);
        folPagFuncionario.setSalarioLiquido(3298.455);
        folPagFuncionario.setTotalDescontos(1546.29);
        folPagFuncionario.setValorFGTS(400.0);
        folPagFuncionario.setDescontoIR(375.43);
        folPagFuncionario.setFaltasSemJustificativa(2);

        folPagFuncionarioTest.setHorasTrabalhadas(235);
        folPagFuncionarioTest.getFuncionario().setSalario(5000.0);
        folPagFuncionarioTest.getFuncionario().setRecebeValeTransporte(true);
        folPagFuncionarioTest.setFaltasSemJustificativa(2);

        controleFolha.calcularFolhadePagamento(folPagFuncionarioTest);

        System.out.println(folPagFuncionario);
        System.out.println(folPagFuncionarioTest);
        assertEquals(folPagFuncionario, folPagFuncionarioTest);
    }

    @Test
    public void salarioBaixo(){

        folPagFuncionario.setHorasTrabalhadas(151);
        folPagFuncionario.setSalarioBase(800);
        folPagFuncionario.setTotalProventos(800.0);
        folPagFuncionario.setValorINSS(60.0);
        folPagFuncionario.getFuncionario().setRecebeValeAlimentacao(false);
        folPagFuncionarioTest.getFuncionario().setRecebeValeTransporte(true);
        folPagFuncionario.setSalarioLiquido(695.0);
        folPagFuncionario.setTotalDescontos(105.0);
        folPagFuncionario.setValorFGTS(64.0);

        folPagFuncionarioTest.setHorasTrabalhadas(151);
        folPagFuncionarioTest.getFuncionario().setSalario(800.0);
        folPagFuncionarioTest.getFuncionario().setRecebeValeTransporte(true);
        folPagFuncionarioTest.getFuncionario().getCargo().setCargaHorariaMensal(160);

        controleFolha.calcularFolhadePagamento(folPagFuncionarioTest);

        System.out.println(folPagFuncionario);
        System.out.println(folPagFuncionarioTest);
        assertEquals(folPagFuncionario, folPagFuncionarioTest);
    }

    @Test
    public void horasNormais(){

        folPagFuncionario.setSalarioBase(3595.0);
        folPagFuncionario.setTotalProventos(3595.0);
        folPagFuncionario.setValorINSS(334.46);
        folPagFuncionario.setSalarioLiquido(3126.26);
        folPagFuncionario.setTotalDescontos(468.74);
        folPagFuncionario.setValorFGTS(287.6);
        folPagFuncionario.setDescontoIR(134.28);

        folPagFuncionarioTest.setHorasTrabalhadas(220);
        folPagFuncionarioTest.getFuncionario().setSalario(3595.0);

        controleFolha.calcularFolhadePagamento(folPagFuncionarioTest);

        System.out.println(folPagFuncionario);
        System.out.println(folPagFuncionarioTest);
        assertEquals(folPagFuncionario, folPagFuncionarioTest);
    }

}
