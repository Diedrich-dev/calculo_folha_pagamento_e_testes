package controle;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import modelo.Endereco;
import modelo.Pessoa;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Andre
 */
public class ControlePessoaTest {

    public ControlePessoaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        InputStream inputStream = new ByteArrayInputStream(getDados().getBytes());
        System.setIn(inputStream);
    }

    @After
    public void tearDown() {
    }

    public String getDados(){
        StringBuilder builder = new StringBuilder();
        builder.append("Andre\n");
        builder.append("11111111111\n");
        builder.append("andre@gmail.com\n");
        builder.append("45999999999\n");
        builder.append("Cascavel\n");
        builder.append("Santa Cruz\n");
        builder.append("Av. Tito Muffato\n");
        builder.append("2317\n");
        return builder.toString();
    }

    @Test
    public void testSetDados() {
        Pessoa pessoaTeste = new Pessoa();
        Pessoa pessoaAux  = new Pessoa(null, "Andre", "11111111111",
                "andre@gmail.com", "45999999999", new Endereco(null, "Cascavel", "Santa Cruz", "Av. Tito Muffato", "2317"));
        ControlePessoa controle = new ControlePessoa();
        controle.setarDados(pessoaTeste);
        assertEquals(pessoaAux, pessoaTeste);
    }

}
