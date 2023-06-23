package controle;

import java.util.List;
import java.util.stream.Collectors;
import modelo.ConfiguracaoFolhaPag;
import persistencia.DaoConfiguracaoFolhaPag;
import util.Input;
import util.validacoes.ValidacaoUtil;

/**
 *
 * @author Andre
 */
public class ControleConfiguracaoFolhaPag {
    private DaoConfiguracaoFolhaPag dao;
    private ValidacaoUtil validator = new ValidacaoUtil(ConfiguracaoFolhaPag.class);

    public ControleConfiguracaoFolhaPag() {
        dao = new DaoConfiguracaoFolhaPag();
    }

    public void cadastrar() {

        ConfiguracaoFolhaPag configFolhaPag = new ConfiguracaoFolhaPag();
        setarDados(configFolhaPag);
        try {
            if (validator.validarEntidade(configFolhaPag)) {
                dao.saveOrUpdate(configFolhaPag);
            }
        } catch (Exception e) {
            validator.msgErroCadastro(e, "salvar");
        }

    }

    public void editar() {
        ConfiguracaoFolhaPag configFolhaPag = pesquisar();
        setarDados(configFolhaPag);
        try {
            if (validator.validarEntidade(configFolhaPag)) {
                dao.saveOrUpdate(configFolhaPag);
            }
        } catch (Exception e) {
            validator.msgErroCadastro(e, "editar");
        }

    }

    public void setarDados(ConfiguracaoFolhaPag config) {
        System.out.println("informe o ano de vigência para configuração da folha de pagamento: ");
        config.setAnoVigencia(Input.nextInt());
        System.out.println("informe valor diário do Vale Alimentação: ");
        config.setValorDiaValeAlimentacao(Input.nextDouble());
        System.out.println("informe valor diário do Vale Transporte: ");
        config.setValorDiaValeTransporte(Input.nextDouble());
        System.out.println("informe o percentual de desconto do Vale Alimentação (valor entre 0 a 1): ");
        config.setPercentualDescontoValeAlimentacao(Input.nextDouble());
        System.out.println("informe o percentual de desconto do Vale Transporte (valor entre 0 a 1): ");
        config.setPercentualDescontoValeTransporte(Input.nextDouble());
    
    }
    
    public ConfiguracaoFolhaPag pesquisar(){
        System.out.println("informe o código da Configuração de Folha de Pagamento que deseja pesquisar: ");
        int codigo = Input.nextInt();
        return carregarPorId(codigo);
    }
    
    public ConfiguracaoFolhaPag carregarPorId(int id) {
        return (ConfiguracaoFolhaPag) dao.findById(id);
    }
    
    public List<ConfiguracaoFolhaPag> carregarTodos() {
        return dao.findAll().stream().map(e -> (ConfiguracaoFolhaPag) e).collect(Collectors.toList());
    }

    public void remover() {
        ConfiguracaoFolhaPag configFolhaPag = pesquisar();
        if(configFolhaPag == null){
            ValidacaoUtil.msgAviso("Cadastro não encontrado", "A Configuração de Folha de Pagamento não foi encontrada na base de dados!");
        }
        dao.delete(configFolhaPag);
    }
}
