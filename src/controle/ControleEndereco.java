package controle;

import java.util.List;
import java.util.stream.Collectors;

import modelo.Endereco;
import persistencia.DaoEndereco;
import util.Input;
import util.validacoes.ValidacaoUtil;

/**
 *
 * @author andre.luchesi
 */
public class ControleEndereco {
    private DaoEndereco dao;
    private ValidacaoUtil validator = new ValidacaoUtil(Endereco.class);

    public ControleEndereco() {
        dao = new DaoEndereco();
    }

    public DaoEndereco getDao() {
        return dao;
    }
    
    public void cadastrar() {
        Endereco endereco = new Endereco();
        salvar(endereco);
    }
    
    public void salvar(Endereco endereco) {
        try {
            if (validator.validarEntidade(endereco)) {
                dao.saveOrUpdate(endereco);
            }
        } catch (Exception e) {
            validator.msgErroCadastro(e, "salvar");
        }
    }

    public void atualizar() {
        Endereco end = pesquisar();
        setarDados(end);
        atualizar(end);
    }
    
    public void atualizar(Endereco end) {
        try {
            if (validator.validarEntidade(end)) {
                dao.saveOrUpdate(end);
            }
        } catch (Exception e) {
            validator.msgErroCadastro(e, "editar");
        }
    }
    
    public void setarDados(Endereco end){
        System.out.println("informe a cidade: ");
        end.setCidade(Input.nextLine());
        System.out.println("informe o bairro: ");
        end.setBairro(Input.nextLine());
        System.out.println("informe a rua: ");
        end.setRua(Input.nextLine());
        System.out.println("informe o número: ");
        end.setNumero(Input.next());
    }
    
    public Endereco pesquisar(){
        System.out.println("informe o código do endereço que deseja pesquisar: ");
        int codigo = Input.nextInt();
        return carregarPorId(codigo);
    }
    
    public Endereco carregarPorId(int id) {
        return (Endereco) dao.findById(id);
    }
    
    public List<Endereco> carregarTodos() {
        return dao.findAll().stream().map(e -> (Endereco) e).collect(Collectors.toList());
    }

    public void remover() {
        Endereco endereco = pesquisar();
        if(endereco == null){
            ValidacaoUtil.msgAviso("Cadastro não encontrado", "O endereço não foi localizado no banco de dados!");
        }
        dao.delete(endereco);
    }
   
}
