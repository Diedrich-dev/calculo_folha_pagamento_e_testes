package controle;

import java.util.List;
import java.util.stream.Collectors;
import modelo.Pessoa;
import persistencia.DaoPessoa;
import util.Input;
import util.validacoes.ValidacaoUtil;

public class ControlePessoa {

    private ValidacaoUtil validator = new ValidacaoUtil(Pessoa.class);
    private DaoPessoa dao;
    protected ControleEndereco controleEndereco;

    public ControlePessoa() {
        dao = new DaoPessoa();
        controleEndereco = new ControleEndereco();
    }
    
    public void cadastrar() {
        Pessoa pessoa = new Pessoa();
        setarDados(pessoa);
        try {
            if (validator.validarEntidade(pessoa)) {
                controleEndereco.salvar(pessoa.getEndereco());
                dao.saveOrUpdate(pessoa);
            }
        } catch (Exception e) {
            validator.msgErroCadastro(e, "salvar");
        }
    }
    
    public void editar() {
        Pessoa pessoa = pesquisar();
        setarDados(pessoa);
        try {
            if (validator.validarEntidade(pessoa)) {
                controleEndereco.atualizar(pessoa.getEndereco());
                dao.saveOrUpdate(pessoa);
            }
        } catch (Exception e) {
            validator.msgErroCadastro(e, "editar");
        }
    }

    public void setarDados(Pessoa p) {
        System.out.println("informe o nome: ");
        p.setNome(Input.nextLine());
        System.out.println("informe o CPF: ");
        p.setCpf(Input.next());
        System.out.println("informe o email: ");
        p.setEmail(Input.next());
        System.out.println("informe o telefone: ");
        p.setTelefone(Input.next());
        controleEndereco.setarDados(p.getEndereco());
    }
    
    public Pessoa pesquisar(){
        System.out.println("informe o código do pessoa que deseja pesquisar: ");
        int codigo = Input.nextInt();
        return carregarPorId(codigo);
    }
    
    public Pessoa carregarPorId(int id) {
        return (Pessoa) dao.findById(id);
    }
    
    public List<Pessoa> carregarTodos() {
        return dao.findAll().stream().map(e -> (Pessoa) e).collect(Collectors.toList());
    }

    public void remover() {
        Pessoa pessoa = pesquisar();
        if(pessoa == null){
            ValidacaoUtil.msgAviso("Cadastro não encontrado", "A Pessoa não foi localizada no banco de dados!");
        }
        dao.delete(pessoa);
    }
}
